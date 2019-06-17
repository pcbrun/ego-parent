package com.ego.manage.service.impl;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;
import com.ego.redis.dao.JedisDao;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther:pcb
 * @Date:19/5/27
 * @Description:com.ego.service.impl
 * @version:1.0
 */
@Service
public class TbItemServiceImpl implements TbItemService {
    @Reference
    private TbItemDubboService tbItemDubboServiceImpl;
    @Reference
    private TbItemDescDubboService tbItemDescDubboServiceImpl;
    @Resource
    private JedisDao jedisDaoImpl;

    @Value("${solr.add.url}")
    String addUrl;
    @Value("${solr.del.url}")
    String delUrl;
    @Value("${redis.item.key}")
    String itemKey;

    @Override
    public EasyUIDataGrid showPage(int page, int rows) {
        return tbItemDubboServiceImpl.show(page, rows);
    }

    @Override
    public EgoResult selParamByItemId(long itemId) {
        EgoResult er = new EgoResult();
        TbItemParamItem tbItemParamItem = tbItemDubboServiceImpl.selParamByItemId(itemId);
        if (tbItemParamItem != null) {
            er.setStatus(200);
            er.setData(tbItemParamItem);
        }
        return er;
    }

    @Override
    public EgoResult selDescByItemId(long itemId) {
        EgoResult er = new EgoResult();
        TbItemDesc tbItemDesc = tbItemDubboServiceImpl.selDescByItemId(itemId);
        if (tbItemDesc != null) {
            er.setStatus(200);
            er.setData(tbItemDesc);
        }
        return er;
    }

    @Override
    public int updateStatus(String ids, byte status) {
        int result = 0;
        TbItem tbItem = new TbItem();
        String[] idsStr = ids.split(",");
        for (String id : idsStr) {
            tbItem.setId(Long.parseLong(id));
            tbItem.setStatus(status);
            Date date = new Date();
            tbItem.setUpdated(date);
            result += tbItemDubboServiceImpl.updItemStatus(tbItem);

            // 在solr中新增上架商品
            if(status==1){
                new Thread() {
                    @Override
                    public void run() {
                        Map<String, Object> map = new HashMap<>();
                        map.put("tbItem", tbItemDubboServiceImpl.selById(Long.parseLong(id)));
                        map.put("itemDesc", tbItemDescDubboServiceImpl.selByItemId(Long.parseLong(id)));
                        // 使用HttpClient向其他项目的控制器发送请求
                        HttpClientUtil.doPostJson(addUrl, JsonUtils.objectToJson(map));
                    }
                }.start();
            }

            if (status == 2 || status == 3) {
                // 删除redis中下架或删除的商品
                String key = itemKey + id;
                if (jedisDaoImpl.exists(key)) {
                    jedisDaoImpl.del(key);
                }
                // 修改solr中的数据
                new Thread() {
                    @Override
                    public void run() {
                        // 使用HttpClient向其他项目的控制器发送请求
                        HttpClientUtil.doPostJson(delUrl, JsonUtils.objectToJson(id));
                    }
                }.start();
            }
        }
        if (result == idsStr.length) {
            return 1;
        }
        return 0;
    }

    @Override
    public int insert(TbItem tbItem, String desc, String itemParams) throws Exception {
        // 不考虑事务回滚的方法
        /* long id= IDUtils.genItemId();
         Date date=new Date();
         tbItem.setId(id);
         tbItem.setCreated(date);
         tbItem.setUpdated(date);
         tbItem.setStatus((byte)1);

         int result=tbItemDubboServiceImpl.insItem(tbItem);
         if(result>0){
             TbItemDesc tbItemDesc=new TbItemDesc();
             tbItemDesc.setItemId(id);
             tbItemDesc.setItemDesc(desc);
             tbItemDesc.setCreated(date);
             tbItemDesc.setUpdated(date);
             result+=tbItemDescDubboServiceImpl.insTbItemDesc(tbItemDesc);
         }
         if(result==2){
             return 1;
         }
         return 0;*/

        // 调用dubbo中考虑事务回滚功能的方法
        long id = IDUtils.genItemId();
        Date date = new Date();
        tbItem.setId(id);
        tbItem.setCreated(date);
        tbItem.setUpdated(date);
        tbItem.setStatus((byte) 1);

        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(id);
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setCreated(date);
        tbItemDesc.setUpdated(date);

        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setItemId(id);
        tbItemParamItem.setParamData(itemParams);
        tbItemParamItem.setCreated(date);
        tbItemParamItem.setUpdated(date);

        int result = 0;
        result = tbItemDubboServiceImpl.insItemDesc(tbItem, tbItemDesc, tbItemParamItem);

        new Thread() {
            @Override
            public void run() {
                Map<String, Object> map = new HashMap<>();
                map.put("tbItem", tbItem);
                map.put("itemDesc", desc);
                // 使用HttpClient向其他项目的控制器发送请求
                HttpClientUtil.doPostJson(addUrl, JsonUtils.objectToJson(map));
            }
        }.start();

        return result;
    }

    @Override
    public EgoResult updateItem(TbItem tbItem, String desc, String itemParams, long itemParamId) {
        TbItem itemSelect = tbItemDubboServiceImpl.selById(tbItem.getId());
        Date date = new Date();
        tbItem.setUpdated(date);

        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(tbItem.getId());
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setCreated(itemSelect.getCreated());
        tbItemDesc.setUpdated(date);

        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setId(itemParamId);
        tbItemParamItem.setItemId(tbItem.getId());
        tbItemParamItem.setParamData(itemParams);
        tbItemParamItem.setCreated(itemSelect.getCreated());
        tbItemParamItem.setUpdated(date);

        EgoResult er = new EgoResult();
        int result = 0;
        try {
            result = tbItemDubboServiceImpl.updItem(tbItem, tbItemDesc, tbItemParamItem);
        } catch (Exception e) {
            e.printStackTrace();
            er.setData(e.getMessage());
        }
        if (result == 1) {
            // 删除redis中修改过的商品
            String key = itemKey + tbItem.getId();
            if (jedisDaoImpl.exists(key)) {
                jedisDaoImpl.del(key);
            }
            er.setStatus(200);
            // 修改solr中的数据
            new Thread() {
                @Override
                public void run() {
                    Map<String, Object> map = new HashMap<>();
                    map.put("tbItem", tbItem);
                    map.put("itemDesc", desc);
                    // 使用HttpClient向其他项目的控制器发送请求
                    HttpClientUtil.doPostJson(addUrl, JsonUtils.objectToJson(map));
                }
            }.start();
        }

        return er;
    }
}

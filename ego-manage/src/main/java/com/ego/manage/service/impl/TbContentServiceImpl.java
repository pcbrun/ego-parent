package com.ego.manage.service.impl;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.manage.service.TbContentService;
import com.ego.pojo.TbContent;
import com.ego.redis.dao.JedisDao;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther:pcb
 * @Date:19/5/30
 * @Description:com.ego.manage.service.impl
 * @version:1.0
 */
@Service
public class TbContentServiceImpl implements TbContentService {
    @Reference
    private TbContentDubboService tbContentDubboServiceImpl;
    @Resource
    private JedisDao jedisDaoImpl;
    @Value("${redis.bigPic.key}")
    String key;

    @Override
    public EasyUIDataGrid showContent(long categoryId, int page, int rows) {
        return tbContentDubboServiceImpl.selByPage(categoryId,page,rows);
    }

    @Override
    public EgoResult insContent(TbContent tbContent) {
        EgoResult er=new EgoResult();
        Date date=new Date();
        tbContent.setCreated(date);
        tbContent.setUpdated(date);
        int result=tbContentDubboServiceImpl.insContent(tbContent);
        if(result>0){
            er.setStatus(200);
        }

        // 判断redis中是否有缓存数据
        if(jedisDaoImpl.exists(key)){
            String value = jedisDaoImpl.get(key);
            List<HashMap> listJson = JsonUtils.jsonToList(value, HashMap.class);
            HashMap<String,Object> map=new HashMap<>();

            map.put("srcB",tbContent.getPic2());
            map.put("height",240);
            map.put("alt","对不起，图片加载失败");
            map.put("width",670);
            map.put("src",tbContent.getPic());
            map.put("widthB",550);
            map.put("href",tbContent.getUrl());
            map.put("heightB",240);

            // 保证redis中只有6个数据
            if(listJson.size()==6){
                // 移除最后一个数据
                listJson.remove(5);
            }
            // 把新增数据添加到第一个
            listJson.add(0,map);


            jedisDaoImpl.set(key,JsonUtils.objectToJson(listJson),60*60*24*10);
        }

        return er;
    }
}

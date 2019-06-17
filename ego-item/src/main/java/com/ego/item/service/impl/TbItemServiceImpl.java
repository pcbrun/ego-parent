package com.ego.item.service.impl;

import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.item.service.TbItemService;
import com.ego.pojo.TbItem;
import com.ego.redis.dao.JedisDao;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Auther:pcb
 * @Date:19/6/9
 * @Description:com.ego.item.service.impl
 * @version:1.0
 */
@Service
public class TbItemServiceImpl implements TbItemService {
    @Reference
    private TbItemDubboService tbItemDubboServiceImpl;
    @Resource
    private JedisDao jedisDaoImpl;

    @Value("${redis.item.key}")
    String itemKey;

    @Override
    public TbItemChild show(Long id) {
        String key = itemKey + id;
        if (jedisDaoImpl.exists(key)) {
            String json = jedisDaoImpl.get(key);
            if (json != null && !json.equals("")) {
                return JsonUtils.jsonToPojo(json, TbItemChild.class);
            }
        }
        TbItem tbItem = tbItemDubboServiceImpl.selById(id);
        TbItemChild tbItemChild = new TbItemChild();

        tbItemChild.setId(tbItem.getId());
        tbItemChild.setTitle(tbItem.getTitle());
        tbItemChild.setSellPoint(tbItem.getSellPoint());
        tbItemChild.setPrice(tbItem.getPrice());
        tbItemChild.setImages(tbItem.getImage() == null || tbItem.getImage().equals("") ? new String[1] : tbItem.getImage().split(","));

        // 将查询到的数据放入redis中
        jedisDaoImpl.set(key, JsonUtils.objectToJson(tbItemChild), 60*60*24*10);

        return tbItemChild;
    }
}
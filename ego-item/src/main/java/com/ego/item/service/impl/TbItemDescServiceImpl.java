package com.ego.item.service.impl;

import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.item.service.TbItemDescService;
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
public class TbItemDescServiceImpl implements TbItemDescService {
    @Reference
    private TbItemDescDubboService tbItemDescDubboServiceImpl;
    @Resource
    private JedisDao jedisDaoImpl;

    @Value("${redis.itemDesc.key}")
    String itemDescKey;

    @Override
    public String showDesc(long itemId) {
        String key=itemDescKey+itemId;
        if(jedisDaoImpl.exists(key)){
            String itemDesc = jedisDaoImpl.get(key);
            if(itemDesc!=null&&!itemDesc.equals("")){
                return itemDesc;
            }
        }
        String itemDesc = tbItemDescDubboServiceImpl.selByItemId(itemId).getItemDesc();
        jedisDaoImpl.set(key,itemDesc,60*60*24*10);

        return itemDesc;
    }
}

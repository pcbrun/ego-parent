package com.ego.portal.service.impl;

import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.pojo.TbContent;
import com.ego.portal.service.TbContentService;
import com.ego.redis.dao.JedisDao;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther:pcb
 * @Date:19/5/31
 * @Description:com.ego.portal.service.impl
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
    public String showBigPic() {
        // 先判断在redis中是否存在
        if(jedisDaoImpl.exists(key)){
            String value=jedisDaoImpl.get(key);
            // 如果redis中存在且不为空则返回值
            if(value!=null&&!value.equals("")){
                return value;
            }
        }

        // 如果redis中不存在，就从mysql中查询出来，再放到redis中
        List<TbContent> list = tbContentDubboServiceImpl.selByCount(6, true);
        List<Map<String,Object>> listResult=new ArrayList<>();

        for(TbContent tbContent:list){
            Map<String,Object> map=new HashMap<>();
            map.put("srcB",tbContent.getPic2());
            map.put("height",240);
            map.put("alt","对不起，图片加载失败");
            map.put("width",670);
            map.put("src",tbContent.getPic());
            map.put("widthB",550);
            map.put("href",tbContent.getUrl());
            map.put("heightB",240);

            listResult.add(map);
        }

        String listJson = JsonUtils.objectToJson(listResult);
        jedisDaoImpl.set(key,listJson,60*60*24*10);

        return listJson;
    }
}

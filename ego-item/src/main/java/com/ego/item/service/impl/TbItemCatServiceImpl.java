package com.ego.item.service.impl;

import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.item.pojo.PortalMenu;
import com.ego.item.pojo.PortalMenuNode;
import com.ego.item.service.TbItemCatService;
import com.ego.pojo.TbItemCat;
import com.ego.redis.dao.JedisDao;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther:pcb
 * @Date:19/5/29
 * @Description:com.ego.item.service.impl
 * @version:1.0
 */
@Service
public class TbItemCatServiceImpl implements TbItemCatService {
    @Reference
    private TbItemCatDubboService tbItemCatDubboServiceImpl;
    @Resource
    private JedisDao jedisDaoImpl;
    @Value("${redis.itemCat.key}")
    String key;

    @Override
    public PortalMenu showCatMenu() {
        // 判断redis中是否存在该数据
        if(jedisDaoImpl.exists(key)){
            String value = jedisDaoImpl.get(key);
            if(value!=null&&!value.equals("")){
                return JsonUtils.jsonToPojo(value,PortalMenu.class);
            }
        }

        // 查询出所有的一级菜单
        List<TbItemCat> list = tbItemCatDubboServiceImpl.show(0);
        PortalMenu pm = new PortalMenu();
        pm.setData(selAllMenu(list));

        // 将mysql中查询到的结果放到redis中
        jedisDaoImpl.set(key,JsonUtils.objectToJson(pm),60*60*24*10);

        return pm;
    }

    /**
     * 将查询到的分类全部封装成需要的数据格式并返回
     *
     * @param list
     * @return
     */
    public List<Object> selAllMenu(List<TbItemCat> list) {
        List<Object> listNode = new ArrayList<>();
        for (TbItemCat tbItemCat : list) {
            if (tbItemCat.getIsParent()) {
                PortalMenuNode pmn = new PortalMenuNode();
                pmn.setU("/products/" + tbItemCat.getId() + ".html");
                pmn.setN("<a href='/products/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a>");
                pmn.setI(selAllMenu(tbItemCatDubboServiceImpl.show(tbItemCat.getId())));
                listNode.add(pmn);
            } else {
                listNode.add("/products/" + tbItemCat.getId() + ".html|" + tbItemCat.getName());
            }
        }
        return listNode;
    }
}

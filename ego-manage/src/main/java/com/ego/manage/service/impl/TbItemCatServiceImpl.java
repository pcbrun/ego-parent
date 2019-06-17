package com.ego.manage.service.impl;

import com.ego.commons.pojo.EasyUITree;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.manage.service.TbItemCatService;
import com.ego.pojo.TbItemCat;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther:pcb
 * @Date:19/5/28
 * @Description:com.ego.manage.service.impl
 * @version:1.0
 */
@Service
public class TbItemCatServiceImpl implements TbItemCatService {
    @Reference
    private TbItemCatDubboService tbItemCatDubboServiceImpl;

    @Override
    public List<EasyUITree> show(long id) {
        List<TbItemCat> list = tbItemCatDubboServiceImpl.show(id);
        List<EasyUITree> listTree=new ArrayList<>();
        for (TbItemCat cat:list){
            EasyUITree tree=new EasyUITree();
            tree.setId(cat.getId());
            tree.setText(cat.getName());
            tree.setState(cat.getIsParent()?"closed":"open");
            listTree.add(tree);
        }
        return listTree;
    }
}

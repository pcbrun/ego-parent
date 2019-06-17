package com.ego.manage.service.impl;

import com.ego.commons.pojo.EasyUITree;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.IDUtils;
import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.manage.service.TbContentCategoryService;
import com.ego.pojo.TbContentCategory;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Auther:pcb
 * @Date:19/5/30
 * @Description:com.ego.manage.service.impl
 * @version:1.0
 */
@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService {
    @Reference
    private TbContentCategoryDubboService tbContentCategoryDubboServiceImpl;

    @Override
    public List<EasyUITree> showContentCategory(long id) {
        List<TbContentCategory> list = tbContentCategoryDubboServiceImpl.selByPid(id);
        List<EasyUITree> listTree = new ArrayList<>();
        for (TbContentCategory tbContentCategory : list) {
            EasyUITree tree = new EasyUITree();
            tree.setId(tbContentCategory.getId());
            tree.setText(tbContentCategory.getName());
            tree.setState(tbContentCategory.getIsParent() ? "closed" : "open");
            listTree.add(tree);
        }
        return listTree;
    }

    @Override
    public EgoResult insertContentCategory(TbContentCategory tbContentCategory) {
        EgoResult er = new EgoResult();
        // 判断当前新增节点名称是否已存在
        List<TbContentCategory> children = tbContentCategoryDubboServiceImpl.selByPid(tbContentCategory.getParentId());
        for (TbContentCategory child : children) {
            if (tbContentCategory.getName().equals(child.getName())) {
                er.setData("该分类名已存在");
                return er;
            }
        }

        long id = IDUtils.genItemId();
        tbContentCategory.setId(id);
        tbContentCategory.setIsParent(false);
        tbContentCategory.setStatus(1);
        tbContentCategory.setSortOrder(1);
        Date date = new Date();
        tbContentCategory.setCreated(date);
        tbContentCategory.setUpdated(date);
        int result = tbContentCategoryDubboServiceImpl.insTbContentCategory(tbContentCategory);
        if (result > 0) {
            TbContentCategory parent = new TbContentCategory();
            parent.setId(tbContentCategory.getParentId());
            parent.setIsParent(true);
            tbContentCategoryDubboServiceImpl.updTbContentCategory(parent);
        }
        er.setStatus(200);
        Map<String, Long> map = new HashMap<>();
        map.put("id", id);
        er.setData(map);
        return er;
    }

    @Override
    public EgoResult updateContentCategory(TbContentCategory tbContentCategory) {
        EgoResult er = new EgoResult();
        // 查询当前节点信息
        TbContentCategory categorySelect = tbContentCategoryDubboServiceImpl.selById(tbContentCategory.getId());
        // 判断当前修改节点名称是否已存在
        List<TbContentCategory> children = tbContentCategoryDubboServiceImpl.selByPid(categorySelect.getParentId());
        for (TbContentCategory child : children) {
            if (tbContentCategory.getName().equals(child.getName())) {
                er.setData("该分类名已存在");
                return er;
            }
        }
        Date date = new Date();
        tbContentCategory.setUpdated(date);
        int result = tbContentCategoryDubboServiceImpl.updTbContentCategory(tbContentCategory);
        if (result > 0) {
            er.setStatus(200);
        }
        return er;
    }

    @Override
    public EgoResult delete(TbContentCategory tbContentCategory) {
        EgoResult er = new EgoResult();
        Date date = new Date();
        tbContentCategory.setStatus(2);
        tbContentCategory.setUpdated(date);
        int result = tbContentCategoryDubboServiceImpl.updTbContentCategory(tbContentCategory);
        if (result > 0) {
            er.setStatus(200);
        } else {
            er.setData("删除失败，可能原因：数据已经不存在");
        }

        // 验证修改父菜单的IsParent属性
        // 前端有问题，新建之后马上删除返回父id，刷新之后删除不会返回父id
        TbContentCategory categorySelect = tbContentCategoryDubboServiceImpl.selById(tbContentCategory.getId());
        if (categorySelect.getParentId() != 0) {
            List<TbContentCategory> list = tbContentCategoryDubboServiceImpl.selByPid(categorySelect.getParentId());
            if (list == null || list.size() == 0) {
                TbContentCategory parent = new TbContentCategory();
                parent.setId(categorySelect.getParentId());
                parent.setIsParent(false);
                parent.setUpdated(date);
                tbContentCategoryDubboServiceImpl.updTbContentCategory(parent);
            }
        }

        return er;
    }
}

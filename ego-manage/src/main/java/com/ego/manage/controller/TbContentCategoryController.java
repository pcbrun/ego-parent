package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUITree;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbContentCategoryService;
import com.ego.pojo.TbContentCategory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther:pcb
 * @Date:19/5/30
 * @Description:com.ego.manage.controller
 * @version:1.0
 */
@Controller
public class TbContentCategoryController {
    @Resource
    private TbContentCategoryService tbContentCategoryServiceImpl;

    /**
     * 显示内容类目
     * @param id
     * @return
     */
    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITree> showContentCategory(@RequestParam(defaultValue = "0") long id){
        return tbContentCategoryServiceImpl.showContentCategory(id);
    }

    /**
     * 新增内容类目
     * @param tbContentCategory
     * @return
     */
    @RequestMapping("/content/category/create")
    @ResponseBody
    public EgoResult insertContentCategory(TbContentCategory tbContentCategory){
        return tbContentCategoryServiceImpl.insertContentCategory(tbContentCategory);
    }

    /**
     * 内容类目重命名
     * @param tbContentCategory
     * @return
     */
    @RequestMapping("/content/category/update")
    @ResponseBody
    public EgoResult updateContentCategory(TbContentCategory tbContentCategory){
        return tbContentCategoryServiceImpl.updateContentCategory(tbContentCategory);
    }

    /**
     * 删除内容类目
     * @param tbContentCategory
     * @return
     */
    @RequestMapping("/content/category/delete")
    @ResponseBody
    public EgoResult deleteContentCategory(TbContentCategory tbContentCategory){
        return tbContentCategoryServiceImpl.delete(tbContentCategory);
    }
}

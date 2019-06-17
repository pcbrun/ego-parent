package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbContentService;
import com.ego.pojo.TbContent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Auther:pcb
 * @Date:19/5/30
 * @Description:com.ego.manage.controller
 * @version:1.0
 */
@Controller
public class TbContentController {
    @Resource
    private TbContentService tbContentServiceImpl;

    /**
     * 显示内容信息
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/content/query/list")
    @ResponseBody
    public EasyUIDataGrid showContent(long categoryId,int page,int rows){
        return tbContentServiceImpl.showContent(categoryId,page,rows);
    }

    @RequestMapping("/content/save")
    @ResponseBody
    public EgoResult insContent(TbContent tbContent){
        return tbContentServiceImpl.insContent(tbContent);
    }
}

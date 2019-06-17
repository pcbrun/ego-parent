package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemParamService;
import com.ego.pojo.TbItemParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Auther:pcb
 * @Date:19/5/29
 * @Description:com.ego.manage.controller
 * @version:1.0
 */
@Controller
public class TbItemParamController {
    @Resource
    private TbItemParamService tbItemParamServiceImpl;

    /**
     * 分页显示商品参数
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/item/param/list")
    @ResponseBody
    public EasyUIDataGrid showPage(int page,int rows){
        return tbItemParamServiceImpl.showPage(page, rows);
    }

    /**
     * 批量删除商品参数
     * @param ids
     * @return
     */
    @RequestMapping("/item/param/delete")
    @ResponseBody
    public EgoResult delete(String ids){
        EgoResult er=new EgoResult();
        try {
            int result=tbItemParamServiceImpl.delete(ids);
            if(result==1){
                er.setStatus(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            er.setData(e.getMessage());
        }
        return er;
    }

    /**
     * 点击商品类目按钮显示添加分组按钮
     * 判断类目是否已经添加模板
     * @param itemCatId
     * @return
     */
    @RequestMapping("/item/param/query/itemcatid/{itemCatId}")
    @ResponseBody
    public EgoResult showParam(@PathVariable long itemCatId){
        return tbItemParamServiceImpl.showParam(itemCatId);
    }

    /**
     * 商品类目参数模板新增
     * @param itemCatId
     * @param tbItemParam
     * @return
     */
    @RequestMapping("/item/param/save/{itemCatId}")
    @ResponseBody
    public EgoResult insert(@PathVariable long itemCatId, TbItemParam tbItemParam){
        tbItemParam.setItemCatId(itemCatId);
        return tbItemParamServiceImpl.insert(tbItemParam);
    }
}

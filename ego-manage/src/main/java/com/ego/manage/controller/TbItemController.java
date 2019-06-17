package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Auther:pcb
 * @Date:19/5/27
 * @Description:com.ego.manage.controller
 * @version:1.0
 */
@Controller
public class TbItemController {
    @Resource
    private TbItemService tbItemServiceImpl;

    /**
     * 分页显示商品
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGrid showPage(int page,int rows){
        return tbItemServiceImpl.showPage(page, rows);
    }

    /**
     * 显示商品编辑页面
     * @return
     */
    @RequestMapping("/rest/page/item-edit")
    public String showItemEdit(){
        return "item-edit";
    }

    /**
     * 商品上架
     * @param ids
     * @return
     */
    @RequestMapping("/rest/item/reshelf")
    @ResponseBody
    public EgoResult reshelf(String ids){
        EgoResult er=new EgoResult();
        int result=tbItemServiceImpl.updateStatus(ids,(byte)1);
        if (result==1){
            er.setStatus(200);
        }
        return er;
    }

    /**
     * 商品下架
     * @param ids
     * @return
     */
    @RequestMapping("/rest/item/instock")
    @ResponseBody
    public EgoResult instock(String ids){
        EgoResult er=new EgoResult();
        int result=tbItemServiceImpl.updateStatus(ids,(byte)2);
        if (result==1){
            er.setStatus(200);
        }
        return er;
    }

    /**
     * 商品删除
     * @param ids
     * @return
     */
    @RequestMapping("/rest/item/delete")
    @ResponseBody
    public EgoResult delete(String ids){
        EgoResult er=new EgoResult();
        int result=tbItemServiceImpl.updateStatus(ids,(byte)3);
        if (result==1){
            er.setStatus(200);
        }
        return er;
    }

    /**
     * 商品新增
     * @param tbItem
     * @param desc
     * @return
     */
    @RequestMapping("/item/save")
    @ResponseBody
    public EgoResult insert(TbItem tbItem,String desc,String itemParams){
        EgoResult er=new EgoResult();
        int result;
        try {
            result = tbItemServiceImpl.insert(tbItem, desc,itemParams);
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
     * 回显商品参数信息
     * @param itemId
     * @return
     */
    @RequestMapping("/rest/item/param/item/query/{itemId}")
    @ResponseBody
    public EgoResult showItemParam(@PathVariable long itemId){
        return tbItemServiceImpl.selParamByItemId(itemId);
    }

    /**
     * 回显商品描述信息
     * @param itemId
     * @return
     */
    @RequestMapping("/rest/item/query/item/desc/{itemId}")
    @ResponseBody
    public EgoResult showItemDesc(@PathVariable long itemId){
        return tbItemServiceImpl.selDescByItemId(itemId);
    }

    /**
     * 商品修改
     * @param tbItem
     * @param desc
     * @param itemParams
     * @return
     */
    @RequestMapping("/rest/item/update")
    @ResponseBody
    public EgoResult updateItem(TbItem tbItem,String desc,String itemParams,long itemParamId){
        return tbItemServiceImpl.updateItem(tbItem,desc,itemParams,itemParamId);
    }
}

package com.ego.item.controller;

import com.ego.item.service.TbItemParamItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Auther:pcb
 * @Date:19/6/9
 * @Description:com.ego.item.controller
 * @version:1.0
 */
@Controller
public class TbItemParamItemController {
    @Resource
    private TbItemParamItemService tbItemParamItemServiceImpl;

    /**
     * 显示商品规格参数
     * @param id
     * @return
     */
    @RequestMapping(value = "/item/param/{id}.html",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String showParam(@PathVariable long id){
        return tbItemParamItemServiceImpl.showParam(id);
    }
}

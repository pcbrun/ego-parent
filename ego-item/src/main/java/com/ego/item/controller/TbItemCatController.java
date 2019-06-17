package com.ego.item.controller;

import com.ego.item.service.TbItemCatService;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Auther:pcb
 * @Date:19/5/29
 * @Description:com.ego.item.controller
 * @version:1.0
 */
@Controller
@CrossOrigin
public class TbItemCatController {
    @Resource
    private TbItemCatService tbItemCatServiceImpl;

    /**
     * 返回jsonp数据格式的所有商品类目信息
     * @param callback
     * @return
     */
    @RequestMapping("/rest/itemcat/all")
    @ResponseBody
    public MappingJacksonValue showMenu(String callback) {
        MappingJacksonValue mjv=new MappingJacksonValue(tbItemCatServiceImpl.showCatMenu());
        mjv.setJsonpFunction(callback);
        return mjv;
    }
}

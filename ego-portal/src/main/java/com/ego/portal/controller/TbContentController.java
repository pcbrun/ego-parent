package com.ego.portal.controller;

import com.ego.portal.service.TbContentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @Auther:pcb
 * @Date:19/5/31
 * @Description:com.ego.portal.controller
 * @version:1.0
 */
@Controller
public class TbContentController {
    @Resource
    private TbContentService tbContentServiceImpl;

    @RequestMapping("/showBigPic")
    public String showBicPic(Model model){
        model.addAttribute("ad1",tbContentServiceImpl.showBigPic());
        return "index";
    }
}

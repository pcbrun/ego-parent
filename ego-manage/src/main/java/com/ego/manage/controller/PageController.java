package com.ego.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther:pcb
 * @Date:19/5/27
 * @Description:com.ego.manage.controller
 * @version:1.0
 */
@Controller
public class PageController {
    @RequestMapping("/")
    public String welcome(){
        return "index";
    }

    @RequestMapping("{page}")
    public String showPage(@PathVariable String page){
        return page;
    }
}

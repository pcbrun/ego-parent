package com.ego.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther:pcb
 * @Date:19/5/29
 * @Description:com.ego.portal.controller
 * @version:1.0
 */
@Controller
public class PageController {
    @RequestMapping("/")
    public String welcome(){
        System.out.println("执行了ego-portal");
        return "forward:/showBigPic";
    }
}

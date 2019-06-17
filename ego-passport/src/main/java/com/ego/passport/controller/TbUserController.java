package com.ego.passport.controller;

import com.ego.commons.pojo.EgoResult;
import com.ego.passport.service.TbUserService;
import com.ego.pojo.TbUser;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @Auther:pcb
 * @Date:19/6/10
 * @Description:com.ego.passport.controller
 * @version:1.0
 */
@Controller
public class TbUserController {
    @Resource
    private TbUserService tbUserServiceImpl;

    /**
     * 显示登录页面
     *
     * @param referer
     * @param model
     * @param interurl
     * @return
     */
    @RequestMapping(value = "/user/showLogin")
    public String showLogin(@RequestHeader(value = "referer", defaultValue = "") String referer, Model model, String interurl) {
        if (interurl != null && !interurl.equals("")) {
            try {
                interurl = new String(interurl.getBytes("iso-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            model.addAttribute("redirect", interurl);
        } else if (!referer.equals("") && !referer.substring(0, 32).equals("http://localhost:8084/user/showRegister")) {
            model.addAttribute("interurl", referer);
            model.addAttribute("redirect", referer);
        }
        return "login";
    }

    /**
     * 登录功能
     *
     * @param user
     * @return
     */
    @RequestMapping("/user/login")
    @ResponseBody
    public EgoResult login(TbUser user, HttpServletRequest request, HttpServletResponse response) {
        return tbUserServiceImpl.login(user, request, response);
    }

    /**
     * 通过token获取用户信息
     *
     * @param token
     * @param callback
     * @return
     */
    @RequestMapping("/user/token/{token}")
    @ResponseBody
    public Object getUserInfo(@PathVariable String token, String callback) {
        EgoResult er = tbUserServiceImpl.getUserInfoByToken(token);
        if (callback != null && !callback.equals("")) {
            MappingJacksonValue mjv = new MappingJacksonValue(er);
            mjv.setJsonpFunction(callback);
            return mjv;
        }
        return er;
    }

    /**
     * 退出登录
     *
     * @param token
     * @param callback
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/user/logout/{token}")
    @ResponseBody
    public Object getUserInfo(@PathVariable String token, String callback, HttpServletRequest request, HttpServletResponse response) {
        EgoResult er = tbUserServiceImpl.logout(token, request, response);
        if (callback != null && !callback.equals("")) {
            MappingJacksonValue mjv = new MappingJacksonValue(er);
            mjv.setJsonpFunction(callback);
            return mjv;
        }

        return er;
    }

    /**
     * 显示注册界面
     *
     * @param referer
     * @param model
     * @param interurl
     * @return
     */
    @RequestMapping(value = "/user/showRegister")
    public String showRegister(@RequestHeader(value = "referer", defaultValue = "") String referer, Model model, String interurl) {
        if (interurl != null && !interurl.equals("")) {
            try {
                interurl = new String(interurl.getBytes("iso-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            model.addAttribute("interurl", interurl);
        } else if (!referer.equals("")) {
            model.addAttribute("interurl", referer);
        }
        return "register";
    }

    /**
     * 检查注册数据是否可用
     *
     * @param param
     * @param type
     * @param callback
     * @return
     */
    @RequestMapping("/user/check/{param}/{type}")
    @ResponseBody
    public Object checkRegister(@PathVariable String param, @PathVariable String type, String callback) {
        EgoResult er = tbUserServiceImpl.checkRegister(param, type);
        if (callback != null && !callback.equals("")) {
            MappingJacksonValue mjv = new MappingJacksonValue(er);
            mjv.setJsonpFunction(callback);
            return mjv;
        }

        return er;
    }

    /**
     * 注册用户
     *
     * @param tbUser
     * @return
     */
    @RequestMapping("/user/register")
    @ResponseBody
    public EgoResult register(TbUser tbUser) {
        return tbUserServiceImpl.register(tbUser);
    }
}

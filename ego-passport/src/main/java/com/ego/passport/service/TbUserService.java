package com.ego.passport.service;

import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther:pcb
 * @Date:19/6/10
 * @Description:com.ego.passport.service
 * @version:1.0
 */
public interface TbUserService {
    /**
     * 登录功能（使用MD5加密）
     * @param user
     * @return
     */
    EgoResult login(TbUser user, HttpServletRequest request, HttpServletResponse response);

    /**
     * 通过token查询用户信息
     * @param token
     * @return
     */
    EgoResult getUserInfoByToken(String token);

    /**
     * 退出登录
     * @param token
     * @return
     */
    EgoResult logout(String token,HttpServletRequest request,HttpServletResponse response);

    /**
     * 检查注册数据是否可用
     * @param param
     * @param type
     * @return
     */
    EgoResult checkRegister(String param,String type);

    /**
     * 用户注册（使用MD5加密）
     * @param tbUser
     * @return
     */
    EgoResult register(TbUser tbUser);
}

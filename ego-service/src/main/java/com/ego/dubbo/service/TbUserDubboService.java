package com.ego.dubbo.service;

import com.ego.pojo.TbUser;

/**
 * @Auther:pcb
 * @Date:19/6/10
 * @Description:com.ego.dubbo.service
 * @version:1.0
 */
public interface TbUserDubboService {
    /**
     * 根据用户名和密码查询登录
     * @param user
     * @return
     */
    TbUser selByUser(TbUser user);

    /**
     * 查询用户名是否已存在
     * @param username
     * @return
     */
    boolean selByUsername(String username);

    /**
     * 查询手机号是否已存在
     * @param phone
     * @return
     */
    boolean selByPhone(String phone);

    /**
     * 新增用户
     * @param tbUser
     * @return
     */
    int insUser(TbUser tbUser);
}

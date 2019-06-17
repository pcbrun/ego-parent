package com.ego.passport.service.impl;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbUserDubboService;
import com.ego.passport.service.TbUserService;
import com.ego.pojo.TbUser;
import com.ego.redis.dao.JedisDao;
import com.ego.redis.dao.impl.JedisDaoImpl;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

/**
 * @Auther:pcb
 * @Date:19/6/10
 * @Description:com.ego.passport.service.impl
 * @version:1.0
 */
@Service
public class TbUserServiceImpl implements TbUserService {
    @Reference
    private TbUserDubboService tbUserDubboServiceImpl;
    @Resource
    private JedisDao jedisDaoImpl;

    @Override
    public EgoResult login(TbUser user, HttpServletRequest request, HttpServletResponse response) {
        EgoResult er = new EgoResult();
        // 对用户登录传过来的哈希密码先进行MD5加密
        String pwd=user.getPassword();
        String pwdEncryption=convertMD5(pwd);
        user.setPassword(pwdEncryption);

        TbUser tbUser = tbUserDubboServiceImpl.selByUser(user);

        if (tbUser != null) {
            er.setStatus(200);
            // 用户登录成功之后将用户信息放入到redis中
            String key = UUID.randomUUID().toString();
            // 可以把密码清空
            tbUser.setPassword(null);
            jedisDaoImpl.set(key, JsonUtils.objectToJson(tbUser), 60 * 60 * 24 * 10);
            // 产生cookie，将redis的key放入到cookie中作为值
            CookieUtils.setCookie(request, response, "TT_TOKEN", key, 60 * 60 * 24 * 10);
        } else {
            er.setMsg("用户名或密码错误");
        }
        return er;
    }

    @Override
    public EgoResult getUserInfoByToken(String token) {
        EgoResult er = new EgoResult();
        String json = jedisDaoImpl.get(token);
        if (json != null && !json.equals("")) {
            TbUser tbUser = JsonUtils.jsonToPojo(json, TbUser.class);
            // 可以把密码清空
            tbUser.setPassword(null);
            er.setStatus(200);
            er.setData(tbUser);
            er.setMsg("ok");
        } else {
            er.setMsg("获取失败");
        }

        return er;
    }

    @Override
    public EgoResult logout(String token, HttpServletRequest request, HttpServletResponse response) {
        jedisDaoImpl.del(token);
        CookieUtils.deleteCookie(request, response, "TT_TOKEN");
        EgoResult er = new EgoResult();
        er.setStatus(200);
        er.setMsg("ok");
        return er;
    }

    @Override
    public EgoResult checkRegister(String param, String type) {
        EgoResult er = new EgoResult();
        er.setStatus(200);
        er.setMsg("ok");
        // 检查用户名是否被占用
        if (type.equals("1")) {
            if (tbUserDubboServiceImpl.selByUsername(param)) {
                er.setData(false);
            } else {
                er.setData(true);
            }
        }
        // 检查手机号是否被占用
        if (type.equals("2")) {
            if (tbUserDubboServiceImpl.selByPhone(param)) {
                er.setData(false);
            } else {
                er.setData(true);
            }
        }

        return er;
    }

    @Override
    public EgoResult register(TbUser tbUser) {
        EgoResult er = new EgoResult();
        //进行MD5加密，页面传过来的不是明文，是一个哈希值，对哈希再加密
        String pwd = tbUser.getPassword();
        String pwdEncryption = convertMD5(pwd);
        tbUser.setPassword(pwdEncryption);

        Date date = new Date();
        tbUser.setCreated(date);
        tbUser.setUpdated(date);

        if (tbUserDubboServiceImpl.insUser(tbUser) == 1) {
            er.setStatus(200);
        }

        return er;
    }

    /**
     * 加密解密算法 执行一次加密，两次解密
     *
     * @param inStr
     * @return
     */
    public static String convertMD5(String inStr) {

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;
    }
}

package com.ego.cart.interceptor;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.JsonUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther:pcb
 * @Date:19/6/11
 * @Description:com.ego.cart.interceptor
 * @version:1.0
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String token = CookieUtils.getCookieValue(httpServletRequest, "TT_TOKEN");
        // 判断用户是否已登录
        if(token!=null&&!token.equals("")){
            String json = HttpClientUtil.doPost("http://localhost:8084/user/token/" + token);
            EgoResult er = JsonUtils.jsonToPojo(json, EgoResult.class);
            if(er.getStatus()==200){
                return true;
            }
        }

        String num= (String) httpServletRequest.getParameter("num");
        if(num!=null&&!num.equals("")) {
            // url中%3F转义为字符？
            httpServletResponse.sendRedirect("http://localhost:8084/user/showLogin?interurl=" + httpServletRequest.getRequestURL() + "%3Fnum=" + num);
        }else{
            httpServletResponse.sendRedirect("http://localhost:8084/user/showLogin?interurl=" + httpServletRequest.getRequestURL());
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

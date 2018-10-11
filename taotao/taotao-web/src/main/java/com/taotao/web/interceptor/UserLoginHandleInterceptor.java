package com.taotao.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.commons.util.CookieUtil;
import com.taotao.sso.query.bean.User;
/*import com.taotao.web.bean.User;*/
import com.taotao.web.service.UserService;
import com.taotao.web.threadlocal.UserThreadLocal;


public class UserLoginHandleInterceptor implements HandlerInterceptor{
    @Autowired
    private UserService userService;
    
    public static final String  COOKIE_NAME = "TT_TOKEN";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
       
        String loginUrl=this.userService.TAOTAO_SSO_URL+"/user/login.html";
        String token=CookieUtil.getCookieValue(request, COOKIE_NAME);
        if(StringUtils.isEmpty(token)){
            //未登录，跳转到登录页面
            response.sendRedirect(loginUrl);
            return false;
        }
        User user=this.userService.queryByToken(token);
        if(null == user){
            response.sendRedirect(loginUrl);
        }
        //登录成功
        UserThreadLocal.set(user);//将user对象绑定到当前线程中 
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) throws Exception {
        // 视图渲染完成后执行
        UserThreadLocal.set(null);//清空本地线程中的user对象线程
        
    }
    
    
}

package com.ljs.miaosha.config;

import com.ljs.miaosha.access.UserContext;
import com.ljs.miaosha.domain.MiaoshaUser;
import com.ljs.miaosha.service.MiaoshaUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//将UserArgumentResolver注册到config里面去
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired                    //既然能注入service，那么可以用来容器来管理，将其放在容器中
    MiaoshaUserService miaoshaUserService;

    /**
     * 根据情况返回对象——返回的对象将被赋值到Controller的方法的参数中
     *
     * @param arg0
     * @param arg1
     * @param webRequest
     * @param arg3
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter arg0, ModelAndViewContainer arg1, NativeWebRequest webRequest,
                                  WebDataBinderFactory arg3) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        String paramToken = request.getParameter(MiaoshaUserService.COOKIE1_NAME_TOKEN);
        System.out.println("@UserArgumentResolver-resolveArgument  paramToken:" + paramToken);
        //获取cookie
        String cookieToken = getCookieValue(request, MiaoshaUserService.COOKIE1_NAME_TOKEN);
        System.out.println("@UserArgumentResolver-resolveArgument  cookieToken:" + cookieToken);
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return null;
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        //System.out.println("goods-token:"+token);
        //System.out.println("goods-cookieToken:"+cookieToken);
//        从缓存中获取token并返回
        MiaoshaUser user = miaoshaUserService.getByToken(token, response);
        System.out.println("@UserArgumentResolver--------user:" + user);

        //去取得已经保存的user，因为在用户登录的时候,user已经保存到threadLocal里面了，因为拦截器首先执行，然后才是取得参数
//		System.out.println("UserArgumentResolver信息： "+ UserContext.getUser());
        return user;
    }

    public String getCookieValue(HttpServletRequest request, String cookie1NameToken) {//COOKIE1_NAME_TOKEN-->"token"
        //遍历request里面所有的cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookie1NameToken)) {
                    System.out.println("getCookieValue:" + cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        System.out.println("No getCookieValue!");
        return null;
    }

    /**
     * 该方法用于判断Controller中方法参数中是否有符合条件的参数：
     * 有则进入下一个方法resolveArgument；
     * 没有则跳过不做处理
     *
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //返回参数的类型
        Class<?> clazz = parameter.getParameterType();
        return clazz == MiaoshaUser.class;
    }

}

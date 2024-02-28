package org.slaughter.filter;

import main.java.org.slaughter.utils.StringRedisOps;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2024年01月31日 17:47
 */
public class SSOFilter implements Filter {

    private StringRedisOps stringRedisOps;
    public static final String USER_INFO = "user";

    public SSOFilter(StringRedisOps stringRedisOps){
        this.stringRedisOps = stringRedisOps;
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        Object userInfo = request.getSession().getAttribute(USER_INFO);;

        //如果未登陆，则拒绝请求，转向登陆页面
        String requestUrl = request.getServletPath();
//        if (!"/toLogin".equals(requestUrl)//不是登陆页面
//                &amp;&amp; !requestUrl.startsWith("/login")//不是去登陆
//                &amp;&amp; null == userInfo) {//不是登陆状态
//
//            String ticket = request.getParameter("ticket");
//            //有票据,则使用票据去尝试拿取用户信息
//            if (null != ticket){
//                userInfo = redisTemplate.opsForValue().get(ticket);
//            }
//            //无法得到用户信息，则去登陆页面
//            if (null == userInfo){
//                response.sendRedirect("http://127.0.0.1:8080/toLogin?url="+request.getRequestURL().toString());
//                return ;
//            }
//
//            /**
//             * 将用户信息，加载进session中
//             */
//            org.slaughter.entity.SysUserEntity user = (org.slaughter.entity.) userInfo;
//            request.getSession().setAttribute(SSOFilter.USER_INFO,user);
//            redisTemplate.delete(ticket);
//        }

        filterChain.doFilter(request,servletResponse);
    }

    @Override
    public void destroy() {

    }

}

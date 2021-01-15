package com.stalltea.springfilter.filter;

import javax.servlet.*;
import java.io.IOException;

//@Order(1)  //方法1
//@WebFilter(urlPatterns = "/*",filterName = "customFilter")  //方法1

//@Order(1) //方法2
//@Component //方法2
public class CustomFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        System.out.println("进入拦截器！！！");
        chain.doFilter(request, response);
    }
}

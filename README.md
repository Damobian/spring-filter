#Springboot 过滤器的三种实现方式

###首先需要一个拦截器，如下
```java
package com.stalltea.springfilter.filter;

import javax.servlet.*;
import java.io.IOException;

public class CustomFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        System.out.println("进入拦截器！！！");
        chain.doFilter(request, response);
    }
}
```

### 方法一
使用@WebFilter、@ServletComponentScan、@Order 注解。@WebFilter是 Servlet3.0 中的注解，SpringBoot 能够支持该注解。通过@ServletComponentScan注解把过滤器扫描注册到servlet容器中。

示例如下：
```java
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@Order(1)  //方法1
@WebFilter(urlPatterns = "/*",filterName = "customFilter")  //方法1
public class CustomFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        System.out.println("进入拦截器！！！");
        chain.doFilter(request, response);
    }
}
```
```java
package com.stalltea.springfilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan //方法1
public class SpringFilterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringFilterApplication.class, args);
    }

}
```
###方法二
使用@Compent、@Order注解
示例如下：
```java
package com.stalltea.springfilter.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Order(1)
@Component
public class CustomFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        System.out.println("进入拦截器！！！");
        chain.doFilter(request, response);
    }
}
```

##方法三
配置FilterRegistrationBean对象,只需定义一个过滤类，然后通过@Bean注解和FilterRegistrationBean对象把过滤器注册到容器中
示例如下：
```java
package com.stalltea.springfilter.config;

import com.stalltea.springfilter.filter.CustomFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<CustomFilter> filterFilterRegistrationBean() {
        FilterRegistrationBean<CustomFilter> customFilter = new FilterRegistrationBean<>();
        customFilter.setFilter(new CustomFilter());
        customFilter.addUrlPatterns("/*");
        customFilter.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return customFilter;
    }

}

```

@Order：表示执行顺序的优先级，值越小优先级越高

@WebFilter：将类声明为过滤器。filterName属性表示过滤器名称。urlPatterns属性表示过滤的url,支持通配符

PS: 参考以下网页
https://cloud.tencent.com/developer/article/1362809

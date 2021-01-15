package com.stalltea.springfilter.config;

import com.stalltea.springfilter.filter.CustomFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class WebConfig {

//    @Bean //方法三
    public FilterRegistrationBean<CustomFilter> filterFilterRegistrationBean() {
        FilterRegistrationBean<CustomFilter> customFilter = new FilterRegistrationBean<>();
        customFilter.setFilter(new CustomFilter());
        customFilter.addUrlPatterns("/*");
        customFilter.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return customFilter;
    }

}

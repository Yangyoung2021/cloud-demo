package com.yang.config;


import feign.Logger;
import org.springframework.context.annotation.Bean;



public class DefaultFeignClientConfiguration {

    @Bean
    public Logger.Level feignLogLevel() {
        return Logger.Level.FULL;
    }
}

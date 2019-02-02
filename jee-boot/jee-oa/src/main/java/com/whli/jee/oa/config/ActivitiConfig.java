package com.whli.jee.oa.config;

import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Activiti 扩展配置
 * @author qiaolin
 * @version 2018/10/22
 **/

@Configuration
public class ActivitiConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public ProcessEngineConfigurationConfigurer processEngineConfigurationConfigurer(){

        ProcessEngineConfigurationConfigurer configurer = new ProcessEngineConfigurationConfigurer() {
            @Override
            public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
                processEngineConfiguration.setActivityFontName("宋体");
                processEngineConfiguration.setLabelFontName("宋体");
                processEngineConfiguration.setAnnotationFontName("宋体");
                processEngineConfiguration.setIdGenerator(new UuidGenerator());
            }
        };

        return configurer;
    }
}

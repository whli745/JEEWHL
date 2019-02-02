package com.whli.jee.job.config;

import com.whli.jee.core.config.DruidConfig;
import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDataSourceConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * <p>定时任务配置类<p>
 * @author whli
 * @version 2018/12/24 10:10
 */
@Configuration
@AutoConfigureAfter(value = {DataSourceAutoConfiguration.class})
public class QuartzConfiguration {

    @Autowired
    private MyJobFactory jobFactory;

    @Autowired
    private DataSource dataSource;

    @Bean
    public SchedulerFactoryBean schedulerFactory() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        //将spring管理job自定义工厂交由调度器维护
        factory.setJobFactory(jobFactory);
        //设置覆盖已存在的任务
        factory.setOverwriteExistingJobs(true);
        //设置调度器自动运行
        factory.setAutoStartup(true);
        //设置数据源
        factory.setDataSource(dataSource);
        // 延时启动
        factory.setStartupDelay(60);
        //设置上下文spring bean name
        factory.setApplicationContextSchedulerContextKey("applicationContext");
        //设置配置文件位置
        factory.setQuartzProperties(quartzProperties());
        return factory;
    }

    @Bean(value = "scheduler")
    public Scheduler scheduler() throws IOException {
        return schedulerFactory().getScheduler();
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        //设置参数
        Properties properties = new Properties();
        properties.setProperty("org.quartz.scheduler.instanceName","myQuartzScheduler");
        properties.setProperty("org.quartz.scheduler.instanceId","AUTO");
        properties.setProperty("org.quartz.scheduler.rmi.export","false");
        properties.setProperty("org.quartz.scheduler.rmi.proxy","false");
        properties.setProperty("org.quartz.scheduler.wrapJobExecutionInUserTransaction","false");
        properties.setProperty("org.quartz.threadPool.class","org.quartz.simpl.SimpleThreadPool");
        properties.setProperty("org.quartz.threadPool.threadCount","10");
        properties.setProperty("org.quartz.threadPool.threadPriority","5");
        properties.setProperty("org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread","true");
        properties.setProperty("org.quartz.jobStore.misfireThreshold","60000");
        properties.setProperty("org.quartz.jobStore.class","org.quartz.impl.jdbcjobstore.JobStoreTX");
        properties.setProperty("org.quartz.jobStore.tablePrefix","QRTZ_");
        properties.setProperty("org.quartz.jobStore.clusterCheckinInterval","15000");
        properties.setProperty("org.quartz.jobStore.isClustered","true");
        //定义参数
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setProperties(properties);
        //在quartz.properties中的属性被读取并注入后再初始化对象
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    /*
     * quartz初始化监听器
     */
    @Bean
    public QuartzInitializerListener executorListener() {
        return new QuartzInitializerListener();
    }
}

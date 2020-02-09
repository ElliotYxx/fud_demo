//package com.centerm.fud_demo.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.AsyncConfigurer;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//
//import java.util.concurrent.Executor;
//import java.util.concurrent.ThreadPoolExecutor;
//
///**
// * @author Sheva
// * @version 1.0
// * @date 2020/2/7 下午12:55
// */
//@Configuration
//@EnableAsync
//@Slf4j
//public class AsyncExecutorConfig{
//
//    @Value("${async.executor.thread.core_pool_size}")
//    private int corePoolSize;
//    @Value("${async.executor.thread_max_pool_size}")
//    private int maxPoolSize;
//    @Value("${async.executor.thread_queue_capacity}")
//    private int queueCapacity;
//    @Value("${async.executor.thread.name.prefix}")
//    private String namePrefix;
//
//    @Bean(name = "asyncExecutor")
//    public Executor getAsyncExecutor() {
//        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//        taskExecutor.setCorePoolSize(corePoolSize);
//        taskExecutor.setMaxPoolSize(maxPoolSize);
//        taskExecutor.setQueueCapacity(queueCapacity);
//        taskExecutor.setThreadNamePrefix(namePrefix);
//        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        taskExecutor.initialize();
//        return taskExecutor;
//    }
//
//}

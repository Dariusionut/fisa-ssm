package ro.fisa.ssm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created at 3/9/2024 by Darius
 **/

@Configuration
@EnableAsync
@EnableScheduling
public class AppConfig implements WebMvcConfigurer {
    @Bean
    @Primary
    public AsyncTaskExecutor taskExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setQueueCapacity(50);
        executor.setMaxPoolSize(10);
        executor.setCorePoolSize(2);
        executor.setThreadNamePrefix("FisaSSMTaskExecutor-");
        executor.setThreadNamePrefix("AsyncThread-");
        executor.setTaskDecorator(runnable -> {
            SecurityContext context = SecurityContextHolder.getContext();
            final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            return () -> {
                try {
                    SecurityContextHolder.setContext(context);
                    RequestContextHolder.setRequestAttributes(requestAttributes);
                    runnable.run();
                } finally {
                    SecurityContextHolder.clearContext();
                    RequestContextHolder.resetRequestAttributes();
                }
            };
        });
        executor.setRejectedExecutionHandler(this.callerRunsPolicy());
        executor.initialize();

        return new DelegatingSecurityContextAsyncTaskExecutor(executor);
    }

    @Bean
    public ThreadPoolExecutor.CallerRunsPolicy callerRunsPolicy() {
        return new ThreadPoolExecutor.CallerRunsPolicy();
    }

    @Configuration
    static class WebConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry
                    .addResourceHandler("/static/**")
                    .addResourceLocations("classpath:/static/");
        }
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setTaskExecutor(this.taskExecutor());
        configurer.setDefaultTimeout(30_000);
    }

}

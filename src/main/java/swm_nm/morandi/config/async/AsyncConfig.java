package swm_nm.morandi.config.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);  // 기본 스레드 풀 크기
        executor.setMaxPoolSize(10);  // 최대 스레드 풀 크기
        executor.setQueueCapacity(25); // 큐의 용량
        executor.setThreadNamePrefix("Async-");
        executor.initialize();
        return executor;
    }
}

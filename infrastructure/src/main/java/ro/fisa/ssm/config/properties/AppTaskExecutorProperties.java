package ro.fisa.ssm.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created at 4/9/2024 by Darius
 **/
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.task.execution")
public class AppTaskExecutorProperties {

    private String threadNamePrefix;

    @Getter
    @Setter
    @Component
    @ConfigurationProperties(prefix = "spring.task.execution.pool")
    public static class PoolProperties {
        private int queueCapacity;
        private int maxSize;
        private int coreSize;
        private boolean allowCoreThreadTimeout;
        private String keepAlive;

        public int getKeepAlive() {
            if (this.keepAlive.contains("s")) {
                final int sIndex = this.keepAlive.indexOf("s");
                final String keepAliveStr = this.keepAlive.substring(0, sIndex);
                return Integer.parseInt(keepAliveStr);
            } else {
                return Integer.parseInt(this.keepAlive);
            }
        }
    }
}

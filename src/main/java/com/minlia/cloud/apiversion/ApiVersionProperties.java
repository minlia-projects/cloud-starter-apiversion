package com.minlia.cloud.apiversion;

import com.minlia.cloud.apiversion.annotation.ApiVersionCondition;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author suimi
 * @author will
 */
@Configuration
@ConfigurationProperties(prefix = "system.api-version")
@Data
public class ApiVersionProperties {
    
    private String version = ApiVersionCondition.MAX_VERSION;

    private String paramName = "api-version";
}

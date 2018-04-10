package com.minlia.cloud.apiversion;

import com.minlia.cloud.apiversion.annotation.ApiVersion;
import com.minlia.cloud.apiversion.annotation.ApiVersionCondition;
import java.lang.reflect.Method;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author will
 */
public class ApiVersionRequestHandlerMapping extends RequestMappingHandlerMapping {

    private ApiVersionProperties versionProperties;

    public ApiVersionRequestHandlerMapping(ApiVersionProperties versionProperties) {
        this.versionProperties = versionProperties;
    }


    @Override
    protected RequestCondition<ApiVersionCondition> getCustomTypeCondition(Class<?> handlerType) {
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
        //如果类没有定义api版本，则设置为应用版本
        String apiVersionValue = apiVersion == null ? versionProperties.getVersion() : apiVersion.value();
        return createCondition(versionProperties.getVersion(), apiVersionValue);
    }

    @Override
    protected RequestCondition<ApiVersionCondition> getCustomMethodCondition(Method method) {
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(method, ApiVersion.class);
        //如果没有定义api版本，则设定为null，意为未定义
        return createCondition(versionProperties.getVersion(), apiVersion == null ? null : apiVersion.value());
    }

    private RequestCondition<ApiVersionCondition> createCondition(String currentVersion, String apiVersion) {

        return ApiVersionCondition.builder().paramName(versionProperties.getParamName()).currentVersion(currentVersion)
                                  .apiVersion(apiVersion).build();
    }

}

/*
 * Copyright (c) 2013-2017, suimi
 */
package com.minlia.cloud.apiversion.annotation;

import javax.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

/**
 * @author suimi
 * @author will
 *
 */
@Getter
@ToString
@Builder
public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {
    public final static String MAX_VERSION = "99.99.99";

    private String paramName;

    /**
     * 当前版本
     */
    private String currentVersion;

    /**
     * api 版本
     */
    private String apiVersion;

    // 采用最后定义优先原则,则方法上的定义覆盖类上面的定义
    @Override
    public ApiVersionCondition combine(ApiVersionCondition other) {
        return ApiVersionCondition.builder().paramName(paramName).currentVersion(currentVersion).apiVersion(
                StringUtils.hasLength(other.apiVersion) ? other.apiVersion : currentVersion).build();
    }

    @Override
    public ApiVersionCondition getMatchingCondition(HttpServletRequest request) {
        String apiVersion = request.getHeader(paramName);
        if (StringUtils.hasLength(apiVersion)) {
            // 如果请求的版本号大于配置版本号， 则满足
            if (compareVersion(apiVersion, this.apiVersion) >= 0 && compareVersion(apiVersion,
                    this.currentVersion) <= 0) {
                return this;
            }
        } else {
            //如果没有版本信息，则小于当前版本的都满足所有
            if (compareVersion(this.apiVersion, this.currentVersion) <= 0) {
                return this;
            }
        }
        return null;
    }

    @Override
    public int compareTo(ApiVersionCondition other, HttpServletRequest request) {
        // 优先匹配最新的版本号
        return compareVersion(other.getApiVersion(), this.apiVersion);
    }

    public int compareVersion(String version, String other) {
        if (!StringUtils.hasLength(version)) {
            if (!StringUtils.hasLength(other)) {
                return 0;
            } else {
                return -1;
            }
        } else if (!StringUtils.hasLength(other)) {
            return 1;
        }
        String[] splitVersion = version.split("\\.");
        String[] splitOther = other.split("\\.");
        int minLength = Math.min(splitOther.length, splitVersion.length);
        for (int i = 0; i < minLength; i++) {
            int result = splitVersion[i].compareTo(splitOther[i]);
            if ((result != 0)) {
                return result;
            }
        }
        return splitVersion.length - splitOther.length;
    }
}

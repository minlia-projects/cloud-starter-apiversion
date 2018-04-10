package com.minlia.cloud.apiversion;

import com.minlia.cloud.apiversion.annotation.ApiVersionCondition;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.AntPathMatcher;

/**
 * @author suimi
 * @author will
 */
@Slf4j
public class SimpleTest {

    @Test
    public void test() {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        assert pathMatcher.match("/{version:(v\\d+)*}/hello", "/v5/hello");
        assert pathMatcher.match("/{version:(v\\d+)*}/hello", "/v56/hello");
        assert !pathMatcher.match("/{version:(v\\d+)*}/hello", "/vd/hello");
//        assert pathMatcher.match("/(v\\d+)*/hello", "/v1/hello");
    }

    @Test
    public void versionCompare() {
        ApiVersionCondition condition = ApiVersionCondition.builder().build();
        assert condition.compareVersion("1.2.2.a", "1.2.2.b") < 0;
        assert condition.compareVersion("1.2.2", "1.2.1") > 0;
        assert condition.compareVersion("1.2.2", "1.2.2") == 0;
        assert condition.compareVersion("1.2.2", "1.2.3") < 0;
        assert condition.compareVersion("1.2.2", "1.2") > 0;
        assert condition.compareVersion("1.2.2", "1.2.2.1") < 0;
        assert condition.compareVersion("1.2.2", "1.2.2.snap") < 0;
        assert condition.compareVersion("1.2.2.a", "1.2.2.a") == 0;

    }


}

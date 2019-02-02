package com.whli.jee.core.anotation;

import java.lang.annotation.*;

/**
 * @Desc 无须验证登录注解
 * @Author whli
 * @Version 1.0
 * @Date 2018/5/29 16:20
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AuthorPermit {
}

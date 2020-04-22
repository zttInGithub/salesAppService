package com.hrtp.system.annotation;

import java.lang.annotation.*;

/**
 * FileCheck
 * description
 * create by lxj 2018/8/30
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FileCheck {
    String value() default "";
}
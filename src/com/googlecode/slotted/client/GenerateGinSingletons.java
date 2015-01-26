package com.googlecode.slotted.client;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface GenerateGinSingletons {
    String baseName() default "Singleton";
    String fullPackage();
    String[] scanPackages();
    String[] modules() default {};
}

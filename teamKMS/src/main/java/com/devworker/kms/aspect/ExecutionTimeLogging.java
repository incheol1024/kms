package com.devworker.kms.aspect;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface ExecutionTimeLogging {
}

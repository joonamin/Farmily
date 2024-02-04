package com.ssafy.farmily.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ssafy.farmily.entity.FamilyStatistics;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface IncrementStatistics {
	FamilyStatistics.Field value();

}

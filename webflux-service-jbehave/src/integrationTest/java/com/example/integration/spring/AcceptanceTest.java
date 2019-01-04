package com.example.integration.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = AcceptanceTestConfiguration.class)
@ActiveProfiles("tests")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AcceptanceTest {

}

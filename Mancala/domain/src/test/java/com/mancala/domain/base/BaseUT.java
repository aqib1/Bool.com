package com.mancala.domain.base;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BaseUT {
    @Test
    public void baseMethods() throws NoSuchMethodException, SecurityException {
        Class<?> c = Base.class;
        String methodName = "hashCode";
        Method method = c.getDeclaredMethod(methodName);
        assertNotNull(method);

        methodName = "equals";
        method = c.getDeclaredMethod(methodName, Object.class);
        assertNotNull(method);

        methodName = "toString";
        method = c.getDeclaredMethod(methodName);
        assertNotNull(method);
    }
}

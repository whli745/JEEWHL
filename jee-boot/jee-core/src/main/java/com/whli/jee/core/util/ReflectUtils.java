package com.whli.jee.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 反射工具类
 */
public class ReflectUtils
{
  public static final Logger logger = LoggerFactory.getLogger(ReflectUtils.class);

  public static Field[] getFields(Object target) {
    if (target == null) {
      return null;
    }
    Class clazz = target.getClass();
    Field[] fields = clazz.getDeclaredFields();
    return fields;
  }

  public static void setFieldValue(Object target, String name, Object value) {
    if ((target == null) || (name == null) || ("".equals(name)) || (value == null)) {
      return;
    }
    Class clazz = target.getClass();
    try {
      Method method = clazz.getDeclaredMethod("set" + Character.toUpperCase(name.charAt(0)) + name.substring(1), new Class[0]);
      if (!Modifier.isPublic(method.getModifiers())) {
        method.setAccessible(true);
      }
      method.invoke(target, new Object[] { value });
    } catch (Exception e) {
      if (logger.isDebugEnabled())
        logger.debug(e.toString());
      try
      {
        Field field = clazz.getDeclaredField(name);
        if (!Modifier.isPublic(field.getModifiers())) {
          field.setAccessible(true);
        }
        field.set(target, value);
      } catch (Exception fe) {
        if (logger.isDebugEnabled())
          logger.debug(fe.toString());
      }
    }
  }

  public static Object getFieldValue(Object target, String name)
  {
    if ((target == null) || (name == null) || (".".equals(name))) {
      return null;
    }
    Class clazz = target.getClass();
    try {
      String methodName = "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
      Method method = clazz.getDeclaredMethod(methodName, new Class[0]);
      if (!Modifier.isPublic(method.getModifiers())) {
        method.setAccessible(true);
      }
      return method.invoke(target, new Object[0]);
    } catch (Exception e) {
      try {
        Field field = null;
        while (field == null) {
          try {
            field = clazz.getDeclaredField(name);
          }
          catch (Exception localException1) {
          }
          if (field == null) {
            clazz = clazz.getSuperclass();
          }
        }
        if (!Modifier.isPublic(field.getModifiers())) {
          field.setAccessible(true);
        }
        return field.get(target);
      }
      catch (Exception localException2) {
      }
    }
    return null;
  }
}
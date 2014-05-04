package com.episode6.hackit.android.testing.reflection;

import com.google.common.collect.Lists;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Nullable;

public class ReflectionUtil {

  public static Object getValueOfField(Object owner, Field field) {
    try {
      field.setAccessible(true);
      return field.get(owner);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  public static void setValueOfField(Object owner, Field field, Object value) {
    try {
      field.setAccessible(true);
      field.set(owner, value);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  public static List<Field> getFieldsWithAnnotation(
      Class<?> fromClass,
      Class<? extends Annotation> annotationClass) {

    List<Field> foundFields = Lists.newArrayList();
    Field[] objectFields = fromClass.getDeclaredFields();
    for (int i = 0; i<objectFields.length; i++) {
      Field field = objectFields[i];
      if (field.isAnnotationPresent(annotationClass)) {
        foundFields.add(field);
      }
    }
    return foundFields;
  }

  public static @Nullable Class<? extends Annotation> getLeftoverAnnotation(
      Field fromField,
      List<Class<? extends Annotation>> excludeList) {

    Annotation[] annotations = fromField.getDeclaredAnnotations();
    if (annotations == null || annotations.length == 0) {
      return null;
    }

    try {
      return getLeftoverAnnotation(annotations, excludeList);
    } catch (Exception e) {
      throw new IllegalArgumentException("Too many annotations on field: " + fromField.toString());
    }
  }

  public static @Nullable Class<? extends Annotation> getLeftoverAnnotation(
      Annotation[] fromArray,
      List<Class<? extends Annotation>> excludeList) throws Exception {

    Class<? extends Annotation> foundAnnotation = null;
    for (int i = 0; i<fromArray.length; i++) {
      Class<? extends Annotation> annotationClass = fromArray[i].annotationType();
      if (excludeList.contains(annotationClass)) {
        continue;
      }

      if (foundAnnotation != null) {
        throw new Exception("Too many annotations in list!");
      }
      foundAnnotation = annotationClass;
    }
    return foundAnnotation;
  }

  public static @Nullable Constructor<?> getAnnotatedConstructor(
      Class<?> objectClass,
      Class<? extends Annotation> annotationClass) {

    Constructor<?> foundConstructor = null;
    Constructor<?>[] constructors = objectClass.getDeclaredConstructors();
    for (int i = 0; i<constructors.length; i++) {
      Constructor<?> constructorToCheck = constructors[i];
      if (!constructorToCheck.isAnnotationPresent(annotationClass)) {
        continue;
      }

      if (foundConstructor != null) {
        throw new IllegalArgumentException("Found more than one constructor annotated with " +
            annotationClass.getSimpleName() + " on class " + objectClass.getName());
      }
      foundConstructor = constructorToCheck;
    }

    return foundConstructor;
  }

  public static @Nullable Constructor<?> getEmptyConstructor(Class<?> objectClass) {
    Constructor<?>[] constructors = objectClass.getDeclaredConstructors();
    for (int i = 0; i<constructors.length; i++) {
      Constructor<?> constructorToCheck = constructors[i];
      if (constructorToCheck.getParameterTypes().length == 0) {
        return constructorToCheck;
      }
    }
    return null;
  }

  public static boolean isFieldGeneric(Field field) {
    return field.getGenericType() instanceof ParameterizedType;
  }

  public static Class<?> getGenericTypeArgument(Field field) {
    ParameterizedType type = (ParameterizedType) field.getGenericType();
    return (Class<?>)type.getActualTypeArguments()[0];
  }
}

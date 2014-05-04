package com.episode6.hackit.android.testing.inject;

import com.episode6.hackit.android.testing.reflection.ReflectionUtil;
import com.episode6.hackit.chop.Chop;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import org.mockito.Mock;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;

import dagger.Lazy;

public class BindKey {

  public enum FieldType {
    NORMAL,
    PROVIDER,
    LAZY,
    SET
  }

  public static final List<Class<? extends Annotation>> NON_BIND_ANNOTATIONS = ImmutableList.of(
      Mock.class,
      Inject.class);

  public static BindKey of(
      @Nullable Class<? extends Annotation> annotationClass,
      Class<?> objectClass,
      FieldType fieldType) {
    return new BindKey(annotationClass, objectClass, fieldType);
  }

  public static BindKey of(@Nullable Class<? extends Annotation> annotationClass, Class<?> objectClass) {
    return of(annotationClass, objectClass, FieldType.NORMAL);
  }

  public static BindKey of(Class<?> objectClass) {
    return of(null, objectClass);
  }

  public static BindKey of(Field field) {
    Chop.d("of Field: %s", field.toString());
    Class<?> objectClass;
    FieldType fieldType;
    Class<? extends Annotation> annotationClass = ReflectionUtil.getLeftoverAnnotation(
        field,
        NON_BIND_ANNOTATIONS);

    if (ReflectionUtil.isFieldGeneric(field)) {
      Class<?> genericType = field.getType();
      objectClass = ReflectionUtil.getGenericTypeArgument(field);
      Chop.d("Found Generic Field outer: %s, inner: %s", genericType.getSimpleName(), objectClass.getSimpleName());
      if (genericType.equals(Provider.class)) {
        fieldType = FieldType.PROVIDER;
      } else if (genericType.equals(Lazy.class)) {
        fieldType = FieldType.LAZY;
      } else if (genericType.equals(Set.class)) {
        fieldType = FieldType.SET;
      } else {
        throw new IllegalArgumentException("Could not create BindKey of Generic Field: " + field.toString() +
            " of type: " + objectClass.getSimpleName());
      }
    } else {
      objectClass = field.getType();
      fieldType = FieldType.NORMAL;
    }
    return of(annotationClass, objectClass, fieldType);
  }

  public static List<BindKey> constructorParams(Constructor<?> constructor) throws Exception {
    List<BindKey> paramKeyList = Lists.newArrayList();
    Class<?>[] paramTypes = constructor.getParameterTypes();
    Annotation[][] paramAnnotations = constructor.getParameterAnnotations();

    for (int i = 0; i<paramTypes.length; i++) {
      try {
        Class<? extends Annotation> annotationClass =
            ReflectionUtil.getLeftoverAnnotation(paramAnnotations[i], NON_BIND_ANNOTATIONS);
        paramKeyList.add(BindKey.of(annotationClass, paramTypes[i]));
      } catch (Exception e) {
        throw new Exception("Too many annotations on constructor param" + paramTypes[i].getSimpleName());
      }
    }
    return paramKeyList;
  }

  public final Class<?> objectClass;
  public final @Nullable
  Class<? extends Annotation> annotationClass;
  public final FieldType fieldType;

  BindKey(
      @Nullable Class<? extends Annotation> annotationClass,
      Class<?> objectClass,
      FieldType fieldType) {
    this.objectClass = objectClass;
    this.annotationClass = annotationClass;
    this.fieldType = fieldType;

    if (this.objectClass.equals(Provider.class) || this.objectClass.equals(Lazy.class)) {
      throw new IllegalArgumentException("Cannot create BindKey of type Provider or Lazy");
    }
  }

  public boolean isOfType(Class<?> classTypeToCheck) {
    return objectClass.equals(classTypeToCheck);
  }

  public boolean isProvider() {
    return fieldType.equals(FieldType.PROVIDER);
  }

  public boolean isLazy() {
    return fieldType.equals(FieldType.LAZY);
  }

  public boolean isNormal() {
    return fieldType.equals(FieldType.NORMAL);
  }

  public boolean isSet() {
    return fieldType.equals(FieldType.SET);
  }

  public BindKey normalVersion() {
    if (isNormal()) {
      return this;
    }
    return of(annotationClass, objectClass);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || !(o instanceof BindKey)) {
      return false;
    }

    BindKey otherKey = (BindKey)o;
    return Objects.equal(this.objectClass, otherKey.objectClass) &&
        Objects.equal(this.annotationClass, otherKey.annotationClass) &&
        Objects.equal(this.fieldType, otherKey.fieldType);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(
        8443,
        6863,
        this.objectClass,
        this.annotationClass,
        this.fieldType);
  }

  @Override
  public String toString() {
    if (annotationClass != null) {
      return String.format("@%s()/%s/%s", annotationClass.getName(), objectClass.getName(), fieldType);
    }
    return String.format("%s/%s", objectClass.getName(), fieldType);
  }
}

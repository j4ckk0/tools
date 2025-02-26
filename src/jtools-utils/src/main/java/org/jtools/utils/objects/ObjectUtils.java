package org.jtools.utils.objects;

/*-
 * #%L
 * Java Tools - Utils
 * %%
 * Copyright (C) 2024 jtools.org
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.jtools.utils.CommonUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class ObjectUtils.
 */
public class ObjectUtils {
	
	/**
	 * Instantiates a new object utils.
	 */
	private ObjectUtils() {}
	
	/**
	 * Clone.
	 *
	 * @param object the object
	 * @return the object
	 */
	public static Object clone(Serializable object) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			Logger.getLogger(ObjectUtils.class.getName()).log(Level.SEVERE, e.getMessage());
			return null;
		}
	}
	
	/**
	 * Apply.
	 *
	 * @param <E> the element type
	 * @param source the source
	 * @param target the target
	 */
	public static <E extends Object> void apply(E source, E target) {
		if (source == null) {
			Logger.getLogger(ObjectUtils.class.getName()).log(Level.SEVERE, "Source is null");
			return;
		}

		if (target == null) {
			Logger.getLogger(ObjectUtils.class.getName()).log(Level.SEVERE, "Target is null");
			return;
		}

		Class<? extends Object> objectClass = source.getClass();
		for (Field field : getFields(objectClass)) {
			try {
				Method getter = findGetter(objectClass, field);
				if (getter != null) {
					Object value = getter.invoke(source);

					Method setter = findSetter(objectClass, field);
					if (setter != null) {
						setter.invoke(target, value);
					} else {
						Logger.getLogger(ObjectUtils.class.getName()).log(Level.FINE,
								"setter not found for field " + field.getName());
					}
				} else {
					Logger.getLogger(ObjectUtils.class.getName()).log(Level.FINE,
							"getter not found for field " + field.getName());
				}
			} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
				Logger.getLogger(ObjectUtils.class.getName()).log(Level.SEVERE, e.getMessage());
			}
		}
	}
	
	/**
	 * Find setter.
	 *
	 * @param objectClass the object class
	 * @param field the field
	 * @return the method
	 */
	public static Method findSetter(Class<?> objectClass, Field field) {
		PropertyDescriptor[] propertyDescriptors;
		try {
			propertyDescriptors = Introspector.getBeanInfo(objectClass).getPropertyDescriptors();
			for (PropertyDescriptor pd : propertyDescriptors) {
				if (pd.getWriteMethod() != null && !"class".equals(pd.getName())) {
					if (pd.getWriteMethod().getName().compareToIgnoreCase("set" + field.getName()) == 0) {
						return pd.getWriteMethod();
					}
				}
			}
		} catch (IntrospectionException e) {
			Logger.getLogger(ObjectUtils.class.getName()).log(Level.SEVERE, e.getMessage(), e);
		}

		// Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Setter not found
		// for field " + field.getName());
		return null;
	}

	/**
	 * Find getter.
	 *
	 * @param objectClass the object class
	 * @param field the field
	 * @return the method
	 */
	public static Method findGetter(Class<?> objectClass, Field field) {
		PropertyDescriptor[] propertyDescriptors;
		try {
			propertyDescriptors = Introspector.getBeanInfo(objectClass).getPropertyDescriptors();
			for (PropertyDescriptor pd : propertyDescriptors) {
				if (pd.getReadMethod() != null && !"class".equals(pd.getName())) {
					if (pd.getReadMethod().getName().compareToIgnoreCase("get" + field.getName()) == 0) {
						return pd.getReadMethod();
					}
				}
			}
		} catch (IntrospectionException e) {
			Logger.getLogger(ObjectUtils.class.getName()).log(Level.SEVERE, e.getMessage(), e);
		}

		// Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Getter not found
		// for field " + field.getName());
		return null;
	}

	/**
	 * Gets the fields.
	 *
	 * @param objectClass the object class
	 * @return the fields
	 */
	public static List<Field> getFields(Class<?> objectClass) {
		List<Field> fields = new ArrayList<>();

		List<Class<?>> classes = new ArrayList<>();
		while (objectClass != Object.class) {
			classes.add(objectClass);
			objectClass = objectClass.getSuperclass();
		}
		Collections.reverse(classes);

		for(Class<?> clazz : classes) {
			fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
		}

		return fields;
	}

	/**
	 * Gets the field.
	 *
	 * @param objectClass the object class
	 * @param fieldName the field name
	 * @return the field
	 */
	public static Field getField(Class<?> objectClass, String fieldName) {
		Field field = null;
		while(objectClass != Object.class) {
			try {
				field = objectClass.getDeclaredField(fieldName);
				if(field != null) {
					return field;
				}
			} catch (NoSuchFieldException | SecurityException e) {
				Logger.getLogger(CommonUtils.class.getName()).log(Level.FINEST, e.getMessage(), e);
			}

			objectClass = objectClass.getSuperclass();
		}

		return null;
	}

	/**
	 * Gets the generic type for field.
	 *
	 * @param objectField the object field
	 * @return the generic type for field
	 */
	public static Class<?> getGenericTypeForField(Field objectField) {
		Type genericType = objectField.getGenericType();
		if(genericType instanceof ParameterizedType) {
			ParameterizedType aType = (ParameterizedType) genericType;

			Type[] actualTypeArguments = aType.getActualTypeArguments();
			if(actualTypeArguments.length == 1) {
				Type fieldArgsType = actualTypeArguments[0];
				return (Class<?>) fieldArgsType;
			}
		}

		return null;
	}

	/**
	 * To string.
	 *
	 * @param list the list
	 * @param delimiter the delimiter
	 * @return the string
	 */
	public static String toString(Collection<?> list, CharSequence delimiter) {
		if(list == null) {
			return null;
		}

		return list.stream().map(Object::toString).collect(Collectors.joining(delimiter));
	}

	/**
	 * Gets the field.
	 *
	 * @param objectClassName the object class name
	 * @param fieldName the field name
	 * @return the field
	 */
	public static Field getField(String objectClassName, String fieldName) {
		try {
			Class<?> objectClass = Class.forName(objectClassName);
			return ObjectUtils.getField(objectClass, fieldName);
		} catch (Exception e) {
			Logger.getLogger(CommonUtils.class.getName()).log(Level.SEVERE, "An error occurred while getting field "
					+ objectClassName + "#" + fieldName + " - Cause: " + e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Gets the object class.
	 *
	 * @param objectClassName the object class name
	 * @return the object class
	 */
	public static Class<?> getObjectClass(String objectClassName) {
		try {
			return Class.forName(objectClassName);
		} catch (Exception e) {
			Logger.getLogger(CommonUtils.class.getName()).log(Level.SEVERE,
					"An error occurred while getting class " + objectClassName + " - Cause: " + e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Instanciate object.
	 *
	 * @param objectClassName the object class name
	 * @return the object
	 */
	public static Object instanciateObject(String objectClassName) {
		try {
			Class<?> objectClass = Class.forName(objectClassName);
			Constructor<?> objectConstructor = objectClass.getConstructor();
			return objectConstructor.newInstance();
		} catch (Exception e) {
			Logger.getLogger(CommonUtils.class.getName()).log(Level.SEVERE, "An error occurred while instanciating the object "
					+ objectClassName + " - Cause: " + e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
}

package com.se7en.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

/**
 * 增强 BeanUtils工具类
 * 
 * @author dl
 */
public final class BeanUtil extends org.apache.commons.beanutils.BeanUtils {
	
	private BeanUtil() {
		throw new UnsupportedOperationException("工具类不能被实例化");
	}

	/**
	 * cglib 快速复制,不带转换器 只复制名称相同且类型相同的属性
	 * 
	 * @param source
	 * @param target
	 */
	public static void fastCopyProperties(final Object source,final Object target) {
		CglibEnhancer.fastCopyProperties(source, target);
	}

	/**
	 * cglib 快速复制到不同对象数组,不带转换器 只复制名称相同且类型相同的属性
	 * 
	 * @param source
	 */
	public static void fastCopyPropertiesDif(final Object source,final Object[] targets) {
		CglibEnhancer.fastCopyPropertiesDif(source, targets);
	}

	/**
	 * cglib 快速复制到相同同对象数组,不带转换器 只复制名称相同且类型相同的属性
	 * 
	 * @param source
	 */
	public static <T extends Object> void fastCopyProperties(final Object source,
			final T[] targets) {
		CglibEnhancer.fastCopyProperties(source, targets);
	}

	/**
	 * cglib 快速复制到不同对象数组，带类型转换 只复制名称相同且类型相同的属性
	 * 
	 * @param source
	 */
	public static void fastCopyPropertiesDif(final Object source,final Object[] targets,
			Converter cov) {
		CglibEnhancer.fastCopyPropertiesDif(source, targets, cov);
	}

	/**
	 * cglib 快速复制到相同对象数组，带类型转换 只复制名称相同且类型相同的属性.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param source
	 *            the source
	 * @param targets
	 *            the targets
	 * @param cov
	 *            the cov
	 */
	public static <T extends Object> void fastCopyProperties(final Object source,
			final T[] targets,final Converter cov) {
		CglibEnhancer.fastCopyProperties(source, targets, cov);
	}

	/**
	 * 复制复制源Bean对象不为空的属性
	 * 
	 * @param source
	 * @param target
	 * @throws BeansException
	 */
	public static void copyNotNullProperties(final Object source,final Object target)
			throws BeansException {
		SpringEnhancer.copyNotNullProperties(source, target);
	}

	public static void copyNotNull2NullProperties(final Object source,
			final Object target) {
		SpringEnhancer.copyNotNull2NullProperties(source, target);
	}

	/**
	 * Gets the field type. 获取字段类型
	 * 
	 * @param cls
	 *            the cls
	 * @param fldName
	 *            the fld name
	 * @return the field type
	 */
	public static Class<?> getFieldType(final Class<?> cls,final String fldName) {
		final Field fld = ReflectionUtils.findField(cls, fldName);
		return fld.getType();
	}

	/**
	 * Cglib增强类
	 * 
	 * @author Administrator
	 */
	private static class CglibEnhancer {
		
		private CglibEnhancer(){
			throw new UnsupportedOperationException("工具类不能被实例化");
		}
		
		/**
		 * cglib 快速复制,不带转换器 只复制名称相同且类型相同的属性
		 * 
		 * @param source
		 * @param target
		 */
		public static void fastCopyProperties(final Object source,final Object target) {
			final BeanCopier bc = BeanCopier.create(source.getClass(),
					target.getClass(), false);
			bc.copy(source, target, null);
		}

		/**
		 * cglib 快速复制到不同对象数组,不带转换器 只复制名称相同且类型相同的属性
		 * 
		 * @param source
		 */
		public static void fastCopyPropertiesDif(Object source, Object[] targets) {
			for (Object target : targets) {
				BeanCopier bc = BeanCopier.create(source.getClass(),
						target.getClass(), false);
				bc.copy(source, target, null);
			}
		}

		/**
		 * cglib 快速复制到相同同对象数组,不带转换器 只复制名称相同且类型相同的属性
		 * 
		 * @param source
		 */
		public static <T extends Object> void fastCopyProperties(Object source,
				T[] targets) {
			BeanCopier bc = BeanCopier.create(source.getClass(),
					targets[0].getClass(), false);
			for (Object target : targets) {
				bc.copy(source, target, null);
			}
		}

		/**
		 * cglib 快速复制到不同对象数组，带类型转换 只复制名称相同且类型相同的属性
		 * 
		 * @param source
		 */
		public static void fastCopyPropertiesDif(Object source,
				Object[] targets, Converter cov) {
			for (Object target : targets) {
				BeanCopier bc = BeanCopier.create(source.getClass(),
						target.getClass(), true);
				bc.copy(source, target, cov);
			}
		}

		/**
		 * cglib 快速复制到相同对象数组，带类型转换 只复制名称相同且类型相同的属性
		 * 
		 * @param source
		 */
		public static <T extends Object> void fastCopyProperties(Object source,
				T[] targets, Converter cov) {
			for (Object target : targets) {
				BeanCopier bc = BeanCopier.create(source.getClass(),
						target.getClass(), true);
				bc.copy(source, target, cov);
			}
		}
	}

	/**
	 * Spring工具增强类
	 * 
	 * @author Administrator
	 */
	private static class SpringEnhancer extends
			org.springframework.beans.BeanUtils {

		/** The Constant NOTNULL_NOTNULL. 非空属性，进行覆盖 */
		private final static int NOTNULL = 1;

		/** The Constant NOTNULL_NULL. 复制非空属性到空属性 */
		private final static int NOTNULL_NULL = 2;

		/**
		 * 复制复制源Bean对象不为空的属性 覆盖目标对象属性值
		 * 
		 * @param source
		 * @param target
		 * @throws BeansException
		 */
		private static void copyNotNullProperties(Object source, Object target)
				throws BeansException {
			copyConsiderNullProperties(source, target, NOTNULL);
		}

		/**
		 * 复制复制源Bean对象不为空的属性 到目标对象值为空的属性
		 * 
		 * @param source
		 * @param target
		 * @throws BeansException
		 */
		private static void copyNotNull2NullProperties(Object source,
				Object target) throws BeansException {
			copyConsiderNullProperties(source, target, NOTNULL_NULL);
		}

		/**
		 * 复制考虑存在空值的属性的情况
		 * 
		 * @param source
		 * @param target
		 * @throws BeansException
		 */
		private static void copyConsiderNullProperties(Object source,
				Object target, int consider) throws BeansException {
			Assert.notNull(source, "Source must not be null");
			Assert.notNull(target, "Target must not be null");

			Class<?> actualEditable = target.getClass();
			PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);

			for (PropertyDescriptor targetPd : targetPds) {
				if (targetPd.getWriteMethod() != null) {
					PropertyDescriptor sourcePd = getPropertyDescriptor(
							source.getClass(), targetPd.getName());
					if (sourcePd != null && sourcePd.getReadMethod() != null) {
						try {
							Method readMethod = sourcePd.getReadMethod();
							if (!Modifier.isPublic(readMethod
									.getDeclaringClass().getModifiers())) {
								readMethod.setAccessible(true);
							}
							Object value = readMethod.invoke(source);
							if (value != null) { // 原对象属性值非空
								Method targetReadMethod = targetPd.getReadMethod();
								if (!Modifier.isPublic(targetReadMethod.getDeclaringClass().getModifiers())) {
									targetReadMethod.setAccessible(true);
								}
								Object targetValue = targetReadMethod.invoke(target);
								// 考虑目标对象属性为空情况
								if (targetValue != null) {
									if (consider == SpringEnhancer.NOTNULL_NULL)
										continue;
								}
								Method writeMethod = targetPd.getWriteMethod();
								if (!Modifier.isPublic(writeMethod
										.getDeclaringClass().getModifiers())) {
									writeMethod.setAccessible(true);
								}
								writeMethod.invoke(target, value);
							}
						} catch (Throwable ex) {
							throw new FatalBeanException(
									"Could not copy properties from source to target",
									ex);
						}
					}
				}
			}
		}

	}

}

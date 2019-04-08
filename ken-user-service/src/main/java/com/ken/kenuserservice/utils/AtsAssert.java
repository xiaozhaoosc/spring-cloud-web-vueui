package com.ken.kenuserservice.utils;

import com.ken.kenuserservice.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;


/**
 * 数据验证工具包
 * <hr>
 * 若参数不满足条件,则抛出异常
 * <hr>
 */
public final class AtsAssert {

	private AtsAssert() {
	}

	/**
	 * 若表达式为false,则抛出异常
	 * 
	 * @param expression
	 *            表达式
	 * @param message
	 *            异常message
	 * @throws BusinessException
	 *             new WmsException(message)
	 */
	public static void isTrue(boolean expression,String errorCode, String message)
			throws BusinessException {
		if (!expression) {
			throw new BusinessException(errorCode,message);
		}
	}
	
//	/**
//	 * @param expression
//	 * @param message
//	 * @param errorVles 错误信息
//	 */
//	public static void isTrue(boolean expression,String errorCode, String message, String[] errorVles)
//			throws BusinessException {
//		if (!expression) {
//			throw new BusinessException(errorCode,message,errorVles);
//		}
//	}

	/**
	 * 若参数不为null,则抛出异常<br>
	 * object != null
	 * 
	 * @param object
	 *            待验证的参数
	 * @param message
	 *            抛出异常的信息
	 * @throws BusinessException
	 *             new WmsException(message)
	 */
	public static void isNull(Object object,String errorCode, String message)
			throws BusinessException {
		if (object != null) {
			throw new BusinessException(errorCode,message);
		}
	}

	/**
	 * 若参数为null,则抛出异常<br>
	 * object != null
	 * 
	 * @param object
	 *            待验证的参数
	 * @param message
	 *            抛出异常的信息
	 * @throws BusinessException
	 *             new WmsException(message)
	 */
	public static void notNull(Object object,String errorCode, String message)
			throws BusinessException {
		if (object == null) {
			throw new BusinessException(errorCode,message);
		}
	}

//	public static void notNull(Object object, String message, String[] errorValues)
//			throws BusinessException {
//		if (object == null) {
//			throw new BusinessException(message,errorValues);
//		}
//	}

	/**
	 * 若参数不为null或空字符,则抛出异常<br>
	 * !StringUtils.isEmpty(text)
	 * 
	 * @param text
	 *            待验证的参数
	 * @param message
	 *            抛出异常的信息
	 * @throws BusinessException
	 *             new WmsException(message)
	 * @see org.apache.commons.lang3.StringUtils#isEmpty(CharSequence)
	 */
	public static void isEmpty(String text,String errorCode, String message) throws BusinessException {
		if (!StringUtils.isEmpty(text)) {
			throw new BusinessException(errorCode,message);
		}
	}

	/**
	 * 若参数为null或空字符,则抛出异常<br>
	 * StringUtils.isEmpty(text)
	 *
	 * @param text
	 *            待验证的参数
	 * @param message
	 *            抛出异常的信息
	 * @throws BusinessException
	 *             new WmsException(message)
	 * @see org.apache.commons.lang3.StringUtils#isEmpty(CharSequence)
	 */
	public static void notEmpty(String text,String errorCode, String message)
			throws BusinessException {
		if (StringUtils.isEmpty(text)) {
			throw new BusinessException(errorCode,message);
		}
	}

	/**
	 * 若参数不为null或空字符(清空两端空格),则抛出异常<br>
	 * StringUtils.isBlank(text)
	 *
	 * @param text
	 *            待验证的参数
	 * @param message
	 *            抛出异常的信息
	 * @throws BusinessException
	 *             new WmsException(message)
	 * @see org.apache.commons.lang3.StringUtils#isBlank(CharSequence)
	 */
	public static void isBlank(String text,String errorCode, String message) throws BusinessException {
		if (!StringUtils.isEmpty(text)) {
			throw new BusinessException(errorCode,message);
		}
	}

	/**
	 * 若参数为null或空字符(清空两端空格),则抛出异常<br>
	 * !StringUtils.isBlank(text)
	 *
	 * @param text
	 *            待验证的参数
	 * @param message
	 *            抛出异常的信息
	 * @throws BusinessException
	 *             new WmsException(message)
	 * @see org.apache.commons.lang3.StringUtils#isBlank(CharSequence)
	 */
	public static void notBlank(String text,String errorCode, String message)
			throws BusinessException {
		if (StringUtils.isEmpty(text)) {
			throw new BusinessException(errorCode,message);
		}
	}
	
//	/**
//	 * 
//	 * @param text
//	 * @param message
//	 * @param errorVles 错误值
//	 */
//	public static void notBlank(String text, String message, String[] errorVles)
//			throws BusinessException {
//		if (StringUtils.isEmpty(text)) {
//			throw new BusinessException(message,errorVles);
//		}
//	}

	/**
	 * 若集合参数为null或isEmpty,则抛出异常
	 * 
	 * @param collection
	 *            待验证的集合参数
	 * @param message
	 *            抛出异常的信息
	 * @throws BusinessException
	 *             new WmsException(message)
	 */
	public static void notEmpty(Collection<?> collection,String errorCode, String message)
			throws BusinessException {
		if (collection == null || collection.isEmpty()) {
			throw new BusinessException(errorCode,message);
		}
	}
	
//	/**
//	 * @param errorValues 异常value
//	 */
//	public static void notEmpty(Collection<?> collection, String message,String[] errorValues)
//			throws BusinessException {
//		if (collection == null || collection.isEmpty()) {
//			throw new BusinessException(message, errorValues);
//		}
//	}
	
	/**
	 * 断言集合为空
	 * @param collection 集合对象
	 * @param message 异常信息
	 * @throws BusinessException
	 */
	public static void empty(Collection<?> collection,String errorCode, String message)
			throws BusinessException {
		if (null != collection && !collection.isEmpty()) {
			throw new BusinessException(errorCode,message);
		}
	}

	/**
	 * 若数组参数为null或length为0,则抛出异常
	 * 
	 * @param array
	 *            待验证的数组参数
	 * @param message
	 *            抛出异常的信息
	 * @throws BusinessException
	 *             new WmsException(message)
	 */
	public static void notEmpty(Object[] array,String errorCode, String message)
			throws BusinessException {
		if (array == null || array.length==0) {
			throw new BusinessException(errorCode,message);
		}
	}

}
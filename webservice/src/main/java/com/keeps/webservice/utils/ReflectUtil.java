package com.keeps.webservice.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import org.apache.log4j.Logger;

import com.keeps.tools.exception.CapecException;
import com.keeps.tools.utils.SpringUtils;
import com.keeps.tools.utils.StringUtils;
import com.keeps.webservice.enums.ReflectEnum;


/** 
 * <p>Title: ReflectUtil.java</p>  
 * <p>Description: TODO</p>  
 * <p>Copyright: Copyright (c) 新开普电子股份有限公司 2016</p>  
 * @author keeps
 * @version
 * @date 创建日期：2016年9月6日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public class ReflectUtil {
	private static final Logger log = Logger.getLogger(ReflectUtil.class.getName());

	/**
	  * @Title:			getData 
	  * @Description:	反射接口，获取数据返回
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月16日
	 */
	public static ReturnData getData(Map<String, String> param){
		ReturnData adv = new ReturnData();
		try {
			if (param==null) {
				throw new CapecException("请求方法错误!");
			}
			String methodName = param.get("cmd");
			if (StringUtils.notText(methodName)) {
				throw new CapecException("找不到CMD方法!");
			}
			// 获取Service类对象
			Class c = ReflectEnum.getServiceName(methodName);
			if (c != null) {
				Object o = SpringUtils.getBean(c.getSimpleName().substring(0,1).toLowerCase()+c.getSimpleName().substring(1));
				//获取方法
				Method getDataMethod = c.getDeclaredMethod(methodName, Map.class);
				// 执行方法
				return (ReturnData) getDataMethod.invoke(o, param);
			}else{
				throw new CapecException("命令字不正确或者ReflectEnum类未加入映射Service类!");
			}
		}catch (SecurityException e) {
            e.printStackTrace();
            log.error("执行方法时发生异常!"+e);
			throw new CapecException("执行方法时发生异常!"+e);
        } catch (NoSuchMethodException e) {
        	e.printStackTrace();
            log.error("执行方法时发生异常!"+e);
			throw new CapecException("执行方法时发生异常!"+e);
        } catch (IllegalArgumentException e) {  
        	e.printStackTrace();
            log.error("执行方法时发生异常!"+e);
			throw new CapecException("执行方法时发生异常!"+e);
        } catch (IllegalAccessException e) { 
        	e.printStackTrace();
            log.error("执行方法时发生异常!"+e);
			throw new CapecException("执行方法时发生异常!"+e);
        } catch (InvocationTargetException e) {  
        	e.printStackTrace();
            log.error("被调用方法内部未被捕获的异常!"+e);
			throw new CapecException("被调用方法内部未被捕获的异常!"+e);
        } catch (CapecException e) {
        	e.printStackTrace();
            log.error(e.getMessage());
			throw new CapecException(e.getMessage());
        } 
	}
	
	/**
	  * @Title:			getFlowConditionPojoClass 
	  * @Description:	限制条件POJO类获取
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月16日
	 */
	public static Class getFlowConditionPojoClass(String methodName) {
		try {
			// 获取Service类对象
			Class c = ReflectEnum.getServiceName(methodName);
			if (c != null) {
				return getSuperClassGenricType(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	  * @Title:			getSuperClassGenricType 
	  * @Description:	通过反射,获得定义Class时声明的父类的范型参数的类型. 
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月16日
	 */
	public static Class getSuperClassGenricType(Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	  * @Title:			getSuperClassGenricType 
	  * @Description:	通过反射,获得定义Class时声明的父类的范型参数的类型. 
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月16日
	 */
	public static Class getSuperClassGenricType(Class clazz, int index)
			throws IndexOutOfBoundsException {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			return null;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			return null;
		}
		if (!(params[index] instanceof Class)) {
			return null;
		}
		return (Class) params[index];
	}
	
	/**
	  * @Title:			getMethod 
	  * @Description:	利用递归找一个类的指定方法，如果找不到，去父亲里面找直到最上层Object对象为止。 
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月16日
	 */
    public static Method getMethod(Class clazz, String methodName,  
            final Class[] classes) throws Exception {  
        Method method = null;  
        try {  
            method = clazz.getDeclaredMethod(methodName, classes);  
        } catch (NoSuchMethodException e) {  
            try {  
                method = clazz.getMethod(methodName, classes);  
            } catch (NoSuchMethodException ex) {  
                if (clazz.getSuperclass() == null) {  
                    return method;  
                } else {  
                    method = getMethod(clazz.getSuperclass(), methodName,  
                            classes);  
                }  
            }  
        }  
        return method;  
    }  

    /** 
     *  
     * @param obj 
     *            调整方法的对象 
     * @param methodName 
     *            方法名 
     * @param classes 
     *            参数类型数组 
     * @param objects 
     *            参数数组 
     * @return 方法的返回值 
     */  
    public static Object invoke(final Object obj, final String methodName,  
            final Class[] classes, final Object[] objects) {  
        try {  
            Method method = getMethod(obj.getClass(), methodName, classes);  
            method.setAccessible(true);// 调用private方法的关键一句话  
            return method.invoke(obj, objects);  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
    public static Object invoke(final Object obj, final String methodName,  
            final Class[] classes) {  
        return invoke(obj, methodName, classes, new Object[] {});  
    }  
  
    public static Object invoke(final Object obj, final String methodName) {  
        return invoke(obj, methodName, new Class[] {}, new Object[] {});  
    }  
  
}

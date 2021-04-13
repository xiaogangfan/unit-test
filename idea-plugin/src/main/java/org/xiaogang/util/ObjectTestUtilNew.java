package org.xiaogang.util;

import com.google.common.collect.Lists;
import net.sf.cglib.core.ReflectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ObjectTestUtilNew {

    public static final String enter = "\n";
    public static final String space8 = "        ";

    public static <T> T newObjectWithPropertiesValue(Class<T> clz, Boolean isRecu) {
        if (isPrimitive(clz) && !isRecu) {
            return (T)initPrimitiveValue(clz);
        }
        T obj = (T)ReflectUtils.newInstance(clz);
        Field[] fields = clz.getDeclaredFields();
        for (Field fieldtemp : fields) {
            fieldtemp.setAccessible(true);
            Class fieldClz = fieldtemp.getType();
            if (Modifier.isFinal(fieldClz.getModifiers())) {
                continue;
            }
            try {
                // Init basic type
                if (isPrimitive(fieldClz)) {
                    fieldtemp.set(obj, initPrimitiveValue(fieldClz));
                    continue;
                }

                // Init javabean
                if (!isJavaClass(fieldClz)) {
                    fieldtemp.set(obj, newObjectWithPropertiesValue(fieldClz, true));
                    continue;
                }

                Type genericType = fieldtemp.getGenericType();
                if (genericType == null) {
                    continue;
                }
                // 如果是泛型参数的类型
                if (genericType instanceof ParameterizedType
                    && fieldClz.equals(List.class)) {
                    ParameterizedType pt = (ParameterizedType)genericType;
                    //得到泛型里的class类型对象
                    Class<?> accountPrincipalApproveClazz = (Class<?>)pt.getActualTypeArguments()[0];
                    if (!isRecu) {
                        fieldtemp.set(obj,
                            Lists.newArrayList(newObjectWithPropertiesValue(accountPrincipalApproveClazz, true)));
                    }
                    continue;
                }

            } catch (Exception e) {
            }
        }
        return obj;
    }

    /**
     * 初始化java的基本类型&&日期等非自定义的javabean
     *
     * @param fieldClz
     * @return
     */
    private static Object initPrimitiveValue(Class fieldClz) {
        Integer num = RandomUtil.randomInt(10);
        if (Long.class.equals(fieldClz) || long.class.equals(fieldClz)) {
            return num.longValue();
        }
        if (Integer.class.equals(fieldClz) || int.class.equals(fieldClz)) {
            return num;
        }
        if (Short.class.equals(fieldClz) || short.class.equals(fieldClz)) {
            return num.shortValue();
        }
        if (Float.class.equals(fieldClz) || float.class.equals(fieldClz)) {
            return num.floatValue();
        }
        if (Double.class.equals(fieldClz) || double.class.equals(fieldClz)) {
            return num.doubleValue();
        }
        if (Character.class.equals(fieldClz) || char.class.equals(fieldClz)) {
            return RandomUtil.randomStr(1).toCharArray()[0];
        }
        if (Byte.class.equals(fieldClz) || byte.class.equals(fieldClz)) {
            return RandomUtil.randomStr(1).getBytes()[0];
        }
        if (Boolean.class.equals(fieldClz) || boolean.class.equals(fieldClz)) {
            return true;
        }
        if (String.class.equals(fieldClz)) {
            return RandomUtil.randomStr(8);
        }
        if (BigDecimal.class.equals(fieldClz)) {
            return new BigDecimal(num);
        }
        if (Enum.class.isAssignableFrom(fieldClz)) {
            Enum[] enums = (Enum[])fieldClz.getEnumConstants();
            int i = RandomUtil.randomInt(enums.length);
            return enums[i];
        }
        if (fieldClz.equals(Date.class)) {
            return new Date();
        }
        return null;
    }

    private static Object initPrimitiveValueProcess(Class fieldClz) {
        Integer num = RandomUtil.randomInt(10);
        if (Long.class.equals(fieldClz) || long.class.equals(fieldClz)) {
            return num.longValue();
        }
        if (Integer.class.equals(fieldClz) || int.class.equals(fieldClz)) {
            return num;
        }
        if (Short.class.equals(fieldClz) || short.class.equals(fieldClz)) {
            return num.shortValue();
        }
        if (Float.class.equals(fieldClz) || float.class.equals(fieldClz)) {
            return num.floatValue();
        }
        if (Double.class.equals(fieldClz) || double.class.equals(fieldClz)) {
            return num.doubleValue();
        }
        if (Character.class.equals(fieldClz) || char.class.equals(fieldClz)) {
            return RandomUtil.randomStr(1).toCharArray()[0];
        }
        if (Byte.class.equals(fieldClz) || byte.class.equals(fieldClz)) {
            return RandomUtil.randomStr(1).getBytes()[0];
        }
        if (Boolean.class.equals(fieldClz) || boolean.class.equals(fieldClz)) {
            return true;
        }
        if (String.class.equals(fieldClz)) {
            return "\"" + RandomUtil.randomStr(8) + "\"";
        }
        if (BigDecimal.class.equals(fieldClz)) {
            return " new BigDecimal() ";
        }
        if (Enum.class.isAssignableFrom(fieldClz)) {
            Enum[] enums = (Enum[])fieldClz.getEnumConstants();
            int i = RandomUtil.randomInt(enums.length);
            return enums[i];
        }
        if (fieldClz.equals(Date.class)) {
            return "new Date()";
        }
        return null;
    }

    private static <T> boolean isPrimitive(Class<T> fieldClz) {
        if (Long.class.equals(fieldClz) || long.class.equals(fieldClz)) {
            return true;
        }
        if (Integer.class.equals(fieldClz) || int.class.equals(fieldClz)) {
            return true;
        }
        if (Short.class.equals(fieldClz) || short.class.equals(fieldClz)) {
            return true;
        }
        if (Float.class.equals(fieldClz) || float.class.equals(fieldClz)) {
            return true;
        }
        if (Double.class.equals(fieldClz) || double.class.equals(fieldClz)) {
            return true;
        }
        if (Character.class.equals(fieldClz) || char.class.equals(fieldClz)) {
            return true;
        }
        if (Byte.class.equals(fieldClz) || byte.class.equals(fieldClz)) {
            return true;
        }
        if (Boolean.class.equals(fieldClz) || boolean.class.equals(fieldClz)) {
            return true;
        }
        if (String.class.equals(fieldClz)) {
            return true;
        }
        if (BigDecimal.class.equals(fieldClz)) {
            return true;
        }
        if (Enum.class.isAssignableFrom(fieldClz)) {
            return true;
        }
        if (fieldClz.equals(Date.class)) {
            return true;
        }
        return false;
    }

    public static boolean isJavaClass(Class<?> clz) {
        return clz != null && clz.getClassLoader() == null;
    }

    public static <T> T newObjectWithPropertiesValue(Class<T> clz) {
        return newObjectWithPropertiesValue(clz, false);
    }

    public static <T> T initMethod(Class<T> clz) {
        return newObjectWithPropertiesValue(clz, false);
    }

    public static <T> void initProcess(Class<T> clz, StringBuffer sb) {
        initProcess(clz, false, sb);
    }

    public static <T> T initProcess(Class<T> clz, Boolean isRecu, StringBuffer sb) {
        T obj = (T)ReflectUtils.newInstance(clz);
        Field[] fields = clz.getDeclaredFields();
        String className = getClassName(clz.getName());
        sb.append(enter + space8 + className + " " + StringUtil.firstLower(className) + " = new " + className + "()");
        for (Field fieldtemp : fields) {
            fieldtemp.setAccessible(true);
            Class fieldClz = fieldtemp.getType();
            try {
                // Init basic type
                if (isPrimitive(fieldClz)) {
                    //                    fieldtemp.set(obj, initPrimitiveValue(fieldClz));
                    sb.append(
                        enter + space8 + StringUtil.firstLower(className) + ".set" + StringUtil
                            .firstUpper(fieldtemp.getName())
                            + "(" + initPrimitiveValueProcess(fieldClz) + ")");
                    continue;
                }

                // Init javabean
                if (!isJavaClass(fieldClz)) {
                    //                    fieldtemp.set(obj, newObjectWithPropertiesValue(fieldClz, true));
                    initProcess(fieldClz, isRecu, sb);
                    sb.append(
                        enter + space8 + StringUtil.firstLower(className) + ".set" + StringUtil
                            .firstUpper(fieldtemp.getName())
                            + "(" + StringUtil.firstLower(fieldtemp.getName()) + ");");
                    continue;
                }

                Type genericType = fieldtemp.getGenericType();
                if (genericType == null) {
                    continue;
                }
                // 如果是泛型参数的类型
                if (genericType instanceof ParameterizedType
                    && fieldClz.equals(List.class)) {
                    ParameterizedType pt = (ParameterizedType)genericType;
                    //得到泛型里的class类型对象
                    Class<?> accountPrincipalApproveClazz = (Class<?>)pt.getActualTypeArguments()[0];
                    if (!isRecu) {
                        fieldtemp.set(obj,
                            Lists.newArrayList(newObjectWithPropertiesValue(accountPrincipalApproveClazz, true)));
                    }
                    continue;
                }

            } catch (Exception e) {
            }
        }
        return obj;
    }

    private static String getClassName(String name) {
        if (name.indexOf(".") < 0) {
            return name;
        }
        int i = name.lastIndexOf(".") + 1;
        return name.substring(i);
    }
}

package org.xiaogang.util;

import com.google.common.collect.Lists;
import net.sf.cglib.core.ReflectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ObjectTestUtilNew {


    public static <T> T newObjectWithPropertiesValue(Class<T> clz, Boolean isRecu) {
        T obj = (T) ReflectUtils.newInstance(clz);
        Field[] fields = clz.getDeclaredFields();
        for (Field fieldtemp : fields) {
            fieldtemp.setAccessible(true);

            Class fieldClz = fieldtemp.getType();
            String fieldName = fieldtemp.getName();
            try {
                //if (fieldClz.getWriteMethod() == null) {
                //    continue;
                //}
                // 字符串类型
                if (fieldClz.equals(String.class)) {
                    fieldtemp.set(obj, fieldName);
                }
                // 数字类型
                Integer num = RandomUtil.randomInt(10);
                if (Long.class.equals(fieldClz) || long.class.equals(fieldClz)) {
                    fieldtemp.set(obj, num.longValue());
                }
                if (Integer.class.equals(fieldClz) || int.class.equals(fieldClz)) {
                    fieldtemp.set(obj, num);
                }
                if (Short.class.equals(fieldClz) || short.class.equals(fieldClz)) {
                    fieldtemp.set(obj, num.shortValue());
                }
                if (Float.class.equals(fieldClz) || float.class.equals(fieldClz)) {
                    fieldtemp.set(obj, num.floatValue());
                }
                if (Double.class.equals(fieldClz) || double.class.equals(fieldClz)) {
                    fieldtemp.set(obj, num.doubleValue());
                }
                if (Character.class.equals(fieldClz) || char.class.equals(fieldClz)) {
                    fieldtemp.set(obj, RandomUtil.randomStr(1).toCharArray()[0]);
                }
                if (Byte.class.equals(fieldClz) || byte.class.equals(fieldClz)) {
                    fieldtemp.set(obj, RandomUtil.randomStr(1).getBytes()[0]);
                }
                if (Boolean.class.equals(fieldClz) || boolean.class.equals(fieldClz)) {
                    fieldtemp.set(obj, true);
                }
                // BigDecimal类型
                if (BigDecimal.class.equals(fieldClz)) {
                    fieldtemp.set(obj, new BigDecimal(num));
                }
                // 枚举类型
                if (Enum.class.isAssignableFrom(fieldClz)) {
                    Enum[] enums = (Enum[]) fieldClz.getEnumConstants();
                    int i = RandomUtil.randomInt(enums.length);
                    fieldtemp.set(obj, enums[i]);
                }
                // 日期类型
                if (fieldClz.equals(Date.class)) {
                    fieldtemp.set(obj, new Date());
                }

                if (!isJavaClass(fieldClz)) {
                    fieldtemp.set(obj, newObjectWithPropertiesValue(fieldClz, true));
                }

                Type genericType = fieldtemp.getGenericType();
                if (genericType == null) {
                    continue;
                }
                // 如果是泛型参数的类型
                if (genericType instanceof ParameterizedType
                        && fieldClz.equals(List.class)) {
                    ParameterizedType pt = (ParameterizedType) genericType;
                    //得到泛型里的class类型对象
                    Class<?> accountPrincipalApproveClazz = (Class<?>) pt.getActualTypeArguments()[0];
                    if (!isRecu) {
                        fieldtemp.set(obj,
                                Lists.newArrayList(newObjectWithPropertiesValue(accountPrincipalApproveClazz, true)));
                    }
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return obj;
    }

    public static boolean isJavaClass(Class<?> clz) {
        return clz != null && clz.getClassLoader() == null;
    }

    /**
     * 初始化一个对象,成员对象的值自动set,减少单测代码量,一般是DO或者DTO对象,必须得有空的构造函数<br> 说明:<br> 1) String类型的值为成员名称<br> 2) 数字类型的值为1~10的随机数<br> 3)
     * 布尔值类型随机为true<br> 3) Date类型的值为当前时间<br> 4) 枚举值为空,需要自己设置<br> 5) 其他类型都不支持,为空<br>
     *
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T newObjectWithPropertiesValue(Class<T> clz) {
        return newObjectWithPropertiesValue(clz, false);
    }

}

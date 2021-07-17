package unit.test.api;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.checkerframework.checker.units.qual.K;
import org.springframework.cglib.core.ReflectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;

public class ObjectInit {

    public static <T> T parseObject(String jsonStr, Class<T> clz) {
        return (T) JSON.parseObject(jsonStr, clz);
    }


    public static <T> List<T> parseList(String jsonStr, Class<T> clz) {
        return JSON.parseArray(jsonStr, clz);
    }

    public static <T> List<T> randomList(Class<T> clz, Integer num) {
        ArrayList<T> objects = Lists.newArrayList();
        for (Integer i = 0; i < num; i++) {
            objects.add((random(clz)));
        }

        try {
            System.out.println(clz.getSimpleName() + ":");
            JSONFormatUtil.printJson(JSON.toJSONString(objects));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objects;
    }

    public static Map randomMap(Class key, Class value ) {
        Map map = new HashMap<>(2);
        map.put(random(key),random(value));
        return map;
    }

    public static <T> T random(Class<T> clz) {
        if (isPrimitive(clz)) {
            return (T) initPrimitiveValue(clz);
        }
        T obj = (T) ReflectUtils.newInstance(clz);
        Field[] fields = clz.getDeclaredFields();
        for (Field fieldtemp : fields) {
            fieldtemp.setAccessible(true);
            Class fieldClz = fieldtemp.getType();
            //if (Modifier.isFinal(fieldClz.getModifiers())) {
            //    continue;
            //}
            try {
                // Init basic type
                if (isPrimitive(fieldClz)) {
                    fieldtemp.set(obj, initPrimitiveValue(fieldClz));
                    continue;
                }

                // Init javabean
                if (!isJavaClass(fieldClz)) {
                    fieldtemp.set(obj, random(fieldClz));
                    continue;
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
                    Object random = random(accountPrincipalApproveClazz);
                    fieldtemp.set(obj, Lists.newArrayList(random));
                    continue;
                }

            } catch (Exception e) {
            }
        }
        try {
            System.out.println(clz.getSimpleName() + ":");
            JSONFormatUtil.printJson(JSON.toJSONString(obj));
        } catch (Exception e) {
            e.printStackTrace();
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
            Enum[] enums = (Enum[]) fieldClz.getEnumConstants();
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
            Enum[] enums = (Enum[]) fieldClz.getEnumConstants();
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

    public static <T> void initProcess(Class<T> clz, StringBuffer sb) {
        initProcess(clz, false, sb);
    }

    public static final String enter = ";\n";
    public static final String space8 = "        ";

    public static <T> T initProcess(Class<T> clz, Boolean isRecu, StringBuffer sb) {
        T obj = (T) ReflectUtils.newInstance(clz);
        Field[] fields = clz.getDeclaredFields();
        String className = getClassName(clz.getName());
        sb.append(space8 + className + " " + className.toLowerCase() + " = new " + className + "()" + enter);
        for (Field fieldtemp : fields) {
            fieldtemp.setAccessible(true);
            Class fieldClz = fieldtemp.getType();
            try {
                // Init basic type
                if (isPrimitive(fieldClz)) {
                    //                    fieldtemp.set(obj, initPrimitiveValue(fieldClz));
                    sb.append(
                            space8 + className.toLowerCase() + ".set" + fieldtemp.getName().substring(0, 1).toUpperCase()
                                    + fieldtemp.getName().substring(1) + "(" + initPrimitiveValueProcess(fieldClz) + ")"
                                    + enter);
                    continue;
                }

                // Init javabean
                if (!isJavaClass(fieldClz)) {
                    //                    fieldtemp.set(obj, newObjectWithPropertiesValue(fieldClz, true));
                    initProcess(fieldClz, isRecu, sb);
                    sb.append(
                            space8 + className.toLowerCase() + ".set" + fieldtemp.getName().substring(0, 1).toUpperCase()
                                    + fieldtemp.getName().substring(1) + "(" + fieldtemp.getName().toLowerCase() + ")" + enter);
                    continue;
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
                                Lists.newArrayList(random(accountPrincipalApproveClazz)));
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

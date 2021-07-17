package unit.test.api;
import org.junit.Test;
import static org.junit.Assert.*;

import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import java.lang.reflect.Field;
import unit.test.api.ObjectInit;
import java.math.BigDecimal;
import org.checkerframework.checker.units.qual.K;
import com.google.common.collect.Lists;
import java.util.*;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.springframework.cglib.core.ReflectUtils;
 
public class ObjectInitTest {

 
    @Test
    public void testRandomMap() { 
        // Initialize the object to be tested
        ObjectInit objectInit = ObjectInit.random(ObjectInit.class);
        // Initialize params of the method
        

        Map invokeResult = objectInit.randomMap(String.class, String.class);

        System.out.println(invokeResult);
        // Write the Assert code
        //Assert.assertEquals(expected, invokeResult);
    }
}
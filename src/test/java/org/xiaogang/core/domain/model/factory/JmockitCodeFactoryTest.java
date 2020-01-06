package org.xiaogang.core.domain.model.factory;

import java.util.List;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

public class JmockitCodeFactoryTest {
    JmockitCodeFactory jmockitCodeFactory = null;

    @Before
    public void initInstance() {
        // Initialize the object to be tested
        jmockitCodeFactory = new JmockitCodeFactory();
    }

    @Test
    public void testGetMatchStatment() {
        // Initialize params of the method;
        String statement = "Dto sd = practiseRepository.load(command.getPractiseId());]";
        List<String> matcherList = Lists.newArrayList();
        matcherList.add("(.*?)(=)(.*?)(practiseRepository" + "\\.)(.*?)(\\((\\n)?)(.*?)(\\))(.*?)(])");
        jmockitCodeFactory.getMatchStatment(statement, matcherList);
        //System.out.println(invokeResult);

        // Write the Assert code
        //Assert.assertEquals(expected, actual);
        //Assert.assertEquals(expected, invokeResult);
    }

}
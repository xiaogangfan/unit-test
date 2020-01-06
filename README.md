### 单测的本质
- 是要去发现代码中的问题。

### 单测环节中存在的问题（现状）
#### 效率
- 手动代码低效，特别是代码重构的时候
- 有些情况对象稍微大一些，我们就得不停的手动set，耗费大量时间
#### 质量
- 应付：为了单测而单测
- 单测不严谨，等于没有

### 对标
JUnitGenerator V2.0
### 优劣势
- 优势
    - 较JUnitGenerator V2.0显著提升研发效率。生成代码的调用，和猜测的验证

### 目前支持的功能
- 针对于单纯的javaBean的code生成，适用于util类、DDD中的领域层等等
- mock类
    - 生成Jmockit风格的单测代码

### 未来要支持的功能
- 通过右键，选择生成单测，然后生成新的单测代码，就的覆盖 （已完成）
- 通过右键，选择生成单测，如果已经存在，窗口提示现状，选择性覆盖哪些方法
- 批量生成功能
- 通过入参控制生成的代码风格，也可以自动检测，比如是Junit还是Jmock判断


该插件的思路
### 目标1：自动生成代码，提升单测效率，节省programmer的时间。
- 方向1：将单测的调用和verify思路通过java代码实现（目前的实现思路）
- 方向2：通过优质的单测例子，使用机器学习手段，不断地训练，生产单测。
        
### 过程中的问题思考
#### 如何指望代码不严谨的人，单测写的严禁？
矛盾：代码写的不好，单测的代码写的也可能会不好，所以需要通过代码按时，足够充分的Assert帮助提升质量
#### 如何区分单测的代码风格？比如普通的Junit or Jmockit
- 标识
    - 文件名结尾，一般的以Service、ServiceImpl、Application等结尾的一般都是需要mock的，故使用mock风格，但是具体哪种mock的组件，后面会提供配置的页面
#### 如何写调用的case？
- 调用参数生成
    - 基本类型 随机生成
    - 引用类型 随机生成
        - 支持代码随机过程可视化，这样可以随意修改想要的值
- 方法调用
    - 如何做多case调用，去猜测真正的调用case
    - 私有方法处理
        - 通过反射将access变成true，调用
    - 常规方法处理
#### 如何写断言
- 根据返回值类型
    - void 
        - 方法没有入参 无需断言
        - 方法有入参 验证当前对象属性的改变
    - boolean 验证返回值 
    - 其他基本类型 验证返回值 
    - 引用类型，验证非空，验证返回值属性

### 想法
- 业务驱动单元测试,业务语言转换成测试用例，测试用例转换成代码
- 将基本的api开放，通过集成，使用者根据自己的情况编写模板
- 如何做成一款产品，卖出去

### 插件安装
地址（先下载，从磁盘安装）：https://plugins.jetbrains.com/plugin/13408-generate-unit-test-code

### 如何使用
- 添加pom:配套的pom：主要用来配合生成单测的代码
```
<dependency>
  <groupId>com.alibaba.cro</groupId>
  <artifactId>unit-test-api</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency> 
```
- 在要测试的代码中，右键，选择自动生成代码，参见截图
![](https://github.com/xiaogangfan/unit-test/blob/master/img/plugin_use.jpg)

```
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.xiaogang.core.domain.model.JavaSourceFile;

/**
 * 描述:
 *
 * @author xiaogangfan
 * @create 2019-08-06 2:17 PM
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Exam {

    private String name;
    private Date effectTime;
    private Integer peroid;
    private Date endTime;
    private String creatorName;
    private String creatorNo;
    private String status;
    private Integer viewAnswer;

    public Exam paramStr(String str, Exam e, JavaSourceFile j) {
        if (viewAnswer == null) {
            return null;
        }
        return this;
    }
    public boolean canViewAnswer() {
        if (viewAnswer == null) {
            return false;
        }
        return viewAnswer == 1;
    }
    public void nore() {

    }
    public Integer digi() {
        return 0;
    }
}
```


生成后的单测如下：
```
import org.junit.Test;
import org.xiaogang.core.domain.model.JavaSourceFile;
import static org.junit.Assert.*;

import org.junit.Assert;
import unit.test.api.ObjectInit;
import org.junit.Before;
import java.util.Date;
 
public class ExamTest {
    Exam exam = null;

 
    @Test
    public void testParamStr() { 
        // Initialize params of the method;
        String str = ObjectInit.random(String.class);
        Exam e = ObjectInit.random(Exam.class);
        JavaSourceFile j = ObjectInit.random(JavaSourceFile.class);
        Exam invokeResult = exam.paramStr(str, e, j);

        // Write the Assert code
        //Assert.assertEquals(expected, actual);
        //Assert.assertEquals(expected, invokeResult);
    }

 
    @Test
    public void testNore() { 
        exam.nore();

        // Write the Assert code
        //Assert.assertTrue(true);
    }

 
    @Test
    public void testCanViewAnswer() { 
        Boolean invokeResult = exam.canViewAnswer();

        // Write the Assert code
        //Assert.assertEquals(expected, actual);
        //Assert.assertEquals(expected, invokeResult);
    }

 
    @Test
    public void testDigi() { 
        Integer invokeResult = exam.digi();

        // Write the Assert code
        //Assert.assertEquals(expected, actual);
        //Assert.assertEquals(expected, invokeResult);
    }

    @Before
    public void initInstance() {
        // Initialize the object to be tested
        exam = ObjectInit.random(Exam.class);
    }
}
```

### 如何贡献
源码： github：git@github.com:xiaogangfan/unit-test.git



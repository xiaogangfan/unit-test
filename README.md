### 单测的本质
- 是要去发现代码中的问题。

### 现实中，写单测环节中可能存在的问题
#### 效率方面
- 手动代码低效，特别是代码重构的时候
- 有些情况对象稍微大一些，我们就得不停的手动set，耗费大量时间
#### 质量方面
- 应付：为了单测而单测
- 单测不严谨，等于没有

### 对标
该项目对标JUnitGenerator V2.0
### 优劣势
- 优势
    - 较JUnitGenerator V2.0显著提升研发效率。生成代码的调用，和猜测的验证

### 本插件的特色
- 自动的生成方法的调用，帮你自动的创建变量、方法的的参数，已随机值的方式生成，无需手动（set）
- 针对调用完成的方法，根据是否有返回值以及变量自动生成Assert的代码，选择性的使用

### 目前支持的功能
- 针对于单纯的javaBean的code生成，适用于util类、DDD中的领域层等等
- mock类
    - 生成Jmockit风格的单测代码

### 未来要支持的功能
- 通过右键，选择生成单测，如果已经存在，窗口提示现状，选择性覆盖哪些方法（这里主要是idea相关的功能开发）
- 更好的去猜测如何设置调用的case、需要多少case以及如何Assert
- 支持更多风格的单测生成，比如Spock等


### 插件的思路
- 方向1：将人工的单测经验，通过java代码来实现（目前的方案）
- 方向2：通过优质的单测例子，使用机器学习手段，不断地训练，生产单测（可能是未来的方案）
        
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
地址（先下载，从磁盘安装）：https://plugins.jetbrains.com/plugin/13408-generate-unit-test-code/versions

### 如何使用
- 添加pom:配套的pom：主要用来配合生成单测的代码
```
<dependency>
  <groupId>com.alibaba.cro</groupId>
  <artifactId>unit-test-api</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency> 
```
- 在要测试的代码中，右键，选择【Auto Generation Test Code】
业务代码：
```
    public Exam paramStr(String str, Exam e) {
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
```
生成后的单测如下：
```
   @Test
    public void testParamStr() { 
        // Initialize params of the method;
        String str = ObjectInit.random(String.class);
        Exam e = ObjectInit.random(Exam.class);
        Exam invokeResult = exam.paramStr(str, e);

        // Write the Assert code
        //Assert.assertEquals(expected, actual);
        //Assert.assertEquals(expected, invokeResult);
    }

 
    @Test
    public void testCanViewAnswer() { 
        Boolean invokeResult = exam.canViewAnswer();

        // Write the Assert code
        //Assert.assertEquals(expected, actual);
        //Assert.assertEquals(expected, invokeResult);
    }

```
- 针对于需要mock的代码，比如xxService
业务代码：对第三方的api调用是需要mock的
```
public boolean canGoWorld(String batchId) {
        SimpleResultDTO<SimpleBatchInfoDTO> resultDTO = inspectHsfService.searchBatch(batchId);
        if (!resultDTO.isSuccess() || resultDTO.getData() == null) {
            log.error("batchId [" + batchId + "] searchBatch fail,detail:" + JSON
                .toJSONString(resultDTO));
            throw new RuntimeException("调用RCP异常");
        }
        if (StringUtils.equals(BatchStatusEnum.NO_COMPLETE.name(), resultDTO.getData().getStatus())) {
            return true;
        }
        return false;
    }
```
单测代码: 这里会把需要mock的数据，猜测出来，然后开发可以自由的去调整，目前是基于Jmockit来实现的
```
    @Test
    public void testCanGoWorld() { 
        // Initialize params of the method;
        String batchId = ObjectInit.random(String.class);
        new Expectations() {{
            //inspectHsfService.searchBatch(batchId);
            //result = ObjectInit.random(SimpleResultDTO<SimpleBatchInfoDTO>.class);// mock的返回值，这里可以手动的修改，ObjectInit是工具类，随机初始化bean
        }};
        Boolean invokeResult = taskService.canGoWorld(batchId);// 如果有调用的返回值，下面的Assert也会提示要验证这个值

        //new Verifications() {{
        //}};
        // Write the Assert code
        //Assert.assertEquals(expected, actual);
        //Assert.assertEquals(expected, invokeResult);
    }
```


### 源码
github：git@github.com:xiaogangfan/unit-test.git

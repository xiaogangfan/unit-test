迭代的需求
1. 支持方法级别的覆盖
    1. 目前只支持类级别的全量覆盖，在新增方法时特别麻烦
1. 随机生成的基本类型和引用类型，支持显示化和定制化生成
    1. 比如下边这个代码直接给一个随机的变量值会更好
    ``` 
        String taskId = ObjectInit.random(String.class); 
   ```
    改成
    ``` 
        String taskId = "zhis is a String" 
    ```
    1. 针对引用类型的初始化,给一个自定义对象的方法
    ``` 
        Practise practise = ObjectInit.random(Practise.class); 
    ```
    改成
    ``` Practise practise = initPractise(); 
        public Practise initPractise(){return null;} 
    ```


迭代的需求
1. 支持分支覆盖【待定方案】
1. 支持代码风格选择【待定方案】
1. 支持方法级别的覆盖
    1. 目前只支持类级别的全量覆盖，在新增方法时特别麻烦【已完成】
1. 随机生成的基本类型和引用类型，支持显示化和定制化生成
    1. 比如下边这个代码直接给一个随机的变量值会更好【待定方案】
    ``` 
        String taskId = ObjectInit.random(String.class); 
   ```
    改成
    ``` 
        String taskId = "zhis is a String" 
    ```
    1. 针对引用类型的初始化,支持参数的反序列化生成【已完成】
    ``` 
        FsscTaskUpdateDTO updateDTO = ObjectInit.random(Practise.class);
    ```
    改成
    ``` 
        FsscTaskUpdateDTO updateDTO = ObjectInit.parseObject("{\n" +
                        "\t\"count\":8,\n" +
                        "\t\"itemCode\":\"cb0ed77d\",\n" +
                        "\t\"itemName\":\"1502a8cd\",\n" +
                        "\t\"price\":8\n" +
                        "}\n" +
                        "Purch",FsscTaskUpdateDTO.class); 
    ```
1. 支持在生成代码时，直接生成json的随机。
    1.方案是使用自定义一个类加载器，具体思路是，拿到对应文件的路径，反查到target/classes路径，供classlodader加载
1. 增加统计功能，分析使用者的情况 
1. 现在代码可读性较差，需要重构下，设计好类图   

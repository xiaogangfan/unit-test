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


package org.xiaogang.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class MyClassloader {

    /**
     * @param args
     * @throws MalformedURLException
     */
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws MalformedURLException {
//        String classesPath = new MyClassloader().getClassesPath("/Users/fanxiaogang/Documents/code/unit-test/unit-test-api/src/main/TEST.java");
//        System.out.println(classesPath);
        //自定义类加载路径
        String path = "file:/Users/fanxiaogang/Documents/code/unit-test/";
        //String jarpath = "jar:/D:/lib/";

        URL[] urls = {new URL(path)};
        //这里没有指定父类加载器，所以不会有向上委托;这个类加载器，只会在自己指定的路径内寻找类
        ClassLoader loader = new URLClassLoader(urls, null);
        try {
            //在自定义的类加载路径中查找类
            Class classzz = loader.loadClass("org.xiaogang.util.Person");
            System.out.println("加载路径：" + loader.getResource(""));
            System.out.println("类加载器：" + classzz.getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Class load(String classPath,String clazzName) {
//        String path = "file:/Users/fanxiaogang/Documents/code/unit-test/";
        //String jarpath = "jar:/D:/lib/";

        URL[] urls = new URL[0];
        try {
            urls = new URL[]{new URL("file:"+classPath)};
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //这里没有指定父类加载器，所以不会有向上委托;这个类加载器，只会在自己指定的路径内寻找类
        ClassLoader loader = new URLClassLoader(urls, null);
        try {
            //在自定义的类加载路径中查找类
            Class classzz = loader.loadClass(clazzName);
            System.out.println("加载路径：" + loader.getResource(""));
            System.out.println("类加载器：" + classzz.getClassLoader());
            return classzz;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getClassesPath(String path) {
        int index1 = path.indexOf("/src/");
        int index2 = path.indexOf("\\src\\");
        if (!(index1 > -1 || index2 > -1)) {
            throw new RuntimeException("需要时maven构建的项目");
        }

        index1 = index1 > -1 ? index1 : index2;

        String classPath = path.substring(0, index1) + File.separator + "target" + File.separator + "classes" + File.separator;
        return classPath;
    }

}
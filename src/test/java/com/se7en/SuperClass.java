package com.se7en;

/**
 * Created by Administrator on 2017/8/1.
 */
public class SuperClass {

    static {
        System.out.println("SuperClass init!");
    }

    public static int value = 123;

    public static final String HELLOWORLD = "hello world!";

    public static void main(String[] args) {
//        SuperClass[] sca = new SuperClass[10];
        System.out.println(SuperClass.HELLOWORLD);
    }

}

class SubClass extends SuperClass{

    static {
        System.out.println("SubClass init!");
    }

}
package com.se7en;

/**
 * Created by Administrator on 2017/7/29.
 */
public class StaticTest {

    public static void main(String[] args) {
        new Sub();
    }

}

class Father{

    static {
        System.out.println("--------------------father static");
    }

    public Father(){
        System.out.println("--------------------father constructor");
    }

}

class Sub extends Father{

    static {
        System.out.println("--------------------sub static");
    }

    public Sub(){
        System.out.println("--------------------sub constructor");
    }

}
package com.my.EnumDemos.MyDemo;

/**
 * @auther Summerday
 */
public class Season {

    //无法随意创建对象
    private Season(){}

    //代码冗余

    //提供的对象在类外不能改变final修饰
    public static final Season SPRING = new Season();
    public static final Season SUMMER = new Season();
    public static final Season AUTUMN = new Season();
    public static final Season WINTER = new Season();
}



//枚举中，构造方法默认私有，并且只能私有
enum EnumSeason{
    SPRING(),SUMMER,AUTUMN(9),WINTER;

    private EnumSeason(){}
    private EnumSeason(int month){
        this.month = month;
    }
    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    private int month;

    public void playAutumn(){
        System.out.println(AUTUMN.month);
    }
}


class Main{
    public static void main(String[] args) {
        EnumSeason.AUTUMN.playAutumn();
    }
}
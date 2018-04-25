package com.scala.module2

class parent
{
    def fun()={println("parent");}
}

class child2 extends parent
{
   override def fun()={println("child");}
}

object run10 extends App
{
    var l1 =new child2();

    l1.fun();

    var l2 =new parent();

    l2.fun();
}
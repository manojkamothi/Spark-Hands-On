package com.scala.module2

class ClassExample
{
    private var value=0;

    def increase{value+=1}

    def display{println(value)}
}

object run extends App
{
    val classobj=new ClassExample()

    classobj.increase

    classobj.increase

    classobj.display
}
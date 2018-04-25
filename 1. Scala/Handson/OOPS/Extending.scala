package com.scala.module2

class Extending
{
    var firstname="";
}

class child extends Extending
{
    var lastname = "John";
}

object run9 extends App
{
    var child =new child();

    child.firstname="Teja";

    child.lastname="Kandregula";

    println("First name : "+child.firstname +" Last name : "+child.lastname);
}
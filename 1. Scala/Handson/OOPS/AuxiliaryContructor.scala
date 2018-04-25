package com.scala.module2

class AuxiliaryContructor
{
    private var value=10;

    def this(value : Int)
    {
          this();        //calls primary contructor
          this.value=value;
          println(value);
    }
}

object run3 extends App
{
      var AuxiliaryContructorObj=new AuxiliaryContructor(35);
}
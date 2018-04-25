
trait A{
  
  println("This is Trait A")
}

trait B{
  
  println("This is Trait B")
}
  
  trait C{
    
    println("This is Trait C")
    }
  
  class D extends A with B with C{
    println("This is class D")
  }

object traittest {
  def main(arg: Array[String]){
    
    var ob = new D()
  }
}
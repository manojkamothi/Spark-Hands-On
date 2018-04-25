
class Auxclass{
  
  var firstname: String = null
  var lastname: String =null
  println("We are in class")
  
  //Defining My First Auxiliary Constructor
  
  
  def this(fn: String){
    this()
    firstname = fn
    println("Auxiliary Constructor: One Argument")
  }
  
  //Defining My Second Auxiliary Constructor
  
  def this(fn: String, ln: String){
    
    this(fn)
    lastname = ln
    println("Auxiliary Constructor: Two Arguments")
    
  }
}
object AuxConstructor {
  def main(arg: Array[String]){
    
    var ob = new Auxclass("spark","Scala")
    println(ob.firstname)
    println(ob.lastname)
  }
  
}
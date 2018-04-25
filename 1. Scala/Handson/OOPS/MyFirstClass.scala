
class NewClass(message: String){
  
  def sayHi() = println(message)
}
object MyFirstClass {
  def main(arg: Array[String]){
    
    var ob = new NewClass("Spark")
    ob.sayHi()
  
  }
}
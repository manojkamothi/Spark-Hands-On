

object MyFirstFunction {
  def main(arg: Array[String]){
   
    def add(a: Double , b: Double) : Double = {
      
      var sum: Double = 0
      sum = a + b
      return sum
    }
    
    println("SUM :" + add(4,12))
  }
  
}
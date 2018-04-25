

object NestedFunc {
  def main(arg: Array[String]){
    
    def square(x: Double) : Double = {
      
      return x*x
    }
    
    def sumsquares(x: Double, y: Double) : Double = {
      
      return square(x) + square(y)
    }
    
    println("Sum of squares :" + sumsquares(4,5))
  }
}
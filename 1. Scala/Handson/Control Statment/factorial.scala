

object factorial {
  def main(arg: Array[String]){
    
    var fact = 1
    var num = 10
    while(num >0){
      fact = fact * num
      num = num - 1
    }
    
    println(fact)
    
    
  }
}
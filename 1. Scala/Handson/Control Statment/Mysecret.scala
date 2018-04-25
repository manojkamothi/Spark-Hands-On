

object Mysecret {
   def main(arg: Array[String]){
     
    var numguess = 0
    do{
      println("Enter the Number :")
      numguess = readLine.toInt
      
    }while(numguess!= 10)
     
     println("You got the number")
     
   }
}
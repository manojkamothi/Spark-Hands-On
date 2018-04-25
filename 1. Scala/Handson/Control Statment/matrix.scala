/*
 * Create a 3 x 3 matrix
 * 1,1
 * 1,2
 * 1,3
 * 2,1
 * ..
 * ..
 * ..
 * 3,3
 */

object matrix {
   def main(arg: Array[String]){
    for(i <- 1 to 3; j <- 1 to 3)
         println(i,j)
  }
  
}
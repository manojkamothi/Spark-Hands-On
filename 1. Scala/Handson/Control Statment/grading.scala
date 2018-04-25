
/*
 * I want you to create a grading system using if else
 * A - 80-100
 * B - 65-79
 * C - 50-64
 * D - 35-49
 * Fail - 0-34
 */
object grading {
  
  def main(arg: Array[String]){

  var x = 13
  if(x >= 80)
  {
    println("Grade A")
  } else if (x >=65 && x<= 80)
    println("grade B")
    else if (x >=50 && x<= 65)
    println("grade C")
    else if (x >=35 && x<= 50)
    println("grade C")
    
    else println("fail")
 }
  
}
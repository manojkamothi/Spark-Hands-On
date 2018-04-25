class Dog(name: String, sound: String, growl: String) extends BaseAnimal(name, sound){
  
  println("This is Dog Class with 3 paramas; Primary Constructor")
  
  def this(){
    this("No Name", "No Sound", "No Growl")
    println("This is Dog Class with 0 paramas; Auxiliary Constructor")
  }
 }
class BaseAnimal(var name: String, var sound: String){
  
  def getName(): String = name
  def getSound(): String = sound
  
  def setName(n: String){
    
    if(!name.matches(".*\\d+.*"))
      name = n
    else
      name = "No Name"
     }
  
  def setSound(s: String){
    
    sound = s
    
  }
  
 println("This is Animal class with 2 params; Primary Constructor")
 
 def this(n: String){
   
   this("No Name", "No Sound")
   setName(n)
   println("This is Animal Class with 1 parameter: Auxiliary Constructor")
 }
  
}

object Animal {
  def main(arg: Array[String]){
    var ba = new Dog()
    
    
  }
}
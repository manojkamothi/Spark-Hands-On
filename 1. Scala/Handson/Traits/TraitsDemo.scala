trait Swimming
{
//def swim()= println("I'm Swimming")
def swim()
}
trait Walking
{
def walk()
}
abstract class Bird
{
def flyMsg : String
def fly() = println(flyMsg)
}
class Pigeon extends Bird with Swimming with Walking
{
val flyMsg = "I'm flying"
def swim()= println("I'm Swimming")
def walk() = println("I'm walking")
}
object TraitsDemo {
def main(args:Array[String])
  {
val p1 = new Pigeon
p1.swim()
p1.fly();
p1.walk()
  }
}

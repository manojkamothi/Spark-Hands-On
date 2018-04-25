

trait Flyable{
  def fly(): String
}

trait BulletProof{
  
  def hitByBullet() : String
  def ricochet(startSpeed: Double) : String = {
    
    return "the bullet ricocheted at speed of :" +startSpeed * .75
  }
}

class SuperHero(name: String) extends Flyable with BulletProof{
  
  println("This is a Marvel Super Hero")
  def fly() = "This is Fly method"
  def hitByBullet() = "This is Hit by Bullet"
}


object Marvel {
  def main(arg: Array[String]){
    
  var ob =new SuperHero("Batman")  
  }
}
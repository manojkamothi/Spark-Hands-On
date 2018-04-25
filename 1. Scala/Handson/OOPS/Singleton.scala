import java.io._

object SinglePoint {
varx1:Int = 0
vary1:Int = 0
defmove(dx:Int, dy:Int)
  {
x1 = x1+dx
y1 = y1+dy
println(x1+" "+y1)
  }

}
object SinglePointDemo{
def main(args:Array[String]){
SinglePoint.move(11,22)
}

}

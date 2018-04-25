import java.io._

class Point(val xc:Int,valyc:Int){
var x:Int= xc
var y:Int=yc

def move(dx:Int,dy:Int){
      x = x + dx
      y = y +dy
println("Point x location : "+ x);
println("Point y location : "+ y);
}
}

class BigPoint( valx:Int, valy:Int,valz:Int)extends Point(x,y) {
varz1 :Int = z;

def move(dx:Int,dy:Int,dz:Int)
  {
x1 = x1+x
y1 = y1+y
z1 = z1+z
println(x1+" "+y1+" "+z1)
  }

}
object ExyendingDemo{
def main(args:Array[String]){
val p1=newBigPoint(10,20,15);

// Move to a new location
      P1.move(10,10,5);
}
}

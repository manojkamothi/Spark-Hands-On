importjava.io.FileReader
importjava.io.FileNotFoundException
importjava.io.IOException

object FinallyDemo{
def main(args:Array[String]){
try{
val f =newFileReader("input.txt")
}catch{
case ex:FileNotFoundException=>{
println("Missing file exception")
}
case ex:IOException=>{
println("IO Exception")
}
}finally{
println("Exiting finally...")
}
}
}

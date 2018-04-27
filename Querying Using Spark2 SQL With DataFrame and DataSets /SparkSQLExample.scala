===================== USING DATAFRAM IN SPARK2 =======================================

--------------- Use cases with Code explanation:

1. We first import a Spark Session into Apache Spark.
2. Creating a Spark Session ‘spark’ using the ‘builder()’ function.
3. Importing the Implicts class into our ‘spark’ Session.
4. We now create a DataFrame ‘df’ and import data from the ’employee.json’ file.
5. Displaying the DataFrame ‘df’. The result is a table of 5 rows of ages and names from our ’employee.json’ file. 


1 import org.apache.spark.sql.SparkSession
2 val spark = SparkSession.builder().appName("Spark SQL basic example").config("spark.some.config.option", "some-value").getOrCreate()
3 import spark.implicits._
4 val df = spark.read.json("hdfs://localhost:8020//user/training/data_sets/emp.json")
5 df.show()

--------------- Use cases with Code explanation:

1. Importing the Implicts class into our ‘spark’ Session.
2. Printing the schema of our ‘df’ DataFrame.
3. Displaying the names of all our records from ‘df’ DataFrame.

1 import spark.implicits._
2 df.printSchema()
3 df.select("name").show()

--------------- Use cases with Code explanation:

1. Displaying the DataFrame after incrementing everyone’s age by two years.
2. We filter all the employees above age 30 and display the result.

1 df.select($"name", $"age" + 2).show()
2 df.filter($"age" > 30).show()


--------------- Use cases with Code explanation:

1. Counting the number of people with the same ages. We use the ‘groupBy’ function for the same.
2. Creating a temporary view ’employee’ of our ‘df’ DataFrame.
3. Perform a ‘select’ operation on our ’employee’ view to display the table into ‘sqlDF’.
4. Displaying the results of ‘sqlDF’.

1 df.groupBy("age").count().show()
2 df.createOrReplaceTempView("employee")
3 val sqlDF = spark.sql("SELECT * FROM employee")
4 sqlDF.show()


============================= USING DATASET WITH SPARK2 ====================================

---------------------------------------- Creating Datasets ---------------------------------------- 
After understanding DataFrames, let us now move on to Dataset API. The below code creates a Dataset class in SparkSQL.

--------------- Use cases with Code explanation:
1. Creating a class ‘Employee’ to store name and age of an employee.
2. Assigning a Dataset ‘caseClassDS’ to store the record of John3.
3. Displaying the Dataset ‘caseClassDS’.
4. Creating a primitive Dataset to demonstrate mapping of DataFrames into Datasets.
5. Assigning the above sequence into an array.

1 case class Employee(name: String, age: Long)
2 val caseClassDS = Seq(Employee("John3", 58)).toDS()
3 caseClassDS.show()
4 val primitiveDS = Seq(1, 2, 3).toDS
5 primitiveDS.map(_ + 1).collect()

----------------------------------------- Figure: Creating a Dataset from a JSON file --------------------------------

--------------- Use cases with Code explanation:
1. Setting the path to our JSON file ’employee.json’.
2. Creating a Dataset and from the file.
3. Displaying the contents of ’employeeDS’ Dataset.

val path = "hdfs://localhost:8020/user/training/data_sets/emp.json"
val employeeDS = spark.read.json(path).as[Employee]
employeeDS.show()

-------------------------- Adding Schema To RDDs (Spark introduces the concept of an RDD (Resilient Distributed Dataset), an immutable fault-tolerant, distributed collection of objects that can be operated on in parallel.)

============================ Figure: Creating a DataFrame for transformations ============================

--------------- Use cases with Code explanation:
1. Importing Expression Encoder for RDDs. RDDs are similar to Datasets but use encoders for serialization.
2. Importing Encoder library into the shell.
3. Importing the Implicts class into our ‘spark’ Session.
4. Creating an ’employeeDF’ DataFrame from ’employee.txt’ and mapping the columns based on the delimiter comma ‘,’ into a temporary view ’employee’.
5. Creating the temporary view ’employee’.
6. Defining a DataFrame ‘youngstersDF’ which will contain all the employees between the ages of 18 and 30.
7. Mapping the names from the RDD into ‘youngstersDF’ to display the names of youngsters.

1 import org.apache.spark.sql.catalyst.encoders.ExpressionEncoder
2 import org.apache.spark.sql.Encoder
3 import spark.implicits._
4 val employeeDF = spark.sparkContext.textFile("hdfs://localhost:8020/user/training/data_sets/emp.txt").map(_.split(",")).map(attributes => Employee(attributes(0), attributes(1).trim.toInt)).toDF()
5 employeeDF.createOrReplaceTempView("employee")
6 val youngstersDF = spark.sql("SELECT name, age FROM employee WHERE age BETWEEN 18 AND 30")
7 youngstersDF.map(youngster => "Name: " + youngster(0)).show()

--------------- Use cases with Code explanation:

1. Converting the mapped names into string for transformations.
2. Using the mapEncoder from Implicits class to map the names to the ages.
3. Mapping the names to the ages of our ‘youngstersDF’ DataFrame. The result is an array with names mapped to their respective ages.

1 youngstersDF.map(youngster => "Name: " + youngster.getAs[String]("name")).show()
2 implicit val mapEncoder = org.apache.spark.sql.Encoders.kryo[Map[String, Any]]
3 youngstersDF.map(youngster => youngster.getValuesMap[Any](List("name", "age"))).collect()


----------------- Operations on JSON Datasets

1. Setting to path to our ’employee.json’ file.
2. Creating a DataFrame ’employeeDF’ from our JSON file.
3. Printing the schema of ’employeeDF’.
4. Creating a temporary view of the DataFrame into ’employee’.
5. Defining a DataFrame ‘youngsterNamesDF’ which stores the names of all the employees between the ages of 18 and 30 present in ’employee’.
6. Displaying the contents of our DataFrame.

1 val path = "hdfs://localhost:8020/user/training/data_sets/emp.json"
2 val employeeDF = spark.read.json(path)
3 employeeDF.printSchema()
4 employeeDF.createOrReplaceTempView("employee")
5 val youngsterNamesDF = spark.sql("SELECT name FROM employee WHERE age BETWEEN 18 AND 30")
6 youngsterNamesDF.show()
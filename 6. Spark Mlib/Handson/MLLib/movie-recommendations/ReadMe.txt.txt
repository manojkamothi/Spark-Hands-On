place the datasets in /user/cloudera/datasets location of hdfs

then compile the project 
---------------------------
sbt compile

package the project 
---------------
sbt package

submit the application
----------------------
spark-submit --class MovieALS --master yarn target/scala-2.10/movie-recommendations_2.10-1.0.jar

result will be shown on the console

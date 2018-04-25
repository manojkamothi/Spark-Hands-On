import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.recommendation.{ALS, Rating}
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  */
case class Movie(movieId: Int, title: String)

object MovieALS {

  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("Movie-Recommendations").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    val ratings = sc.textFile("/user/cloudera/datasets/ratings.dat")
    val users   = sc.textFile("/user/cloudera/datasets/users.dat")
    val movies  = sc.textFile("/user/cloudera/datasets/movies.dat")

    // function to parse input UserID::MovieID::Rating
    // and pass into  constructor for org.apache.spark.mllib.recommendation.Rating class
    def parseRating(str:String) : Rating = {
      val fields = str.split("::")
      Rating(fields(0).toInt,fields(1).toInt,fields(2).toDouble)
    }
    // function to parse input into Movie class
    def parseMovie(str: String): Movie = {
      val fields = str.split("::")
      assert(fields.size == 3)
      Movie(fields(0).toInt, fields(1))
    }

    val ratingsRDD = ratings.map(parseRating).cache()
    val moviesDF = movies.map(parseMovie).toDF.cache()
    // Randomly split ratings RDD into training data RDD (80%) and test data RDD (20%)
    val splits = ratingsRDD.randomSplit(Array(0.8, 0.2), 0L)

    val trainingRatingsRDD = splits(0).cache()
    val testRatingsRDD = splits(1).cache()

    val numTraining = trainingRatingsRDD.count()
    val numTest = testRatingsRDD.count()
    println(s"Training: $numTraining, test: $numTest.")

    // Build the recommendation model using ALS with rank=20, iterations=10
    val model = ALS.train(trainingRatingsRDD, 20, 10)

    // Make movie predictions for user 4169
    val topRecsForUser = model.recommendProducts(4169, 10)

    // get movie titles to show with recommendations
    val movieTitles=moviesDF.map(array => (array(0), array(1))).collectAsMap()

    // print out top recommendations for user 4169 with titles
     topRecsForUser.map(rating => (movieTitles(rating.product), rating.rating)).foreach(println)


    // get predicted ratings to compare to test ratings
    val predictionsForTestRDD  = model.predict(testRatingsRDD.map{case Rating(user, product, rating) => (user, product)})

    println("Recommends for TestRDD ratings")

    predictionsForTestRDD.take(10).mkString("\n")

    // prepare the predictions for comparison
    val predictionsKeyedByUserProductRDD = predictionsForTestRDD.map{
      case Rating(user, product, rating) => ((user, product), rating)
    }
    // prepare the test for comparison
    val testKeyedByUserProductRDD = testRatingsRDD.map{
      case Rating(user, product, rating) => ((user, product), rating)
    }

    //Join the test with the predictions
    val testAndPredictionsJoinedRDD = testKeyedByUserProductRDD.join(predictionsKeyedByUserProductRDD)

    println(testAndPredictionsJoinedRDD.take(10).mkString("\n"))


  }
}

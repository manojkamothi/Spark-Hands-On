val rawData = sc.textFile("hdfs://localhost:8020/user/training/ml-100k/u.data")

rawData.first()

val rawRatings = rawData.map(_.split("\t").take(3))

import org.apache.spark.mllib
import org.apache.spark.mllib.recommendation.ALS
import org.apache.spark.mllib.recommendation.Rating

val ratings = rawRatings.map { case Array(user, movie, rating) => Rating(user.toInt, movie.toInt, rating.toDouble) }

ratings.first()

# Train the Data
val model = ALS.train(ratings, 50, 10, 0.01)

# test the model
val predictRating = model.predict(789, 123)

val userId = 789
val k = 10
val topKRecs = model.recommendProducts(userId, k)

println(topKRecs.mkString("\n"))

val movies = sc.textFile("hdfs://localhost:8020/user/training/ml-100k/u.item")
val titles = movies.map(line => line.split("\\|").take(2)).map(array => (array(0.toInt), array(1))).collectAsMap()

val moviesForUser = ratings.keyBy(_.user).lookup(789)

println(moviesForUser.size)

moviesForUser.sortBy(-_.rating).take(10).map(rating => (titles(rating.product.toString), rating.rating)).foreach(println)
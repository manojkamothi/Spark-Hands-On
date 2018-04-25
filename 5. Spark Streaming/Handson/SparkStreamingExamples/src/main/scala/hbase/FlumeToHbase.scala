package hbase


import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapred.TableOutputFormat
import org.apache.hadoop.mapred.JobConf
import org.apache.spark.SparkConf
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.flume.FlumeUtils
import org.apache.spark.storage.StorageLevel


object FlumeToHbase {

  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)


  val (r,b,swpd,free,buff,cash,si,so,bi,bo,ins,cs,us,sy,id) =
    ( "R".getBytes(), "B".getBytes(),"SWPD".getBytes(), "FREE".getBytes(), "BUFF".getBytes(),
      "CACH".getBytes(), "SI".getBytes(), "SO".getBytes(), "BI".getBytes(), "BO".getBytes(),
      "INS".getBytes(), "CS".getBytes(), "US".getBytes(), "SY".getBytes(), "ID".getBytes() )

    val table = "vmstat"
    val data = "data".getBytes()

  def main(args: Array[String]): Unit = {


    val sparkConf = new SparkConf().setAppName(this.getClass.getName)
      .setMaster("local[2]")
    val ssc = new StreamingContext(sparkConf, Seconds(10))

    val conf = HBaseConfiguration.create()
    conf.set(TableOutputFormat.OUTPUT_TABLE, table)
    conf.set("hbase.zookeeper.quorum", "localhost");
    conf.set("hbase.zookeeper.property.clientPort", "2181");
    //conf.set("zookeeper.znode.parent", "/hbase-unsecure");

    val jobConfig: JobConf = new JobConf(conf, this.getClass)
    jobConfig.set("mapreduce.output.fileoutputformat.outputdir", "hdfs://localhost:8020/user/cloudera/out")
    jobConfig.setOutputFormat(classOf[TableOutputFormat])
    jobConfig.set(TableOutputFormat.OUTPUT_TABLE, table)

    import org.apache.hadoop.hbase.HColumnDescriptor
    import org.apache.hadoop.hbase.HTableDescriptor
    import org.apache.hadoop.hbase.client.HBaseAdmin
    val admin = new HBaseAdmin(conf)

    if (!admin.isTableAvailable(table)) {
      println("Creating  Table " + table)
      val tableDesc = new HTableDescriptor(table)
      tableDesc.addFamily(new HColumnDescriptor(data))
      admin.createTable(tableDesc)
    }



    val stream = FlumeUtils.createPollingStream(ssc, "localhost", 6666, StorageLevel.MEMORY_ONLY_SER_2)
                 .map(x => x.event)
                 .map(x => new String(x.getBody.array()))
                 .filter(line => !line.contains("memory"))
                 .filter(line => !line.contains("buff"))

    stream.foreachRDD {

      stats =>

                
       val putObject = stats.map(_.split("[\\s]+")).map {

          vmstat =>
              val put = new Put(vmstat(5).getBytes())
              put.add(data, r, vmstat(0).getBytes)
              put.add(data, b, vmstat(1).getBytes)
              put.add(data, swpd, vmstat(2).getBytes)
              put.add(data, free, vmstat(3).getBytes)
              put.add(data, buff, vmstat(5).getBytes)
              put.add(data, cash, vmstat(6).getBytes)
              put.add(data, si, vmstat(7).getBytes)
              put.add(data, so, vmstat(8).getBytes)
              put.add(data, bi, vmstat(10).getBytes)
              put.add(data, bo, vmstat(11).getBytes)
              put.add(data, ins, vmstat(12).getBytes)
              put.add(data, cs, vmstat(13).getBytes)
              put.add(data, us, vmstat(14).getBytes)
              put.add(data, sy, vmstat(15).getBytes)
              put.add(data, id, vmstat(16).getBytes)
              (new ImmutableBytesWritable(vmstat(5).getBytes), put)
          }
      putObject.saveAsHadoopDataset(jobConfig);
    }


    ssc.start
    ssc.awaitTermination
  }
}
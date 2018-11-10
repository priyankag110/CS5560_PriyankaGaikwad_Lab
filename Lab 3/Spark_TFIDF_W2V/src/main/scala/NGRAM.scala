import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Mayanka on 19-06-2017.
  */
object NGRAM {

  def main(args: Array[String]): Unit = {

    System.setProperty("hadoop.home.dir", "C:\\Users\\Shawn\\Downloads\\winutils")

    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")

    val sc = new SparkContext(sparkConf)

    val data = sc.wholeTextFiles("C:\\Users\\Shawn\\Documents\\GitHub\\CS5560_PriyankaGaikwad_Lab\\Lab 1\\Retrive_abstract\\new_data_alzimers\\projectdata",minPartitions=1)
    val files = data.map { case (filename, content) => filename}
    def processFile(file: String) = {
      println (file)

      // your logic of processing a single file comes here

      val logData = sc.textFile(file).collect();
      val str =logData.toString()

      for(i <- 0 until logData.length){
        val a = getNGrams( logData(i),3)
        a.foreach(f=>println(f.mkString(" ")))
      }

      // save rdd of single file processed data to hdfs comes here
    }

    files.collect.foreach( filename => {
      processFile(filename)
    })

   // val a = getNGrams( "the bee is bee of the bees." ,3)
    //a.foreach(f=>println(f.mkString(" ")))
  }

  def getNGrams(sentence: String, n:Int): Array[Array[String]] = {
    val words = sentence
    val ngrams = words.split(' ').sliding(n)
    ngrams.toArray
  }

}



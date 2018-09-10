package wordnet

import java.io.{BufferedWriter, File, FileWriter}

import org.apache.spark.{SparkConf, SparkContext}
import rita.RiWordNet

/**
  * Created by Mayanka on 26-06-2017.
  */
object WordNetSpark {
  def main(args: Array[String]): Unit = {
   // System.setProperty("hadoop.home.dir", "C:\\winutils")
    val conf = new SparkConf().setAppName("WordNetSpark").setMaster("local[*]").set("spark.driver.memory", "4g").set("spark.executor.memory", "4g")
    val sc = new SparkContext(conf)

    for( a <- 1 to 11) {
      val data = sc.textFile("data/filteredAbstractData/" + (a) + ".txt")
      val file = new File("data/filteredAbstractData/" + (a) + "_synon.txt")
      val bw = new BufferedWriter(new FileWriter(file))

      val dd = data.map(line => {
        val wordnet = new RiWordNet("C:\\\\WordNet-3.0")
        val wordSet = line.split(" ")
        val synarr = wordSet.map(word => {
          if (wordnet.exists(word))
            (word, getSynoymns(wordnet, word))
          else
            (word, null)
        })
        synarr
      })
      dd.collect().foreach(linesyn => {
        linesyn.foreach(wordssyn => {
          if (wordssyn._2 != null)

            bw.write(wordssyn._1 + ":" + wordssyn._2.mkString(","))

        })
        bw.write("\n\n")
      })
      bw.close()

      println("File " + (a)+ " Processed Successfully.")
    }
  }
  def getSynoymns(wordnet:RiWordNet,word:String): Array[String] ={
    // println(word)
    val pos=wordnet.getPos(word)
    // println(pos.mkString(" "))
    val syn=wordnet.getAllSynonyms(word, pos(0), 10)
    syn
  }


}

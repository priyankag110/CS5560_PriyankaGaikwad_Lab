package openie

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import scala.io.Source
import java.io._
/**
  * Created by Mayanka on 27-Jun-16.
  */
object SparkOpenIE {

  def main(args: Array[String]) {
    // Configuration
    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")

    val sc = new SparkContext(sparkConf)

    // For Windows Users
   // System.setProperty("hadoop.home.dir", "C:\\winutils")



    // Turn off Info Logger for Console
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    /*
    val input = sc.textFile("data/sentenceSample").map(line => {
      //Getting OpenIE Form of the word using lda.CoreNLP

      val t=CoreNLP.returnTriplets(line)
      println (t)
      println ("hello")
    })
*/

    for( a <- 1 to 11) {
      var myVar: Int = 0;
      val file = new File("data/filteredAbstractData/" + (a) + "_triplet.txt")
      val bw = new BufferedWriter(new FileWriter(file))
      val source = Source.fromFile("data/filteredAbstractData/" + (a) + ".txt")
      for (line <- source.getLines()) {
        val t = CoreNLP.returnTriplets(line)
        // println (t)
        bw.write(t)
        bw.write("\n")
        myVar = myVar + t.count(_ == '(')
      }
      bw.write("------Triplets found are " + (myVar) + "-------")
      bw.close()
      source.close()
      println(myVar)
      println("File " + (a) + " Processed Successfully.")
    }

    //println(CoreNLP.returnTriplets("Further studies will be needed to confirm these findings and to determine whether improved risk prediction models have practical value in identifying women at higher risk who would most benefit from chemo prevention, screening, and other risk-reducing strategies."))
   //println(input.collect().mkString("\n"))



  }

}

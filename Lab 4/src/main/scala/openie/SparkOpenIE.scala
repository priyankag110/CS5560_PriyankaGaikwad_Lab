package openie

import java.io.{BufferedWriter, FileWriter, PrintStream}

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.log4j.Level
import org.apache.log4j.Logger
//import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Mayanka on 27-Jun-16.
  */
object SparkOpenIE {

  def main(args: Array[String]) {
    // Configuration
    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")
    val writer= new BufferedWriter(new FileWriter("data/newAbstracts/abstract9_triplet_output.txt"));

    val sc = new SparkContext(sparkConf)

    // For Windows Users
    System.setProperty("hadoop.home.dir", "C:\\winutils")


    // Turn off Info Logger for Console
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)
    val subjects: List[String] = List()
    val inputSubject = sc.wholeTextFiles("data/newAbstracts",minPartitions=20)
      .map(line => {
        //Getting OpenIE Form of the word using lda.CoreNLP
        var line2=line._2.replaceAll("[,]"," ")
        val t=CoreNLP.returnTriplets(line2,"S")

        t
      }).collect( )

    val out =new PrintStream("SubjectsList.txt")
    inputSubject.foreach(f=>{out.println(f.mkString("\n"))})
    out.close()

    val predicates: List[String] = List()
    val inputPredicate = sc.wholeTextFiles("data/newAbstracts",minPartitions=20)
      .map(line => {
        //Getting OpenIE Form of the word using lda.CoreNLP
        var line2=line._2.replaceAll("[,]"," ")
        val t=CoreNLP.returnTriplets(line2,"P")

        t
      }).collect( )

    val out1 =new PrintStream("PredicatesList.txt")
    inputPredicate.foreach(f=>{out1.println(f.mkString("\n"))})
    out1.close()

    val Objects: List[String] = List()
    val inputobject = sc.wholeTextFiles("data/newAbstracts",minPartitions=20)
      .map(line => {
        //Getting OpenIE Form of the word using lda.CoreNLP
        var line2=line._2.replaceAll("[,]"," ")
        val t=CoreNLP.returnTriplets(line2,"O")

        t
      }).collect( )

    val out2 =new PrintStream("ObjectsList.txt")
    inputobject.foreach(f=>{out2.println(f.mkString("\n"))})
    out2.close()
    // println(CoreNLP.returnTriplets("The dog has a lifespan of upto 10-12 years."))
    //println(input.collect().mkString("\n"))
    //writer.append(input.collect().mkString("\n"))
    val AllSPO: List[String] = List()
    val allSPOobject = sc.wholeTextFiles("data/newAbstracts",minPartitions=20)
      .map(line => {
        //Getting OpenIE Form of the word using lda.CoreNLP
        var line2=line._2.replaceAll("[,]"," ")
        val t=CoreNLP.returnTriplets(line2,"")

        t
      }).collect( )

    val out3 =new PrintStream("AllSPOList.txt")
    allSPOobject.foreach(f=>{out3.println(f.mkString("\n"))})
    out3.close()


  }

}

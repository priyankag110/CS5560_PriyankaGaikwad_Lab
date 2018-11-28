package NCBO_BioNLP

import java.io.{BufferedWriter, File, FileWriter}

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer



object NCBO_medWords {

  def main(args: Array[String]) {

    System.setProperty("hadoop.home.dir","C:\\Users\\Shawn\\Downloads\\winutils")

    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")
      .set("spark.executor.memory", "4g").set("spark.driver.memory", "2g")

    val sc=new SparkContext(sparkConf)

    val inputf = sc.wholeTextFiles("data/" +  "ex.pmid", 20)
    val input = inputf.map(abs => {
      abs._2
    }).cache()

    // val input=sc.textFile("input", 10)

    val sparkIDs=input.flatMap(ids=> {ids.split("[\r\n]+")}).map(id => {
      val list = RESTClientGet.getMedWords(id).mkString("")
      val medicalWordsWriter = new BufferedWriter(new FileWriter("data/medicalwordsdata/medWords_" + id + ".txt"))
      medicalWordsWriter.append(list)
      medicalWordsWriter.close()
      list
    })

    sparkIDs.saveAsTextFile("output/medWords")

    val o=sparkIDs.collect()

    val allMedicalWordsWriter = new BufferedWriter(new FileWriter("data/medicalwordsdata/allMedWords.txt"))
    val uniqueMedicalWordsWriter = new BufferedWriter(new FileWriter("data/medicalwordsdata/allUniqueMedWords.txt"))
    val uniqueMedWords = new mutable.HashSet[String]()

    o.foreach(list => {
      allMedicalWordsWriter.append(list)
      val splitList = list.split("\n")
      splitList.foreach(word => {
        uniqueMedWords.add(word)
      })

    })
    allMedicalWordsWriter.close()

    for (word <- uniqueMedWords) {
      uniqueMedicalWordsWriter.append(word).append("\n")
    }
    uniqueMedicalWordsWriter.close()
  }

}
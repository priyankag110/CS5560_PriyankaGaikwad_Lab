package grouping
import java.io.{BufferedWriter, FileWriter}

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable
object Med_Grouping {

  def main(args: Array[String]) {

    // For Windows Users
    System.setProperty("hadoop.home.dir","C:\\Users\\Shawn\\Downloads\\winutils")

    // Configuration
    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")

    val sc = new SparkContext(sparkConf)

    val uniqueMedWordsFile = sc.textFile("data/medicalwordsdata/allUniqueMedWords.txt")

    val uniqueMedWordsTuple = uniqueMedWordsFile.map(line => {
      val splitLine = line.split("\t")
      (splitLine(0), splitLine(1))
    })

    val uniqueMedWordsTupleBroadCast = sc.broadcast(uniqueMedWordsTuple.collectAsMap())

    val objectsCategorized = sc.textFile("ObjectsList.txt").map(line => {
      val categorized = new mutable.StringBuilder(line)
      val tuple = uniqueMedWordsTupleBroadCast.value
      val result1 = line.split('_')
      var myVar = 0
      for (v <- result1) {
        if(tuple.keySet.contains(v)) {
          myVar = 1
        }
      }
      if(myVar > 0) {
        for (v <- result1) {
          if (tuple.keySet.contains(v)) {
            categorized.append("\t").append(tuple(v))
          }
        }
      }
      else
      {
        categorized.append("\t").append("Other")
      }
      categorized
    })

    val subjectsCategorized = sc.textFile("SubjectsList.txt").map(line => {
      val categorized = new mutable.StringBuilder(line)
      val tuple = uniqueMedWordsTupleBroadCast.value
      val result1 = line.split('_')
      var myVar = 0
      for (v <- result1) {
        if(tuple.keySet.contains(v)) {
          myVar = 1
        }
      }
      if(myVar > 0) {
        for (v <- result1) {
          if (tuple.keySet.contains(v)) {
            categorized.append("\t").append(tuple(v))
          }
        }
      }
      else
      {
        categorized.append("\t").append("Other")
      }
      categorized
    })

    val predicatesCategorized = sc.textFile("PredicatesList.txt").map(line => {
      val categorized = new mutable.StringBuilder(line)
      val tuple = uniqueMedWordsTupleBroadCast.value
      val result1 = line.split('_')
      var myVar = 0
      for (v <- result1) {
        if(tuple.keySet.contains(v)) {
          myVar = 1
        }
      }
      if(myVar > 0) {
        for (v <- result1) {
          if (tuple.keySet.contains(v)) {
            categorized.append("\t").append(tuple(v))
          }
        }
      }
      else
      {
        categorized.append("\t").append("Other")
      }
      categorized
    })


    val subjectCategorizedWriter = new BufferedWriter(new FileWriter("data/ontology/categorizedSubjects.txt"))
    val objectsCategorizedWriter = new BufferedWriter(new FileWriter("data/ontology/categorizedObjects.txt"))
    val predicatesCategorizedWriter = new BufferedWriter(new FileWriter("data/ontology/categorizedPredicates.txt"))

    subjectsCategorized.collect().foreach(subject => {
      subjectCategorizedWriter.append(subject).append("\n")
    })
    subjectCategorizedWriter.close()

    objectsCategorized.collect().foreach(subject => {
      objectsCategorizedWriter.append(subject).append("\n")
    })
    objectsCategorizedWriter.close()

    predicatesCategorized.collect().foreach(subject => {
      predicatesCategorizedWriter.append(subject).append("\n")
    })
    predicatesCategorizedWriter.close()
  }


}

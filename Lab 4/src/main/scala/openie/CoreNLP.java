package openie;

import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.util.Quadruple;

import java.util.Collection;
import java.util.Iterator;
import java.util.*;

public class CoreNLP {
    public static String[] returnTriplets(String sentence, String tripletElement) {

        Document doc = new Document(sentence);
        String triplets="";
        String new1="";
        String new2="",new3="";
        HashSet<String> ListItems = new HashSet<String>();
        for (Sentence sent : doc.sentences()) {  // Will iterate over two sentences

            Iterator<Quadruple<String, String, String, Double>> openIETripletList=sent.openie().iterator();
            int noOfTriplets=0;
            while(openIETripletList.hasNext()) {
                //triplets+= l.toString();
                Quadruple<String, String, String, Double> singleTriplet= openIETripletList.next();
                String subject=singleTriplet.first;
                String predicate=singleTriplet.second;
                String object=singleTriplet.third;
                switch (tripletElement)
                {
                    case "S":
                        ListItems.add(subject.replaceAll(" ", "_"));
                        break;
                    case "P":
                        ListItems.add(predicate.replaceAll(" ", "_"));
                        break;
                    case "O":
                        ListItems.add(object.replaceAll(" ", "_"));
                        break;
                    default:
                        ListItems.add(subject.replaceAll(" ", "_") + "," + predicate.replaceAll(" ", "_") + "," + object.replaceAll(" ", "_"));
                        break;
                }
                //you can use this if you want subject to be printed in next cell subject+","+predicate","+object+",";
                noOfTriplets++;

            }

            HashSet ref = new HashSet( ListItems ); // create a HashSet
            Iterator i = ref.iterator(); // get iterator

            while ( i.hasNext() )
                triplets += i.next()+ "\n";;

            System.out.println("Count:"+noOfTriplets +"\n");
        }
        /**
         *  Triplet Results
         *  sentence, subject;predicate;object\n subject;predicate;object \n , No. Of Triplets
         */
        String[] arr = ListItems.toArray(new String[ListItems.size()]);
        return arr;
    }

}
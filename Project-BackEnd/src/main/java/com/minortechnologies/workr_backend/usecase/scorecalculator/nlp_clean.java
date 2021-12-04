package com.minortechnologies.workr_backend.usecase.scorecalculator;


import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import edu.stanford.nlp.trees.Tree;

import java.util.*;
import java.util.regex.Pattern;
import java.util.Scanner;

class BasicPipelineExample {

    public static String text = "Assisted approximately 100 undergraduate Commerce students select and enroll in courses" +
            " for upcoming semester, helping ensure students enrolled in courses required for graduation while building" +
            " a well-rounded knowledge base as per their career goals";

    static boolean is_ok(Object[] arr){
        if(arr.length >=2){
            if( (arr[arr.length - 2].equals("NP") || arr[arr.length - 2].equals("VP"))
                    && (arr[arr.length - 1].toString().startsWith("NN")  || arr[arr.length - 1].toString().startsWith("VB"))
            && Arrays.stream(arr).noneMatch("SBAR" :: equals))
                return true;
            return false;
        }
        return false;
    }

    public static void scraping(HashMap<String, Object[]> arr){
        for(String item: arr.keySet()){
            Object[] stack = arr.get(item);
            if(is_ok(stack))
                System.out.println(item);
        }

    }
    public static void tree(){
        String s = "(ROOT (NP (NP (NP (VBN Developed) (NN content)) (PP (IN for) (NP (NP (DT a) (JJ new) (NNP Facebook) (NN page)) (, ,) (SBAR (WHNP (WDT which)) (S (VP (VBD resulted) (PP (IN in) (NP (DT a) (JJ strong) (NN sign) (HYPH -) (NN up))))))))) (PP (IN for) (NP (NNS events))) (. .)))";
        String[] words = s.split(" ");
        Stack<String> arr = new Stack<>();
        HashMap<String, Object[]> mapping = new HashMap<>();
        for(String item: words){
            if(item.charAt(0) == '('){
                arr.push(item.substring(1));
            }
            else{
                int index = item.indexOf(")");
                String word = item.substring(0,index);
                System.out.println(word+" "+ arr);
                mapping.put(word, arr.toArray());
                for(int i = 0;i<item.length() - word.length();i++)
                    arr.pop();
            }

        }
        scraping(mapping);

    }






    public static void main(String[] args) {
//          Scanner sc = new Scanner(System.in);
//        // set up pipeline properties
//        Properties props = new Properties();
//        // set the list of annotators to run
//        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,depparse,coref,kbp,quote");
//        // set a property for an annotator, in this case the coref annotator is being set to use the neural algorithm
//        props.setProperty("coref.algorithm", "neural");
//        // build pipeline
//        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
//        // create a document object
//        CoreDocument document = new CoreDocument(text);
//        // annnotate the document
//        pipeline.annotate(document);
//        // examples
//
//        // second sentence
//        CoreSentence sentence = document.sentences().get(0);
//
//
//        // constituency parse for the second sentence
//        Tree constituencyParse = sentence.constituencyParse();
//        System.out.println("Example: constituency parse");
//        System.out.println(constituencyParse.toString());
          tree();

//        String[] arr = text.split(" ");
//        for(String item: arr) {
//            if(item.equals(" ") || Pattern.matches("\\p{Punct}", item))
//                continue;
////            constituencyParse.get
//        }
//
//        Set<Constituent> treeConstituents = constituencyParse.constituents(new LabeledScoredConstituentFactory());
//        for (Constituent constituent : treeConstituents) {
//            if (constituent.label() != null &&
//                    (constituent.label().toString().startsWith("ADJ"))) {
//                System.err.println("found constituent: " + constituent.toString());
//                System.err.println(constituencyParse.getLeaves().subList(constituent.start(), constituent.end() + 1));
//           }

    }
}


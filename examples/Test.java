import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import edu.hit.ir.ltp4j.SplitSentence;
import edu.hit.ir.ltp4j.Segmentor;
import edu.hit.ir.ltp4j.Postagger;
import edu.hit.ir.ltp4j.NER;
import edu.hit.ir.ltp4j.Parser;
import edu.hit.ir.ltp4j.SRL;
import edu.hit.ir.ltp4j.Pair;

public class Test {
  private String segmentModel;
  private String postagModel;
  private String NERModel;
  private String parserModel;
  private String SRLModel;

  private boolean ParseArguments(String[] args) {
    if (args.length == 1 && (args[0].equals("--help") || args[0].equals("-h"))) {
      Usage();
      return false;
    }

    for (int i = 0; i < args.length; ++ i) {
      if (args[i].startsWith("--segment-model=")) {
        segmentModel = args[i].split("=")[1];
      } else if (args[i].startsWith("--postag-model=")) {
        postagModel = args[i].split("=")[1];
      } else if (args[i].startsWith("--ner-model=")) {
        NERModel = args[i].split("=")[1];
      } else if (args[i].startsWith("--parser-model=")) {
        parserModel = args[i].split("=")[1];
      } else if (args[i].startsWith("--srl-model=")) {
        SRLModel = args[i].split("=")[1];
      } else {
        throw new IllegalArgumentException("Unknown options " + args[i]);
      }
    }

    if (segmentModel == null || postagModel == null || NERModel == null ||
        parserModel == null || SRLModel == null) {
      Usage();
      throw new IllegalArgumentException("");
    }

    Segmentor.create(segmentModel);
    Postagger.create(postagModel);
    NER.create(NERModel);
    Parser.create(parserModel);
    SRL.create(SRLModel);

    return true;
  }


  public void Usage() {
    System.err.println("An command line example for ltp4j - The Java embedding of LTP");
    System.err.println("Sentences are inputted from stdin.");
    System.err.println("");
    System.err.println("Usage:");
    System.err.println("");
    System.err.println("  java -cp <jar-path> --segment-model=<path> \\");
    System.err.println("                      --postag-model=<path> \\");
    System.err.println("                      --ner-model=<path> \\");
    System.err.println("                      --parser-model=<path> \\");
    System.err.println("                      --srl-model=<path>");
  }


  private String join(ArrayList<String> payload, String conjunction) {
    StringBuilder sb = new StringBuilder();
    if (payload == null || payload.size() == 0) {
      return "";
    }
    sb.append(payload.get(0));
    for (int i = 1; i < payload.size(); ++ i) {
      sb.append(conjunction).append(payload.get(i));
    }
    return sb.toString();
  }


  public void Analyse(String sent) {
    ArrayList<String> sents = new ArrayList<String>();
    SplitSentence.splitSentence(sent,sents);

    // System.out.println("sents:"+sents.size());

    for(int m = 0; m < sents.size(); m++) {
      ArrayList<String> words = new ArrayList<String>();
      ArrayList<String> postags = new ArrayList<String>();
      ArrayList<String> ners = new ArrayList<String>();
      ArrayList<Integer> heads = new ArrayList<Integer>();
      ArrayList<String> deprels = new ArrayList<String>();
      List<Pair<Integer, List<Pair<String, Pair<Integer, Integer>>>>> srls =
        new ArrayList<Pair<Integer, List<Pair<String, Pair<Integer, Integer>>>>>();

      System.out.println("#" + (m+1));
      System.out.println("Sentence       : " + sents.get(m));

      Segmentor.segment(sents.get(m), words);
      System.out.println("Segment Result : " + join(words, "\t"));

      Postagger.postag(words,postags);
      System.out.println("Postag Result  : " + join(postags, "\t"));

      NER.recognize(words,postags,ners);
      System.out.println("NER Result     : " + join(ners, "\t"));

      Parser.parse(words,postags,heads,deprels);
      int size = heads.size();
      StringBuilder sb = new StringBuilder();
      sb.append(heads.get(0)).append(":").append(deprels.get(0));
      for(int i = 1; i < heads.size(); i++) {
        sb.append("\t").append(heads.get(i)).append(":").append(deprels.get(i));
      }
      System.out.println("Parse Result   : " + sb.toString());

      for (int i = 0; i < heads.size(); i++) {
        heads.set(i, heads.get(i) - 1);
      }

      SRL.srl(words,postags,heads,deprels,srls);
      size = srls.size();
      System.out.print("SRL Result     : ");
      if (size == 0) {
        System.out.println();
      }
      for (int i = 0; i < srls.size(); i++) {
        System.out.print(srls.get(i).first + " ->");
        for (int j = 0; j < srls.get(i).second.size(); j++) {
          System.out.print(srls.get(i).second.get(j).first
              + ": beg = " + srls.get(i).second.get(j).second.first
              + " end = " + srls.get(i).second.get(j).second.second + ";");
        }
        System.out.println();
      }
    }
  }

  public static void release(){
    Segmentor.release();
    Postagger.release();
    NER.release();
    Parser.release();
    SRL.release();
  }

  public static void main(String[] args) {
    Test test = new Test();

    try {
      if (!test.ParseArguments(args)) {
        return;
      }

      Scanner input = new Scanner(System.in);
      String sent;
      try {
        while((sent = input.nextLine())!=null){
          if(sent.length()>0){
            test.Analyse(sent);
          }
        }
      } catch(Exception e) {
        release();
      }
    } catch (IllegalArgumentException e) {
    }
  }
}

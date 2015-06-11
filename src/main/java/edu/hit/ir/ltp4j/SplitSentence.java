package edu.hit.ir.ltp4j;
import java.util.List;

public class SplitSentence{
  static {
    NarSystem.loadLibrary();
  }

  public final native void splitSentence(String sent,List<String> sents);
}

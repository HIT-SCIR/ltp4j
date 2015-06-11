package edu.hit.ir.ltp4j;
import java.util.List;

public class NER {
  static {
    NarSystem.loadLibrary();
  }

  public final native int create(String modelPath);
  public final native int recognize(List<String> words,
      List<String> postags, List<String> ners);
  public final native void release();
}


package edu.hit.ir.ltp4j;
import java.util.List;

public class Postagger {
  static {
    NarSystem.loadLibrary();
  }

  public final native int create(String modelPath);
  public final native int create(String modelPath, String lexiconPath);
  public final native int postag(List<String> words,
      List<String> tags);
  public final native void release();
}


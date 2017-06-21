package edu.hit.ir.ltp4j;
import java.util.List;

public class Segmentor {
  static {
    NarSystem.loadLibrary();
  }

  public final native int create(String modelPath);
  public final native int create(String modelPath, String lexiconPath);
  public final native int segment(String sent, List<String> words);
  public final native void release();
}


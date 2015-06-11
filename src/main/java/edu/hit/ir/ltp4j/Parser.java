package edu.hit.ir.ltp4j;
import java.util.List;

public class Parser {
  static {
    NarSystem.loadLibrary();
  }

  public final native int create(String modelPath);
  public final native int parse(List<String> words,
      List<String> tags, List<Integer> heads,
      List<String> deprels);
  public final native void release();
}


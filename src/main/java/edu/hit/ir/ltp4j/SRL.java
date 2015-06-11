package edu.hit.ir.ltp4j;
import java.util.List;

public class SRL {
  static {
    NarSystem.loadLibrary();
  }

  public final native int create(String modelPath);
  public final native int srl(
      List<String> words,
      List<String> tags,
      List<String> ners,
      List<Integer> heads,
      List<String> deprels,
      List<Pair<Integer, List<Pair<String, Pair<Integer, Integer>>>>> srls);
  public final native void release();
}


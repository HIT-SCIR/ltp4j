package edu.hit.ir.ltp4j;
import java.util.List;

public class SRL {
  static {
    System.loadLibrary("srl_jni");
  }
  public static native int create(String modelPath);

  public static native int srl(
      List<String> words,
      List<String> tags,
      List<Integer> heads,
      List<String> deprels,
      List<Pair<Integer, List<Pair<String, Pair<Integer, Integer>>>>> srls);

  public static native void release();
}


package edu.hit.ir.ltp4j;
import java.util.List;

public class SDP {
  static {
    NarSystem.loadLibrary();
  }

  public final native int create(String modelPath);
  public final native int parse(
          List<String> words,
          List<String> tags,
          List<List<Pair<Integer, String>>> sdps);

  public final native void release();
}

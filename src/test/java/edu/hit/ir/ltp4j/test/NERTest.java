package edu.hit.ir.ltp4j.test;

import edu.hit.ir.ltp4j.NER;
import java.util.List;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

public class NERTest {
  @Test public final void testNERCreate() 
    throws Exception
  {
    NER app = new NER();
    Assert.assertEquals( 1, app.create("ltp_data/ner.model") );
  }
  
  @Test public final void testNERRecognize()
    throws Exception
  {
    NER app = new NER();
    app.create("ltp_data/ner.model");
    
    List<String> words = new ArrayList<String>();
    List<String> tags = new ArrayList<String>();
    
    words.add("中国");     tags.add("ns");
    words.add("进出口");   tags.add("n");
    words.add("银行");     tags.add("n");
    words.add("与");       tags.add("p");
    words.add("中国");     tags.add("ns");
    words.add("银行");     tags.add("n");
    words.add("加强");     tags.add("v");
    words.add("合作");     tags.add("v");
    
    List<String> result = new ArrayList<String>();
    app.recognize(words, tags, result);
    
    Assert.assertEquals( 8, result.size() );
    Assert.assertEquals( "B-Ni", result.get(0) );
    Assert.assertEquals( "I-Ni", result.get(1) );
    Assert.assertEquals( "E-Ni", result.get(2) );
    Assert.assertEquals( "O", result.get(3) );
    Assert.assertEquals( "B-Ni", result.get(4) );
    Assert.assertEquals( "E-Ni", result.get(5) );
    Assert.assertEquals( "O", result.get(6) );
    Assert.assertEquals( "O", result.get(7) );
  }
}

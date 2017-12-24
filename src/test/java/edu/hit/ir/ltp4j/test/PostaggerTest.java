package edu.hit.ir.ltp4j.test;

import edu.hit.ir.ltp4j.Postagger;
import java.util.List;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

public class PostaggerTest {
  @Test public final void testPostaggerCreate() 
    throws Exception
  {
    Postagger app = new Postagger();
    Assert.assertEquals( 1, app.create("ltp_data/pos.model") );
  }

  @Test public final void testPostaggerPostag() 
    throws Exception
  {
    Postagger app = new Postagger();
    app.create("ltp_data/pos.model");

    List<String> words = new ArrayList<String>();

    words.add("中国");
    words.add("进出口");
    words.add("银行");
    words.add("与");
    words.add("中国");
    words.add("银行");
    words.add("加强");
    words.add("合作");

    List<String> result = new ArrayList<String>();

    app.postag(words, result);
    Assert.assertEquals( 8, result.size() );
    Assert.assertEquals( "ns", result.get(0) );
    Assert.assertEquals( "n", result.get(1) );
    Assert.assertEquals( "n", result.get(2) );
    Assert.assertEquals( "p", result.get(3) );
    Assert.assertEquals( "ns", result.get(4) );
    Assert.assertEquals( "n", result.get(5) );
    Assert.assertEquals( "v", result.get(6) );
    Assert.assertEquals( "v", result.get(7) );
  }
}

package edu.hit.ir.ltp4j.test;

import edu.hit.ir.ltp4j.NER;
import org.junit.Assert;
import org.junit.Test;

public class NERTest {
  @Test public final void testNERCreate() 
    throws Exception
  {
    NER app = new NER();
    Assert.assertEquals( 1, app.create("/home/yjliu/work/ltp/ltp/ltp_data/ner.model") );
  }
}

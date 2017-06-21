package edu.hit.ir.ltp4j.test;

import edu.hit.ir.ltp4j.Segmentor;
import java.util.List;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

public class SegmentorTest {
  @Test public final void testSegmentorCreate() 
    throws Exception
  {
    Segmentor app = new Segmentor();
    Assert.assertEquals( 1, app.create("ltp_data/cws.model") );
  }

  @Test public final void testSegmentorSegment()
    throws Exception
  {
    Segmentor app = new Segmentor();
    app.create("ltp_data/cws.model");

    List<String> result = new ArrayList<String>();
    app.segment("中国进出口银行与中国银行加强合作", result);

    Assert.assertEquals( 8, result.size() );
    Assert.assertEquals( "中国", result.get(0) );
    Assert.assertEquals( "进出口", result.get(1) );
    Assert.assertEquals( "银行", result.get(2) );
    Assert.assertEquals( "与", result.get(3) );
    Assert.assertEquals( "中国", result.get(4) );
    Assert.assertEquals( "银行", result.get(5) );
    Assert.assertEquals( "加强", result.get(6) );
    Assert.assertEquals( "合作", result.get(7) );
  }
}

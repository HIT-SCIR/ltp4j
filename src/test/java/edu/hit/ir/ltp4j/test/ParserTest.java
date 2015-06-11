package edu.hit.ir.ltp4j.test;

import edu.hit.ir.ltp4j.Parser;
import org.junit.Assert;
import org.junit.Test;

public class ParserTest {
  @Test public final void testParserCreate() 
    throws Exception
  {
    Parser app = new Parser();
    Assert.assertEquals( 1, app.create("/home/yjliu/work/ltp/ltp/ltp_data/parser.model") );
  }
}

package edu.hit.ir.ltp4j.test;

import edu.hit.ir.ltp4j.Parser;
import java.util.List;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

public class ParserTest {
  @Test public final void testParserCreate() 
    throws Exception
  {
    Parser app = new Parser();
    Assert.assertEquals( 1, app.create("ltp_data/parser.model") );
  }
  
  @Test public final void testParserParse()
    throws Exception
  {
    Parser app = new Parser();
    app.create("ltp_data/parser.model");
    
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

    List<Integer> heads = new ArrayList<Integer>();
    List<String> deprels = new ArrayList<String>();
       
    app.parse(words, tags, heads, deprels);

    Assert.assertEquals( 8, heads.size() );  Assert.assertEquals( 8, deprels.size() );
    Assert.assertTrue( 3 == heads.get(0) );  Assert.assertEquals( "ATT", deprels.get(0) );
    Assert.assertTrue( 3 == heads.get(1) );  Assert.assertEquals( "ATT", deprels.get(1) );
    Assert.assertTrue( 7 == heads.get(2) );  Assert.assertEquals( "SBV", deprels.get(2) );
    Assert.assertTrue( 3 == heads.get(3) );  Assert.assertEquals( "ADV", deprels.get(3) );
    Assert.assertTrue( 6 == heads.get(4) );  Assert.assertEquals( "ATT", deprels.get(4) );
    Assert.assertTrue( 4 == heads.get(5) );  Assert.assertEquals( "POB", deprels.get(5) );
    Assert.assertTrue( 0 == heads.get(6) );  Assert.assertEquals( "HED", deprels.get(6) );
    Assert.assertTrue( 7 == heads.get(7) );  Assert.assertEquals( "VOB", deprels.get(7) );
  }
}

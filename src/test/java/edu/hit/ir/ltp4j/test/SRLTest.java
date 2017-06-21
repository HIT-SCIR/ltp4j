package edu.hit.ir.ltp4j.test;

import edu.hit.ir.ltp4j.SRL;
import org.junit.Assert;
import org.junit.Test;

public class SRLTest {
  @Test public final void testSRLCreate() 
    throws Exception
  {
    SRL app = new SRL();
    Assert.assertEquals( 1, app.create("ltp_data/srl/") );
  }
}

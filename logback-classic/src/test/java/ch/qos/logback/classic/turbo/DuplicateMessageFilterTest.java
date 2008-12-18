package ch.qos.logback.classic.turbo;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.qos.logback.core.spi.FilterReply;


public class DuplicateMessageFilterTest {

  
  @Test
  public void smoke() {
    DuplicateMessageFilter dmf = new DuplicateMessageFilter();
    dmf.setAllowedRepetitions(1);
    dmf.start();
    assertEquals(FilterReply.NEUTRAL, dmf.decide(null, null, null, "x", null, null));
    assertEquals(FilterReply.NEUTRAL, dmf.decide(null, null, null, "y", null, null));
    assertEquals(FilterReply.DENY, dmf.decide(null, null, null, "x", null, null));
    assertEquals(FilterReply.DENY, dmf.decide(null, null, null, "y", null, null));
  }
  
  @Test
  public void memoryLoss() {
    DuplicateMessageFilter dmf = new DuplicateMessageFilter();
    dmf.setAllowedRepetitions(1);
    dmf.setCacheSize(1);
    dmf.start();
    assertEquals(FilterReply.NEUTRAL, dmf.decide(null, null, null, "a", null, null));
    assertEquals(FilterReply.NEUTRAL, dmf.decide(null, null, null, "b", null, null));
    assertEquals(FilterReply.NEUTRAL, dmf.decide(null, null, null, "a", null, null));
  }

  
  @Test
  public void many() {
    DuplicateMessageFilter dmf = new DuplicateMessageFilter();
    dmf.setAllowedRepetitions(1);
    int cacheSize = 10;
    int margin  = 2;
    dmf.setCacheSize(cacheSize);
    dmf.start();
    for(int i = 0; i < cacheSize+margin; i++) {
      assertEquals(FilterReply.NEUTRAL, dmf.decide(null, null, null, "a"+i, null, null));
    }
    for(int i = cacheSize-1; i >= margin; i--) {
      assertEquals(FilterReply.DENY, dmf.decide(null, null, null, "a"+i, null, null));
    }
    for(int i = margin-1; i >= 0; i--) {
      assertEquals(FilterReply.NEUTRAL, dmf.decide(null, null, null, "a"+i, null, null));
    }
  }
}

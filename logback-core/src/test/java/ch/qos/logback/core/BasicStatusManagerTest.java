package ch.qos.logback.core;

import static ch.qos.logback.core.BasicStatusManager.MAX_HEADER_COUNT;
import static ch.qos.logback.core.BasicStatusManager.TAIL_SIZE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ch.qos.logback.core.status.ErrorStatus;
import ch.qos.logback.core.status.Status;


public class BasicStatusManagerTest {

  
  BasicStatusManager bsm = new BasicStatusManager();
  
  @Test
  public void smoke() {
    bsm.add(new ErrorStatus("hello", this));
    assertEquals(Status.ERROR, bsm.getLevel());
    
    List<Status> statusList = bsm.getCopyOfStatusList();
    assertNotNull(statusList);
    assertEquals(1, statusList.size());
    assertEquals("hello", statusList.get(0).getMessage());
  }
  
  @Test
  public void many() {
    int margin = 300;
    int len = MAX_HEADER_COUNT+TAIL_SIZE+margin;
    for(int i = 0; i < len; i++) {
      bsm.add(new ErrorStatus(""+i, this));
    }
    
    List<Status> statusList = bsm.getCopyOfStatusList();
    assertNotNull(statusList);
    assertEquals(MAX_HEADER_COUNT+TAIL_SIZE, statusList.size());
    List<Status> witness = new ArrayList<Status>();
    for(int i = 0; i < MAX_HEADER_COUNT; i++) {
      witness.add(new ErrorStatus(""+i, this));
    }
    for(int i = 0; i < TAIL_SIZE; i++) {
      witness.add(new ErrorStatus(""+(MAX_HEADER_COUNT+margin+i), this));
    }
    assertEquals(witness, statusList);
  }
}

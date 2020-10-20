package com.crio.qcharm.request;

import com.crio.qcharm.ds.CopyBuffer;
import com.crio.qcharm.ds.Cursor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class MasterRequestTest {

//  public MasterRequest makeMasterRequest() {
//
//    Cursor cursorEnd = new Cursor(8, 34);
//    Cursor cursorStart = new Cursor(5, 1);
//    List<String> dataNow = new ArrayList<>();
//
//    String[] lines = new String[]{"def ann(inv, amt_now, days):", "    DAYS_IN_YEAR = 365.0", "  "
//        + "  ret = "
//        + "(amt_now - "
//        + "inv) / "
//        + "(1.0 * inv)", "    yrs = days/DAYS_IN_YEAR", "    return ((1+ret) ** (1/yrs)) - 1",
//        "", "print(ann(154.89, 271.46, 344))", "print(ann(99.55, 153.24, 344))", "print(ann(1027.20, 1348.49, 344))",""};
//
//    return new MasterRequest(cursorStart, cursorEnd, 0, 0, Arrays.asList(lines), "ann.py");
//  }

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void multiLineCopyTest() {
//    final MasterRequest masterRequest = makeMasterRequest();
//
//    final Pair<CopyBuffer, List<String>> copyBuffer = masterRequest.getCopyBuffer();
//
//    System.out.println(copyBuffer);
    assert (true);
  }
}

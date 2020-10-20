package com.crio.qcharm.ds;

import com.crio.qcharm.log.UncaughtExceptionHandler;
import com.crio.qcharm.request.EditRequest;
import com.crio.qcharm.request.PageRequest;
import com.crio.qcharm.request.SearchReplaceRequest;
import com.crio.qcharm.request.SearchRequest;
import com.crio.qcharm.request.UndoRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.apache.logging.log4j.ThreadContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

// @ExtendWith(SpringExtension.class)
class SourceFileHandlerHybridImplTest {

  @BeforeEach
  public void setupUncaughtExceptionHandler() {
    Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());
    ThreadContext.put("runId", UUID.randomUUID().toString());
  }

  static FileInfo inefficientSearch;
  static List<Cursor> expectedCursorPositions = new ArrayList<>();
  static String pattern;
  @BeforeAll
  static void setup() {

    StringBuffer prefix = new StringBuffer("");

    for (int i = 0; i < 30; ++i) {
      prefix.append("ab");
    }

    pattern = prefix.toString() + "aa";
    patternGenerator(prefix.toString() + "ab", pattern);
  }

  List<String> clone(List<String> lst) {
    return lst.stream().collect(Collectors.toList());
  }

  @AfterAll
  static void teardown() {
    inefficientSearch.getLines().clear();
  }

  static void patternGenerator(String pattern1, String pattern2) {
    StringBuffer buffer1 = new StringBuffer("");
    StringBuffer buffer2 = new StringBuffer("");

    int K = 250;
    for (int i = 0; i < K; ++i) {
      buffer1.append(pattern1);
    }
    buffer1.append(pattern2);

    for (int  i = 0; i < K; ++i) {
      buffer1.append(pattern1);
    }

    List<String> lines = new ArrayList<>();

    int N = 1000;

    String s1 = buffer1.toString();
    for (int i = 0; i < N; ++i) {
      lines.add(s1 + new Integer(i).toString() );
    }

    int len1 = pattern1.length();
    for (int i = 0; i < N; ++i) {
      expectedCursorPositions.add(new Cursor(i, K * len1));
    }

    inefficientSearch =  new FileInfo("testfile", lines);
  }

  FileInfo getLargeSampleFileInfo(String fileName, int n) {
    List<String> testLines = new ArrayList<>();

    for (int i = 0; i < n; ++i) {
      StringBuffer buffer = new StringBuffer("lineno");
      buffer.append(i);
      testLines.add(buffer.toString());
    }
    return new FileInfo(fileName, testLines);
  }

  FileInfo getSampleFileInfo() {
    List<String> testLines = new ArrayList<>();
    testLines.add("def sqr(x):");
    testLines.add(" return x * x");

    return new FileInfo("testfile.txt", testLines);
  }

  @Test
  @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
  void smallFileLoadingReturnsAllLines() {
    SourceFileHandler sourceFileHandlerHybrid = getSourceFileHandler("testfile");

    FileInfo fileInfo = getSampleFileInfo();
    Page page = sourceFileHandlerHybrid.loadFile(fileInfo);

    assertEquals(fileInfo.getLines(), page.getLines());
  }

  private SourceFileHandler getSourceFileHandler(String testfile) {
    return new SourceFileHandlerHybridImpl(testfile);
  }

  @Test
  @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
  void largeFileLoadingReturnsFiftyLinesOfData() {
    SourceFileHandler sourceFileHandlerHybrid = getSourceFileHandler("testfile");

    FileInfo fileInfo = getLargeSampleFileInfo("largeFile", 1000000);
    Page page = sourceFileHandlerHybrid.loadFile(fileInfo);
    assertEquals(fileInfo.getLines().subList(0, 50), page.getLines());
    assertEquals(0, page.getStartingLineNo());
    assertEquals(new Cursor(0,0), page.getCursorAt());
  }

  @Test
  @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
  void getNextLinesReturnsEmptyPageIfThereIsNoLinesAfter() {
    String fileName = "testfile";
    SourceFileHandler sourceFileHandlerHybrid = getSourceFileHandler(fileName);

    FileInfo fileInfo = getLargeSampleFileInfo(fileName, 100);
    sourceFileHandlerHybrid.loadFile(fileInfo);

    int length = 35;
    int startingLineNo = 100;
    Cursor cursor = new Cursor(0, 0);
    PageRequest pageRequest = new PageRequest(startingLineNo, fileName, length, cursor);
    Page emptyPage = sourceFileHandlerHybrid.getNextLines(pageRequest);

    assertEquals(new ArrayList<String>(), emptyPage.getLines());
    assertEquals(startingLineNo, emptyPage.getStartingLineNo());
    assertEquals(cursor, emptyPage.getCursorAt());
  }

  @Test
  @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
  void getNextLinesReturnsLessThanRequestedNumberOfLines() {
    String fileName = "testfile";
    SourceFileHandler sourceFileHandlerHybrid = getSourceFileHandler(fileName);

    FileInfo fileInfo = getLargeSampleFileInfo(fileName, 100);
    sourceFileHandlerHybrid.loadFile(fileInfo);

    int length = 35;
    int startingLine = 90;

    Cursor cursor = new Cursor(0, 0);
    PageRequest pageRequest = new PageRequest(startingLine, fileName, length, cursor);
    Page page = sourceFileHandlerHybrid.getNextLines(pageRequest);

    assertEquals(fileInfo.getLines().subList(startingLine+1, 100), page.getLines());
    assertEquals(startingLine+1, page.getStartingLineNo());
    assertEquals(cursor, page.getCursorAt());
  }

  @Test
  @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
  void getPrevLinesReturnsRequestedNumberOfLines() {
    String fileName = "testfile";
    SourceFileHandler sourceFileHandlerHybrid = getSourceFileHandler(fileName);

    FileInfo fileInfo = getLargeSampleFileInfo(fileName, 100);
    sourceFileHandlerHybrid.loadFile(fileInfo);

    int length = 35;
    int startingLine = 35;

    Cursor cursor = new Cursor(0, 0);
    PageRequest pageRequest = new PageRequest(startingLine, fileName, length, cursor);
    Page page = sourceFileHandlerHybrid.getPrevLines(pageRequest);

    assertEquals(fileInfo.getLines().subList(0, length), page.getLines());
    assertEquals(0, page.getStartingLineNo());
    assertEquals(cursor, page.getCursorAt());
  }

  @Test
  @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
  void getPrevLinesReturnsEmptyPageIfThereIsNoLinesBefore() {
    String fileName = "testfile";
    SourceFileHandler sourceFileHandlerHybrid = getSourceFileHandler(fileName);

    FileInfo fileInfo = getLargeSampleFileInfo(fileName, 100);
    sourceFileHandlerHybrid.loadFile(fileInfo);

    int length = 35;
    Cursor cursor = new Cursor(0, 0);
    PageRequest pageRequest = new PageRequest(0, fileName, length, cursor);
    Page emptyPage = sourceFileHandlerHybrid.getPrevLines(pageRequest);

    assertEquals(new ArrayList<String>(), emptyPage.getLines());
  }

  @Test
  @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
  void getPrevLinesReturnsLessThanRequestedNumberOfLines() {
    String fileName = "testfile";
    SourceFileHandler sourceFileHandlerHybrid = getSourceFileHandler(fileName);

    FileInfo fileInfo = getLargeSampleFileInfo(fileName, 100);
    sourceFileHandlerHybrid.loadFile(fileInfo);

    int length = 35;
    int startingLine = 10;

    Cursor cursor = new Cursor(0, 0);
    PageRequest pageRequest = new PageRequest(startingLine, fileName, length, cursor);
    Page page = sourceFileHandlerHybrid.getPrevLines(pageRequest);

    assertEquals(fileInfo.getLines().subList(0, 10), page.getLines());
    assertEquals(0, page.getStartingLineNo());
    assertEquals(cursor, page.getCursorAt());
  }

  @Test
  @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
  void getNextLinesReturnsRequestedNumberOfLines() {
    String fileName = "testfile";
    SourceFileHandler sourceFileHandlerHybrid = getSourceFileHandler(fileName);

    FileInfo fileInfo = getLargeSampleFileInfo(fileName, 100);
    sourceFileHandlerHybrid.loadFile(fileInfo);

    int length = 35;
    int startingLine = 35;

    Cursor cursor = new Cursor(0, 0);
    PageRequest pageRequest = new PageRequest(startingLine, fileName, length, cursor);
    Page page = sourceFileHandlerHybrid.getNextLines(pageRequest);

    assertEquals(fileInfo.getLines().subList(36, 71), page.getLines());
    assertEquals(36, page.getStartingLineNo());
    assertEquals(cursor, page.getCursorAt());
  }

  @Test
  @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
  void getLinesFromReturnsRequestedNumberOfLines() {
    String fileName = "testfile";
    SourceFileHandler sourceFileHandlerHybrid = getSourceFileHandler(fileName);

    FileInfo fileInfo = getLargeSampleFileInfo(fileName, 100);
    sourceFileHandlerHybrid.loadFile(fileInfo);

    int length = 35;
    int startingLine = 10;

    Cursor cursor = new Cursor(20, 13);
    PageRequest pageRequest = new PageRequest(startingLine, fileName, length, cursor);
    Page page = sourceFileHandlerHybrid.getLinesFrom(pageRequest);

    Cursor expectedCursorPosition = new Cursor(startingLine, 0);
    assertEquals(expectedCursorPosition, page.getCursorAt());
    assertEquals(fileInfo.getLines().subList(startingLine, startingLine + length), page.getLines());
    assertEquals(10, page.getStartingLineNo());
  }

  @Test
  @Timeout(value = 20000, unit = TimeUnit.MILLISECONDS)
  void  efficientSearchTest() {
    String fileName = "efficientSearchTest";
    SourceFileHandler sourceFileHandlerHybrid = getSourceFileHandler(fileName);

    sourceFileHandlerHybrid.loadFile(inefficientSearch);
    SearchRequest searchRequest = new SearchRequest(0, pattern, fileName);
    long timeTakenInNs = 0;
    for (int i = 0; i < 10; ++i) {
      long startTime = System.nanoTime();
      List<Cursor> cursors = sourceFileHandlerHybrid.search(searchRequest);
      timeTakenInNs += System.nanoTime() - startTime;
      assertEquals(expectedCursorPositions, cursors);
    }
    System.out.printf("efficientSearchTest timetaken = %d ns\n", timeTakenInNs);
    assert (timeTakenInNs < 3000000000l);
  }




  @Test
  @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
  void search() {
    String fileName = "testfile";
    SourceFileHandler sourceFileHandlerHybrid = getSourceFileHandler(fileName);

    int N = 100;
    FileInfo fileInfo = getLargeSampleFileInfo(fileName, N);
    sourceFileHandlerHybrid.loadFile(fileInfo);

    SearchRequest searchRequest = new SearchRequest(0, "lineno", fileName);

    List<Cursor> cursors = sourceFileHandlerHybrid.search(searchRequest);
    List<Cursor> expected = new ArrayList<>();

    for (int i = 0; i < N; ++i) {
      expected.add(new Cursor(i, 0));
    }

    assertEquals(expected, cursors);
  }







}

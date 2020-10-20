package com.crio.qcharm.ds;

import com.crio.qcharm.log.UncaughtExceptionHandler;
import com.crio.qcharm.request.PageRequest;
import com.crio.qcharm.request.SearchRequest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SourceFileVersionLinkedListImplTest {

  @BeforeEach
  public void setupUncaughtExceptionHandler() {
    Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());
  }

  private FileInfo getSmallFile(String fileName) {
    List<String> lines = new ArrayList<>();
    lines.add("Hello World");
    lines.add("def square(x):");
    lines.add("    return x * x;");

    return makeFile(fileName, lines);
  }

  private FileInfo makeFile(String fileName, int n) {
    List<String> lines = new ArrayList<>();
    for (int i = 1; i <= n; ++i) {
      StringBuffer oneLine = new StringBuffer("lineno");
      oneLine.append(i);
      lines.add(oneLine.toString());
    }
    return new FileInfo(fileName, lines);
  }

  private FileInfo makeFile(String fileName, List<String> lines) {
    return new FileInfo(fileName, lines);
  }

  @Test
  void getAllLines() {
    FileInfo fileInfo = makeFile("getAllLines", 73);

    SourceFileVersion SourceFileVersion =
        new SourceFileVersionArrayListImpl(fileInfo);

    List<String> linesReceived = SourceFileVersion.getAllLines();

    assertEquals(fileInfo.getLines(), linesReceived);
  }

  @Test
  void getLinesBefore() {
    String fileName = "getLinesBefore1";
    FileInfo fileInfo = makeFile(fileName, 73);

    SourceFileVersion SourceFileVersion =
        new SourceFileVersionArrayListImpl(fileInfo);

    Cursor cursorAt = new Cursor(10, 0);
    PageRequest pageRequest = new PageRequest(10, fileName, 25, cursorAt);

    Page linesBefore = SourceFileVersion.getLinesBefore(pageRequest);

    assertEquals(fileInfo.getLines().subList(0, 10), linesBefore.getLines());
    assertEquals(cursorAt, linesBefore.getCursorAt());
  }

  @Test
  void getLinesBeforeLineZero() {
    String fileName = "getLinesBefore1";
    FileInfo fileInfo = makeFile(fileName, 73);

    SourceFileVersion SourceFileVersion =
        new SourceFileVersionArrayListImpl(fileInfo);

    Cursor cursorAt = new Cursor(0, 0);
    PageRequest pageRequest = new PageRequest(0, fileName, 25, cursorAt);
    Page linesBefore = SourceFileVersion.getLinesBefore(pageRequest);

    assertEquals(0, linesBefore.getLines().size());
    assertEquals(cursorAt, linesBefore.getCursorAt());
  }

  @Test
  void getLinesAfter() {
    String fileName = "getLinesAfter1";
    FileInfo fileInfo = makeFile(fileName, 73);

    SourceFileVersion SourceFileVersion =
        new SourceFileVersionArrayListImpl(fileInfo);

    Cursor cursorAt = new Cursor(10, 10);
    PageRequest pageRequest = new PageRequest(10, fileName, 25, cursorAt);
    Page linesBefore = SourceFileVersion.getLinesAfter(pageRequest);

    assertEquals(fileInfo.getLines().subList(11, 11+25), linesBefore.getLines());
    assertEquals(cursorAt, linesBefore.getCursorAt());
  }

  @Test
  void getLinesWhenStartIsCloseToFileEnd() {
    String fileName = "getLinesFrom1";
    FileInfo fileInfo = makeFile(fileName, 73);

    SourceFileVersion SourceFileVersion =
        new SourceFileVersionArrayListImpl(fileInfo);

    Cursor cursorAt = new Cursor(60, 0);
    PageRequest pageRequest = new PageRequest(60, fileName, 25, cursorAt);
    Page linesFrom = SourceFileVersion.getLinesFrom(pageRequest);

    assertEquals(fileInfo.getLines().subList(60, 73), linesFrom.getLines());
    assertEquals(cursorAt, linesFrom.getCursorAt());
  }

  @Test
  void getLinesFromGetsExactlyTheSameNumberOfLines() {
    String fileName = "getLinesFrom1";
    FileInfo fileInfo = makeFile(fileName, 73);

    SourceFileVersion SourceFileVersion =
        new SourceFileVersionArrayListImpl(fileInfo);

    Cursor cursorAt = new Cursor(10, 0);
    PageRequest pageRequest = new PageRequest(10, fileName, 25, cursorAt);
    Page linesFrom = SourceFileVersion.getLinesFrom(pageRequest);

    assertEquals(fileInfo.getLines().subList(10, 10+25), linesFrom.getLines());
    assertEquals(cursorAt, linesFrom.getCursorAt());
  }

  @Test
  void getCursorsWithMatch() {
    String fileName = "getCursors1";
    FileInfo fileInfo = getSmallFile(fileName);

    SourceFileVersion SourceFileVersion =
        new SourceFileVersionArrayListImpl(fileInfo);

    String pattern = "Hello";
    final SearchRequest searchRequest = new SearchRequest(0, pattern, fileName);
    List<Cursor> occurrences = SourceFileVersion.getCursors(searchRequest);

    assertEquals(1, occurrences.size());
    assertEquals(new Cursor(0, 0), occurrences.get(0));
  }

  @Test
  void getCursorsWithNoMatch() {
    String fileName = "getCursors2";
    FileInfo fileInfo = getSmallFile(fileName);

    SourceFileVersion SourceFileVersion =
        new SourceFileVersionArrayListImpl(fileInfo);

    String pattern = "hello";
    final SearchRequest searchRequest = new SearchRequest(0, pattern, fileName);
    List<Cursor> occurrences = SourceFileVersion.getCursors(searchRequest);

    assertEquals(0, occurrences.size());
  }

  @Test
  void getCursorsWithMultipleMatches() {
    String fileName = "getCursors3";
    FileInfo fileInfo = getSmallFile(fileName);

    SourceFileVersion SourceFileVersion =
        new SourceFileVersionArrayListImpl(fileInfo);

    String pattern = "x";
    final SearchRequest searchRequest = new SearchRequest(0, pattern, fileName);
    List<Cursor> occurrences = SourceFileVersion.getCursors(searchRequest);

    assertEquals(3, occurrences.size());
    assertEquals(new Cursor(1,11), occurrences.get(0));
    assertEquals(new Cursor(2,11), occurrences.get(1));
    assertEquals(new Cursor(2,15), occurrences.get(2));
  }

  @Test
  void apply() {
    String fileName = "apply1";
    FileInfo fileInfo = getSmallFile(fileName);

    List<String> lines = new ArrayList<>();
    lines.add("def newSquareFunction(var)");
    lines.add("    return var * var;");


    Cursor cursor = new Cursor(1,0);

    UpdateLines updateLines = new UpdateLines(1, 2, lines, cursor);

    SourceFileVersion lastOp =
        new SourceFileVersionArrayListImpl(fileInfo);


    assertEquals(fileInfo.getLines(), lastOp.getAllLines());

    List<Edits> updateLinesList = new ArrayList<>();
    updateLinesList.add(updateLines);
    lastOp.apply(updateLinesList);

    List<String> allLines = lastOp.getAllLines();

    assertEquals(fileInfo.getLines().get(0), allLines.get(0));
    assertEquals(lines.get(0), allLines.get(1));
    assertEquals(lines.get(1), allLines.get(2));
  }
}


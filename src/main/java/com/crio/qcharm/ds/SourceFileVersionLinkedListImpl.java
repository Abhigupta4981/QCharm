package com.crio.qcharm.ds;

import com.crio.qcharm.request.PageRequest;
import com.crio.qcharm.request.SearchRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SourceFileVersionLinkedListImpl implements SourceFileVersion {

  private String fileName;
  private List<String> lines;

  // Input:
  //     FileInfo - contains following information
  //         1. fileName
  //         2. List of lines
  // Steps:
  //     You task here is to construct SourceFileVersionLinkedListImpl object by
  //     1. Storing the lines received from fileInfo object
  //     2. Storing the fileName received from fileInfo object.
  // Recommendations:
  //     1. Use Java LinkedList to store the lines received from fileInfo


  SourceFileVersionLinkedListImpl(FileInfo fileInfo) {
    this.fileName = fileName;
    this.lines = new LinkedList<>(fileInfo.getLines());
  }

  public SourceFileVersionLinkedListImpl() {
  }

  public SourceFileVersionLinkedListImpl(SourceFileVersionLinkedListImpl obj) {
    this(new FileInfo(obj.getFileName(), obj.getAllLines()));
  }


  @Override
  public SourceFileVersion apply(List<Edits> edits) {
    for (Edits oneEdit : edits) {
      if (oneEdit instanceof UpdateLines) {
        apply((UpdateLines) oneEdit);
      } else {
        assert (oneEdit instanceof SearchReplace);
        apply((SearchReplace) oneEdit);
      }
    }
    return this;
  }


  
  // Input:
  //    SearchReplace
  //          1. pattern - pattern to be found
  //          2. newPattern - pattern to be replaced with
  //  Description:
  //      Find every occurrence of the pattern and replace it newPattern.

  @Override
  public void apply(SearchReplace searchReplace) {
    String pattern = searchReplace.getPattern();
    String newPattern = searchReplace.getNewPattern();
    List<String> lines = this.getAllLines();
    List<String> res = new LinkedList<>();
    for(String s: lines) {
      String s1 = s.replace(pattern, newPattern);
      res.add(s1);
    }
    this.lines = res;
  }

  
  // Input:
  //     UpdateLines
  //        1. startingLineNo - starting line number of last time it received page from backend
  //        2. numberOfLines - number of lines received from backend last time.
  //        3. lines - present view of lines in range(startingLineNo,startingLineNo+numberOfLines)
  //        4. cursor
  // Description:
  //        1. Remove the line numbers in the range(starting line no, ending line no)
  //        2. Inserting the lines in new content starting position starting line no
  // Example:
  //        UpdateLines looks like this
  //            1. start line no - 50
  //            2. numberOfLines - 10
  //            3. lines - ["Hello world"]
  //
  //       Assume the file has 100 lines in it
  //
  //       File contents before edit:
  //       ==========================
  //       line no 0
  //       line no 1
  //       line no 2
  //          .....
  //       line no 99
  //
  //        File contents After Edit:
  //        =========================
  //        line no 0
  //        line no 1
  //        line no 2
  //        line no 3
  //         .....
  //        line no 49
  //        Hello World
  //        line no 60
  //        line no 61
  //          ....
  //        line no 99
  //

  @Override
  public void apply(UpdateLines updateLines) {
    int startingLine = updateLines.getStartingLineNo();
    int numLines = updateLines.getNumberOfLines();
    List<String> newContent = updateLines.getLines();
    List<String> lines = this.getAllLines();
    lines.subList(startingLine, startingLine + numLines).clear();
    lines.addAll(startingLine, newContent);
    this.lines = lines;
  }

  @Override
  public List<String> getAllLines() {
    return this.lines;
  }

  @Override
  public String getFileName() {
    return this.fileName;
  }

  
  // Input:
  //    1. lineNumber - The line number
  //    2. numberOfLines - Number of lines requested
  // Expected functionality:
  //    1. Get the requested number of lines starting before the given line number.
  //    2. Make page object of this and return.
  //    3. For cursor position use the value from pageRequest
  //    4. For fileName use the value from pageRequest
  // NOTE:
  //    If there less than requested number of lines, then return just those lines.
  //    Zero is the first line number of the file
  // Example:
  //    lineNumber - 50
  //    numberOfLines - 25
  //    Then lines returned is
  //    (line number 25, line number 26 ... , line number 48, line number49)
  @Override
  public Page getLinesBefore(PageRequest pageRequest) {
    int lineNumber = pageRequest.getStartingLineNo();
    int numberOfLines = pageRequest.getNumberOfLines();
    int startingLine = Math.max(0, lineNumber - numberOfLines);
    List<String> res = this.lines.subList(startingLine, lineNumber);
    Page page = new Page();
    page.setLines(res);
    page.setFileName(pageRequest.getFileName());
    page.setStartingLineNo(startingLine);
    page.setCursorAt(pageRequest.getCursorAt());
    return page;
  }

  // Input:
  //    1. lineNumber - The line number
  //    2. numberOfLines - Number of lines requested
  // Expected functionality:
  //    1. Get the requested number of lines starting after the given line number.
  //    2. Make page object of this and return.
  //    3. For cursor position use the value from pageRequest
  //    4. For fileName use the value from pageRequest
  // NOTE:
  //    If there less than requested number of lines, then return just those lines.
  //    Zero is the first line number of the file  @Override
  // Example:
  //    lineNumber - 50
  //    numberOfLines - 25
  //    Then lines returned is
  //    (line number 51, line number 52 ... , line number 74, line number75)

  @Override
  public Page getLinesAfter(PageRequest pageRequest) {
    int lineNumber = pageRequest.getStartingLineNo();
    int numberOfLines = pageRequest.getNumberOfLines();
    int startingLine = lineNumber + 1;
    int endingLine = Math.min(lineNumber + numberOfLines + 1, this.lines.size());
    List<String> res;
    if (startingLine > endingLine) {
      res = new LinkedList<>();
    } else {
      res = this.lines.subList(startingLine, endingLine);
    }
    Page page = new Page();
    page.setFileName(pageRequest.getFileName());
    page.setCursorAt(pageRequest.getCursorAt());
    page.setLines(res);
    page.setStartingLineNo(lineNumber);
    return page;
  }

  // Input:
  //    1. lineNumber - The line number
  //    2. numberOfLines - Number of lines requested
  // Expected functionality:
  //    1. Get the requested number of lines starting from the given line number.
  //    2. Make page object of this and return.
  //    3. For cursor position should be (startingLineNo, 0)
  //    4. For fileName use the value from pageRequest
  // NOTE:
  //    If there less than requested number of lines, then return just those lines.
  //    Zero is the first line number of the file  @Override
  // Example:
  //    lineNumber - 50
  //    numberOfLines - 25
  //    Then lines returned is
  //    (line number 50, line number 51 ... , line number 73, line number74)

  @Override
  public Page getLinesFrom(PageRequest pageRequest) {
    int lineNumber = pageRequest.getStartingLineNo();
    int numberOfLines = pageRequest.getNumberOfLines();
    int endingLine = Math.min(lineNumber + numberOfLines, this.lines.size());
    List<String> res = this.lines.subList(lineNumber, endingLine);
    Page page = new Page();
    page.setFileName(pageRequest.getFileName());
    page.setLines(res);
    page.setCursorAt(new Cursor(lineNumber, 0));
    page.setStartingLineNo(lineNumber);
    return page;
  }

  // Input:
  //    SearchRequest - contains following information
  //        1. pattern - pattern you want to search
  //        2. File name - file where you want to search for the pattern
  // Description:
  //    1. Find all occurrences of the pattern in the SourceFile
  //    2. Create an empty list of cursors
  //    3. For each occurrence starting position add to the list of cursors
  //    4. return the list of cursors
  // Recommendation:
  //    1. Use FASTER string search algorithm.
  //    2. Feel free to try any other algorithm/data structure to improve search speed.
  // Reference:
  //     https://www.geeksforgeeks.org/kmp-algorithm-for-pattern-searching/

  @Override
  public List<Cursor> getCursors(SearchRequest searchRequest) {
    boolean efficient = true;
    String pattern = searchRequest.getPattern();
    List<Cursor> res = new LinkedList<>();
    List<String> lines = this.getAllLines();
    for (int i = 0; i < lines.size(); i++) {
      String s = lines.get(i);
      List<Integer> searched = PatternSearchAlgorithm.stringSearch(s, pattern, efficient, false);
      for(int j = 0; j < searched.size(); j++) {
        Cursor cursor = new Cursor(i, searched.get(j));
        res.add(cursor);
      }
    }
    return res;
  }

  @Override
  public Page getCursorPage() {
    return null;
  }


}

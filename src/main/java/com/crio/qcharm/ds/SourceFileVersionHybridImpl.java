package com.crio.qcharm.ds;

import com.crio.qcharm.request.PageRequest;
import com.crio.qcharm.request.SearchRequest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import lombok.Data;

@Data
public class SourceFileVersionHybridImpl implements SourceFileVersion {


  // Input:
  //     FileInfo - contains following information
  //         1. fileName
  //         2. List of lines
  // Steps:
  //     You task here is to construct SourceFileVersionHybridImpl object by
  //     1. Storing the lines received from fileInfo object
  //     2. Storing the fileName received from fileInfo object.
  // Recommendations:
  //     1. Use Java List<Page> to store the lines received from fileInfo

  private String fileName;
  private Bucket bucket;

  SourceFileVersionHybridImpl(FileInfo fileInfo) {
    String fileName = fileInfo.getFileName();
    this.fileName = fileName;
    List<String> lines = fileInfo.getLines();
    List<Page> pages = new ArrayList<>();
    for(int i = 0; i < lines.size(); i+=50) {
      int endingIdx = Math.min(i+50, lines.size());
      Page page = new Page(lines.subList(i, endingIdx), i, fileName, null);
      pages.add(page);
    }
    this.bucket = new Bucket(pages);
  }

  public SourceFileVersionHybridImpl() {
    this.bucket = new Bucket();
  }

  public SourceFileVersionHybridImpl(SourceFileVersionHybridImpl obj) {
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



  @Override
  public void apply(SearchReplace searchReplace) {
  }


  @Override
  public void apply(UpdateLines updateLines) {
  }

  @Override
  public List<String> getAllLines() {
    List<String> lines = new LinkedList<>();
    for(Page page: this.bucket.getPages()) {
      List<String> line = page.getLines();
      lines.addAll(line);
    }
    return lines;
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
    List<String> lines = this.getLines(startingLine, numberOfLines);
    Page page = new Page();
    page.setLines(lines);
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


  List<String> getLines(int lineNumber, int numberOfLines) {
    List<String> lines = new ArrayList<>(this.getAllLines());
    int endingLine = Math.min(lineNumber + numberOfLines, lines.size());
    List<String> res = new LinkedList<>();
    for (int i = lineNumber; i < endingLine; i++) {
      res.add(lines.get(i));
    }
    return res;
  }

  @Override
  public Page getLinesAfter(PageRequest pageRequest) {
    int lineNumber = pageRequest.getStartingLineNo();
    int numberOfLines = pageRequest.getNumberOfLines();
    int startingLine = lineNumber + 1;
    List<String> lines = this.getLines(startingLine, numberOfLines);
    Page page = new Page();
    page.setFileName(pageRequest.getFileName());
    page.setCursorAt(pageRequest.getCursorAt());
    page.setLines(lines);
    page.setStartingLineNo(lineNumber);
    return page;
  }

  // TODO: CRIO_TASK_MODULE_IMPROVE_PERFORMANCE_V1
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
    List<String> lines = this.getLines(lineNumber, numberOfLines);
    Page page = new Page();
    page.setFileName(pageRequest.getFileName());
    page.setLines(lines);
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

  @Override
  public String getFileName() {
    return this.fileName;
  }

}

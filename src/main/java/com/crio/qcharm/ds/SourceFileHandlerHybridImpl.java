package com.crio.qcharm.ds;

import com.crio.qcharm.request.EditRequest;
import com.crio.qcharm.request.PageRequest;
import com.crio.qcharm.request.SearchReplaceRequest;
import com.crio.qcharm.request.SearchRequest;
import com.crio.qcharm.request.UndoRequest;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class SourceFileHandlerHybridImpl implements SourceFileHandler {


  private String fileName;
  private SourceFileVersion sourceFile;
  private CopyBuffer copyBuffer;
  private Stack<SourceFileVersion> undoStack;
  private Stack<SourceFileVersion> redoStack;

  public SourceFileHandlerHybridImpl(String fileName) {
    this.fileName = fileName;
    this.undoStack = new Stack<>();
    this.redoStack = new Stack<>();
  }


  
  // Input:
  //      FileName
  //  Steps:
  //    1. Given SourceFile name get the latest version of the it.
  //  Description:
  //    After loading the file the SourceFile would have gone through multiple
  //    changes. When we say "Latest version of the SourceFile" it means the SourceFile's present
  //    view after applying all the changes.

  @Override
  public SourceFileVersion getLatestSourceFileVersion(String fileName) {
    return this.sourceFile;
  }

  
  // Input:
  //      FileInfo
  //  Steps:
  //    1. Create an object of the SourceFileVersionHybridImpl class using the given fileInfo.
  //    2. Using that object get the first 50 lines of this file.
  //    3. Create Page object using the lines received and return the same.
  //  How to construct Page object ?
  //    1. lines should be the first 50 lines of the SourceFile
  //    2. cursorAt should be new Cursor(0,0)
  //    3. StartingLineNo is set to 0
  //    4. fileName should be same as the fileInfo.fileName
  //
  //  What is Cursor?
  //     It represents position of the cursor in the editor.
  //     Cursor is represented using (lineNumber, columnNumber).

  @Override
  public Page loadFile(FileInfo fileInfo) {
    this.sourceFile = new SourceFileVersionLinkedListImpl(fileInfo);
    Page page = new Page();
    List<String> sourceLines = sourceFile.getAllLines();
    int limit = Math.min(50, sourceLines.size());
    List<String> res = sourceLines.subList(0, limit);
    page.setLines(res);
    page.setCursorAt(new Cursor(0, 0));
    page.setStartingLineNo(0);
    page.setFileName(fileInfo.getFileName());
    return page;
  }

  
  // Input:
  //     PageRequest - contains following information
  //         1. Starting line number
  //         2. File name;
  //         3. requested number of Lines
  //         4. Cursor position
  //  Steps:
  //    1. After loadFile SourceFileVersionHybridImpl has all the file information
  //    2. Using that get "requested number of lines" above "the given line number".
  //    3. Construct Page object and return
  //  How to construct Page object ?
  //    1. lines - lines you got in step 2
  //    2. cursorAt should be same as pageRequest.cursorAt
  //    3. StartingLineNo should be same as first line number of lines
  //    4. fileName should be same as the pageRequest.fileName

  @Override
  public Page getPrevLines(PageRequest pageRequest) {
    return this.sourceFile.getLinesBefore(pageRequest);
  }

  
  // Input:
  //     PageRequest - contains following information
  //         1. Starting line number
  //         2. File name;
  //         3. requested number of Lines
  //         4. Cursor position
  //  Steps:
  //    1. After loadFile SourceFileVersionHybridImpl has all the file information
  //    2. Using that get "requested number of lines" below "the given line number".
  //    3. Construct Page object and return
  //  How to construct Page object ?
  //    1. lines - lines you got in step 2
  //    2. cursorAt should be same as pageRequest.cursorAt
  //    3. StartingLineNo should be same as first line number of lines
  //    4. fileName should be same as the pageRequest.fileName

  @Override
  public Page getNextLines(PageRequest pageRequest) {
    Page page = this.sourceFile.getLinesAfter(pageRequest);
    if (page.getLines().size() == 0) {
      return page;
    }
    page.setStartingLineNo(page.getStartingLineNo() + 1);
    return page;
  }

  
  // Input:
  //     PageRequest - contains following information
  //         1. Starting line number
  //         2. File name;
  //         3. requested number of Lines
  //         4. Cursor position
  //  Steps:
  //    1. After loadFile SourceFileVersionHybridImpl has all the file information
  //    2. Using the object get "requested number of lines" starting from "the given line number".
  //    3. Construct Page object and return
  //  How to construct Page object ?
  //    1. lines - lines you got in step 2
  //    2. cursorAt should be same be set to (startingLineNo, 0);
  //    3. StartingLineNo should be same as first line number of lines
  //    4. fileName should be same as the pageRequest.fileName

  @Override
  public Page getLinesFrom(PageRequest pageRequest) {
    return this.sourceFile.getLinesFrom(pageRequest);
  }

  
  // Input:
  //     SearchRequest - contains following information
  //         1. pattern - pattern you want to search
  //         2. File name - file where you want to search for the pattern
  // Description:
  //    1. For the given SourceFile use SourceFileVersionHybridImpl
  //    .getCursors() to find all occurrences of the pattern in the SourceFile.
  //    2. return the all occurrences starting position in a list.

  @Override
  public List<Cursor> search(SearchRequest searchRequest) {
    return this.sourceFile.getCursors(searchRequest);
  }


  @Override
  public void setCopyBuffer(CopyBuffer copyBuffer) {
    this.copyBuffer = copyBuffer;
  }

  
  // Input:
  //      None
  // Description:
  //      return the previously stored copy buffer
  //      if nothing is stored return copy buffer containing empty lines.

  @Override
  public CopyBuffer getCopyBuffer() {
    return this.copyBuffer;
  }

  
  // Input:
  //      Object of type SourceFileVersionHybridImpl
  // Description:
  //      make a copy of the the given SourceFileVersionHybridImpl object return new object
  // NOTE:
  //      DON'T CHANGE THE SIGNATURE OF THIS FUNCTION

  @Override
  public SourceFileVersion cloneObj(SourceFileVersion ver) {
    return new SourceFileVersionHybridImpl((SourceFileVersionHybridImpl) ver);
  }


  @Override
  public void editLines(EditRequest editRequest) {
    
  }



  @Override
  public void searchReplace(SearchReplaceRequest searchReplaceRequest) {
  }


  @Override
  public void undo(UndoRequest undoRequest) {
  }


  @Override
  public void redo(UndoRequest undoRequest) {
  }


  public Page getCursorPage() {
    return null;
  }

}

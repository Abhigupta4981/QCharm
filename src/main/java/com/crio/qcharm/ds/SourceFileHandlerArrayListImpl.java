package com.crio.qcharm.ds;

import com.crio.qcharm.request.EditRequest;
import com.crio.qcharm.request.PageRequest;
import com.crio.qcharm.request.SearchReplaceRequest;
import com.crio.qcharm.request.SearchRequest;
import com.crio.qcharm.request.UndoRequest;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class SourceFileHandlerArrayListImpl implements SourceFileHandler {

  private String fileName;
  private SourceFileVersion sourceFile;
  private CopyBuffer copyBuffer;
  private Stack<SourceFileVersion> undoStack;
  private Stack<SourceFileVersion> redoStack;

  public SourceFileHandlerArrayListImpl(String fileName) {
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
  //    1. Create an object of the SourceFileVersionArrayListImpl class using the given fileInfo.
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
    this.sourceFile = new SourceFileVersionArrayListImpl(fileInfo);
    Page page = new Page();
    List<String> sourceLines = sourceFile.getAllLines();
    List<String> res = new ArrayList<>();
    for(int i = 0; i<Math.min(50, sourceLines.size()); i++) {
      res.add(sourceLines.get(i));
    }
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
  //    1. After loadFile SourceFileVersionArrayListImpl has all the file information
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
  //    1. After loadFile SourceFileVersionArrayListImpl has all the file information
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
  //    1. After loadFile SourceFileVersionArrayListImpl has all the file information
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
  //    1. For the given SourceFile use SourceFileVersionArrayListImpl
  //    .getCursors() to find all occurrences of the pattern in the SourceFile.
  //    2. return the all occurrences starting position in a list.

  @Override
  public List<Cursor> search(SearchRequest searchRequest) {
    return this.sourceFile.getCursors(searchRequest);
  }


  // Input:
  //     CopyBuffer - contains following information
  //         1. List of lines
  // Description:
  //      Store the incoming copy buffer

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
  //      Object of type SourceFileVersionArrayListImpl
  // Description:
  //      make a copy of the the given SourceFileVersionArrayListImpl object return new object
  // NOTE:
  //      DON'T CHANGE THE SIGNATURE OF THIS FUNCTION

  @Override
  public SourceFileVersion cloneObj(SourceFileVersion ver) {
    return new SourceFileVersionArrayListImpl((SourceFileVersionArrayListImpl) ver);
  }


  // Input:
  //     EditRequest
  //        1. starting line no - starting line number of last time it received page from backend
  //        2. ending line no - ending line no of the last time it received page from backend;
  //        3. new content - list of lines present view of lines(starting line no, ending line no)
  //        4. file name
  //        5. cursor
  // Description:
  //        1. Remove the line numbers in the range(starting line no, ending line no)
  //        2. Inserting the lines in new content starting position starting line no
  // Example:
  //        EditRequest looks like this
  //            1. start line no - 50
  //            2. ending line no - 60
  //            3. new content - ["Hello world"]
  //
  //       Assume the file has 100 lines in it
  //
  //       File contents before edit:
  //       ==========================
  //       line no 1
  //       line no 2
  //          .....
  //       line no 100
  //
  //        File contents After Edit:
  //        =========================
  //        line no 1
  //        line no 2
  //        line no 3
  //         .....
  //        line no 49
  //        Hello World
  //        line no 61
  //        line no 62
  //          ....
  //        line no 100
  //

  @Override
  public void editLines(EditRequest editRequest) {
    List<String> newContent = editRequest.getNewContent();
    int startingLine = editRequest.getStartingLineNo();
    int endingLine = editRequest.getEndingLineNo();
    Cursor cursor = editRequest.getCursorAt();
    UpdateLines updateLines = new UpdateLines(startingLine, 
                              endingLine - startingLine, 
                              newContent, cursor);
    SourceFileVersion clonedSourceFile = this.cloneObj(this.sourceFile);
    this.undoStack.push(clonedSourceFile);
    this.sourceFile.apply(updateLines);
  }

  
  // Input:
  //      SearchReplaceRequest
  //        1. pattern  - pattern to be found
  //        2. newPattern - pattern to be replaced with
  //        3. fileName
  // Description:
  //      using the SourceFileVersionArrayListImpl object find every occurrence of pattern
  //      and replace it with the given newPattern

  @Override
  public void searchReplace(SearchReplaceRequest searchReplaceRequest) {
    int startingLineNo = searchReplaceRequest.getStartingLineNo();
    String pattern = searchReplaceRequest.getPattern();
    String newPattern = searchReplaceRequest.getNewPattern();
    SearchReplace sReplace = new SearchReplace(startingLineNo, 
                                                    pattern.length(), 
                                                    null, 
                                                    pattern, 
                                                    newPattern);
    SourceFileVersion clonedSourceFile = this.cloneObj(this.sourceFile);
    this.undoStack.add(clonedSourceFile);
    sourceFile.apply(sReplace);
  }

  
  // Input:
  //      UndoRequest
  //        1. fileName
  // Description:
  //      1. For the given file go back by one edit.
  //      2. If the file is already at its oldest change do nothing

  @Override
  public void undo(UndoRequest undoRequest) {
    if (this.undoStack.size() == 0) {
      return;
    }
    SourceFileVersion undoSourceFile = this.undoStack.pop();
    SourceFileVersion clonedSourceFile = this.cloneObj(this.sourceFile);
    this.redoStack.push(clonedSourceFile);
    this.sourceFile = undoSourceFile;
  }

  
  // Input:
  //      UndoRequest
  //        1. fileName
  // Description:
  //      1. Re apply the last undone change. Basically reverse the last last undo.
  //      2. If there was no undo done earlier do nothing.

  @Override
  public void redo(UndoRequest undoRequest) {
    if (this.redoStack.size() == 0) {
      return;
    }
    SourceFileVersion redoSourceFile = this.redoStack.pop();
    SourceFileVersion clonedSourceFile = this.cloneObj(this.sourceFile);
    this.undoStack.add(clonedSourceFile);
    this.sourceFile = redoSourceFile;
  }

  // TODO: CRIO_TASK_MODULE_UNDO_REDO
  // Input:
  //      None
  // Description:
  //      Return the page that was in view as of this edit.
  //      1. starting line number  -should be same as it was in the last change
  //      2. Cursor - should return to the same position as it was in the last change
  //      3. Number of lines - should be same as it was in the last change.

  public Page getCursorPage() {
    return null;
  }

}

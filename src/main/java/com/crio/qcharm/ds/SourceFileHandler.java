package com.crio.qcharm.ds;

import com.crio.qcharm.request.EditRequest;
import com.crio.qcharm.request.PageRequest;
import com.crio.qcharm.request.SearchReplaceRequest;
import com.crio.qcharm.request.SearchRequest;
import com.crio.qcharm.request.UndoRequest;
import java.util.List;

public interface SourceFileHandler {

  SourceFileVersion getLatestSourceFileVersion(String fileName);

  Page loadFile(FileInfo fileInfo);

  Page getPrevLines(PageRequest pageRequest);

  Page getNextLines(PageRequest pageRequest);

  Page getLinesFrom(PageRequest pageRequest);

  List<Cursor> search(SearchRequest searchRequest);



  SourceFileVersion cloneObj(SourceFileVersion ver);

  void setCopyBuffer(CopyBuffer copyBuffer);

  CopyBuffer getCopyBuffer();

  void editLines(EditRequest editRequest);

  void searchReplace(SearchReplaceRequest searchReplaceRequest);


  void undo(UndoRequest undoRequest);

  void redo(UndoRequest undoRequest);

  Page getCursorPage();

}

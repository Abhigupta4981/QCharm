package com.crio.qcharm.ds;

import com.crio.qcharm.request.PageRequest;
import com.crio.qcharm.request.SearchRequest;
import java.util.List;

public interface SourceFileVersion {

  SourceFileVersion apply(List<Edits> edits);

  void apply(SearchReplace searchReplace);

  void apply(UpdateLines updateLines);

  List<String> getAllLines();

  Page getLinesBefore(PageRequest pageRequest);

  Page getLinesAfter(PageRequest pageRequest);

  Page getLinesFrom(PageRequest pageRequest);

  List<Cursor> getCursors(SearchRequest searchRequest);

  Page getCursorPage();

  String getFileName();

}

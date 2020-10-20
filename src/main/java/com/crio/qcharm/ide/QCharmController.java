
package com.crio.qcharm.ide;

import com.crio.qcharm.ds.CopyBuffer;
import com.crio.qcharm.ds.Cursor;
import com.crio.qcharm.ds.FileInfo;
import com.crio.qcharm.ds.Page;
import com.crio.qcharm.ds.RunCodeArgs;
import com.crio.qcharm.ds.RunCodeOutput;
import com.crio.qcharm.ds.SourceFileHandler;
import com.crio.qcharm.ds.SourceFileHandlerArrayListImpl;
import com.crio.qcharm.request.EditRequest;
import com.crio.qcharm.request.MasterRequest;
import com.crio.qcharm.request.PageRequest;
import com.crio.qcharm.request.SearchReplaceRequest;
import com.crio.qcharm.runner.BaseRunner;
import com.crio.qcharm.runner.CRunner;
import com.crio.qcharm.runner.CppRunner;
import com.crio.qcharm.runner.JavaRunner;
import com.crio.qcharm.runner.PythonRunner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("qcharm")
public class QCharmController {
  SourceFileHandler sourceFileHandler =
      new SourceFileHandlerArrayListImpl("fileName");

  @PostMapping("/get_prev_lines_new")
  @ResponseBody
  public Object getPrevLinesNew(@RequestBody MasterRequest masterRequest) {
    Page prevPage = sourceFileHandler.getPrevLines(masterRequest.getPageRequest());
    return new ResponseEntity<>(prevPage, HttpStatus.OK);
  }

  @PostMapping("/get_next_lines_new")
  @ResponseBody
  public Object getNextLinesNew(@RequestBody MasterRequest masterRequest) {
    sourceFileHandler.editLines(masterRequest.getEditRequest());
    Page prevPage = sourceFileHandler.getNextLines(masterRequest.getPageRequest());
    return new ResponseEntity<>(prevPage, HttpStatus.OK);
  }

  @PostMapping("/load_file_new")
  @ResponseBody
  public Object loadFileNew(@RequestBody MasterRequest masterRequest) {
    FileInfo fileInfo = masterRequest.getFileInfo();
    Page page = sourceFileHandler.loadFile(fileInfo);
    return new ResponseEntity<>(page, HttpStatus.OK);
  }

  @PostMapping("/jump_new")
  @ResponseBody
  public Object getLinesNew(@RequestBody MasterRequest masterRequest) {
    sourceFileHandler.editLines(masterRequest.getEditRequest());
    Page page = sourceFileHandler.getLinesFrom(masterRequest.getPageRequest());
    return new ResponseEntity<>(page, HttpStatus.OK);
  }

  @PostMapping("/search_new")
  @ResponseBody
  public Object searchNew(@RequestBody MasterRequest masterRequest) {
    sourceFileHandler.editLines(masterRequest.getEditRequest());
    List<Cursor> cursors = sourceFileHandler.search(masterRequest.getSearchRequest());
    return new ResponseEntity<>(cursors, HttpStatus.OK);
  }

  @PostMapping("/cut_new")
  @ResponseBody
  public Object cutNew(@RequestBody MasterRequest masterRequest) {

    final Pair<CopyBuffer, List<String>> buffers = masterRequest.getCopyBuffer();
    sourceFileHandler.setCopyBuffer(buffers.getKey());
    EditRequest editRequest = masterRequest.getEditRequest();
    editRequest.setNewContent(buffers.getValue());
    sourceFileHandler.editLines(editRequest);
    final PageRequest pageRequest = masterRequest.getPageRequestPostEdit();
    final Page page = sourceFileHandler.getLinesFrom(pageRequest);
    page.setCursorAt(masterRequest.getCursorStart());

    return new ResponseEntity<>(page, HttpStatus.OK);
  }

  @PostMapping("/copy_new")
  @ResponseBody
  public Object copyNew(@RequestBody MasterRequest masterRequest) {
    final Pair<CopyBuffer, List<String>> buffers = masterRequest.getCopyBuffer();

    CopyBuffer copyBuffer = buffers.getKey();
    sourceFileHandler.setCopyBuffer(copyBuffer);

    EditRequest editRequest = masterRequest.getEditRequest();
    sourceFileHandler.editLines(editRequest);
    final PageRequest pageRequest = masterRequest.getPageRequestPostEdit();
    final Page page = sourceFileHandler.getLinesFrom(pageRequest);
    page.setCursorAt(masterRequest.getCursorStart());
    return new ResponseEntity<>(page, HttpStatus.OK);
  }

  @PostMapping("/paste_new")
  @ResponseBody
  public Object pasteNew(@RequestBody MasterRequest masterRequest) {
    sourceFileHandler.editLines(masterRequest.getEditRequest());

    masterRequest.applyPaste(sourceFileHandler.getCopyBuffer());

    sourceFileHandler.editLines(masterRequest.getEditRequest());

    final PageRequest pageRequest = masterRequest.getPageRequestPostEdit();
    final Page page = sourceFileHandler.getLinesFrom(pageRequest);
    page.setCursorAt(masterRequest.getCursorStart());
    System.out.println(page);
    return new ResponseEntity<>(page, HttpStatus.OK);
  }

  @PostMapping("/edit_new")
  @ResponseBody
  public Object editNew(@RequestBody MasterRequest masterRequest) {
    sourceFileHandler.editLines(masterRequest.getEditRequest());
    final PageRequest pageRequest = masterRequest.getPageRequestPostEdit();
    final Page page = sourceFileHandler.getLinesFrom(pageRequest);
    page.setCursorAt(masterRequest.getCursorStart());

    return new ResponseEntity<>(page, HttpStatus.OK);
  }

  @PostMapping("/search_replace_new")
  @ResponseBody
  public Object searchReplaceNew(@RequestBody MasterRequest masterRequest) {
    final SearchReplaceRequest searchReplaceRequest = masterRequest.getSearchReplaceRequest();
    sourceFileHandler.editLines(masterRequest.getEditRequest());
    sourceFileHandler.searchReplace(searchReplaceRequest);
    final PageRequest pageRequestAfterReplace = masterRequest.getPageRequestPostEdit();
    final Page linesAfterReplace = sourceFileHandler.getLinesFrom(pageRequestAfterReplace);

    return new ResponseEntity<>(linesAfterReplace, HttpStatus.OK);
  }


  //FIXME: NOT COMPLETE
  @PostMapping("/undo_new")
  @ResponseBody
  public Object undo(@RequestBody MasterRequest masterRequest) {
    System.out.println("Called Undo");

    sourceFileHandler.editLines(masterRequest.getEditRequest());
    sourceFileHandler.undo(masterRequest.getUndoRequest());
    Page page = sourceFileHandler.getCursorPage();

    return new ResponseEntity<>(page, HttpStatus.OK);
  }

  //FIXME: NOT COMPLETE
  @PostMapping("/redo_new")
  @ResponseBody
  public Object redo(@RequestBody MasterRequest masterRequest) {
    System.out.println("Called Redo");

    sourceFileHandler.editLines(masterRequest.getEditRequest());
    sourceFileHandler.redo(masterRequest.getUndoRequest());
    Page page = sourceFileHandler.getCursorPage();

    return new ResponseEntity<>(page, HttpStatus.OK);
  }


  @PostMapping("/run_file")
  @ResponseBody
  public RunCodeOutput execute(@RequestBody RunCodeArgs runCodeArgs) throws Exception {
    System.out.println("Called run-file");

    List<String> allLines = sourceFileHandler
        .getLatestSourceFileVersion(runCodeArgs.getFileName()).getAllLines();
    runCodeArgs.setLines(allLines);
    return runCode(runCodeArgs);
  }

  @PostMapping("/run_file_internal")
  public RunCodeOutput runCode(@RequestBody RunCodeArgs runCodeArgs) throws Exception {
    List<String> filesToDelete = new ArrayList<>();
    String fileName = runCodeArgs.getFileName();
    try {
      filesToDelete.add(fileName);
      FileWriter writer = new FileWriter(fileName, false);
      for (String line : runCodeArgs.getLines()) {
        writer.write(line + "\n");
      }
      writer.flush();
      writer.close();

      BaseRunner runner = getRunner(runCodeArgs.getLanguage());
      return runner.runProgram(filesToDelete, fileName, runCodeArgs.getInputs());

    } finally {
      for (String file : filesToDelete) {
        Files.deleteIfExists(new File(file).toPath());
      }
    }
  }

  private BaseRunner getRunner(String language) {
    if (language.equalsIgnoreCase("java")) {
       return new JavaRunner();
    }
    if (language.equalsIgnoreCase("python")) {
      return  new PythonRunner();
    }
    if (language.equalsIgnoreCase("c++")) {
      return  new CppRunner();
    }
    if (language.equalsIgnoreCase("c")) {
      return  new CRunner();
    }
    return null;
  }
}
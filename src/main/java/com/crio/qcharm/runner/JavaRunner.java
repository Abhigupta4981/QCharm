package com.crio.qcharm.runner;

import com.crio.qcharm.ds.RunCodeOutput;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 *  DONT'T EDIT
 */

public class JavaRunner extends BaseRunner {

  @Override
  public String extractFileName(List<String> lines) {
    return extractClassName(lines) + ".java";
  }

  private String extractClassName(List<String> lines) {
    for (String currentLine : lines) {
      String[] wordsArray = currentLine.split("\\s");
      List<String> words = Stream.of(wordsArray).filter(s -> s != null && !s.isEmpty())
          .collect(Collectors.toList());
      for(int i = 1; i < words.size(); i++) {
        if("public".equals(words.get(i-1)) && "class".equals(words.get(i)) && words.size() > i +1) {
          return words.get(i+1).replace("{", "");
        }
      }
    }
    return null;
  }

  @Override
  public RunCodeOutput runProgram(List<String> filesToDelete, String fileName,
      String[] inputs) throws Exception {
    String compileProcess = "javac " + fileName;
    String classFile = fileName.substring(0, fileName.length() - 5);
    filesToDelete.add(classFile + ".class");
    String runProcess = TIMEOUT_5 + "java " + classFile;

    StringBuilder outputBuilder = new StringBuilder();
    outputBuilder.append(callProcess(compileProcess, new String[]{}));
    outputBuilder.append(callProcess(runProcess, inputs));

    RunCodeOutput output = new RunCodeOutput();
    output.setOutput(outputBuilder.toString());
    return output;
  }
}

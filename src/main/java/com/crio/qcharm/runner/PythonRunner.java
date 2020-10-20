package com.crio.qcharm.runner;
import com.crio.qcharm.ds.RunCodeOutput;
import java.util.List;

/*
 *  DONT'T EDIT
 */

public class PythonRunner extends BaseRunner {


  @Override
  public String extractFileName(List<String> lines) {
    return "scratch.py";
  }

  public RunCodeOutput runProgram(List<String> filesToDelete, String fileName,
      String[] inputs) throws Exception {
    String runProcess = TIMEOUT_5 + "python3 " + fileName;

    StringBuilder outputBuilder = new StringBuilder();
    outputBuilder.append(callProcess(runProcess, inputs));

    RunCodeOutput output = new RunCodeOutput();
    output.setOutput(outputBuilder.toString());
    return output;
  }
}

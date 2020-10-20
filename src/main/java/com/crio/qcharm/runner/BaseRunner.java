package com.crio.qcharm.runner;


/*
 *  DONT'T EDIT
 */

import com.crio.qcharm.ds.RunCodeOutput;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public abstract class BaseRunner {

  public static final String TIMEOUT_5 = "timeout 5 ";

  public abstract String extractFileName(List<String> lines);

  public abstract RunCodeOutput runProgram(List<String> filesToDelete, String fileName,
      String[] inputs) throws Exception;


  protected String callProcess(String command, String[] inputs) throws Exception {
    StringBuilder outputBuilder = new StringBuilder();

    Process pro = Runtime.getRuntime().exec(command);
    writeIO(pro.getOutputStream(), inputs);
    outputBuilder.append(readIO(pro.getInputStream()));
    outputBuilder.append(readIO(pro.getErrorStream()));
    pro.waitFor();
    String output = outputBuilder.toString();
    System.out.println(outputBuilder);
    return output;
  }

  protected String readIO(InputStream ins) throws Exception {
    String line = null;
    BufferedReader in = new BufferedReader(new InputStreamReader(ins));
    StringBuilder outputBuilder = new StringBuilder();
    while ((line = in.readLine()) != null) {
      outputBuilder.append(line + "\n");
    }
    return outputBuilder.toString();
  }

  protected void writeIO(OutputStream ins, String[] inputs) throws Exception {
    if (inputs == null || inputs.length == 0) {
      return;
    }
    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(ins));
    for (String input : inputs) {
      out.write(input);
      out.write("\n");
      out.flush();
    }
  }
}

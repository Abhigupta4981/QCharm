package com.crio.qcharm.ds;


/*
 *  DONT'T EDIT
 */

public class RunCodeOutput {
  private int status;
  private String output;

  public RunCodeOutput(int status, String output) {
    this.status = status;
    this.output = output;
  }

  public RunCodeOutput() {
  }

  public int getStatus() {
    return this.status;
  }

  public String getOutput() {
    return this.output;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public void setOutput(String output) {
    this.output = output;
  }

  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof RunCodeOutput)) {
      return false;
    }
    final RunCodeOutput other = (RunCodeOutput) o;
    if (!other.canEqual((Object) this)) {
      return false;
    }
    if (this.getStatus() != other.getStatus()) {
      return false;
    }
    final Object this$output = this.getOutput();
    final Object other$output = other.getOutput();
    if (this$output == null ? other$output != null : !this$output.equals(other$output)) {
      return false;
    }
    return true;
  }

  protected boolean canEqual(final Object other) {
    return other instanceof RunCodeOutput;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getStatus();
    final Object $output = this.getOutput();
    result = result * PRIME + ($output == null ? 43 : $output.hashCode());
    return result;
  }

  public String toString() {
    return "RunCodeOutput(status=" + this.getStatus() + ", output=" + this.getOutput() + ")";
  }
}

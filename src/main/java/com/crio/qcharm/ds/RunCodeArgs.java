package com.crio.qcharm.ds;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RunCodeArgs {
  private String language;
  private String fileName;
  private List<String> lines;
  private String[] inputs;

  public RunCodeArgs() {
  }

  public String getLanguage() {
    return this.language;
  }

  public String getFileName() {
    return this.fileName;
  }

  public List<String> getLines() {
    return this.lines;
  }

  public String[] getInputs() {
    return this.inputs;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public void setLines(List<String> lines) {
    this.lines = lines;
  }

  public void setInputs(String[] inputs) {
    this.inputs = inputs;
  }

  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof RunCodeArgs)) {
      return false;
    }
    final RunCodeArgs other = (RunCodeArgs) o;
    if (!other.canEqual((Object) this)) {
      return false;
    }
    final Object this$language = this.getLanguage();
    final Object other$language = other.getLanguage();
    if (this$language == null ? other$language != null : !this$language.equals(other$language)) {
      return false;
    }
    final Object this$fileName = this.getFileName();
    final Object other$fileName = other.getFileName();
    if (this$fileName == null ? other$fileName != null : !this$fileName.equals(other$fileName)) {
      return false;
    }
    final Object this$lines = this.getLines();
    final Object other$lines = other.getLines();
    if (this$lines == null ? other$lines != null : !this$lines.equals(other$lines)) {
      return false;
    }
    if (!java.util.Arrays.deepEquals(this.getInputs(), other.getInputs())) {
      return false;
    }
    return true;
  }

  protected boolean canEqual(final Object other) {
    return other instanceof RunCodeArgs;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $language = this.getLanguage();
    result = result * PRIME + ($language == null ? 43 : $language.hashCode());
    final Object $fileName = this.getFileName();
    result = result * PRIME + ($fileName == null ? 43 : $fileName.hashCode());
    final Object $lines = this.getLines();
    result = result * PRIME + ($lines == null ? 43 : $lines.hashCode());
    result = result * PRIME + java.util.Arrays.deepHashCode(this.getInputs());
    return result;
  }

  public String toString() {
    return "RunCodeArgs(language=" + this.getLanguage() + ", fileName=" + this.getFileName()
        + ", lines=" + this.getLines() + ", inputs=" + java.util.Arrays
        .deepToString(this.getInputs()) + ")";
  }
}

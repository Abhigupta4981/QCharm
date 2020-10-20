package com.crio.qcharm.request;

public class SearchRequest {
  int startingLineNo;
  String pattern;
  String fileName;

  public SearchRequest(int startingLineNo, String pattern, String fileName) {
    this.startingLineNo = startingLineNo;
    this.pattern = pattern;
    this.fileName = fileName;
  }

  public SearchRequest() {
  }

  public int getStartingLineNo() {
    return this.startingLineNo;
  }

  public String getPattern() {
    return this.pattern;
  }

  public String getFileName() {
    return this.fileName;
  }

  public void setStartingLineNo(int startingLineNo) {
    this.startingLineNo = startingLineNo;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof SearchRequest)) {
      return false;
    }
    final SearchRequest other = (SearchRequest) o;
    if (!other.canEqual((Object) this)) {
      return false;
    }
    if (this.getStartingLineNo() != other.getStartingLineNo()) {
      return false;
    }
    final Object this$pattern = this.getPattern();
    final Object other$pattern = other.getPattern();
    if (this$pattern == null ? other$pattern != null : !this$pattern.equals(other$pattern)) {
      return false;
    }
    final Object this$fileName = this.getFileName();
    final Object other$fileName = other.getFileName();
    if (this$fileName == null ? other$fileName != null : !this$fileName.equals(other$fileName)) {
      return false;
    }
    return true;
  }

  protected boolean canEqual(final Object other) {
    return other instanceof SearchRequest;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getStartingLineNo();
    final Object $pattern = this.getPattern();
    result = result * PRIME + ($pattern == null ? 43 : $pattern.hashCode());
    final Object $fileName = this.getFileName();
    result = result * PRIME + ($fileName == null ? 43 : $fileName.hashCode());
    return result;
  }

  public String toString() {
    return "SearchRequest(startingLineNo=" + this.getStartingLineNo() + ", pattern=" + this
        .getPattern() + ", fileName=" + this.getFileName() + ")";
  }
}

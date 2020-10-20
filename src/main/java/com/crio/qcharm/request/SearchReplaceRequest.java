
/*
 *  DONT'T EDIT
 */

package com.crio.qcharm.request;

public class SearchReplaceRequest {
  int startingLineNo;
  int length;
  String pattern;
  String newPattern;
  String fileName;

  public SearchReplaceRequest(int startingLineNo, int length, String pattern, String newPattern,
      String fileName) {
    this.startingLineNo = startingLineNo;
    this.length = length;
    this.pattern = pattern;
    this.newPattern = newPattern;
    this.fileName = fileName;
  }

  public SearchReplaceRequest() {
  }

  public int getStartingLineNo() {
    return this.startingLineNo;
  }

  public int getLength() {
    return this.length;
  }

  public String getPattern() {
    return this.pattern;
  }

  public String getNewPattern() {
    return this.newPattern;
  }

  public String getFileName() {
    return this.fileName;
  }

  public void setStartingLineNo(int startingLineNo) {
    this.startingLineNo = startingLineNo;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  public void setNewPattern(String newPattern) {
    this.newPattern = newPattern;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof SearchReplaceRequest)) {
      return false;
    }
    final SearchReplaceRequest other = (SearchReplaceRequest) o;
    if (!other.canEqual((Object) this)) {
      return false;
    }
    if (this.getStartingLineNo() != other.getStartingLineNo()) {
      return false;
    }
    if (this.getLength() != other.getLength()) {
      return false;
    }
    final Object this$oldPattern = this.getPattern();
    final Object other$oldPattern = other.getPattern();
    if (this$oldPattern == null ? other$oldPattern != null
        : !this$oldPattern.equals(other$oldPattern)) {
      return false;
    }
    final Object this$newPattern = this.getNewPattern();
    final Object other$newPattern = other.getNewPattern();
    if (this$newPattern == null ? other$newPattern != null
        : !this$newPattern.equals(other$newPattern)) {
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
    return other instanceof SearchReplaceRequest;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getStartingLineNo();
    result = result * PRIME + this.getLength();
    final Object $oldPattern = this.getPattern();
    result = result * PRIME + ($oldPattern == null ? 43 : $oldPattern.hashCode());
    final Object $newPattern = this.getNewPattern();
    result = result * PRIME + ($newPattern == null ? 43 : $newPattern.hashCode());
    final Object $fileName = this.getFileName();
    result = result * PRIME + ($fileName == null ? 43 : $fileName.hashCode());
    return result;
  }

  public String toString() {
    return "SearchReplaceRequest(startingLineNo=" + this.getStartingLineNo() + ", length=" + this
        .getLength() + ", pattern=" + this.getPattern() + ", newPattern=" + this
        .getNewPattern() + ", fileName=" + this.getFileName() + ")";
  }
}

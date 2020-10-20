
/*
 *  DONT'T EDIT
 */

package com.crio.qcharm.ds;

import java.util.List;

public class Page {
  private List<String> lines;
  private int startingLineNo;
  private String fileName;
  private Cursor cursorAt;

  public Page(List<String> lines, int startingLineNo, String fileName, Cursor cursorAt) {
    this.lines = lines;
    this.startingLineNo = startingLineNo;
    this.fileName = fileName;
    this.cursorAt = cursorAt;
  }

  // WARNING: DONT' REMOVE ME
  public Page() {
  }

  public List<String> getLines() {
    return this.lines;
  }

  public int getStartingLineNo() {
    return this.startingLineNo;
  }

  public String getFileName() {
    return this.fileName;
  }

  public Cursor getCursorAt() {
    return this.cursorAt;
  }

  public void setLines(List<String> lines) {
    this.lines = lines;
  }

  public void setStartingLineNo(int startingLineNo) {
    this.startingLineNo = startingLineNo;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public void setCursorAt(Cursor cursorAt) {
    this.cursorAt = cursorAt;
  }

  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Page)) {
      return false;
    }
    final Page other = (Page) o;
    if (!other.canEqual((Object) this)) {
      return false;
    }
    final Object this$lines = this.getLines();
    final Object other$lines = other.getLines();
    if (this$lines == null ? other$lines != null : !this$lines.equals(other$lines)) {
      return false;
    }
    if (this.getStartingLineNo() != other.getStartingLineNo()) {
      return false;
    }
    final Object this$fileName = this.getFileName();
    final Object other$fileName = other.getFileName();
    if (this$fileName == null ? other$fileName != null : !this$fileName.equals(other$fileName)) {
      return false;
    }
    final Object this$cursorAt = this.getCursorAt();
    final Object other$cursorAt = other.getCursorAt();
    if (this$cursorAt == null ? other$cursorAt != null : !this$cursorAt.equals(other$cursorAt)) {
      return false;
    }
    return true;
  }

  protected boolean canEqual(final Object other) {
    return other instanceof Page;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $lines = this.getLines();
    result = result * PRIME + ($lines == null ? 43 : $lines.hashCode());
    result = result * PRIME + this.getStartingLineNo();
    final Object $fileName = this.getFileName();
    result = result * PRIME + ($fileName == null ? 43 : $fileName.hashCode());
    final Object $cursorAt = this.getCursorAt();
    result = result * PRIME + ($cursorAt == null ? 43 : $cursorAt.hashCode());
    return result;
  }

  public String toString() {
    return "Page(lines=" + this.getLines() + ", startingLineNo=" + this.getStartingLineNo()
        + ", fileName=" + this.getFileName() + ", cursorAt=" + this.getCursorAt() + ")";
  }
}
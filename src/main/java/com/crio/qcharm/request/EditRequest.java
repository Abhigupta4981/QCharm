/*
 *  DONT'T EDIT
 */

package com.crio.qcharm.request;

import com.crio.qcharm.ds.Cursor;
import java.util.List;

public class EditRequest {
  int startingLineNo;
  int endingLineNo;
  List<String> newContent;
  String fileName;
  Cursor cursorAt;

  public EditRequest(int startingLineNo, int endingLineNo, List<String> newContent, String fileName,
      Cursor cursorAt) {
    this.startingLineNo = startingLineNo;
    this.endingLineNo = endingLineNo;
    this.newContent = newContent;
    this.fileName = fileName;
    this.cursorAt = cursorAt;
  }

  public EditRequest() {
  }

  public int getStartingLineNo() {
    return this.startingLineNo;
  }

  public int getEndingLineNo() {
    return this.endingLineNo;
  }

  public List<String> getNewContent() {
    return this.newContent;
  }

  public String getFileName() {
    return this.fileName;
  }

  public Cursor getCursorAt() {
    return this.cursorAt;
  }

  public void setStartingLineNo(int startingLineNo) {
    this.startingLineNo = startingLineNo;
  }

  public void setEndingLineNo(int endingLineNo) {
    this.endingLineNo = endingLineNo;
  }

  public void setNewContent(List<String> newContent) {
    this.newContent = newContent;
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
    if (!(o instanceof EditRequest)) {
      return false;
    }
    final EditRequest other = (EditRequest) o;
    if (!other.canEqual((Object) this)) {
      return false;
    }
    if (this.getStartingLineNo() != other.getStartingLineNo()) {
      return false;
    }
    if (this.getEndingLineNo() != other.getEndingLineNo()) {
      return false;
    }
    final Object this$newContent = this.getNewContent();
    final Object other$newContent = other.getNewContent();
    if (this$newContent == null ? other$newContent != null
        : !this$newContent.equals(other$newContent)) {
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
    return other instanceof EditRequest;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getStartingLineNo();
    result = result * PRIME + this.getEndingLineNo();
    final Object $newContent = this.getNewContent();
    result = result * PRIME + ($newContent == null ? 43 : $newContent.hashCode());
    final Object $fileName = this.getFileName();
    result = result * PRIME + ($fileName == null ? 43 : $fileName.hashCode());
    final Object $cursorAt = this.getCursorAt();
    result = result * PRIME + ($cursorAt == null ? 43 : $cursorAt.hashCode());
    return result;
  }

  public String toString() {
    return "EditRequest(startingLineNo=" + this.getStartingLineNo() + ", endingLineNo=" + this
        .getEndingLineNo() + ", newContent=" + this.getNewContent() + ", fileName=" + this
        .getFileName() + ", cursorAt=" + this.getCursorAt() + ")";
  }
}

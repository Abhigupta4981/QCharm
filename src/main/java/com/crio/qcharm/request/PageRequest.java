/*
 *  DONT'T EDIT
 */
package com.crio.qcharm.request;

import com.crio.qcharm.ds.Cursor;
import javax.validation.constraints.NotNull;

public class PageRequest {
  @NotNull
  int startingLineNo;
  @NotNull
  String fileName;
  @NotNull
  int numberOfLines;
  @NotNull
  Cursor cursorAt;

  public PageRequest(@NotNull int startingLineNo, @NotNull String fileName,
      @NotNull int numberOfLines, @NotNull Cursor cursorAt) {
    this.startingLineNo = startingLineNo;
    this.fileName = fileName;
    this.numberOfLines = numberOfLines;
    this.cursorAt = cursorAt;
  }

  public PageRequest() {
  }

  public @NotNull int getStartingLineNo() {
    return this.startingLineNo;
  }

  public @NotNull String getFileName() {
    return this.fileName;
  }

  public @NotNull int getNumberOfLines() {
    return this.numberOfLines;
  }

  public @NotNull Cursor getCursorAt() {
    return this.cursorAt;
  }

  public void setStartingLineNo(@NotNull int startingLineNo) {
    this.startingLineNo = startingLineNo;
  }

  public void setFileName(@NotNull String fileName) {
    this.fileName = fileName;
  }

  public void setNumberOfLines(@NotNull int numberOfLines) {
    this.numberOfLines = numberOfLines;
  }

  public void setCursorAt(@NotNull Cursor cursorAt) {
    this.cursorAt = cursorAt;
  }

  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof PageRequest)) {
      return false;
    }
    final PageRequest other = (PageRequest) o;
    if (!other.canEqual((Object) this)) {
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
    if (this.getNumberOfLines() != other.getNumberOfLines()) {
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
    return other instanceof PageRequest;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getStartingLineNo();
    final Object $fileName = this.getFileName();
    result = result * PRIME + ($fileName == null ? 43 : $fileName.hashCode());
    result = result * PRIME + this.getNumberOfLines();
    final Object $cursorAt = this.getCursorAt();
    result = result * PRIME + ($cursorAt == null ? 43 : $cursorAt.hashCode());
    return result;
  }

  public String toString() {
    return "PageRequest(startingLineNo=" + this.getStartingLineNo() + ", fileName=" + this
        .getFileName() + ", numberOfLines=" + this.getNumberOfLines() + ", cursorAt=" + this
        .getCursorAt() + ")";
  }
}


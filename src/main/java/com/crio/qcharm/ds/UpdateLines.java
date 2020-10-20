
/*
 *  DONT'T EDIT
 */

package com.crio.qcharm.ds;

import java.util.List;

public class UpdateLines implements Edits {
  int startingLineNo;
  int numberOfLines;
  List<String> lines;
  Cursor cursor;

  public UpdateLines(int startingLineNo, int numberOfLines, List<String> lines, Cursor cursor) {
    this.startingLineNo = startingLineNo;
    this.numberOfLines = numberOfLines;
    this.lines = lines;
    this.cursor = cursor;
  }

  @Override
  public int getStartingLineNo() {
    return this.startingLineNo;
  }

  @Override
  public int getNumberOfLines() {
    return this.numberOfLines;
  }

  public List<String> getLines() {
    return this.lines;
  }

  @Override
  public Cursor getCursor() {
    return this.cursor;
  }

  public void setStartingLineNo(int startingLineNo) {
    this.startingLineNo = startingLineNo;
  }

  public void setNumberOfLines(int numberOfLines) {
    this.numberOfLines = numberOfLines;
  }

  public void setLines(List<String> lines) {
    this.lines = lines;
  }

  public void setCursor(Cursor cursor) {
    this.cursor = cursor;
  }

  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof UpdateLines)) {
      return false;
    }
    final UpdateLines other = (UpdateLines) o;
    if (!other.canEqual((Object) this)) {
      return false;
    }
    if (this.getStartingLineNo() != other.getStartingLineNo()) {
      return false;
    }
    if (this.getNumberOfLines() != other.getNumberOfLines()) {
      return false;
    }
    final Object this$lines = this.getLines();
    final Object other$lines = other.getLines();
    if (this$lines == null ? other$lines != null : !this$lines.equals(other$lines)) {
      return false;
    }
    final Object this$cursor = this.getCursor();
    final Object other$cursor = other.getCursor();
    if (this$cursor == null ? other$cursor != null : !this$cursor.equals(other$cursor)) {
      return false;
    }
    return true;
  }

  protected boolean canEqual(final Object other) {
    return other instanceof UpdateLines;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getStartingLineNo();
    result = result * PRIME + this.getNumberOfLines();
    final Object $lines = this.getLines();
    result = result * PRIME + ($lines == null ? 43 : $lines.hashCode());
    final Object $cursor = this.getCursor();
    result = result * PRIME + ($cursor == null ? 43 : $cursor.hashCode());
    return result;
  }

  public String toString() {
    return "UpdateLines(startingLineNo=" + this.getStartingLineNo() + ", numberOfLines=" + this.getNumberOfLines()
        + ", lines=" + this.getLines() + ", cursor=" + this.getCursor() + ")";
  }
}

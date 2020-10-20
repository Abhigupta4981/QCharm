
/*
 *  DONT'T EDIT
 */
package com.crio.qcharm.ds;

public class DummyEdit implements Edits {
  int startingLineNo = 0;
  int length = 0;
  Cursor cursor = new Cursor(0,0);

  public DummyEdit() {
  }

  public int getStartingLineNo() {
    return this.startingLineNo;
  }

  public int getNumberOfLines() {
    return this.length;
  }

  public Cursor getCursor() {
    return this.cursor;
  }

  public void setStartingLineNo(int startingLineNo) {
    this.startingLineNo = startingLineNo;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public void setCursor(Cursor cursor) {
    this.cursor = cursor;
  }

  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof DummyEdit)) {
      return false;
    }
    final DummyEdit other = (DummyEdit) o;
    if (!other.canEqual((Object) this)) {
      return false;
    }
    if (this.getStartingLineNo() != other.getStartingLineNo()) {
      return false;
    }
    if (this.getNumberOfLines() != other.getNumberOfLines()) {
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
    return other instanceof DummyEdit;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getStartingLineNo();
    result = result * PRIME + this.getNumberOfLines();
    final Object $cursor = this.getCursor();
    result = result * PRIME + ($cursor == null ? 43 : $cursor.hashCode());
    return result;
  }

  public String toString() {
    return "DummyEdit(startingLineNo=" + this.getStartingLineNo() + ", numberOfLines=" + this.getNumberOfLines()
        + ", cursor=" + this.getCursor() + ")";
  }
}

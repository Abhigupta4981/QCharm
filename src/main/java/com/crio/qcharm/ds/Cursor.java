
/*
 *  DONT'T EDIT
 */

package com.crio.qcharm.ds;

import java.util.Objects;

public class Cursor {
  int lineNo;
  int columnNo;

  public Cursor(int lineNo, int columnNo) {
    this.lineNo = lineNo;
    this.columnNo = columnNo;
  }

  // WARNING: DONT' REMOVE ME
  public Cursor() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Cursor)) {
      return false;
    }
    Cursor cursor = (Cursor) o;
    return lineNo == cursor.lineNo &&
        columnNo == cursor.columnNo;
  }

  @Override
  public int hashCode() {
    return Objects.hash(lineNo, columnNo);
  }

  public int getLineNo() {
    return this.lineNo;
  }

  public int getColumnNo() {
    return this.columnNo;
  }

  public void setLineNo(int lineNo) {
    this.lineNo = lineNo;
  }

  public void setColumnNo(int columnNo) {
    this.columnNo = columnNo;
  }

  public String toString() {
    return "Cursor(lineNo=" + this.getLineNo() + ", columnNo=" + this.getColumnNo() + ")";
  }
}
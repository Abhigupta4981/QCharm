
/*
 *  DONT'T EDIT
 */

package com.crio.qcharm.ds;

public class SearchReplace implements  Edits {
  int startingLineNo;
  int length;
  Cursor cursor;
  String pattern;
  String newPattern;

  public SearchReplace(int startingLineNo, int length, Cursor cursor, String pattern,
      String newPattern) {
    this.startingLineNo = startingLineNo;
    this.length = length;
    this.cursor = cursor;
    this.pattern = pattern;
    this.newPattern = newPattern;
  }

  @Override
  public int getStartingLineNo() {
    return this.startingLineNo;
  }

  @Override
  public int getNumberOfLines() {
    return this.length;
  }

  @Override
  public Cursor getCursor() {
    return this.cursor;
  }

  public String getPattern() {
    return this.pattern;
  }

  public String getNewPattern() {
    return this.newPattern;
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

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  public void setNewPattern(String newPattern) {
    this.newPattern = newPattern;
  }

  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof SearchReplace)) {
      return false;
    }
    final SearchReplace other = (SearchReplace) o;
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
    final Object this$pattern = this.getPattern();
    final Object other$pattern = other.getPattern();
    if (this$pattern == null ? other$pattern != null : !this$pattern.equals(other$pattern)) {
      return false;
    }
    final Object this$newPattern = this.getNewPattern();
    final Object other$newPattern = other.getNewPattern();
    if (this$newPattern == null ? other$newPattern != null
        : !this$newPattern.equals(other$newPattern)) {
      return false;
    }
    return true;
  }

  protected boolean canEqual(final Object other) {
    return other instanceof SearchReplace;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getStartingLineNo();
    result = result * PRIME + this.getNumberOfLines();
    final Object $cursor = this.getCursor();
    result = result * PRIME + ($cursor == null ? 43 : $cursor.hashCode());
    final Object $pattern = this.getPattern();
    result = result * PRIME + ($pattern == null ? 43 : $pattern.hashCode());
    final Object $newPattern = this.getNewPattern();
    result = result * PRIME + ($newPattern == null ? 43 : $newPattern.hashCode());
    return result;
  }

  public String toString() {
    return "SearchReplace(startingLineNo=" + this.getStartingLineNo() + ", numberOfLines=" + this
        .getNumberOfLines() + ", cursor=" + this.getCursor() + ", pattern=" + this.getPattern()
        + ", newPattern=" + this.getNewPattern() + ")";
  }
}

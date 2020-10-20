
/*
 *  DONT'T EDIT
 */
package com.crio.qcharm.ds;

import java.util.List;

public class CopyBuffer {
  List<String> lines;

  public CopyBuffer(List<String> lines) {
    this.lines = lines;
  }

  public CopyBuffer() {
  }

  public void print() {
    System.out.println("CopyBuffer: start");
    for (int i = 0; i < lines.size(); ++i) {
      System.out.println(lines.get(i));
    }
    System.out.println("CopyBuffer: end");
  }

  public List<String> getLines() {
    return this.lines;
  }

  public void setLines(List<String> lines) {
    this.lines = lines;
  }

  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof CopyBuffer)) {
      return false;
    }
    final CopyBuffer other = (CopyBuffer) o;
    if (!other.canEqual((Object) this)) {
      return false;
    }
    final Object this$lines = this.getLines();
    final Object other$lines = other.getLines();
    if (this$lines == null ? other$lines != null : !this$lines.equals(other$lines)) {
      return false;
    }
    return true;
  }

  protected boolean canEqual(final Object other) {
    return other instanceof CopyBuffer;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $lines = this.getLines();
    result = result * PRIME + ($lines == null ? 43 : $lines.hashCode());
    return result;
  }

  public String toString() {
    return "CopyBuffer(lines=" + this.getLines() + ")";
  }
}


/*
 *  DONT'T EDIT
 */
package com.crio.qcharm.ds;

import java.util.ArrayList;
import java.util.List;

public class FileInfo {
  String fileName;
  List<String> lines;

  public FileInfo(String fileName, List<String> lines) {
    this.fileName = fileName;
    this.lines = new ArrayList<>(lines);
  }

  public FileInfo() {
  }

  public String getFileName() {
    return this.fileName;
  }

  public List<String> getLines() {
    return this.lines;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public void setLines(List<String> lines) {
    this.lines = lines;
  }

  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof FileInfo)) {
      return false;
    }
    final FileInfo other = (FileInfo) o;
    if (!other.canEqual((Object) this)) {
      return false;
    }
    final Object this$fileName = this.getFileName();
    final Object other$fileName = other.getFileName();
    if (this$fileName == null ? other$fileName != null : !this$fileName.equals(other$fileName)) {
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
    return other instanceof FileInfo;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $fileName = this.getFileName();
    result = result * PRIME + ($fileName == null ? 43 : $fileName.hashCode());
    final Object $lines = this.getLines();
    result = result * PRIME + ($lines == null ? 43 : $lines.hashCode());
    return result;
  }

  public String toString() {
    return "FileInfo(fileName=" + this.getFileName() + ", lines=" + this.getLines() + ")";
  }
}

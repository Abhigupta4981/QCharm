
/*
 *  DONT'T EDIT
 */

package com.crio.qcharm.request;

import javax.validation.constraints.NotNull;

public class UndoRequest {
  @NotNull
  String fileName;

  public UndoRequest(@NotNull String fileName) {
    this.fileName = fileName;
  }

  public UndoRequest() {
  }

  public @NotNull String getFileName() {
    return this.fileName;
  }

  public void setFileName(@NotNull String fileName) {
    this.fileName = fileName;
  }

  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof UndoRequest)) {
      return false;
    }
    final UndoRequest other = (UndoRequest) o;
    if (!other.canEqual((Object) this)) {
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
    return other instanceof UndoRequest;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $fileName = this.getFileName();
    result = result * PRIME + ($fileName == null ? 43 : $fileName.hashCode());
    return result;
  }

  public String toString() {
    return "UndoRequest(fileName=" + this.getFileName() + ")";
  }
}
  package com.crio.qcharm.request;


  import com.crio.qcharm.ds.CopyBuffer;
  import com.crio.qcharm.ds.Cursor;
  import com.crio.qcharm.ds.FileInfo;
  import java.util.Arrays;
  import java.util.List;
  import javax.validation.constraints.NotNull;
  import org.apache.commons.lang3.tuple.Pair;

  public class MasterRequest {
    @NotNull
    Cursor cursorStart;
    @NotNull
    Cursor cursorEnd;
    @NotNull
    int startingLineNo;
    @NotNull
    int endingLineNo;
    @NotNull
    String dataNow;
    @NotNull
    String fileName;
    @NotNull
    int start;
    @NotNull
    int end;
    int lineNumber;
    int numberOfLines;
    String pattern;
    String newPattern;

    static final String splitter = "\\n";

    public MasterRequest() {
    }
    //final String splitter = "\\n";

    public PageRequest getPageRequest() {
      return new PageRequest(
          this.lineNumber,
          this.fileName,
          this.numberOfLines,
          this.cursorStart);
    }

    public PageRequest getPageRequestPostEdit() {
      String[] lines = dataNow.split(splitter, -1);
      return new PageRequest(startingLineNo, fileName, lines.length, cursorStart);
    }

    public SearchRequest getSearchRequest() {
      return new SearchRequest(startingLineNo, pattern, fileName);
    }

    public FileInfo getFileInfo() {
      String[] lines = dataNow.split(splitter, -1);
      System.out.printf("lines len = %d\n", lines.length);
      System.out.println(dataNow);
      return new FileInfo(fileName, Arrays.asList(lines));
    }

    public EditRequest getEditRequest() {
      String[] lines = dataNow.split(splitter, -1);
      return new EditRequest(startingLineNo, endingLineNo, Arrays.asList(lines), fileName, cursorStart);
    }

    public Pair<CopyBuffer, List<String>>  getCopyBuffer() {
      String part1 = dataNow.substring(0, start);
      String part2 = dataNow.substring(start, end);
      String part3 = dataNow.substring(end);
      String rest = part1 + part3;
      List<String> restOfData = Arrays.asList(rest.split(splitter, -1));
      CopyBuffer copyBuffer = new CopyBuffer(Arrays.asList(part2.split(splitter, -1)));
      return Pair.of(copyBuffer, restOfData);
    }

    public void applyPaste(CopyBuffer copyBuffer) {
      String part1 = dataNow.substring(0, start);
      String part3 = dataNow.substring(end);

      StringBuffer stringBuffer = new StringBuffer("");
      List<String> lines = copyBuffer.getLines();
      for (int i = 0; i < lines.size(); ++i) {
        stringBuffer.append(lines.get(i));
        stringBuffer.append("\n");
      }

      String dataTobePasted = stringBuffer.toString();

      String dataAfterPaste = part1 + dataTobePasted + part3;

      dataNow = dataAfterPaste;
    }

    public SearchReplaceRequest getSearchReplaceRequest() {
      return new SearchReplaceRequest(startingLineNo, 0, pattern, newPattern, fileName);
    }

    public @NotNull Cursor getCursorStart() {
      return this.cursorStart;
    }

    public @NotNull Cursor getCursorEnd() {
      return this.cursorEnd;
    }

    public @NotNull int getStartingLineNo() {
      return this.startingLineNo;
    }

    public @NotNull int getEndingLineNo() {
      return this.endingLineNo;
    }

    public @NotNull String getDataNow() {
      return this.dataNow;
    }

    public @NotNull String getFileName() {
      return this.fileName;
    }

    public @NotNull int getStart() {
      return this.start;
    }

    public @NotNull int getEnd() {
      return this.end;
    }

    public int getLineNumber() {
      return this.lineNumber;
    }

    public int getNumberOfLines() {
      return this.numberOfLines;
    }

    public String getPattern() {
      return this.pattern;
    }

    public String getNewPattern() {
      return this.newPattern;
    }

    public void setCursorStart(@NotNull Cursor cursorStart) {
      this.cursorStart = cursorStart;
    }

    public void setCursorEnd(@NotNull Cursor cursorEnd) {
      this.cursorEnd = cursorEnd;
    }

    public void setStartingLineNo(@NotNull int startingLineNo) {
      this.startingLineNo = startingLineNo;
    }

    public void setEndingLineNo(@NotNull int endingLineNo) {
      this.endingLineNo = endingLineNo;
    }

    public void setDataNow(@NotNull String dataNow) {
      this.dataNow = dataNow;
    }

    public void setFileName(@NotNull String fileName) {
      this.fileName = fileName;
    }

    public void setStart(@NotNull int start) {
      this.start = start;
    }

    public void setEnd(@NotNull int end) {
      this.end = end;
    }

    public void setLineNumber(int lineNumber) {
      this.lineNumber = lineNumber;
    }

    public void setNumberOfLines(int numberOfLines) {
      this.numberOfLines = numberOfLines;
    }

    public void setPattern(String pattern) {
      System.out.printf("Setting new Pattern %s\n", pattern);
      this.pattern = pattern;
    }

    public void setNewPattern(String newPattern) {
      this.newPattern = newPattern;
    }

    public boolean equals(final Object o) {
      if (o == this) {
        return true;
      }
      if (!(o instanceof MasterRequest)) {
        return false;
      }
      final MasterRequest other = (MasterRequest) o;
      if (!other.canEqual((Object) this)) {
        return false;
      }
      final Object this$cursorStart = this.getCursorStart();
      final Object other$cursorStart = other.getCursorStart();
      if (this$cursorStart == null ? other$cursorStart != null
          : !this$cursorStart.equals(other$cursorStart)) {
        return false;
      }
      final Object this$cursorEnd = this.getCursorEnd();
      final Object other$cursorEnd = other.getCursorEnd();
      if (this$cursorEnd == null ? other$cursorEnd != null
          : !this$cursorEnd.equals(other$cursorEnd)) {
        return false;
      }
      if (this.getStartingLineNo() != other.getStartingLineNo()) {
        return false;
      }
      if (this.getEndingLineNo() != other.getEndingLineNo()) {
        return false;
      }
      final Object this$dataNow = this.getDataNow();
      final Object other$dataNow = other.getDataNow();
      if (this$dataNow == null ? other$dataNow != null : !this$dataNow.equals(other$dataNow)) {
        return false;
      }
      final Object this$fileName = this.getFileName();
      final Object other$fileName = other.getFileName();
      if (this$fileName == null ? other$fileName != null : !this$fileName.equals(other$fileName)) {
        return false;
      }
      if (this.getStart() != other.getStart()) {
        return false;
      }
      if (this.getEnd() != other.getEnd()) {
        return false;
      }
      if (this.getLineNumber() != other.getLineNumber()) {
        return false;
      }
      if (this.getNumberOfLines() != other.getNumberOfLines()) {
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
      return other instanceof MasterRequest;
    }

    public int hashCode() {
      final int PRIME = 59;
      int result = 1;
      final Object $cursorStart = this.getCursorStart();
      result = result * PRIME + ($cursorStart == null ? 43 : $cursorStart.hashCode());
      final Object $cursorEnd = this.getCursorEnd();
      result = result * PRIME + ($cursorEnd == null ? 43 : $cursorEnd.hashCode());
      result = result * PRIME + this.getStartingLineNo();
      result = result * PRIME + this.getEndingLineNo();
      final Object $dataNow = this.getDataNow();
      result = result * PRIME + ($dataNow == null ? 43 : $dataNow.hashCode());
      final Object $fileName = this.getFileName();
      result = result * PRIME + ($fileName == null ? 43 : $fileName.hashCode());
      result = result * PRIME + this.getStart();
      result = result * PRIME + this.getEnd();
      result = result * PRIME + this.getLineNumber();
      result = result * PRIME + this.getNumberOfLines();
      final Object $pattern = this.getPattern();
      result = result * PRIME + ($pattern == null ? 43 : $pattern.hashCode());
      final Object $newPattern = this.getNewPattern();
      result = result * PRIME + ($newPattern == null ? 43 : $newPattern.hashCode());
      return result;
    }

    public String toString() {
      return "MasterRequest(cursorStart=" + this.getCursorStart() + ", cursorEnd=" + this
          .getCursorEnd() + ", startingLineNo=" + this.getStartingLineNo() + ", endingLineNo="
          + this
          .getEndingLineNo() + ", dataNow=" + this.getDataNow() + ", fileName=" + this.getFileName()
          + ", start=" + this.getStart() + ", end=" + this.getEnd() + ", lineNumber=" + this
          .getLineNumber() + ", numberOfLines=" + this.getNumberOfLines() + ", pattern=" + this
          .getPattern() + ", newPattern=" + this.getNewPattern() + ")";
    }

    public UndoRequest getUndoRequest() {
      return new UndoRequest(fileName);
    }
  };

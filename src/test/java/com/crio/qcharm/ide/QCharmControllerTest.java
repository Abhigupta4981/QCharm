package com.crio.qcharm.ide;

import com.crio.qcharm.ds.RunCodeArgs;
import com.crio.qcharm.ds.RunCodeOutput;
import com.crio.qcharm.log.UncaughtExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {QCharmController.class})
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@AutoConfigureMockMvc
class QCharmControllerTest {
  private MockMvc mvc;
  private ObjectMapper objectMapper;

  final String LOAD_FILE_URI = "/qcharm/load_file";
  final String RUN_FILE_URI = "/qcharm/run_file_internal";
  final String COPY_BUFFER_URI = "/qcharm/copy_buffer";

  @InjectMocks
  private QCharmController qCharmController;

  @BeforeEach
  public void setup() {
    objectMapper = new ObjectMapper();

    MockitoAnnotations.initMocks(this);

    mvc = MockMvcBuilders.standaloneSetup(qCharmController).build();

    Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());

  }
//
//  FileInfo getLargeSampleFileInfo(String fileName, int n) {
//    List<String> testLines = new ArrayList<>();
//
//    for (int i = 0; i < n; ++i) {
//      StringBuffer buffer = new StringBuffer("lineno");
//      buffer.append(i);
//      testLines.add(buffer.toString());
//    }
//    return new FileInfo(fileName, testLines);
//  }
//
//  FileInfo getSampleFileInfo() {
//    List<String> testLines = new ArrayList<>();
//    testLines.add("def sqr(x):");
//    testLines.add(" return x * x");
//
//    return new FileInfo("testfile.txt", testLines);
//  }
//
//  // CRIO_SOLUTION_AND_STUB_START_MODULE_LOAD_FILE
//  @Test
//  void loadFileTest() throws Exception {
//    FileInfo fileInfo = getSampleFileInfo();
//
//    URI uri = UriComponentsBuilder
//        .fromPath(LOAD_FILE_URI)
//        .build().toUri();
//
//    System.out.println("URI string = " + uri.toString());
//    String fileInfoString = objectMapper.writeValueAsString(fileInfo);
//
//    final MvcResult mvcResult = mvc.perform(post(LOAD_FILE_URI)
//        .contentType(MediaType.APPLICATION_JSON).content(fileInfoString)
//        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
//
//    String content = mvcResult.getResponse().getContentAsString();
//
//    Page page = objectMapper.readValue(content, Page.class);
//
//    assertEquals(page.getLines(), fileInfo.getLines());
//    System.out.println(content);
//  }
//
//  @Test
//  void getPageTest() {
//    assertEquals(7 * 7, 49);
//  }
//
//  @AfterEach
//  void tearDown() {
//  }
//
//  @Test
//  void getSourceFileHandler() {
//  }
//
//  @Test
//  void addToQueue() {
//  }
//  // CRIO_SOLUTION_AND_STUB_END_MODULE_LOAD_FILE
//
//  // CRIO_SOLUTION_AND_STUB_START_MODULE_CUT_COPY_PASTE
//  @Test
//  void set_copy_buffer() throws Exception {
//    FileInfo fileInfo = getLargeSampleFileInfo("test", 100);
//
//    List<String> lines = fileInfo.getLines().subList(4,5);
//
//    CopyBuffer copyBuffer = new CopyBuffer(lines);
//    URI uri = UriComponentsBuilder
//        .fromPath(COPY_BUFFER_URI)
//        .build().toUri();
//
//    System.out.println("URI string = " + uri.toString());
//    String copyBufferString = objectMapper.writeValueAsString(copyBuffer);
//
//    final ResultActions resultActions = mvc.perform(post(COPY_BUFFER_URI)
//        .contentType(MediaType.APPLICATION_JSON).content(copyBufferString)
//        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
//  }
//  // CRIO_SOLUTION_AND_STUB_END_MODULE_CUT_COPY_PASTE
//
//  @Test
//  void get_copy_buffer() {
//  }
//
//  @Test
//  void loadFile() {
//  }
//
//  @Test
//  void getPrevLines() {
//
//  }
//
//  @Test
//  void getNextLines() {
//  }
//
//  @Test
//  void getLines() {
//  }
//
//  @Test
//  void search() {
//  }
//
//  @Test
//  void editLines() {
//  }
//
//  @Test
//  void searchReplace() {
//  }
//
//  @Test
//  void undo() {
//  }

  @Test
  void testRunProgram() throws Exception {
    String code = "public class SimpleJavaClass {\n"
        + "\n"
        + "  public static void main(String[] args) {\n"
        + "    System.out.println(\"hello\");\n"
        + "  }\n"
        + "}";
    RunCodeArgs args = new RunCodeArgs();
    args.setFileName("SimpleJavaClass.java");
    args.setLanguage("java");
    args.setLines(Arrays.asList(code));

    URI uri = UriComponentsBuilder
        .fromPath(RUN_FILE_URI)
        .build().toUri();

    System.out.println("URI string = " + uri.toString());
    String fileInfoString = objectMapper.writeValueAsString(args);

    final MvcResult mvcResult = mvc.perform(post(RUN_FILE_URI)
        .contentType(MediaType.APPLICATION_JSON).content(fileInfoString)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

    String content = mvcResult.getResponse().getContentAsString();

    RunCodeOutput output = objectMapper.readValue(content, RunCodeOutput.class);

    assertEquals(output.getOutput(),"hello\n");
  }

  @Test
  void testRunProgramWithInputParameters() throws Exception {
    String code = "import java.util.Scanner;\n"
        + "\n"
        + "public class SimpleJavaClass {\n"
        + "  public static void main(String[] args) {\n"
        + "    Scanner myInput = new Scanner( System.in );\n"
        + "    String name = myInput.next();\n"
        + "    String surname = myInput.next();\n"
        + "    System.out.println(\"hello \" + name + \" \" + surname);\n"
        + "  }\n"
        + "}\n";
    RunCodeArgs args = new RunCodeArgs();
    args.setFileName("SimpleJavaClass.java");
    args.setLanguage("java");
    args.setLines(Arrays.asList(code));
    args.setInputs(new String[]{"anand", "vaidya"});

    URI uri = UriComponentsBuilder
        .fromPath(RUN_FILE_URI)
        .build().toUri();

    System.out.println("URI string = " + uri.toString());
    String fileInfoString = objectMapper.writeValueAsString(args);

    final MvcResult mvcResult = mvc.perform(post(RUN_FILE_URI)
        .contentType(MediaType.APPLICATION_JSON).content(fileInfoString)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

    String content = mvcResult.getResponse().getContentAsString();

    RunCodeOutput output = objectMapper.readValue(content, RunCodeOutput.class);

    assertEquals(output.getOutput(),"hello anand vaidya\n");
  }


  @Test
  void testRunProgramWithInputParametersPython() throws Exception {
    String code = "val = input(\"Enter your value: \")\n"
        + "print(\"hello \"+ val)";
    RunCodeArgs args = new RunCodeArgs();
    args.setFileName("SimplePythonCode.py");
    args.setLanguage("python");
    args.setLines(Arrays.asList(code));
    args.setInputs(new String[]{"anand"});

    URI uri = UriComponentsBuilder
        .fromPath(RUN_FILE_URI)
        .build().toUri();

    System.out.println("URI string = " + uri.toString());
    String fileInfoString = objectMapper.writeValueAsString(args);

    final MvcResult mvcResult = mvc.perform(post(RUN_FILE_URI)
        .contentType(MediaType.APPLICATION_JSON).content(fileInfoString)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

    String content = mvcResult.getResponse().getContentAsString();

    RunCodeOutput output = objectMapper.readValue(content, RunCodeOutput.class);

    assertEquals(output.getOutput(),"Enter your value: hello anand\n");
  }




  @Test
  void testRunProgramCpp() throws Exception {
    String code = "#include<stdio.h>\n"
        + "\n"
        + "int main()\n"
        + "\n"
        + "{\n"
        + "\n"
        + "            printf(\"Hello World\\n\");\n"
        + "\n"
        + "            return 0;\n"
        + "\n"
        + "}";
    RunCodeArgs args = new RunCodeArgs();
    args.setFileName("SampleCppClass.cpp");
    args.setLanguage("c++");
    args.setLines(Arrays.asList(code));
    args.setInputs(new String[]{});

    URI uri = UriComponentsBuilder
        .fromPath(RUN_FILE_URI)
        .build().toUri();

    System.out.println("URI string = " + uri.toString());
    String fileInfoString = objectMapper.writeValueAsString(args);

    final MvcResult mvcResult = mvc.perform(post(RUN_FILE_URI)
        .contentType(MediaType.APPLICATION_JSON).content(fileInfoString)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

    String content = mvcResult.getResponse().getContentAsString();

    RunCodeOutput output = objectMapper.readValue(content, RunCodeOutput.class);

    assertEquals(output.getOutput(),"Hello World\n");
  }


  @Test
  void testRunProgramC() throws Exception {
    String code = "#include<stdio.h>\n"
        + "\n"
        + "int main()\n"
        + "\n"
        + "{\n"
        + "\n"
        + "            printf(\"Hello World\\n\");\n"
        + "\n"
        + "            return 0;\n"
        + "\n"
        + "}";
    RunCodeArgs args = new RunCodeArgs();
    args.setFileName("SampleCppClass.c");
    args.setLanguage("c");
    args.setLines(Arrays.asList(code));
    args.setInputs(new String[]{});

    URI uri = UriComponentsBuilder
        .fromPath(RUN_FILE_URI)
        .build().toUri();

    System.out.println("URI string = " + uri.toString());
    String fileInfoString = objectMapper.writeValueAsString(args);

    final MvcResult mvcResult = mvc.perform(post(RUN_FILE_URI)
        .contentType(MediaType.APPLICATION_JSON).content(fileInfoString)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

    String content = mvcResult.getResponse().getContentAsString();

    RunCodeOutput output = objectMapper.readValue(content, RunCodeOutput.class);

    assertEquals(output.getOutput(),"Hello World\n");
  }
}

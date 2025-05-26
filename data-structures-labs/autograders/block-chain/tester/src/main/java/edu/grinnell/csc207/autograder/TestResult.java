package edu.grinnell.csc207.autograder;

import com.google.gson.Gson;

public class TestResult {
  public final String name;
  public final String status;
  public final String output;

  private TestResult(String name, boolean passed, String details) {
    this.name = name;
    status = passed ? "success" : "failed";
    output = details == null ? "" : details;
  }

  public static TestResult passed(String name) {
    return new TestResult(name, true, null);
  }

  public static TestResult failed(String name, String details) {
    return new TestResult(name, false, details);
  }

  public String toString() {
    return String.format(
      "[\"%s\" %s%s]", name,
      status.equals("success") ? "✅" : "❌",
      output.equals("") ? "" : ": " + output);
  }

  public String toJSONString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}

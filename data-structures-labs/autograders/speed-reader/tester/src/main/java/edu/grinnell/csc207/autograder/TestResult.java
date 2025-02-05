package edu.grinnell.csc207.autograder;

public class TestResult {
  public final String name;
  public final boolean passed;
  public final String details;

  private TestResult(String name, boolean passed, String details) {
    this.name = name;
    this.passed = passed;
    this.details = details;
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
      passed ? "✅" : "❌",
      details == null ? "" : ": " + details);
  }

  public String toJSONString() {
    return String.format(
      "{\"name\":\"%s\",\"status\":\"%s\",\"output\":\"%s\"}",
      name,
      passed ? "success" : "failed",
      details == null ? "" : details);
  }
}

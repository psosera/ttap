package edu.grinnell.csc207.autograder;

import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.LauncherSession;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.TestPlan.Visitor;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

public class Autograder {
  public static void main(String[] args) {

    Map<String, TestResult> results = new HashMap<>();
    
    LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
      .selectors(
        selectPackage("edu.grinnell.csc207.autograder"))
      .filters(
        includeClassNamePatterns(".*Tests"))
      .build();

    SummaryGeneratingListener listener = new SummaryGeneratingListener();

    try (LauncherSession session = LauncherFactory.openSession()) {
      Launcher launcher = session.getLauncher();
      launcher.registerTestExecutionListeners(listener);
      TestPlan testPlan = launcher.discover(request);
      launcher.execute(testPlan);

      testPlan.accept(new Visitor() {
        @Override
        public void visit(TestIdentifier id) {
          if (id.isTest()) {
            results.put(id.getUniqueId(), TestResult.passed(id.getDisplayName()));
          }
        }
      });
    }

    TestExecutionSummary summary = listener.getSummary();
    List<TestExecutionSummary.Failure> failures = summary.getFailures();
    for (TestExecutionSummary.Failure failure : failures) {
      TestIdentifier id = failure.getTestIdentifier();
      results.put(id.getUniqueId(), TestResult.failed(id.getDisplayName(), failure.getException().getMessage()));
    }

    Iterator<Map.Entry<String, TestResult>> entries = results.entrySet().iterator();
    if (!entries.hasNext()) {
      throw new RuntimeException("No tests found!");
    } else {
      String testEntries = entries.next().getValue().toJSONString();
      while (entries.hasNext()) {
        testEntries += "," + entries.next().getValue().toJSONString();
      }
      System.out.printf("[%s]\n", testEntries);
    }
  }
}

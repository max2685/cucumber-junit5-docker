package com.neotech;

import com.codeborne.selenide.WebDriverRunner;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.junit.jupiter.api.extension.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Log
public class TestResultLoggerExtension implements TestWatcher, AfterAllCallback, BeforeEachCallback, AfterTestExecutionCallback {
    private List<TestResultStatus> testResultsStatus = new ArrayList<>();

    private enum TestResultStatus {
        SUCCESSFUL, ABORTED, FAILED, DISABLED
    }

    @Override
    @SneakyThrows
    public void afterTestExecution(ExtensionContext extensionContext) {
        WebDriverRunner.driver().getWebDriver().quit();
    }

    @Override
    public void afterAll(ExtensionContext context) {
        Map<TestResultStatus, Long> summary = testResultsStatus.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        log.info("Test summary: " + context.getDisplayName() + " -> " + summary.toString());
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        log.info("---------------------- Starting: " + context.getDisplayName() + " ----------------------");
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        log.info("Test: " + context.getDisplayName()
                + " : is disabled with the following reason -> " + reason.orElse("No reason"));
        testResultsStatus.add(TestResultStatus.DISABLED);
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        log.info(context.getDisplayName() + ": passed");
        testResultsStatus.add(TestResultStatus.SUCCESSFUL);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        log.info(context.getDisplayName() + ": aborted");
        testResultsStatus.add(TestResultStatus.ABORTED);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        log.info(context.getDisplayName() + ": failed");
        testResultsStatus.add(TestResultStatus.FAILED);
    }
}

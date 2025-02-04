package org.example.utils;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.Optional;

public class TestResultLogger implements TestWatcher {
    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        System.out.println("Test '" + context.getDisplayName() + "' ABORTED.");
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        System.out.println("Test '" + context.getDisplayName() + "' DISABLED.");
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        System.out.println("Test '" + context.getDisplayName() + "' FAILED.");
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        System.out.println("Test '" + context.getDisplayName() + "' PASSED.");
    }
}

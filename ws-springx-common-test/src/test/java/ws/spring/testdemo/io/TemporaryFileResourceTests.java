/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.testdemo.io;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ws.spring.io.TemporaryFileResource;

import java.io.IOException;
import java.lang.ref.Cleaner;
import java.lang.ref.WeakReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author WindShadow
 * @version 2026-02-01
 */

@Slf4j
public class TemporaryFileResourceTests {

    private static final Cleaner CLEANER = Cleaner.create();

    @Test
    void autoCleanTest() throws IOException, InterruptedException {

        Path tmpDir = Paths.get(System.getProperty("java.io.tmpdir"));
        Path filePath = Files.createTempFile(tmpDir, "test", ".tmp");
        Assertions.assertTrue(Files.exists(filePath));
        log.info("Created temporary file {}", filePath);
        try {
            AtomicBoolean clean = new AtomicBoolean(false);
            TemporaryFileResource resource = new TemporaryFileResource(filePath) {

                {
                    CLEANER.register(this, () -> clean.set(true));
                }
            };
            WeakReference<TemporaryFileResource> reference = new WeakReference<>(resource);
            resource = null;
            System.gc();
            while (reference.get() != null || !clean.get()) ;
            Thread.sleep(1000L);
            Assertions.assertFalse(Files.exists(filePath));
        } finally {
            Files.deleteIfExists(filePath);
        }
    }
}

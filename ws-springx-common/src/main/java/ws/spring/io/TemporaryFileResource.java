/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.io;

import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.io.IOException;
import java.lang.ref.Cleaner;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author WindShadow
 * @version 2026-02-01
 */

public class TemporaryFileResource extends FileSystemResource {

    private static final Cleaner CLEANER = Cleaner.create();

    private record InnerCleaner(Path file) implements Runnable {

        @Override
        public void run() {

            if (file == null) return;
            try {
                Files.deleteIfExists(file);
            } catch (IOException e) {
                throw new IllegalStateException("Error while deleting temporary file: " + file, e);
            }
        }
    }

    private final Cleaner.Cleanable cleanable = CLEANER.register(this, new InnerCleaner(this.getFilePath()));

    public TemporaryFileResource(String path) {
        super(path);
    }

    public TemporaryFileResource(File file) {
        super(file);
    }

    public TemporaryFileResource(Path filePath) {
        super(filePath);
    }

    public TemporaryFileResource(FileSystem fileSystem, String path) {
        super(fileSystem, path);
    }

    public void release() {
        cleanable.clean();
    }
}

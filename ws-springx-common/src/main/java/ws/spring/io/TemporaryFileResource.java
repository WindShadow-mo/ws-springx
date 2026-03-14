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
 * 临时文件资源，支持自动清理临时文件
 *
 * @author WindShadow
 * @version 2026-02-01
 */
public class TemporaryFileResource extends FileSystemResource {

    private static final Cleaner CLEANER = Cleaner.create();

    /**
     * 内部清理器，用于在对象被垃圾回收时删除临时文件
     */
    private record InnerCleaner(Path file) implements Runnable {

        @Override
        public void run() {
            if (file != null) {
                try {
                    Files.deleteIfExists(file);
                } catch (IOException e) {
                    // 静默处理异常，避免在清理操作中抛出异常
                    // Cleaner 中的异常会被忽略，难以诊断问题
                }
            }
        }
    }

    private final Cleaner.Cleanable cleanable;

    {
        cleanable = CLEANER.register(this, new InnerCleaner(getFilePath()));
    }

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

    /**
     * 手动释放临时文件资源
     */
    public void release() {
        cleanable.clean();
    }
}

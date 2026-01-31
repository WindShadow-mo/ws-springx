/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.util;

import org.jspecify.annotations.Nullable;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author WindShadow
 * @version 2026-02-01
 */

public class FilenameValidator {

    /**
     * 默认明确禁止的字符
     */
    private static final Set<Character> DEFAULT_ILLEGAL_CHARS = new HashSet<>(Arrays.asList('\\', '/', ':', '*', '?', '"', '<', '>', '|'));
    private static final FilenameValidator DEFAULT_VALIDATOR = ofIllegalChars(DEFAULT_ILLEGAL_CHARS);

    /**
     * 非法字符集
     */
    private final Set<Character> illegalChars;

    /**
     * 保留名称
     */
    private final Set<String> reservedNames;

    public FilenameValidator(Set<Character> illegalChars, Set<String> reservedNames) {

        Objects.requireNonNull(illegalChars);
        Objects.requireNonNull(reservedNames);
        this.illegalChars = illegalChars.isEmpty() ? Collections.emptySet() : illegalChars.stream()
                .flatMap(c -> Stream.of(c, Character.toUpperCase(c), Character.toLowerCase(c)))
                .collect(Collectors.toSet());
        this.reservedNames = reservedNames.isEmpty() ? Collections.emptySet() : reservedNames.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toSet());
    }

    public boolean validate(@Nullable String filename) {

        // 空值
        if (filename == null || filename.isEmpty()) return false;

        // 以空格开头或结尾
        if (' ' == filename.charAt(0)) return false;
        if (' ' == filename.charAt(filename.length() - 1)) return false;

        // 路径准确且无穿透
        try {
            Path path = Paths.get(filename);
            if (!path.equals(path.getFileName())) return false;
        } catch (InvalidPathException e) {
            return false;
        }

        // 保留文件名
        String upperCaseName = filename.toUpperCase();
        if (reservedNames.contains(upperCaseName)) return false;

        // 非法字符 或 全为点号
        boolean allDots = true;
        for (int i = 0; i < filename.length(); i++) {
            char c = filename.charAt(i);
            if (illegalChars.contains(c)) return false;
            if ('.' != c) {
                allDots = false;
            }
        }
        return !allDots;
    }

    public static FilenameValidator defaultValidator() {
        return DEFAULT_VALIDATOR;
    }

    public static FilenameValidator unrestraintValidator() {
        return of(null, null);
    }

    public static FilenameValidator ofIllegalChars(@Nullable Set<Character> illegalChars) {
        return of(illegalChars, null);
    }

    public static FilenameValidator ofReservedNames(@Nullable Set<String> reservedNames) {
        return of(null, reservedNames);
    }

    public static FilenameValidator of(@Nullable Set<Character> illegalChars, @Nullable Set<String> reservedNames) {

        illegalChars = illegalChars == null ? Collections.emptySet() : illegalChars;
        reservedNames = reservedNames == null ? Collections.emptySet() : reservedNames;
        return new FilenameValidator(illegalChars, reservedNames);
    }
}

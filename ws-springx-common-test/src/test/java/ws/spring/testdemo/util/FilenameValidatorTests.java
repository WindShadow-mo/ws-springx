/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.testdemo.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ws.spring.util.FilenameValidator;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author WindShadow
 * @version 2026-02-01
 */
public class FilenameValidatorTests {

    private final Random random = new Random();

    private FilenameValidator validator;

    @Test
    void defaultValidTest() {

        validator = FilenameValidator.defaultValidator();
        baseTest();
    }

    @Test
    void customValidTest() {

        Set<String> reservedNames = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            reservedNames.add("Test-" + random.nextInt());
        }

        validator = FilenameValidator.ofReservedNames(reservedNames);
        baseTest();
        for (String reservedName : reservedNames) {
            Assertions.assertFalse(validator.validate(reservedName));
            Assertions.assertFalse(validator.validate(reservedName.toUpperCase()));
            Assertions.assertFalse(validator.validate(reservedName.toLowerCase()));
        }
    }

    private void baseTest() {

        Assertions.assertTrue(validator.validate("test.txt"));
        Assertions.assertTrue(validator.validate("test"));
        Assertions.assertTrue(validator.validate("..test.txt"));
        Assertions.assertTrue(validator.validate("..test."));
        Assertions.assertTrue(validator.validate("te;st.txt"));

        Assertions.assertFalse(validator.validate(null));
        Assertions.assertFalse(validator.validate(""));
        Assertions.assertFalse(validator.validate(" "));
        Assertions.assertFalse(validator.validate("test.txt "));
        Assertions.assertFalse(validator.validate(" test.txt"));
        Assertions.assertFalse(validator.validate("/test.txt"));
        Assertions.assertFalse(validator.validate("\\test.txt"));
        Assertions.assertFalse(validator.validate("te\"st.txt"));
        Assertions.assertFalse(validator.validate("te:st.txt"));
        Assertions.assertFalse(validator.validate("te*st.txt"));
        Assertions.assertFalse(validator.validate("te?st.txt"));
        Assertions.assertFalse(validator.validate("te>st.txt"));
        Assertions.assertFalse(validator.validate("te<st.txt"));
        Assertions.assertFalse(validator.validate("te|st.txt"));
        Assertions.assertFalse(validator.validate("\\test.txt"));
        Assertions.assertFalse(validator.validate("dir/test.txt"));
        Assertions.assertFalse(validator.validate("../test.txt"));
        Assertions.assertFalse(validator.validate("."));
        Assertions.assertFalse(validator.validate(".."));
        Assertions.assertFalse(validator.validate("..."));
    }
}

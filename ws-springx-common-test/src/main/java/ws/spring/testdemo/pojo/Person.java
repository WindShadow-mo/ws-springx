/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.testdemo.pojo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author WindShadow
 * @version 2023-07-21
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Person {

    @Email
    @NotBlank
    private String email;
}

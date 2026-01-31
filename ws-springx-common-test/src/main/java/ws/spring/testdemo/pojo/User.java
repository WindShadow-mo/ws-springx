/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.testdemo.pojo;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author WindShadow
 * @verion 2020/9/20.
 */

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    private String name;
    private Integer age;
    private String desc;

    @Email
    private String email;
}

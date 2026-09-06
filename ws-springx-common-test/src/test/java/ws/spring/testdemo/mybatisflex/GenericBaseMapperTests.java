/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.testdemo.mybatisflex;

import com.mybatisflex.core.query.QueryWrapper;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ws.spring.mybatis.mapper.ImportMapper;
import ws.spring.testdemo.anno.EnableMybatisFlexTest;
import ws.spring.testdemo.mybatisflex.mapper.EmployeeBaseMapper;
import ws.spring.testdemo.mybatisflex.pojo.Employee;

/**
 * @author WindShadow
 * @version 2025-07-13.
 */
@ActiveProfiles({"datasource", "mybatisflex"})
@SpringBootTest(classes = GenericBaseMapperTests.Config.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class GenericBaseMapperTests {

    @ImportMapper(mapper = EmployeeBaseMapper.class)
    @EnableMybatisFlexTest
    @SpringBootConfiguration
    static class Config {}

    @Autowired
    private EmployeeBaseMapper employeeMapper;

    @Test
    void selectOneTest() {

        Employee tom = employeeMapper.selectOneByQuery(QueryWrapper.create().where(Employee::getEmpId).eq(1001));
        Assertions.assertEquals("tom", tom.getEmpName());
        Assertions.assertThrows(TooManyResultsException.class, () -> employeeMapper.selectOneByQuery(QueryWrapper.create()));
        Assertions.assertThrows(TooManyResultsException.class, () -> employeeMapper.selectOneByQueryAs(QueryWrapper.create(), Employee.class));
        Assertions.assertThrows(TooManyResultsException.class, () -> employeeMapper.selectOneWithRelationsByQuery(QueryWrapper.create()));
        Assertions.assertThrows(TooManyResultsException.class, () -> employeeMapper.selectOneWithRelationsByQueryAs(QueryWrapper.create(), Employee.class));
    }
}

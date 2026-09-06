/*
 * SPDX-License-Identifier: MIT
 * Copyright (c) 2025 莫千风
 */

package ws.spring.testdemo.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import ws.spring.mybatis.mapper.EnhancedMapperFactory;
import ws.spring.mybatis.mapper.ImportMapper;
import ws.spring.testdemo.anno.EnableMybatisTest;
import ws.spring.testdemo.mybatis.mapper.EmployeeMapper;
import ws.spring.testdemo.mybatis.mapper.EmployeeMapperByImport;
import ws.spring.testdemo.mybatis.mapper.EmployeeMapperForImport;
import ws.spring.testdemo.mybatis.mapper.scan.EmployeeMapperForScan;

/**
 * @author WindShadow
 * @version 2025-03-30
 */
@ActiveProfiles({"datasource", "mybatis"})
@SpringBootTest(classes = EnhancedMapperFactoryTests.Config.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class EnhancedMapperFactoryTests {

    @ImportMapper(mapper = EmployeeMapperByImport.class)
    @ImportMapper(mapper = EmployeeMapperForImport.class, location = "classpath:/mapper/EmployeeMapperForImport.xml")
    @MapperScan("ws.spring.testdemo.mybatis.mapper.scan")
    @EnableMybatisTest
    @SpringBootConfiguration
    static class Config {

        @Bean
        public FactoryBean<EmployeeMapper> employeeMapper(SqlSessionFactory sqlSessionFactory) {

            EnhancedMapperFactory<EmployeeMapper> mapperFactory = new EnhancedMapperFactory<>(EmployeeMapper.class);
            mapperFactory.setSqlSessionFactory(sqlSessionFactory);
            return mapperFactory;
        }
    }


    @Autowired
    private ApplicationContext context;

    @Test
    void enhancedMapperFactoryRegisterTest() {

        Assertions.assertDoesNotThrow(() -> context.getBean(EmployeeMapperForScan.class).countEmployees());
        Assertions.assertDoesNotThrow(() -> context.getBean(EmployeeMapper.class).countEmployees());
        Assertions.assertDoesNotThrow(() -> context.getBean(EmployeeMapperForImport.class).countEmployees());
        Assertions.assertDoesNotThrow(() -> context.getBean(EmployeeMapperByImport.class).countEmployees());
    }
}

package com.dascom.common.quartz;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.quartz.utils.ConnectionProvider;

import com.dascom.quartz.ApplicationContextHolder;

public class SuiteQuartzDataSourceProvider implements ConnectionProvider {

    private DataSource dataSource;
    
    @Override
    public Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("获取数据库连接失败，没有发现DataSource。");
        }
        return dataSource.getConnection();
    }

    @Override
    public void shutdown() throws SQLException {
    }

    @Override
    public void initialize() throws SQLException {
        dataSource = ApplicationContextHolder.getBean(DataSource.class);
    }

}

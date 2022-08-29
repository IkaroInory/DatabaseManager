package com.arcticfox.dbm;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.*;

@SuppressWarnings("unused")
public final class Database {
    private final DatabaseDriver driver;
    private final String url;
    private boolean connect;

    private Connection connection;
    private Statement statement;

    public Database(DatabaseDriver driver, String ip, String databaseName) { this(driver, ip, 3306, databaseName); }
    public Database(DatabaseDriver driver, String ip, int port, String databaseName) {
        this.driver = driver;
        this.url = "jdbc:mysql://" + ip + ":" + port + "/"
                + URLEncoder.encode(databaseName, StandardCharsets.UTF_8) + "?"         // 数据库名含空格，用Url转义
                + "allowPublicKeyRetrieval=true&"
                + "useSSL=false&"
                + "serverTimezone=UTC";
        this.connect = false;
    }

    public String getUrl() { return url; }
    public boolean isConnect() { return connect; }

    public void open(String user, String password) {
        if (connect) return;

        connect = true;
        try {
            Class.forName(driver.url);
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            connect = false;
        }
    }
    public void close() {
        if (!connect) return;

        connect = false;
        try {
            statement.close();
            connection.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            connect = true;
        }
    }

    public boolean execute(String sql) throws SQLException { return statement.execute(sql); }
    public ResultSet query(String sql) {
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    public boolean update(String sql) {
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

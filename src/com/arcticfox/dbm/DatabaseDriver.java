package com.arcticfox.dbm;

@SuppressWarnings("unused")
public enum DatabaseDriver {
    Access("sun.jdbc.odbc.JdbcOdbcDriver"),
    MySQL_("com.mysql.jdbc.Driver"),
    MySQL("com.mysql.cj.jdbc.Driver"),
    Oracle("oracle.jdbc.driver.OracleDriver"),
    SQLServer("com.microsoft.sqlserver.jdbc.SQLServerDriver");

    public final String url;

    DatabaseDriver(String url) { this.url = url; }
}

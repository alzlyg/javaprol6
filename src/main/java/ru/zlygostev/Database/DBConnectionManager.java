package ru.zlygostev.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

// Класс подсоединяется к БД
public class DBConnectionManager {
    private DBConfig config;
    private Connection connection;

    public DBConnectionManager() { this(new DefaultDBConfig()); }
    public DBConnectionManager(final DBConfig config) { this.config = config; }
    public Connection getConnection() { return connection; }
    public Statement createStatement() throws Exception {
        return connection.createStatement();
    }
    public PreparedStatement createPreparedStatement(final String sql) throws Exception {
        return connection.prepareStatement(sql);
    }
    public void connect() throws Exception {
        // Загрузка JDBC драйвера в память
        Class.forName(config.getDriverName());
        // Через обёртку DriverManager происходит подключение к БД
        connection = DriverManager.getConnection(config.toString(), config.getUsername(), config.getPassword());
    }
   public void disconnect() throws Exception {
       connection.close();
   }
}
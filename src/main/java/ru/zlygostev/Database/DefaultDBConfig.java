package ru.zlygostev.Database;

// Класс конфигурации БД по умолчанимю
public class DefaultDBConfig extends DBConfig {
    {
        setDriverName("com.mysql.jdbc.Driver");
        setDatabaseType("mysql");
        setDatabaseName("mysql");
        setHost("localhost");
        setPort(3306);
        setUsername("root");
        setPassword("root");
    }
}

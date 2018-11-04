package ru.zlygostev.Database;

// Класс хранит конфигурацию БД
public class DBConfig {
    private String driverName;
    private String databaseName;
    private String databaseType;
    private String host;
    private String username;
    private String password;
    private Integer port;

    public String getDriverName() {return driverName;}
    public void setDriverName(String driverName) {this.driverName = driverName;}
    public String getDatabaseName() {return databaseName;}
    public void setDatabaseName(String databaseName) {this.databaseName = databaseName;}
    public String getDatabaseType() {return databaseType;}
    public void setDatabaseType(String databaseType) {this.databaseType = databaseType;}
    public String getHost() {return host;}
    public void setHost(String host) {this.host = host;}
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public Integer getPort() {return port;}
    public void setPort(Integer port) {this.port = port;}

    @Override
    public String toString() {
        return "jdbc:" + databaseType + "://" + host + ":" + port + "/" + databaseName;
    }


}

package ru.zlygostev.dto;

// Data Tranfer Object - воспроизводит структуру таблицы в БД
public class ProjectDTO {
    private String login;
    private String password;
    private String nik;

    public ProjectDTO() {
    }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getNik() { return nik; }
    public void setNik(String nik) { this.nik = nik; }
}

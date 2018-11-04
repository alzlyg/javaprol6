package ru.zlygostev.Database;

import ru.zlygostev.dto.ProjectDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// Data Access Object прослойка между БД и системой
public final class ProjectDAO {
    private final DBConnectionManager manager = new DBConnectionManager();

    public ProjectDAO() {}
    public DBConnectionManager getManager() { return manager; }

    // Метод разбирает текущую строчку ResultSet и размещает данные в ProjectDTO
    private ProjectDTO fetch(final ResultSet row) throws SQLException {
        if (row == null) return null;
        final ProjectDTO project = new ProjectDTO();
        project.setLogin(row.getString(FieldConst.LOGIN));
        project.setPassword(row.getString(FieldConst.PASSWORD));
        project.setNik(row.getString(FieldConst.NIK));
        return project;
    }

    // Получить все записи из таблицы
    public List<ProjectDTO> findAll() throws Exception {
        final Statement statement = manager.createStatement();
        final String query = "SELECT * FROM 'app_project'";
        final ResultSet resultSet = statement.executeQuery(query);
        final List<ProjectDTO> result = new ArrayList<>();
        while (resultSet.next()) result.add(fetch(resultSet));
        statement.close();
        return result;
    }

    // Создание таблицы
    public void createTable() throws Exception {
        final Statement statement = manager.createStatement();
        final String query = "CREATE TABLE `megaTEST` (`login` TEXT NOT NULL, `pass` TEXT NULL, `nik` TEXT NULL, PRIMARY KEY (`login`))";
        final ResultSet resultSet = statement.executeQuery(query);
        statement.close();
    }

    public List<String> logins() throws Exception {
        final Statement statement = manager.createStatement();
        final List<String> result = new ArrayList<String>();
        final String query = "SELECT login FROM 'app_project'";
        final ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) result.add(resultSet.getString(FieldConst.LOGIN));
        statement.close();
        return result;
    }

    public void update(final ProjectDTO project) throws Exception {
        if (project == null) return;
        final String query = "UPDATE app_project SET 'login' = ?, 'password' = ?, 'nik' = ?";
        final PreparedStatement statement = manager.createPreparedStatement(query);//?
        statement.setString(1, project.getLogin());
        statement.setString(2, project.getPassword());
        statement.setString(3, project.getNik());
        statement.execute();
    }

    public void removeByLogin(final String login) throws Exception {
        if (login.isEmpty()) return;
        final String query = "DELETE FROM 'app_project' WHERE 'login' = " + login;//?
        final PreparedStatement statment = manager.createPreparedStatement(query);
        statment.setString(1, login);
        statment.execute();
    }

    public void clear() throws Exception {
        final String query = "DELETE FROM 'app_project'";
        final PreparedStatement statement = manager.createPreparedStatement(query);
        statement.execute();
    }
}

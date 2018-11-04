package ru.zlygostev.server;

import ru.zlygostev.Database.ProjectDAO;
import ru.zlygostev.dto.ProjectDTO;
import ru.zlygostev.server.AuthService;

import java.util.ArrayList;
import java.util.List;

public class BaseAuthService implements AuthService {
    private class Entry {
        private String login;
        private String pass;
        private String nick;

        public Entry(String login, String pass, String nick) {
            this.login = login;
            this.pass = pass;
            this.nick = nick;
        }
    }

    private ArrayList<Entry> entries;

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

    public BaseAuthService() {
        ProjectDAO projectDAO = new ProjectDAO();
        entries = new ArrayList<>();
        try {
            projectDAO.getManager().connect();
            final List<ProjectDTO> list = projectDAO.findAll();
            for(int i=0; i<list.size(); i++)
                entries.add(new Entry(list.get(i).getLogin(), list.get(i).getPassword(), list.get(i).getNik()));
            projectDAO.getManager().disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            entries.add(new Entry("login1", "pass1", "nik1"));
            entries.add(new Entry("login2", "pass2", "nik2"));
            entries.add(new Entry("login3", "pass3", "nik3"));
        }

    }

    @Override
    public String getNickByLoginPass(String login, String pass) {
        for (Entry o : entries) {
            if (o.login.equals(login) && o.pass.equals(pass)) return o.nick;
        }
        return null;
    }
}

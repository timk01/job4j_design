package ru.job4j.spammer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ImportDB {

    private Properties config;
    private String dump;
    private Connection connection;

    public ImportDB(String dump) {
        initConnection();
        this.dump = dump;
    }

    public void initConnection() {
        config = new Properties();
        try (InputStream input = ImportDB.class.getClassLoader().getResourceAsStream("importDB.properties")) {
            config.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Class.forName(config.getProperty("jdbc.driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(
                    config.getProperty("jdbc.url"),
                    config.getProperty("jdbc.username"),
                    config.getProperty("jdbc.password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String[] checkSplit(String[] split) {
        if (split.length != 3 || split[0].isEmpty() || split[1].isEmpty()) {
            throw new IllegalArgumentException(
                    "must have ';', both parts can't be empty");
        }
        return split;
    }

    public List<User> load() throws IOException {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(dump))) {
            reader.lines()
                    .filter(str -> !str.isEmpty())
                    .map(str -> checkSplit(str.split(";", 3)))
                    .forEach(split -> users.add(new User(split[0], split[1])));
        }
        return users;
    }

    public void save(List<User> users) throws ClassNotFoundException, SQLException {

        for (User user : users) {
            try (PreparedStatement preparedStatement =
                         connection
                                 .prepareStatement("INSERT INTO users(name, email) values(?, ?)")) {
                preparedStatement.setString(1, user.name);
                preparedStatement.setString(2, user.email);
                preparedStatement.execute();
            }
        }
    }

    private static class User {
        String name;
        String email;

        public User(String name, String email) {
            this.name = name;
            this.email = email;
        }
    }

    public static void main(String[] args) throws Exception {
        ImportDB dataBase = new ImportDB("./data/dump.txt");
        dataBase.save(dataBase.load());
    }
}
package ru.job4j.jdbc;

import ru.job4j.io.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.StringJoiner;

public class ConnectionDemo {
    private static Config config;

    private static Connection getConnection() throws Exception {
        config = new Config("C:\\projects\\job4j_design\\src\\main\\resources\\app.properties");
        config.load();
        Class.forName(config.value("driver"));
        String url = config.value("url");
        String login = config.value("login");
        String password = config.value("password");
        return DriverManager.getConnection(url, login, password);
    }

    public static void main(String[] args) throws Exception {
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                String sql = String.format(
                        "CREATE TABLE IF NOT EXISTS demo_table(%s, %s);",
                        "id SERIAL PRIMARY KEY",
                        "name TEXT"
                );
                statement.execute(sql);
                System.out.println(getTableScheme(connection, "demo_table"));
            }
        }
    }

    /**
     * "-".repeat(30).concat(System.lineSeparator());
     * = это строка из 30 черточек с переносом строки в конце
     *
     * String.format("%-15s|%-15s%n", "NAME", "TYPE");
     * = это строка из  NAME           |TYPE, т.е. они сдвинуты по левому краю "-"
     * и разграничены 15 проблеами, в конце которых | - "|%" нужно для экранки |
     *
     * т.е. когда мы к buffer.add(header); получится:
     * ------------------------------
     * NAME          |TYPE
     * ------------------------------
     *
     * потом мы экзекутим куэри с селектом из таблички, причем
     * Лимит 1 ограничивает количество возвращаемых строк одной.
     * В данном контексте, видимо, используется для получения структуры таблицы (метаданных)
     * без избыточной информации.
     *
     * дальше получаем метаданную и идем по коламнах в ней, в каждой строки есть 2 столбца.
     * их и добавляем также как и в формате раньше
     *
     * ы итоге будет:
     * ------------------------------
     * NAME           |TYPE
     * ------------------------------
     * id             |serial
     * ------------------------------
     * name           |text
     * ------------------------------2
     * @param connection
     * @param tableName
     * @return
     * @throws Exception
     */

    public static String getTableScheme(Connection connection, String tableName) throws Exception {
        var rowSeparator = "-".repeat(50).concat(System.lineSeparator());
        var header = String.format("%-35s|%-35s%n", "NAME", "TYPE");
        var buffer = new StringJoiner(rowSeparator, rowSeparator, rowSeparator);
        buffer.add(header);
        try (var statement = connection.createStatement()) {
            var selection = statement.executeQuery(String.format(
                    "SELECT * FROM %s LIMIT 1", tableName
            ));
            var metaData = selection.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                buffer.add(String.format("%-35s|%-35s%n",
                        metaData.getColumnName(i), metaData.getColumnTypeName(i))
                );
            }
        }
        return buffer.toString();
    }
}
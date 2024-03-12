package ru.job4j.jdbc;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static ru.job4j.jdbc.ConnectionDemo.getTableScheme;

public class TableEditor implements AutoCloseable {

    private Connection connection;

    private Properties properties;

    public TableEditor(Properties properties) throws Exception {
        this.properties = properties;
        initConnection();
    }

    /**
     *
     * В контексте данного кода `TableEditor.class` предоставляет ссылку на объект типа `Class`, представляющий класс `TableEditor`.
     * <p>
     * Метод `getClass()` используется для получения объекта `Class`, который представляет тип (класс) данного объекта.
     * <p>
     * Затем, используя полученный объект `Class`, вызывается метод `getClassLoader()`,
     * который возвращает загрузчик класса для данного класса.
     * <p>
     * Затем используется метод `getResourceAsStream()`,
     * который предоставляется этим загрузчиком класса для доступа к ресурсам (например, файлам) в проекте.
     * <p>
     * Таким образом, `TableEditor.class.getClassLoader()` используется для получения загрузчика класса `TableEditor`,
     * а затем через этот загрузчик класса выполняется доступ к ресурсу "app.properties" в проекте.
     * @return connection
     * @throws Exception
     */

    private Connection getConnection() throws Exception {
        try (InputStream in = TableEditor.class.getClassLoader().getResourceAsStream("app.properties")) {
            properties.load(in);
        }
        Class.forName(properties.getProperty("driver_TableEditor"));
        String url = properties.getProperty("url_TableEditor");
        String login = properties.getProperty("login_TableEditor");
        String password = properties.getProperty("password_TableEditor");
        return DriverManager.getConnection(url, login, password);
    }

    private void initConnection() throws Exception {
        connection = getConnection();
    }

    private void executePreparedStatement(String prepStatement) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(prepStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable(String tableName) {
        String createTable = String.format(
                "create table if not exists %s();",
                tableName);
        executePreparedStatement(createTable);
    }

    public void dropTable(String tableName) {
        String dropTable = String.format(
                "drop table if exists %s;",
                tableName);
        executePreparedStatement(dropTable);
    }

    public void addColumn(String tableName, String columnName, String type) {
        String addColumn = String.format(
                "ALTER TABLE %s "
                        + "ADD COLUMN %s %s;",
                tableName,
                columnName,
                type);
        executePreparedStatement(addColumn);
    }

    public void dropColumn(String tableName, String columnName) {
        String dropColumn = String.format(
                "ALTER TABLE %s "
                        + "DROP COLUMN IF EXISTS %s;",
                tableName,
                columnName);
        executePreparedStatement(dropColumn);
    }

    public void renameColumn(String tableName, String columnName, String newColumnName) {
        String dropColumn = String.format(
                "ALTER TABLE %s "
                        + "RENAME %s TO %s;",
                tableName,
                columnName,
                newColumnName);
        executePreparedStatement(dropColumn);
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) throws Exception {
        Properties config = new Properties();
        TableEditor tableEditor = new TableEditor(config);
        tableEditor.initConnection();

        String tableName = "test_table";
        tableEditor.dropTable(tableName);

        tableEditor.createTable(tableName);
        System.out.println(getTableScheme(tableEditor.connection, tableName));
        System.out.println();

        String columnName1 = "test_varchar_column1";
        String columnName1Type = "varchar(64)";
        String columnName2 = "test_int_column1";
        String columnName2Type = "int";
        tableEditor.addColumn(tableName, columnName1, columnName1Type);
        tableEditor.addColumn(tableName, columnName2, columnName2Type);
        System.out.println(getTableScheme(tableEditor.connection, tableName));
        System.out.println();

        String column1NewName = "test_varchar_column1_new";
        tableEditor.renameColumn(tableName, columnName1, column1NewName);
        System.out.println(getTableScheme(tableEditor.connection, tableName));
        System.out.println();

        tableEditor.dropColumn(tableName, columnName2);
        System.out.println(getTableScheme(tableEditor.connection, tableName));
        System.out.println();

        tableEditor.dropTable(tableName);
    }
}
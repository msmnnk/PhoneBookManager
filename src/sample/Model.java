package sample;

import java.sql.*;

public class Model {

    private Connection connection;

    public Model(String user, String pass, String url) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, user, pass);
    }

    public Connection getConnection() {
        return connection;
    }

    public void update(String editNumber, String surname, int year, String number, String address) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("UPDATE `abonents` SET `surname`=\"" + surname + "\",`year`=\"" + year + "\",`number`=\"" + number + "\",`address`=\"" + address + "\" WHERE `number` = \"" + editNumber + "\" ;");
    }

    public void delete(String number) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM `abonents` WHERE `number` = \"" + number + "\";");
    }

    public void add(String surname, int year, String number, String address) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO `abonents`(`surname`, `year`, `number`, `address`) VALUES (\"" + surname + "\"," + year + ",\"" + number + "\",\"" + address + "\")");
    }

    public ResultSet selectAllDB() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM `abonents`;");
        return res;
    }

    public ResultSet query(String comm) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery(comm);
        return res;
    }
}

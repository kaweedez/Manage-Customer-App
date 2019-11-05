package db;

//import com.mysql.jdbc.Connection;
import java.sql.Connection;
;import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBConnection {
    private static DBConnection dbConnection;
    private Connection connection;

    private DBConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/depTest?createDatabaseIfNotExist=true&allowMultiQueries=true", "root", "root");
            PreparedStatement pstm = connection.prepareStatement("SHOW TABLES");
            ResultSet resultSet = pstm.executeQuery();

            if (!resultSet.next()) {
                String sql = "create table Customer(\n" +
                        "  cusId varchar(10) primary key,\n" +
                        "  cusName varchar(50),\n" +
                        "  cusAddress varchar(50),\n" +
                        "  cusContact varchar(10)\n" +
                        ");";
                pstm = connection.prepareStatement(sql);
                pstm.execute();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static DBConnection getInstance() {
        return dbConnection = ((dbConnection == null) ? new DBConnection() : dbConnection);
    }

    public Connection getConnection() {
        return connection;
    }
}

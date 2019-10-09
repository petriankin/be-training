package c3p0;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = C3poDataSource.getConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

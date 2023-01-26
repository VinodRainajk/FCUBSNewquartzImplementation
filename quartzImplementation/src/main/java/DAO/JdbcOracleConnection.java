package DAO;

import java.sql.*;
import java.util.Properties;

public class JdbcOracleConnection {

    public static void main(String[] args) {

        Connection conn2 = null;

        try {
            Class.forName("oracle.jdbc.OracleDriver");


            // METHOD #2
            String dbURL2 = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
            String username = "dev";
            String password = "dev";
            conn2 = DriverManager.getConnection(dbURL2, username, password);

            if (conn2 != null) {
                System.out.println("Connected with connection #2");
                String sql = "select id,message,queue_name from Custom_Notification";
                Statement statement = conn2.createStatement();
                ResultSet result = statement.executeQuery(sql);

                int count = 0;

                while (result.next()){
                    String id = result.getString("id");
                    String message = result.getString("message");
                    String queue_name = result.getString("queue_name");

                   // String output = "User #%d: %s - %s - %s - %s";
                    System.out.println(id + ' '+ message);
                }

            }

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn2 != null && !conn2.isClosed()) {
                    conn2.close();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}

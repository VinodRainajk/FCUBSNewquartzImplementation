package DAO;

import Model.CustomNotification;
import threading.QuartzExecutorService;
import threading.loadProperties;
import threading.mainExecutor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JdbcOracleConnection {


    public static Connection getconnection() {

        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");


            // METHOD #2
            String dbURL2 = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
            String username = "dev";
            String password = "dev";
            conn = DriverManager.getConnection(dbURL2, username, password);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            return conn;
        }
    }


    public void processMessage() {
        System.out.println("Inside  processMessage");
        Connection conn2 = getconnection();
       // loadProperties loadproperties = new loadProperties();
        //Integer limitRecord = Integer.valueOf(loadProperties.getPropertyValue("limitRecord"));
        Integer limitRecord = 100;
        List<CustomNotification> customNotificationList = new ArrayList<>();
        QuartzExecutorService quartzExecutorService = QuartzExecutorService.getQuartzExecutorService();
        mainExecutor mainexecutor = new mainExecutor(quartzExecutorService);

        try {

            if (conn2 != null) {
                System.out.println("Connected with connection #2");
                String sql = "select id,message,queue_name from Custom_Notification where nvl(status,'U') = 'U'  order by id";
               // Statement statement = conn2.createStatement();
               // ResultSet result = statement.executeQuery(sql);
                PreparedStatement pstmt =  conn2.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                pstmt.setFetchSize(5000);
                ResultSet result = pstmt.executeQuery();
                int count = 0;

                while (result.next()){
                    System.out.println("trip second ");
                    String id = result.getString("id");
                    String message = result.getString("message");
                    String queue_name = result.getString("queue_name");
                    CustomNotification customNotification = new CustomNotification(id,message,queue_name);
                    customNotificationList.add(customNotification);

                    if(customNotificationList.size() == 100){
                        mainexecutor.processMessage(customNotificationList);
                        customNotificationList.clear();
                    }


                }
                // catchup
                if(customNotificationList.size() > 0){
                    mainexecutor.processMessage(customNotificationList);

                }
                System.out.println("Comming out of message push ");

            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                System.out.println("shutting down the executor ");
                quartzExecutorService.getExecutorService().shutdown();
                if (conn2 != null && !conn2.isClosed()) {
                    conn2.close();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}

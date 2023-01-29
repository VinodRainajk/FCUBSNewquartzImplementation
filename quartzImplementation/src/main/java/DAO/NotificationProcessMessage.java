package DAO;

import Model.CustomNotification;
import Model.NotificationResult;
import threading.QuartzExecutorService;
import threading.loadProperties;
import threading.threadDistributor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class NotificationProcessMessage extends Thread{

    @Override
    public void run(){
        System.out.println("NotificationProcessMessage thread is running...");
        processMessage();
    }


    public  Connection getconnection() {

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
        loadProperties loadproperties = new loadProperties();
        Integer limitRecord = Integer.valueOf(loadproperties.getPropertyValue("limitRecord"));
        //Integer limitRecord = 100;
        List<threadDistributor> customNotificationList = new ArrayList<>();
        QuartzExecutorService quartzExecutorService = QuartzExecutorService.getQuartzExecutorService();
        //mainExecutor mainexecutor = new mainExecutor(quartzExecutorService);
        List<Future<NotificationResult>> notificationresultList = null;
        try {

            if (conn2 != null) {
                System.out.println("Connected with connection #2");
                String sql = "select id,message,queue_name from Custom_Notification where nvl(status,'U') = 'U'  order by id";
               // Statement statement = conn2.createStatement();
               // ResultSet result = statement.executeQuery(sql);
                PreparedStatement pstmt =  conn2.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                pstmt.setFetchSize(5000);
                ResultSet result = pstmt.executeQuery();

                if (result.isBeforeFirst() )
                {
                    System.out.println("Update the status");
                    Connection conn = getconnection();
                    String sql2 = "UPDATE Custom_Job_Status SET job_status=? ";
                    PreparedStatement statement = conn.prepareStatement(sql2);

                    statement.setString(1, "W");
                    int rowsUpdated = statement.executeUpdate();

                }

                int count = 0;


                while (result.next()){
                    System.out.println("trip second ");
                    String id = result.getString("id");
                    String message = result.getString("message");
                    String queue_name = result.getString("queue_name");
                    CustomNotification customnotification = new CustomNotification(id,message,queue_name);
                    threadDistributor threaddistributor = new threadDistributor(customnotification);
                    customNotificationList.add(threaddistributor);




                }

                try {
                    notificationresultList = quartzExecutorService.getExecutorService().invokeAll(customNotificationList);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // catchup
               /* if(customNotificationList.size() > 0){
                    mainexecutor.processMessage(customNotificationList);

                }*/
                System.out.println("Comming out of message push ");





            }
            Connection conn3 = getconnection();
            conn3 = getconnection();
            for (int i = 0; i < notificationresultList.size(); i++) {
                Future<NotificationResult> future = notificationresultList.get(i);
                try {
                    NotificationResult result = future.get();
                    System.out.println(" result are " + result.getId() + ": " + result.getStatus());
                    String sql = "UPDATE Custom_Notification SET status=? where id = ?";
                    PreparedStatement statement = conn3.prepareStatement(sql);

                    statement.setString(1, result.getStatus());
                    statement.setString(2, result.getId()) ;
                    int rowsUpdated = statement.executeUpdate();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }


        }
    } catch (SQLException e) {
            throw new RuntimeException(e);
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

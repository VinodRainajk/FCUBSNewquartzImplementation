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
        List<threadDistributor> customNotificationList = new ArrayList<>();
        QuartzExecutorService quartzExecutorService = QuartzExecutorService.getQuartzExecutorService();
        List<Future<NotificationResult>> notificationresultList = null;
        try {
            if (conn2 != null) {
                System.out.println("Getting the Unprocessed Message");
                String sql = "select id,message,queue_name from Custom_Notification where nvl(status,'U') = 'U'  order by id";
                PreparedStatement pstmt =  conn2.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                pstmt.setFetchSize(5000);
                ResultSet result = pstmt.executeQuery();

                if (result.isBeforeFirst() )
                {
                    updateJobStatus("W");
                }

                int count = 0;
                while (result.next())
                {
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

                System.out.println("All message pushed to Processor ");
            }


            Connection conn3 = getconnection();
            conn3 = getconnection();
        try {
            String sql = "UPDATE Custom_Notification SET status=? where id = ?";
            PreparedStatement statement = conn3.prepareStatement(sql);
            for (int i = 0; i < notificationresultList.size(); i++) {
                Future<NotificationResult> future = notificationresultList.get(i);
                    NotificationResult result = future.get();
                    System.out.println(" result are " + result.getId() + ": " + result.getStatus());
                    statement.setString(1, result.getStatus());
                    statement.setString(2, result.getId());
                    int rowsUpdated = statement.executeUpdate();
            }
        }catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("shutting down the executor ");
                quartzExecutorService.getExecutorService().shutdown();
                if (conn3 != null && !conn3.isClosed()) {
                    conn3.close();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
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

        updateJobStatus("C");
    }

   private void updateJobStatus(String status)
   {
       Connection conn = getconnection();
       try {
           System.out.println("Update the status to" +status);

           String sql2 = "UPDATE Custom_Job_Status SET job_status=? ";
           PreparedStatement statement = conn.prepareStatement(sql2);
           statement.setString(1, status);
           int rowsUpdated = statement.executeUpdate();
       }catch (Exception e)
       {
           e.printStackTrace();
        }finally {
           try {
               if (conn != null && !conn.isClosed())
               {
                   conn.close();
               }
           } catch (SQLException ex) {
               ex.printStackTrace();
           }
       }
   }
}

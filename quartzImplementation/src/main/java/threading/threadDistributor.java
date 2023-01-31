package threading;

import DAO.NotificationProcessMessage;
import Model.CustomNotification;
import Model.NotificationResult;

import java.util.concurrent.Callable;

import static java.lang.Thread.currentThread;

public class threadDistributor implements Callable<NotificationResult> {

    private CustomNotification customNotification;

    public threadDistributor(CustomNotification customNotification)
    {
        this.customNotification = customNotification;
    }

    public CustomNotification getcustomNotification() {
        return customNotification;
    }
/*
    @Override
    public void run() {
        System.out.println("The value sent as a param is " + customNotification.getMessage());
        System.out.println("Thread executing value is  " + currentThread().getId());

        //code to push message to Kafka/MQ
        // Message sent successfull
        Connection conn = null;
        try {
            NotificationProcessMessage notificationProcessMessage = new NotificationProcessMessage();
              conn = notificationProcessMessage.getconnection();
            String sql = "UPDATE Custom_Notification SET status=? where id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, "P");
            statement.setString(2, customNotification.getId());
            int rowsUpdated = statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
*/
    @Override
    public NotificationResult call() throws Exception {
        NotificationResult notificationResult = new NotificationResult(customNotification.getId());

      try{
          System.out.println("The value sent as a param is " + customNotification.getMessage());
          System.out.println("Thread executing value is  " + currentThread().getId());
          //code to push message to Kafka/MQ
          // Message sent successfull
          notificationResult.setStatus("P");
        }catch(Exception exp)
        {

            notificationResult.setStatus("E");
        }


        return notificationResult;
    }
}

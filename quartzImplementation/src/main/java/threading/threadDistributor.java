package threading;

import DAO.JdbcOracleConnection;
import Model.CustomNotification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.lang.Thread.currentThread;

public class threadDistributor implements Runnable{

    private CustomNotification customNotification;

    public threadDistributor(CustomNotification customNotification)
    {
        this.customNotification = customNotification;
    }

    public CustomNotification getcustomNotification() {
        return customNotification;
    }

    @Override
    public void run() {
        System.out.println("The value sent as a param is " + customNotification.getMessage());
        System.out.println("Thread executing value is  " + currentThread().getId());

        //code to push message to Kafka/MQ
        // Message sent successfull
        Connection conn = null;
        try {
            conn = JdbcOracleConnection.getconnection();
            String sql = "UPDATE Custom_Notification SET status=? where id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, "P");
            statement.setString(2, customNotification.getId());
            int rowsUpdated = statement.executeUpdate();
            //conn.commit();
            if (rowsUpdated > 0) {
                System.out.println("An existing user was updated successfully!");
            }
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
}

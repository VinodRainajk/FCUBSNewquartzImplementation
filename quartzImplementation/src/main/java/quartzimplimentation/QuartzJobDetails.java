package quartzimplimentation;

import DAO.NotificationProcessMessage;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@DisallowConcurrentExecution
public class QuartzJobDetails implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Inside QuartzJobDetails execute");
        NotificationProcessMessage notificationProcessMessage = new NotificationProcessMessage();
      // if(validateJobStaus(notificationProcessMessage.getconnection()))
          // {
               System.out.println("Main Thread Started");
               notificationProcessMessage.start();
           //s}
        System.out.println("done QuartzJobDetails execute");
    }
/*
    public boolean validateJobStaus(Connection connection)  {
       try
       {
           String sql = "select job_status from Custom_Job_Status ";
           PreparedStatement pstmt =  connection.prepareStatement(sql);
           ResultSet result = pstmt.executeQuery();
           String job_status = "C";
           while (result.next())
           {
               System.out.println("Check the Job status");
                job_status = result.getString("job_status");
           }
           System.out.println(" Job status "+ job_status);
           if(job_status.equals("W"))
           {
               return false;
           }

       }catch (SQLException ex)
           {
           ex.printStackTrace();
           }
        finally
           {
               try {
                   if (connection != null && !connection.isClosed()) {
                       connection.close();
                   }

               } catch (SQLException ex) {
                   ex.printStackTrace();
               }
           }

        return true;
    }

 */
}

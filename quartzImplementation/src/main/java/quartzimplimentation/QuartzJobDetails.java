package quartzimplimentation;

import DAO.NotificationProcessMessage;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@DisallowConcurrentExecution
public class QuartzJobDetails implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Inside QuartzJobDetails execute");
        NotificationProcessMessage jdbcOracleConnection = new NotificationProcessMessage();
        jdbcOracleConnection.processMessage();
        System.out.println("done QuartzJobDetails execute");
    }
}

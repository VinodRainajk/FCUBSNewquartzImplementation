package quartzimplimentation;

import ExecutorService.NotificationThreadGenerator;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@DisallowConcurrentExecution
public class QuartzJobDetails implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Inside QuartzJobDetails execute");
        NotificationThreadGenerator notificationThreadGenerator = new NotificationThreadGenerator();
        System.out.println("Main Thread Started");
        notificationThreadGenerator.start();

        System.out.println("done QuartzJobDetails execute");
    }

}

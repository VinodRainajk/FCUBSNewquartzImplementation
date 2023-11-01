package ExecutorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotificationExecutorService {
    private ExecutorProperties loadProperties = new ExecutorProperties();
    private  String threadCount = loadProperties.getPropertyValue("numberofthreads");
    private  ExecutorService executorService =  Executors.newFixedThreadPool( Integer.valueOf(threadCount));

    public ExecutorService getExecutorService() {
        return executorService;
    }

    private static NotificationExecutorService notificationExecutorService = null;
        private NotificationExecutorService()
        {

        }

     public static NotificationExecutorService getQuartzExecutorService()
     {

         if (notificationExecutorService == null)
             notificationExecutorService = new NotificationExecutorService();


         return notificationExecutorService;
     }

}

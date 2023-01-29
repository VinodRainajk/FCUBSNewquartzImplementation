package threading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QuartzExecutorService {
    private loadProperties loadProperties = new loadProperties();
    private  String propertyvalue = loadProperties.getPropertyValue("numberofthreads");
    private ExecutorService executorService =  Executors.newFixedThreadPool( Integer.valueOf(propertyvalue));

    public ExecutorService getExecutorService() {
        return executorService;
    }

    private static QuartzExecutorService quartzExecutorService = null;
        private QuartzExecutorService()
        {

        }

     public static QuartzExecutorService getQuartzExecutorService()
     {

         if (quartzExecutorService == null)
             quartzExecutorService = new QuartzExecutorService();


         return quartzExecutorService;
     }

}

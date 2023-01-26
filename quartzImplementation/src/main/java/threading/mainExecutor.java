package threading;

import DAO.sampleData;
import Model.CustomNotification;


import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class mainExecutor  {

    private QuartzExecutorService quartzExecutorService;
    public mainExecutor(QuartzExecutorService quartzExecutorService){
        this.quartzExecutorService = quartzExecutorService;
    }

    public  void  processMessage(List<CustomNotification> listofMessage)
    {
        System.out.println("Inside the mainExecutor processMessage");
        //sampleData sampledata = new sampleData();

        for(CustomNotification customNotification : listofMessage)
        {
            threadDistributor threaddistributor =  new threadDistributor(customNotification);
            quartzExecutorService.getExecutorService().execute(threaddistributor);
        }



    }



}

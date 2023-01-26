package threading;

import DAO.sampleData;


import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class mainExecutor  {

    static loadProperties loadProperties = new loadProperties();
    static String propertyvalue = loadProperties.getPropertyValue("numberofthreads");
    static ExecutorService executorService = Executors.newFixedThreadPool( Integer.valueOf(propertyvalue));

    public mainExecutor() throws IOException {
    }

    public static void main(String[] args)
    {

        System.out.println("Inside the mainExecutor main");
        sampleData sampledata = new sampleData();

        for(Integer val : sampledata.loadData())
        {
            threadDistributor threaddistributor =  new threadDistributor(val);
            executorService.execute(threaddistributor);
        }

        executorService.shutdown();

    }



}

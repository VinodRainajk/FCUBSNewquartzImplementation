package threading;

import Model.CustomNotification;

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
        System.out.println("The value sent as a param is "+customNotification.getMessage());
        System.out.println("Thread executing value is  "+currentThread().getId()
        );
    }
}

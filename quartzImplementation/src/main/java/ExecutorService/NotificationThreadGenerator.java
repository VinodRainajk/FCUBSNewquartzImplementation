package ExecutorService;

import NotificationService.NotificationMsgExtractor;

public class NotificationThreadGenerator extends Thread{
    @Override
    public void run(){
        System.out.println("NotificationProcessMessage thread is running...");

        NotificationMsgExtractor notificationMsgExtractor= new NotificationMsgExtractor();
        notificationMsgExtractor.processMessage();
    }
}

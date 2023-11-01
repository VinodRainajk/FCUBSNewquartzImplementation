package NotificationService;

import Entity.NotificationCustomTxnDetail;
import Model.NotificationResult;

import java.util.concurrent.Callable;

import static java.lang.Thread.currentThread;

public class NotificationPublisher implements Callable<NotificationResult> {

    private NotificationCustomTxnDetail notificationCustomTxnDetail;

    public NotificationPublisher(NotificationCustomTxnDetail notificationCustomTxnDetail)
    {
        this.notificationCustomTxnDetail = notificationCustomTxnDetail;
    }

    public NotificationCustomTxnDetail getcustomNotification() {
        return notificationCustomTxnDetail;
    }


    @Override
    public NotificationResult call() throws Exception {
        System.out.println("the Notification result being caaled NotificationResult");
        NotificationResult notificationResult = new NotificationResult(notificationCustomTxnDetail.getMsgRefNo());

        try{
            System.out.println("The value sent as a param is " + notificationCustomTxnDetail.getMessage());
            if (notificationCustomTxnDetail.getNotifName() == "Queue")
            {
                DistributeJMSQueue( notificationCustomTxnDetail.getMessage());
            }else {
                DistributeKafkaTopic(notificationCustomTxnDetail.getMessage());
            }
            notificationResult.setStatus("P");

        }catch(Exception exp)
        {

            notificationResult.setStatus("E");
            notificationResult.setResposeMessage(exp.toString());
        }
        return notificationResult;
    }

        public void DistributeJMSQueue( String message)
        {
            System.out.println("DistributeJMSQueue " + message);

        }


        public void DistributeKafkaTopic(String message)
        {
            System.out.println("DistributeKafkaTopic " + message);
        }

}

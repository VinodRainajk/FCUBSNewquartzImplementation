import Entity.NotificationCustomTxnDetail;
import NotificationService.NotificationMsgExtractor;
import org.junit.Test;

public class ExecutorTest {


    @Test
    public void testIsMessageProcessed()
    {
        NotificationMsgExtractor notificationMsgExtractor = new NotificationMsgExtractor();
        notificationMsgExtractor.processMessage();
    }

}

package NotificationService;

import Entity.NotificationCustomTxnDetail;
import ExecutorService.NotificationExecutorService;
import Hibernate.HibernateConfiguration;
import Model.NotificationResult;
import jakarta.persistence.Query;
import org.hibernate.Session;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class NotificationMsgExtractor {

    private NotificationExecutorService notificationExecutorService = NotificationExecutorService.getQuartzExecutorService();

    private  String findQualifyingMesage()
    {
        UUID uuid=UUID.randomUUID();
        HibernateConfiguration hibernateConfiguration = new HibernateConfiguration();
        Session session =  hibernateConfiguration.getHibernateSession();
        session.beginTransaction();
        System.out.println(" inside extractMessage " );
        List<NotificationCustomTxnDetail> notifCustomtxnlist = session.createQuery("FROM Notification_Txn_Details where globalUID is null").getResultList();
        notifCustomtxnlist.stream()
                        .forEach(s->s.setGlobalUID(uuid.toString()))
                ;
        notifCustomtxnlist.stream().forEach(s-> session.persist(s));
        session.close();
        return uuid.toString();
    }

    private List<NotificationPublisher> extractMessage()
    {
       System.out.println(" inside extractMessage " );
       String uuid = findQualifyingMesage();
        System.out.println(" uuid "+uuid );
        HibernateConfiguration hibernateConfiguration = new HibernateConfiguration();
        Session session =  hibernateConfiguration.getHibernateSession();
        session.beginTransaction();

        Query notificationCustomTxnDetail = session.createQuery("from Notification_Txn_Details where globalUID = :globaluuID ")
                .setParameter("globaluuID",uuid);

       List<NotificationCustomTxnDetail> notificationCustomTxnlist =  notificationCustomTxnDetail.getResultList();

        List<NotificationPublisher> notificationPublisherList = notificationCustomTxnlist
                .stream()
                .map(s-> new NotificationPublisher(s))
                .collect(Collectors.toList());
        session.close();
        return notificationPublisherList;
    }

    private List<Future<NotificationResult>> generateAndDistibute() throws InterruptedException {
        System.out.println(" inside generateAndDistibute " );
        List<NotificationPublisher> notificationPublisherList = extractMessage();
        List<Future<NotificationResult>> notificationresultList  = notificationExecutorService.getExecutorService().invokeAll(notificationPublisherList);
        return notificationresultList;
    }

    public void processMessage()
    {
        System.out.println(" inside processMessage " );
        try {
            List<Future<NotificationResult>>  processedmessage =   generateAndDistibute();

            for (int i = 0; i < processedmessage.size(); i++) {
                Future<NotificationResult> future = processedmessage.get(i);
                NotificationResult result = future.get();
                System.out.println(" result are " + result.getMsgRefNo()+ ": " + result.getStatus());

            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }


    }


}

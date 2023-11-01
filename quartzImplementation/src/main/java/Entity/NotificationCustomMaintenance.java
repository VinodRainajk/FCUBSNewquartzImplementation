package Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "Notification_Maintenance")
public class NotificationCustomMaintenance {


    @Column
    private String queueTopicName;

    @Column
    private String queueTopicType;
    @Column
    private String notifname;

    @Column
    @Id
    private String NotifId;
    @Column
    private int priority;
    @Column
    private int processBucket;

    public String getqueueTopicName() {
        return queueTopicName;
    }

    public String getqueueTopicType() {
        return queueTopicType;
    }

    public String getnotifname() {
        return notifname;
    }

    public int getpriority() {
        return priority;
    }

    public int getProcessBucket() {
        return processBucket;
    }
}

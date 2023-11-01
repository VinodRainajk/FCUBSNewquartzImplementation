package Entity;

import jakarta.persistence.*;

@Entity(name = "Notification_Txn_Master")
public class NotificationCustomTxnMaster {

    @Column
    private String notifName;

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String processSequence;

    public String getProcessSequence() {
        return processSequence;
    }

    public void setProcessSequence(String processSequence) {
        this.processSequence = processSequence;
    }

    public String getNotifName() {
        return notifName;
    }

    public void setNotifName(String notifName) {
        this.notifName = notifName;
    }

    public String getGlobalUID() {
        return globalUID;
    }

    public void setGlobalUID(String globalUID) {
        this.globalUID = globalUID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column
    private String globalUID;

    @Column
    private String status;
}

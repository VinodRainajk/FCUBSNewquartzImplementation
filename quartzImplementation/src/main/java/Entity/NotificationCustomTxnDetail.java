package Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "Notification_Txn_Details")
public class NotificationCustomTxnDetail {

    @Column
    private String notifName;

    @Column
    private String status;
    @Column
    @Id
    private String  MsgRefNo;

    public void setNotifName(String notifName) {
        this.notifName = notifName;
    }

    public void setMsgRefNo(String msgRefNo) {
        MsgRefNo = msgRefNo;
    }

    private String globalUID;
    private String Message;

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    private String responseMessage;


    public void setStatus(String status) {
        this.status = status;
    }

    public void setGlobalUID(String globalUID) {
        this.globalUID = globalUID;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getNotifName() {
        return notifName;
    }

    public String getStatus() {
        return status;
    }

    public String getMsgRefNo() {
        return MsgRefNo;
    }

    public String getGlobalUID() {
        return globalUID;
    }

    public String getMessage() {
        return Message;
    }
}

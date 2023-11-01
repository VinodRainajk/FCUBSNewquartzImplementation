package Model;

public class NotificationResult {
    String MsgRefNo ;
    String status;
    String resposeMessage;

    public NotificationResult(String id) {
        this.MsgRefNo = MsgRefNo;
    }

    public String getMsgRefNo() {
        return MsgRefNo;
    }

    public void setMsgRefNo(String id) {
        this.MsgRefNo = MsgRefNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResposeMessage() {
        return resposeMessage;
    }

    public void setResposeMessage(String resposeMessage) {
        this.resposeMessage = resposeMessage;
    }
}

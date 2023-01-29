package Model;

public class NotificationResult {
    String id ;
    String status;
    String resposeMessage;

    public NotificationResult(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

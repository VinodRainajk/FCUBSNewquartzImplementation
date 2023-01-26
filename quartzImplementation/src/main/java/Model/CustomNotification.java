package Model;

public class CustomNotification {

    String id ;

    public CustomNotification(String id, String message, String queue_name) {
        this.id = id;
        this.message = message;
        this.queue_name = queue_name;
    }

    String message;
    String queue_name ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getQueue_name() {
        return queue_name;
    }

    public void setQueue_name(String queue_name) {
        this.queue_name = queue_name;
    }
}

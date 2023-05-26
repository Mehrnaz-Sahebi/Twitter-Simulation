package User;

import java.io.Serializable;

public class SocketModel implements Serializable {
    public Api eventType;
    public String message;
    public Object data;

    public SocketModel(Api eventType, Object data) {

        this(eventType, null, data);
    }

    public SocketModel(Api eventType, String message, Object data) {
        this.eventType = eventType;
        this.data = data;
        this.message = message;
    }

    @SuppressWarnings("unchecked")
    public <T> T get() {
        return (T) data;
    }
}
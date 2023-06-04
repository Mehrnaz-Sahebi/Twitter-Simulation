

import java.io.Serializable;

public class SocketModel implements Serializable {
    public Api eventType;
    public ResponseOrErrorType message;
    public Object data;

    public SocketModel(Api eventType, Object data) {

        this(eventType, null, data);
    }

    public SocketModel(Api eventType, ResponseOrErrorType message, Object data) {
        this.eventType = eventType;
        this.data = data;
        this.message = message;
    }

    public SocketModel(Api eventType, ResponseOrErrorType message) {
        this.eventType = eventType;
        this.message = message;
    }

    public void setMessage(ResponseOrErrorType message) {
        this.message = message;
    }

    public void setEventType(Api eventType) {
        this.eventType = eventType;
    }

    @SuppressWarnings("unchecked")
    public <T> T get() {
        return (T) data;
    }
}
package webSocket.messages;

public class InfoMessage {
    private String info;

    public InfoMessage(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    /* For logging purposes */
    @Override
    public String toString() {
        return "[InfoMessage] " + info;
    }
}

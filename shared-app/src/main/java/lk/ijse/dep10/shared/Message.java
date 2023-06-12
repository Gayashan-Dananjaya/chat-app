package lk.ijse.dep10.shared;

import java.io.Serializable;

public class Message implements Serializable {
    private Header header;
    private Object content;

    public Message() {
    }

    public Message(Header header, Object content) {
        this.header = header;
        this.content = content;
    }

    public Header getHeader() {
        return header;
    }

    public Object getContent() {
        return content;
    }
}

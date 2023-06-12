package lk.ijse.dep10.app.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class UserSocketDetails {
    private Socket localSocket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public UserSocketDetails(Socket localSocket) {
        this.localSocket = localSocket;
        try {
            this.oos = new ObjectOutputStream(localSocket.getOutputStream());
            this.oos.flush();
        } catch (IOException e) {
            System.out.println("Error 2");
            e.printStackTrace();
        }
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    public ObjectInputStream getOis() throws IOException {
        return (ois == null) ? ois = new ObjectInputStream(this.localSocket.getInputStream()) : ois;
    }
}

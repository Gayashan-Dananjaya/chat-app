package lk.ijse.dep10.client.controller;

import com.sun.javafx.scene.PointLightHelper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.dep10.shared.Header;
import lk.ijse.dep10.shared.Message;
import lk.ijse.dep10.shared.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class ChatSceneController {

    @FXML
    private Button btnSend;

    @FXML
    private ListView<String> lstUsers;

    @FXML
    private TextArea txtChatHistory;

    @FXML
    private TextField txtMessage;

    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private User user;
    private Message message;
    private final ArrayList<Message> messageList = new ArrayList<>();

    public void initData(Socket socket, ObjectInputStream ois, ObjectOutputStream oos, User user) {
        this.socket = socket;
        this.ois = ois;
        this.oos = oos;
        this.user = user;
        System.out.println(this.user.getUsername());
    }

    public void initialize() {
        readServerResponses();

        Stage primaryStage = (Stage) System.getProperties().get("primaryStage");
        primaryStage.setOnCloseRequest(windowEvent -> {
            System.exit(0);
        });

//        Platform.runLater(() -> {
//            btnSend.getScene().getWindow().setOnCloseRequest(windowEvent -> {
//                System.exit(0);
//            });
//        });
    }

    @FXML
    void btnSendOnAction(ActionEvent event) {
        try {
            System.out.println("Sending message");
            Message msg = new Message(Header.MESSAGE, this.txtMessage.getText());
            oos.writeObject(msg);
        } catch (IOException e) {
            System.out.println("Error 6");
            e.printStackTrace();
        }
        System.out.println(message);
    }

    private void readServerResponses() {
        new Thread(() -> {
            while (true) {
                try {
                    message = (Message) ois.readObject();
                    System.out.println("Message received from server : " + message.getHeader());
                    messageList.add(message);
                    Platform.runLater(() -> {
                        Message msg = messageList.get(0);

                        if (msg.getHeader() == Header.MESSAGE) {
                            System.out.println("Message received");
                            txtChatHistory.setText((String) msg.getContent());
                        } else if (msg.getHeader() == Header.USER_LIST) {
                            System.out.println("User List received" + msg.getContent());
                            lstUsers.getItems().clear();
                            System.out.println(((ArrayList<String>) msg.getContent()).size());
                            lstUsers.getItems().addAll((ArrayList<String>) msg.getContent());
                        }

                        messageList.remove(0);
                    });
                } catch (Exception e) {
                    System.out.println("Error 5");
                    e.printStackTrace();
                }
            }
        }).start();
    }

}

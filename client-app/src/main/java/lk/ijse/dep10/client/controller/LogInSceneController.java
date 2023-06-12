package lk.ijse.dep10.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dep10.shared.Header;
import lk.ijse.dep10.shared.Message;
import lk.ijse.dep10.shared.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LogInSceneController {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnLogIn;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;


    public void initData(Socket socket, ObjectOutputStream oos, ObjectInputStream ois) {
        this.socket = socket;
        this.oos = oos;
        this.ois = ois;
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

    @FXML
    void btnLogInOnAction(ActionEvent event) throws IOException, ClassNotFoundException {
        User user = new User(txtUsername.getText(), txtPassword.getText());
        Message message = new Message(Header.LOGIN, user);
        oos.writeObject(message);
        Message returnMsg = (Message) ois.readObject();
        if (returnMsg.getContent() == null) {
            System.out.println("Username or Password error");
        } else {
            User user1 = (User) returnMsg.getContent();
            Stage stage = (Stage) btnLogIn.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/ChatScene.fxml"));
            AnchorPane root = fxmlLoader.load();

            ChatSceneController controller = fxmlLoader.getController();
            controller.initData(socket, ois, oos, user1);

            stage.setScene(new Scene(root));
            stage.show();
            stage.setTitle("Chat App");
        }
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) {

    }

    @FXML
    void txtUsernameOnAction(ActionEvent event) {

    }

}

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

public class SignUpSceneController {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSignUp;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;


    public void initData(Socket socket, ObjectOutputStream oos, ObjectInputStream ois) {
        System.out.println("initData2");
        this.socket = socket;
        this.oos = oos;
        this.ois = ois;
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

    @FXML
    void btnSignUpOnAction(ActionEvent event) throws IOException {
        User user = new User(txtUsername.getText(), txtPassword.getText());
        Message message = new Message(Header.SIGNUP, user);
        oos.writeObject(message);

        Stage stage = (Stage) btnSignUp.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/MainScene.fxml"));
        AnchorPane root = fxmlLoader.load();

        MainSceneController controller = fxmlLoader.getController();
        controller.initData2(socket, oos, ois);

        stage.setScene(new Scene(root));
        stage.show();
        stage.centerOnScreen();
        stage.setTitle("SignUp Window");
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) {

    }

    @FXML
    void txtUsernameOnAction(ActionEvent event) {

    }

}

package lk.ijse.dep10.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainSceneController {

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSignUp;

    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;


    public void initData(Socket socket, ObjectOutputStream oos) {
        System.out.println("initData1");
        System.out.println(socket.getRemoteSocketAddress());
        this.socket = socket;
        this.oos = oos;
        try {
            this.ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Error 3");
            e.printStackTrace();
        }
    }

    public void initData2(Socket socket, ObjectOutputStream oos, ObjectInputStream ois) {
        System.out.println("initData3");
        this.socket = socket;
        this.oos = oos;
        this.ois = ois;
    }

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/LogInScene.fxml"));
        AnchorPane root = fxmlLoader.load();

        LogInSceneController controller = fxmlLoader.getController();
        controller.initData(socket, oos, ois);

        stage.setScene(new Scene(root));
        stage.show();
        stage.centerOnScreen();
        stage.setTitle("LogIn Window");
    }

    @FXML
    void btnSignUpOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnSignUp.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/SignUpScene.fxml"));
        AnchorPane root = fxmlLoader.load();

        SignUpSceneController controller = fxmlLoader.getController();
        controller.initData(socket, oos, ois);

        stage.setScene(new Scene(root));
        stage.show();
        stage.centerOnScreen();
        stage.setTitle("SignUp Window");
    }

}

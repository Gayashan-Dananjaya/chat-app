package lk.ijse.dep10.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dep10.client.controller.MainSceneController;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        System.getProperties().put("primaryStage", primaryStage);

        Socket socket = new Socket("127.0.0.1", 5050);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("Client running");

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/MainScene.fxml"));
        AnchorPane root = fxmlLoader.load();

        MainSceneController controller = fxmlLoader.getController();
        controller.initData(socket, oos);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setTitle("Chat App");
        primaryStage.centerOnScreen();
    }
}

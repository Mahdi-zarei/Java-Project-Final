package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 450, 900));
        primaryStage.setAlwaysOnTop(true);
        Manager manager=new Manager(root.getScene());
        manager.add(FXMLLoader.load(getClass().getResource("Login.fxml")),"Login");
        manager.add(FXMLLoader.load(getClass().getResource("Register.fxml")),"Register");
        manager.add(FXMLLoader.load(getClass().getResource("Menu.fxml")),"Menu");
        manager.add(FXMLLoader.load(getClass().getResource("DeckPick.fxml")),"Deck");
        manager.add(FXMLLoader.load(getClass().getResource("endGame.fxml")),"End");
        manager.add(FXMLLoader.load(getClass().getResource("History.fxml")),"History");
        Character.init();
        Manager.setScene("Login");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

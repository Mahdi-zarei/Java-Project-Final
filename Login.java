package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class Login {
    public static String user;
    @FXML
    private PasswordField Pass;
    @FXML
    private TextField User;
    @FXML
    private Label Checker;
    @FXML
    private void log() {
        user=User.getText();
        String pass=Pass.getText();
        if (Manager.loginData(user,pass)) {
            Manager.getProperty(user);
            if (Manager.isDecked()) Manager.setScene("Menu");
            else Manager.setScene("Deck");
        } else Checker.setText("Username or password is incorrect");
    }
    @FXML
    private void Register() {
        Manager.setScene("Register");
    }

    public static String getUser() {
        return user;
    }
}

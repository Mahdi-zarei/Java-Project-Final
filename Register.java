package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class Register {
    @FXML
    private TextField User;
    @FXML
    private TextField Name;
    @FXML
    private PasswordField Pass,Pass2;
    @FXML
    private Label Checker;
    @FXML
    private Label confPass;
    @FXML
    private Label Logger;
    private boolean isCache=false;

    @FXML
    private boolean checkUser() {
        String user=User.getText();
        if (!Manager.userCheck(user)) {
            Checker.setText("Username Already exists");
            return false;
        }
        else Checker.setText("All fine");
        return true;
    }
    @FXML
    private boolean checkPass() {
        String tmp1=Pass.getText();
        String tmp2=Pass2.getText();
        if (tmp1.equals(tmp2)) {
            confPass.setText("All fine");
            return true;
        }
        else confPass.setText("Not equal");
        return false;
    }
    @FXML
    private void finallize() {
        if (checkUser() && checkPass()) {
            Manager.addUser(User.getText(),Pass.getText(),Name.getText());
            Logger.setText("Registered");
        } else Logger.setText("Cannot register");
    }
    @FXML
    private void Login() {
        Manager.setScene("Login");
    }
}

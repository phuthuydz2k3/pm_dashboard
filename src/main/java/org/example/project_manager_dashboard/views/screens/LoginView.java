package org.example.project_manager_dashboard.views.screens;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.project_manager_dashboard.controllers.LoginController;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginView implements Initializable {

    private TranslateTransition transition;
    private Alert alert;

    @FXML
    private Label dateLabel;

    @FXML
    private Label forgot_backBtn1;

    @FXML
    private Label forgot_backBtn2;

    @FXML
    private PasswordField forgot_confirm;

    @FXML
    private Button forgot_confirmBtn1;

    @FXML
    private Button forgot_confirmBtn2;

    @FXML
    private Label forgot_errorLbl1;

    @FXML
    private Label forgot_errorLbl2;

    @FXML
    private TextField forgot_fullname;

    @FXML
    private PasswordField forgot_password;

    @FXML
    private TextField forgot_phoneNumber;


    @FXML
    private TextField forgot_username;

    @FXML
    private Button login_btn;

    @FXML
    private Label login_errorAlert;

    @FXML
    private Hyperlink login_forgotPassword;

    @FXML
    private AnchorPane login_form;

    @FXML
    private ImageView login_hiddenPassIcon;

    @FXML
    private PasswordField login_password_hidden;

    @FXML
    private TextField login_password_show;

    @FXML
    private ImageView login_showedPassIcon;

    @FXML
    private Label login_switchRegister;

    @FXML
    private TextField login_username;

    @FXML
    private Button register_btn;

    @FXML
    private PasswordField register_confirm;

    @FXML
    private Label register_errorAlert;

    @FXML
    private AnchorPane register_form;

    @FXML
    private AnchorPane forgot_form1;

    @FXML
    private AnchorPane forgot_form2;

    @FXML
    private TextField register_fullname;

    @FXML
    private PasswordField register_password;

    @FXML
    private TextField register_phoneNumber;

    @FXML
    private TextField register_username;

    @FXML
    private ToggleGroup role;

    @FXML
    private Label timeLabel;

    @FXML
    private RadioButton role0;

    @FXML
    private RadioButton role1;
    @FXML
    private RadioButton role2;

    @FXML
    private AnchorPane slider;

    private LoginController loginController = LoginController.getLoginController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_errorAlert.setVisible(false);
        login_form.setVisible(true);
        login_showedPassIcon.setVisible(false);
        login_hiddenPassIcon.setVisible(true);
    }

    @FXML
    void onLoginBtnClicked() {
        slider.requestFocus();
        if (login_username.getText().isBlank() || login_password_hidden.getText().isBlank()) {
            login_errorAlert.setText("Please enter username and password.");
            login_errorAlert.setVisible(true);
            return;
        }

        try {
            int login = loginController.login(login_username.getText(), login_password_hidden.getText());
            if (login == 1) {
                closeStage();

                // Example of loading different FXML based on user type
                String fxmlPath = "/fxml/main.fxml";

                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } else if (login == 0){
                login_errorAlert.setText("Invalid username or password.");
                login_errorAlert.setVisible(true);
            } else if (login == 2) {
                login_errorAlert.setText("You have been banned.");
                login_errorAlert.setVisible(true);
            }
        } catch (Exception e) {
            login_errorAlert.setText("Error: " + e.getMessage());
            login_errorAlert.setVisible(true);
        }
    }

    private void closeStage() {
        Stage stage = (Stage) login_form.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onShowedPasswordIconClicked() {
        login_showedPassIcon.setVisible(false);
        login_hiddenPassIcon.setVisible(true);
        login_password_hidden.setText(login_password_show.getText());
        login_password_hidden.setVisible(true);
        login_password_show.setVisible(false);
    }

    @FXML
    void onHiddenPasswordIconClicked() {
        login_hiddenPassIcon.setVisible(false);
        login_showedPassIcon.setVisible(true);
        login_password_show.setText(login_password_hidden.getText());
        login_password_show.setVisible(true);
        login_password_hidden.setVisible(false);
    }

    @FXML
    void switchRegisterForm() {
//        slider.requestFocus();
//        register_form.setVisible(true);
//        transition = new TranslateTransition();
//        transition.setNode(slider);
//        transition.setToX(400);
//        transition.setDuration(Duration.seconds(.6));
//        transition.setOnFinished(e -> {
//            //hide login form
//            login_username.setText("");
//            login_password_show.setText("");
//            login_password_hidden.setText("");
//            login_errorAlert.setVisible(false);
//            login_form.setVisible(false);
//
//        });
//        transition.play();
    }

    @FXML
    void switchLoginForm() {
//        slider.requestFocus();
//        login_form.setVisible(true);
//        transition = new TranslateTransition();
//        transition.setNode(slider);
//        transition.setToX(0);
//        transition.setDuration(Duration.seconds(.6));
//        transition.setOnFinished(e -> {
//            //reset register form and hide.
//            register_fullname.setText("");
//            register_username.setText("");
//            register_password.setText("");
//            register_confirm.setText("");
//            register_phoneNumber.setText("");
////            role0.setSelected(false);
////            role1.setSelected(false);
//            register_errorAlert.setVisible(false);
//            register_form.setVisible(false);
//        });
//        transition.play();
    }

    @FXML
    void onRegisterBtnClicked() {
        slider.requestFocus();

        if (!checkRegister()) {
            register_errorAlert.setVisible(true);
            return;
        }

        try {
            boolean usernameExisted = loginController.nameExisted(register_username.getText());
            if (usernameExisted) {
                showError("Username already exists.");
            } else {
                registerUser();
                showSuccess();
                switchLoginForm();
            }
        } catch (Exception ex) {
            showError("Error: " + ex.getMessage());
        }
    }

    private void registerUser() {
        String register_vaiTro = "Product Manager";

        System.out.println(register_username.getText() + " " + register_password.getText());

        loginController.register(register_username.getText(), register_password.getText(), register_fullname.getText()
                , register_phoneNumber.getText(), register_vaiTro, false);

        register_errorAlert.setVisible(false);
    }

    private void showError(String message) {
        register_errorAlert.setText(message);
        register_errorAlert.setVisible(true);
    }

    private void showSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText("Successfully registered!");
        alert.showAndWait();
    }


    private String getRoleFromToggleGroup() {
        String register_vaiTro = "";
        if (role.getSelectedToggle() == role0) {
            register_vaiTro = "Customer";
        } else if (role.getSelectedToggle() == role1) {
            register_vaiTro = "Admin";
        } else if (role.getSelectedToggle() == role2) {
            register_vaiTro = "Product manager";
        }
        return register_vaiTro;
    }

    Boolean checkRegister() {
        if (register_fullname.getText().isEmpty()) {
            register_errorAlert.setText("Please enter your full name.");
            return false;
        } else if (register_phoneNumber.getText().isEmpty()) {
            register_errorAlert.setText("Please enter your phone number.");
            return false;
        } else if (register_username.getText().isEmpty()) {
            register_errorAlert.setText("Please enter your username.");
            return false;
        } else if (register_password.getText().isEmpty()) {
            register_errorAlert.setText("Please enter your password.");
            return false;
        } else if (register_confirm.getText().isEmpty()) {
            register_errorAlert.setText("Please confirm your password.");
            return false;
        } else if (!register_confirm.getText().equals(register_password.getText())) {
            register_errorAlert.setText("Password confirmation is incorrect.");
            return false;
        }

        return true;
    }

    // Forgot Password functionality

    @FXML
    void onForgotClicked() {
        slider.requestFocus();

        login_form.setVisible(false);
        login_username.setText("");
        login_password_show.setText("");
        login_password_hidden.setText("");
        login_errorAlert.setVisible(false);

        forgot_form1.setVisible(true);
    }

    @FXML
    void onForgotConfirmBtn1() {
        slider.requestFocus();

        if (checkForgotForm1()) {
            try {
                boolean existed = loginController.checkAccountExisted(forgot_fullname.getText(), forgot_username.getText(), forgot_phoneNumber.getText());
                if (existed) {
                    forgot_form1.setVisible(false);
                    forgot_form2.setVisible(true);
                } else {
                    forgot_errorLbl1.setText("Account does not exist.");
                    forgot_errorLbl1.setVisible(true);
                }
            } catch (Exception ex) {
                forgot_errorLbl1.setText("Error: " + ex.getMessage());
                forgot_errorLbl1.setVisible(true);
            }
        } else {
            forgot_errorLbl1.setVisible(true);
        }
    }

    private boolean checkForgotForm1() {
        if (forgot_fullname.getText().isBlank()) forgot_errorLbl1.setText("Please enter your full name.");
        else if (forgot_username.getText().isBlank()) forgot_errorLbl1.setText("Please enter your username.");
        else if (forgot_phoneNumber.getText().isBlank()) forgot_errorLbl1.setText("Please enter your phone number.");
        else return true;

        return false;
    }

    @FXML
    void onForgotConfirmBtn2() {
        slider.requestFocus();
        if (checkForgotForm2()) {
            try {
                loginController.updatePassword(forgot_fullname.getText(), forgot_username.getText(), forgot_phoneNumber.getText(), forgot_password.getText());

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Password changed successfully!");
                alert.showAndWait();

                //switch to login
                forgot_fullname.setText("");
                forgot_username.setText("");
                forgot_phoneNumber.setText("");
                forgot_password.setText("");
                forgot_confirm.setText("");

                forgot_form1.setVisible(false);
                forgot_form2.setVisible(false);
                login_form.setVisible(true);
            } catch (Exception ex) {
                forgot_errorLbl2.setText("Error: " + ex.getMessage());
                forgot_errorLbl2.setVisible(true);
            }
        } else {
            forgot_errorLbl2.setVisible(true);
        }
    }

    private boolean checkForgotForm2() {
        if (forgot_password.getText().isBlank()) forgot_errorLbl2.setText("Please enter your new password.");
        else if (forgot_confirm.getText().isBlank()) forgot_errorLbl2.setText("Please confirm your password.");
        else if (!forgot_confirm.getText().equals(forgot_password.getText()))
            forgot_errorLbl2.setText("Password confirmation does not match.");
        else return true;

        return false;
    }

    @FXML
    void onForgotBackBtn1Clicked() {
        slider.requestFocus();

        login_form.setVisible(true);
        forgot_form1.setVisible(false);

        login_username.setText("");
        login_password_hidden.setText("");
        login_password_show.setText("");

        forgot_errorLbl1.setVisible(false);

        forgot_fullname.setText("");
        forgot_username.setText("");
        forgot_phoneNumber.setText("");

    }

    @FXML
    void onForgotBackBtn2Clicked() {
        forgot_form1.setVisible(true);
        forgot_form2.setVisible(false);
        forgot_errorLbl1.setVisible(false);
        forgot_errorLbl2.setVisible(false);
        forgot_password.setText("");
        forgot_confirm.setText("");

        slider.requestFocus();
    }
}
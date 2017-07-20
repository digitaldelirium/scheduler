package edu.wgu.scheduler.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/16/2017 at 21:19.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public class LoginController implements Initializable {
    @FXML
    private AnchorPane apLogin;
    @FXML
    private GridPane gpLogin;
    @FXML
    private Label lblWelcome;
    @FXML
    private Label lblUsername;
    @FXML
    private Label lblPassword;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private ButtonBar btnbarLogin;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnRegister;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location
     *         The location used to resolve relative paths for the root object, or
     *         <tt>null</tt> if the location is not known.
     * @param resources
     *         The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

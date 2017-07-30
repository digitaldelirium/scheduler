package edu.wgu.scheduler.controllers;

import edu.wgu.scheduler.MainApp;
import edu.wgu.scheduler.models.User;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import org.apache.log4j.Logger;

import javax.security.auth.login.FailedLoginException;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Function;

import static edu.wgu.scheduler.MainApp.*;


/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/16/2017 at 21:19.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public class LoginController implements Initializable {
    public static String username = null;
    public static String password = null;
    public static int userCount;
    public static boolean loggedIn;
    @FXML
    public static AnchorPane apLogin;
    @FXML
    public static TextField txtUsername;
    @FXML
    public static PasswordField txtPassword;
    public static ButtonType btnLogin;
    public static ButtonType btnRegister;
    public static boolean canAddUser;
    @FXML
    private GridPane gpLogin;
    @FXML
    private Label lblWelcome;
    @FXML
    private Label lblUsername;
    @FXML
    private Label lblPassword;
    private static ResourceBundle bundle;
    private static ObservableList<String> userList;
    private static ObservableMap<String, String> userMap;
    private static MainApp mainApp;
    private static Logger log = Logger.getLogger(LoginController.class.getName());

    public LoginController() throws SQLException, IOException {
        Locale locale = Locale.getDefault();
        try (FileInputStream inputStream = new FileInputStream("config.properties")) {
            config.load(inputStream);

            getUserCount();
            userMap = getUserMap();

            bundle = ResourceBundle.getBundle("scheduler.Scheduler", locale);
        } catch (IOException e) {
            System.out.println("Could not load application config file!");
            e.printStackTrace();
        }

        initialize(MainApp.class.getResource("/fxml/Login.fxml"), bundle);
    }

    private static boolean login(Pair<String, String> credentials) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        ZonedDateTime dateTime = ZonedDateTime.now();
        log.trace(String.format("[%s]\tAttempting login for user %s ...", formatter.format(dateTime), username));
        try {
            Function<Pair<String, String>, Boolean> loginUser = (cred) -> {
                if ((userMap.containsKey(cred.getKey())) && (userMap.get(cred.getKey()).equals(cred.getValue()))) {
                    log.trace(String.format("[%s]\tUser:\t%s Logged in successfully!", formatter.format(ZonedDateTime.now()), username));
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(bundle.getString("LoginSuccessTitle"));
                    alert.setContentText(bundle.getString("LoginSuccessMessage"));
                    alert.show();
                    return true;
                }
                return false;
            };
            if (loginUser.apply(credentials)) {
                return true;
            }
            throw new FailedLoginException();
        } catch (FailedLoginException e) {
            log.trace(String.format("[%s]\tUser:\t%s login failed!", formatter.format(ZonedDateTime.now()), username));
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("LoginFailed"));
            alert.setContentText(bundle.getString("LoginFailedMessage"));
        }
        return false;
    }

    /**
     * @return Whether the anonymous addUser operation was successful
     */
    private static boolean addUser() {
        boolean disableButton = false;
        String username = txtUsername.getText().trim();
        String password;
        if ((username == null) || (username.length() == 0)) {
            throw new IllegalArgumentException(bundle.getString("nullUsername"));
        }
        password = txtPassword.getText();

        // Create extra String variable to not blow up the password check with the trim statement.   People need to know if their pw has a space in it.
        String tmpPw = password;
        if ((tmpPw.trim().length() < 3) || password.charAt(password.length() - 1) == ' ') {
            throw new InvalidPasswordException();
        }
        if (canAddUser == true && (userCount == 0)) {
            disableButton = addUser(null, username, password);
            if (disableButton) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("user created successfully");
                alert.setContentText("user created successfully");
                alert.showAndWait();
            }
        }

        return disableButton;
    }

    /**
     * @param username
     * @param password
     * @param createdBy
     *
     * @return
     */
    public static boolean addUser(String createdBy, String username, String password) {
        user = new User(username, password, createdBy);
        user.setActive(true);
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement insert = connection.prepareStatement("INSERT INTO User (userName, User.password, createdDate, createdBy, active, lastUpdate) VALUES (?, ?, ?, ?, ?, ?)");
            insert.setString(1, username);
            insert.setString(2, password);
            insert.setObject(3, user.getCreatedDate());
            insert.setString(4, createdBy);
            insert.setBoolean(5, user.isActive());
            insert.setObject(6, user.getCreatedDate());

            // An INSERT statement should not return a value, if it does it ran a select query.
            boolean result = insert.execute();
            if (result) {
                System.out.println("This query was an insert and performed a select PreparedStatement...  Fail");
                return false;
            }

            if (insert.getUpdateCount() > 0) {
                userCount++;
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("An error occurred while trying to add a new user!");
            System.out.println(ex.getLocalizedMessage());
        }
        return false;
    }

    public static void handleLoginButtonAction(MouseEvent event) {
        loggedIn = login(new Pair<>(txtUsername.getText().trim(), txtPassword.getText()));
    }

    public static void handleRegisterButtonAction(MouseEvent event) {
        if (userCount == 0) {
            addUser();
        } else {
            try {
                userList.forEach((s) -> {
                    if (s.trim().equals(username.trim())) {
                        throw new IllegalArgumentException("User exists in database");
                    }
                });
            } catch (IllegalArgumentException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(bundle.getString("userExists"));
                alert.setContentText(bundle.getString("userExistsError"));
                alert.show();
                return;
            }
            if (addUser(user.getUsername(), username.trim(), password)) {
                ++userCount;
            }
        }
    }

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
        apLogin = new AnchorPane();
        this.gpLogin = new GridPane();
        this.lblWelcome = new Label();
        this.lblUsername = new Label();
        this.lblPassword = new Label();
        txtUsername = new TextField();
        txtPassword = new PasswordField();
        btnLogin = new ButtonType(resources.getString("Login"), ButtonBar.ButtonData.OK_DONE);
        btnRegister = new ButtonType(resources.getString("Register"));
        this.gpLogin.getChildren().addAll(this.lblWelcome, this.lblUsername, this.lblPassword, txtUsername, txtPassword);
    }

    private ObservableMap<String, String> getUserMap() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM User");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                userMap.put(rs.getString(8), rs.getString(7));
            }
        }

        return userMap;
    }

    private void getUserCount() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM User");
            ResultSet users = statement.executeQuery();
            while (users.next()) {
                userCount = users.getInt(1);
            }

            statement = connection.prepareStatement("SELECT DISTINCT userName FROM User");
            users = statement.executeQuery();
            while (users.next()) {
                userList.add(users.getString(1));
            }
        }
    }

    private static class InvalidPasswordException extends IllegalArgumentException {
        public InvalidPasswordException() {
            super(bundle.getString("invalidPassword"));
        }
    }
}

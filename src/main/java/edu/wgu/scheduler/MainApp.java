package edu.wgu.scheduler;

import edu.wgu.scheduler.controllers.AppViewController;
import edu.wgu.scheduler.controllers.AppointmentViewController;
import edu.wgu.scheduler.controllers.CustomerViewController;
import edu.wgu.scheduler.controllers.LoginController;
import edu.wgu.scheduler.models.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import static edu.wgu.scheduler.controllers.LoginController.*;


public class MainApp extends Application {

    public static BorderPane rootPane;
    public static User user;
    public static BasicDataSource dataSource;
    public static Stage primaryStage;

    public static Properties config = new Properties();
    static Locale locale = Locale.getDefault();


    private ObservableMap<Integer, Customer> customers = FXCollections.emptyObservableMap();
    private ObservableMap<Integer, Address> addresses = FXCollections.emptyObservableMap();
    private ObservableMap<Integer, ICity> cities = FXCollections.emptyObservableMap();
    private ObservableMap<Integer, Appointment> appointments = FXCollections.emptyObservableMap();
    private ObservableMap<Integer, Country> countries = FXCollections.emptyObservableMap();
    private ObservableMap<Integer, IReminder> reminders = FXCollections.emptyObservableMap();
    private ResourceBundle bundle;
    private AppointmentViewController appointmentView;
    private CustomerViewController customerView;

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        primaryStage = stage;
        primaryStage.setTitle("Welcome to C195 scheduler!");
        bundle = ResourceBundle.getBundle("Scheduler", locale);

        File configFile = new File("C:\\Users\\maste\\Documents\\NetBeansProjects\\scheduler\\src\\main\\resources\\config.properties");
/*        if(configFile.exists()){
            System.out.println("File Exists");
        }
        else {
            System.out.println("File Not Found");
        }
*/

        try (FileInputStream inputStream = new FileInputStream(configFile)) {
            config.load(inputStream);
            System.out.println("Configuration loaded");
        } catch (IOException ex) {
            System.out.println("Throwing Exception from Main app config load");
            throw ex;
        }

        getDataSourceConnection();
        LoginController loginController = new LoginController();
        loggedIn = showLoginDialog(loginController);

        initLayout();


    }

    private boolean showLoginDialog(LoginController loginController) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle(bundle.getString("Login"));
        dialog.getDialogPane().getButtonTypes().addAll(btnLogin, btnRegister);

        Node loginButton = dialog.getDialogPane().lookupButton(btnLogin);
        loginButton.setDisable(true);

        Node registerButton = dialog.getDialogPane().lookupButton(btnRegister);


        txtUsername.textProperty().addListener(((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            username = txtUsername.getText();
        }));

        txtPassword.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            password = txtPassword.getText();
            if ((txtPassword.textProperty().getValue().trim() != null) && (txtUsername.textProperty().getValue().trim() != null) && (userCount > 0)) {
                loginButton.setDisable(false);
            } else {
                loginButton.setDisable(true);
            }
        });

        loginButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> handleLoginButtonAction(event));

        registerButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleRegisterButtonAction(event));

        dialog.getDialogPane().setContent(apLogin);
        Platform.runLater(() -> txtUsername.requestFocus());

        dialog.setResultConverter(button -> {
            if (button == btnLogin) {
                return new Pair<>(username, password);
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent((Pair<String, String> usernamePassword) -> {
            loggedIn = true;
        });

        return loggedIn;
    }

    private void getDataSourceConnection() {
        try {
            byte[] decodedPassword = Base64.getDecoder().decode(config.getProperty("dbPassword"));
            String password = new String(decodedPassword, "utf-8");

            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassLoader(getClass().getClassLoader());
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUrl(config.getProperty("dsn"));
            dataSource.setUsername(config.getProperty("dbUser"));
            dataSource.setPassword(password);
            dataSource.setPoolPreparedStatements(true);
            dataSource.setMaxOpenPreparedStatements(10);

            MainApp.dataSource = dataSource;
        }
        catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(String.format("Failed to get database connection!\n\n%s", ex.getLocalizedMessage()));
            alert.setTitle("Failure Getting Database Connection");
            alert.show();
        }
    }

    private void initLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/AppView.fxml"));
            AppViewController controller = loader.getController();
            rootPane = loader.load();

            Scene scene = new Scene(rootPane);
            scene.getStylesheets().add("/styles/Styles.css");

//            controller.setMainApp(this);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e) {
        e.printStackTrace();
    }
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}

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
import javafx.collections.ObservableList;
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
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import static edu.wgu.scheduler.controllers.LoginController.*;


public class MainApp extends Application {

    public static BorderPane rootPane;
    public static User user;
    public static BasicDataSource dataSource;
    public static Stage primaryStage;

    public static Properties config = new Properties();
    public static Locale locale = Locale.getDefault();
    public static ResourceBundle bundle;

    private static ObservableMap<Integer, ICustomer> customers = FXCollections.observableHashMap();
    private static ObservableMap<Integer, IAddress> addresses = FXCollections.observableHashMap();
    private static ObservableMap<Integer, ICity> cities = FXCollections.observableHashMap();
    private static ObservableMap<Integer, IAppointment> appointments = FXCollections.observableHashMap();
    private static ObservableMap<Integer, ICountry> countries = FXCollections.observableHashMap();
    private static ObservableMap<Integer, IReminder> reminders = FXCollections.observableHashMap();
    private static ObservableList<ICustomerView> customerList;

    private AppViewController appView;

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        primaryStage = stage;
        primaryStage.setTitle("Welcome to C195 scheduler!");

        if(locale.getCountry().equals("US")){
            if(locale.getLanguage().equals("en")) {
                locale = new Locale("en");
            }
        }

        bundle = ResourceBundle.getBundle("Scheduler", locale);

        File configFile = null;
        try {
            configFile = new File(getClass().getClassLoader().getResource("resources/config.properties").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        try (FileInputStream inputStream = new FileInputStream(configFile)) {
            config.load(inputStream);
            System.out.println("Configuration loaded");
        } catch (IOException ex) {
            System.out.println("Throwing Exception from Main app config load");
            throw ex;
        }

        getDataSourceConnection();
        LoginController loginController = new LoginController();

        int x = 0;
        while (x < 3) {
            if (loggedIn) {
                initLayout();
                break;
            } else {
                showLoginDialog(loginController);
            }
            x++;
        }

        if(x == 3){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("LoginExceededTitle"));
            alert.setContentText(bundle.getString("MaxLoginFailures"));
            alert.showAndWait();
            System.exit(1);
        }

    }

    private boolean showLoginDialog(LoginController loginController) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle(bundle.getString("Login"));
        dialog.setHeaderText(bundle.getString("Welcome"));

        dialog.getDialogPane().getButtonTypes().addAll(btnLogin, btnRegister);

        Node loginButton = dialog.getDialogPane().lookupButton(btnLogin);
        loginButton.setDisable(true);

        Node registerButton = dialog.getDialogPane().lookupButton(btnRegister);

        txtUsername.textProperty().addListener(((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            username = txtUsername.getText();
            if ((txtPassword.textProperty().getValue().trim() != null) && (!username.trim().isEmpty()) && (userCount > 0)) {
                loginButton.setDisable(false);
            } else {
                loginButton.setDisable(true);
            }
        }));

        txtPassword.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            password = txtPassword.getText();
            if ((txtPassword.textProperty().getValue().trim() != null) && (txtUsername.textProperty().getValue().trim() != null) && (userCount > 0)) {
                loginButton.setDisable(false);
            } else {
                loginButton.setDisable(true);
            }
        });

        loginButton.addEventHandler(MouseEvent.MOUSE_CLICKED, LoginController::handleLoginButtonAction);

        registerButton.addEventHandler(MouseEvent.MOUSE_CLICKED, LoginController::handleRegisterButtonAction);

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
                login(usernamePassword);
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
        rootPane = new BorderPane();

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/AppView.fxml"));
            appView = loader.getController();
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

    public static ObservableMap<Integer, ICustomer> getCustomers() {
        return customers;
    }

    public static void setCustomers(HashMap<Integer, ICustomer> customers) {
        MainApp.customers.putAll(customers);
    }

    public static ObservableMap<Integer, IAddress> getAddresses() {
        return addresses;
    }

    public static void setAddresses(HashMap<Integer, IAddress> addresses) {
        MainApp.addresses.putAll(addresses);
    }

    public static ObservableMap<Integer, ICity> getCities() {
        return cities;
    }

    public static void setCities(ObservableMap<Integer, ICity> cities) {
        MainApp.cities = cities;
    }

    public static ObservableMap<Integer, IAppointment> getAppointments() {
        return appointments;
    }

    public static void setAppointments(ObservableMap<Integer, IAppointment> appointments) {
        MainApp.appointments = appointments;
    }

    public static ObservableMap<Integer, ICountry> getCountries() {
        return countries;
    }

    public static void setCountries(ObservableMap<Integer, ICountry> countries) {
        MainApp.countries = countries;
    }

    public static ObservableMap<Integer, IReminder> getReminders() {
        return reminders;
    }

    public static void setReminders(ObservableMap<Integer, IReminder> reminders) {
        MainApp.reminders = reminders;
    }

    public static ObservableList<ICustomerView> getCustomerList() {
        return customerList;
    }

    public static void setCustomerList(ObservableList<ICustomerView> customerList) {
        MainApp.customerList = customerList;
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

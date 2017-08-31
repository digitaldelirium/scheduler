package edu.wgu.scheduler;

import edu.wgu.scheduler.controllers.*;
import edu.wgu.scheduler.models.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.Node;
import javafx.scene.Parent;
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
import java.io.IOException;
import java.net.URISyntaxException;
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
    private static Scene scene;

    private static AppViewController appViewController;
    private static AppointmentViewController appointmentViewController;
    private static CustomerViewController customerViewController;
    private static DataViewController dataViewController;
    private Parent appView;
    private static Parent appointmentView;
    private static Parent customerView;
    private static Parent dataView;


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
        // TODO:  uncomment this to enable login
        user = new User("test1");
        initLayout();
/*        loggedIn = showLoginDialog(loginController);

        int x = 1;
        while (x < 3) {
            if (loggedIn) {
                initLayout();
                break;
            } else {
                loggedIn = showLoginDialog(loginController);
            }
            x++;
        }

        if (x == 3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("LoginExceededTitle"));
            alert.setContentText(bundle.getString("MaxLoginFailures"));
            alert.showAndWait();
            System.exit(1);
        }*/
    }

    private boolean showLoginDialog(LoginController loginController) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle(bundle.getString("Login"));
        dialog.setHeaderText(bundle.getString("Welcome"));

        dialog.getDialogPane().getButtonTypes().add(btnLogin);
        dialog.getDialogPane().setPrefWidth(350.0);

        Node loginButton = dialog.getDialogPane().lookupButton(btnLogin);
        loginButton.setDisable(true);


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
                loggedIn = login(usernamePassword);
        });

        if(loggedIn){
            user = new User(username);
        }

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
        
        appointmentViewController = AppointmentViewController.getInstance();
        appointmentView = appointmentViewController.apAppointmentView;
        appointmentViewController.setMainApp(this);


        customerViewController = CustomerViewController.getInstance();
        customerView = customerViewController.apCustomerView;
        customerViewController.setMainApp(this);
        
        dataViewController = new DataViewController();
        dataView = dataViewController.tabPane;
        dataViewController.setMainApp(this);
        
        appViewController = AppViewController.getInstance();
        appView = appViewController.getBorderPane();
        appViewController.setMainApp(this);

        setDataViewController(appointmentViewController.getDataViewController());

        rootPane = (BorderPane) appView;

        scene = new Scene(rootPane);
        scene.getStylesheets().add("/styles/Styles.css");
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static DataViewController getDataViewController() {
        return dataViewController;
    }

    public static void setDataViewController(DataViewController dataViewController) {
        MainApp.dataViewController = dataViewController;
    }

    public Parent getAppView() {
        return appView;
    }

    public void setAppView(Parent appView) {
        this.appView = appView;
    }

    public static Parent getAppointmentView() {
        return appointmentView;
    }

    public static void setAppointmentView(Parent appointmentView) {
                MainApp.appointmentView = appointmentView;
    }

    public static Parent getCustomerView() {
        return customerView;
    }

    public static void setCustomerView(Parent customerView) {
        MainApp.customerView = customerView;
    }

    public static Parent getDataView() {
        return dataView;
    }

    public static void setDataView(Parent dataView) {
        MainApp.dataView = dataView;
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

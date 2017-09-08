package edu.wgu.scheduler;

import edu.wgu.scheduler.controllers.*;
import edu.wgu.scheduler.models.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

import static edu.wgu.scheduler.controllers.LoginController.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainApp extends Application {

    public static BorderPane rootPane;
    public static User user;
    public static BasicDataSource dataSource;
    public static Stage primaryStage;
    public static Properties config = new Properties();
    public static Locale locale = Locale.getDefault();
    public static ResourceBundle bundle;

    private static ObservableList<Customer> customers;
    private static ObservableList<Address> addresses;
    private static ObservableList<City> cities;
    private static ObservableList<Appointment> appointments;
    private static ObservableList<Country> countries;
    private static ObservableList<Reminder> reminders;
    private static ObservableList<AppointmentView> appointmentViews;
    private static ObservableList<CustomerView> customerViews;
    private static Scene scene;

    private static AppViewController appViewController;
    private static AppointmentViewController appointmentViewController;
    private static CustomerViewController customerViewController;
    private DataViewController dataViewController;
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
        loggedIn = showLoginDialog(loginController);

        int x = 0;
        while (x <= 2) {
            if (loggedIn) {
                initLayout();
                break;
            } else if (x == 2 ){
                x++;
                break;
            }
            else {
                loggedIn = showLoginDialog(loginController);
                if(loggedIn)
                    break;
            }
            x++;
        }

        if (x == 3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("LoginExceededTitle"));
            alert.setContentText(bundle.getString("MaxLoginFailures"));
            alert.showAndWait();
            log.trace("[SECURITY VIOLATION!!!]\tMaximum login attempts exceeded!\tExiting!");
            System.exit(1);
        }
    }

    private boolean showLoginDialog(LoginController loginController) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle(bundle.getString("Login"));
        dialog.setHeaderText(bundle.getString("Welcome"));

        dialog.getDialogPane().getButtonTypes().add(btnLogin);
        dialog.getDialogPane().setPrefWidth(350.0);

        Node loginButton = dialog.getDialogPane().lookupButton(btnLogin);

        txtUsername.textProperty().addListener(((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            username = txtUsername.getText();
        }));

        txtPassword.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            password = txtPassword.getText();
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
//        rootPane = new BorderPane();

        appointmentViewController = AppointmentViewController.getInstance();
        appointmentView = appointmentViewController.apAppointmentView;
        appointmentViewController.setMainApp(this);

        customerViewController = CustomerViewController.getInstance();
        customerView = customerViewController.apCustomerView;
        customerViewController.setMainApp(this);

        appViewController = AppViewController.getInstance();
        appView = appViewController.getBorderPane();
        appViewController.setMainApp(this);

        appViewController.setDataViewController(appointmentViewController.getDataViewController());

        dataViewController = appointmentViewController.getDataViewController();
        dataView = dataViewController.tabPane;
        dataViewController.setMainApp(this);

/*        System.out.println(this.dataViewController);
        System.out.println(this.dataView);
        System.out.println(appointmentViewController.toString());
        System.out.println(customerViewController.toString());
        System.out.println(appViewController.toString());*/

        rootPane = (BorderPane) appView;
        rootPane.setBottom(dataViewController.tabPane);

        scene = new Scene(rootPane);
        scene.getStylesheets().add("resources/styles/Styles.css");
        
        primaryStage.setScene(scene);
        primaryStage.show();

        reminders.filtered(reminder -> {
            if (reminder.getReminderDate().withZoneSameInstant(ZoneId.systemDefault()).isEqual(ZonedDateTime.now().plusMinutes(15))){
                int minutesTilAppointment = ZonedDateTime.now(ZoneId.systemDefault()).compareTo(reminder.getReminderDate());
                int appointmentId = reminder.getAppointmentId();
                final Appointment[] app = new Appointment[1];

                appointments.filtered(appointment -> {
                    if (appointment.getAppointmentId() == appointmentId){
                        app[0] = appointment;
                        return true;
                    }
                    return false;
                });

                final Customer[] cust = new Customer[1];
                customers.filtered(customer -> {
                   if (customer.getCustomerId() == app[0].getCustomerId()){
                       cust[0] = customer;
                       return true;
                   }
                   return false;
                });
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("You have an appointment!");
                alert.setHeaderText(String.format("You have an appointment in %d minutes", minutesTilAppointment));
                alert.setContentText(String.format("Meeting with %s at %s Titled: %s", cust[0].getCustomerName(), app[0].getLocation(), app[0].getTitle() ));
                alert.show();
                return true;
            }
            return false;
        });
    }


    public DataViewController getDataViewController() {
        return dataViewController;
    }

    public void setDataViewController(DataViewController dataViewController) {
        this.dataViewController = dataViewController;
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
        rootPane.setBottom(MainApp.dataView);
    }

    public static ObservableList<Customer> getCustomers() {
        return customers;
    }

    public static void setCustomers(List<Customer> customers) {
        MainApp.customers = FXCollections.observableList(new LinkedList<>(), cu -> new Observable[]{
                cu.customerNameProperty(),
                cu.addressIdProperty(),
                cu.lastUpdateByProperty(),
                cu.lastUpdateProperty(),
                cu.activeProperty()
        });

        MainApp.customers.setAll(customers);
    }

    public static ObservableList<Address> getAddresses() {
        return addresses;
    }

    public static void setAddresses(List<Address> addresses) {
        MainApp.addresses = FXCollections.observableList(new LinkedList<>(), address -> new Observable[]{
                address.addressProperty(),
                address.address2Property(),
                address.phoneProperty(),
                address.postalCodeProperty(),
                address.lastUpdateByProperty(),
                address.lastUpdateProperty(),
                address.cityIdProperty()
        });

        MainApp.addresses.setAll(addresses);
    }

    public static ObservableList<City> getCities() {
        return cities;
    }

    public static void setCities(List<City> cities) {
        MainApp.cities = FXCollections.observableList(new LinkedList<>(), city -> new Observable[]{
                city.cityProperty(),
                city.countryIdProperty(),
                city.lastUpdateByProperty(),
                city.lastUpdateProperty()
            });
        MainApp.cities.setAll(cities);
    }

    public static ObservableList<Appointment> getAppointments() {
        return appointments;
    }

    public static void setAppointments(List<Appointment> appointments) {
        if(MainApp.appointments == null) {
            MainApp.appointments = FXCollections.observableList(new LinkedList<>(), appointment -> new Observable[]{
                    appointment.customerIdProperty(),
                    appointment.contactProperty(),
                    appointment.descriptionProperty(),
                    appointment.titleProperty(),
                    appointment.startProperty(),
                    appointment.endProperty(),
                    appointment.lastUpdatedByProperty(),
                    appointment.lastUpdateProperty()
            });
        }
        MainApp.appointments.setAll(appointments);
    }

    public static ObservableList<Country> getCountries() {
        return countries;
    }

    public static void setCountries(List<Country> countries) {
        MainApp.countries = FXCollections.observableList(new LinkedList<>(), country -> new Observable[]{
                country.countryProperty(),
                country.lastUpdateByProperty(),
                country.lastUpdateProperty()
        });

        MainApp.countries.setAll(countries);
    }

    public static ObservableList<AppointmentView> getAppointmentViews() {
        return appointmentViews;
    }

    public static void setAppointmentViews(List<AppointmentView> appointmentViews) {
        if(MainApp.appointmentViews == null) {
            MainApp.appointmentViews = FXCollections.observableList(new LinkedList<>(), av -> new Observable[]{
                    av.titleProperty(),
                    av.descriptionProperty(),
                    av.locationProperty(),
                    av.contactProperty(),
                    av.urlProperty(),
                    av.customerNameProperty(),
                    av.startProperty(),
                    av.endProperty(),
                    av.lastUpdatedProperty()
            });
        }
        MainApp.appointmentViews.setAll(appointmentViews);
    }

    public static ObservableList<CustomerView> getCustomerViews() {
        return customerViews;
    }

    public static void setCustomerViews(List<CustomerView> customerViews) {
        if(MainApp.customerViews == null) {
            MainApp.customerViews = FXCollections.observableList(new LinkedList<>(), customerView -> new Observable[]{
                    customerView.activeProperty(),
                    customerView.lastUpdateByProperty(),
                    customerView.lastUpdateProperty(),
                    customerView.phoneProperty(),
                    customerView.postalCodeProperty(),
                    customerView.cityProperty(),
                    customerView.countryProperty(),
                    customerView.address2Property(),
                    customerView.addressProperty(),
                    customerView.customerNameProperty()
            });
        }
        MainApp.customerViews.setAll(customerViews);
    }

    public static ObservableList<Reminder> getReminders() {
        return reminders;
    }

    public static void setReminders(List<Reminder> reminders) {
        if(MainApp.reminders == null) {
            MainApp.reminders = FXCollections.observableList(new LinkedList<>(), re -> new Observable[]{
                    re.remindercolProperty(),
                    re.reminderDateProperty(),
                    re.snoozeIncrementProperty(),
                    re.snoozeIncrementTypeIdProperty()
            });
        }
        MainApp.reminders.setAll(reminders);
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

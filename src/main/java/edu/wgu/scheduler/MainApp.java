package edu.wgu.scheduler;

import edu.wgu.scheduler.controllers.AppViewController;
import edu.wgu.scheduler.controllers.AppointmentViewController;
import edu.wgu.scheduler.controllers.CustomerViewController;
import edu.wgu.scheduler.models.*;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;


public class MainApp extends Application {

    public static BorderPane rootPane;

    static Properties config = new Properties();
    static Locale locale = Locale.getDefault();
    public static User user;
    public static BasicDataSource dataSource;

    private Stage primaryStage;
    private ObservableMap<Integer, Customer> customers = FXCollections.emptyObservableMap();
    private ObservableMap<Integer, Address> addresses = FXCollections.emptyObservableMap();
    private ObservableMap<Integer, City> cities = FXCollections.emptyObservableMap();
    private ObservableMap<Integer, Appointment> appointments = FXCollections.emptyObservableMap();
    private ObservableMap<Integer, Country> countries = FXCollections.emptyObservableMap();
    private ObservableMap<Integer, Reminder> reminders = FXCollections.emptyObservableMap();
    private ResourceBundle bundle;
    private AppointmentViewController appointmentView;
    private CustomerViewController customerView;

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        this.primaryStage.setTitle("Welcome to C195 scheduler!");
        bundle = ResourceBundle.getBundle("Scheduler", locale);

        try (FileInputStream inputStream = new FileInputStream("config.properties")) {
            config.load(inputStream);
            System.out.println("Configuration loaded");
        } catch (IOException ex) {
            System.out.println("Throwing Exception from Main app config load");
            throw ex;
        }

        getDataSourceConnection();

        initLayout();


    }

    private void getDataSourceConnection() {
        try {
            byte[] decodedPassword = Base64.getDecoder().decode(config.getProperty("dbPassword"));
            String password = new String(decodedPassword, "utf-8");

            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassLoader(getClass().getClassLoader());
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUrl(config.getProperty("dsn"));
            dataSource.setUsername(config.getProperty("username"));
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
            this.primaryStage.show();
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

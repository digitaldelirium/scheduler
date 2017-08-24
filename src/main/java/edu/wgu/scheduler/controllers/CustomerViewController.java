package edu.wgu.scheduler.controllers;

import com.sun.corba.se.impl.orb.ParserTable;
import com.sun.org.apache.xml.internal.security.Init;
import edu.wgu.scheduler.MainApp;
import edu.wgu.scheduler.models.Customer;
import edu.wgu.scheduler.models.CustomerViewProperty;
import edu.wgu.scheduler.models.ICustomerView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

import static edu.wgu.scheduler.controllers.AppViewController.*;
import static edu.wgu.scheduler.MainApp.*;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/16/2017 at 21:21.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public class CustomerViewController implements Initializable {
    @FXML
    protected AnchorPane apCustomerView;
    @FXML
    private VBox vbCustomerEditor;
    @FXML
    private ToggleButton tbCustomerEditMode;
    @FXML
    private GridPane gpCustomerEditor;
    @FXML
    private Label lblAddress;
    @FXML
    private Label lblCustomerName;
    @FXML
    private Label lblAddress2;
    @FXML
    private Label lblCity;
    @FXML
    private Label lblState;
    @FXML
    private Label lblPostalCode;
    @FXML
    private Label lblCountry;
    @FXML
    private Label lblCustomerSince;
    @FXML
    private Label lblCustomerCreatedDate;
    @FXML
    private Label lblPhone;
    @FXML
    private Label lblPrefix;
    @FXML
    private ButtonBar btnbarCustomerEditor;
    @FXML
    private Button btnCustomerOk;
    @FXML
    private Button btnCustomerCancel;
    @FXML
    private TextField txtCustomerName;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtAddress2;
    @FXML
    private TextField txtCity;
    @FXML
    private TextField txtState;
    @FXML
    private TextField txtPostalCode;
    @FXML
    private TextField txtCountry;
    @FXML
    private TextField txtPhone;
    @FXML
    private HBox hbPhone;
    @FXML
    private CheckBox cbActive;
    private MainApp mainApp;
    private TableView<ICustomerView> tvCustomerView = new TableView<>();
    private TableColumn<ICustomerView, String> tcCustomerName = new TableColumn<>();
    private TableColumn<ICustomerView, String> tcAddress = new TableColumn<>();
    private TableColumn<ICustomerView, String> tcAddress2 = new TableColumn<>();
    private TableColumn<ICustomerView, String> tcCity = new TableColumn<>();
    private TableColumn<ICustomerView, String> tcPostalCode = new TableColumn<>();
    private TableColumn<ICustomerView, String> tcCountry = new TableColumn<>();
    private TableColumn<ICustomerView, String> tcPhone = new TableColumn<>();
    private TableColumn<ICustomerView, Byte> tcActive = new TableColumn<>();
    private ObservableMap<LocalDateTime, ICustomerView> omCustomerView;
    private Map<Integer, ICustomer> customerMap = new HashMap<>();

    public CustomerViewController() {
        omCustomerView = getOmCustomerView();
        customers = getCustomers();
        initialize("/fxml/CustomerView.fxml", null);
    }

    private Map<Integer, Customer> getCustomers() {
        try(Connection connection = dataSource.getConnection())
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
        this.apCustomerView = new AnchorPane();
        this.gpCustomerEditor = new GridPane();
        this.tbCustomerEditMode = new ToggleButton();
        this.tbCustomerEditMode.setSelected(false);
        this.lblCustomerName = new Label();
        this.lblAddress = new Label();
        this.lblAddress2 = new Label();
        this.lblCity = new Label();
        this.lblState = new Label();
        this.lblPostalCode = new Label();
        this.lblCountry = new Label();
        this.lblPhone = new Label();
        this.lblCustomerSince = new Label();
        this.lblCustomerCreatedDate = new Label();
        this.cbActive = new CheckBox();
        this.cbActive.setSelected(false);
        this.lblPrefix = new Label();
        this.txtCustomerName = new TextField();
        this.txtAddress = new TextField();
        this.txtAddress2 = new TextField();
        this.txtCity = new TextField();
        this.txtState = new TextField();
        this.txtPostalCode = new TextField();
        this.txtCountry = new TextField();
        this.txtPhone = new TextField();
        this.hbPhone = new HBox(5.0, lblPrefix, txtPhone);
        this.btnbarCustomerEditor = new ButtonBar();
        this.btnCustomerCancel = new Button();
        this.btnCustomerCancel.setCancelButton(true);
        this.btnCustomerOk = new Button();
        this.btnCustomerOk.setDefaultButton(true);
        lblListView.setText("Customer List View");
        lblTableView.setText("Customer Table View");
        lvListView = new ListView<ICustomerView>();

        tvCustomerView.getColumns().addAll(
                tcCustomerName,
                tcAddress,
                tcAddress2,
                tcCity,
                tcPostalCode,
                tcCountry,
                tcPhone,
                tcActive
        );
        tvTableView = this.tvCustomerView;

    }

    private ObservableMap<LocalDateTime, ICustomerView> getOmCustomerView() throws SQLException {
        Map<Integer, ICustomerView> customerView = new HashMap<>();
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * from Customers");
            ResultSet resultSet = statement.execute();
            if(rs.next()){
                rs.get
            }
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}

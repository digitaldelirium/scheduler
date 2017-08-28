package edu.wgu.scheduler.controllers;

import edu.wgu.scheduler.MainApp;
import edu.wgu.scheduler.models.*;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

import static edu.wgu.scheduler.MainApp.*;
import static edu.wgu.scheduler.models.CustomerViewProperty.*;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/16/2017 at 21:21.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public class CustomerViewController implements Initializable {
    @FXML
    public
    AnchorPane apCustomerView;
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
    @FXML
    private HBox hbCustomerEditor;
    @FXML
    private Button btnNewCustomer;
    private MainApp mainApp;
    private TableView<ICustomerView> tvCustomerView;
    private TableColumn<ICustomerView, String> tcCustomerName = new TableColumn<>("Customer Name");
    private TableColumn<ICustomerView, String> tcAddress = new TableColumn<>("Address");
    private TableColumn<ICustomerView, String> tcAddress2 = new TableColumn<>("Address 2");
    private TableColumn<ICustomerView, String> tcCity = new TableColumn<>("City");
    private TableColumn<ICustomerView, String> tcPostalCode = new TableColumn<>("Postal Code");
    private TableColumn<ICustomerView, String> tcCountry = new TableColumn<>("Country");
    private TableColumn<ICustomerView, String> tcPhone = new TableColumn<>("Phone");
    private TableColumn<ICustomerView, Byte> tcActive = new TableColumn<>("Active");
    private ObservableMap<ZonedDateTime, ICustomerView> omCustomerView;
    private DataViewController dataViewController;
    private static CustomerViewController instance;

    private CustomerViewController() {
        initialize(MainApp.class.getResource("/fxml/CustomerView.fxml"), null);
    }

    public static CustomerViewController getInstance() {
        if(instance == null){
            new CustomerViewController();
        }
        return instance;
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     */
    public void initialize(URL location, ResourceBundle resourceBundle) {
        instance = this;
        dataViewController = new DataViewController();
        this.apCustomerView = new AnchorPane();
        this.vbCustomerEditor = new VBox();
        this.vbCustomerEditor.setPrefHeight(475.0);
        this.vbCustomerEditor.setPrefWidth(985.0);
        this.vbCustomerEditor.setPadding(new Insets(5.0));
        this.vbCustomerEditor.setSpacing(10.0);
        this.btnNewCustomer = new Button("New Customer");
        this.tbCustomerEditMode = new ToggleButton("Edit Customer");
        this.tbCustomerEditMode.setSelected(false);
        this.hbCustomerEditor = new HBox(this.tbCustomerEditMode, this.btnNewCustomer);
        this.hbCustomerEditor.setPadding(new Insets(5));
        this.hbCustomerEditor.setSpacing(10.0);

        setupGridPane();

        // Add gpCustomerEditor and add it to vbCustomerEditor and apCustomerEditor to complete view
        this.vbCustomerEditor.getChildren().addAll(hbCustomerEditor, gpCustomerEditor);
        this.apCustomerView.getChildren().add(vbCustomerEditor);

        setupDataView();
        setupEventHandlers();
    }

    private void getCustomerViewData() throws SQLException {
        List<ICustomerView> customerViews = new LinkedList<>();
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * from Customers");
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                Boolean active;

                switch(rs.getByte(8)){
                    case 0:
                        active = false;
                        break;
                    default:
                        active = true;
                        break;
                }

                CustomerViewProperty view = new CustomerViewProperty(
                        new CustomerView(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        active,
                        rs.getDate(9),
                        rs.getString(10),
                        rs.getString(11),
                        ZonedDateTime.ofInstant(rs.getTimestamp(12).toInstant(), ZoneId.systemDefault()))
                );

                customerViews.add(view);
            }
            // Populate MainApp collection customerList
            setCustomerList(FXCollections.observableList(customerViews));
        }
    }

    private void getCountryData() throws  SQLException {
        HashMap<Integer, ICountry> countryHashMap = new HashMap<>();
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM country");
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                CountryProperty country = new CountryProperty(
                rs.getInt("countryId"),
                rs.getString("country"),
                rs.getString("createBy"),
                rs.getDate("createDate"),
                rs.getTimestamp("lastUpdate"),
                rs.getString("lastUpdatedBy")
                );

                countryHashMap.put(country.hashCode(), country);
            }
            setCountries(FXCollections.observableMap(countryHashMap));
        }
    }

    private void getCityData() throws SQLException {
        HashMap<Integer, ICity> cityHashMap = new HashMap<>();
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM city");
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                CityProperty city = new CityProperty(
                        rs.getInt("cityId"),
                        rs.getString("city"),
                        rs.getInt("countryId"),
                        rs.getString("createdBy"),
                        rs.getTimestamp("lastUpdate"),
                        rs.getString("lastUpdatedByProperty"),
                        ZonedDateTime.ofInstant(rs.getDate("createDate").toInstant(), ZoneId.systemDefault())
                );

                cityHashMap.put(city.hashCode(), city);
            }
            setCities(FXCollections.observableMap(cityHashMap));
        }

    }

    private void getAddressData() throws SQLException {
        HashMap<Integer, IAddress> addressHashMap = new HashMap<>();
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM address");
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                AddressProperty address = new AddressProperty(
                        rs.getInt("cityId"),
                        rs.getString("address"),
                        rs.getString("address2"),
                        rs.getString("postalCode"),
                        rs.getString("phone"),
                        rs.getString("createdBy")
                );
                addressHashMap.put(address.hashCode(), address);
            }
            setAddresses(addressHashMap);
        }
    }

    private void getCustomerData() throws SQLException {
        HashMap<Integer, ICustomer> customerHashMap = new HashMap<>();
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * from customer");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                CustomerProperty customer = new CustomerProperty(
                        resultSet.getDate("createDate").toLocalDate(),
                        resultSet.getInt("customerIdProperty"),
                        resultSet.getByte("active"),
                        resultSet.getInt("addressId"),
                        resultSet.getString("createdBy"),
                        resultSet.getString("customerName"),
                        resultSet.getString("lastUpdateBy"),
                        resultSet.getTimestamp("lastUpdate")
                );

                customerHashMap.put(customer.hashCode(), customer);
            }
            setCustomers(customerHashMap);
        }
    }

    public DataViewController getDataViewController() {
        return dataViewController;
    }

    private void createNewCustomer(){
        tbCustomerEditMode.setSelected(true);
        tbCustomerEditMode.setDisable(true);

        // enable text fields and make them editable
        enableEditing();

        this.gpCustomerEditor.getChildren().filtered(node -> {
            if (node instanceof TextField){
                ((TextField) node).clear();
                return true;
            }
            return false;
        });

        Platform.runLater(() -> txtCustomerName.positionCaret(0));
    }

    public void enableEditing() {
        if(tbCustomerEditMode.isSelected()){
            this.tbCustomerEditMode.setText("Read Only Mode");
            this.gpCustomerEditor.getChildren().filtered(n -> {
                if (n instanceof TextField){
                    if (n.isDisabled())
                        n.setDisable(false);

                    if (!((TextField) n).isEditable())
                        ((TextField) n).setEditable(true);

                    return true;
                }
                return false;
            });
        }
        else {
            this.tbCustomerEditMode.setText("Edit Mode");
            this.gpCustomerEditor.getChildren().filtered(n -> {
                if (n instanceof TextField){
                    n.setDisable(true);
                    System.out.println(String.format("%s is disabled:\t%s", n.getClass().getName(), n.isDisabled()));
                    ((TextField) n).setEditable(false);
                    System.out.println(String.format("%s is editable:\t%s", n.getClass().getName(), ((TextField) n).isEditable()));
                    return true;
                }
                return false;
            });
        }


    }

    private void setupGridPane() {
        this.gpCustomerEditor = new GridPane();
        this.lblCustomerName = new Label("Customer Name:");
        this.lblAddress = new Label("Address:");
        this.lblAddress2 = new Label("Address2:");
        this.lblCity = new Label("City:");
        this.lblState = new Label("State / Province:");
        this.lblPostalCode = new Label("Postal Code:");
        this.lblCountry = new Label("Country:");
        this.lblPhone = new Label("Phone:");
        this.lblCustomerSince = new Label("Customer Since:");
        this.lblCustomerCreatedDate = new Label();
        this.cbActive = new CheckBox("Active");
        this.cbActive.setSelected(false);
        this.lblPrefix = new Label("Prefix");
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
        this.btnCustomerCancel = new Button("Cancel");
        this.btnCustomerCancel.setCancelButton(true);
        this.btnCustomerOk = new Button("Save");
        this.btnCustomerOk.setDefaultButton(true);

        this.lblCustomerName.setLabelFor(txtCustomerName);
        this.lblAddress.setLabelFor(txtAddress);
        this.lblAddress2.setLabelFor(txtAddress2);
        this.lblCity.setLabelFor(txtCity);
        this.lblState.setLabelFor(txtState);
        this.lblPostalCode.setLabelFor(txtPostalCode);
        this.lblCountry.setLabelFor(txtCountry);
        this.lblPhone.setLabelFor(hbPhone);
        this.lblPrefix.setLabelFor(txtPhone);
        this.lblCustomerSince.setLabelFor(lblCustomerCreatedDate);

        // Setup child containers of gpCustomerEditor
        this.btnbarCustomerEditor.getButtons().addAll(btnCustomerOk, btnCustomerCancel);

        this.gpCustomerEditor.add(lblCustomerName, 0, 0);
        this.gpCustomerEditor.add(txtCustomerName, 1, 0);
        this.gpCustomerEditor.add(lblAddress, 0, 1);
        this.gpCustomerEditor.add(txtAddress, 1, 1);
        this.gpCustomerEditor.add(lblAddress2, 0, 2);
        this.gpCustomerEditor.add(txtAddress2, 1, 2);
        this.gpCustomerEditor.add(lblCity, 0, 3);
        this.gpCustomerEditor.add(txtCity, 1, 3);
        this.gpCustomerEditor.add(lblState, 0, 4);
        this.gpCustomerEditor.add(txtState, 1, 4);
        this.gpCustomerEditor.add(lblPostalCode, 0, 5);
        this.gpCustomerEditor.add(txtPostalCode, 1, 5);
        this.gpCustomerEditor.add(lblCountry, 0, 6);
        this.gpCustomerEditor.add(txtCountry, 1, 6);
        this.gpCustomerEditor.add(lblPhone, 0, 7);
        this.gpCustomerEditor.add(hbPhone, 1, 7);
        this.gpCustomerEditor.add(lblCustomerSince, 0, 8);
        this.gpCustomerEditor.add(lblCustomerCreatedDate, 1, 8);
        this.gpCustomerEditor.add(cbActive, 1, 9);
        this.gpCustomerEditor.add(btnbarCustomerEditor, 1, 10);

        this.gpCustomerEditor.setVgap(5.0);
        this.gpCustomerEditor.setHgap(10.0);
        this.gpCustomerEditor.getRowConstraints().setAll(new RowConstraints(10.0, 30.0, Integer.MAX_VALUE));
        this.gpCustomerEditor.getColumnConstraints().setAll(new ColumnConstraints(10.0, 150.0, Integer.MAX_VALUE));
        this.gpCustomerEditor.setPadding(new Insets(10, 20, 20, 20));

        setupTextFields(true);
    }

    private void setupDataView() {
        dataViewController.setLblListView(new Label("Customer List View"));
        dataViewController.setLblTableView(new Label("Customer Table View"));
        dataViewController.setListView(new ListView<ICustomerView>());

        try {
            getCustomerData();
        } catch (SQLException e) {
            System.out.println("Could not get customer data due to SQL error!");
            e.printStackTrace();
        }

        try {
            getAddressData();
        }
        catch (SQLException e){
            System.out.println("Could not obtain address data!");
            e.getLocalizedMessage();
        }

        try {
            getCityData();
        }
        catch (SQLException e){
            System.out.println("Could not get city data!");
            e.getLocalizedMessage();
        }

        try {
            getCountryData();
        }
        catch (SQLException e){
            System.out.println("Could not get country data!");
            e.getLocalizedMessage();
        }

        try {
            getCustomerViewData();
        }
        catch (SQLException e) {
            System.out.println("Could not get customer view information");
            e.getLocalizedMessage();
        }

        this.tvCustomerView = new TableView<>();
        this.tvCustomerView.setItems(getCustomerList());
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

        dataViewController.setTableView(this.tvCustomerView);
    }

    private void setupEventHandlers(){
        this.tbCustomerEditMode.setOnAction(event -> enableEditing());

        this.btnNewCustomer.setOnAction(event -> createNewCustomer());

        this.btnCustomerOk.setOnAction(event -> saveCustomer());
    }

    /***
     * Sets whether text fields are enabled or disabled
     * true = disable Text Fields
     * false = enable Text Fields
     * @param disabled
     */
    private void setupTextFields(Boolean disabled) {
        this.gpCustomerEditor.getChildren().filtered(node -> {
            if(node instanceof TextField){
                ((TextField) node).setPrefWidth(300.0);
                ((TextField) node).setEditable(!disabled);
                node.setDisable(disabled);
                return true;
            }
            return false;
        });
    }

    public void setDataViewController(DataViewController dataViewController) {
        this.dataViewController = dataViewController;
    }

    private void saveCustomer() {
        String name = txtCustomerName.getText();
        String addr = txtAddress.getText();
        String addr2 = txtAddress2.getText();
        String city = txtCity.getText();
        String state = txtState.getText();
        String postal = txtPostalCode.getText();
        String country = txtCountry.getText();
        String phone = txtPhone.getText();

        ObservableMap countries = getCountries();
        ObservableMap cities = getCities();
        ObservableMap addresses = getAddresses();
        ObservableMap customers = getCustomers();
    }

    public Label getLblPrefix() {
        return lblPrefix;
    }

    public void setLblPrefix(Label lblPrefix) {
        this.lblPrefix = lblPrefix;
    }

    public TextField getTxtCustomerName() {
        return txtCustomerName;
    }

    public void setTxtCustomerName(TextField txtCustomerName) {
        this.txtCustomerName = txtCustomerName;
    }

    public TextField getTxtAddress() {
        return txtAddress;
    }

    public void setTxtAddress(TextField txtAddress) {
        this.txtAddress = txtAddress;
    }

    public TextField getTxtAddress2() {
        return txtAddress2;
    }

    public void setTxtAddress2(TextField txtAddress2) {
        this.txtAddress2 = txtAddress2;
    }

    public TextField getTxtCity() {
        return txtCity;
    }

    public void setTxtCity(TextField txtCity) {
        this.txtCity = txtCity;
    }

    public TextField getTxtState() {
        return txtState;
    }

    public void setTxtState(TextField txtState) {
        this.txtState = txtState;
    }

    public TextField getTxtPostalCode() {
        return txtPostalCode;
    }

    public void setTxtPostalCode(TextField txtPostalCode) {
        this.txtPostalCode = txtPostalCode;
    }

    public TextField getTxtCountry() {
        return txtCountry;
    }

    public void setTxtCountry(TextField txtCountry) {
        this.txtCountry = txtCountry;
    }

    public TextField getTxtPhone() {
        return txtPhone;
    }

    public void setTxtPhone(TextField txtPhone) {
        this.txtPhone = txtPhone;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}

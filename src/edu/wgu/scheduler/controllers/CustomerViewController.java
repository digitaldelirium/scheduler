package edu.wgu.scheduler.controllers;

import edu.wgu.scheduler.MainApp;
import edu.wgu.scheduler.models.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.net.URL;
import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import static edu.wgu.scheduler.MainApp.*;
import static edu.wgu.scheduler.controllers.AppViewController.toggleTextFields;

/**
 * Created by Ian Cornett - icornet@wgu.edu on 7/16/2017 at 21:21.
 * Part of Project: scheduler
 * <p>
 * Student ID: 000292065
 */
public class CustomerViewController extends AnchorPane {
    
    public AnchorPane apCustomerView;
    private VBox vbCustomerEditor;
    private ToggleButton tbCustomerEditMode;
    private GridPane gpCustomerEditor;
    private Label lblAddress;
    private Label lblCustomerName;
    private Label lblAddress2;
    private Label lblCity;
    private Label lblState;
    private Label lblPostalCode;
    private Label lblCountry;
    private Label lblCustomerSince;
    private Label lblCustomerCreatedDate;
    private Label lblPhone;
    private Label lblPrefix;
    private ButtonBar btnbarCustomerEditor;
    private Button btnCustomerOk;
    private Button btnCustomerCancel;
    private TextField txtCustomerName;
    private TextField txtAddress;
    private TextField txtAddress2;
    private TextField txtCity;
    private TextField txtState;
    private TextField txtPostalCode;
    private TextField txtCountry;
    private TextField txtPhone;
    private HBox hbPhone;
    
    private CheckBox cbActive;
    
    private HBox hbCustomerEditor;
    
    private Button btnNewCustomer;
    private MainApp mainApp;
    private boolean isNewCustomer;
    private ListView<ICustomerView> lvCustomerView;
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
    private boolean isCustomerUpdate;

    private CustomerViewController() {
        initialize();
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
    public void initialize() {
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
        this.vbCustomerEditor.getChildren().addAll(hbCustomerEditor, gpCustomerEditor /*, this.dataViewController.tabPane */);
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
                boolean active;

                switch(rs.getByte("active")){
                    case 0:
                        active = false;
                        break;
                    default:
                        active = true;
                        break;
                }

                CustomerView view = new CustomerView(
                        rs.getString("customerName"),
                        rs.getString("address"),
                        rs.getString("address2"),
                        rs.getString("city"),
                        rs.getString("postalCode"),
                        rs.getString("country"),
                        rs.getString("phone"),
                        active,
                        rs.getTimestamp("createDate"),
                        rs.getString("createdBy"),
                        rs.getString("lastUpdateBy"),
                        rs.getTimestamp("lastUpdate")
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
                Country country = new Country(
                rs.getInt("countryId"),
                rs.getString("country"),
                rs.getString("createdBy"),
                rs.getTimestamp("createDate"),
                rs.getTimestamp("lastUpdate"),
                rs.getString("lastUpdateBy")
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
                City city = new City(
                        rs.getInt("cityId"),
                        rs.getString("city"),
                        rs.getInt("countryId"),
                        rs.getString("createdBy"),
                        rs.getTimestamp("lastUpdate"),
                        rs.getString("lastUpdateBy"),
                        rs.getTimestamp("createDate")
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
                Address address = new Address(
                        rs.getInt("cityId"),
                        rs.getString("address"),
                        rs.getString("address2"),
                        rs.getString("postalCode"),
                        rs.getString("phone"),
                        rs.getString("createdBy"),
                        rs.getTimestamp("lastUpdate"),
                        rs.getTimestamp("createDate")
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
                Customer customer = new Customer(
                        resultSet.getTimestamp("createDate"),
                        resultSet.getInt("customerId"),
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
        isNewCustomer = true;
        tbCustomerEditMode.setSelected(true);
        tbCustomerEditMode.setDisable(false);

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
            System.out.println(e.getLocalizedMessage());
        }

        dataViewController.setLblTableView(new Label("Customers"));
        dataViewController.setLblListView(new Label("Customer List"));
        dataViewController.setListView(new ListView<>(getCustomerList()));
        this.tvCustomerView = new TableView<>(getCustomerList());
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
        this.lvCustomerView = new ListView<>(getCustomerList());
        dataViewController.setListView(this.lvCustomerView);
    }

    private void setupEventHandlers(){
        this.tbCustomerEditMode.setOnAction(event -> enableEditing());

        this.btnNewCustomer.setOnAction(event -> createNewCustomer());

        this.btnCustomerOk.setOnAction(event -> {
            try {
                saveCustomer();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /***
     * Sets whether text fields are enabled or disabled
     * true = disable Text Fields
     * false = enable Text Fields
     * @param disabled
     */
    private void setupTextFields(Boolean disabled) {
        this.gpCustomerEditor.getChildren().filtered(node -> toggleTextFields(node, disabled));
    }

    private boolean saveCustomer() throws SQLException {
        int countryId;
        int addressId = 0;
        int cityId = 0;
        int customerId = 0;
        String name = txtCustomerName.getText().trim();
        String addr = txtAddress.getText().trim();
        String addr2 = txtAddress2.getText().trim();
        String city = txtCity.getText().trim();
        String state = txtState.getText().trim();
        String postal = txtPostalCode.getText().trim();
        String country = txtCountry.getText().trim();
        String phone = txtPhone.getText().trim();

        ObservableMap<Integer, ICustomer> customers = getCustomers();

        try(Connection connection = dataSource.getConnection()){

            countryId = getCountryId(connection, country);

            if(countryId != 0) {
                cityId = getCityId(connection, countryId, city);
            }

            if(cityId != 0) {
                addressId = getAddressId(connection, cityId, addr, addr2, postal, phone);
            }

            if(addressId != 0) {
                customerId = getCustomerId(connection, addressId, name, isNewCustomer);
            }

            if (customerId != 0){
                PreparedStatement statement = connection.prepareStatement("SELECT createDate FROM customer WHERE customerId = ?;");
                ResultSet rs = statement.executeQuery();
                if(rs.next()){
                    this.lblCustomerSince.setText(rs.getTimestamp(1).toInstant().atZone(ZoneId.of("UTC")).format(DateTimeFormatter.ISO_DATE));
                }

                this.dataViewController.setListView(new ListView<>(getCustomerList()));
                this.dataViewController.setTableView(new TableView<>(getCustomerList()));

                resetForm();
                return true;
            }
        }
        resetForm();
        return false;
    }

    private void resetForm() {
        this.gpCustomerEditor.getChildren().filtered(node -> {
            if (node instanceof TextField){
                ((TextField) node).clear();
                return true;
            }
            else if(node instanceof CheckBox){
                ((CheckBox) node).setSelected(false);
                return true;
            }
            else if(node instanceof Button){
                node.setDisable(true);
            }
            return false;
        });
    }

    private int getCustomerId(Connection connection, int addressId, String name, boolean isNewCustomer) {
        int customerId = 0;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT customerId FROM customer WHERE LCASE(customerName) LIKE LCASE(?)" +
                                                                              "AND addressId = ?");
            statement.setString(1, name);
            statement.setInt(2, addressId);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                customerId = rs.getInt(1);
                statement.close();
                if(!isCustomerUpdate) {
                    return customerId;
                }
            }

            if(isNewCustomer){
                statement = connection.prepareStatement("INSERT INTO customer (customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy)" +
                                                                "VALUES (?, ?, ?, UTC_TIMESTAMP, ?, UTC_TIMESTAMP, ?);");
                statement.setString(1, txtCustomerName.getText().trim());
                statement.setInt(2, addressId);
                statement.setBoolean(3, true);
                statement.setString(4, user.getUsername());
                statement.setString(5, user.getUsername());
                boolean failed = statement.execute();
                if(!failed){
                    statement = connection.prepareStatement("SELECT customerId FROM customer WHERE LCASE(customerName) LIKE LCASE(?)" +
                                                                                      "AND addressId = ?");
                    statement.setString(1, name);
                    statement.setInt(2, addressId);
                    rs = statement.executeQuery();
                    if(rs.next()){
                        customerId = rs.getInt(1);
                        this.isNewCustomer = false;
                    }
                    statement.close();
                    getCustomerData();
                    getCustomerViewData();
                    this.dataViewController.setListView(this.lvCustomerView);
                    this.dataViewController.setTableView(this.tvCustomerView);
                    rootPane.setBottom(this.dataViewController);
                    this.txtPhone.clear();
                    this.gpCustomerEditor.getChildren().filtered(node -> toggleTextFields(node, true));
                    return customerId;
                }
            } else if(isCustomerUpdate && customerId != 0) {
                statement = connection.prepareStatement("UPDATE customer\n" +
                                                                "SET customerName = ?, addressId = ?, lastUpdateBy = ?, lastUpdate = ?\n" +
                                                                "WHERE customerId = ?;");
                statement.setString(1, txtCustomerName.getText().trim());
                statement.setInt(2, addressId);
                statement.setString(3, user.getUsername());
                statement.setTimestamp(4, new Timestamp(ZonedDateTime.now(ZoneId.of("UTC")).toEpochSecond() * 1000));
                statement.setInt(5, customerId);
                int recordsUpdated = statement.executeUpdate();
                if (recordsUpdated > 0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Customer update succeeded!");
                    alert.setContentText(String.format("There were %d records updated!", recordsUpdated));
                    alert.show();
                    getCustomerData();
                    getCustomerViewData();
                    this.isCustomerUpdate = false;
                    return customerId;
                }
            }
            this.isNewCustomer = false;
            this.isCustomerUpdate = false;
            statement.close();
            getCustomerData();
            getCustomerViewData();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error querying customer data");
            alert.setContentText("There was an error querying the database for customer data!");
            alert.show();
        }
        return 0;
    }

    private int getAddressId(Connection connection, int cityId, String addr, String addr2, String postal, String phone) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT addressId FROM address\n" +
                                                                              "WHERE LCASE(address) LIKE LCASE(?)\n" +
                                                                              "AND LCASE(address2) LIKE LCASE(?)\n" +
                                                                              "AND LCASE(postalCode) LIKE LCASE(?)\n" +
                                                                              "AND LCASE(phone) LIKE LCASE(?)\n" +
                                                                              "AND cityId = ?;");
            statement.setString(1, addr);
            statement.setString(2, addr2);
            statement.setString(3, postal);
            statement.setString(4, phone);
            statement.setInt(5, cityId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int addressId = rs.getInt(1);
                statement.close();
                getAddressData();
                return addressId;
            }

            statement = connection.prepareStatement("INSERT INTO address " +
                                                            "(address, address2, cityId, postalCode, phone, createdBy, " +
                                                            "createDate, lastUpdate, lastUpdateBy)\n" +
                                                            "    VALUES (?, ?, ?, ?, ?, ?, UTC_TIMESTAMP, UTC_TIMESTAMP, ?);");
            statement.setString(1, addr);
            statement.setString(2, addr2);
            statement.setInt(3, cityId);
            statement.setString(4, postal);
            statement.setString(5, phone);
            statement.setString(6, user.getUsername());
            statement.setString(7, user.getUsername());
            boolean failed = statement.execute();
            if (!failed){
                statement = connection.prepareStatement("SELECT addressId FROM address \n" +
                                                                "WHERE LCASE(address) = LCASE(?)\n" +
                                                                "      AND LCASE(address2) = LCASE(?) \n" +
                                                                "      AND LCASE(postalCode) = LCASE(?)\n" +
                                                                "      AND LCASE(phone) = LCASE(?) \n" +
                                                                "      AND cityId = ?;");
                statement.setString(1, addr);
                statement.setString(2, addr2);
                statement.setString(3, postal);
                statement.setString(4, phone);
                statement.setInt(5, cityId);
                rs = statement.executeQuery();

                if(rs.next()){
                    int addrId = rs.getInt(1);
                    getAddressData();
                    statement.close();
                    return addrId;
                }
                statement.close();
                getAddressData();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error querying address data");
            alert.setContentText("There was an error querying the database for address data!");
            alert.show();
        }
        return 0;
    }

    private int getCountryId(Connection connection, String country) {
        int countryId;
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT countryId FROM country\n" +
                                                                              "WHERE LCASE(country) LIKE LCASE(?);");
            statement.setString(1, country);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                countryId = rs.getInt(1);
                statement.close();
                getCountryData();
                return countryId;
            }
            else {
                statement = connection.prepareStatement(
                        "INSERT INTO country (country, createdBy, createDate, lastUpdate, lastUpdateBy)\n" +
                                "    VALUES (?, ?, UTC_TIMESTAMP, UTC_TIMESTAMP, ?);");
                statement.setString(1, country);
                statement.setString(2, user.getUsername());
                statement.setString(3, user.getUsername());
                boolean failed = statement.execute();
                if(failed){
                    throw new SQLDataException("Insert country operation failed!");
                }
                else {
                    statement = connection.prepareStatement("SELECT countryId FROM country WHERE LCASE(country) LIKE LCASE(?)");
                    statement.setString(1, country);
                    rs = statement.executeQuery();
                    if(rs.next()){
                        countryId =  rs.getInt(1);
                        getCountryData();
                        statement.close();
                        return countryId;
                    }
                }
                statement.close();
            }
        } catch (SQLDataException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error inserting country data");
            alert.setContentText(String.format("There was an error saving the country to the database\n%s", e.toString()));
            alert.show();
        }
        catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error querying country data");
            alert.setContentText("There was an error querying the database for country data!");
            alert.show();
        }
        return 0;
    }

    private int getCityId(Connection connection, int countryId, String city) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT cityId FROM city\n" +
                                                                              "WHERE LCASE(city) LIKE LCASE(?)\n" +
                                                                              "AND countryId = ?;");
            statement.setString(1, city);
            statement.setInt(2, countryId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int cityId =  rs.getInt(1);
                statement.close();
                return cityId;
            }
            else {
                statement = connection.prepareStatement("INSERT INTO city (city, countryId, createDate, createdBy, lastUpdateBy, lastUpdate)" +
                                                                "VALUES (?, ?, UTC_TIMESTAMP, ?, ?, UTC_TIMESTAMP);");
                statement.setString(1, city);
                statement.setInt(2, countryId);
                statement.setString(3, user.getUsername());
                statement.setString(4, user.getUsername());
                boolean failed = statement.execute();

                if(failed){
                    throw new SQLDataException("Failed to insert City!");
                }

                statement = connection.prepareStatement("SELECT * from city WHERE city = ?");
                statement.setString(1, city);
                rs = statement.executeQuery();

                if(rs.next()){
                    int cityId = rs.getInt(1);
                    statement.close();
                    getCityData();
                    return cityId;
                }
            }
            statement.close();
            getCityData();
        }
        catch (SQLDataException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error querying city data");
            alert.setContentText(String.format("There was an error querying the database for city data!\n%s", e.getMessage()));
            alert.show();
        }
        catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error querying city data");
            alert.setContentText("There was an error querying the database for city data!");
            alert.show();
        }
        return 0;
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

    public GridPane getGpCustomerEditor() {
        return gpCustomerEditor;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("CustomerViewController{")
                .append("\napCustomerView=")
                .append(apCustomerView)
                .append(",\n vbCustomerEditor=")
                .append(vbCustomerEditor)
                .append(",\n tbCustomerEditMode=")
                .append(tbCustomerEditMode)
                .append(",\n gpCustomerEditor=")
                .append(gpCustomerEditor)
                .append(",\n lblAddress=")
                .append(lblAddress)
                .append(",\n lblCustomerName=")
                .append(lblCustomerName)
                .append(",\n lblAddress2=")
                .append(lblAddress2)
                .append(",\n lblCity=")
                .append(lblCity)
                .append(",\n lblState=")
                .append(lblState)
                .append(",\n lblPostalCode=")
                .append(lblPostalCode)
                .append(",\n lblCountry=")
                .append(lblCountry)
                .append(",\n lblCustomerSince=")
                .append(lblCustomerSince)
                .append(",\n lblCustomerCreatedDate=")
                .append(lblCustomerCreatedDate)
                .append(",\n lblPhone=")
                .append(lblPhone)
                .append(",\n lblPrefix=")
                .append(lblPrefix)
                .append(",\n btnbarCustomerEditor=")
                .append(btnbarCustomerEditor)
                .append(",\n btnCustomerOk=")
                .append(btnCustomerOk)
                .append(",\n btnCustomerCancel=")
                .append(btnCustomerCancel)
                .append(",\n txtCustomerName=")
                .append(txtCustomerName)
                .append(",\n txtAddress=")
                .append(txtAddress)
                .append(",\n txtAddress2=")
                .append(txtAddress2)
                .append(",\n txtCity=")
                .append(txtCity)
                .append(",\n txtState=")
                .append(txtState)
                .append(",\n txtPostalCode=")
                .append(txtPostalCode)
                .append(",\n txtCountry=")
                .append(txtCountry)
                .append(",\n txtPhone=")
                .append(txtPhone)
                .append(",\n hbPhone=")
                .append(hbPhone)
                .append(",\n cbActive=")
                .append(cbActive)
                .append(",\n hbCustomerEditor=")
                .append(hbCustomerEditor)
                .append(",\n btnNewCustomer=")
                .append(btnNewCustomer)
                .append(",\n isNewCustomer=")
                .append(isNewCustomer)
                .append(",\n lvCustomerView=")
                .append(lvCustomerView)
                .append(",\n tvCustomerView=")
                .append(tvCustomerView)
                .append(",\n tcCustomerName=")
                .append(tcCustomerName)
                .append(",\n tcAddress=")
                .append(tcAddress)
                .append(",\n tcAddress2=")
                .append(tcAddress2)
                .append(",\n tcCity=")
                .append(tcCity)
                .append(",\n tcPostalCode=")
                .append(tcPostalCode)
                .append(",\n tcCountry=")
                .append(tcCountry)
                .append(",\n tcPhone=")
                .append(tcPhone)
                .append(",\n tcActive=")
                .append(tcActive)
                .append(",\n omCustomerView=")
                .append(omCustomerView)
                .append(",\n dataViewController=")
                .append(dataViewController)
                .append(",\n isCustomerUpdate=")
                .append(isCustomerUpdate)
                .append("\n}")
                .toString();
    }
}

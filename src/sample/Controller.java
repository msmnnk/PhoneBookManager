package sample;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class Controller {

    Model Data;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Abonent> Table = new TableView<Abonent>();

    @FXML
    private TableColumn<Abonent, String> surnameCol = new TableColumn<Abonent, String>("surname");

    @FXML
    private TableColumn<Abonent, Integer> yearCol = new TableColumn<Abonent, Integer>("year");

    @FXML
    private TableColumn<Abonent, String> numberCol = new TableColumn<Abonent, String>("number");

    @FXML
    private TableColumn<Abonent, String> addressCol = new TableColumn<Abonent, String>("address");

    @FXML
    private TextField surnameF;

    @FXML
    private TextField cF;

    @FXML
    private TextField numberF;

    @FXML
    private TextField addresF;

    @FXML
    private Label errorBase;

    @FXML
    private Label errorQuery;

    @FXML
    private ListView<String> resultList;

    @FXML
    private TextField aF;

    @FXML
    private TextField bF;

    @FXML
    private TextField yearF;

    @FXML
    void TableClick(MouseEvent event) {
        if (Table.getSelectionModel().getSelectedItem() != null) {
            Abonent selAbonent = Table.getSelectionModel().getSelectedItem();
            surnameF.setText(selAbonent.getSurname());
            yearF.setText(String.valueOf(selAbonent.getYear()));
            numberF.setText(selAbonent.getNumber());
            addresF.setText(selAbonent.getAddress());
        }
    }

    @FXML
    void addButtonClick(MouseEvent event) {
        errorBase.setVisible(false);
        String surname = surnameF.getText();
        String year = yearF.getText();
        String number = numberF.getText();
        String address = addresF.getText();
        try {
            Data.add(surname, Integer.parseInt(year), number, address);
            load();
        } catch (SQLException e) {
            errorBase.setVisible(true);
            errorBase.setText("SQL: error");
        }
    }

    @FXML
    void deleteButtonClick(MouseEvent event) {
        if (Table.getSelectionModel().getSelectedItem() != null) {
            errorBase.setVisible(false);
            try {
                Data.delete(Table.getSelectionModel().getSelectedItem().getNumber());
                load();
            } catch (SQLException e) {
                errorBase.setVisible(true);
                errorBase.setText("SQL: Error");
            }
        } else {
            errorBase.setVisible(true);
            errorBase.setText("Delete: Not selected");
        }
    }

    @FXML
    void editButtonClick(MouseEvent event) {
        if (Table.getSelectionModel().getSelectedItem() != null) {
            errorBase.setVisible(false);
            String surname = surnameF.getText();
            String year = yearF.getText();
            String number = numberF.getText();
            String address = addresF.getText();
            String editNumber = Table.getSelectionModel().getSelectedItem().getNumber();
            try {
                Data.update(editNumber, surname, Integer.parseInt(year), number, address);
                load();
            } catch (SQLException e) {
                errorBase.setVisible(true);
                errorBase.setText("SQL: Error");
            }
        } else {
            errorBase.setVisible(true);
            errorBase.setText("Not selection");
        }
    }

    @FXML
    void aButtonClick(MouseEvent event) {
        if (!aF.getText().isEmpty()) {
            String surname = aF.getText();
            String comm = "SELECT  `number` FROM `abonents` WHERE `surname` = \"" + surname + "\"";
            try {
                ResultSet res = Data.query(comm);
                ObservableList<String> list = FXCollections.observableArrayList();
                errorQuery.setVisible(false);
                while (res.next()) {
                    list.add("number: " + res.getString("number"));
                }
                if (!list.isEmpty()) {
                    resultList.setItems(list);
                } else {
                    errorQuery.setVisible(true);
                    errorQuery.setText("Not found");
                }
            } catch (SQLException e) {
                errorQuery.setVisible(true);
                errorQuery.setText("SQL: Error");
            }
        } else {
            errorQuery.setVisible(true);
            errorQuery.setText("Error: Empty");
        }
    }


    @FXML
    void bButtonClick(MouseEvent event) {
        if (!bF.getText().isEmpty()) {
            String number = bF.getText();
            String comm = "SELECT  `surname` FROM `abonents` WHERE `number` = \"" + number + "\"";
            try {
                ResultSet res = Data.query(comm);
                ObservableList<String> list = FXCollections.observableArrayList();
                errorQuery.setVisible(false);
                while (res.next()) {
                    list.add("surname: " + res.getString("surname"));
                }
                if (!list.isEmpty()) {
                    resultList.setItems(list);
                } else {
                    errorQuery.setVisible(true);
                    errorQuery.setText("Not found");
                }
            } catch (SQLException e) {
                errorQuery.setVisible(true);
                errorQuery.setText("SQL: Error");
            }
        } else {
            errorQuery.setVisible(true);
            errorQuery.setText("Error: Empty");
        }
    }

    @FXML
    void cButtonClick(MouseEvent event) {
        if (!cF.getText().isEmpty()) {
            String year = cF.getText();
            String comm = "SELECT  count(`number`) as counter  FROM `abonents` WHERE `year` >= " + year + "; ";
            try {
                ResultSet res = Data.query(comm);
                ObservableList<String> list = FXCollections.observableArrayList();
                errorQuery.setVisible(false);
                while (res.next()) {
                    list.add("count: " + res.getString("counter"));
                }
                if (!list.isEmpty()) {
                    resultList.setItems(list);
                } else {
                    errorQuery.setVisible(true);
                    errorQuery.setText("Not found");
                }
            } catch (SQLException e) {
                errorQuery.setVisible(true);
                errorQuery.setText("SQL: Error");
            }
        } else {
            errorQuery.setVisible(true);
            errorQuery.setText("Error: Empty");
        }
    }

    @FXML
    void initialize() {
        errorBase.setVisible(false);
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        connect();
        load();
    }

    public void connect() {
        try {
            Data = new Model("root", "", "jdbc:mysql://localhost/abonents");
            errorBase.setVisible(false);
        } catch (SQLException | ClassNotFoundException e) {
            errorBase.setVisible(true);
            errorBase.setText("Connection error");
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            ResultSet res = Data.selectAllDB();
            errorBase.setVisible(false);
            ObservableList<Abonent> list = FXCollections.observableArrayList();
            while (res.next()) {
                Abonent abonent = new Abonent(res.getString("surname"), res.getInt("year"), res.getString("number"), res.getString("address"));
                list.add(abonent);
            }
            Table.setItems(list);
            surnameF.setText("");
            yearF.setText("");
            numberF.setText("");
            addresF.setText("");
        } catch (SQLException e) {
            errorBase.setVisible(true);
            errorBase.setText("Data load error");
            e.printStackTrace();
        }
    }
}

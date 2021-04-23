package esprit.pidev;

import esprit.pidev.entity.Formation;
import esprit.pidev.services.FormationServices;
import esprit.pidev.services.FormationServicesImp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ListMesFormation implements Initializable {


    @FXML
    public TableView<Formation> table;


    @FXML
    public TableColumn<Formation,String> nomFormation;
    @FXML
    public TableColumn<Formation,String> categorieFormation;
    @FXML
    public TableColumn<Formation,String> descFormation;
    @FXML
    public TableColumn<Formation,Integer> prixFormation;
    @FXML
    public TableColumn register;
    @FXML
    public TableColumn<Formation,String> dureeFormation;
    @FXML
    public TableColumn delete;


    ObservableList<Formation> myFormations = FXCollections.observableArrayList();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
        loadData();

        /************************/

        Callback<TableColumn<Formation, String>, TableCell<Formation, String>> cellFactory
                = //
                new Callback<TableColumn<Formation, String>, TableCell<Formation, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Formation, String> param) {
                        final TableCell<Formation, String> cell = new TableCell<Formation, String>() {

                            final Button btn = new Button("modifier");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(event -> {
                                        Formation formation = getTableView().getItems().get(getIndex());
                                        System.out.println("formation to update is " +  formation.toString());
                                        FormationServices formationServices = new FormationServicesImp();
                                        formationServices.update(formation);

                                    });

                                    setGraphic(btn);
                                    btn.setStyle("-fx-background-color: transparent; -fx-border-color: #1B76D7; -fx-border-radius: 20;");
                                    setText(null);

                                }
                            }
                        };
                        return cell;
                    }
                };

        /*************************/


        register.setCellFactory(cellFactory);


        /*****Start deleteButton  ****/

        Callback<TableColumn<Formation, String>, TableCell<Formation, String>> cellFactoryDelete
                = //
                new Callback<TableColumn<Formation, String>, TableCell<Formation, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Formation, String> param) {
                        final TableCell<Formation, String> cell = new TableCell<Formation, String>() {

                            final Button btn = new Button("Supprimer");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(event -> {
                                        Formation formation = getTableView().getItems().get(getIndex());
                                        System.out.println("formation to delete is :  " +  formation.toString());
                                        FormationServices formationServices = new FormationServicesImp();
                                        formationServices.deleteFormation(formation);
                                        myFormations.remove(formation);
                                    });

                                    setGraphic(btn);
                                    btn.setStyle("-fx-background-color: transparent; -fx-border-color: #1B76D7; -fx-border-radius: 20;");
                                    setText(null);


                                }
                            }
                        };
                        return cell;
                    }
                };



        delete.setCellFactory(cellFactoryDelete);


        /*****END deleteButton ****/


    }


    private void loadData(){
        int idOwner = 27;
        FormationServicesImp formationServicesImp = new FormationServicesImp();
        List<Formation> getMyFormations =  formationServicesImp.getAllFormationsByOwner(idOwner);


        for(Formation formation :getMyFormations){
            myFormations.add(formation);
        }

        table.setItems(myFormations);
    }


    public void initTable(){
        nomFormation.setCellValueFactory(new PropertyValueFactory<>("nomFormation"));
        categorieFormation.setCellValueFactory(new PropertyValueFactory<>("categorieFormation"));
        descFormation.setCellValueFactory(new PropertyValueFactory<>("descFormation"));
        prixFormation.setCellValueFactory(new PropertyValueFactory<>("prixFormation"));
        dureeFormation.setCellValueFactory(new PropertyValueFactory<>("dureeFormation"));

        editableRows();
    }

    private void editableRows(){

        nomFormation.setCellFactory(TextFieldTableCell.forTableColumn());

        nomFormation.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setNomFormation(e.getNewValue());
        });

        categorieFormation.setCellFactory(TextFieldTableCell.forTableColumn());

        categorieFormation.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setCategorieFormation(e.getNewValue());
        });



        descFormation.setCellFactory(TextFieldTableCell.forTableColumn());

        descFormation.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setDescFormation(e.getNewValue());
        });


        dureeFormation.setCellFactory((TextFieldTableCell.forTableColumn()));
        dureeFormation.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setDureeFormation(e.getNewValue());
        });

        table.setEditable(true);

    }

}

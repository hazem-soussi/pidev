package esprit.pidev;


import esprit.pidev.entity.Formation;

import esprit.pidev.services.FormationServices;
import esprit.pidev.services.FormationServicesImp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.util.Callback;
import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;

public class ListFormationController implements Initializable {



    @FXML
    public TableView<Formation> table;


    @FXML
    public TableColumn<Formation,String> nomFormation;
    @FXML
    public TableColumn<Formation,String> categorieFormation;
    @FXML
    public TableColumn<Formation,String> descFormation;
    @FXML
    public TableColumn<Formation,String> dureeFormation;
    @FXML
    public TableColumn<Formation,Integer> prixFormation;
    @FXML
    public TableColumn register;


    @FXML
    public TextField findFormation;


    ObservableList<Formation> listFormation = FXCollections.observableArrayList();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        FormationServicesImp formationServicesImp = new FormationServicesImp();
       // List<Formation> allFormation =  formationServicesImp.getAllFormation();
        List<Formation> allFormation = formationServicesImp.getFormation(27);



        for(Formation formation :allFormation){
            listFormation.add(formation);

        }

        searchFormation();

        nomFormation.setCellValueFactory(new PropertyValueFactory<>("nomFormation"));
        categorieFormation.setCellValueFactory(new PropertyValueFactory<>("categorieFormation"));
        descFormation.setCellValueFactory(new PropertyValueFactory<>("descFormation"));
        dureeFormation.setCellValueFactory(new PropertyValueFactory<>("dureeFormation"));
        prixFormation.setCellValueFactory(new PropertyValueFactory<>("prixFormation"));

        /********************************************************************/


        Callback<TableColumn<Formation, String>, TableCell<Formation, String>> cellFactory
                = //
                new Callback<TableColumn<Formation, String>, TableCell<Formation, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Formation, String> param) {
                        final TableCell<Formation, String> cell = new TableCell<Formation, String>() {

                            final Button btn = new Button("s'inscrire maintenant");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(event -> {
                                       Formation formation = getTableView().getItems().get(getIndex());
                                       System.out.println("register in " +  formation.toString());
                                        FormationServices formationServices = new FormationServicesImp();
                                        formationServices.registerToFormation(27,formation.getId());
                                        System.out.println("formation id to register is " + formation.getId());
                                        listFormation.remove(formation);
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



        register.setCellFactory(cellFactory);

        /*******************************************************************/

        table.setItems(listFormation);




    }



    void searchFormation(){


        FilteredList<Formation> filteredFormation = new FilteredList<>(listFormation,b->true);
        findFormation.textProperty().addListener(((observable, value1, value2) -> {
           filteredFormation.setPredicate((formation)->{
               if(value2 == null || value2.isEmpty()){
                   return true;
               }

               String lowerCaseFormationName = value2.toLowerCase();
               return formation.getNomFormation().toLowerCase().contains(lowerCaseFormationName);
           });


            SortedList<Formation> sortedFormation = new SortedList<>(filteredFormation);
            sortedFormation.comparatorProperty().bind(table.comparatorProperty());

            table.setItems(sortedFormation);



        }));


    }

}

package esprit.pidev;

import esprit.pidev.entity.Formation;
import esprit.pidev.services.FormationServices;
import esprit.pidev.services.FormationServicesImp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ajouterFormation {

    @FXML
    public TextField nomFormation;


    @FXML
    public TextField categorieFormation;


    @FXML
    public TextField prixFormation;



    @FXML
    public TextField dureeFormation;


    @FXML
    public TextArea descFormation;



    @FXML
    private void onAddFormationButton(ActionEvent event){

        FormationServices formationServices = new FormationServicesImp();
        Formation formation = new Formation();

        formation.setNomFormation(nomFormation.getText());
        formation.setCategorieFormation(categorieFormation.getText());
        formation.setDureeFormation(dureeFormation.getText());
        formation.setPrixFormation(Integer.parseInt(prixFormation.getText()));
        formation.setDescFormation(descFormation.getText());

        formationServices.addFormation(formation,27);
        System.out.println("La formation a été bien ajouté");
        System.out.println("La formation a ajouté est : " + nomFormation.getText() + " " +
                categorieFormation.getText() + " " +dureeFormation.getText() + " " + prixFormation.getText() + " " + descFormation.getText());

    }




}

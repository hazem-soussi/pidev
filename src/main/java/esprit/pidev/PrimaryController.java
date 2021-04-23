package esprit.pidev;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class PrimaryController implements  Initializable{

    @FXML
    public Button smartModeButton;
    @FXML
    private BorderPane mainPane;

    private boolean isClicked = false;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("button  1 clicked ");
        FxmLoader object = new FxmLoader();
        Pane view = object.getPane("listAllFormations");
        mainPane.setCenter(view);
        mainPane.setPrefWidth(1100);
        mainPane.setPadding(new Insets(0,30,0,0));
        smartModeButton.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));

    }


    @FXML
    private void onClickEventButton1(ActionEvent event) throws IOException {

        System.out.println("button  1 clicked ");
        FxmLoader object = new FxmLoader();
        Pane view = object.getPane("listAllFormations");
        mainPane.setCenter(view);
        mainPane.setPrefWidth(1100);
        mainPane.setPadding(new Insets(0,30,0,0));

    }


    @FXML
    private void onAddFormationButton(ActionEvent event){

        System.out.println("Button Add a New Formation clicked  ! ");

        FxmLoader object = new FxmLoader();
        Pane view = object.getPane("ajouterFormation");
        mainPane.setCenter(view);
        mainPane.setPrefWidth(1100);
        mainPane.setPadding(new Insets(0,30,0,0));


    }

   @FXML
    public void onSeeAllMyFormations(ActionEvent actionEvent) {

       FxmLoader object = new FxmLoader();
       Pane view = object.getPane("listMesFormations");
       mainPane.setCenter(view);
       mainPane.setPrefWidth(1100);
       mainPane.setPadding(new Insets(0,30,0,0));


    }


    @FXML
    public void onChatButton(ActionEvent actionEvent){

        FxmLoader object = new FxmLoader();
        Pane view = object.getPane("ChatView");
        mainPane.setCenter(view);
        mainPane.setPrefWidth(1100);
        mainPane.setPadding(new Insets(0,30,0,0));


    }

    @FXML
    public void onListRegisterButton(ActionEvent actionEvent){

        FxmLoader object = new FxmLoader();
        Pane view = object.getPane("listMyRegistrations");
        mainPane.setCenter(view);
        mainPane.setPrefWidth(1100);
        mainPane.setPadding(new Insets(0,30,0,0));


    }

    @FXML
    public void onButtonSmartMode(ActionEvent actionEvent) throws IOException {
        if(!(isClicked)){
            Runtime.getRuntime().exec(new String[]{"wscript.exe", "C:/Users/hp/Desktop/MongoDB/omar/test.vbs"});
            System.out.println("I am start opening thanks [+]");
            smartModeButton.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
            isClicked = true;
            {

                System.out.println("I am already open dude ! ");
                smartModeButton.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        File file = new File("C:/orders/orders.txt");
                        BufferedReader br = null;
                        try {
                            br = new BufferedReader(new FileReader(file));

                            String st;
                            while ((st = br.readLine()) != null) {
                                //System.out.println(st);
                                if(st.equals("i want to go to chat")){
                                    Platform.runLater(()->{
                                        FxmLoader object = new FxmLoader();
                                        Pane view = object.getPane("ChatView");
                                        mainPane.setCenter(view);
                                        mainPane.setPrefWidth(1100);
                                        mainPane.setPadding(new Insets(0,30,0,0));
                                    });
                                }
                                else if(st.equals("i want to go to add")){
                                    Platform.runLater(()->{
                                        FxmLoader object = new FxmLoader();
                                        Pane view = object.getPane("ajouterFormation");
                                        mainPane.setCenter(view);
                                        mainPane.setPrefWidth(1100);
                                        mainPane.setPadding(new Insets(0,30,0,0));
                                    });
                                }
                                else if(st.equals("i want to go to modify")){
                                    Platform.runLater(()->{
                                        FxmLoader object = new FxmLoader();
                                        Pane view = object.getPane("listMesFormation");
                                        mainPane.setCenter(view);
                                        mainPane.setPrefWidth(1100);
                                        mainPane.setPadding(new Insets(0,30,0,0));
                                    });
                                }

                            }

                            br.close();

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, 0, 2000);
            }


        }
        else{
            if(isClicked){
                System.out.println("End Smart mode");
                Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", "C:/Users/hp/Desktop/MongoDB/omar/stopSmartMode.vbs"});
              //  System.out.println(p.pid());
                isClicked = false;
            }
        }
    }
}

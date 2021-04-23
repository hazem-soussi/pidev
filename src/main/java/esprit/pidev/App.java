package esprit.pidev;

import esprit.pidev.services.FormationServices;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private double x,y;

    @Override
    public void start(Stage stage) throws IOException {


        //starts the remote control

      /*  System.out.println("Start microphone");
        Runtime.getRuntime().exec(new String[]{"wscript.exe", "C:/Users/hp/Desktop/MongoDB/omar/test.vbs"});
        System.out.println("***End of microphone not blocking ***");*/



      /*  String script = "C:\\work\\selenium\\chrome\\test.vbs";
          // search for real path:
        String executable = "C:\\windows\\...\\vbs.exe";
        String cmdArr [] = {executable, script};
        Runtime.getRuntime ().exec (cmdArr);
*/



        Parent root = FXMLLoader.load(getClass().getResource("primary.fxml"));
        scene = new Scene(root,1200,600);
        stage.setScene(scene);
        stage.setResizable(false);

        stage.show();
    }

    /*static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args)
    {
        launch();
        //Connection con = BaseDao.connection();
    }*/

}
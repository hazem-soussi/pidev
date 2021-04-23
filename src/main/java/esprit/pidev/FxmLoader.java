package esprit.pidev;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.net.URL;

public class FxmLoader {

   private Pane view;


   public Pane getPane(String fileName){
       try{
          URL fileUrl = App.class.getResource(fileName + ".fxml");
          if(fileUrl == null){
             throw new java.io.FileNotFoundException("nor found");
          }

          view = new FXMLLoader().load(fileUrl);
       }

       catch (Exception e){
          // System.out.println("i'm inside fxmloader");
         e.printStackTrace();
       }

       return view;
   }



}

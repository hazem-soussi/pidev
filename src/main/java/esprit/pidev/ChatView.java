package esprit.pidev;


import esprit.pidev.services.FormationServices;
import esprit.pidev.services.FormationServicesImp;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ChatView implements Initializable {



    private MessageProducer messageProducer;
    private Session session;
    private String codeUser;
    private Connection connection;
    private Object lock = new Object();
    private TextField textFieldCode,textFieldHost,textFieldPort,textFieldTo;
    private String pathFileConversation = null ;



    @FXML
    public ComboBox listStudents;
    @FXML
    public Label currentUserName;
    @FXML
    public ListView<String> listView;
    @FXML
    public Button connectButton;
    @FXML
    public Button deconnectButton;
    @FXML
    public TextArea textAreaMessage;

    @FXML
    public Button sendButton;

    @FXML
    public ImageView remoteControl;

     //List of all my clients
    List<String> studentsNames = new ArrayList<>();

    // set All names in the combo
    ObservableList<String> observableList = FXCollections.observableList(studentsNames);


    //messgaes obserbales
    ObservableList<String> observableListMessages = FXCollections.observableArrayList();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        System.out.println("i'am setting the students names  + ");
        FormationServices formationServices = new FormationServicesImp();

        currentUserName.setText(formationServices.getCurrentUserName(27));

        studentsNames = formationServices.getMyAllClients(27);
        for(String user :studentsNames){
            listStudents.getItems().add(user);
        }
        listStudents.getSelectionModel().selectedItemProperty().addListener(((observable, value1,value2) -> {

            System.out.println(listStudents.getSelectionModel().getSelectedItem().toString());

        }));
    }

    @FXML
    public void onConnectButton(ActionEvent actionEvent) {
        System.out.println("Button connect clicked ! ");
        deconnectButton.setDisable(false);
        connectButton.setDisable(true);

        //listView = new ListView<>(observableListMessages);
        listView.setItems(observableListMessages);

        System.out.println("********Start test connect button *********");

            try {
                if ((checkFileChat("C:/RegisterChatFiles/"+getFileName()))||(checkFileChat("C:/RegisterChatFiles/"+getFileName2()))) {
                    System.out.println("File found " + getPathFileConversation());
                    if (getLastLine(getPathFileConversation()).equals("#")) {
                        deleteLastLine(getPathFileConversation());
                    }
                    Platform.runLater(() -> {
                        // deleteLastLine("C:/RegisterChatFiles/file.txt");
                        File file = new File(getPathFileConversation());
                        BufferedReader br = null;
                        try {
                            br = new BufferedReader(new FileReader(file));

                            String st;
                            while ((st = br.readLine()) != null) {
                                observableListMessages.add(st);
                            }

                            br.close();

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    });
                }



                else{
                    if(!(checkFileChat("C:/RegisterChatFiles/"+getFileName()))){
                        File createNewFile = new File("C:/RegisterChatFiles/"+getFileName());
                        createNewFile.createNewFile();
                        setPathFileConversation(createNewFile.getPath());
                        System.out.println("This inside the click connect button event  .....");
                        System.out.println("File Created successfully ");
                    }
                }


                /*codeUser = textFieldCode.getText();
                String host = textFieldHost.getText();
                int port = Integer.parseInt(textFieldPort.getText());*/

                ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(

                        "tcp://localhost:61616"

                );

                //hBox.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                connection = connectionFactory.createConnection();
                connection.start();

                session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                System.out.println(connection.getClientID());
                Destination destination = session.createQueue("enset.chat");


                MessageConsumer messageConsumer = session.createConsumer(destination, "code='" + currentUserName.getText() + "'");
                //MessageConsumer messageConsumer = session.createConsumer(destination);
                messageProducer = session.createProducer(destination);
                messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

                messageConsumer.setMessageListener(message -> {
                    try {
                        if (message instanceof TextMessage) {

                            TextMessage textMessage = (TextMessage) message;

                            Platform.runLater(() -> {
                                try {

                                    observableListMessages.add(textMessage.getText());


                                } catch (JMSException ex) {
                                    ex.printStackTrace();
                                }

                            });

                        } else if (message instanceof StreamMessage) {

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });


            } catch (Exception e) {
                e.printStackTrace();
            }

        textAreaMessage.setOnKeyPressed(event -> {
            System.out.println("Enter pressed ! ");
            if (event.getCode() == KeyCode.ENTER) {

                try {

                    TextMessage textMessage = session.createTextMessage();

                    textMessage.setText(currentUserName.getText() + ":" + textAreaMessage.getText());
                    textMessage.setStringProperty("code", listStudents.getSelectionModel().getSelectedItem().toString());

                    Platform.runLater(() -> {
                        try {
                            observableListMessages.add(textMessage.getText());

                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    });


                    messageProducer.send(textMessage);
                    textAreaMessage.setText("");


                } catch (JMSException e) {
                    e.printStackTrace();
                }


            }

        });
        sendButton.setOnAction(e -> {

            System.out.println("Send Button clicked  ! ");

            try {

                TextMessage textMessage = session.createTextMessage();
                textMessage.setText(currentUserName.getText() + " : " + textAreaMessage.getText());

                Platform.runLater(() -> {
                    try {


                        observableListMessages.add(textMessage.getText());
                    } catch (JMSException er) {
                        er.printStackTrace();
                    }
                });

                textMessage.setStringProperty("code",listStudents.getSelectionModel().getSelectedItem().toString());
                messageProducer.send(textMessage);
                textAreaMessage.setText("");

            } catch (JMSException err) {
                err.printStackTrace();
            }
        });

        System.out.println("********End connect button*******");

    }

    @FXML
    public void onDeconnectButton(ActionEvent actionEvent) {
        System.out.println("Button deconnect clicked !");
        connectButton.setDisable(false);
        deconnectButton.setDisable(true);
            //hBox.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
            try {

                Thread copyConversation = new Thread(()->{
                    if(!(getLastLine(getPathFileConversation()).equals("#"))){
                        System.out.println("this is the value of file of conversation inside thread " + getPathFileConversation());
                        try {
                            clearTheFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        observableListMessages.stream().forEach((element) -> {

                            try {
                                writeTofile(getPathFileConversation(), element,true);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            // System.out.println("elemnt to add is" + element);

                        });
                        setAtTheLastLine(getPathFileConversation());
                        synchronized (lock){
                            System.out.println("register the conversation successfully ....");
                            System.out.println("I send a notification to thread  ! ");
                            lock.notify();
                        }
                    }
                    else{
                        synchronized (lock){
                            System.out.println("I send a notification to thread  ! ");
                            System.out.println("The conversation is already saved successfully");
                            lock.notify();
                        }
                    }

                });


                Thread exitThread = new Thread(() -> {

                    try {
                        synchronized (lock){
                            lock.wait();
                            System.out.println("I'm notified ");
                            System.out.println("I'll close the connection Now");
                            session.close();
                            connection.close();

                        }

                    } catch (JMSException | InterruptedException e) {
                        e.printStackTrace();
                    }

                });

                copyConversation.start();
                exitThread.start();

            }
            catch (Exception e){
                e.printStackTrace();
            }


    }


    /****Starts Functions of saving convertations******/

    public void setPathFileConversation(String path){
        pathFileConversation = path;
    }
    public String getPathFileConversation(){
        return pathFileConversation;
    }
    public  boolean checkFileChat(String filePath){
        File file = new File(filePath);
        String secondChoicePathFile  = filePath.substring(22);
        String secondPossiblePath = file.getPath().replace(file.getPath().substring(21),getFileName2());


        File file1 = new File(secondPossiblePath);

        if(file.isFile()) {
            System.out.println("This inside check file function .....");
            System.out.println("file exist already with name " + file.getPath());
            setPathFileConversation(file.getPath());
            return true;
            //return true;
        }
        else if(file1.isFile()){
            System.out.println("This inside check file function .....");
            System.out.println("File exist already with name " + file1.getPath());
            setPathFileConversation(file1.getPath());
            return true;
        }
        else{
            System.out.println("This inside check file function .....");
            System.out.println("file not found  - ");

        }

        return false;
    }
    public void writeTofile(String filePath,String text, boolean register) throws IOException {

        /*Thread thread = new Thread(()->{*/

        BufferedWriter f_writer = null;
        try {
            f_writer = new BufferedWriter(new FileWriter(filePath,register));
            f_writer.newLine();
            f_writer.write(text);
            f_writer.flush();

            // System.out.println("a new item added successfully Done[+] ");
            f_writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public  void setAtTheLastLine(String filePath){
        BufferedWriter f_writer = null;
        try {
            f_writer = new BufferedWriter(new FileWriter(filePath,true));

            f_writer.flush();
            f_writer.write("#");
            f_writer.flush();

            // System.out.println("a new item added successfully Done[+] ");
            f_writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getLastLine(String filePath){
        List<String> list = new ArrayList<String>();

        if(!fileIsEmpty()){
            try {
                BufferedReader br = new BufferedReader(new FileReader(filePath));
                list = br.lines().collect(Collectors.toList());
                return list.get(((list.size())-1));
            }
            catch (Exception ex){
                ex.printStackTrace();
            }

        }
        return "";
    }
    public  boolean  fileIsEmpty(){
        File file = new File(getPathFileConversation());
        if (file.length() == 0)
            return true;
        else {
            System.out.println("File is not empty!!!");
            return false;
        }

    }
    public  void deleteLastLine(String pathFile){

        List<String> list = new ArrayList<String>();

        if(!fileIsEmpty()){
            try {
                BufferedReader br = new BufferedReader(new FileReader(pathFile));

                list = br.lines().collect(Collectors.toList());
                System.out.println("I' ll remove the last line ");
                System.out.println(list.get(((list.size())-1)));
                list.remove(((list.size())-1));
                clearTheFile();
                list.stream().forEach(item->{
                    try {
                        writeTofile(pathFile,item,true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                System.out.println(list.get(((list.size())-1)));
                br.close();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }

        }
    }
    public  void clearTheFile() throws IOException {
        FileWriter fwOb = new FileWriter(getPathFileConversation(), false);
        PrintWriter pwOb = new PrintWriter(fwOb, false);
        pwOb.flush();
        pwOb.close();
        fwOb.close();
    }
    public String getFileName(){
        return currentUserName.getText() +listStudents.getSelectionModel().getSelectedItem()+".txt";
    }
    public  String getFileName2(){
        return listStudents.getSelectionModel().getSelectedItem() + currentUserName.getText()+".txt";
    }




    /**Sart remote control ***/

    @FXML
    public void remoteControlButton(MouseEvent mouseEvent) {

        System.out.println("Start Remote control");
       try{

           ProcessBuilder pb = new ProcessBuilder("C:/Program Files/AdoptOpenJDK/jdk-8.0.232.09-hotspot/bin/java", "-jar", "jrdesktop.jar");
           pb.directory(new File("C:/Users/hp/IdeaProjects"));
           Process p = pb.start();


       }
       catch(Exception e){
           e.printStackTrace();
       }

    }


    /***End remote control ****/


}

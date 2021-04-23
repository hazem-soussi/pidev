package esprit.pidev.dao;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class BaseDao {

    /*Connection to dataBase*/




        public final static Connection connection() {

            String url,password,userName;
            url = "jdbc:mysql://localhost:3306/pidev?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            userName = "root";
            password = "";


            try {

                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, userName, password);
                System.out.println("Connection starts successfully ! ");
                return con;


            }
            catch(SQLException | ClassNotFoundException e) {
                System.out.println(e);
                return null;
            }



        }


    }







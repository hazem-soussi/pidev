package esprit.pidev.services;

import esprit.pidev.dao.BaseDao;
import esprit.pidev.entity.Formation;
import esprit.pidev.entity.User;
import org.apache.camel.reifier.loadbalancer.StickyLoadBalancerReifier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FormationServicesImp implements FormationServices {

    Connection con = BaseDao.connection();


    @Override
    public List<Formation> getAllFormations() {


        try {
            List<Formation> formations  = new ArrayList<Formation>();
            String sql = "select id,nom_formation,categ_formation,desc_formation,duree_formation,prix_formation from formation";
            PreparedStatement st = con.prepareStatement(sql);

            ResultSet rs = st.executeQuery(sql);

            while(rs.next()) {
                Formation formation = new Formation();
                formation.setId(rs.getInt("id"));
                formation.setNomFormation(rs.getString("nom_formation"));
                formation.setCategorieFormation(rs.getString("categorie_formation"));
                formation.setDescFormation(rs.getString("desc_formation"));
                formation.setDureeFormation(rs.getString("duree_formation"));
                formation.setPrixFormation(rs.getInt("prix_formation"));

                formations.add(formation);

            }

            return formations;
        } catch (SQLException e) {

            System.out.println("Faild to get list of Formations ! ");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void addFormation(Formation formation,int idOwner) {
        try{

            //sql req to insert formation
            String sql ="insert into formation (nom_formation,categ_formation,desc_formation,duree_formation,prix_formation) values (?,?,?,?,?)";

            // prepareStatment for formation req
            PreparedStatement st = con.prepareStatement(sql);

            // setting cours values
            st.setString(1,formation.getNomFormation());
            st.setString(2,formation.getCategorieFormation());
            st.setString(3,formation.getDescFormation());
            st.setString(4,formation.getDureeFormation());
            st.setInt(5,formation.getPrixFormation());
            //execute Formation req
            st.executeUpdate();

            //sql req to insert in table offer Formation
            String sql_postuler_formation = "insert into postuler_formation  values (?,?)";
            PreparedStatement st_psotuler_formation  = con.prepareStatement(sql_postuler_formation );
            //setting offer Formation infos
            st_psotuler_formation .setInt(1,idOwner);
            st_psotuler_formation .setInt(2,getNextAutoIncrementFormationId()-1);

            System.out.println("offer Formation table will contain   : " + idOwner + "   " + getNextAutoIncrementFormationId());


            //execute offerFormation
            st_psotuler_formation .executeUpdate();

        }

        catch(Exception err) {

            System.out.println("ADD Formation FAILS [-]");
            err.printStackTrace();

        }
    }

    @Override
    public List<Formation> getAllFormationsByOwner(int idOwner) {

        try {
            List<Formation> formations  = new ArrayList<Formation>();
            String sql = "select * from formation where id in (select formation_id from postuler_formation where user_id  ="+idOwner+")";
            PreparedStatement st = con.prepareStatement(sql);
            //st.setArray(1,rs);

            //st.setInt(1,23);

            ResultSet rs = st.executeQuery(sql);

            while(rs.next()) {
                Formation formation = new Formation();
                formation.setId(rs.getInt("id"));
                formation.setNomFormation(rs.getString("nom_formation"));
                formation.setCategorieFormation(rs.getString("categorie_formation"));
                formation.setDescFormation(rs.getString("desc_formation"));
                formation.setPrixFormation(rs.getInt("prix_formation"));
                formation.setDureeFormation(rs.getString("duree_formation"));




                formations.add(formation);
                System.out.println("------------------");
                System.out.println("this is the first format : " + formation);
                System.out.println("------------------");

            }

            return formations;
        } catch (SQLException e) {

            System.out.println("Faild to get list of formations ! ");
            e.printStackTrace();
        }

   return null;

    }

    @Override
    public int getNextAutoIncrementFormationId() {


        try{
            PreparedStatement clean = con.prepareStatement("SET @@SESSION.information_schema_stats_expiry = 0;");
            clean.executeUpdate();
            String sql_get_next_autoIncrement_id ="SELECT `AUTO_INCREMENT`FROM  INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'pidev' AND   TABLE_NAME   = 'formation'"; //db name
            PreparedStatement st_get_next_id  =  con.prepareStatement(sql_get_next_autoIncrement_id);
            ResultSet res = st_get_next_id.executeQuery(sql_get_next_autoIncrement_id);

            if(res.next()){
                System.out.println("next id found : " + res.getInt("AUTO_INCREMENT"));
                int nextFormationId = res.getInt("AUTO_INCREMENT");
                return nextFormationId;
            }


        }
        catch(SQLException ex){
            System.out.println("NEXT ID NOT FOUND [-]");
            ex.printStackTrace();
        }
        return -1;
    }

    @Override
    public void deleteFormation(Formation formation) {

        String sql = "delete from formation where id = ?";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, formation.getId());
            st.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    @Override
    public void update(Formation formation) {

        String sql = "update formation set nom_formation = ?,categ_formation = ?,desc_formation = ? , duree_formation = ? where id=?";
        try {

            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, formation.getNomFormation());
            st.setString(2, formation.getCategorieFormation());
            st.setString(3, formation.getDescFormation());
            st.setString(4, formation.getDureeFormation());
//            st.setInt(5, cours.getPrixCours());

            st.setInt(5, formation.getId());

            st.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }


    }

    @Override
    public List<String> getMyAllClients( int idStudent) {
        try {
            List<String> users  = new ArrayList<String>();
            String sql = "select nom from user where id in (select user_id from inscription_formation where formation_id in (select formation_id from postuler_formation where user_id = "+idStudent+"))";
            PreparedStatement st = con.prepareStatement(sql);

            ResultSet rs = st.executeQuery(sql);

            while(rs.next()) {
                User user = new User();
                user.setNom(rs.getString("nom"));

                System.out.println("the client name  is " + user);

                users.add(user.getNom());

            }

            return users;
        } catch (SQLException e) {

            System.out.println("Faild to get list of formation ! ");
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public String getCurrentUserName(int idUser) {

        try {
            String name = "";
            String sql = "select nom from user where id = "+idUser;
            PreparedStatement st = con.prepareStatement(sql);

            ResultSet rs = st.executeQuery(sql);

            while(rs.next()) {
                User user = new User();
                user.setNom(rs.getString("nom"));
              //  System.out.println("the client name  is " + user);
                name = user.getNom();

            }

            return name;
        } catch (SQLException e) {

            System.out.println("Faild to get Current user name ");
            e.printStackTrace();
        }

        return null;


    }

    @Override
    public void registerToFormation(int user_id, int formation_id) {
       try{

           String sql = "insert into inscription_formation (user_id,formation_id) values(?,?)";
           PreparedStatement st = con.prepareStatement(sql);
           st.setInt(1,user_id);
           st.setInt(2,formation_id);
           st.executeUpdate();
       }
       catch(Exception ex){
           ex.printStackTrace();
       }

    }

    @Override
    public List<Formation> getMyRegistrations(int idUser) {

        List<Formation> formations  = new ArrayList<Formation>();

        try{

            String sql = "select id,nom_formation,categ_formation,desc_formation,duree_formation,prix_formation from formation where id in (select formation_id from inscription_formation where user_id  = "+idUser +");";
            PreparedStatement st = con.prepareStatement(sql);

            ResultSet rs = st.executeQuery(sql);

            while(rs.next()) {
                Formation formation = new Formation();
                formation.setId(rs.getInt("id"));
                formation.setNomFormation(rs.getString("nom_formation"));
                formation.setCategorieFormation(rs.getString("categorie_formation"));
                formation.setDescFormation(rs.getString("desc_formation"));
                formation.setDureeFormation(rs.getString("duree_formation"));
                formation.setPrixFormation(rs.getInt("prix_formation"));

                formations.add(formation);

            }
            return formations;
        }

        catch(Exception exception){
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Formation> getFormation(int userId) {
        try {
            List<Formation> formations  = new ArrayList<Formation>();

            String sql = "select id,nom_formation,categ_formation,desc_formation,duree_formation,prix_formation from formation where id not in (select formation_id from inscription_formation where user_id = "+ userId +") and id not in (select formation_id from postuler_formation where user_id = "+userId+")";
            PreparedStatement st = con.prepareStatement(sql);

            ResultSet rs = st.executeQuery(sql);

            while(rs.next()) {
                Formation formation = new Formation();
                formation.setId(rs.getInt("id"));
                formation.setNomFormation(rs.getString("nom_formation"));
                formation.setCategorieFormation(rs.getString("categorie_formation"));
                formation.setDescFormation(rs.getString("desc_formation"));
                formation.setDureeFormation(rs.getString("duree_formation"));
                formation.setPrixFormation(rs.getInt("prix_formation"));

                formations.add(formation);

            }

            return formations;
        } catch (SQLException e) {

            System.out.println("Faild to get list of formation ! ");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void deleteMyRegestration(int userId,int formation_id) {

        String sql = "delete from inscription_formation where user_id = ? and  formation_id = ?";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, userId);
            st.setInt(2,formation_id);
            st.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }

    }
}

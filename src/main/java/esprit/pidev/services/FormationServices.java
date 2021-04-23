package esprit.pidev.services;

import esprit.pidev.entity.Formation;

import java.util.List;

public interface FormationServices {

    List<Formation> getAllFormations();
    public void addFormation(Formation formation,int idOwner);
    List<Formation> getAllFormationsByOwner(int IdOwner);
    int getNextAutoIncrementFormationId();
    void deleteFormation(Formation formation);
    void update(Formation formation);
    List<String> getMyAllClients(int idStudent);
    String getCurrentUserName(int idUser);
    void registerToFormation(int user_id,int formation_id);
    List<Formation> getMyRegistrations(int idUser);
    List<Formation> getFormation(int userId);
    void deleteMyRegestration(int userId,int formation_id);
}

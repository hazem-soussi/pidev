package esprit.pidev.entity;

public class User {

            private int id;
            private String email,nom;

            public User(){

            }

    public User(int id, String email, String nom) {
        this.id = id;
        this.email = email;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}

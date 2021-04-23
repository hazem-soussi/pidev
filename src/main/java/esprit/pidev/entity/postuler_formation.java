package esprit.pidev.entity;

public class postuler_formation {

    private int user_id,formation_id;


    public postuler_formation() {
    }

    public postuler_formation(int user_id, int formation_id) {
        this.user_id = user_id;
        this.formation_id = formation_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getFormation_id() {
        return formation_id;
    }

    public void setFormation_id(int formation_id) {
        this.formation_id = formation_id;
    }


    @Override
    public String toString() {
        return "postuler_formation{" +
                "user_id=" + user_id +
                ", formation_id=" + formation_id +
                '}';
    }
}

package esprit.pidev.entity;

public class Formation {

    String nomFormation,categorieFormation,descFormation,dureeFormation;
    int prixFormation;
    int id;

    public Formation() {
    }

    public Formation(String nomFormation, String categorieFormation, String descFormation, int prixFormation,String duree_Formation) {
        this.nomFormation = nomFormation;
        this.categorieFormation = categorieFormation;
        this.descFormation = descFormation;
        this.prixFormation = prixFormation;
        this.dureeFormation = duree_Formation;
    }

    public String getNomFormation() {
        return nomFormation;
    }

    public void setNomFormation(String nomFormation) {
        this.nomFormation = nomFormation;
    }

    public String getCategorieFormation() {
        return categorieFormation;
    }

    public void setCategorieFormation(String categorieFormation) {
        this.categorieFormation = categorieFormation;
    }

    public String getDescFormation() {
        return descFormation;
    }

    public void setDescFormation(String descFormation) {
        this.descFormation = descFormation;
    }

    public int getPrixFormation() {
        return prixFormation;
    }

    public void setPrixFormation(int prixFormation) {
        this.prixFormation = prixFormation;
    }


    public String getDureeFormation() {
        return dureeFormation;
    }

    public void setDureeFormation(String dureeFormation) {
        this.dureeFormation = dureeFormation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Formation{" +
                "id='" + id + '\'' +
                "nomFormation='" + nomFormation + '\'' +
                ", categorieFormation='" + categorieFormation + '\'' +
                ", descFormation='" + descFormation + '\'' +
                ", prixFormation='" + prixFormation + '\'' +
                ", dureeFormation='" + dureeFormation + '\'' +
                '}';
    }

    public void setPriceFormation1(String price){
         this.prixFormation = Integer.parseInt(price);
    }
}

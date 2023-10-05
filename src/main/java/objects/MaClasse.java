package objects;

public class MaClasse {
    private String maChaine;
    private float monFloat;

    public MaClasse(String maChaine, float monFloat) {
        this.maChaine = maChaine;
        this.monFloat = monFloat;
    }

    public String getMaChaine() {
        return maChaine;
    }

    public void setMaChaine(String maChaine) {
        this.maChaine = maChaine;
    }

    public float getMonFloat() {
        return monFloat;
    }

    public void setMonFloat(float monFloat) {
        this.monFloat = monFloat;
    }

    @Override
    public String toString() {
        return "MaClasse{" +
                "maChaine='" + maChaine + '\'' +
                ", monFloat=" + monFloat +
                '}';
    }
}

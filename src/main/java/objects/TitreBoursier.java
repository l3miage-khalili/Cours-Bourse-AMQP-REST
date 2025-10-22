package objects;


public class TitreBoursier {
    private String code;
    private String nom;
    private double prixActuel;
    private int quantite;
    private String secteur;

    // Constructeur
    public TitreBoursier(String code, String nom, double prixActuel, int quantite, String secteur) {
        this.code = code;
        this.nom = nom;
        this.prixActuel = prixActuel;
        this.quantite = quantite;
        this.secteur = secteur;
    }

    // Getters
    public String getCode() {
        return code;
    }

    public String getNom() {
        return nom;
    }

    public double getPrixActuel() {
        return prixActuel;
    }

    public int getQuantite() {
        return quantite;
    }

    public String getSecteur() {
        return secteur;
    }

    // Setters
    public void setPrixActuel(double prixActuel) {
        this.prixActuel = prixActuel;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    // Méthodes utiles
    public double getValeurPortefeuille() {
        return prixActuel * quantite;
    }

    @Override
    public String toString() {
        return "TitreBoursier{" +
                "code='" + code + '\'' +
                ", nom='" + nom + '\'' +
                ", prixActuel=" + prixActuel +
                ", quantite=" + quantite +
                ", secteur='" + secteur + '\'' +
                '}';
    }
}

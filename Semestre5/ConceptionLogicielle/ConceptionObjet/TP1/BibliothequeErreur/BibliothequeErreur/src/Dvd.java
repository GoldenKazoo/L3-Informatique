class DVD extends Document {
    private int duree; // en minutes
    private String genre;

    public DVD(String titre, String auteur, int annee, int duree, String genre) {
        super(titre, auteur, annee);
        this.duree = duree;
        this.genre = genre;
    }

    @Override
    public void afficherDetails() {
        System.out.println("DVD: \"" + getTitre() + "\" de " + getAuteur() + " (" + getAnnee() + "), " + duree + " minutes, Genre: " + genre);
    }

    @Override
    public String getType() {
        return "DVD";
    }

    // private String getTitre() {
    //     return titre;
    // }

    // private String getAuteur() {
    //     return auteur;
    // }

    // private int getAnnee() {
    //     return annee;
    // }
}

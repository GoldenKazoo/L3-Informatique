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
        super.afficherDetails();
        System.out.println("durée: " + duree + " minutes, Genre: " + genre);
    }

    @Override
    public String getType() {
        return "DVD";
    }

    
}

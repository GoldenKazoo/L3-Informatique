import java.util.ArrayList;
import java.util.List;

class Bibliotheque {
    private List<Document> documents;

    public Bibliotheque() {
        documents = new ArrayList<>();
    }

    public void ajouterDocument(Document doc) {
        documents.add(doc);
    }

    public void gererPret(Document doc, String action) {
        switch (action.toLowerCase()) {
            case "emprunter":
                doc.emprunter();
                break;
            case "retourner":
                doc.retourner();
                break;
            case "reserver":
                doc.reserver();
                break;
            default:
                System.out.println("Action non reconnue.");
        }
    }

    public Document rechercherParTitre(String titre) {
        for (Document doc : documents) {
            if (doc.getTitre().equalsIgnoreCase(titre)) {
                return doc;
            }
        }
        return null;
    }

    public List<Document> rechercherParAuteur(String auteur) {
        List<Document> resultat = new ArrayList<>();
        for (Document doc : documents) {
            if (doc.getAuteur().equalsIgnoreCase(auteur)) {
                resultat.add(doc);
            }
        }
        return resultat;
    }
}

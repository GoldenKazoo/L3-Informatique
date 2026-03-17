public class GraphTest {

    static int passed = 0;
    static int failed = 0;

    public static void main(String[] args) {
        Graph g = new Graph();
        g.ajoutSommet();
        g.ajoutSommet();
        g.ajoutSommet();
        g.toString();
        // testAjoutUnSommet();
        // testAjoutPlusieursSommets();
        // testAjoutAreteValide();
        // testAjoutAreteInvalide_iHorsLimite();
        // testAjoutAreteInvalide_jHorsLimite();
        // testAjoutAreteGrapheVide();
        // testToStringFormatDot();
        // testGrapheVideToString();

        System.out.println("\n=== Résultats : " + passed + " passés, " + failed + " échoués ===");
    }

    static void assertTrue(String nom, boolean condition) {
        if (condition) {
            System.out.println("✅ " + nom);
            passed++;
        } else {
            System.out.println("❌ " + nom);
            failed++;
        }
    }

    static void assertThrowsIllegal(String nom, Runnable r) {
        try {
            r.run();
            System.out.println("❌ " + nom + " (aucune exception levée)");
            failed++;
        } catch (IllegalArgumentException e) {
            System.out.println("✅ " + nom);
            passed++;
        }
    }

    static void testAjoutUnSommet() {
        Graph g = new Graph();
        g.ajoutSommet();
        assertTrue("Ajout d'un sommet", g.toString().contains("1"));
    }

    static void testAjoutPlusieursSommets() {
        Graph g = new Graph();
        g.ajoutSommet();
        g.ajoutSommet();
        g.ajoutSommet();
        String dot = g.toString();
        assertTrue("Plusieurs sommets (1, 2, 3)",
            dot.contains("1") && dot.contains("2") && dot.contains("3"));
    }

    static void testAjoutAreteValide() {
        Graph g = new Graph();
        g.ajoutSommet();
        g.ajoutSommet();
        try {
            g.ajoutArete(0, 1);
            System.out.println("✅ Arête valide (0 -> 1)");
            passed++;
        } catch (Exception e) {
            System.out.println("❌ Arête valide (0 -> 1) : exception inattendue");
            failed++;
        }
    }

    static void testAjoutAreteInvalide_iHorsLimite() {
        Graph g = new Graph();
        g.ajoutSommet();
        assertThrowsIllegal("Arête invalide (i hors limite)", () -> g.ajoutArete(5, 0));
    }

    static void testAjoutAreteInvalide_jHorsLimite() {
        Graph g = new Graph();
        g.ajoutSommet();
        assertThrowsIllegal("Arête invalide (j hors limite)", () -> g.ajoutArete(0, 5));
    }

    static void testAjoutAreteGrapheVide() {
        Graph g = new Graph();
        assertThrowsIllegal("Arête sur graphe vide", () -> g.ajoutArete(0, 0));
    }

    static void testToStringFormatDot() {
        Graph g = new Graph();
        g.ajoutSommet();
        assertTrue("Format DOT commence par 'digraph{'", g.toString().startsWith("digraph{"));
    }

    static void testGrapheVideToString() {
        Graph g = new Graph();
        assertTrue("Graphe vide format DOT", g.toString().startsWith("digraph{"));
    }
}

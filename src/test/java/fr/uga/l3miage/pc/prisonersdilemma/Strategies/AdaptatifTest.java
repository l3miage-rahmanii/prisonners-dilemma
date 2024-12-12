package fr.uga.l3miage.pc.prisonersdilemma.Strategies;





public class AdaptatifTest {
    /*

    private Adaptatif adaptatif;
    private String[] historique;

    @BeforeEach
    void setUp() {
        historique = new String[20]; // Taille arbitraire pour les tests
        adaptatif = nesw Adaptatif(historique);
    }

    @Test
    void testProchainCoupInitialSequence() {
        // Vérifie que les premiers coups suivent la séquence initiale
        String expectedSequence = "cccccccttttt";
        for (int i = 0; i < expectedSequence.length(); i++) {
            assertEquals(String.valueOf(expectedSequence.charAt(i)), adaptatif.prochainCoup());
            adaptatif.miseAJourDernierCoupAdversaire("c", 1); // Mettre à jour avec des valeurs arbitraires
        }
    }

    @Test
    void testProchainCoupApresSequenceInitialeFavoriseCooperation() {
        // Simule une situation où coopérer rapporte plus de points
        String initialSequence = "cccccccttttt";
        for (int i = 0; i < initialSequence.length(); i++) {
            adaptatif.miseAJourDernierCoupAdversaire("c", 1);
        }

        // Coopération a un score plus élevé
        adaptatif.miseAJourDernierCoupAdversaire("c", 10); // Score élevé pour coopération
        adaptatif.miseAJourDernierCoupAdversaire("t", 1);  // Score bas pour trahison

        assertEquals("c", adaptatif.prochainCoup());
    }

    @Test
    void testProchainCoupApresSequenceInitialeFavoriseTrahison() {
        // Simule une situation où trahir rapporte plus de points
        String initialSequence = "cccccccttttt";
        for (int i = 0; i < initialSequence.length(); i++) {
            adaptatif.miseAJourDernierCoupAdversaire("t", 1);
        }

        // Trahison a un score plus élevé
        adaptatif.miseAJourDernierCoupAdversaire("t", 10); // Score élevé pour trahison
        adaptatif.miseAJourDernierCoupAdversaire("c", 1);  // Score bas pour coopération

        assertEquals("t", adaptatif.prochainCoup());
    }

    @Test
    void testMiseAJourDernierCoupAdversaire() {
        adaptatif.miseAJourDernierCoupAdversaire("c", 5);
        adaptatif.miseAJourDernierCoupAdversaire("t", 10);

        assertEquals("c", historique[0]); // Vérifie que le premier coup enregistré est correct
        assertEquals("t", historique[1]); // Vérifie que le deuxième coup enregistré est correct
    }

     */
}
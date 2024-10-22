import java.util.ArrayList;
import java.util.List;

public class Jeu {
    private int[][] cases;
    private int objectif;
    private List<Observateur> observateurs;
    private int nbGagnees;
    private int nbJouees;
    private int taille;

    public Jeu() {
        observateurs = new ArrayList<>();
        setTaille(4); // Taille par défaut du plateau
        nouveau();
    }

    public void setTaille(int taille) {
        this.taille = taille;
        cases = new int[taille][taille];
    }

    public int getObjectif() {
        return objectif;
    }

    public void setObjectif(int nouvelObjectif) {
        this.objectif = nouvelObjectif;
    }

    public void nouveau() {
        cases = new int[taille][taille];
        ajouterNouvelleTuile();
        ajouterNouvelleTuile();
        nbJouees++;
        notifierObservateurs();
    }

    public void ajouterObservateur(Observateur obs) {
        observateurs.add(obs);
    }

    public void notifierObservateurs() {
        for (Observateur obs : observateurs) {
            obs.reagir();
        }
    }

    public void jouer(String direction) {
        switch (direction) {
            case "haut":
                deplacerHaut();
                break;
            case "bas":
                deplacerBas();
                break;
            case "gauche":
                deplacerGauche();
                break;
            case "droite":
                deplacerDroite();
                break;
        }
        ajouterNouvelleTuile();
        notifierObservateurs();
    }

    public int size() {
        return taille;
    }

    public int getCase(int l, int c) {
        return cases[l][c];
    }

    public int getNbGagnees() {
        return nbGagnees;
    }

    public int getNbJouees() {
        return nbJouees;
    }

    // Mouvement vers le haut
    private void deplacerHaut() {
        for (int col = 0; col < taille; col++) {
            int[] colonne = new int[taille];
            int k = 0;
            for (int row = 0; row < taille; row++) {
                if (cases[row][col] != 0) {
                    colonne[k++] = cases[row][col];
                }
            }
            fusionner(colonne);
            for (int row = 0; row < taille; row++) {
                cases[row][col] = colonne[row];
            }
        }
    }

    // Mouvement vers le bas
    private void deplacerBas() {
        for (int col = 0; col < cases[0].length; col++) {
            int[] colonne = new int[cases.length];
            for (int lig = 0; lig < cases.length; lig++) {
                colonne[lig] = cases[lig][col];
            }
            fusionnerColonneVersBas(colonne);
            decalerColonneVersBas(colonne);
            for (int lig = 0; lig < cases.length; lig++) {
                cases[lig][col] = colonne[lig];
            }
        }
    }

    // Mouvement vers la gauche
    private void deplacerGauche() {
        for (int row = 0; row < taille; row++) {
            int[] ligne = new int[taille];
            int k = 0;
            for (int col = 0; col < taille; col++) {
                if (cases[row][col] != 0) {
                    ligne[k++] = cases[row][col];
                }
            }
            fusionner(ligne);
            for (int col = 0; col < taille; col++) {
                cases[row][col] = ligne[col];
            }
        }
    }

    // Mouvement vers la droite
    private void deplacerDroite() {
        for (int lig = 0; lig < cases.length; lig++) {
            int[] ligne = cases[lig];
            fusionnerLigneVersDroite(ligne);
            decalerLigneVersDroite(ligne);
            for (int col = 0; col < ligne.length; col++) {
                cases[lig][col] = ligne[col];
            }
        }
    }

    // Fusionne les tuiles de même valeur dans une ligne ou une colonne
    private void fusionner(int[] ligne) {
        for (int i = 0; i < taille - 1; i++) {
            if (ligne[i] != 0 && ligne[i] == ligne[i + 1]) {
                ligne[i] *= 2;
                ligne[i + 1] = 0;
                nbGagnees += (ligne[i] == objectif) ? 1 : 0; // Si l'objectif est atteint
            }
        }

        // Compacte la ligne après fusion
        int[] compactee = new int[taille];
        int k = 0;
        for (int i = 0; i < taille; i++) {
            if (ligne[i] != 0) {
                compactee[k++] = ligne[i];
            }
        }

        System.arraycopy(compactee, 0, ligne, 0, taille);
    }

    private void fusionnerColonneVersBas(int[] colonne) {
        for (int i = colonne.length - 1; i > 0; i--) {
            if (colonne[i] != 0 && colonne[i] == colonne[i-1]) {
                colonne[i] *= 2;
                colonne[i-1] = 0;
            }
        }
    }

    private void decalerColonneVersBas(int[] colonne) {
        int[] nouvelleColonne = new int[colonne.length];
        int index = colonne.length - 1;
        for (int i = colonne.length - 1; i >= 0; i--) {
            if (colonne[i] != 0) {
                nouvelleColonne[index--] = colonne[i];
            }
        }
        System.arraycopy(nouvelleColonne, 0, colonne, 0, colonne.length);
    }

    private void fusionnerLigneVersDroite(int[] ligne) {
        for (int i = ligne.length - 1; i > 0; i--) {
            if (ligne[i] != 0 && ligne[i] == ligne[i-1]) {
                ligne[i] *= 2;
                ligne[i-1] = 0;
            }
        }
    }

    private void decalerLigneVersDroite(int[] ligne) {
        int[] nouvelleLigne = new int[ligne.length];
        int index = ligne.length - 1;
        for (int i = ligne.length - 1; i >= 0; i--) {
            if (ligne[i] != 0) {
                nouvelleLigne[index--] = ligne[i];
            }
        }
        System.arraycopy(nouvelleLigne, 0, ligne, 0, ligne.length);
    }

    // Ajoute une nouvelle tuile (2 ou 4) à un emplacement vide
    private void ajouterNouvelleTuile() {
        List<int[]> positionsVides = new ArrayList<>();
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if (cases[i][j] == 0) {
                    positionsVides.add(new int[]{i, j});
                }
            }
        }

        if (!positionsVides.isEmpty()) {
            int[] position = positionsVides.get((int) (Math.random() * positionsVides.size()));
            cases[position[0]][position[1]] = Math.random() < 0.9 ? 2 : 4; // 90% de chance de générer un 2, 10% un 4
        }
    }
}
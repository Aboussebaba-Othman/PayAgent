package model;

import java.util.ArrayList;
import java.util.List;

public class Agent extends Personne {
    private TypeAgent typeAgent;
    private Departement departement;
    private List<Paiment> paiments = new ArrayList<>();

    public Agent(String id, String nom, String prenom, String email, String motDePasse,
                 TypeAgent typeAgent, Departement departement) {
        super(id, nom, prenom, email, motDePasse);
        this.typeAgent = typeAgent;
        this.departement = departement;
    }
    public boolean peutRecevoirBonus() {
        return typeAgent == TypeAgent.RESPONSABLE_DEPARTEMENT ||
                typeAgent == TypeAgent.DIRECTEUR;
    }
    public boolean peutRecevoirIndemnite() {
        return typeAgent == TypeAgent.RESPONSABLE_DEPARTEMENT ||
                typeAgent == TypeAgent.DIRECTEUR;
    }

    public boolean ajouterPaiment(Paiment paiment) {
        // Vérification du montant
        if (paiment.getMontant() <= 0) {
            System.out.println("Le montant doit être positif!");
            return false;
        }

        // Vérification pour BONUS
        if (paiment.getType() == TypePaiment.BONUS && !peutRecevoirBonus()) {
            System.out.println("Cet agent ne peut pas recevoir bonus!");
            return false;
        }

        // Vérification pour INDEMNITE
        if (paiment.getType() == TypePaiment.INDEMNITE && !peutRecevoirIndemnite()) {
            System.out.println("Cet agent ne peut pas recevoir indemnité!");
            return false;
        }

        // Si tout va bien, ajouter le paiement
        paiments.add(paiment);
        System.out.println("Paiement ajouté avec succès!");
        return true;
    }
    public double calculerTotalPaiments() {
        double total = 0;
        for (Paiment p : paiments) {
            total += p.getMontant();
        }
        return total;
    }

    public int compterPaiments(TypePaiment type) {
        int count = 0;
        for (Paiment p : paiments) {
            if (p.getType() == type) {
                count++;
            }
        }
        return count;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public List<Paiment> getPaiments() {
        return paiments;
    }

    public Departement getDepartement() {
        return departement;
    }

    public TypeAgent getTypeAgent() { return typeAgent; }
    public void setTypeAgent(TypeAgent typeAgent) { this.typeAgent = typeAgent; }
}

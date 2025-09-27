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

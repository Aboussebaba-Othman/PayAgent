package model;

import java.util.ArrayList;
import java.util.List;

public class Departement {
    private String idDepartement;
    private String nom;
    private Agent responsable;
    private List<Agent> agents = new ArrayList<>();

    public Departement(String idDepartement, String nom) {
        this.idDepartement = idDepartement;
        this.nom = nom;
    }

    public void ajouterAgent(Agent agent) {
        agents.add(agent);
        agent.setDepartement(this);
    }

    public void supprimerAgent(Agent agent) {
        agents.remove(agent);
        agent.setDepartement(null);
    }

    public void affecterResponsable(Agent agent) {
        this.responsable = agent;
    }

    public String getIdDepartement() { return idDepartement; }
    public void setIdDepartement(String idDepartement) { this.idDepartement = idDepartement; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public Agent getResponsable() { return responsable; }
    public void setResponsable(Agent responsable) { this.responsable = responsable; }

    public List<Agent> getAgents() { return agents; }
}


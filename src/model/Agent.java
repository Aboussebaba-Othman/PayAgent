package model;


import java.util.ArrayList;
import java.util.List;


public class Agent extends Personne {

    private TypeAgent typeAgent;
    private Departement departement;
    private List<Paiment> paiments = new ArrayList<>();

    public Agent(String id, String nom, String prenom, String email, String motDePasse, TypeAgent typeAgent, Departement departement, List<Paiment> paiments) {
        super(id, nom, prenom, email, motDePasse);
        this.typeAgent = typeAgent;
    }
    public void setDepartement(Departement departement){
        this.departement = departement;
    }
    public void addPaiment(Paiment paiment){
        paiments.add(paiment);
    }
    public List<Paiment> getPaiments(){
        return paiments;
    }
    public Departement getDepartement(){
        return departement;
    }
}

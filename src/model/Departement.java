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
}

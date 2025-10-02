package controller;

import model.Agent;
import model.Departement;
import service.interfaces.IDepartementService;

import java.util.List;
import java.util.Optional;

public class DepartementController {
    private final IDepartementService departementService;
    public DepartementController(IDepartementService departementService){
        this.departementService = departementService;
    }

    public Departement creerDepartement(String nom){
        return departementService.creerDepartement(nom);
    }

    public Departement modifierDepartement(String departementId, String nouveauNom){
        return departementService.modifierDepartement(departementId,nouveauNom);
    }

    public boolean supprimerDepartement(String departementId){
        return departementService.supprimerDepartement(departementId);
    }

    public Optional<Departement> trouverDepartement(String departementId){
        return departementService.trouverDepartement(departementId);
    }

    public List<Departement> listerTousLesDepartements() {
        return departementService.listerTousLesDepartements();
    }

    public boolean ajouterAgentAuDepartement(String departementId, String agentId) {
        return departementService.ajouterAgentAuDepartement(departementId, agentId);
    }

    public boolean retirerAgentDuDepartement(String departementId, String agentId) {
        return departementService.retirerAgentDuDepartement(departementId, agentId);
    }

    public boolean affecterResponsable(String departementId, String agentId) {
        return departementService.affecterResponsable(departementId, agentId);
    }

    public Optional<Departement> rechercherParNom(String nom) {
        return departementService.rechercherParNom(nom);
    }

    public List<Departement> listerDepartementsAvecResponsable() {
        return departementService.listerDepartementsAvecResponsable();
    }

    public List<Departement> listerDepartementsSansResponsable() {
        return departementService.listerDepartementsSansResponsable();
    }

}

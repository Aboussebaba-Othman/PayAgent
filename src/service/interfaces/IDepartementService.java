package service.interfaces;

import model.Departement;
import model.Agent;
import java.util.List;
import java.util.Optional;

public interface IDepartementService {
    // Gestion de base
    Departement creerDepartement(String nom);
    Departement modifierDepartement(String departementId, String nouveauNom);
    boolean supprimerDepartement(String departementId);
    Optional<Departement> trouverDepartement(String departementId);
    List<Departement> listerTousLesDepartements();
    // Gestion des agents
    boolean ajouterAgentAuDepartement(String departementId, String agentId);
    boolean retirerAgentDuDepartement(String departementId, String agentId);
    boolean affecterResponsable(String departementId, String agentId);
    // Recherche
    Optional<Departement> rechercherParNom(String nom);
    List<Departement> listerDepartementsAvecResponsable();
    List<Departement> listerDepartementsSansResponsable();
}
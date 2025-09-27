package controller;

import model.Agent;
import model.Paiment;
import model.TypeAgent;
import model.TypePaiment;
import service.interfaces.IAgentService;

import java.util.List;
import java.util.Optional;

public class AgentController {

    private final IAgentService agentService;

    public AgentController(IAgentService agentService) {
        this.agentService = agentService;
    }

    public Agent creerAgent(String nom, String prenom, String email, String motDePasse,
                            TypeAgent typeAgent, String departementId) {
        return agentService.creerAgent(nom, prenom, email, motDePasse, typeAgent, departementId);
    }

    public Agent modifierAgent(String agentId, String nom, String prenom, String email) {
        return agentService.modifierAgent(agentId, nom, prenom, email);
    }

    public boolean supprimerAgent(String agentId) {
        return agentService.supprimerAgent(agentId);
    }

    public Optional<Agent> trouverAgent(String agentId) {
        return agentService.trouverAgent(agentId);
    }

    public List<Agent> listerTousLesAgents() {
        return agentService.listerTousLesAgents();
    }

    // ====== PAIEMENTS ======

    public boolean ajouterPaiement(String agentId, TypePaiment type, double montant,
                                   String motif, boolean conditionValidee) {
        return agentService.ajouterPaiement(agentId, type, montant, motif, conditionValidee);
    }

    public List<Paiment> consulterHistoriquePaiements(String agentId) {
        return agentService.consulterHistoriquePaiements(agentId);
    }

    public List<Paiment> filtrerPaiementsParType(String agentId, TypePaiment type) {
        return agentService.filtrerPaiementsParType(agentId, type);
    }

    public double calculerTotalPaiements(String agentId) {
        return agentService.calculerTotalPaiements(agentId);
    }

    // ====== FILTRAGE ET RECHERCHE ======

    public List<Agent> rechercherAgentsParNom(String nom) {
        return agentService.rechercherAgentsParNom(nom);
    }

    public List<Agent> listerAgentsParType(TypeAgent type) {
        return agentService.listerAgentsParType(type);
    }

    public List<Agent> listerAgentsParDepartement(String departementId) {
        return agentService.listerAgentsParDepartement(departementId);
    }
}
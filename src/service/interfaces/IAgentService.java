package service.interfaces;

import model.Agent;
import model.Paiment;
import model.TypeAgent;
import model.TypePaiment;

import java.util.List;
import java.util.Optional;


public interface IAgentService {


    void creerAgent(String nom, String prenom, String email, String motDePasse, TypeAgent typeAgent, String departementId);
    void modifierAgent(String agentId, String nom, String prenom, String email);
    boolean supprimerAgent(String agentId);
    Optional<Agent> trouverAgent(String agentId);
    List<Agent> listerTousLesAgents();
    // ===== RECHERCHE ET FILTRAGE =====
    List<Agent> rechercherAgentsParNom(String nom);
    List<Agent> listerAgentsParType(TypeAgent type);
    List<Agent> listerAgentsParDepartement(String departementId);
}
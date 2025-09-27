package service.interfaces;

import model.Agent;
import model.Paiment;
import model.TypeAgent;
import model.TypePaiment;

import java.util.List;
import java.util.Optional;


public interface IAgentService {


    Agent creerAgent(String nom, String prenom, String email, String motDePasse,
                     TypeAgent typeAgent, String departementId);


    Agent modifierAgent(String agentId, String nom, String prenom, String email);


    boolean supprimerAgent(String agentId);


    Optional<Agent> trouverAgent(String agentId);


    List<Agent> listerTousLesAgents();

    // ===== GESTION DES PAIEMENTS =====


    boolean ajouterPaiement(String agentId, TypePaiment type, double montant,
                            String motif, boolean conditionValidee);


    List<Paiment> consulterHistoriquePaiements(String agentId);


    List<Paiment> filtrerPaiementsParType(String agentId, TypePaiment type);

    double calculerTotalPaiements(String agentId);

    // ===== RECHERCHE ET FILTRAGE =====

    List<Agent> rechercherAgentsParNom(String nom);


    List<Agent> listerAgentsParType(TypeAgent type);


    List<Agent> listerAgentsParDepartement(String departementId);
}
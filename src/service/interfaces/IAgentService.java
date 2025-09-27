package service.interfaces;

import model.Agent;
import model.Paiment;
import model.TypeAgent;
import model.TypePaiment;

import java.util.List;
import java.util.Optional;

/**
 * Service pour gérer les Agents
 * Interface = liste des choses qu'on peut faire avec un Agent
 */
public interface IAgentService {

    // ===== GESTION DE BASE =====

    /**
     * Créer un nouvel agent
     */
    Agent creerAgent(String nom, String prenom, String email, String motDePasse,
                     TypeAgent typeAgent, String departementId);

    /**
     * Modifier un agent existant
     */
    Agent modifierAgent(String agentId, String nom, String prenom, String email);

    /**
     * Supprimer un agent
     */
    boolean supprimerAgent(String agentId);

    /**
     * Trouver un agent par ID
     */
    Optional<Agent> trouverAgent(String agentId);

    /**
     * Lister tous les agents
     */
    List<Agent> listerTousLesAgents();

    // ===== GESTION DES PAIEMENTS =====

    /**
     * Ajouter un paiement à un agent
     */
    boolean ajouterPaiement(String agentId, TypePaiment type, double montant,
                            String motif, boolean conditionValidee);

    /**
     * Consulter l'historique des paiements d'un agent
     */
    List<Paiment> consulterHistoriquePaiements(String agentId);

    /**
     * Filtrer les paiements par type
     */
    List<Paiment> filtrerPaiementsParType(String agentId, TypePaiment type);

    /**
     * Calculer le total des paiements d'un agent
     */
    double calculerTotalPaiements(String agentId);

    // ===== RECHERCHE ET FILTRAGE =====

    /**
     * Chercher des agents par nom
     */
    List<Agent> rechercherAgentsParNom(String nom);

    /**
     * Lister les agents par type
     */
    List<Agent> listerAgentsParType(TypeAgent type);

    /**
     * Lister les agents d'un département
     */
    List<Agent> listerAgentsParDepartement(String departementId);
}
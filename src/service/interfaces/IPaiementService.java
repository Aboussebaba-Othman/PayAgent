package service.interfaces;

import model.Agent;
import model.Paiment;
import model.TypePaiment;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IPaiementService {

    // ===== CRUD =====
    Paiment creerPaiement(String agentId, TypePaiment type, double montant, String motif, boolean conditionValidee);
    Optional<Paiment> trouverPaiement(String idPaiement);
    boolean supprimerPaiement(String idPaiement);
    List<Paiment> listerTousLesPaiements();
    Paiment modifierPaiement(String idPaiement, TypePaiment type, double montant, String motif, boolean conditionValidee);
    // ===== RECHERCHE =====
    List<Paiment> consulterPaiementsParAgent(String agentId);
    List<Paiment> filtrerPaiementsParType(TypePaiment type);
    List<Paiment> filtrerPaiementsParDate(LocalDate date);
    List<Paiment> filtrerPaiementsEntreDates(LocalDate debut, LocalDate fin);
    List<Paiment> filtrerPaiementsParMontantSuperieur(double montant);
    // ===== STATISTIQUES =====
    double calculerTotalPaiementsDepartement(String departementId);
    double calculerSalaireMoyenDepartement(String departementId);
    List<Agent> classementAgentsParPaiements(String departementId);
    double calculerTotalPaiementsAgent(String agentId);
    long compterPaiementsParType(TypePaiment type);
    Optional<Paiment> paiementMaxParAgent(String agentId);
    Optional<Paiment> paiementMinParAgent(String agentId);

}

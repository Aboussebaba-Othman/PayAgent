package controller;

import model.Agent;
import model.Paiment;
import model.TypePaiment;
import service.interfaces.IPaiementService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PaiementController {

    private final IPaiementService paiementService;

    public PaiementController(IPaiementService paiementService) {
        this.paiementService = paiementService;
    }

    public Paiment creerPaiement(String agentId, TypePaiment type, double montant, String motif, boolean conditionValidee) {
        return paiementService.creerPaiement(agentId, type, montant, motif, conditionValidee);
    }

    public Optional<Paiment> trouverPaiement(String idPaiement) {
        return paiementService.trouverPaiement(idPaiement);
    }

    public boolean supprimerPaiement(String idPaiement) {
        return paiementService.supprimerPaiement(idPaiement);
    }

    public List<Paiment> listerTousLesPaiements() {
        return paiementService.listerTousLesPaiements();
    }

    public Paiment modifierPaiement(String idPaiement, TypePaiment type, double montant, String motif, boolean conditionValidee) {
        return paiementService.modifierPaiement(idPaiement, type, montant, motif, conditionValidee);
    }

    public List<Paiment> consulterPaiementsParAgent(String agentId) {
        return paiementService.consulterPaiementsParAgent(agentId);
    }

    public List<Paiment> filtrerPaiementsParType(TypePaiment type) {
        return paiementService.filtrerPaiementsParType(type);
    }

    public List<Paiment> filtrerPaiementsParDate(LocalDate date) {
        return paiementService.filtrerPaiementsParDate(date);
    }

    public List<Paiment> filtrerPaiementsEntreDates(LocalDate debut, LocalDate fin) {
        return paiementService.filtrerPaiementsEntreDates(debut, fin);
    }

    public List<Paiment> filtrerPaiementsParMontantSuperieur(double montant) {
        return paiementService.filtrerPaiementsParMontantSuperieur(montant);
    }

    public double calculerTotalPaiementsDepartement(String departementId) {
        return paiementService.calculerTotalPaiementsDepartement(departementId);
    }

    public double calculerSalaireMoyenDepartement(String departementId) {
        return paiementService.calculerSalaireMoyenDepartement(departementId);
    }

    public List<Agent> classementAgentsParPaiements(String departementId) {
        return paiementService.classementAgentsParPaiements(departementId);
    }

    public double calculerTotalPaiementsAgent(String agentId) {
        return paiementService.calculerTotalPaiementsAgent(agentId);
    }

    public long compterPaiementsParType(TypePaiment type) {
        return paiementService.compterPaiementsParType(type);
    }

    public Optional<Paiment> paiementMaxParAgent(String agentId) {
        return paiementService.paiementMaxParAgent(agentId);
    }

    public Optional<Paiment> paiementMinParAgent(String agentId) {
        return paiementService.paiementMinParAgent(agentId);
    }
}

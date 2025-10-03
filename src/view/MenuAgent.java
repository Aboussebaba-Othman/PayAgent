package view;

import controller.AuthController;
import controller.PaiementController;
import model.Agent;
import model.Paiment;
import model.TypePaiment;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class MenuAgent {

    private final AuthController authController;
    private final PaiementController paiementController;
    private final Scanner scanner;

    public MenuAgent(AuthController authController, PaiementController paiementController, Scanner scanner) {
        this.authController = authController;
        this.paiementController = paiementController;
        this.scanner = scanner;
    }

    public void afficher() {
        int choix;
        do {
            Agent agent = authController.obtenirAgentConnecte().get();
            System.out.println("\n=== MENU AGENT ===");
            System.out.println("Connecte: " + agent.getNomComplet() + " (" + agent.getTypeAgent() + ")");
            System.out.println("1. Consulter mes informations");
            System.out.println("3. Afficher mes paiements");
            System.out.println("4. Filtrer mes paiements par type");
            System.out.println("5. Filtrer mes paiements par date");
            System.out.println("6. Trier mes paiements par montant");
            System.out.println("7. Calculer le total de mes paiements");
            System.out.println("8. Paiement maximum");
            System.out.println("9. Paiement minimum");
            System.out.println("0. Deconnexion");
            System.out.print("Choix: ");
            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> consulterMesInformations();
                case 3 -> afficherPaiements();
                case 4 -> filtrerPaiementsParType();
                case 5 -> filtrerPaiementsParDate();
                case 6 -> trierPaiementsParMontant();
                case 7 -> calculerTotalPaiements();
                case 8 -> paiementMax();
                case 9 -> paiementMin();
                case 0 -> authController.seDeconnecter();
                default -> System.out.println("Choix invalide");
            }
        } while (choix != 0);
    }

    private void consulterMesInformations() {
        Agent agent = authController.obtenirAgentConnecte().get();
        System.out.println("\n=== MES INFORMATIONS ===");
        System.out.println("ID: " + agent.getId());
        System.out.println("Nom complet: " + agent.getNomComplet());
        System.out.println("Email: " + agent.getEmail());
        System.out.println("Type: " + agent.getTypeAgent());
        if (agent.getDepartement() != null) {
            System.out.println("Departement: " + agent.getDepartement().getNom());
        } else {
            System.out.println("Departement: Non assigne");
        }
    }

    private void afficherPaiements() {
        Agent agent = authController.obtenirAgentConnecte().get();
        List<Paiment> paiements = paiementController.consulterPaiementsParAgent(agent.getId());
        if (paiements.isEmpty()) {
            System.out.println("Aucun paiement");
            return;
        }
        paiements.forEach(p ->
                System.out.println(p.getDate() + " | " + p.getType() + " | " +
                        p.getMontant() + " DH | " + p.getMotif())
        );
    }

    private void filtrerPaiementsParType() {
        Agent agent = authController.obtenirAgentConnecte().get();
        System.out.print("Type (SALAIRE, PRIME, BONUS, INDEMNITE): ");
        String type = scanner.nextLine();
        try {
            TypePaiment tp = TypePaiment.valueOf(type.toUpperCase());
            List<Paiment> paiements = paiementController.consulterPaiementsParAgent(agent.getId())
                    .stream()
                    .filter(p -> p.getType() == tp)
                    .toList();
            paiements.forEach(p -> System.out.println(p.getDate() + " | " + p.getMontant()));
        } catch (Exception e) {
            System.out.println("Type invalide");
        }
    }

    private void filtrerPaiementsParDate() {
        Agent agent = authController.obtenirAgentConnecte().get();
        System.out.print("Date debut (yyyy-MM-dd): ");
        LocalDate debut = LocalDate.parse(scanner.nextLine());
        System.out.print("Date fin (yyyy-MM-dd): ");
        LocalDate fin = LocalDate.parse(scanner.nextLine());
        List<Paiment> paiements = paiementController.consulterPaiementsParAgent(agent.getId())
                .stream()
                .filter(p -> !p.getDate().isBefore(debut) && !p.getDate().isAfter(fin))
                .toList();
        paiements.forEach(p -> System.out.println(p.getDate() + " | " + p.getMontant()));
    }

    private void trierPaiementsParMontant() {
        Agent agent = authController.obtenirAgentConnecte().get();
        List<Paiment> paiements = paiementController.consulterPaiementsParAgent(agent.getId())
                .stream()
                .sorted(Comparator.comparingDouble(Paiment::getMontant))
                .toList();
        paiements.forEach(p -> System.out.println(p.getMontant() + " | " + p.getType()));
    }

    private void calculerTotalPaiements() {
        Agent agent = authController.obtenirAgentConnecte().get();
        double total = paiementController.calculerTotalPaiementsAgent(agent.getId());
        System.out.println("Total paiements: " + total + " DH");
    }

    private void paiementMax() {
        Agent agent = authController.obtenirAgentConnecte().get();
        paiementController.paiementMaxParAgent(agent.getId())
                .ifPresent(p -> System.out.println("Paiement max: " + p.getMontant() + " DH (" + p.getType() + ")"));
    }

    private void paiementMin() {
        Agent agent = authController.obtenirAgentConnecte().get();
        paiementController.paiementMinParAgent(agent.getId())
                .ifPresent(p -> System.out.println("Paiement min: " + p.getMontant() + " DH (" + p.getType() + ")"));
    }
}
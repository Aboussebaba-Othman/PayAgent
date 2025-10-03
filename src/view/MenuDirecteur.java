package view;

import controller.AgentController;
import controller.AuthController;
import controller.DepartementController;
import controller.PaiementController;
import model.Agent;
import model.TypeAgent;
import model.TypePaiment;

import java.util.List;
import java.util.Scanner;

public class MenuDirecteur {

    private final AuthController authController;
    private final AgentController agentController;
    private final DepartementController departementController;
    private final PaiementController paiementController;
    private final Scanner scanner;

    public MenuDirecteur(AuthController authController,
                         AgentController agentController,
                         DepartementController departementController,
                         PaiementController paiementController,
                         Scanner scanner) {
        this.authController = authController;
        this.agentController = agentController;
        this.departementController = departementController;
        this.paiementController = paiementController;
        this.scanner = scanner;
    }

    public void afficher() {
        int choix;
        do {
            Agent directeur = authController.obtenirAgentConnecte().get();
            System.out.println("\n=== MENU DIRECTEUR ===");
            System.out.println("Connecte: " + directeur.getNomComplet());
            System.out.println("1. Creer un agent");
            System.out.println("2. Modifier un agent");
            System.out.println("3. Supprimer un agent");
            System.out.println("4. Rechercher agents par nom");
            System.out.println("5. Lister agents par type");
            System.out.println("6. Lister tous les agents");
            System.out.println("7. Creer un departement");
            System.out.println("8. Modifier un departement");
            System.out.println("9. Supprimer un departement");
            System.out.println("10. Trouver un departement par ID");
            System.out.println("11. Lister tous les departements");
            System.out.println("12. Rechercher departement par nom");
            System.out.println("13. Lister departements avec responsable");
            System.out.println("14. Lister departements sans responsable");
            System.out.println("15. Affecter un responsable");
            System.out.println("16. Ajouter un paiement");
            System.out.println("17. Modifier un paiement");
            System.out.println("18. Supprimer un paiement");
            System.out.println("19. Lister tous les paiements");
            System.out.println("20. Consulter paiements d'un agent");
            System.out.println("21. Filtrer paiements par type");
            System.out.println("22. Filtrer paiements par montant");
            System.out.println("23. Filtrer paiements par date");
            System.out.println("24. Filtrer paiements entre deux dates");
            System.out.println("25. Statistiques d'un agent");
            System.out.println("26. Statistiques d'un departement");
            System.out.println("27. Classement agents d'un departement");
            System.out.println("0. Deconnexion");
            System.out.print("Choix: ");
            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> creerAgent();
                case 2 -> modifierAgent();
                case 3 -> supprimerAgent();
                case 4 -> rechercherAgentsParNom();
                case 5 -> listerAgentsParType();
                case 6 -> listerTousLesAgents();
                case 7 -> creerDepartement();
                case 8 -> modifierDepartement();
                case 9 -> supprimerDepartement();
                case 10 -> trouverDepartement();
                case 11 -> listerTousLesDepartements();
                case 12 -> rechercherDepartementParNom();
                case 13 -> listerDepartementsAvecResponsable();
                case 14 -> listerDepartementsSansResponsable();
                case 15 -> affecterResponsable();
                case 16 -> ajouterPaiement();
                case 17 -> modifierPaiement();
                case 18 -> supprimerPaiement();
                case 19 -> listerTousLesPaiements();
                case 20 -> consulterPaiementsAgent();
                case 21 -> filtrerPaiementsParType();
                case 22 -> filtrerPaiementsParMontant();
                case 23 -> filtrerPaiementsParDate();
                case 24 -> filtrerPaiementsEntreDates();
                case 25 -> statistiquesAgent();
                case 26 -> statistiquesDepartement();
                case 27 -> classementAgents();
                case 0 -> authController.seDeconnecter();
                default -> System.out.println("Choix invalide");
            }
        } while (choix != 0);
    }

    private void creerAgent() {
        try {
            System.out.print("Nom: ");
            String nom = scanner.nextLine();
            System.out.print("Prenom: ");
            String prenom = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Mot de passe: ");
            String motDePasse = scanner.nextLine();
            System.out.print("Type (OUVRIER, RESPONSABLE_DEPARTEMENT, DIRECTEUR, STAGIAIRE): ");
            String type = scanner.nextLine();
            System.out.print("ID Departement (laisser vide si aucun): ");
            String deptId = scanner.nextLine().trim();

            if (deptId.isEmpty()) deptId = null;

            agentController.creerAgent(
                    nom, prenom, email, motDePasse,
                    TypeAgent.valueOf(type.toUpperCase()),
                    deptId
            );
            System.out.println("Agent cree");
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    private void modifierAgent() {
        System.out.print("ID agent: ");
        String id = scanner.nextLine();
        System.out.print("Nouveau nom: ");
        String nom = scanner.nextLine();
        System.out.print("Nouveau prenom: ");
        String prenom = scanner.nextLine();
        System.out.print("Nouvel email: ");
        String email = scanner.nextLine();
        agentController.modifierAgent(id, nom, prenom, email);
    }

    private void supprimerAgent() {
        System.out.print("ID agent: ");
        String id = scanner.nextLine();
        agentController.supprimerAgent(id);
    }

    private void rechercherAgentsParNom() {
        System.out.print("Nom a rechercher: ");
        String nom = scanner.nextLine();
        agentController.rechercherAgentsParNom(nom)
                .forEach(a -> System.out.println(a.getId() + " | " + a.getNomComplet()));
    }

    private void listerAgentsParType() {
        System.out.print("Type (OUVRIER, STAGIAIRE, RESPONSABLE_DEPARTEMENT, DIRECTEUR): ");
        TypeAgent type = TypeAgent.valueOf(scanner.nextLine().toUpperCase());
        agentController.listerAgentsParType(type)
                .forEach(a -> System.out.println(a.getId() + " | " + a.getNomComplet()));
    }

    private void listerTousLesAgents() {
        agentController.listerTousLesAgents()
                .forEach(a -> System.out.println(a.getId() + " | " + a.getNomComplet()));
    }

    private void creerDepartement() {
        System.out.print("Nom departement: ");
        String nom = scanner.nextLine();
        departementController.creerDepartement(nom);
    }

    private void modifierDepartement() {
        System.out.print("ID departement: ");
        String id = scanner.nextLine();
        System.out.print("Nouveau nom: ");
        String nom = scanner.nextLine();
        departementController.modifierDepartement(id, nom);
    }

    private void supprimerDepartement() {
        System.out.print("ID departement: ");
        String id = scanner.nextLine();
        departementController.supprimerDepartement(id);
    }

    private void trouverDepartement() {
        System.out.print("ID departement: ");
        String id = scanner.nextLine();
        departementController.trouverDepartement(id)
                .ifPresentOrElse(
                        d -> System.out.println(d.getIdDepartement() + " | " + d.getNom()),
                        () -> System.out.println("Non trouve")
                );
    }

    private void listerTousLesDepartements() {
        departementController.listerTousLesDepartements()
                .forEach(d -> System.out.println(d.getIdDepartement() + " | " + d.getNom()));
    }

    private void rechercherDepartementParNom() {
        System.out.print("Nom departement: ");
        String nom = scanner.nextLine();
        departementController.rechercherParNom(nom)
                .ifPresentOrElse(
                        d -> System.out.println(d.getIdDepartement() + " | " + d.getNom()),
                        () -> System.out.println("Non trouve")
                );
    }

    private void listerDepartementsAvecResponsable() {
        departementController.listerDepartementsAvecResponsable()
                .forEach(d -> System.out.println(d.getIdDepartement() + " | " + d.getNom()));
    }

    private void listerDepartementsSansResponsable() {
        departementController.listerDepartementsSansResponsable()
                .forEach(d -> System.out.println(d.getIdDepartement() + " | " + d.getNom()));
    }

    private void affecterResponsable() {
        System.out.print("ID departement: ");
        String deptId = scanner.nextLine();
        System.out.print("ID agent: ");
        String agentId = scanner.nextLine();
        departementController.affecterResponsable(deptId, agentId);
    }

    private void ajouterPaiement() {
        System.out.print("ID agent: ");
        String agentId = scanner.nextLine();
        System.out.print("Type (SALAIRE, PRIME, BONUS, INDEMNITE): ");
        TypePaiment type = TypePaiment.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Montant: ");
        double montant = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Motif: ");
        String motif = scanner.nextLine();
        boolean conditionValidee = false;
        if (type == TypePaiment.BONUS || type == TypePaiment.INDEMNITE) {
            System.out.print("Condition validee (true/false): ");
            conditionValidee = scanner.nextBoolean();
            scanner.nextLine();
        }
        paiementController.creerPaiement(agentId, type, montant, motif, conditionValidee);
    }

    private void modifierPaiement() {
        System.out.print("ID paiement: ");
        String id = scanner.nextLine();
        System.out.print("Type (SALAIRE, PRIME, BONUS, INDEMNITE): ");
        TypePaiment type = TypePaiment.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Montant: ");
        double montant = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Motif: ");
        String motif = scanner.nextLine();
        System.out.print("Condition validee (true/false): ");
        boolean conditionValidee = scanner.nextBoolean();
        scanner.nextLine();
        paiementController.modifierPaiement(id, type, montant, motif, conditionValidee);
    }

    private void supprimerPaiement() {
        System.out.print("ID paiement: ");
        String id = scanner.nextLine();
        paiementController.supprimerPaiement(id);
    }

    private void listerTousLesPaiements() {
        paiementController.listerTousLesPaiements()
                .forEach(p -> System.out.println(p.getIdPaiement() + " | " + p.getMontant() + " | " + p.getType()));
    }

    private void consulterPaiementsAgent() {
        System.out.print("ID agent: ");
        String agentId = scanner.nextLine();
        paiementController.consulterPaiementsParAgent(agentId)
                .forEach(p -> System.out.println(p.getDate() + " | " + p.getType() + " | " + p.getMontant()));
    }

    private void filtrerPaiementsParType() {
        System.out.print("Type (SALAIRE, PRIME, BONUS, INDEMNITE): ");
        TypePaiment type = TypePaiment.valueOf(scanner.nextLine().toUpperCase());
        paiementController.filtrerPaiementsParType(type)
                .forEach(p -> System.out.println(p.getDate() + " | " + p.getMontant()));
    }

    private void filtrerPaiementsParMontant() {
        System.out.print("Montant minimum: ");
        double montant = scanner.nextDouble();
        scanner.nextLine();
        paiementController.filtrerPaiementsParMontantSuperieur(montant)
                .forEach(p -> System.out.println(p.getDate() + " | " + p.getMontant()));
    }

    private void filtrerPaiementsParDate() {
        System.out.print("Date (yyyy-mm-dd): ");
        String dateStr = scanner.nextLine();
        paiementController.filtrerPaiementsParDate(java.time.LocalDate.parse(dateStr))
                .forEach(p -> System.out.println(p.getDate() + " | " + p.getMontant()));
    }

    private void filtrerPaiementsEntreDates() {
        System.out.print("Date debut (yyyy-mm-dd): ");
        String debut = scanner.nextLine();
        System.out.print("Date fin (yyyy-mm-dd): ");
        String fin = scanner.nextLine();
        paiementController.filtrerPaiementsEntreDates(java.time.LocalDate.parse(debut), java.time.LocalDate.parse(fin))
                .forEach(p -> System.out.println(p.getDate() + " | " + p.getMontant()));
    }

    private void statistiquesAgent() {
        System.out.print("ID agent: ");
        String agentId = scanner.nextLine();
        System.out.println("Total paiements: " + paiementController.calculerTotalPaiementsAgent(agentId));
        paiementController.paiementMaxParAgent(agentId).ifPresent(p ->
                System.out.println("Paiement max: " + p.getMontant()));
        paiementController.paiementMinParAgent(agentId).ifPresent(p ->
                System.out.println("Paiement min: " + p.getMontant()));
    }

    private void statistiquesDepartement() {
        System.out.print("ID departement: ");
        String deptId = scanner.nextLine();
        System.out.println("Total paiements: " + paiementController.calculerTotalPaiementsDepartement(deptId));
        System.out.println("Salaire moyen: " + paiementController.calculerSalaireMoyenDepartement(deptId));
    }

    private void classementAgents() {
        System.out.print("ID departement: ");
        String deptId = scanner.nextLine();
        List<Agent> classement = paiementController.classementAgentsParPaiements(deptId);
        for (int i = 0; i < classement.size(); i++) {
            Agent a = classement.get(i);
            double total = paiementController.calculerTotalPaiementsAgent(a.getId());
            System.out.println((i + 1) + ". " + a.getNomComplet() + " - " + total + " DH");
        }
    }
}
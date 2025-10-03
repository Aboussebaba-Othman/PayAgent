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

public class MenuResponsable {

    private final AuthController authController;
    private final AgentController agentController;
    private final DepartementController departementController;
    private final PaiementController paiementController;
    private final Scanner scanner;

    public MenuResponsable(AuthController authController,
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
            Agent responsable = authController.obtenirAgentConnecte().get();
            System.out.println("\n=== MENU RESPONSABLE DEPARTEMENT ===");
            System.out.println("Connecte: " + responsable.getNomComplet());
            System.out.println("1. Ajouter un agent");
            System.out.println("2. Modifier un agent");
            System.out.println("3. Supprimer un agent");
            System.out.println("4. Rechercher agents par nom");
            System.out.println("5. Lister agents par type");
            System.out.println("6. Lister agents du departement");
            System.out.println("7. Affecter un agent au departement");
            System.out.println("8. Retirer un agent du departement");
            System.out.println("9. Ajouter un paiement");
            System.out.println("10. Consulter paiements d'un agent");
            System.out.println("11. Filtrer paiements par type");
            System.out.println("12. Filtrer paiements par montant minimum");
            System.out.println("13. Statistiques du departement");
            System.out.println("14. Classement agents par total paiements");
            System.out.println("15. Mes informations");
            System.out.println("16. Mes paiements");
            System.out.println("0. Deconnexion");
            System.out.print("Choix: ");
            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> ajouterAgent();
                case 2 -> modifierAgent();
                case 3 -> supprimerAgent();
                case 4 -> rechercherAgentsParNom();
                case 5 -> listerAgentsParType();
                case 6 -> listerAgentsDuDepartement();
                case 7 -> affecterAgent();
                case 8 -> retirerAgent();
                case 9 -> ajouterPaiement();
                case 10 -> consulterPaiementsAgent();
                case 11 -> filtrerPaiementsParType();
                case 12 -> filtrerPaiementsParMontant();
                case 13 -> statistiquesDepartement();
                case 14 -> classementAgents();
                case 15 -> mesInformations();
                case 16 -> mesPaiements();
                case 0 -> authController.seDeconnecter();
                default -> System.out.println("Choix invalide");
            }
        } while (choix != 0);
    }

    private void ajouterAgent() {
        try {
            Agent responsable = authController.obtenirAgentConnecte().get();
            if (responsable.getDepartement() == null) {
                System.out.println("Vous n'etes pas affecte a un departement");
                return;
            }

            System.out.print("Nom: ");
            String nom = scanner.nextLine();
            System.out.print("Prenom: ");
            String prenom = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Mot de passe: ");
            String motDePasse = scanner.nextLine();
            System.out.print("Type (OUVRIER, STAGIAIRE): ");
            String type = scanner.nextLine();

            agentController.creerAgent(
                    nom, prenom, email, motDePasse,
                    TypeAgent.valueOf(type.toUpperCase()),
                    responsable.getDepartement().getIdDepartement()
            );
            System.out.println("Agent cree et affecte au departement " +
                    responsable.getDepartement().getNom());
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
        System.out.print("Type (OUVRIER, STAGIAIRE, DIRECTEUR, RESPONSABLE_DEPARTEMENT): ");
        TypeAgent type = TypeAgent.valueOf(scanner.nextLine().toUpperCase());
        agentController.listerAgentsParType(type)
                .forEach(a -> System.out.println(a.getId() + " | " + a.getNomComplet()));
    }

    private void listerAgentsDuDepartement() {
        Agent responsable = authController.obtenirAgentConnecte().get();
        if (responsable.getDepartement() == null) {
            System.out.println("Vous n'etes pas affecte a un departement");
            return;
        }
        var agents = agentController.listerAgentsParDepartement(responsable.getDepartement().getIdDepartement());
        agents.forEach(a -> System.out.println(a.getId() + " | " + a.getNomComplet()));
    }

    private void affecterAgent() {
        Agent responsable = authController.obtenirAgentConnecte().get();
        if (responsable.getDepartement() == null) {
            System.out.println("Vous n'etes pas affecte a un departement");
            return;
        }
        System.out.print("ID agent: ");
        String id = scanner.nextLine();
        departementController.ajouterAgentAuDepartement(responsable.getDepartement().getIdDepartement(), id);
    }

    private void retirerAgent() {
        Agent responsable = authController.obtenirAgentConnecte().get();
        if (responsable.getDepartement() == null) {
            System.out.println("Vous n'etes pas affecte a un departement");
            return;
        }
        System.out.print("ID agent: ");
        String id = scanner.nextLine();
        departementController.retirerAgentDuDepartement(responsable.getDepartement().getIdDepartement(), id);
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

    private void statistiquesDepartement() {
        Agent responsable = authController.obtenirAgentConnecte().get();
        if (responsable.getDepartement() == null) {
            System.out.println("Vous n'etes pas affecte a un departement");
            return;
        }
        String deptId = responsable.getDepartement().getIdDepartement();
        System.out.println("Total paiements: " + paiementController.calculerTotalPaiementsDepartement(deptId));
        System.out.println("Salaire moyen: " + paiementController.calculerSalaireMoyenDepartement(deptId));
    }

    private void classementAgents() {
        Agent responsable = authController.obtenirAgentConnecte().get();
        if (responsable.getDepartement() == null) {
            System.out.println("Vous n'etes pas affecte a un departement");
            return;
        }
        String deptId = responsable.getDepartement().getIdDepartement();
        List<Agent> classement = paiementController.classementAgentsParPaiements(deptId);
        for (int i = 0; i < classement.size(); i++) {
            Agent a = classement.get(i);
            double total = paiementController.calculerTotalPaiementsAgent(a.getId());
            System.out.println((i + 1) + ". " + a.getNomComplet() + " - " + total + " DH");
        }
    }

    private void mesInformations() {
        Agent agent = authController.obtenirAgentConnecte().get();
        System.out.println("Nom: " + agent.getNomComplet());
        System.out.println("Email: " + agent.getEmail());
        System.out.println("Type: " + agent.getTypeAgent());
        if (agent.getDepartement() != null) {
            System.out.println("Departement: " + agent.getDepartement().getNom());
        } else {
            System.out.println("Departement: Non assigne");
        }
    }

    private void mesPaiements() {
        Agent agent = authController.obtenirAgentConnecte().get();
        paiementController.consulterPaiementsParAgent(agent.getId())
                .forEach(p -> System.out.println(p.getDate() + " | " + p.getType() + " | " + p.getMontant()));
    }
}
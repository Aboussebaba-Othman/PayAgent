package view;

import controller.AgentController;
import controller.DepartementController;
import controller.PaiementController;
import model.Agent;
import model.TypeAgent;
import repository.AgentRepositoryImpl;
import repository.DepartementRepository;
import repository.PaiementRepository;
import service.AgentServiceImpl;
import service.DepartementServiceImpl;
import service.PaiementServiceImpl;
import service.interfaces.IAgentService;
import service.interfaces.IDepartementService;
import service.interfaces.IPaiementService;

import java.util.List;
import java.util.Scanner;

public class Menu {

    private final AgentController agentController;
    private final DepartementController departementController;
    private final Scanner scanner = new Scanner(System.in);
    private final PaiementController paiementController;

    public Menu() {
        AgentRepositoryImpl agentRepository = new AgentRepositoryImpl();
        DepartementRepository departementRepository = new DepartementRepository();
        PaiementRepository paiementRepository = new PaiementRepository();

        IAgentService agentService = new AgentServiceImpl(agentRepository, departementRepository);
        IDepartementService departementService = new DepartementServiceImpl(departementRepository, agentRepository);
        IPaiementService paiementService = new PaiementServiceImpl(paiementRepository, agentRepository, departementRepository);

        this.agentController = new AgentController(agentService);
        this.departementController = new DepartementController(departementService);
        this.paiementController = new PaiementController(paiementService);
    }

    public void start() {
        int choix;
        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Gestion des agents");
            System.out.println("2. Gestion des départements");
            System.out.println("0. Quitter");
            System.out.print("Choix: ");
            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> menuAgents();
                case 2 -> menuDepartements();
                case 0 -> System.out.println("Fin du programme");
                default -> System.out.println("Choix invalide !");
            }
        } while (choix != 0);
    }

    // ===== MENU AGENTS =====
    private void menuAgents() {
        int choix;
        do {
            System.out.println("\n--- Gestion des agents ---");
            System.out.println("1. Créer un agent");
            System.out.println("2. Modifier un agent");
            System.out.println("3. Supprimer un agent");
            System.out.println("4. Trouver un agent par ID");
            System.out.println("5. Lister tous les agents");
            System.out.println("6. Lister les agents par departement");
            System.out.println("7. Lister Les Agents par Type");
            System.out.println("8. Rechercher Agents par Nom");
            System.out.println("0. Retour");
            System.out.print("Choix: ");
            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> creerAgent();
                case 2 -> modifierAgent();
                case 3 -> supprimerAgent();
                case 4 -> trouverAgent();
                case 5 -> listerAgents();
                case 6 -> listerAgentsParDepartement();
                case 7 -> listerAgentsParType();
                case 8 -> rechercherAgentsParNom();
                case 0 -> {}
                default -> System.out.println("Choix invalide !");
            }
        } while (choix != 0);
    }

    private void creerAgent() {
        try {
            System.out.print("Nom: ");
            String nom = scanner.nextLine();
            System.out.print("Prénom: ");
            String prenom = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Mot de passe: ");
            String motDePasse = scanner.nextLine();
            System.out.print("Type (OUVRIER, RESPONSABLE_DEPARTEMENT, DIRECTEUR, STAGIAIRE): ");
            String type = scanner.nextLine();

            agentController.creerAgent(nom, prenom, email, motDePasse,
                    TypeAgent.valueOf(type.toUpperCase()), null);
        } catch (Exception e) {
            System.out.println("Erreur lors de la création: " + e.getMessage());
        }
    }

    private void modifierAgent() {
        try {
            System.out.print("ID de l'agent à modifier: ");
            String id = scanner.nextLine();
            System.out.print("Nouveau nom: ");
            String nom = scanner.nextLine();
            System.out.print("Nouveau prénom: ");
            String prenom = scanner.nextLine();
            System.out.print("Nouvel email: ");
            String email = scanner.nextLine();

            agentController.modifierAgent(id, nom, prenom, email);
        } catch (Exception e) {
            System.out.println("Erreur lors de la modification: " + e.getMessage());
        }
    }

    private void supprimerAgent() {
        System.out.print("ID de l'agent à supprimer: ");
        String id = scanner.nextLine();
        boolean deleted = agentController.supprimerAgent(id);
        System.out.println(deleted ? "Agent supprimé" : "Agent introuvable");
    }

    private void trouverAgent() {
        System.out.print("ID de l'agent à chercher: ");
        String id = scanner.nextLine();
        agentController.trouverAgent(id).ifPresentOrElse(
                a -> System.out.println("Agent trouvé: " + a.getNomComplet() + " | " + a.getEmail()),
                () -> System.out.println("Aucun agent trouvé avec cet ID")
        );
    }

    private void listerAgents() {
        System.out.println("\n--- Liste des agents ---");
        List<Agent> agents = agentController.listerTousLesAgents();
        if (agents.isEmpty()) {
            System.out.println("Aucun agent trouvé.");
        } else {
            agents.forEach(agent ->
                    System.out.println(agent.getId() + " | " + agent.getNomComplet() + " | " + agent.getEmail())
            );
        }
    }

    private void listerAgentsParDepartement() {
        System.out.print("ID du département: ");
        String deptId = scanner.nextLine();

        var agents = agentController.listerAgentsParDepartement(deptId);
        if (agents.isEmpty()) {
            System.out.println("Aucun agent trouvé dans ce département.");
        } else {
            System.out.println("--- Agents du département ---");
            agents.forEach(a -> System.out.println(a.getId() + "  ||  " + a.getNomComplet()));
        }
    }

    private void listerAgentsParType() {
        System.out.print("Type d'agent (OUVRIER, RESPONSABLE_DEPARTEMENT, DIRECTEUR, STAGIAIRE): ");
        String type = scanner.nextLine();

        try {
            var agents = agentController.listerAgentsParType(TypeAgent.valueOf(type.toUpperCase()));
            if (agents.isEmpty()) {
                System.out.println("Aucun agent trouvé pour ce type.");
            } else {
                System.out.println("--- Agents du type " + type.toUpperCase() + " ---");
                agents.forEach(a -> System.out.println(a.getId() + " | " + a.getNomComplet()));
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Type invalide !");
        }
    }

    private void rechercherAgentsParNom() {
        System.out.print("Nom à rechercher: ");
        String nom = scanner.nextLine();

        var agents = agentController.rechercherAgentsParNom(nom);
        if (agents.isEmpty()) {
            System.out.println("Aucun agent trouvé avec ce nom.");
        } else {
            System.out.println("--- Résultats de recherche ---");
            agents.forEach(a -> System.out.println(a.getId() + "  ||  " + a.getNomComplet()));
        }
    }



    private void menuDepartements() {
        int choix;
        do {
            System.out.println("\n--- Gestion des départements ---");
            System.out.println("1. Créer un département");
            System.out.println("2. Modifier un département");
            System.out.println("3. Supprimer un département");
            System.out.println("4. Trouver un département par ID");
            System.out.println("5. Lister tous les départements");
            System.out.println("6. Affecter un responsable");
            System.out.println("7. Ajouter un agent à un département");
            System.out.println("8. Retirer un agent d’un département");
            System.out.println("0. Retour");
            System.out.print("Choix: ");
            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> creerDepartement();
                case 2 -> modifierDepartement();
                case 3 -> supprimerDepartement();
                case 4 -> trouverDepartement();
                case 5 -> listerDepartements();
                case 6 -> affecterResponsable();
                case 7 -> ajouterAgentAuDepartement();
                case 8 -> retirerAgentDuDepartement();
                case 0 -> {}
                default -> System.out.println("Choix invalide !");
            }
        } while (choix != 0);
    }

    private void creerDepartement() {
        System.out.print("Nom du département: ");
        String nom = scanner.nextLine();
        departementController.creerDepartement(nom);
    }

    private void modifierDepartement() {
        System.out.print("ID du département: ");
        String id = scanner.nextLine();
        System.out.print("Nouveau nom: ");
        String nom = scanner.nextLine();
        departementController.modifierDepartement(id, nom);
    }

    private void supprimerDepartement() {
        System.out.print("ID du département: ");
        String id = scanner.nextLine();
        departementController.supprimerDepartement(id);
    }

    private void trouverDepartement() {
        System.out.print("ID du département: ");
        String id = scanner.nextLine();
        departementController.trouverDepartement(id).ifPresentOrElse(
                d -> System.out.println("Département trouvé: " + d.getIdDepartement() + " | " + d.getNom()),
                () -> System.out.println("Aucun département trouvé avec cet ID")
        );
    }

    private void listerDepartements() {
        System.out.println("\n--- Liste des départements ---");
        var depts = departementController.listerTousLesDepartements();
        if (depts.isEmpty()) {
            System.out.println("Aucun département trouvé.");
        } else {
            depts.forEach(d -> System.out.println(d.getIdDepartement() + " | " + d.getNom()));
        }
    }

    private void affecterResponsable() {
        System.out.print("ID du département: ");
        String deptId = scanner.nextLine();
        System.out.print("ID de l’agent responsable: ");
        String agentId = scanner.nextLine();
        departementController.affecterResponsable(deptId, agentId);
    }

    private void ajouterAgentAuDepartement() {
        System.out.print("ID du département: ");
        String deptId = scanner.nextLine();
        System.out.print("ID de l’agent: ");
        String agentId = scanner.nextLine();
        departementController.ajouterAgentAuDepartement(deptId, agentId);
    }

    private void retirerAgentDuDepartement() {
        System.out.print("ID du département: ");
        String deptId = scanner.nextLine();
        System.out.print("ID de l’agent: ");
        String agentId = scanner.nextLine();
        departementController.retirerAgentDuDepartement(deptId, agentId);
    }
}

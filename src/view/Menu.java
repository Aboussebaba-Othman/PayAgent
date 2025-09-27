package view;

import controller.AgentController;
import model.Agent;
import model.TypeAgent;
import repository.AgentRepositoryImpl;
import service.AgentServiceImpl;
import service.interfaces.IAgentService;

import java.util.List;
import java.util.Scanner;

public class Menu {

    private final AgentController controller;
    private final Scanner scanner = new Scanner(System.in);

    public Menu() {
        // Fixed: proper dependency injection
        IAgentService service = new AgentServiceImpl(new AgentRepositoryImpl());
        this.controller = new AgentController(service);
    }

    public void start() {
        int choix;
        do {
            System.out.println("\n=== MENU GESTION AGENTS ===");
            System.out.println("1. Créer un agent");
            System.out.println("2. Modifier un agent");
            System.out.println("3. Supprimer un agent");
            System.out.println("4. Trouver un agent par ID");
            System.out.println("5. Lister tous les agents");
            System.out.println("0. Quitter");
            System.out.print("Choix: ");
            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> creerAgent();
                case 2 -> modifierAgent();
                case 3 -> supprimerAgent();
                case 4 -> trouverAgent();
                case 5 -> listerAgents();
                case 0 -> System.out.println("👋 Fin du programme");
                default -> System.out.println("⚠️ Choix invalide !");
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

            Agent agent = controller.creerAgent(nom, prenom, email, motDePasse,
                    TypeAgent.valueOf(type.toUpperCase()), null);
            System.out.println("✅ Agent créé: " + agent.getNomComplet());
        } catch (IllegalArgumentException e) {
            System.out.println("⚠️ Type d'agent invalide: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("⚠️ Erreur lors de la création: " + e.getMessage());
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

            Agent agent = controller.modifierAgent(id, nom, prenom, email);
            System.out.println("✅ Agent modifié: " + agent.getNomComplet());
        } catch (Exception e) {
            System.out.println("⚠️ Erreur lors de la modification: " + e.getMessage());
        }
    }

    private void supprimerAgent() {
        System.out.print("ID de l'agent à supprimer: ");
        String id = scanner.nextLine();
        boolean deleted = controller.supprimerAgent(id);
        System.out.println(deleted ? "Agent supprimé" : "Agent introuvable");
    }

    private void trouverAgent() {
        System.out.print("ID de l'agent à chercher: ");
        String id = scanner.nextLine();
        controller.trouverAgent(id).ifPresentOrElse(
                a -> System.out.println("Agent trouvé: " + a.getNomComplet() + " | " + a.getEmail()),
                () -> System.out.println("Aucun agent trouvé avec cet ID")
        );
    }

    private void listerAgents() {
        System.out.println("\n--- Liste des agents ---");
        List<Agent> agents = controller.listerTousLesAgents();
        if (agents.isEmpty()) {
            System.out.println("Aucun agent trouvé.");
        } else {
            agents.forEach(agent ->
                    System.out.println(agent.getId() + " | " + agent.getNomComplet() + " | " + agent.getEmail())
            );
        }
    }
}
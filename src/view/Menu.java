package view;

import controller.AgentController;
import controller.AuthController;
import controller.DepartementController;
import controller.PaiementController;
import model.Agent;
import repository.AgentRepositoryImpl;
import repository.DepartementRepository;
import repository.PaiementRepository;
import service.AgentServiceImpl;
import service.AuthServiceImpl;
import service.DepartementServiceImpl;
import service.PaiementServiceImpl;
import service.interfaces.IAgentService;
import service.interfaces.IAuthService;
import service.interfaces.IDepartementService;
import service.interfaces.IPaiementService;

import java.util.Scanner;

public class Menu {

    private final AgentController agentController;
    private final DepartementController departementController;
    private final PaiementController paiementController;
    private final AuthController authController;
    private final Scanner scanner = new Scanner(System.in);

    public Menu() {
        AgentRepositoryImpl agentRepository = new AgentRepositoryImpl();
        DepartementRepository departementRepository = new DepartementRepository();
        PaiementRepository paiementRepository = new PaiementRepository();

        IAgentService agentService = new AgentServiceImpl(agentRepository, departementRepository);
        IDepartementService departementService = new DepartementServiceImpl(departementRepository, agentRepository);
        IPaiementService paiementService = new PaiementServiceImpl(paiementRepository, agentRepository, departementRepository);
        IAuthService authService = new AuthServiceImpl(agentRepository);

        this.agentController = new AgentController(agentService);
        this.departementController = new DepartementController(departementService);
        this.paiementController = new PaiementController(paiementService);
        this.authController = new AuthController(authService);
    }

    public void start() {
        System.out.println("========================================");
        System.out.println("   SYSTEME DE GESTION DES PAIEMENTS");
        System.out.println("========================================");

        if (!menuConnexion()) {
            System.out.println("Echec de connexion");
            return;
        }

        Agent agentConnecte = authController.obtenirAgentConnecte().get();

        switch (agentConnecte.getTypeAgent()) {
            case OUVRIER, STAGIAIRE -> new MenuAgent(authController, paiementController, scanner).afficher();
            case RESPONSABLE_DEPARTEMENT -> new MenuResponsable(authController, agentController, departementController, paiementController, scanner).afficher();
            case DIRECTEUR -> new MenuDirecteur(authController, agentController, departementController, paiementController, scanner).afficher();
        }
    }

    private boolean menuConnexion() {
        System.out.println("\n=== CONNEXION ===");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Mot de passe: ");
        String motDePasse = scanner.nextLine();

        return authController.seConnecter(email, motDePasse).isPresent();
    }
}
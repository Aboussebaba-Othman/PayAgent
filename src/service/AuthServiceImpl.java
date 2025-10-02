package service;

import model.Agent;
import repository.AgentRepositoryImpl;
import service.interfaces.IAuthService;

import java.util.Optional;

public class AuthServiceImpl implements IAuthService {

    private final AgentRepositoryImpl agentRepository;
    private Agent agentConnecte;

    public AuthServiceImpl(AgentRepositoryImpl agentRepository) {
        this.agentRepository = agentRepository;
        this.agentConnecte = null;
    }

    @Override
    public Optional<Agent> seConnecter(String email, String motDePasse) {
        if (email == null || email.trim().isEmpty()) {
            System.out.println("L'email ne peut pas être vide");
            return Optional.empty();
        }

        if (motDePasse == null || motDePasse.trim().isEmpty()) {
            System.out.println("Le mot de passe ne peut pas être vide");
            return Optional.empty();
        }

        Optional<Agent> agentOpt = agentRepository.findByEmail(email.trim());

        if (agentOpt.isEmpty()) {
            System.out.println("Email incorrect");
            return Optional.empty();
        }

        Agent agent = agentOpt.get();

        if (!agent.getMotDePasse().equals(motDePasse)) {
            System.out.println("mot de passe incorrect");
            return Optional.empty();
        }

        this.agentConnecte = agent;
        System.out.println("Connexion Bien! Bienvenue " + agent.getNomComplet());
        return Optional.of(agent);
    }

    @Override
    public void seDeconnecter() {
        if (agentConnecte != null) {
            System.out.println("Au revoir " + agentConnecte.getNomComplet());
            agentConnecte = null;
        }
    }

    @Override
    public Optional<Agent> obtenirAgentConnecte() {
        return Optional.ofNullable(agentConnecte);
    }

    @Override
    public boolean estConnecte() {
        return agentConnecte != null;
    }
}
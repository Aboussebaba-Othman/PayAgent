package service;

import model.Agent;
import model.Paiment;
import model.TypeAgent;
import model.TypePaiment;
import repository.AgentRepositoryImpl;
import service.interfaces.IAgentService;

import java.util.List;
import java.util.Optional;

public class AgentServiceImpl implements IAgentService {

    private final AgentRepositoryImpl agentRepository;

    public AgentServiceImpl(AgentRepositoryImpl agentRepository) {
        this.agentRepository = agentRepository;
    }



    @Override
    public Agent creerAgent(String nom, String prenom, String email, String motDePasse,
                            TypeAgent typeAgent, String departementId) {


        long count = agentRepository.count();
        String agentId = "AG" + (count + 1);

        Agent agent = new Agent(agentId, nom, prenom, email, motDePasse, typeAgent, null);

        agentRepository.save(agent);

        System.out.println("Agent saved: " + agent.getNomComplet());
        return agent;
    }

    @Override
    public Agent modifierAgent(String agentId, String nom, String prenom, String email) {
        Optional<Agent> agentOpt = agentRepository.findById(agentId);

        if (agentOpt.isEmpty()) {
            throw new IllegalArgumentException("Agent non trouvé avec ID: " + agentId);
        }

        Agent agent = agentOpt.get();

        agent.setNom(nom.trim());
        agent.setPrenom(prenom.trim());
        agent.setEmail(email.trim());

        agentRepository.update(agent);

        System.out.println("Agent update: " + agent.getNomComplet());
        return agent;
    }

    @Override
    public boolean supprimerAgent(String agentId) {
        boolean deleted = agentRepository.deleteById(agentId);
        if (deleted) {
            System.out.println("Agent supprimé (ID: " + agentId + ")");
        } else {
            System.out.println("Aucun agent trouvé avec ID: " + agentId);
        }
        return deleted;
    }

    @Override
    public Optional<Agent> trouverAgent(String agentId) {
        return agentRepository.findById(agentId);
    }

    @Override
    public List<Agent> listerTousLesAgents() {
        return agentRepository.findAll();
    }

    @Override
    public boolean ajouterPaiement(String agentId, TypePaiment type, double montant, String motif, boolean conditionValidee) {
        return false;
    }

    @Override
    public List<Paiment> consulterHistoriquePaiements(String agentId) {
        return List.of();
    }

    @Override
    public List<Paiment> filtrerPaiementsParType(String agentId, TypePaiment type) {
        return List.of();
    }

    @Override
    public double calculerTotalPaiements(String agentId) {
        return 0;
    }

    @Override
    public List<Agent> rechercherAgentsParNom(String nom) {
        return List.of();
    }

    @Override
    public List<Agent> listerAgentsParType(TypeAgent type) {
        return List.of();
    }

    @Override
    public List<Agent> listerAgentsParDepartement(String departementId) {
        return List.of();
    }

}
package repository;

import model.Agent;
import model.Departement;
import model.TypeAgent;
import repository.Interface.IAgentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AgentRepository implements IAgentRepository {
    private final List<Agent> agents = new ArrayList<>();

    @Override
    public void save(Agent agent) {
        if (agent == null) {
            throw new IllegalArgumentException("Agent ne peut pas etre null");
        }
        agents.add(agent);
        System.out.println("Agent Saved");
    }

    @Override
    public List<Agent> findByTypeAgent(TypeAgent typeAgent) {
        return agents.stream().filter(t -> t.getTypeAgent() == typeAgent).collect(Collectors.toList());
    }

    @Override
    public List<Agent> findByDepartement(Departement departement) {
        return agents.stream().filter(d->d.getDepartement() == departement).collect(Collectors.toList());
    }

    @Override
    public List<Agent> findByNomContaining(String nom) {
        return agents.stream().filter(c->c.getNom().toLowerCase().contains(nom.toLowerCase())).collect(Collectors.toList());
    }

    @Override
    public Optional<Agent> findByEmail(String email) {
        return agents.stream().filter(e->e.getEmail().equals(email)).findFirst();
    }

    @Override
    public boolean existsByEmail(String email) {
        return agents.stream().anyMatch(agent -> agent.getEmail().equalsIgnoreCase(email));
    }

    @Override
    public long countByDepartement(Departement departement) {
        return agents.stream().filter(agent -> agent.getDepartement().equals(departement)).count();
    }

    @Override
    public Optional<Agent> findById(String id) {
        return agents.stream().filter(agentB -> agentB.getId().equals(id)).findFirst();
    }

    @Override
    public List<Agent> findAll() {
        return new ArrayList<>(agents);
    }

    @Override
    public void update(Agent entity) {

    }

    @Override
    public boolean deleteById(String id) {
        return agents.removeIf(agent -> agent.getId().equals(id));
    }

    @Override
    public boolean existsById(String id) {
        return agents.stream().anyMatch(agent -> agent.getId().equals(id));
    }

    @Override
    public long count() {
        return agents.size();
    }



}

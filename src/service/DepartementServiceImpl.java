package service;

import model.Agent;
import model.Departement;
import model.TypeAgent;
import repository.AgentRepositoryImpl;
import repository.DepartementRepository;
import service.interfaces.IDepartementService;

import java.util.List;
import java.util.Optional;


public class DepartementServiceImpl implements IDepartementService {
    private final DepartementRepository departementRepository;
    private final AgentRepositoryImpl agentRepository;

    public DepartementServiceImpl(DepartementRepository departementRepository,
                                  AgentRepositoryImpl agentRepository) {
        this.departementRepository = departementRepository;
        this.agentRepository = agentRepository;
    }

    @Override
    public Departement creerDepartement(String nom) {
        long count = departementRepository.count();
        String id = "DEP" + (count + 1);
        Departement dept = new Departement(id, nom);
        departementRepository.save(dept);
        System.out.println("Département créé: " + nom);
        return dept;
    }

    @Override
    public Departement modifierDepartement(String departementId, String nouveauNom) {
        Departement dept = departementRepository.findById(departementId)
                .orElseThrow(() -> new IllegalArgumentException("Département non trouvé"));

        dept.setNom(nouveauNom);
        departementRepository.update(dept);
        System.out.println("Département Update: " + nouveauNom);
        return dept;
    }

    @Override
    public boolean supprimerDepartement(String departementId) {
        boolean deleted = departementRepository.deleteById(departementId);
        if (deleted) {
            System.out.println("Département supprimé (ID: " + departementId + ")");
        }
        return deleted;
    }

    @Override
    public Optional<Departement> trouverDepartement(String departementId) {
        return departementRepository.findById(departementId);
    }

    @Override
    public List<Departement> listerTousLesDepartements() {
        return departementRepository.findAll();
    }

    @Override
    public boolean ajouterAgentAuDepartement(String departementId, String agentId) {
        Optional<Departement> deptOpt = departementRepository.findById(departementId);
        Optional<Agent> agentOpt = agentRepository.findById(agentId);

        if (deptOpt.isEmpty() || agentOpt.isEmpty()) {
            return false;
        }

        Agent agent = agentOpt.get();
        Departement dept = deptOpt.get();

        agent.setDepartement(dept);
        agentRepository.update(agent);

        System.out.println("Agent " + agent.getNomComplet() + " ajouté au département " + dept.getNom());
        return true;
    }

    @Override
    public boolean retirerAgentDuDepartement(String departementId, String agentId) {
        if (departementId == null || departementId.trim().isEmpty()) {
            System.out.println("ID du département invalide");
            return false;
        }

        if (agentId == null || agentId.trim().isEmpty()) {
            System.out.println("ID de l'agent invalide");
            return false;
        }

        Optional<Departement> deptOpt = departementRepository.findById(departementId);
        if (deptOpt.isEmpty()) {
            System.out.println("Département non trouvé avec l'ID: " + departementId);
            return false;
        }

        Optional<Agent> agentOpt = agentRepository.findById(agentId);
        if (agentOpt.isEmpty()) {
            System.out.println("Agent non trouvé avec l'ID: " + agentId);
            return false;
        }

        Agent agent = agentOpt.get();
        Departement dept = deptOpt.get();

        List<Agent> agentsDuDept = agentRepository.findByDepartement(dept);
        boolean appartientAuDept = agentsDuDept.stream()
                .anyMatch(a -> a.getId().equals(agentId));

        if (!appartientAuDept) {
            System.out.println("L'agent " + agent.getNomComplet() + " n'appartient pas au département " + dept.getNom());
            return false;
        }

        agent.setDepartement(null);
        agentRepository.update(agent);

        System.out.println("Agent " + agent.getNomComplet() + " retiré du département " + dept.getNom());
        return true;
    }
    @Override
    public boolean affecterResponsable(String departementId, String agentId) {
        Departement dept = departementRepository.findById(departementId)
                .orElseThrow(() -> new IllegalArgumentException("Département non trouvé"));

        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new IllegalArgumentException("Agent non trouvé"));

        if (agent.getTypeAgent() != TypeAgent.RESPONSABLE_DEPARTEMENT) {
            throw new IllegalArgumentException("Seul un agent de type RESPONSABLE_DEPARTEMENT peut être responsable");
        }

        if (dept.getResponsable() != null) {
            throw new IllegalStateException("Ce département a déjà un responsable: "
                    + dept.getResponsable().getNomComplet());
        }

        dept.setResponsable(agent);
        departementRepository.update(dept);

        System.out.println(agent.getNomComplet() + " est maintenant responsable du département " + dept.getNom());
        return true;
    }



    @Override
    public Optional<Departement> rechercherParNom(String nom) {
        return departementRepository.findByNom(nom);
    }

    @Override
    public List<Departement> listerDepartementsAvecResponsable() {
        return departementRepository.findByResponsableNotNull();
    }

    @Override
    public List<Departement> listerDepartementsSansResponsable() {
        return departementRepository.findByResponsableIsNull();
    }
}

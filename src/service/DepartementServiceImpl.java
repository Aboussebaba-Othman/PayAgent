package service;

import model.Agent;
import model.Departement;
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
        return false;
    }

    @Override
    public boolean retirerAgentDuDepartement(String departementId, String agentId) {
        return false;
    }

    @Override
    public boolean affecterResponsable(String departementId, String agentId) {
        return false;
    }

    @Override
    public double calculerTotalPaiementsDepartement(String departementId) {
        return 0;
    }

    @Override
    public double calculerSalaireMoyenDepartement(String departementId) {
        return 0;
    }

    @Override
    public List<Agent> classementAgentsParPaiements(String departementId) {
        return List.of();
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

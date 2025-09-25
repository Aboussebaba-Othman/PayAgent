package repository.Interface;

import model.Agent;
import model.Departement;
import model.TypeAgent;

import java.util.List;
import java.util.Optional;

public interface AgentRepository extends IGenericRepository<Agent, String> {

    List<Agent> findByTypeAgent(TypeAgent typeAgent);
    List<Agent> findByDepartement(Departement departement);
    List<Agent> findByNomContaining(String nom);
    Optional<Agent> findByEmail(String email);
    boolean existsByEmail(String email);
    long countByDepartement(Departement departement);
}

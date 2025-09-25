package repository.Interface;

import model.Departement;
import model.Agent;
import java.util.List;
import java.util.Optional;

public interface IDepartementRepository extends IGenericRepository<Departement, String> {

    Optional<Departement> findByNom(String nom);

    List<Departement> findByResponsableNotNull();

    List<Departement> findByResponsableIsNull();

    Optional<Departement> findByAgentsContaining(Agent agent);

    boolean existsByNom(String nom);
}
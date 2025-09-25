package repository.Interface;

import model.Paiment;
import model.TypePaiment;
import model.Agent;
import java.time.LocalDate;
import java.util.List;


public interface IPaiementRepository extends IGenericRepository<Paiment, String> {

    List<Paiment> findByAgent(Agent agent);

    List<Paiment> findByType(TypePaiment type);

    List<Paiment> findByDate(LocalDate date);

    List<Paiment> findByDateBetween(LocalDate debut, LocalDate fin);

    List<Paiment> findByMontantGreaterThan(double montant);

    List<Paiment> findByAgentAndType(Agent agent, TypePaiment type);

    double getTotalByAgent(Agent agent);

    long countByType(TypePaiment type);
}
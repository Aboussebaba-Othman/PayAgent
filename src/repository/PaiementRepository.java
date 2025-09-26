package repository;

import model.Agent;
import model.Paiment;
import model.TypePaiment;
import repository.Interface.IPaiementRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PaiementRepository implements IPaiementRepository {
    @Override
    public List<Paiment> findByAgent(Agent agent) {
        return List.of();
    }

    @Override
    public List<Paiment> findByType(TypePaiment type) {
        return List.of();
    }

    @Override
    public List<Paiment> findByDate(LocalDate date) {
        return List.of();
    }

    @Override
    public List<Paiment> findByDateBetween(LocalDate debut, LocalDate fin) {
        return List.of();
    }

    @Override
    public List<Paiment> findByMontantGreaterThan(double montant) {
        return List.of();
    }

    @Override
    public List<Paiment> findByAgentAndType(Agent agent, TypePaiment type) {
        return List.of();
    }

    @Override
    public double getTotalByAgent(Agent agent) {
        return 0;
    }

    @Override
    public long countByType(TypePaiment type) {
        return 0;
    }

    @Override
    public void save(Paiment entity) {

    }

    @Override
    public Optional<Paiment> findById(String s) {
        return Optional.empty();
    }

    @Override
    public List<Paiment> findAll() {
        return List.of();
    }

    @Override
    public void update(Paiment entity) {

    }

    @Override
    public boolean deleteById(String s) {
        return false;
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }
}

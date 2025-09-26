package repository;

import model.Agent;
import model.Paiment;
import model.TypePaiment;
import repository.Interface.IPaiementRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PaiementRepository implements IPaiementRepository {

    private final List<Paiment> paiments = new ArrayList<>();

    @Override
    public List<Paiment> findByAgent(Agent agent) {
        return agent.getPaiments();
    }

    @Override
    public List<Paiment> findByType(TypePaiment type) {
        return paiments.stream().filter(paiment -> paiment.getType() == type).collect(Collectors.toList());
    }

    @Override
    public List<Paiment> findByDate(LocalDate date) {
        return paiments.stream().filter(paiment -> paiment.getDate() == date).collect(Collectors.toList());
    }

    @Override
    public List<Paiment> findByDateBetween(LocalDate debut, LocalDate fin) {
        return paiments.stream().filter(paiment -> !paiment.getDate().isBefore(debut) && !paiment.getDate().isAfter(fin)).collect(Collectors.toList());
    }

    @Override
    public List<Paiment> findByMontantGreaterThan(double montant) {
        return paiments.stream().filter(paiment -> paiment.getMontant()>montant).collect(Collectors.toList());
    }

    @Override
    public List<Paiment> findByAgentAndType(Agent agent, TypePaiment type) {
        return agent.getPaiments().stream().filter(paiment -> paiment.getType().equals(type)).collect(Collectors.toList());
    }

    @Override
    public double getTotalByAgent(Agent agent) {
        return agent.getPaiments().stream().mapToDouble(Paiment::getMontant).sum();
    }

    @Override
    public long countByType(TypePaiment type) {
        return paiments.stream().filter(paiment -> paiment.getType().equals(type)).count();
    }

    @Override
    public void save(Paiment paiment) {
        paiments.add(paiment);
        System.out.println("paiment saved");
    }

    @Override
    public Optional<Paiment> findById(String id) {
        return paiments.stream().filter(paiment -> paiment.getIdPaiement().equals(id)).findFirst();
    }

    @Override
    public List<Paiment> findAll() {
        return new ArrayList<>(paiments);
    }

    @Override
    public void update(Paiment entity) {

    }

    @Override
    public boolean deleteById(String id) {
        return paiments.removeIf(paiment -> paiment.getIdPaiement().equals(id));
    }

    @Override
    public boolean existsById(String id) {
        return paiments.stream().anyMatch(paiment -> paiment.getIdPaiement().equals(id));
    }

    @Override
    public long count() {
        return paiments.size();
    }
}

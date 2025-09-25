package repository;

import model.Agent;
import model.Departement;
import repository.Interface.IDepartementRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DepartementRepository implements IDepartementRepository {

    private final List<Departement> departements = new ArrayList<>();

    @Override
    public Optional<Departement> findByNom(String nom) {
        return departements.stream().filter(departement -> departement.getNom().equals(nom)).findFirst();
    }

    @Override
    public List<Departement> findByResponsableNotNull() {
        return List.of();
    }

    @Override
    public List<Departement> findByResponsableIsNull() {
        return List.of();
    }

    @Override
    public Optional<Departement> findByAgentsContaining(Agent agent) {
        return departements.stream().filter(Departement-> agent.getNom().contains(agent.getNom())).findAny();
    }

    @Override
    public boolean existsByNom(String nom) {
        return false;
    }

    @Override
    public void save(Departement entity) {

    }

    @Override
    public Optional<Departement> findById(String s) {
        return Optional.empty();
    }

    @Override
    public List<Departement> findAll() {
        return List.of();
    }

    @Override
    public void update(Departement entity) {

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

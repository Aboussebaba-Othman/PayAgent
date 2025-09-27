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
        return departements.stream().filter(dept -> dept.getNom().equalsIgnoreCase(nom)).findFirst();
    }

    @Override
    public List<Departement> findByResponsableNotNull() {
        return departements.stream().filter(departement -> departement.getResponsable() != null).collect(Collectors.toList());
    }

    @Override
    public List<Departement> findByResponsableIsNull() {
        return departements.stream().filter(departement -> departement.getResponsable() == null).collect(Collectors.toList());
    }

    @Override
    public Optional<Departement> findByAgentsContaining(Agent agent) {
        return departements.stream().filter(Departement-> agent.getNom().contains(agent.getNom())).findAny();
    }

    @Override
    public boolean existsByNom(String nom) {
        return departements.stream().anyMatch(dep->dep.getNom().equalsIgnoreCase(nom));
    }

    @Override
    public void save(Departement departement) {
        if (departement == null){
            throw new IllegalArgumentException("departement ne peut pas etre null");
        }
        departements.add(departement);
        System.out.println("departement saved");
    }

    @Override
    public Optional<Departement> findById(String id) {
        return departements.stream().filter(dept -> dept.getIdDepartement().equals(id)).findFirst();    }

    @Override
    public List<Departement> findAll() {
        return new ArrayList<>(departements);
    }

    @Override
    public void update(Departement entity) {

    }


    @Override
    public boolean deleteById(String id) {
        return departements.removeIf(dep->dep.getIdDepartement().equals(id));
    }

    @Override
    public boolean existsById(String id) {
        return departements.stream().anyMatch(dep->dep.getIdDepartement().equals(id));
    }

    @Override
    public long count() {
        return departements.size();
    }
}

package service;

import model.Agent;
import model.Departement;
import model.Paiment;
import model.TypeAgent;
import model.TypePaiment;
import repository.AgentRepositoryImpl;
import repository.DepartementRepository;
import repository.PaiementRepository;
import service.interfaces.IPaiementService;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PaiementServiceImpl implements IPaiementService {

    private final PaiementRepository paiementRepository;
    private final AgentRepositoryImpl agentRepository;
    private final DepartementRepository departementRepository;

    public PaiementServiceImpl(PaiementRepository paiementRepository,
                               AgentRepositoryImpl agentRepository,
                               DepartementRepository departementRepository) {
        this.paiementRepository = paiementRepository;
        this.agentRepository = agentRepository;
        this.departementRepository = departementRepository;
    }

    // ========== CRUD ==========

    @Override
    public Paiment creerPaiement(String agentId, TypePaiment type, double montant,
                                 String motif, boolean conditionValidee) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant doit être strictement positif");
        }

        if (motif == null || motif.trim().isEmpty()) {
            throw new IllegalArgumentException("Le motif ne peut pas être vide");
        }

        Optional<Agent> agentOpt = agentRepository.findById(agentId);
        if (agentOpt.isEmpty()) {
            throw new IllegalArgumentException("Agent non trouvé avec ID: " + agentId);
        }

        Agent agent = agentOpt.get();

        if (type == TypePaiment.BONUS) {
            if (!agent.peutRecevoirBonus()) {
                throw new IllegalArgumentException(
                        "Seuls les agents de type RESPONSABLE_DEPARTEMENT ou DIRECTEUR peuvent recevoir un bonus"
                );
            }
            if (!conditionValidee) {
                throw new IllegalArgumentException(
                        "La condition pour recevoir le bonus n'est pas validée"
                );
            }
        }

        if (type == TypePaiment.INDEMNITE) {
            if (!agent.peutRecevoirIndemnite()) {
                throw new IllegalArgumentException(
                        "Seuls les agents de type RESPONSABLE_DEPARTEMENT ou DIRECTEUR peuvent recevoir une indemnité"
                );
            }
            if (!conditionValidee) {
                throw new IllegalArgumentException(
                        "La condition pour recevoir l'indemnité n'est pas validée"
                );
            }
        }
        String paiementId = "PAY" + System.currentTimeMillis();
        Paiment paiement = new Paiment(paiementId, type, montant, LocalDate.now(), motif, conditionValidee, agent
        );

        paiementRepository.save(paiement);
        System.out.println("Paiement créé: " + type + " de " + montant + " DH pour " +
                agent.getNomComplet());

        return paiement;
    }

    @Override
    public Optional<Paiment> trouverPaiement(String idPaiement) {
        if (idPaiement == null || idPaiement.trim().isEmpty()) {
            return Optional.empty();
        }
        return paiementRepository.findById(idPaiement);
    }

    @Override
    public boolean supprimerPaiement(String idPaiement) {
        if (idPaiement == null || idPaiement.trim().isEmpty()) {
            System.out.println("ID de paiement invalide");
            return false;
        }

        boolean deleted = paiementRepository.deleteById(idPaiement);
        if (deleted) {
            System.out.println("Paiement supprimé avec succès (ID: " + idPaiement + ")");
        } else {
            System.out.println("Paiement non trouvé (ID: " + idPaiement + ")");
        }
        return deleted;
    }

    @Override
    public List<Paiment> listerTousLesPaiements() {
        return paiementRepository.findAll();
    }

    @Override
    public Paiment modifierPaiement(String idPaiement, TypePaiment type, double montant,
                                    String motif, boolean conditionValidee) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant doit être strictement positif");
        }
        Optional<Paiment> paiementOpt = paiementRepository.findById(idPaiement);
        if (paiementOpt.isEmpty()) {
            throw new IllegalArgumentException("Paiement non trouvé avec ID: " + idPaiement);
        }

        Paiment paiement = paiementOpt.get();
        Agent agent = paiement.getAgent();

        if (type == TypePaiment.BONUS) {
            if (!agent.peutRecevoirBonus()) {
                throw new IllegalArgumentException(
                        "Cet agent ne peut pas recevoir de bonus"
                );
            }
            if (!conditionValidee) {
                throw new IllegalArgumentException(
                        "La condition pour le bonus n'est pas validée"
                );
            }
        }
        if (type == TypePaiment.INDEMNITE) {
            if (!agent.peutRecevoirIndemnite()) {
                throw new IllegalArgumentException(
                        "Cet agent ne peut pas recevoir d'indemnité"
                );
            }
            if (!conditionValidee) {
                throw new IllegalArgumentException(
                        "La condition pour l'indemnité n'est pas validée"
                );
            }
        }

        paiement.setType(type);
        paiement.setMontant(montant);
        paiement.setMotif(motif);
        paiement.setConditionValidee(conditionValidee);

        paiementRepository.update(paiement);

        System.out.println("Paiement modifié avec succès");
        return paiement;
    }


    // ========== RECHERCHE & FILTRAGE ==========


    @Override
    public List<Paiment> consulterPaiementsParAgent(String agentId) {
        Optional<Agent> agentOpt = agentRepository.findById(agentId);
        if (agentOpt.isEmpty()) {
            System.out.println("Agent non trouvé");
            return List.of();
        }

        return paiementRepository.findByAgent(agentOpt.get());
    }

    @Override
    public List<Paiment> filtrerPaiementsParType(TypePaiment type) {
        if (type == null) {
            return List.of();
        }
        return paiementRepository.findByType(type);
    }

    @Override
    public List<Paiment> filtrerPaiementsParDate(LocalDate date) {
        if (date == null) {
            return List.of();
        }
        return paiementRepository.findByDate(date);
    }

    @Override
    public List<Paiment> filtrerPaiementsEntreDates(LocalDate debut, LocalDate fin) {
        if (debut == null || fin == null) {
            return List.of();
        }
        if (debut.isAfter(fin)) {
            throw new IllegalArgumentException(
                    "La date de début doit être antérieure ou égale à la date de fin"
            );
        }
        return paiementRepository.findByDateBetween(debut, fin);
    }

    @Override
    public List<Paiment> filtrerPaiementsParMontantSuperieur(double montant) {
        return paiementRepository.findByMontantGreaterThan(montant);
    }

    // ========== STATISTIQUES ==========

    @Override
    public double calculerTotalPaiementsAgent(String agentId) {
        Optional<Agent> agentOpt = agentRepository.findById(agentId);
        return agentOpt.map(paiementRepository::getTotalByAgent).orElse(0.0);

    }

    @Override
    public long compterPaiementsParType(TypePaiment type) {
        if (type == null) {
            return 0;
        }
        return paiementRepository.countByType(type);
    }

    @Override
    public Optional<Paiment> paiementMaxParAgent(String agentId) {
        Optional<Agent> agentOpt = agentRepository.findById(agentId);
        return agentOpt.flatMap(agent -> paiementRepository.findByAgent(agent)
                .stream()
                .max(Comparator.comparingDouble(Paiment::getMontant)));

    }

    @Override
    public Optional<Paiment> paiementMinParAgent(String agentId) {
        Optional<Agent> agentOpt = agentRepository.findById(agentId);
        return agentOpt.flatMap(agent -> paiementRepository.findByAgent(agent)
                .stream()
                .min(Comparator.comparingDouble(Paiment::getMontant)));

    }

    @Override
    public double calculerTotalPaiementsDepartement(String departementId) {
        Optional<Departement> deptOpt = departementRepository.findById(departementId);
        if (deptOpt.isEmpty()) {
            System.out.println("Département non trouvé");
            return 0.0;
        }

        Departement dept = deptOpt.get();
        List<Agent> agents = agentRepository.findByDepartement(dept);

        return agents.stream()
                .mapToDouble(paiementRepository::getTotalByAgent)
                .sum();
    }

    @Override
    public double calculerSalaireMoyenDepartement(String departementId) {
        Optional<Departement> deptOpt = departementRepository.findById(departementId);
        if (deptOpt.isEmpty()) {
            System.out.println("Département non trouvé");
            return 0.0;
        }

        Departement dept = deptOpt.get();
        List<Agent> agents = agentRepository.findByDepartement(dept);

        if (agents.isEmpty()) {
            return 0.0;
        }

        double totalSalaires = agents.stream()
                .flatMap(agent -> paiementRepository.findByAgent(agent).stream())
                .filter(p -> p.getType() == TypePaiment.SALAIRE)
                .mapToDouble(Paiment::getMontant)
                .sum();

        long nombreAgentsAvecSalaire = agents.stream()
                .filter(agent -> paiementRepository.findByAgent(agent).stream()
                        .anyMatch(p -> p.getType() == TypePaiment.SALAIRE))
                .count();

        return nombreAgentsAvecSalaire > 0 ? totalSalaires / nombreAgentsAvecSalaire : 0.0;
    }

    @Override
    public List<Agent> classementAgentsParPaiements(String departementId) {
        Optional<Departement> deptOpt = departementRepository.findById(departementId);
        if (deptOpt.isEmpty()) {
            System.out.println("Département non trouvé");
            return List.of();
        }

        Departement dept = deptOpt.get();
        List<Agent> agents = agentRepository.findByDepartement(dept);

        return agents.stream()
                .sorted((a1, a2) -> {
                    double total1 = paiementRepository.getTotalByAgent(a1);
                    double total2 = paiementRepository.getTotalByAgent(a2);
                    return Double.compare(total2, total1);
                })
                .collect(Collectors.toList());
    }
}
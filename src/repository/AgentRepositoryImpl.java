package repository;

import config.DatabaseConnection;
import model.Agent;
import model.Departement;
import model.TypeAgent;
import repository.Interface.IAgentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AgentRepositoryImpl implements IAgentRepository {

    private final Connection connection;
    private final DepartementRepository departementRepository;

    public AgentRepositoryImpl() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
            this.departementRepository = new DepartementRepository();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur connexion DB: " + e.getMessage());
        }
    }

    @Override
    public void save(Agent agent) {
        String sql = "INSERT INTO agent (id, nom, prenom, email, mot_de_passe, type_agent, departement_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, agent.getId());
            stmt.setString(2, agent.getNom());
            stmt.setString(3, agent.getPrenom());
            stmt.setString(4, agent.getEmail());
            stmt.setString(5, agent.getMotDePasse());
            stmt.setString(6, agent.getTypeAgent().name());
            stmt.setString(7, agent.getDepartement() != null ? agent.getDepartement().getIdDepartement() : null);

            stmt.executeUpdate();
            System.out.println("Nouvel agent insere: " + agent.getNom() + " " + agent.getPrenom());
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RuntimeException("Email ou ID deja existant: " + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la sauvegarde de l'agent: " + e.getMessage());
        }
    }

    @Override
    public Optional<Agent> findById(String id) {
        String sql = "SELECT * FROM agent WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return Optional.of(mapResultSetToAgent(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Agent> findAll() {
        List<Agent> agents = new ArrayList<>();
        String sql = "SELECT * FROM agent";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) agents.add(mapResultSetToAgent(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll: " + e.getMessage());
        }
        return agents;
    }

    @Override
    public void update(Agent agent) {
        String sql = "UPDATE agent SET nom = ?, prenom = ?, email = ?, mot_de_passe = ?, type_agent = ?, departement_id = ? " +
                "WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, agent.getNom());
            stmt.setString(2, agent.getPrenom());
            stmt.setString(3, agent.getEmail());
            stmt.setString(4, agent.getMotDePasse());
            stmt.setString(5, agent.getTypeAgent().name());
            stmt.setString(6, agent.getDepartement() != null ? agent.getDepartement().getIdDepartement() : null);
            stmt.setString(7, agent.getId());

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                System.out.println("Aucun agent trouve avec l'ID: " + agent.getId());
            } else {
                System.out.println("Agent mis a jour: " + agent.getNom() + " " + agent.getPrenom());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise a jour de l'agent: " + e.getMessage());
        }
    }

    @Override
    public boolean deleteById(String id) {
        String sql = "DELETE FROM agent WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur deleteById: " + e.getMessage());
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM agent WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur existsByEmail: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Optional<Agent> findByEmail(String email) {
        String sql = "SELECT * FROM agent WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return Optional.of(mapResultSetToAgent(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByEmail: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Agent> findByTypeAgent(TypeAgent typeAgent) {
        List<Agent> agents = new ArrayList<>();
        String sql = "SELECT * FROM agent WHERE type_agent = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, typeAgent.name());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) agents.add(mapResultSetToAgent(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByTypeAgent: " + e.getMessage());
        }
        return agents;
    }

    @Override
    public List<Agent> findByDepartement(Departement departement) {
        List<Agent> agents = new ArrayList<>();
        String sql = "SELECT * FROM agent WHERE departement_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, departement.getIdDepartement());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) agents.add(mapResultSetToAgent(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByDepartement: " + e.getMessage());
        }
        return agents;
    }

    @Override
    public List<Agent> findByNomContaining(String nom) {
        List<Agent> agents = new ArrayList<>();
        String sql = "SELECT * FROM agent WHERE LOWER(nom) LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + nom.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) agents.add(mapResultSetToAgent(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByNomContaining: " + e.getMessage());
        }
        return agents;
    }

    @Override
    public long countByDepartement(Departement departement) {
        String sql = "SELECT COUNT(*) FROM agent WHERE departement_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, departement.getIdDepartement());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur countByDepartement: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public boolean existsById(String id) {
        return findById(id).isPresent();
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM agent";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) return rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur count: " + e.getMessage());
        }
        return 0;
    }

    private Agent mapResultSetToAgent(ResultSet rs) throws SQLException {
        String departementId = rs.getString("departement_id");
        Departement departement = null;

        if (departementId != null && !departementId.trim().isEmpty()) {
            departement = departementRepository.findById(departementId).orElse(null);
        }

        return new Agent(
                rs.getString("id"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("email"),
                rs.getString("mot_de_passe"),
                TypeAgent.valueOf(rs.getString("type_agent")),
                departement
        );
    }
}
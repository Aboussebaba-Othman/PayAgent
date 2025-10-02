package repository;

import config.DatabaseConnection;
import model.Agent;
import model.Paiment;
import model.TypePaiment;
import repository.Interface.IPaiementRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaiementRepository implements IPaiementRepository {

    private final Connection connection;

    public PaiementRepository() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur connexion DB: " + e.getMessage());
        }
    }

    @Override
    public void save(Paiment paiment) {
        String sql = "INSERT INTO paiement (id_paiement, agent_id, type_paiement, montant, date_paiement, motif, condition_validee) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, paiment.getIdPaiement());
            stmt.setString(2, paiment.getAgent().getId());
            stmt.setString(3, paiment.getType().name());
            stmt.setDouble(4, paiment.getMontant());
            stmt.setDate(5, Date.valueOf(paiment.getDate()));
            stmt.setString(6, paiment.getMotif());
            stmt.setBoolean(7, paiment.isConditionValidee());

            stmt.executeUpdate();
            System.out.println("Paiement enregistré: " + paiment.getType() + " - " + paiment.getMontant() + " DH");
        } catch (SQLException e) {
            throw new RuntimeException("Erreur save paiement: " + e.getMessage());
        }
    }

    @Override
    public Optional<Paiment> findById(String id) {
        String sql = "SELECT * FROM paiement WHERE id_paiement = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return Optional.of(mapResultSetToPaiment(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Paiment> findAll() {
        List<Paiment> paiments = new ArrayList<>();
        String sql = "SELECT * FROM paiement";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) paiments.add(mapResultSetToPaiment(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll: " + e.getMessage());
        }
        return paiments;
    }

    @Override
    public List<Paiment> findByAgent(Agent agent) {
        List<Paiment> paiments = new ArrayList<>();
        String sql = "SELECT * FROM paiement WHERE agent_id = ? ORDER BY date_paiement DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, agent.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Paiment p = mapResultSetToPaiment(rs);
                p.setAgent(agent);
                paiments.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByAgent: " + e.getMessage());
        }
        return paiments;
    }

    @Override
    public List<Paiment> findByAgentAndType(Agent agent, TypePaiment type) {
        List<Paiment> paiments = new ArrayList<>();
        String sql = "SELECT * FROM paiement WHERE agent_id = ? AND type_paiement = ? ORDER BY date_paiement DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, agent.getId());
            stmt.setString(2, type.name());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Paiment p = mapResultSetToPaiment(rs);
                p.setAgent(agent);
                paiments.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByAgentAndType: " + e.getMessage());
        }
        return paiments;
    }

    @Override
    public List<Paiment> findByType(TypePaiment type) {
        List<Paiment> paiments = new ArrayList<>();
        String sql = "SELECT * FROM paiement WHERE type_paiement = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, type.name());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) paiments.add(mapResultSetToPaiment(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByType: " + e.getMessage());
        }
        return paiments;
    }

    @Override
    public List<Paiment> findByDate(LocalDate date) {
        List<Paiment> paiments = new ArrayList<>();
        String sql = "SELECT * FROM paiement WHERE date_paiement = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) paiments.add(mapResultSetToPaiment(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByDate: " + e.getMessage());
        }
        return paiments;
    }

    @Override
    public List<Paiment> findByDateBetween(LocalDate debut, LocalDate fin) {
        List<Paiment> paiments = new ArrayList<>();
        String sql = "SELECT * FROM paiement WHERE date_paiement BETWEEN ? AND ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(debut));
            stmt.setDate(2, Date.valueOf(fin));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) paiments.add(mapResultSetToPaiment(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByDateBetween: " + e.getMessage());
        }
        return paiments;
    }

    @Override
    public List<Paiment> findByMontantGreaterThan(double montant) {
        List<Paiment> paiments = new ArrayList<>();
        String sql = "SELECT * FROM paiement WHERE montant > ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, montant);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) paiments.add(mapResultSetToPaiment(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByMontantGreaterThan: " + e.getMessage());
        }
        return paiments;
    }

    @Override
    public double getTotalByAgent(Agent agent) {
        String sql = "SELECT COALESCE(SUM(montant), 0) FROM paiement WHERE agent_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, agent.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur getTotalByAgent: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public long countByType(TypePaiment type) {
        String sql = "SELECT COUNT(*) FROM paiement WHERE type_paiement = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, type.name());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur countByType: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public void update(Paiment paiment) {
        String sql = "UPDATE paiement SET type_paiement = ?, montant = ?, date_paiement = ?, motif = ?, condition_validee = ? " +
                "WHERE id_paiement = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, paiment.getType().name());
            stmt.setDouble(2, paiment.getMontant());
            stmt.setDate(3, Date.valueOf(paiment.getDate()));
            stmt.setString(4, paiment.getMotif());
            stmt.setBoolean(5, paiment.isConditionValidee());
            stmt.setString(6, paiment.getIdPaiement());

            stmt.executeUpdate();
            System.out.println("Paiement mis à jour");
        } catch (SQLException e) {
            throw new RuntimeException("Erreur update paiement: " + e.getMessage());
        }
    }

    @Override
    public boolean deleteById(String id) {
        String sql = "DELETE FROM paiement WHERE id_paiement = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur deleteById: " + e.getMessage());
        }
    }

    @Override
    public boolean existsById(String id) {
        return findById(id).isPresent();
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM paiement";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) return rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur count: " + e.getMessage());
        }
        return 0;
    }

    private Paiment mapResultSetToPaiment(ResultSet rs) throws SQLException {
        return new Paiment(
                rs.getString("id_paiement"),
                TypePaiment.valueOf(rs.getString("type_paiement")),
                rs.getDouble("montant"),
                rs.getDate("date_paiement").toLocalDate(),
                rs.getString("motif"),
                rs.getBoolean("condition_validee"),
                null
        );
    }
}
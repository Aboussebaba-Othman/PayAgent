package repository;

import config.DatabaseConnection;
import model.Agent;
import model.Departement;
import repository.Interface.IDepartementRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartementRepository implements IDepartementRepository {

    private final Connection connection;

    public DepartementRepository() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur connexion DB" + e.getMessage());
        }
    }

    @Override
    public Optional<Departement> findByNom(String nom) {
        String sql = "SELECT * FROM departement WHERE nom = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nom);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) return Optional.of(mapResultSetToDepartement(rs));
        }catch (SQLException e){
            throw new RuntimeException("Erreur findByNOM :" + e.getMessage());
        }
        return  Optional.empty();

    }

    @Override
    public List<Departement> findByResponsableNotNull() {
        List<Departement> departements = new ArrayList<>();
        String sql = "SELECT * FROM departement WHERE responsable_id is not null";
        try(Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                departements.add(mapResultSetToDepartement(rs));
            }
        }catch (SQLException e){
            throw new RuntimeException("Erreur findByResponsableNotNull" + e.getMessage());
        }
        return departements;
    }

    @Override
    public List<Departement> findByResponsableIsNull() {
        List<Departement> departements = new ArrayList<>();
        String sql = "SELECT * FROM departement WHERE responsable_id is null";
        try(Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                departements.add(mapResultSetToDepartement(rs));
            }
        }catch (SQLException e){
            throw  new RuntimeException("Erreur findByResponsableIsNull :" + e.getMessage());
        }
        return departements;
    }

    @Override
    public Optional<Departement> findByAgentsContaining(Agent agent) {
        String sql = "SELECT d.* FROM departement d JOIN agent a ON d.id_departement = a.departement_id " +
                "WHERE a.id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
        stmt.setString(1, agent.getId());
        ResultSet rs = stmt.executeQuery();
        if (rs.next()){
            return Optional.of(mapResultSetToDepartement(rs));
        }
        }catch (SQLException e){
            throw new RuntimeException("Erreur findByAgentsContaining :" + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public boolean existsByNom(String nom) {
        String sql = "SELECT COUNT(*) FROM departement WHERE nom = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, nom);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) return rs.getInt(1) > 0;
        }catch (SQLException e){
            throw new RuntimeException("Erreur existsByNom :" + e.getMessage());
        }
        return false;
    }

    @Override
    public void save(Departement departement) {
        String sql = "INSERT INTO departement (id_departement, nom, responsable_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, departement.getIdDepartement());
            stmt.setString(2, departement.getNom());
            stmt.setString(3, departement.getResponsable() != null ? departement.getResponsable().getId() : null);

            stmt.executeQuery();
            System.out.println("Nouvel departement créé :" + departement.getNom());
        }catch (SQLException e){
            throw new RuntimeException("Erreur de saved departement" + e.getMessage());
        }
    }

    @Override
    public Optional<Departement> findById(String id) {
        String sql = "SELECT * FROM departement WHERE id_departement = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return Optional.of(mapResultSetToDepartement(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById: " + e.getMessage());
        }
        return Optional.empty();
    }
    @Override
    public List<Departement> findAll() {
        List<Departement> list = new ArrayList<>();
        String sql = "SELECT * FROM departement";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToDepartement(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll: " + e.getMessage());
        }
        return list;
    }

    @Override
    public void update(Departement entity) {
        String sql = "UPDATE departement SET nom = ?, responsable_id = ? WHERE id_departement = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entity.getNom());
            stmt.setString(2, entity.getResponsable() != null ? entity.getResponsable().getId() : null);
            stmt.setString(3, entity.getIdDepartement());
            stmt.executeUpdate();
            System.out.println("departement mis à jour: " + entity.getNom());
        } catch (SQLException e) {
            throw new RuntimeException("Erreur update departement: " + e.getMessage());
        }
    }


    @Override
    public boolean deleteById(String id) {
        String sql = "DELETE FROM departement WHERE id_departement = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur deleteById: " + e.getMessage());
        }
    }

    @Override
    public boolean existsById(String id) {
        String sql = "SELECT COUNT(*) FROM departement WHERE id_departement = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur existsById: " + e.getMessage());
        }
        return false;
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM departement";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur count: " + e.getMessage());
        }
        return 0;
    }

    private  Departement mapResultSetToDepartement(ResultSet rs) throws SQLException {
        return new Departement(
                rs.getString("idDepartement"),
                rs.getString("nom")
        );
    }
}

package service.interfaces;

import model.Agent;
import java.util.Optional;

public interface IAuthService {
    Optional<Agent> seConnecter(String email, String motDePasse);
    void seDeconnecter();
    Optional<Agent> obtenirAgentConnecte();
    boolean estConnecte();
}
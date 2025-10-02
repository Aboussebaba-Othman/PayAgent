package controller;

import model.Agent;
import service.interfaces.IAuthService;

import java.util.Optional;

public class AuthController {

    private final IAuthService authService;

    public AuthController(IAuthService authService){
    this.authService = authService;
    }
    public Optional<Agent> seConnecter(String email, String motDePasse){
        return authService.seConnecter(email, motDePasse);
    }
    public void seDeconnecter(){
        authService.seDeconnecter();
    }
    public Optional<Agent> obtenirAgentConnecte(){
        return authService.obtenirAgentConnecte();
    }
    public boolean estConnecte(){
        return authService.estConnecte();
    }

}

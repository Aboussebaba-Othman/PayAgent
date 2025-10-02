package controller;

import service.interfaces.IDepartementService;
import service.interfaces.IPaiementService;

public class PaiementController {
    private final IPaiementService paiementService;
    public PaiementController(IPaiementService paiementService){
        this.paiementService = paiementService;
    }



}

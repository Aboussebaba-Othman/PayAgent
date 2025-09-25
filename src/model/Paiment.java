package model;

import java.time.LocalDate;


public class Paiment {

    private String idPaiement;
    private TypePaiment type;
    private double montant;
    private LocalDate date;
    private String motif;
    private boolean conditionValidee;

    public Paiment(String idPaiement, TypePaiment type, double montant, LocalDate date, String motif, boolean conditionValidee) {
        this.idPaiement = idPaiement;
        this.type = type;
        this.montant = montant;
        this.date = date;
        this.motif = motif;
        this.conditionValidee = conditionValidee;
    }

    public boolean estValide() {
        return montant > 0;
    }

    @Override
    public String toString() {
        return "Paiment{" +
                ", type=" + type +
                ", montant=" + montant +
                ", date=" + date +
                ", motif='" + motif + '\'' +
                '}';
    }

    public String getIdPaiement() { return idPaiement; }
    public void setIdPaiement(String idPaiement) { this.idPaiement = idPaiement; }

    public TypePaiment getType() { return type; }
    public void setType(TypePaiment type) { this.type = type; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }

    public boolean isConditionValidee() { return conditionValidee; }
    public void setConditionValidee(boolean conditionValidee) { this.conditionValidee = conditionValidee; }
}

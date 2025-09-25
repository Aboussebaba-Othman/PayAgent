import config.DatabaseConnection;
import model.Agent;
import model.Departement;
import model.TypeAgent;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        Departement dep1 = new Departement("d1","math");
        Departement dep2 = new Departement("d2","physique");

        Agent directure = new Agent("a1","othman","aboussebaba","aboussebaba@gmail.com","1111", TypeAgent.DIRECTEUR,dep1);
        Agent responsable = new Agent("a2","oussama","errahili","errahili@gmail.com","2222", TypeAgent.RESPONSABLE_DEPARTEMENT,dep1);
        Agent ouvrier = new Agent("a3","said","ebailla","said@gmail.com","3333",TypeAgent.OUVRIER,dep2);



    }
}

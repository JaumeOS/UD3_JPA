import ajudes.JPAException;
import controller.Persistencia;
import models.Centre;
import models.Preferencia;

public class Main {
    public static void main(String[] args) {
        retornaObjecte();
        creaPreferencia();
        retornaNoms();
    }

    private static void retornaObjecte() {
        try {
            Persistencia p = new Persistencia("interins");
            Centre centre = (Centre) p.retornaObjecte("07007841");
            System.out.println(centre + "\n");
        } catch (JPAException e) {
            e.printStackTrace();
        }
    }

    private static void creaPreferencia() {
        try {
            Persistencia p = new Persistencia("interins");
            p.creaPreferencia("23797768F", "07000108", "0591", "001", 7);
            //Preferencia preferencia = p.retornaPreferencia("23797768F");
            Preferencia preferencia = (Preferencia) p.retornaObjecte("23797768F");
            System.out.println(preferencia);
        } catch (JPAException e) {
            e.printStackTrace();
        }
    }

    private static void retornaNoms() {
        try {
            Persistencia p = new Persistencia("interins");
            p.retornaNoms(0, 10).forEach(item -> System.out.println(item[0] + ", " + item[1]));
        } catch (JPAException e) {
            e.printStackTrace();
        }
    }
}

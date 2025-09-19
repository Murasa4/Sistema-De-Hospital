package SistemaHospital;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalSistema {

    private static final String url = "jdbc:postgresql://localhost:5432/hospital";
    private static final String username = "postgres";
    private static final String password = "admin";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);


        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Paciente paciente = new Paciente(connection, scanner);
            Doctores doctores = new Doctores(connection);
            while (true) {
                System.out.println("HOSPITAL");
                System.out.println("1 - Agregar Pacientes");
                System.out.println("2 - Ver Pacientes");
                System.out.println("3 - Ver Doctores");
                System.out.println("4 - Reservar Citas");
                System.out.println("5 - Salir");
                System.out.println();
                System.out.println("Eliga una opcion: ");
                int eleccion = scanner.nextInt();

                switch (eleccion){
                    case 1:
                        paciente.agregarPaciente();
                    case 2:
                        paciente.vistaPaciente();
                    case 3:
                        doctores.vistaDoctores();
                    case 4:

                    case 5:
                        return;
                    default:
                        System.out.println("Eliga una opcion valida.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void reservarCita()

}

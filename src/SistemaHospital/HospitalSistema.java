package SistemaHospital;

import java.sql.*;
import java.util.Date;
import java.util.Scanner;

public class HospitalSistema {

    private static final String url = "";
    private static final String username = "";
    private static final String password = "";

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
                        System.out.println();
                        break;
                    case 2:
                        paciente.vistaPaciente();
                        System.out.println();
                        break;
                    case 3:
                        doctores.vistaDoctores();
                        System.out.println();
                        break;
                    case 4:
                        reservarCita(paciente, doctores, connection, scanner);
                        System.out.println();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Eliga una opcion valida.");
                        break;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void reservarCita(Paciente paciente, Doctores doctores, Connection connection, Scanner scanner) {
        System.out.print("Introduce ID del Paciente: ");
        int idPaciente = scanner.nextInt();
        System.out.println("Introduce ID del Doctor: ");
        int idDoctor = scanner.nextInt();
        System.out.println("Introduce la fecha de la cita (YYYY-MM-DD)");
        String fechaCita = scanner.next();

        if(paciente.getPacienteById(idPaciente) && doctores.getDoctorById(idDoctor)) {
            if(disponibilidadDoctor(idDoctor, fechaCita, connection)) {
                String citaQuery = "INSERT INTO citas(id_paciente, id_doctor, cita_fecha) VALUES (?,?,?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(citaQuery);
                    preparedStatement.setInt(1, idPaciente);
                    preparedStatement.setInt(2, idDoctor);
                    preparedStatement.setDate(3, java.sql.Date.valueOf(fechaCita));
                    int filasAfectadas = preparedStatement.executeUpdate();
                    if(filasAfectadas>0) {
                        System.out.println("Cita agendada correctamente.");
                    } else {
                        System.out.println("Error al reservar");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else {
                System.out.println("Doctor no disponible.");
            }
        } else {
            System.out.println("No existe el Paciente o el Doctor");
        }
    }

    public static boolean disponibilidadDoctor(int doctorId, String citaFecha, Connection connection) {
        String query = "SELECT COUNT(*) FROM citas WHERE id_doctor = ? AND cita_fecha = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setDate(2, java.sql.Date.valueOf(citaFecha));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                    int contador = resultSet.getInt(1);
                    if (contador == 0) {
                        return true;
                    } else {
                        return false;
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            return false;
        }

    }



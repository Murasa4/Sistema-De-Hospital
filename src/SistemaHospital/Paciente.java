package SistemaHospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Paciente {
    private Connection connection;
    private Scanner scanner;

    public Paciente(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void agregarPaciente() {
        System.out.print("Nombre del Paciente: ");
        String nombre = scanner.next();
        System.out.print("Edad del Paciente: ");
        int edad = scanner.nextInt();
        System.out.print("Genero del Paciente: ");
        String genero = scanner.next();

        try {
            String query = "INSERT INTO Pacientes(nombre,edad,genero) VALUES(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,nombre);
            preparedStatement.setInt(2,edad);
            preparedStatement.setString(3,genero);

            int filasAfectadas = preparedStatement.executeUpdate();
            if(filasAfectadas > 0) {
                System.out.println("Paciente agregado correctamente.");
            } else {
                System.out.println("Error al agregar paciente");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}

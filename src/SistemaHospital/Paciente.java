package SistemaHospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            String query = "INSERT INTO pacientes(nombre,edad,genero) VALUES(?,?,?)";
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

    public void vistaPaciente() {
        String query = "SELECT * FROM Pacientes";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Pacientes: ");
            System.out.println("+-------------+--------------------------+----------+---------+");
            System.out.println("| ID Paciente | Nombre                   | Edad     | Genero    |");
            System.out.println("+-------------+--------------------------+----------+---------+");
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                int edad = resultSet.getInt("edad");
                String genero = resultSet.getString("genero");
                System.out.printf("| %-13s | %-24s | %-10s | %-11s |\n", id,nombre,edad, genero);
                System.out.println("+-------------+--------------------------+----------+---------+");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean getPacienteById(int id) {
        String query =  "SELECT * FROM pacientes WHERE id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}

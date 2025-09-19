package SistemaHospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctores {
    private Connection connection;

    public Doctores(Connection connection) {
        this.connection = connection;
    }


    public void vistaDoctores() {
        String query = "SELECT * FROM doctores";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Pacientes: ");
            System.out.println("+-------------+--------------------------+-------------------+");
            System.out.println("| ID Doctores | Nombre                   | Especializacion   |");
            System.out.println("+-------------+--------------------------+-------------------+");
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                String especializacion = resultSet.getString("especializacion");
                System.out.printf("|%-13s|%-24s|%-24s|\n", id,nombre,especializacion);
                System.out.println("+-------------+--------------------------+-------------------+");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean getDoctorById(int id) {
        String query =  "SELECT * FROM doctores WHERE id = ?";
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

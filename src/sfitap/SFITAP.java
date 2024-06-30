package sfitap;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SFITAP {

    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/db_sfi_tap";
    private static final String  DB_USER= "capiedrahita1";
    private static final String DB_PASSWORD = "C@ps*7414";
    private static final Logger LOGGER = Logger.getLogger(SFITAP.class.getName());

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    //Agregar usuario a la bd
    
    public void AgregarUsuario(String username, String password, String firstName, String lastName, String id, String birthDate, String role) {
        String sql = "INSERT INTO usuarios (nombre_de_usuario, contraseña, nombre, apellido, cedula, fecha_de_nacimiento, rol) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, firstName);
            pstmt.setString(4, lastName);
            pstmt.setString(5, id);
            pstmt.setString(6, birthDate);
            pstmt.setString(7, role);
            pstmt.executeUpdate();
            System.out.println("Usuario insertado correctamente.");
        } catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println("Usuario ya existe, no se puede insertar.");
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    //Actualizar usuario de la bd
    
    public void ActualizarUsuario(int userId, String username, String password, String firstName, String lastName, String id, String birthDate, String role) {
        String sql = "UPDATE usuarios SET nombre_de_usuario = ?, contraseña = ?, nombre = ?, apellido = ?, cedula = ?, fecha_de_nacimiento = ?, rol = ? WHERE usuario_id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, firstName);
            pstmt.setString(4, lastName);
            pstmt.setString(5, id);
            pstmt.setString(6, birthDate);
            pstmt.setString(7, role);
            pstmt.setInt(8, userId);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Usuario actualizado correctamente.");
            } else {
                System.out.println("No se encontró el usuario para actualizar.");
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    //Eliminar usuario de la bd
    
    public void EliminarUsuario(int userId) {
        String sql = "DELETE FROM usuarios WHERE usuario_id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Usuario eliminado correctamente.");
            } else {
                System.out.println("No se encontró el usuario para eliminar.");
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    //Muestra datos de usuarios en la bd
    
    public void ListaUsuarios() {
        String sql = "SELECT * FROM usuarios";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getInt("usuario_id") + " : " + rs.getString("nombre_de_usuario"));
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        SFITAP userManagement = new SFITAP();

        // Agregar usuario
        userManagement.AgregarUsuario("ABC", "ABC123", "JUAN", "GOMEZ", "963852741", "1994-06-08", "DFG");

        // Listar usuarios
        userManagement.ListaUsuarios();

        // Actualizar usuario
        userManagement.ActualizarUsuario(1, "DEF", "DEF456", "PEDRO", "LOPEZ", "123456789", "1990-01-01", "ADMIN");

        // Eliminar usuario
        userManagement.EliminarUsuario(8);

        // Listar usuarios nuevamente
        userManagement.ListaUsuarios();
    }
}

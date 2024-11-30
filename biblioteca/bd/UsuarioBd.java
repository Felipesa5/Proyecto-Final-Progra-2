package biblioteca.bd;

import biblioteca.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioBd {

    public boolean crearUsuario(Connection connection, Usuario usuario) throws SQLException {
        String query = "INSERT INTO usuarios (nombre, email) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            return stmt.executeUpdate() > 0;
        }
    }

    public Usuario buscarUsuarioPorNombre(Connection connection, String nombre) throws SQLException {
        String query = "SELECT * FROM usuarios WHERE nombre = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nombre);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getString("email"));
                }
            }
        }
        return null;
    }

    public List<Usuario> obtenerUsuarios(Connection connection) throws SQLException {
        String query = "SELECT * FROM usuarios";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            List<Usuario> usuarios = new ArrayList<>();
            while (rs.next()) {
                usuarios.add(new Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getString("email")));
            }
            return usuarios;
        }
    }
}

package biblioteca.bd;

import biblioteca.modelo.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibroBd {

    public boolean crearLibro(Connection connection, Libro libro) throws SQLException {
        String query = "INSERT INTO libros (titulo, autor, disponible) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setBoolean(3, libro.isDisponible());
            return stmt.executeUpdate() > 0;
        }
    }

    public Libro buscarLibroDisponible(Connection connection, String titulo) throws SQLException {
        String query = "SELECT * FROM libros WHERE titulo = ? AND disponible = TRUE";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, titulo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Libro(rs.getInt("id"), rs.getString("titulo"), rs.getString("autor"), rs.getBoolean("disponible"));
                }
            }
        }
        return null;
    }

    public Libro buscarLibroPorTitulo(Connection connection, String titulo) throws SQLException {
        String query = "SELECT * FROM libros WHERE titulo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, titulo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Libro(rs.getInt("id"), rs.getString("titulo"), rs.getString("autor"), rs.getBoolean("disponible"));
                }
            }
        }
        return null;
    }

    public void actualizarDisponibilidad(Connection connection, int libroId, boolean disponible) throws SQLException {
        String query = "UPDATE libros SET disponible = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setBoolean(1, disponible);
            stmt.setInt(2, libroId);
            stmt.executeUpdate();
        }
    }

    public List<Libro> obtenerLibrosDisponibles(Connection connection) throws SQLException {
        String query = "SELECT * FROM libros WHERE disponible = TRUE";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            List<Libro> libros = new ArrayList<>();
            while (rs.next()) {
                libros.add(new Libro(rs.getInt("id"), rs.getString("titulo"), rs.getString("autor"), rs.getBoolean("disponible")));
            }
            return libros;
        }
    }
}

package biblioteca.bd;

import biblioteca.modelo.Libro;
import biblioteca.modelo.Prestamo;
import biblioteca.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrestamoBd {

    public boolean registrarPrestamo(Connection connection, Prestamo prestamo) throws SQLException {
        String query = """
            INSERT INTO prestamos (usuario_id, libro_id, fecha_prestamo)
            VALUES (?, ?, CURRENT_DATE)
        """;
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, prestamo.getUsuario().getId());
            stmt.setInt(2, prestamo.getLibro().getId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean devolverPrestamo(Connection connection, int libroId) throws SQLException {
        String query = """
            UPDATE prestamos
            SET fecha_devolucion = CURRENT_DATE
            WHERE libro_id = ? AND fecha_devolucion IS NULL
        """;
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, libroId);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Prestamo> obtenerPrestamos(Connection connection) throws SQLException {
        String query = """
            SELECT p.id AS prestamo_id, p.fecha_prestamo, p.fecha_devolucion, 
                   l.id AS libro_id, l.titulo, l.autor, l.disponible,
                   u.id AS usuario_id, u.nombre, u.email
            FROM prestamos p
            INNER JOIN libros l ON p.libro_id = l.id
            INNER JOIN usuarios u ON p.usuario_id = u.id
        """;
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            List<Prestamo> prestamos = new ArrayList<>();
            while (rs.next()) {
                Libro libro = new Libro(rs.getInt("libro_id"), rs.getString("titulo"), rs.getString("autor"), rs.getBoolean("disponible"));
                Usuario usuario = new Usuario(rs.getInt("usuario_id"), rs.getString("nombre"), rs.getString("email"));
                Prestamo prestamo = new Prestamo(rs.getInt("prestamo_id"), libro, usuario, rs.getDate("fecha_prestamo"), rs.getDate("fecha_devolucion"));
                prestamos.add(prestamo);
            }
            return prestamos;
        }
    }
}

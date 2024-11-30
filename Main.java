import biblioteca.servicio.BibliotecaServicio;
import consulta.ConexionBd;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = ConexionBd.getConnection()) {
            BibliotecaServicio servicio = new BibliotecaServicio();
            while (true) {
                String opcion = JOptionPane.showInputDialog(
                        "Que deseas hacer hoy la biblioteca Sánchez 📖:\n" +
                        "1. Registrar usuario\n" +
                        "2. Registrar libro\n" +
                        "3. Prestar libro\n" +
                        "4. Devolver libro\n" +
                        "5. Ver usuarios\n" +
                        "6. Ver libros disponibles\n" +
                        "7. Ver libros prestados\n" +
                        "8. Salir"
                );
                if (opcion == null || opcion.trim().isEmpty()) continue;

                switch (opcion) {
                    case "1" -> servicio.crearUsuario(connection);
                    case "2" -> servicio.crearLibro(connection);
                    case "3" -> servicio.registrarPrestamo(connection);
                    case "4" -> servicio.devolverLibro(connection);
                    case "5" -> servicio.verUsuarios(connection);
                    case "6" -> servicio.verLibrosDisponibles(connection);
                    case "7" -> servicio.verLibrosPrestados(connection);
                    case "8" -> {
                        JOptionPane.showMessageDialog(null, "¡Hasta pronto!");
                        return;
                    }
                    default -> JOptionPane.showMessageDialog(null, "Opción no válida.");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la conexión: " + e.getMessage());
        }
    }
}

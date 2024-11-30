package biblioteca.servicio;

import biblioteca.bd.LibroBd;
import biblioteca.bd.PrestamoBd;
import biblioteca.bd.UsuarioBd;
import biblioteca.datos.CapturaDatos;
import biblioteca.modelo.Libro;
import biblioteca.modelo.Prestamo;
import biblioteca.modelo.Usuario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BibliotecaServicio {
    private final UsuarioBd usuarioDAO;
    private final LibroBd libroDAO;
    private final PrestamoBd prestamoDAO;
    private final CapturaDatos captura;

    public BibliotecaServicio() {
        this.usuarioDAO = new UsuarioBd();
        this.libroDAO = new LibroBd();
        this.prestamoDAO = new PrestamoBd();
        this.captura = new CapturaDatos();
    }

    // Crear usuario
    public void crearUsuario(Connection connection) throws SQLException {
        String nombre = captura.captura("Ingrese el nombre del usuario:");
        String email = captura.captura("Ingrese el email del usuario:");
        Usuario usuario = new Usuario(0, nombre, email);
        if (usuarioDAO.crearUsuario(connection, usuario)) {
            captura.mensajeDialogo("Usuario creado exitosamente.");
        }
    }

    // Crear libro
    public void crearLibro(Connection connection) throws SQLException {
        String titulo = captura.captura("Ingrese el título del libro:");
        String autor = captura.captura("Ingrese el autor del libro:");
        Libro libro = new Libro(0, titulo, autor, true);
        if (libroDAO.crearLibro(connection, libro)) {
            captura.mensajeDialogo("Libro creado exitosamente.");
        }
    }

    // Registrar préstamo
    public void registrarPrestamo(Connection connection) throws SQLException {
        String usuarioNombre = captura.captura("Ingrese el nombre del usuario:");
        Usuario usuario = usuarioDAO.buscarUsuarioPorNombre(connection, usuarioNombre);
        if (usuario == null) {
            captura.mensajeDialogo("El usuario no existe. Regístrelo primero.");
            return;
        }

        String tituloLibro = captura.captura("Ingrese el título del libro:");
        Libro libro = libroDAO.buscarLibroDisponible(connection, tituloLibro);
        if (libro == null) {
            captura.mensajeDialogo("El libro no está disponible o no existe.");
            return;
        }

        Prestamo prestamo = new Prestamo(0, libro, usuario, null, null);
        if (prestamoDAO.registrarPrestamo(connection, prestamo)) {
            libroDAO.actualizarDisponibilidad(connection, libro.getId(), false);
            captura.mensajeDialogo("¡Préstamo registrado con éxito!");
        }
    }

    // Devolver libro
    public void devolverLibro(Connection connection) throws SQLException {
        String tituloLibro = captura.captura("Ingrese el título del libro a devolver:");
        Libro libro = libroDAO.buscarLibroPorTitulo(connection, tituloLibro);
        if (libro == null || libro.isDisponible()) {
            captura.mensajeDialogo("El libro no está prestado.");
            return;
        }

        if (prestamoDAO.devolverPrestamo(connection, libro.getId())) {
            libroDAO.actualizarDisponibilidad(connection, libro.getId(), true);
            captura.mensajeDialogo("¡Libro devuelto exitosamente!");
        } else {
            captura.mensajeDialogo("Error al devolver el libro.");
        }
    }

    // Ver usuarios
    public void verUsuarios(Connection connection) throws SQLException {
        List<Usuario> usuarios = usuarioDAO.obtenerUsuarios(connection);
        if (usuarios.isEmpty()) {
            captura.mensajeDialogo("No hay usuarios registrados.");
            return;
        }
        StringBuilder sb = new StringBuilder("Usuarios registrados:\n");
        for (Usuario usuario : usuarios) {
            sb.append("- ").append(usuario.getNombre()).append(" (").append(usuario.getEmail()).append(")\n");
        }
        captura.mensajeDialogo(sb.toString());
    }

    // Ver libros disponibles
    public void verLibrosDisponibles(Connection connection) throws SQLException {
        List<Libro> libros = libroDAO.obtenerLibrosDisponibles(connection);
        if (libros.isEmpty()) {
            captura.mensajeDialogo("No hay libros disponibles.");
            return;
        }
        StringBuilder sb = new StringBuilder("Libros disponibles:\n");
        for (Libro libro : libros) {
            sb.append("- ").append(libro.getTitulo()).append(" (").append(libro.getAutor()).append(")\n");
        }
        captura.mensajeDialogo(sb.toString());
    }

    // Ver libros prestados
    public void verLibrosPrestados(Connection connection) throws SQLException {
        List<Prestamo> prestamos = prestamoDAO.obtenerPrestamos(connection);
        if (prestamos.isEmpty()) {
            captura.mensajeDialogo("No hay libros prestados.");
            return;
        }
        StringBuilder sb = new StringBuilder("Libros prestados:\n");
        for (Prestamo prestamo : prestamos) {
            sb.append("- Libro: ").append(prestamo.getLibro().getTitulo())
              .append(", Usuario: ").append(prestamo.getUsuario().getNombre())
              .append(", Fecha préstamo: ").append(prestamo.getFechaPrestamo()).append("\n");
        }
        captura.mensajeDialogo(sb.toString());
    }
}

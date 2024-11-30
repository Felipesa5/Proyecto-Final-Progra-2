package biblioteca.modelo;

import java.util.Date;

public class Prestamo {
    private int id;
    private Libro libro;
    private Usuario usuario;
    private Date fechaPrestamo;
    private Date fechaDevolucion;

    public Prestamo(int id, Libro libro, Usuario usuario, Date fechaPrestamo, Date fechaDevolucion) {
        this.id = id;
        this.libro = libro;
        this.usuario = usuario;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
    }

    public int getId() { return id; }
    public Libro getLibro() { return libro; }
    public Usuario getUsuario() { return usuario; }
    public Date getFechaPrestamo() { return fechaPrestamo; }
    public Date getFechaDevolucion() { return fechaDevolucion; }
}

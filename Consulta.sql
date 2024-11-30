CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE libros (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    disponible BOOLEAN DEFAULT TRUE
);

CREATE TABLE prestamos (
    id SERIAL PRIMARY KEY,
    usuario_id INT REFERENCES usuarios(id) ON DELETE CASCADE,
    libro_id INT REFERENCES libros(id) ON DELETE CASCADE,
    fecha_prestamo DATE NOT NULL DEFAULT CURRENT_DATE,
    fecha_devolucion DATE
);

INSERT INTO usuarios (nombre, email) VALUES 
('Andres Sanchez', 'morenofelipe513@gmail.com'),
('Johana Moreno', 'johanaMoreno@gmail.com'),
('Alvaro Sanchez', 'sanchezalvaro@gmail.com');

INSERT INTO libros (titulo, autor) VALUES 
('El Quijote', 'Miguel de Cervantes'),
('Cien Años de Soledad', 'Gabriel García Márquez'),
('1984', 'George Orwell'),
('El Principito', 'Antoine de Saint-Exupéry'),
('Crimen y Castigo', 'Fiódor Dostoyevski');
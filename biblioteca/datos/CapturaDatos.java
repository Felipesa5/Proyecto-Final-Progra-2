package biblioteca.datos;

import javax.swing.JOptionPane;

public class CapturaDatos {

    public String captura(String mensaje) {
        String valor = JOptionPane.showInputDialog(mensaje);
        while (valor == null || valor.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor ingrese un valor válido.");
            valor = JOptionPane.showInputDialog(mensaje);
        }
        return valor.trim();
    }

    public void mensajeDialogo(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }
}

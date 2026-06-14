package vista;

import dao.SerieDAO;
import modelo.Serie;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private JTextField txtInicio;

    private JTextArea txtResultado;

    private JButton btnCalcular;
    private JButton btnGuardar;

    private Serie serieActual;

    public VentanaPrincipal() {

        setTitle(
                "Serie de 6 en 6 - Oracle 19c"
        );

        setSize(700,500);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {

        JPanel superior =
                new JPanel();

        superior.add(
                new JLabel(
                        "Número Inicial:"
                )
        );

        txtInicio =
                new JTextField(10);

        superior.add(txtInicio);

        btnCalcular =
                new JButton("Calcular");

        superior.add(btnCalcular);

        btnGuardar =
                new JButton("Guardar");

        superior.add(btnGuardar);

        add(
                superior,
                BorderLayout.NORTH
        );

        txtResultado =
                new JTextArea();

        add(
                new JScrollPane(
                        txtResultado
                ),
                BorderLayout.CENTER
        );

        btnCalcular.addActionListener(
                e -> calcularSerie()
        );

        btnGuardar.addActionListener(
                e -> guardar()
        );
    }

    private void calcularSerie() {

        try {

            int inicio =
                    Integer.parseInt(
                            txtInicio.getText()
                    );

            StringBuilder serieTexto =
                    new StringBuilder();

            int suma = 0;

            int valor = inicio;

            for(int i=1; i<=26; i++) {

                serieTexto.append(valor);

                if(i < 26)
                    serieTexto.append(" - ");

                suma += valor;

                valor += 6;
            }

            txtResultado.setText("");

            txtResultado.append(
                    "SERIE GENERADA\n\n"
            );

            txtResultado.append(
                    serieTexto.toString()
            );

            txtResultado.append(
                    "\n\nSUMA TOTAL = "
                            + suma
            );

            serieActual =
                    new Serie(
                            inicio,
                            26,
                            serieTexto.toString(),
                            suma
                    );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese un número válido"
            );
        }
    }

    private void guardar() {

        if(serieActual == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Primero calcule la serie"
            );

            return;
        }

        SerieDAO dao =
                new SerieDAO();

        boolean resultado =
                dao.guardar(
                        serieActual
                );

        if(resultado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Registro almacenado"
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al guardar"
            );
        }
    }
}   
# -_sistema_de_serie_numerica_de_6_en_6_almacenamiento_oracle_- :.
# Proyecto Java 21 + IntelliJ IDEA + Swing + Oracle Database 19c:

<img width="1024" height="1024" alt="image" src="https://github.com/user-attachments/assets/cbb21ab9-af23-4e2d-9e70-df3d2f46ffac" />    

<img width="2545" height="1079" alt="image" src="https://github.com/user-attachments/assets/ed56510b-9514-4e8b-b42d-b51bf463e58f" />    

```

## Sistema de Serie Numérica de 6 en 6 con Almacenamiento en Oracle

Aplicación desarrollada en **Java 21**, **Swing** y **Oracle Database 19c** que permite:

- Ingresar un número inicial.
- Calcular una serie sumando de 6 en 6.
- Generar hasta 26 números de la serie.
- Calcular la suma total de la serie.
- Mostrar resultados en una interfaz gráfica.
- Guardar cálculos en Oracle 19c.
- Consultar el historial almacenado.

---

# 1. Base de Datos Oracle 19c

## Crear Tabla

```sql
CREATE TABLE SERIE_SEIS (
    ID NUMBER GENERATED ALWAYS AS IDENTITY,
    FECHA_REGISTRO DATE DEFAULT SYSDATE,
    NUMERO_INICIAL NUMBER,
    CANTIDAD_TERMINOS NUMBER,
    SERIE_GENERADA VARCHAR2(4000),
    SUMA_TOTAL NUMBER,
    CONSTRAINT PK_SERIE_SEIS PRIMARY KEY(ID)
);
```

---

# 2. Estructura del Proyecto

```text
SerieDeSeisOracle/

├── src/
│   ├── conexion/
│   │     ConexionOracle.java
│   │
│   ├── modelo/
│   │     Serie.java
│   │
│   ├── dao/
│   │     SerieDAO.java
│   │
│   ├── vista/
│   │     VentanaPrincipal.java
│   │
│   └── Main.java
│
└── ojdbc11.jar
```

---

# 3. Clase de Conexión Oracle

## ConexionOracle.java

```java
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionOracle {

    private static final String URL =
            "jdbc:oracle:thin:@localhost:1521:XE";

    private static final String USER = "SYSTEM";
    private static final String PASSWORD = "oracle";

    public static Connection conectar() {

        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

            return DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

        } catch (Exception e) {

            System.out.println(
                    "Error de conexión: "
                            + e.getMessage()
            );

            return null;
        }
    }
}
```

---

# 4. Modelo

## Serie.java

```java
package modelo;

public class Serie {

    private int numeroInicial;
    private int cantidadTerminos;
    private String serieGenerada;
    private int sumaTotal;

    public Serie() {
    }

    public Serie(
            int numeroInicial,
            int cantidadTerminos,
            String serieGenerada,
            int sumaTotal) {

        this.numeroInicial = numeroInicial;
        this.cantidadTerminos = cantidadTerminos;
        this.serieGenerada = serieGenerada;
        this.sumaTotal = sumaTotal;
    }

    public int getNumeroInicial() {
        return numeroInicial;
    }

    public void setNumeroInicial(int numeroInicial) {
        this.numeroInicial = numeroInicial;
    }

    public int getCantidadTerminos() {
        return cantidadTerminos;
    }

    public void setCantidadTerminos(int cantidadTerminos) {
        this.cantidadTerminos = cantidadTerminos;
    }

    public String getSerieGenerada() {
        return serieGenerada;
    }

    public void setSerieGenerada(String serieGenerada) {
        this.serieGenerada = serieGenerada;
    }

    public int getSumaTotal() {
        return sumaTotal;
    }

    public void setSumaTotal(int sumaTotal) {
        this.sumaTotal = sumaTotal;
    }
}
```

---

# 5. DAO

## SerieDAO.java

```java
package dao;

import conexion.ConexionOracle;
import modelo.Serie;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SerieDAO {

    public boolean guardar(Serie serie) {

        String sql =
                "INSERT INTO SERIE_SEIS " +
                "(NUMERO_INICIAL," +
                "CANTIDAD_TERMINOS," +
                "SERIE_GENERADA," +
                "SUMA_TOTAL) " +
                "VALUES (?,?,?,?)";

        try (
                Connection con =
                        ConexionOracle.conectar();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    serie.getNumeroInicial()
            );

            ps.setInt(
                    2,
                    serie.getCantidadTerminos()
            );

            ps.setString(
                    3,
                    serie.getSerieGenerada()
            );

            ps.setInt(
                    4,
                    serie.getSumaTotal()
            );

            ps.executeUpdate();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
}
```

---

# 6. Interfaz Gráfica Swing

## VentanaPrincipal.java

```java
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
```

---

# 7. Clase Principal

## Main.java

```java
import vista.VentanaPrincipal;

public class Main {

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(
                () -> {

                    new VentanaPrincipal()
                            .setVisible(true);

                }
        );
    }
}
```

---

# 8. Ejemplo de Procesamiento

## Entrada

```text
1
```

## Serie Generada

```text
1
7
13
19
25
31
37
43
49
55
61
67
73
79
85
91
97
103
109
115
121
127
133
139
145
151
```

## Suma Total

```text
1976
```

---

# 9. Registros Almacenados en Oracle

## Consulta

```sql
SELECT *
FROM SERIE_SEIS;
```

## Resultado

| ID | INICIO | TERMINOS | SUMA |
|----|---------|-----------|------|
| 1 | 1 | 26 | 1976 |
| 2 | 5 | 26 | 2080 |
| 3 | 10 | 26 | 2210 |

---

# 10. Mejoras Recomendadas

- Agregar JTable para consultar historial.
- Botón eliminar registros.
- Botón exportar a Excel.
- Generar reporte PDF.
- Implementar patrón MVC completo.
- Utilizar Maven para la gestión de dependencias.
- Integrar Oracle JDBC mediante Maven.
- Agregar gráficas estadísticas.
- Implementar CRUD completo:
  - Crear
  - Consultar
  - Actualizar
  - Eliminar

---

# Tecnologías Utilizadas

| Tecnología | Versión |
|------------|----------|
| Java | 21 |
| IntelliJ IDEA | 2025 |
| Swing | Incluido en JDK |
| Oracle Database | 19c |
| JDBC Oracle | ojdbc11 |

---

# Compatibilidad

Este proyecto es totalmente compatible con:

- Java 21
- IntelliJ IDEA 2025
- Swing
- Oracle Database 19c
- Oracle JDBC Driver (ojdbc11.jar)

---

# Autor

Proyecto académico desarrollado para el cálculo y almacenamiento de series numéricas incrementadas de 6 en 6 utilizando Java Swing y Oracle Database 19c .
:. . / .

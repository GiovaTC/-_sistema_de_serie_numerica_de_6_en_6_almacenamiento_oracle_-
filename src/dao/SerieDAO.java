package dao;

import conexion.ConexionOracle;
import modelo.Serie;
import oracle.jdbc.proxy.annotation.Pre;

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

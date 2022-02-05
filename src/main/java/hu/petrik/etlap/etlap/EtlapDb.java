package hu.petrik.etlap.etlap;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtlapDb {
    Connection connection;

    public EtlapDb() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/etlapdb", "root", "");

    }

    public List<Etlap> getEtlap() throws SQLException {
        List<Etlap> etlapLista = new ArrayList<>();
        Statement stmt = connection.createStatement();
        String sql = "SELECT * FROM etlap ";
        ResultSet resultSet = stmt.executeQuery(sql);
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nev = resultSet.getString("nev");
            String leiras = resultSet.getString("leiras");
            int ar = resultSet.getInt("ar");
            String kategoria = resultSet.getString("kategoria");
            Etlap etlap = new Etlap(id, nev, leiras, ar, kategoria);
            etlapLista.add(etlap);

        }

        return etlapLista;

    }
}

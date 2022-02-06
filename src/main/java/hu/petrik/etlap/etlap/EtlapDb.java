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

    public boolean etlapTorles(int id) throws SQLException {
        String sql = "DELETE FROM etlap WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        int erintettek = stmt.executeUpdate();
        return erintettek == 1;



    }

    public int etlapHozzaadas(String nev, String leiras, int ar, String kategoria) throws SQLException {
        String sql = "INSERT INTO etlap(nev, leiras, ar, kategoria) VALUES ?,?,?,?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, nev);
        stmt.setString(2, leiras);
        stmt.setInt(3, ar);
        stmt.setString(4, kategoria);
        return stmt.executeUpdate();

    }

    public boolean etlapModositasa(Etlap modositando) throws SQLException {
        String sql = "UPDATE etlap SET nev = ?," + "leiras = ?," + "ar = ?," + "kategoria = ? " +
                "WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, modositando.getNev());
        stmt.setString(2, modositando.getLeiras());
        stmt.setInt(3, modositando.getAr());
        stmt.setString(4, modositando.getKategoria());
        stmt.setInt(5, modositando.getId());
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }
}

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
        String sql = "SELECT * FROM etlap INNER JOIN kategoria ON (etlap.kategoria_id = kategoria.id)";
        ResultSet resultSet = stmt.executeQuery(sql);
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nev = resultSet.getString("nev");
            String leiras = resultSet.getString("leiras");
            int ar = resultSet.getInt("ar");
            String kategoria = resultSet.getString("kategoria.nev");
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

    public List<Etlap> etlapSzurve(String kategoriaSzuro) throws SQLException {
        List<Etlap> szurtEtlapLista = new ArrayList<>();
        String sql = "SELECT * FROM etlap INNER JOIN kategoria ON (etlap.kategoria_id = kategoria.id) WHERE kategoria.nev = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, kategoriaSzuro);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nev = resultSet.getString("nev");
            String leiras = resultSet.getString("leiras");
            int ar = resultSet.getInt("ar");
            String kategoria = resultSet.getString("kategoria.nev");
            Etlap etlap = new Etlap(id, nev, leiras, ar, kategoria);
            szurtEtlapLista.add(etlap);

        }
        return szurtEtlapLista;
    }

    public int etlapHozzaadasa(String nev, String leiras, int kategoria, int ar) throws SQLException {
        String sql="INSERT INTO etlap (nev,leiras,kategoria_id,ar) VALUES(?,?,?,?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1,nev);
        stmt.setString(2,leiras);
        stmt.setInt(3,kategoria);
        stmt.setInt(4,ar);
        return stmt.executeUpdate();
    }


    public boolean etlapModositasa(Etlap modositandoEtlap) throws SQLException {
        String sql = "UPDATE etlap SET nev = ?," + "leiras = ?," + "ar = ?," + "kategoria = ? " +
                "WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, modositandoEtlap.getNev());
        stmt.setString(2, modositandoEtlap.getLeiras());
        stmt.setInt(3, modositandoEtlap.getAr());
        stmt.setString(4, modositandoEtlap.getKategoria());
        stmt.setInt(5, modositandoEtlap.getId());
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }

    public boolean etlapArEmelesForinttal(int id, int ertek) throws SQLException {
        String sql = "UPDATE etlap SET ar = ar + ? WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, ertek);
        stmt.setInt(2, id);
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }

    public boolean etlapArEmelesForinttalOsszes(int ertek) throws SQLException {
        String sql = "UPDATE etlap SET ar = ar + ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, ertek);
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }

    public boolean etlapArEmelesSzazalekkal(int id, int ertek) throws SQLException {
        String sql = "UPDATE etlap SET ar = ar * (1 + ? / 100)  WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, ertek);
        stmt.setInt(2, id);
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }

    public boolean etlapArEmelesSzazalekkalOsszes(int ertek) throws SQLException {
        String sql = "UPDATE etlap SET ar = ar * (1 + ? / 100)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, ertek);
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }

    public List<Kategoria> getKategoria() throws SQLException {
        List<Kategoria> kategoriaList = new ArrayList<>();
        Statement stmt = connection.createStatement();
        String sql = "SELECT * FROM kategoria ORDER BY id";
        ResultSet resultSet = stmt.executeQuery(sql);
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nev = resultSet.getString("nev");
            Kategoria kategoria = new Kategoria(id, nev);
            kategoriaList.add(kategoria);

        }

        return kategoriaList;

    }

    public boolean kategoriaTorles(int id) throws SQLException {
        String sql = "DELETE FROM kategoria WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        int erintettek = stmt.executeUpdate();
        return erintettek == 1;

    }

    public int kategoriaHozzaadasa(String nev) throws SQLException {
        String sql="INSERT INTO kategoria(nev) VALUES(?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1,nev);
        return stmt.executeUpdate();
    }

    public boolean kategoriaModositasa(Kategoria modositandoKategoria) throws SQLException {
        String sql = "UPDATE etlap SET nev = ?," +
                "WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, modositandoKategoria.getNev());
        stmt.setInt(2, modositandoKategoria.getId());
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }
}

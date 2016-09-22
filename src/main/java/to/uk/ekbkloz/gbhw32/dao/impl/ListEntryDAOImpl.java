package to.uk.ekbkloz.gbhw32.dao.impl;

import to.uk.ekbkloz.gbhw32.dao.ListEntryDAO;
import to.uk.ekbkloz.gbhw32.model.ListEntry;

import javax.persistence.Table;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by KlozK on 18.09.2016.
 */
public class ListEntryDAOImpl implements ListEntryDAO {
    private Connection conn = null;

    @Override
    public void add(ListEntry listEntry) throws SQLException {
        add(Arrays.asList(listEntry));
    }

    @Override
    public void add(List<ListEntry> listEntries) throws SQLException {
        if (conn != null) {
            PreparedStatement prepStmnt = conn.prepareStatement("insert INTO  " + ListEntry.class.getAnnotation(Table.class).name()
                    + " (Группа1, Группа2, Группа3, Группа4, Группа5, Товар, Код, Артикул, ПолнНазв, РознЦена) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            for (ListEntry entry : listEntries) {
                prepStmnt.setString(1, entry.getGroup1());
                prepStmnt.setString(2, entry.getGroup2());
                prepStmnt.setString(3, entry.getGroup3());
                prepStmnt.setString(4, entry.getGroup4());
                prepStmnt.setString(5, entry.getGroup5());
                prepStmnt.setString(6, entry.getItem());
                prepStmnt.setLong  (7, entry.getCode());
                prepStmnt.setString(8, entry.getVendorCode());
                prepStmnt.setString(9, entry.getFullName());
                prepStmnt.setInt  (10, entry.getPrice());
                prepStmnt.addBatch();
            }
            prepStmnt.executeBatch();
            conn.commit();
        }
    }

    @Override
    public void update(ListEntry listEntry) throws SQLException {
        update(Arrays.asList(listEntry));
    }

    @Override
    public void update(List<ListEntry> listEntries) throws SQLException {
        if (conn != null) {
            PreparedStatement prepStmnt = conn.prepareStatement("UPDATE " + ListEntry.class.getAnnotation(Table.class).name()
                    + " SET Группа1 = ?, Группа2 = ?, Группа3 = ?, Группа4 = ?, Группа5 = ?, Товар = ?, Артикул = ?, ПолнНазв = ?, РознЦена = ? WHERE Код = ?");
            for (ListEntry entry : listEntries) {
                prepStmnt.setString(1, entry.getGroup1());
                prepStmnt.setString(2, entry.getGroup2());
                prepStmnt.setString(3, entry.getGroup3());
                prepStmnt.setString(4, entry.getGroup4());
                prepStmnt.setString(5, entry.getGroup5());
                prepStmnt.setString(6, entry.getItem());
                prepStmnt.setString(7, entry.getVendorCode());
                prepStmnt.setString(8, entry.getFullName());
                prepStmnt.setInt   (9, entry.getPrice());
                prepStmnt.setLong (10, entry.getCode());
                prepStmnt.addBatch();
            }
            prepStmnt.executeBatch();
            conn.commit();
        }
    }

    @Override
    public ArrayList<ListEntry> getAll() throws SQLException {
        ArrayList<ListEntry> listEntries = new ArrayList<ListEntry>();
        if (conn != null) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + ListEntry.class.getAnnotation(Table.class).name());
            while(rs.next()) {
                ListEntry entry = new ListEntry();
                entry.setGroup1(rs.getString("Группа1"));
                entry.setGroup2(rs.getString("Группа2"));
                entry.setGroup3(rs.getString("Группа3"));
                entry.setGroup4(rs.getString("Группа4"));
                entry.setGroup5(rs.getString("Группа5"));
                entry.setItem(rs.getString("Товар"));
                entry.setCode(rs.getLong("Код"));
                entry.setVendorCode(rs.getString("Артикул"));
                entry.setFullName(rs.getString("ПолнНазв"));
                entry.setPrice(rs.getInt("РознЦена"));
                listEntries.add(entry);
            }
        }
        return listEntries;
    }

    @Override
    public ListEntry getByCode(Long code) throws SQLException {
        ListEntry entry = null;
        if (conn != null) {
            PreparedStatement prepStmnt = conn.prepareStatement("SELECT * FROM " + ListEntry.class.getAnnotation(Table.class).name() + " WHERE Код = ?");
            prepStmnt.setLong (1, code);
            ResultSet rs = prepStmnt.executeQuery();
            entry = new ListEntry();
            entry.setGroup1(rs.getString("Группа1"));
            entry.setGroup2(rs.getString("Группа2"));
            entry.setGroup3(rs.getString("Группа3"));
            entry.setGroup4(rs.getString("Группа4"));
            entry.setGroup5(rs.getString("Группа5"));
            entry.setItem(rs.getString("Товар"));
            entry.setCode(code);
            entry.setVendorCode(rs.getString("Артикул"));
            entry.setFullName(rs.getString("ПолнНазв"));
            entry.setPrice(rs.getInt("РознЦена"));
        }
        return entry;
    }

    @Override
    public void openConnection() throws SQLException, ClassNotFoundException {
        if (conn == null || conn.isClosed()) {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/gbhw32.db");
            conn.setAutoCommit(false);
        }
    }

    @Override
    public void closeConnection() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    @Override
    public Integer getRowsCount() throws SQLException {
        Integer count = 0;
        if (conn != null) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as cnt FROM " + ListEntry.class.getAnnotation(Table.class).name());
            count = rs.getInt("cnt");
        }
        return count;
    }
}

package to.uk.ekbkloz.gbhw32.dao;

import to.uk.ekbkloz.gbhw32.model.ListEntry;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KlozK on 18.09.2016.
 */
public interface ListEntryDAO {
    public void add(ListEntry listEntry) throws SQLException;
    public void add(List<ListEntry> listEntries) throws SQLException;
    public void update(ListEntry listEntry) throws SQLException;
    public void update(List<ListEntry> listEntries) throws SQLException;
    public ArrayList<ListEntry> getAll() throws SQLException;
    public ListEntry getByCode(Long code) throws SQLException;
    public void openConnection() throws SQLException, ClassNotFoundException;
    public void closeConnection() throws SQLException;
    public Integer getRowsCount() throws SQLException;
}

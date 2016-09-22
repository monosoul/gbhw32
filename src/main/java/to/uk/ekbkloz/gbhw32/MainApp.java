package to.uk.ekbkloz.gbhw32;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import to.uk.ekbkloz.gbhw32.dao.ListEntryDAO;
import to.uk.ekbkloz.gbhw32.dao.impl.ListEntryDAOImpl;
import to.uk.ekbkloz.gbhw32.model.ListEntry;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by KlozK on 18.09.2016.
 */
public class MainApp {
    public static void main(String[] args) throws IOException, SQLException {
        try(Reader in = new FileReader("src/main/resources/List.csv")){
            List<CSVRecord> records = CSVFormat.RFC4180.withDelimiter('\t').withIgnoreEmptyLines().withIgnoreSurroundingSpaces().withFirstRecordAsHeader().parse(in).getRecords();
            ListEntryDAO dao = new ListEntryDAOImpl();
            try {
                dao.openConnection();
                Integer rowsCount = dao.getRowsCount();
                ArrayList<ListEntry> listEntriesInCSV = ListEntry.getFromCsvRecord(records);
                if (rowsCount == 0) {
                    dao.add(listEntriesInCSV);
                }
                else {
                    Map<Long, ListEntry> codeToLEmap = dao.getAll().parallelStream().collect(Collectors.toConcurrentMap(le -> le.getCode(), le -> le));
                    List<ListEntry> toUpdate = Collections.synchronizedList(new ArrayList<ListEntry>());
                    List<ListEntry> toAdd = Collections.synchronizedList(new ArrayList<ListEntry>());
                    listEntriesInCSV.parallelStream().forEach(le -> {
                        if (codeToLEmap.containsKey(le.getCode())) {
                            if (!codeToLEmap.get(le.getCode()).equals(le)) {
                                toUpdate.add(le);
                            }
                        }
                        else {
                            toAdd.add(le);
                        }
                    });
                    if (!toUpdate.isEmpty()) {
                        dao.update(toUpdate);
                    }
                    if (!toAdd.isEmpty()) {
                        dao.add(toAdd);
                    }
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                dao.closeConnection();
            }
        }
    }

}

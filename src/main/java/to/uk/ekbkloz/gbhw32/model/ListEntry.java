package to.uk.ekbkloz.gbhw32.model;

import org.apache.commons.csv.CSVRecord;
import to.uk.ekbkloz.gbhw32.util.Converter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * Created by KlozK on 18.09.2016.
 */
@Entity
@Table(name = "List")
public class ListEntry {
    /**
     * Группа1
     */
    private String group1;
    /**
     * Группа2
     */
    private String group2;
    /**
     * Группа3
     */
    private String group3;
    /**
     * Группа4
     */
    private String group4;
    /**
     * Группа5
     */
    private String group5;
    /**
     * Товар
     */
    private String item;
    /**
     * Код
     */
    private Long code;
    /**
     * Артикул
     */
    private String vendorCode;
    /**
     * ПолнНазв
     */
    private String fullName;
    /**
     * РознЦена
     */
    private Integer price;


    public ListEntry() {
    }

    @Column(name = "Группа1")
    public String getGroup1() {
        return group1;
    }

    public void setGroup1(String group1) {
        this.group1 = group1;
    }

    @Column(name = "Группа2")
    public String getGroup2() {
        return group2;
    }

    public void setGroup2(String group2) {
        this.group2 = group2;
    }

    @Column(name = "Группа3")
    public String getGroup3() {
        return group3;
    }

    public void setGroup3(String group3) {
        this.group3 = group3;
    }

    @Column(name = "Группа4")
    public String getGroup4() {
        return group4;
    }

    public void setGroup4(String group4) {
        this.group4 = group4;
    }

    @Column(name = "Группа5")
    public String getGroup5() {
        return group5;
    }

    public void setGroup5(String group5) {
        this.group5 = group5;
    }

    @Column(name = "Товар")
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Column(name = "Код", nullable = false)
    @Id
    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    @Column(name = "Артикул")
    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    @Column(name = "ПолнНазв")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(name = "РознЦена")
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * Получает инстанс ListEntry из CSVRecord
     * @param record
     * @return ListEntry
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static ListEntry getFromCsvRecord(CSVRecord record) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class clazz = ListEntry.class;
        ListEntry entry = new ListEntry();
        String setterName;
        String header;
        Class fieldType;
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().startsWith("get") && !Modifier.isStatic(method.getModifiers())) {
                setterName = "set" + method.getName().substring(3);
                header = method.getAnnotation(Column.class).name();
                fieldType = method.getReturnType();
                clazz.getDeclaredMethod(setterName, fieldType).invoke(entry, Converter.fromString(record.get(header), fieldType));
            }
        }
        return entry;
    }

    public static ArrayList<ListEntry> getFromCsvRecord(Iterable<CSVRecord> records) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ArrayList<ListEntry> listEntries = new ArrayList<ListEntry>();
        for (CSVRecord record : records) {
            listEntries.add(getFromCsvRecord(record));
        }
        return listEntries;
    }

    @Override
    public String toString() {
        return "ListEntry{" +
                "group1='" + group1 + '\'' +
                ", group2='" + group2 + '\'' +
                ", group3='" + group3 + '\'' +
                ", group4='" + group4 + '\'' +
                ", group5='" + group5 + '\'' +
                ", item='" + item + '\'' +
                ", code='" + code + '\'' +
                ", vendorCode='" + vendorCode + '\'' +
                ", fullName='" + fullName + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListEntry listEntry = (ListEntry) o;

        if (getGroup1() != null ? !getGroup1().equals(listEntry.getGroup1()) : listEntry.getGroup1() != null)
            return false;
        if (getGroup2() != null ? !getGroup2().equals(listEntry.getGroup2()) : listEntry.getGroup2() != null)
            return false;
        if (getGroup3() != null ? !getGroup3().equals(listEntry.getGroup3()) : listEntry.getGroup3() != null)
            return false;
        if (getGroup4() != null ? !getGroup4().equals(listEntry.getGroup4()) : listEntry.getGroup4() != null)
            return false;
        if (getGroup5() != null ? !getGroup5().equals(listEntry.getGroup5()) : listEntry.getGroup5() != null)
            return false;
        if (getItem() != null ? !getItem().equals(listEntry.getItem()) : listEntry.getItem() != null) return false;
        if (getCode() != null ? !getCode().equals(listEntry.getCode()) : listEntry.getCode() != null) return false;
        if (getVendorCode() != null ? !getVendorCode().equals(listEntry.getVendorCode()) : listEntry.getVendorCode() != null)
            return false;
        if (getFullName() != null ? !getFullName().equals(listEntry.getFullName()) : listEntry.getFullName() != null)
            return false;
        return getPrice() != null ? getPrice().equals(listEntry.getPrice()) : listEntry.getPrice() == null;

    }

    @Override
    public int hashCode() {
        int result = getGroup1() != null ? getGroup1().hashCode() : 0;
        result = 31 * result + (getGroup2() != null ? getGroup2().hashCode() : 0);
        result = 31 * result + (getGroup3() != null ? getGroup3().hashCode() : 0);
        result = 31 * result + (getGroup4() != null ? getGroup4().hashCode() : 0);
        result = 31 * result + (getGroup5() != null ? getGroup5().hashCode() : 0);
        result = 31 * result + (getItem() != null ? getItem().hashCode() : 0);
        result = 31 * result + (getCode() != null ? getCode().hashCode() : 0);
        result = 31 * result + (getVendorCode() != null ? getVendorCode().hashCode() : 0);
        result = 31 * result + (getFullName() != null ? getFullName().hashCode() : 0);
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
        return result;
    }
}

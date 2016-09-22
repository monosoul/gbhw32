package to.uk.ekbkloz.gbhw32.util;

/**
 * Created by KlozK on 18.09.2016.
 */
public class Converter {
    public static <T> T fromString(String str, Class<T> targetType) {
        if (targetType.equals(Integer.class)) return targetType.cast(Integer.parseInt(str));
        if (targetType.equals(Long.class)) return targetType.cast(Long.parseLong(str));
        if (targetType.equals(Double.class)) return targetType.cast(Double.parseDouble(str));
        if (targetType.equals(Float.class)) return targetType.cast(Float.parseFloat(str));
        if (targetType.equals(String.class)) return targetType.cast(str);
        return null;
    }
}

package android.support.p000v4.p002os;

import com.google.code.microlog4android.format.SimpleFormatter;
import java.util.Locale;

/* renamed from: android.support.v4.os.LocaleHelper */
final class LocaleHelper {
    LocaleHelper() {
    }

    static Locale forLanguageTag(String str) {
        if (str.contains(SimpleFormatter.DEFAULT_DELIMITER)) {
            String[] split = str.split(SimpleFormatter.DEFAULT_DELIMITER);
            if (split.length > 2) {
                return new Locale(split[0], split[1], split[2]);
            }
            if (split.length > 1) {
                return new Locale(split[0], split[1]);
            }
            if (split.length == 1) {
                return new Locale(split[0]);
            }
        } else if (!str.contains("_")) {
            return new Locale(str);
        } else {
            String[] split2 = str.split("_");
            if (split2.length > 2) {
                return new Locale(split2[0], split2[1], split2[2]);
            }
            if (split2.length > 1) {
                return new Locale(split2[0], split2[1]);
            }
            if (split2.length == 1) {
                return new Locale(split2[0]);
            }
        }
        throw new IllegalArgumentException("Can not parse language tag: [" + str + "]");
    }

    static String toLanguageTag(Locale locale) {
        StringBuilder sb = new StringBuilder();
        sb.append(locale.getLanguage());
        String country = locale.getCountry();
        if (country != null && !country.isEmpty()) {
            sb.append(SimpleFormatter.DEFAULT_DELIMITER);
            sb.append(locale.getCountry());
        }
        return sb.toString();
    }
}

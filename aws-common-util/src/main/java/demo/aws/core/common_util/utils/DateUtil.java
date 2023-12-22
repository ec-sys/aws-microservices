package demo.aws.core.common_util.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
    public static final String ISO_ZONED_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZZ";

    /**
     * {@link Date}を{@code yyyy-MM-dd'T'HH:mm:ssZZ}形式でUTCタイムゾーンの文字列に変換する。
     *
     * @param source 変換対象の{@link Date}
     * @return 日付フォーマットで指定された文字列. {@code source}、{@code format}のいずれかが{@code null}の場合{@code null}を返却.
     */
    public static String formatUTC(final Date source) {
        return formatUTC(source, ISO_ZONED_DATETIME_FORMAT);
    }

    /**　
     * {@link Date}を指定されたフォーマットでUTCタイムゾーンの文字列に変換する。
     *
     * @param source 変換対象の{@link Date}
     * @param format 日付フォーマット
     * @return 日付フォーマットで指定された文字列. {@code source}、{@code format}のいずれかが{@code null}の場合{@code null}を返却.
     */
    public static String formatUTC(final Date source, final String format) {
        return format(source, format, TimeZone.getTimeZone("UTC"));
    }

    /**
     * {@link Date}を指定されたフォーマットの文字列に変換する。
     *
     * @param source 変換対象の{@link Date}
     * @param format 日付フォーマット
     * @param timeZone 変換後のタイムゾーン
     * @return 日付フォーマットで指定された文字列. {@code source}、{@code format}のいずれかが{@code null}の場合{@code null}を返却.
     */
    public static String format(final Date source, final String format, final TimeZone timeZone) {
        if (source == null) {
            return null;
        }
        if (format == null || format.length() == 0) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (timeZone != null) {
            sdf.setTimeZone(timeZone);
        }
        return sdf.format(source);
    }

    public static int getYear(Date targetDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(targetDate);
        return calendar.get(Calendar.YEAR);
    }
}

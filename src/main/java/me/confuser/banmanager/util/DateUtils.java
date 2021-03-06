package me.confuser.banmanager.util;

import com.j256.ormlite.dao.GenericRawResults;
import lombok.Getter;
import me.confuser.banmanager.BanManager;
import me.confuser.bukkitutil.Message;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtils {

    private static final List<Long> times = Arrays.asList(
            TimeUnit.DAYS.toSeconds(365),
            TimeUnit.DAYS.toSeconds(30),
            TimeUnit.DAYS.toSeconds(7),
            TimeUnit.DAYS.toSeconds(1),
            TimeUnit.HOURS.toSeconds(1),
            TimeUnit.MINUTES.toSeconds(1),
            TimeUnit.SECONDS.toSeconds(1));
    private static final List<String> timesString = Arrays
            .asList("year", "month", "week", "day", "hour", "minute", "second");
    @SuppressWarnings("FieldMayBeFinal")
    private static BanManager plugin = BanManager.getPlugin();
    @Getter
    private static long timeDiff = 0;
    @SuppressWarnings("FieldMayBeFinal")
    private static Pattern timePattern = Pattern
            .compile("(?:([0-9]+)\\s*y[a-z]*[,\\s]*)?" + "(?:([0-9]+)\\s*mo[a-z]*[,\\s]*)?" + "(?:([0-9]+)\\s*w[a-z]*[,\\s]*)?" + "(?:([0-9]+)\\s*d[a-z]*[,\\s]*)?" + "(?:([0-9]+)\\s*h[a-z]*[,\\s]*)?" + "(?:([0-9]+)\\s*m[a-z]*[,\\s]*)?" + "(?:([0-9]+)\\s*(?:s[a-z]*)?)?", Pattern.CASE_INSENSITIVE);

    public static String formatDifference(long time) {
        if (time == 0) {
            return Message.getString("never");
        }

        StringBuilder diff = new StringBuilder();
        boolean firstappend = true;
        for (int i = 0; i < times.size(); i++) {
            Long current = times.get(i);
            long duration = time / current;

            if (duration > 0) {
                if (firstappend) {
                    firstappend = false;
                } else {
                    diff.append(" ");
                }
                diff.append(duration).append(" ");

                String key = timesString.get(i);
                if (duration > 1) {
                    key += "s";
                }

                diff.append(Message.get("time." + key));

                time -= duration * current;
            }
        }

        return diff.length() == 0 ? Message.getString("time.now") : diff.toString();
    }

    public static String getDifferenceFormat(long timestamp) {
        return formatDifference(timestamp - (System.currentTimeMillis() / 1000L));
    }

    // Copyright essentials, all credits to them for this.
    public static long parseDateDiff(String time, boolean future) throws Exception {
        Matcher m = timePattern.matcher(time);
        int years = 0;
        int months = 0;
        int weeks = 0;
        int days = 0;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;
        boolean found = false;
        while (m.find()) {
            if (m.group() == null || m.group().isEmpty()) {
                continue;
            }
            for (int i = 0; i < m.groupCount(); i++) {
                if (m.group(i) != null && !m.group(i).isEmpty()) {
                    found = true;
                    break;
                }
            }
            if (found) {
                if (m.group(1) != null && !m.group(1).isEmpty()) {
                    years = Integer.parseInt(m.group(1));
                }
                if (m.group(2) != null && !m.group(2).isEmpty()) {
                    months = Integer.parseInt(m.group(2));
                }
                if (m.group(3) != null && !m.group(3).isEmpty()) {
                    weeks = Integer.parseInt(m.group(3));
                }
                if (m.group(4) != null && !m.group(4).isEmpty()) {
                    days = Integer.parseInt(m.group(4));
                }
                if (m.group(5) != null && !m.group(5).isEmpty()) {
                    hours = Integer.parseInt(m.group(5));
                }
                if (m.group(6) != null && !m.group(6).isEmpty()) {
                    minutes = Integer.parseInt(m.group(6));
                }
                if (m.group(7) != null && !m.group(7).isEmpty()) {
                    seconds = Integer.parseInt(m.group(7));
                }
                break;
            }
        }
        if (!found) {
            throw new Exception("Illegal Date");
        }

        if (years > 20) {
            throw new Exception("Illegal Date");
        }

        Calendar c = new GregorianCalendar();
        if (years > 0) {
            c.add(Calendar.YEAR, years * (future ? 1 : -1));
        }
        if (months > 0) {
            c.add(Calendar.MONTH, months * (future ? 1 : -1));
        }
        if (weeks > 0) {
            c.add(Calendar.WEEK_OF_YEAR, weeks * (future ? 1 : -1));
        }
        if (days > 0) {
            c.add(Calendar.DAY_OF_MONTH, days * (future ? 1 : -1));
        }
        if (hours > 0) {
            c.add(Calendar.HOUR_OF_DAY, hours * (future ? 1 : -1));
        }
        if (minutes > 0) {
            c.add(Calendar.MINUTE, minutes * (future ? 1 : -1));
        }
        if (seconds > 0) {
            c.add(Calendar.SECOND, seconds * (future ? 1 : -1));
        }
        return c.getTimeInMillis() / 1000L;
    }

    public static long findTimeDiff() throws SQLException {
        String query = "SELECT UNIX_TIMESTAMP() - ? as mysqlTime";

        GenericRawResults<String[]> results = plugin.getPlayerStorage()
                .queryRaw(query, String.valueOf(System.currentTimeMillis() / 1000L));

        timeDiff = Long.parseLong(results.getFirstResult()[0]);

        results.close();

        return timeDiff;
    }
}

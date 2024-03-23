package space.jetbrains.uision.scufflebot.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DurationParser {
    public static long parseStringToDuration(String duration) {
        String integerTimeUnit = "[0-9][smwdy{0,2}]";

        final var original = Pattern.compile(integerTimeUnit, Pattern.CASE_INSENSITIVE);

        final var parser = original.matcher(duration);

        return 0L;
    }
}

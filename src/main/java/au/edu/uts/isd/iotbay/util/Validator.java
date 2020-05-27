package au.edu.uts.isd.iotbay.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static boolean matches(String regex, String input) {
        final Pattern pattern = Pattern.compile(regex);
        return matches(pattern, input);
    }

    public static boolean matches(Pattern pattern, String input) {
        final Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean isNullOrEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }

    private Validator() {
        throw new IllegalStateException("Unable to create an instance of: " + getClass().getSimpleName());
    }

    public static class Patterns {
        public static final Pattern EMAIL_PATTERN = Pattern.compile("([a-zA-Z0-9]+)(([._-])([a-zA-Z0-9]+))*(@)([a-z]+)(.)([a-z]{3})((([.])[a-z]{0,2})*)");
        public static final Pattern NAME_PATTERN = Pattern.compile("([A-Z][a-z]+[\\s])+[A-Z][a-z]*");
        public static final Pattern PASSWORD_PATTERN = Pattern.compile("[a-zA-Z0-9!@#$%^&*)(+=-]{4,}");
    }
}

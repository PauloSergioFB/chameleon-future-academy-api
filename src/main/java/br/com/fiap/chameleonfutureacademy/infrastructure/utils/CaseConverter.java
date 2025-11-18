package br.com.fiap.chameleonfutureacademy.infrastructure.utils;

public final class CaseConverter {

    private CaseConverter() {
    }

    public static String snakeToCamel(String input) {
        if (input == null || input.isBlank()) {
            return input;
        }

        StringBuilder result = new StringBuilder();
        boolean toUpper = false;

        for (char c : input.toCharArray()) {
            if (c == '_') {
                toUpper = true;
            } else {
                if (toUpper) {
                    result.append(Character.toUpperCase(c));
                    toUpper = false;
                } else {
                    result.append(c);
                }
            }
        }

        return result.toString();
    }

}

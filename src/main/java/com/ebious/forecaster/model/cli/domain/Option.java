package com.ebious.forecaster.model.cli.domain;

import java.util.Objects;

public class Option {
    private final String name;
    private final String value;

    public Option(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static Option getEmptyOption() {
        return new Option("", "");
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Option: " +
                "name='" + name + '\'' +
                ", value='" + value + '\'';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Option option = (Option) o;

        if (!Objects.equals(name, option.name)) return false;
        return Objects.equals(value, option.value);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}

package com.ebious.forecaster.model.cli;

import com.ebious.forecaster.model.cli.domain.Option;

public abstract class OptionMapper<T> {

    public T map(Option option) {
        boolean isValid = true;
        if (!isRequired(option)) return mapToObject(option);
        if (!validateOptionName(option)) isValid = false;
        if (!validateOptionValue(option)) isValid = false;
        if (!isValid) throwException(option);
        return mapToObject(option);
    }

    protected abstract boolean isRequired(Option option);

    protected abstract boolean validateOptionName(Option option);

    protected abstract boolean validateOptionValue(Option option);

    protected abstract T mapToObject(Option option);

    protected abstract void throwException(Option option);
}

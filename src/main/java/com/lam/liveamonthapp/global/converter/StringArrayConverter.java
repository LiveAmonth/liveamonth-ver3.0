package com.lam.liveamonthapp.global.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class StringArrayConverter implements AttributeConverter<Set<String>, String> {
    private static final String SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(Set<String> attribute) {
        return String.join(SEPARATOR, attribute);
    }

    @Override
    public Set<String> convertToEntityAttribute(String dbData) {
        return Arrays.stream(dbData.split(SEPARATOR)).collect(Collectors.toSet());
    }
}

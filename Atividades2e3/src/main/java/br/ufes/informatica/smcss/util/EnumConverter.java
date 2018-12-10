package br.ufes.informatica.smcss.util;

import java.lang.reflect.ParameterizedType;

import javax.persistence.AttributeConverter;

public class EnumConverter<T extends Enum<T>> implements AttributeConverter<T, Integer> {

    final Class<T> enumClass;

    {
        @SuppressWarnings({ "rawtypes" })
        Class<? extends EnumConverter> clazz = getClass();
        ParameterizedType type = (ParameterizedType) clazz.getGenericSuperclass();
        @SuppressWarnings("unchecked")
        Class<T> actualTypeArgument = (Class<T>) type.getActualTypeArguments()[0];
        enumClass =(Class<T>) actualTypeArgument;
    }

    @Override
    public Integer convertToDatabaseColumn(T enumValue) {
        return enumValue.ordinal();
    }

    @Override
    public T convertToEntityAttribute(Integer indice) {
        return enumClass.getEnumConstants()[indice];
    }
}

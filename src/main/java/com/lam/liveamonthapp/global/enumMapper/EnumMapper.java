package com.lam.liveamonthapp.global.enumMapper;

import lombok.NoArgsConstructor;
import com.lam.liveamonthapp.paging.metaModel.MetaModelType;
import com.lam.liveamonthapp.paging.metaModel.MetaModelValue;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 프로젝트 내에서 enum클래스는 변동이 없기 때문에 사용할 때마다 생성해주는 것이아니라
 * EnumMapper 클래스를 담을 factory를 Bean 으로 등록해서 필요할 때마다 꺼내서 쓴다.
 */
@NoArgsConstructor
public class EnumMapper {
    public static String STATUS_POSTFIX = "_STATUS";
    public static String SEARCH_TYPE_POSTFIX = "_SEARCH_TYPE";
    public static String FILTER_TYPE_POSTFIX = "_FILTER_TYPE";
    public static String SORT_TYPE_POSTFIX = "_SORT_TYPE";

    private final Map<String, List<EnumMapperValue>> enumFactory = new LinkedHashMap<>();
    private final Map<String, List<MetaModelValue>> metaModelFactory = new LinkedHashMap<>();

    public void put(String key, Class<? extends EnumMapperType> e) {
        enumFactory.put(key, toEnumValues(e));
    }

    public void put(EnumClassConst enumClazz, Class<? extends EnumMapperType> e) {
        enumFactory.put(enumClazz.getClassName(), toEnumValues(e));
    }
    public void putMetaModelType(EnumClassConst enumClazz, Class<? extends MetaModelType> e) {
        metaModelFactory.put(enumClazz.getClassName(), toMetaModelValues(e));
    }

    private List<EnumMapperValue> toEnumValues(Class<? extends EnumMapperType> e) {
        return Arrays.stream(e.getEnumConstants())
                        .map(EnumMapperValue::new)
                        .collect(Collectors.toList());
    }
    private List<MetaModelValue> toMetaModelValues(Class<? extends MetaModelType> e) {
        return  Arrays.stream(e.getEnumConstants())
                        .map(MetaModelValue::new)
                        .collect(Collectors.toList());
    }

    public List<EnumMapperValue> get(String key) {
        return enumFactory.get(key);
    }
    public List<MetaModelValue> getMetaModel(String key) {
        return metaModelFactory.get(key);
    }

    public Map<String, List<EnumMapperValue>> get(List<String> keys) {
        if (keys == null || keys.size() == 0) return new LinkedHashMap<>();
        return keys.stream()
                .collect(Collectors.toMap(Function.identity(), key -> enumFactory.get(key)));
    }

    public Map<String, List<EnumMapperValue>> getAll() {
        return enumFactory;
    }


}


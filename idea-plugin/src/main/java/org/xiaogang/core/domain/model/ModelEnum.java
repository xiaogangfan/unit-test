package org.xiaogang.core.domain.model;

import java.util.Arrays;
import java.util.Optional;

/**
 * 描述:
 *
 * @author xiaogangfan
 * @create 2019-09-09 5:49 PM
 */
public enum ModelEnum {

    DDD_Model
    ,DDD_Applicaiton
    ;

    ModelEnum() {

    }

    public ModelEnum valuesOf(String name) {
        if (name == null || name.equals("")) {
            return null;
        }
        ModelEnum[] values = values();
        Optional<ModelEnum> first = Arrays.stream(values).filter(
            batchStatusEnumDTO -> batchStatusEnumDTO.name().equalsIgnoreCase(name)).findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        return null;
    }

    @Override
    public String toString() {
        return "BatchStatusEnumDTO{}";
    }

}
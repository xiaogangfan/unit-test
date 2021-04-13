package org.xiaogang.core.domain.model.enums;

/**
 * 描述:
 *
 * @author xiaogangfan
 * @create 2019-08-08 12:08 PM
 */
public enum ReturnTypeEnum {
    BOOLEAN("boolean", "Boolean"),
    STRING("string", "String"),
    INTEGER("integer", "Integer"),
    LONG("long", "Long"),

    ;

    String code;
    String name;

    ReturnTypeEnum(String typeCode, String typeName) {
        this.code = typeCode;
        this.name = typeName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
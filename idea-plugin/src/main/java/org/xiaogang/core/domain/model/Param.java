package org.xiaogang.core.domain.model;

//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.experimental.Accessors;

/**
 * 描述:
 *
 * @author xiaogangfan
 * @create 2019-09-03 7:11 PM
 */

public class Param {
    private String type;
    private String name;

    public Param() {
    }

    public Param(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
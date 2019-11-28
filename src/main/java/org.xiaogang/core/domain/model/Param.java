package org.xiaogang.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 描述:
 *
 * @author xiaogangfan
 * @create 2019-09-03 7:11 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Param {
    private String type;
    private String name;
}
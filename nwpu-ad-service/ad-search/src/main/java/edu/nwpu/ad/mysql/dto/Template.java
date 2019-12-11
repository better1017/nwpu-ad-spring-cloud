package edu.nwpu.ad.mysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 模板文件映射到Template对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Template {

    private String database;
    private List<JsonTable> tableList;
}

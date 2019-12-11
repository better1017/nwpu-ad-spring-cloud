package edu.nwpu.ad.mysql.dto;

import edu.nwpu.ad.mysql.constant.OpType;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 对模板文件进行解析
 * 模板文件映射到Template对象
 * 所以是对Template对象进行解析
 */
@Data
public class ParseTemplate {

    // Database的标识
    private String database;

    // 模板文件中定义的各个表的属性（操作属性，层级属性）的一个表达。
    private Map<String, TableTemplate> tableTemplateMap = new HashMap<>();

    public static ParseTemplate parse(Template _template) {
        ParseTemplate template = new ParseTemplate();
        template.setDatabase(_template.getDatabase());

        // 填充tableTemplateMap
        for (JsonTable table : _template.getTableList()) {

            String name = table.getTableName();
            Integer level = table.getLevel();

            TableTemplate tableTemplate = new TableTemplate();
            tableTemplate.setTableName(name);
            tableTemplate.setLevel(level.toString());
            template.tableTemplateMap.put(name, tableTemplate);

            // 遍历操作类型对应的列
            Map<OpType, List<String>> opTypeFieldSetMap =
                    tableTemplate.getOpTypeFieldSetMap();

            for (JsonTable.Column column : table.getInsert()) {
                getAndCreateIfNeed(
                        OpType.ADD,
                        opTypeFieldSetMap,
                        ArrayList::new
                ).add(column.getColumn());
            }
            for (JsonTable.Column column : table.getUpdate()) {
                getAndCreateIfNeed(
                        OpType.UPDATE,
                        opTypeFieldSetMap,
                        ArrayList::new
                ).add(column.getColumn());
            }
            for (JsonTable.Column column : table.getDelete()) {
                getAndCreateIfNeed(
                        OpType.UPDATE,
                        opTypeFieldSetMap,
                        ArrayList::new
                ).add(column.getColumn());
            }
        }
        return template;
    }

    /**
     * 实现方法，能够从map中按key取值，如果不存在则会创建并加入到map中
     * **/
    private static <T, R> R getAndCreateIfNeed(T key, Map<T, R> map,
                                               Supplier<R> factory) {
        return map.computeIfAbsent(key, k -> factory.get());
    }

}

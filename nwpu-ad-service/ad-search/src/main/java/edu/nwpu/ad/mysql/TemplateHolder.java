package edu.nwpu.ad.mysql;

import com.alibaba.fastjson.JSON;
import edu.nwpu.ad.mysql.constant.OpType;
import edu.nwpu.ad.mysql.dto.ParseTemplate;
import edu.nwpu.ad.mysql.dto.TableTemplate;
import edu.nwpu.ad.mysql.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * 1. 解析template.json模板文件
 * 2. 实现列索引到列名的映射关系
 */
@Slf4j
@Component
public class TemplateHolder {

    private ParseTemplate template;

    // 是根据sql语句进行查询
    private final JdbcTemplate jdbcTemplate;

    private String SQL_SCHEMA = "select table_schema, table_name, " +
            "column_name, ordinal_position from information_schema.columns " +
            "where table_schema = ? and table_name = ?";

    public TemplateHolder(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * loadJson(String path)应该在容器启动时就调用，
     * 也就是说在TemplateHolder加载的时候就执行
     * **/
    @PostConstruct
    public void init() {
        loadJson("template.json");
    }

    /**
     * 对外服务的方法
     * 通过表的名字，拿到表的所有信息
     * **/
    public TableTemplate getTable(String tableName) {
        return template.getTableTemplateMap().get(tableName);
    }

    /**
     * 加载配置文件
     * **/
    private void loadJson(String path) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream inStream = cl.getResourceAsStream(path);

        try {
            Template template = JSON.parseObject(
                    inStream,
                    Charset.defaultCharset(),
                    Template.class
            );
            this.template = ParseTemplate.parse(template);
            loadMeta();
        } catch (IOException ex) {
            log.error(ex.getMessage());
            throw new RuntimeException("|----fail to parse json file----|");
        }
    }

    /**
     * 定义方法，实现查询模板中每张表的信息，完成映射
     * **/
    private void loadMeta() {
        // 对每张表进行for循环
        for (Map.Entry<String, TableTemplate> entry :
                template.getTableTemplateMap().entrySet()) {
            TableTemplate table = entry.getValue();

            List<String> updateFields = table.getOpTypeFieldSetMap().get(
                    OpType.UPDATE
            );
            List<String> insertFields = table.getOpTypeFieldSetMap().get(
                    OpType.ADD
            );
            List<String> deleteFields = table.getOpTypeFieldSetMap().get(
                    OpType.DELETE
            );
            jdbcTemplate.query(SQL_SCHEMA, new Object[]{
                    template.getDatabase(), table.getTableName()
            }, (rs, i) -> {

                int pos = rs.getInt("ORDINAL_POSITION");
                String colName = rs.getString("COLUMN_NAME");

                if ((null != updateFields && updateFields.contains(colName))
                        || (null != insertFields && insertFields.contains(colName))
                        || (null != deleteFields && deleteFields.contains(colName))) {
                    table.getPosMap().put(pos - 1, colName);
                }

                return null;
            });
        }
    }
}

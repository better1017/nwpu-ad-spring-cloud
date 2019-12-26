package edu.nwpu.ad.mysql.dto;

import edu.nwpu.ad.mysql.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MySqlRowData {

    private String tableName;
    private String level;
    private OpType opType;
    private List<Map<String, String>> fieldValueMap = new ArrayList<>();
}

package edu.nwpu.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class App {

    // 应用的编码
    private String appCode;
    // 应用的名称
    private String appName;
    // 应用的包名
    private String packageName;
    // activity 名称
    private String activityName;
}

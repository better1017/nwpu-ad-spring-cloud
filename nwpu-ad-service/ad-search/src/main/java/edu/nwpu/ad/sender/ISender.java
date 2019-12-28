package edu.nwpu.ad.sender;

import edu.nwpu.ad.mysql.dto.MySqlRowData;

public interface ISender {

    void sender(MySqlRowData rowData);
}

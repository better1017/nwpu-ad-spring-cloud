package edu.nwpu.ad.mysql.listener;

import edu.nwpu.ad.mysql.dto.BinlogRowData;

public interface Ilistener {
    void register();
    void onEvent(BinlogRowData eventData);
}

package org.apache.dolphinscheduler.alert.manager;

import org.apache.dolphinscheduler.alert.utils.Constants;
import org.apache.dolphinscheduler.alert.utils.SendFeishuUtil;
import org.apache.dolphinscheduler.plugin.model.AlertData;
import org.apache.dolphinscheduler.plugin.model.AlertInfo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeishuManager {

    private static final Logger logger = LoggerFactory.getLogger(MsgManager.class);

    public Map<String, Object> send(AlertInfo alertInfo) {
        Map<String, Object> retMap = new HashMap<>();
        retMap.put(Constants.STATUS, false);
        String feiShuWebHook = SendFeishuUtil.FEI_SHU_WEB_HOOK;

        AlertData alertData = alertInfo.getAlertData();

        try {
            SendFeishuUtil.sendMsg(feiShuWebHook, SendFeishuUtil.markdownText(alertData.getTitle(),alertData.getContent()));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        retMap.put(Constants.STATUS, true);
        return retMap;
    }
}

package org.apache.dolphinscheduler.alert.utils;

import org.apache.dolphinscheduler.plugin.model.AlertData;

import java.io.IOException;

import org.junit.Test;

public class SenFeiShuUtilTest {

    @Test
    public void testMakeTeamSendMsg1() throws IOException {
        AlertData alertData = new AlertData();
        alertData.setShowType("table");
        alertData.setTitle("start process success");
        alertData.setContent("[\"id:164585\",\"name:每十分钟重点游戏登录注册监控-0-1611905196304\",\"job type: start process\",\"state: SUCCESS\",\"recovery:NO\",\"run time: 1\",\"start time: 2021-01-29 15:26:36\",\"end time: 2021-01-29 15:26:47\",\"host: 172.25.0.39:5678\"]");
        SendFeishuUtil.sendMsg("https://open.feishu.cn/open-apis/bot/v2/hook/xxxxx",SendFeishuUtil.markdownText(alertData.getTitle(),alertData.getContent()));
    }
}

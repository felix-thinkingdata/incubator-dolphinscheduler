package org.apache.dolphinscheduler.alert.utils;

import java.io.IOException;

import org.junit.Test;

public class SenFeiShuUtilTest {

    @Test
    public void testMakeTeamSendMsg1() throws IOException {
        SendFeishuUtil.sendMsg("https://open.feishu.cn/open-apis/bot/v2/hook/xxxxx","test feishu alert");
    }
}

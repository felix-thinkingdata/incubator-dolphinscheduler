package org.apache.dolphinscheduler.alert.utils;


import org.apache.dolphinscheduler.common.utils.StringUtils;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;


public class SendFeishuUtil {

    public static final Logger logger = LoggerFactory.getLogger(SendFeishuUtil.class);

    public static final String FEI_SHU_WEB_HOOK = PropertyUtils.getString(Constants.FEI_SHU_WEB_HOOK);

    public static void sendMsg(String apiPath, String content) throws IOException {
        try {
            CloseableHttpClient httpClient = HttpRequestUtil.getHttpClient();
            HttpPost post = new HttpPost(apiPath);

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("msg_type", "text");
            JSONObject textObj = new JSONObject();
            textObj.put("text", content);
            jsonObj.put("content", textObj);
            StringEntity stringEntity = new StringEntity(jsonObj.toJSONString(), ContentType.APPLICATION_JSON);

            post.setEntity(stringEntity);

            CloseableHttpResponse httpResponse = httpClient.execute(post);

            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                logger.error("send feishu message error, return http status code: " + statusCode);
            }
            String responseBody = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            System.out.println(responseBody);
            JSONObject resObj = JSONObject.parseObject(responseBody);
            int retCode = resObj.getIntValue("StatusCode");
            if (retCode != 0) {
                logger.error("feishu alert message error, return api code: " + retCode + ", return api message: " + responseBody);
            }
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * get Enterprise WeChat is enable
     *
     * @return isEnable
     */
    public static boolean isEnable() {
        Boolean isEnable = null;
        try {
            isEnable = PropertyUtils.getBoolean(Constants.FEI_SHU_ENABLE);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (isEnable == null) {
            return false;
        }
        return isEnable;
    }


    /**
     * convert text to markdown style
     *
     * @param title the title
     * @param content the content
     * @return markdown text
     */
    public static String markdownText(String title, String content) {
        if (StringUtils.isNotEmpty(content)) {
            List<String> list;
            try {
                list = JSONUtils.toList(content, String.class);
            } catch (Exception e) {
                logger.error("json format exception", e);
                return null;
            }

            StringBuilder contents = new StringBuilder(100);
            contents.append(String.format("`%s`%n", title));
            for (String str : list) {
                contents.append(Constants.MARKDOWN_QUOTE);
                contents.append(str);
                contents.append(Constants.MARKDOWN_ENTER);
            }

            return contents.toString();

        }
        return null;
    }


}

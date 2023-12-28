package cn.javgo.lion.mybatis.generator;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * description：
 *
 * @author javgo.cn
 * @date 2023/12/28
 */
public class Main {
    public static void main(String[] args) {
        // 示例 returnGoodsBatchId 数组
        String[] returnGoodsBatchIds = {"6620", "6611", "6602","6578","6576","6562","6555","6547","6537","6531","6529","6518","6517","6491","6479","6472","6470","6469","6466","6462","6461","6444","6419","6418","6407","6406","6405","6404","6403","6402"};
        JSONArray results = new JSONArray();
        for (String id : returnGoodsBatchIds) {
            JSONObject response = sendPostRequest(id);
            saveResponseToFile(response, id + "_response.json");
        }
    }

    private static JSONObject sendPostRequest(String returnGoodsBatchId) {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost("https://erp.qipeidao.com/bs/returnGoods/applyReturnGoodsInfo");

        // 设置请求头
        post.setHeader("Content-Type", "application/json");
        post.setHeader("Cookie", "org=649690c83c347ae700213aef2951a5fc; jk-erpSessionId=0705795e-03d7-424e-806a-b3117944604e");

        // 设置请求体
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("returnGoodsBatchId", returnGoodsBatchId);

        try {
            StringEntity entity = new StringEntity(jsonParam.toString());
            post.setEntity(entity);

            HttpResponse response = httpClient.execute(post);
            String jsonResponse = EntityUtils.toString(response.getEntity());

            return new JSONObject(jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject().put("error", "Request failed for id: " + returnGoodsBatchId);
        }
    }

    private static void saveResponseToFile(JSONObject response, String filename) {
        File file = new File(filename);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(response.toString(4)); // Indent the JSON for readability
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

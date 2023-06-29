package com.antgroup.antchain.xbuilders.integration.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LogBO {
    /**
     * 事件主题
     */
    private List<String> topics;

    /**
     * 事件数据
     */
    private String data;

    public static LogBO fromJson(JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }

        JSONArray topics = jsonObject.getJSONArray("topics");

        LogBO logBO = new LogBO();
        logBO.setTopics(
                topics == null ? new ArrayList<>() :
                        topics.stream().map(t -> (String) t).collect(Collectors.toList()));
        logBO.setData(jsonObject.getString("logData"));

        return logBO;
    }
}

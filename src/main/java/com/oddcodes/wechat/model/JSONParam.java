package com.oddcodes.wechat.model;

import com.alibaba.fastjson.JSONObject;

/**
 * @author dean.lee
 */
public class JSONParam extends JSONObject {

    @Override
    public JSONParam put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}

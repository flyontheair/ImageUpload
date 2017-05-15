package com.springapp.mvc.services;

import java.util.Map;

/**
 * Created by Max on 2017/5/14.
 */
public interface SaveImage {
    /**
     *字典存入mongo
     * @param map
     * @return
     */
    public String Save(Map<String,String> map);
}

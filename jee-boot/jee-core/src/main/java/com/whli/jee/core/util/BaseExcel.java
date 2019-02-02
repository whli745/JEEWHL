package com.whli.jee.core.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>类或方法描述</p>
 *
 * @author whli
 * @date 2019/1/7 14:07
 */
public abstract class BaseExcel {
    public abstract String fileName();

    public abstract LinkedHashMap<String,String> headers();

    public abstract List<Map<String,Object>> datas();
}

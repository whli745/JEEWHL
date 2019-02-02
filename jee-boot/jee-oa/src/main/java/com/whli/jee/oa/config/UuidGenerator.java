package com.whli.jee.oa.config;

import org.activiti.engine.impl.cfg.IdGenerator;

import java.util.UUID;

/**
 * <p>类或方法描述</p>
 *
 * @author whli
 * @date 2019/1/17 13:36
 */
public class UuidGenerator implements IdGenerator {
    @Override
    public String getNextId() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-","").toUpperCase();
    }
}

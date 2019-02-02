package com.whli.jee.oa.service;

import com.whli.jee.oa.entity.BasModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>流程模型业务层</p>
 * @author whli
 * @date 2019/1/16 10:27
 */
public interface IBasModelService {

    /**
     * 分页查询流程模型
     * @return
     */
    public List findByPage(BasModel entity);

    /**
     * 分页查询流程模型
     * @return
     */
    public List findAll();

    /**
     * 获取所有模型的总数
     * @return
     */
    public int getCount(BasModel entity);

    /**
     * 创建新模型
     * @param entity
     */
    @Transactional
    public String addModel(BasModel entity);

    /**
     * 删除模型
     * @param entity
     * @return
     */
    public int deleteModel(BasModel entity);

    /**
     * 发布模型
     * @param entity
     * @return
     */
    @Transactional
    public int deployModel(BasModel entity);
}

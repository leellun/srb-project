package com.newland.srb.core.service;

import com.newland.srb.core.pojo.dto.ExcelDictDTO;
import com.newland.srb.core.pojo.entity.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
public interface IDictService extends IService<Dict> {

    void importData(InputStream inputStream);

    List<ExcelDictDTO> listDictData();

    List<Dict> listByParentId(Long parentId);

    List<Dict> findByDictCode(String dictCode);

    String getNameByParentDictCodeAndValue(String dictCode, Integer value);
}

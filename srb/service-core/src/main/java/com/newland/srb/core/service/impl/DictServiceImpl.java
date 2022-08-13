package com.newland.srb.core.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newland.srb.core.listener.ExcelDictDTOListener;
import com.newland.srb.core.mapper.DictMapper;
import com.newland.srb.core.pojo.dto.ExcelDictDTO;
import com.newland.srb.core.pojo.entity.Dict;
import com.newland.srb.core.service.IDictService;
import io.netty.util.Timeout;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author leellun
 * @since 2022-08-08
 */
@Service("dictService")
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void importData(InputStream inputStream) {
        EasyExcel.read(inputStream, ExcelDictDTO.class, new ExcelDictDTOListener(baseMapper)).sheet().doRead();
    }

    @Override
    public List<ExcelDictDTO> listDictData() {
        List<Dict> dictList = baseMapper.selectList(null);
        ArrayList<ExcelDictDTO> excelDictDTOS = new ArrayList<>(dictList.size());
        dictList.forEach(dict -> {
            ExcelDictDTO excelDictDTO = new ExcelDictDTO();
            BeanUtils.copyProperties(dict, excelDictDTO);
            excelDictDTOS.add(excelDictDTO);
        });
        return excelDictDTOS;
    }

    @Override
    public List<Dict> listByParentId(Long parentId) {
        try {
            List<Dict> dictList = (List<Dict>) redisTemplate.opsForValue().get("srb:core:dictList:" + parentId);
            if (dictList != null) {
                return dictList;
            }
        } catch (Exception e) {
            log.error("redis服务器异常:" + ExceptionUtils.getStackTrace(e));
        }
        List<Dict> dictList = baseMapper.selectList(Wrappers.<Dict>query().eq("parent_id", parentId));
        dictList.forEach(dict -> {
            boolean hasChildren = this.hasChildren(dict.getId());
            dict.setHasChildren(hasChildren);
        });
        try {
            redisTemplate.opsForValue().set("srb:core:dictList:" + parentId, dictList, 5, TimeUnit.MINUTES);
        } catch (Exception e) {
            log.error("redis服务器异常:" + ExceptionUtils.getStackTrace(e));
        }
        return dictList;
    }

    @Override
    public List<Dict> findByDictCode(String dictCode) {
        return this.listByParentId(baseMapper.selectOne(Wrappers.<Dict>query().eq("dict_code", dictCode)).getId());
    }

    @Override
    public String getNameByParentDictCodeAndValue(String dictCode, Integer value) {
        Dict parentDict = baseMapper.selectOne(Wrappers.<Dict>query().eq("dict_code", dictCode));
        if (parentDict == null) {
            return "";
        }
        Dict dict = baseMapper.selectOne(Wrappers.<Dict>query().eq("parent_id", parentDict.getId()).eq("value", value));
        if (dict == null) {
            return "";
        }
        return dict.getName();
    }

    private boolean hasChildren(Long id) {
        Integer count = baseMapper.selectCount(Wrappers.<Dict>query().eq("parent_id", id));
        if (count.intValue() > 0) {
            return true;
        }
        return false;
    }
}

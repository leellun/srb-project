package com.newland.srb.core.controller.admin;

import com.alibaba.excel.EasyExcel;
import com.newland.srb.common.exception.BusinessException;
import com.newland.srb.common.result.R;
import com.newland.srb.common.result.ResponseEnum;
import com.newland.srb.core.pojo.dto.ExcelDictDTO;
import com.newland.srb.core.pojo.entity.Dict;
import com.newland.srb.core.service.IDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据字典管理
 */
@Api(tags = "数据字典管理")
@RestController
@RequestMapping("/admin/core/dict")
public class AdminDictController {
    @Autowired
    @Qualifier("dictService")
    private IDictService dictService;

    /**
     * Excel数据的批量导入
     *
     * @param file
     * @return
     */
    @ApiOperation("Excel数据的批量导入")
    @PostMapping("/import")
    public R batchImport(@ApiParam(value = "Excel数据字典文件", required = true) @RequestParam("file") MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            dictService.importData(inputStream);
            return R.ok().message("数据字典数据批量导入成功");
        } catch (Exception e) {
            throw new BusinessException(ResponseEnum.UPLOAD_ERROR, e);
        }
    }

    /**
     * Excel数据的导出
     *
     * @param response
     * @throws IOException
     */
    @ApiOperation("Excel数据的导出")
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("mydict", "utf-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), ExcelDictDTO.class).sheet("数据字典").doWrite(dictService.listDictData());
    }

    /**
     * 根据上级id获取子节点数据列表
     *
     * @param parentId
     * @return
     */
    @ApiOperation("根据上级id获取子节点数据列表")
    @GetMapping("/listByParentId/{parentId}")
    public R listByParentId(@ApiParam(value = "上级节点id", required = true) @PathVariable Long parentId) {
        List<Dict> dictList = dictService.listByParentId(parentId);
        Map<String, Object> data = new HashMap<>();
        data.put("list", dictList);
        return R.ok().data(data);
    }

}

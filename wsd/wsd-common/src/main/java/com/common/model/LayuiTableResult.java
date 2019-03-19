package com.common.model;

import lombok.Data;

import java.util.List;

/***
 * layui表格数据格式
 */
@Data
public class LayuiTableResult<T> {

    private Integer code;

    private String msg;

    private Long count;

    private List<T> Data;

    public LayuiTableResult(Integer code, String msg, Long count, List<T> data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        Data = data;
    }
}

package com.seventeen.common.param;

import io.swagger.annotations.ApiModelProperty;

/**
 * 基础分页参数
 *
 * @author seventeen
 * @date 2018/11/5
 */

public class BasePageParam extends CommonParam {

    /**
     * 默认页码为第一页
     */
    private static final int DEFAULT_PAGE_NUM = 1;

    /**
     * 默认每页数据为20条
     */
    private static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * 最大返回数据条数
     */
    private static final int MAX_PAGE_SIZE = 500;

    /**
     * 分页查询开始的行数
     */
    private Integer start;

    @ApiModelProperty(value = "页码")
    private Integer pageNum;

    @ApiModelProperty(value = "每页大小")
    private Integer pageSize;
    
    public Integer getPageNum() {
        return (pageNum == null || pageNum < 1) ? DEFAULT_PAGE_NUM : pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        if (pageSize == null) {
            return DEFAULT_PAGE_SIZE;
        }
        // 每页大小不能超过最大值
        if (pageSize > MAX_PAGE_SIZE) {
            return MAX_PAGE_SIZE;
        }

        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStart() {
        if (start != null) {
            return start;
        }

        return (this.getPageNum() - 1) * this.getPageSize();
    }

    public void setStart(Integer start) {
        this.start = start;
    }

}

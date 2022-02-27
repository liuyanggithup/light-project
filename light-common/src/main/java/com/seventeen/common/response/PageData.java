package com.seventeen.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 基础分页数据
 *
 * @author seventeen
 * @date 2018/11/2
 */

@ApiModel
public class PageData<T> {

    @ApiModelProperty(value = "总数据量")
    private Integer totalCount;

    @ApiModelProperty(value = "总页数")
    private Integer pageCount;

    @ApiModelProperty(value = "(新项目不要使用)每页大小")
    @Deprecated
    private int perPage;

    @ApiModelProperty(value = "(新项目不要使用)每页大小")
    @Deprecated
    private int page;

    @ApiModelProperty(value = "每页大小")
    private int pageSize;

    @ApiModelProperty(value = "是否有下一页,true：是 false：否")
    private Boolean hasNext;

    @ApiModelProperty(value = "结果集(List对象)")
    private List<T> list;

    public Integer getTotalCount() {
        return Objects.isNull(totalCount) ? 0 : totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageCount() {
        return Objects.isNull(pageCount) ? 0 : pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    @Deprecated
    public int getPage() {
        return page;
    }

    @Deprecated
    public void setPage(int page) {
        this.page = page;
    }

    @Deprecated
    public int getPerPage() {
        return perPage;
    }

    @Deprecated
    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }

    public Boolean getHasNext() {
        if (hasNext != null) {
            return hasNext;
        }

        return list != null && list.size() > 0;
    }

}

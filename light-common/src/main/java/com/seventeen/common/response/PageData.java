package com.seventeen.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 基础分页数据
 *
 * @author seventeen
 * @date 2018/11/2
 */

@ApiModel
@Data
public class PageData<T> {

    @ApiModelProperty(value = "总数据量")
    private long totalCount;

    @ApiModelProperty(value = "结果集(List对象)")
    private List<T> list;

    @ApiModelProperty(value = "是否有下一页,true：是 false：否")
    private Boolean hasNext;

    public static <T> PageData<T> build(List<T> list, long totalCount) {
        PageData<T> pageData = new PageData<>();
        pageData.setList(list);
        pageData.setTotalCount(totalCount);
        return pageData;
    }

    public Boolean getHasNext() {
        if (hasNext != null) {
            return hasNext;
        }

        return list != null && list.size() > 0;
    }
}

package com.seventeen.common.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 基础参数，全局使用的参数例如接口解密等
 *
 *
 * @author seventeen
 * @date 2018/11/5
 */
@Data
public class CommonParam {

    @ApiModelProperty(value = "时间戳")
    private String timestamp;
}

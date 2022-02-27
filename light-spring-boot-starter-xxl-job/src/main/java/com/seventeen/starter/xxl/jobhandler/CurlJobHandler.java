package com.seventeen.starter.xxl.jobhandler;

import com.seventeen.starter.xxl.CommandUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author seventeen
 */
@JobHandler(value = "curlJobHandler")
@Component
public class CurlJobHandler extends IJobHandler {


    @Override
    public ReturnT<String> execute(String param) throws Exception {

        if (StringUtils.isEmpty(param)) {
            return new ReturnT<String>(IJobHandler.FAIL.getCode(), "参数不能为空，请设置");
        }

        String command = "curl ";
        String m = CommandUtil.getOptionValue("-m", param);
        if (StringUtils.isEmpty(m)) {
            command += "-m 1200 ";
        }

        String connectTimeout = CommandUtil.getOptionValue("--connect-timeout", param);
        if (StringUtils.isEmpty(connectTimeout)) {
            command += "--connect-timeout 1200 ";
        }

        command += param;
        return CommandUtil.process(command);
    }


}

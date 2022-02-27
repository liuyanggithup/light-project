package com.seventeen.starter.xxl.jobhandler;

import com.seventeen.starter.xxl.CommandUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.stereotype.Component;

/**
 * @author seventeen
 */
@JobHandler(value = "commandJobHandler")
@Component
public class CommandJobHandler extends IJobHandler {

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        return CommandUtil.process(param);
    }
}

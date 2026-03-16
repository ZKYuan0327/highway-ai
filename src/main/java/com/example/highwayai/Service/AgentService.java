package com.example.highwayai.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.highwayai.pojo.RoadStrategy;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class AgentService {
    @Autowired
    private RoadStrategyService roadStrategyService;

    private static final Logger logger = LoggerFactory.getLogger(AgentService.class);

    public record RoadStrategyRecommendation(RoadStrategy roadStrategy){}

    @Tool(description = "查询数据库中属于该路价值提升策略类的数据")
    public List<RoadStrategy> getRoadStrategyByCategoryLevel2(@ToolParam(description = "路价值提升策略类") String categoryLevel2){
        LambdaQueryWrapper<RoadStrategy> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(RoadStrategy::getCategoryLevel2, categoryLevel2);

        return roadStrategyService.list(queryWrapper);
    }
}


//public Page<RoadStrategy> pageByCategoryLevel2(int page, int pageSize, String categoryLevel2){
//    log.info("page = {}, pageSize = {}", page, pageSize);
//    Page pageInfo = new Page(page, pageSize);
//
//    LambdaQueryWrapper<RoadStrategy> queryWrapper = new LambdaQueryWrapper<>();
//    queryWrapper.eq(RoadStrategy::getCategoryLevel2, categoryLevel2);
//
//    super.page(pageInfo, queryWrapper);
//    return pageInfo;
//}


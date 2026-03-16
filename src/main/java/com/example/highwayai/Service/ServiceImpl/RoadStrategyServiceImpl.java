package com.example.highwayai.Service.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.highwayai.Service.RoadStrategyService;
import com.example.highwayai.mapper.RoadStrategyMapper;
import com.example.highwayai.pojo.RoadStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoadStrategyServiceImpl extends ServiceImpl<RoadStrategyMapper, RoadStrategy> implements RoadStrategyService {

}

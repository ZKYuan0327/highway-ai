package com.example.highwayai.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("road_strategy")
public class RoadStrategy {

    private Long id;

    private String categoryLevel1;          // 策略归属（一级分类）

    private String categoryLevel2;          // 策略子类（二级分类）

    private Integer strategyNo;              // 策略序号

    private String strategyName;             // 策略名称

    private String description;              // 策略解析

    private String applicableConditions;     // 适用条件

    private String conditionTags;            // 条件标签（匹配特征）

    private String adaptability;              // 适配度

    private String implementationTarget;      // 实施对象

    private String highwayTypeApplicability;  // 高速类型适用性
    private String remarks;                   // 备注

    // 策略标签（从O列开始，索引14-25）
    private Boolean deviceFacilityFlag;       // 设备/设施类

    private Boolean managementServiceFlag;    // 管理/服务类

    private Boolean networkCoordinationFlag;  // 路网协同类

    private Boolean shortTermFlag;            // 短期策略

    private Boolean longTermFlag;             // 长期策略

    private Boolean efficiencyFlag;           // 效率提升类

    private Boolean safetyFlag;               // 安全保障类

    private Boolean serviceUpgradeFlag;       // 服务升级类

    private Boolean greenLowcarbonFlag;       // 绿色低碳类

    private Boolean commercialFlag;           // 商业开发类

    private Boolean intelligentFlag;          // 智能化类

    private Boolean traditionalRenovationFlag; // 传统改造类

    // 审计字段
    @TableField(fill = FieldFill.INSERT)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}
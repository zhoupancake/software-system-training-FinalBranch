package com.systemtraining.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.systemtraining.entity.Project;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ProjectMapper extends BaseMapper<Project> {
}

package com.systemtraining.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.systemtraining.entity.Project;
import com.systemtraining.mapper.ProjectMapper;
import com.systemtraining.service.ProjectService;
import org.springframework.stereotype.Service;


@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {
}

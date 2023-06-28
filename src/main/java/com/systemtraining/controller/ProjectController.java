package com.systemtraining.controller;

import com.systemtraining.common.HttpResponseEntity;
import com.systemtraining.entity.Project;
import com.systemtraining.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ProjectController {

    private ProjectService projectService;

    @Autowired
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }
    // 查询项目列表的请求处理方法
    @PostMapping("/queryProjectList")
    public HttpResponseEntity queryProjectList(@RequestBody Project requestProject) {
        // 根据请求的项目ID和项目名称进行模糊查询
        List<Project> projectList = projectService.query()
                .like("id", requestProject.getId() == null ? "" : requestProject.getId()) // 若请求的项目ID为空，则使用空字符串进行查询
                .like("project_name", requestProject.getProjectName() == null ? "" : requestProject.getProjectName()) // 若请求的项目名称为空，则使用空字符串进行查询
                .list();
        return HttpResponseEntity.response(!projectList.isEmpty(), "查询", projectList); // 返回HttpResponseEntity对象
    }

    @PostMapping("/modifyProjectInfo")
    public HttpResponseEntity modifyProjectInfo(@RequestBody Project project) {
        return HttpResponseEntity.response(projectService.updateById(project), "修改", null);
    }

    @PostMapping("/deleteProjectById")
    public HttpResponseEntity deleteProject(@RequestBody Project project) {
        return HttpResponseEntity.response(projectService.removeById(project), "删除", null);
    }
    @PostMapping("/addProjectInfo")
    public HttpResponseEntity addProjectInfo(@RequestBody Project project) {
        return HttpResponseEntity.response(projectService.save(project), "添加", null);
    }

}

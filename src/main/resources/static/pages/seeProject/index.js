let projectId
onload = async () => {
  $('#headerDivB').text('项目详情')
  $('#headerUsername').text($util.getItem('userInfo').username)

  projectId = $util.getPageParam('seeProject')
  fetchProjectInfo(projectId)
  await fetchProjectInfo(projectId)
  await getQuestionnaireInfo(projectId)
}

const fetchProjectInfo = (id) => {
  let params = {
    id
  }
  $.ajax({
    url: API_BASE_URL + '/queryProjectList',
    type: "POST",
    data: JSON.stringify(params),
    dataType: "json",
    contentType: "application/json",
    success(res) {
      let info = res.data[0]
      $('#projectName').text(info.projectName)
      $('#createTime').text(info.creationDate)
      $('#personInCharge').text(info.createdBy)
      $('#projectDescription').text(info.projectContent)
    }
  })
}
const handleClickLink = async (id) =>{
  let params = {
    id: id
  }
  let res = await fetch(API_BASE_URL + '/questionnaire/link' ,
      {method: 'POST', body: JSON.stringify(params),
        headers: {"Content-Type": "application/json"}}).then(res => {
    return res.json()
  })
  if (res.code === '666'){
    localStorage.setItem("questionnaireId", id)
    localStorage.setItem("questionnaireName", res.data.name)
    localStorage.setItem("questionnaireDescription", res.data.comment)
    location.href = '/pages/answerSheet/index.html'
  } else {
    alert('问卷未发布')
  }
}

const handleClickPreview = (id) =>{
  localStorage.setItem("questionnaireId", id);
  $util.setPageParam("isPreview", '1')
  location.href = '/pages/answerDetail/index.html'
}


const handleClickStatistic = (id, name)=>{
  localStorage.setItem("questionnaireId", id)
  localStorage.setItem("questionnaireName", name)
  location.href = '/pages/statistics/index.html'
}


const handleReleaseQuestionnaire = (id) =>{
  let params = {
    id,
    status: '1'
  }
  $.ajax({
    url: API_BASE_URL + '/questionnaire/release',
    type: "POST",
    data: JSON.stringify(params),
    dataType: "json",
    contentType: "application/json",
    success(res) {
      alert('发布成功')
      $("#questionnaireTable tr:not(:first)").empty()
      getQuestionnaireInfo(projectId)
    }
  })
}
const handleCloseQuestionnaire = async (id) =>{
  let params = {
    id: id
  }
  let res = await fetch(API_BASE_URL + '/questionnaire/close' ,
      {method: 'POST', body: JSON.stringify(params),
        headers: {"Content-Type": "application/json"}}).then(res => {
    return res.json()
  })
  let info = res.message
  alert(info)
  $("#questionnaireTable tr:not(:first)").empty()
  await getQuestionnaireInfo(projectId)
}

const handleDeleteQuestionnaire = async (id) =>{
  let params = {
    id: id
  }
  if(window.confirm('你确定要执行删除操作吗？')){
    let res = await fetch(API_BASE_URL + '/questionnaire/delete' ,
        {method: 'POST', body: JSON.stringify(params),
          headers: {"Content-Type": "application/json"}}).then(res =>{
      return res.json()
    })
    let info = res.message
    alert(info)
    $("#questionnaireTable tr:not(:first)").empty()
    await getQuestionnaireInfo(projectId)
  }else{

  }


}

const getQuestionnaireInfo = async (id) =>{
  let params = {
    projectId: id
  }
  let res = await fetch(API_BASE_URL + '/questionnaire/list' ,
      {method: 'POST', body: JSON.stringify(params),
        headers: {"Content-Type": "application/json"}}).then(res =>{
    return res.json()
  })
  let info = res.data
  info.map((value, index) => {
    if (value.status !== '1'){
      $("#questionnaireTable").append(`
        <tr>
            <td>${index + 1}</td>
            <td>${value.name}</td>
            <td>${value.startTime}</td>
            <td>
            <button type="button" class="btn btn-link btn-red" onclick="handleClickPreview('${value.id}')">预览</button>
            <button type="button" class="btn btn-link" onclick="handleReleaseQuestionnaire('${value.id}')">发布</button>
            <button type="button" class="btn btn-link" onclick="handleDeleteQuestionnaire('${value.id}')">删除</button>
            <button type="button" class="btn btn-link btn-red" onclick="handleClickStatistic('${value.id}', '${value.name}')">统计</button>
          </td>
        </tr>`)
    } else {
      $("#questionnaireTable").append(`
        <tr>
            <td>${index + 1}</td>
            <td>${value.name}</td>
            <td>${value.startTime}</td>
            <td>
            <button type="button" class="btn btn-link btn-red" onclick="handleClickPreview('${value.id}')">预览</button>
            <button type="button" class="btn btn-link btn-red" onclick="handleClickLink('${value.id}')">链接</button>
            <button type="button" class="btn btn-link" onclick="handleCloseQuestionnaire('${value.id}')">关闭</button>
            <button type="button" class="btn btn-link" onclick="handleDeleteQuestionnaire('${value.id}')">删除</button>
            <button type="button" class="btn btn-link btn-red" onclick="handleClickStatistic('${value.id}', '${value.name}')">统计</button>
          </td>
        </tr>`)
    }

  })
}
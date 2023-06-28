let projectId = ""

onload = () => {
  $('#headerUsername').text($util.getItem('userInfo').username)
  $('#headerDivB').text('答卷查看')
  projectId  = $util.getPageParam('seeAnswer')
  console.log(projectId)
  fetchAnswerInfoList()
}


let pageNum = 1
let answerInfoList = []
// projectId
const fetchAnswerInfoList = () => {
  let params = {
    projectId,
    pageNum,
    pageSize: 10,
    username: $('#username').val()
  }
  $.ajax({
    url: API_BASE_URL + '/answer/list',
    type: 'POST',
    data: JSON.stringify(params),
    dataType: 'json',
    contentType: 'application/json',
    success(res) {
      $('#table #tbody').html('')
      answerInfoList = res.data
      answerInfoList.map((item, index) => {

        $('#table #tbody').append(`
          <tr>
            <td>${item.questionnaireName}</td>
            <td>${item.username}</td>
            <td>${item.answerTime}</td>
            <td>
              <button type="button" class="btn btn-link btn-red" 
              onclick="seeDetail('${item.questionnaireName}','${item.questionnaireId}',
               '${item.id}','${item.username}' )">明细</button>
            </td>
          </tr>
        `)
      })
    }
  })
}

const fetchUserList = () => {
  let params = {
    pageNum,
    pageSize: 10,
    username: $('#username').val()
  }
  $.ajax({
    url: API_BASE_URL + '/admin/queryUserList',
    type: 'POST',
    data: JSON.stringify(params),
    dataType: 'json',
    contentType: 'application/json',
    success(res) {
      $('#table #tbody').html('')
      userList = res.data
      userList.map((item, index) => {

        $('#table #tbody').append(`
          <tr>
            <td>${item.questionnaireName}</td>
            <td>${item.username}</td>
            <td>${item.answerTime}</td>
            <td>
              <button type="button" class="btn btn-link btn-red" 
              onclick="seeDetail('${item.questionnaireName}','${item.questionnaireId}',
               '${item.id}','${item.username}' )">明细</button>
            </td>
          </tr>
        `)
      })
    }
  })
}
const seeDetail = (questionnaireName,questionnaireId, id, username) =>{
  let item = {questionnaireName,questionnaireId, id, username}
  $util.setPageParam("answerInfo", item)
  $util.setPageParam("isPreview", '0')
  location.href = '/pages/answerDetail/index.html'
}

const handleTableChange = (page) => {
  if (page === 1) {
    if (pageNum === 1) return
    pageNum--
  } else if (page === 2) {
    pageNum++
  } else if (page === 3) {
    pageNum = +$('#goNum').val()
  }
  $('#currentPage').text(pageNum)
  fetchAnswerInfoList()
}



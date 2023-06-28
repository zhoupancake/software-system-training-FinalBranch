const handleConfirmModify = () => {
  $('#modifyTitleModal').modal('hide')
  $('.questionnaire-title > span').text(questionnaireTitle)
  $('.questionnaire-description > span').text(questionnaireDescription)
  handleModifyQuestionnaire()
}

const onQuestionnaireTitleInput = (e) => {
  questionnaireTitle = e.value
}

const onQuestionnaireDescriptionInput = (e) => {
  questionnaireDescription = e.value
}

const handleModifyQuestionnaire = () => {
  let params = {
    id: localStorage.getItem("questionnaireId"),
    name: $('#questionnaireTitle').val(),
    comment: $('#questionnaireDescription').val(),
  }
  if (!params.name) return alert('调查名不能为空！')
  if (!params.comment) return alert('说明不能为空！')
  $.ajax({
    url: API_BASE_URL + '/questionnaire/update',
    type: 'POST',
    data: JSON.stringify(params),
    dataType: 'json',
    contentType: 'application/json',
    success(res) {
      if (res.code === "666") {
        alert('修改成功')
      } else {
        alert("修改失败")
      }
    }
  })
}

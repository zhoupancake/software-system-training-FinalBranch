let answer = []
let singleQuestions = []
let multiQuestions = []
let questionList = []
let linkIds = []

$('.questionnaire-title').text(localStorage.getItem("questionnaireName"))
$('.questionnaire-description').text(localStorage.getItem("questionnaireDescription"))
const removeQuestionByLink = (radioName) =>{
    let linkObj = linkIds.find(value => {
        return value.radioName === radioName
    })
    if (!linkObj) return
    let params = {
        id: linkObj.linkId
    }
    $.ajax({
        url: API_BASE_URL + '/question/link',
        type: 'POST',
        data: JSON.stringify(params),
        dataType: 'json',
        contentType: 'application/json',
        success(res) {
            if (res.code === '666') {
                let item = res.data
                let index1 = 0
                questionList.forEach((value, index2) => {
                    value.option.forEach(value1 => {
                        if (item.id === value1.linkQuestionId){
                            index1 = index2
                        }
                    })
                })
                singleQuestions.splice(index1, 1)
                $(`#linkQuestion${index1}`).remove()

            }
        }
    })
}
const getQuestionByLink = (linkId, radioName) =>{
    let params = {
        id: linkId
    }
    $.ajax({
        url: API_BASE_URL + '/question/link',
        type: 'POST',
        data: JSON.stringify(params),
        dataType: 'json',
        contentType: 'application/json',
        success(res) {
            if (res.code === '666'){
                linkIds.push({linkId, radioName})
                console.log(res.data)
                let item = res.data
                let index = singleQuestions.length
                singleQuestions.push({questionId: item.id, name: 'chooseTerm'+ index})
                let str=''
                item.option.forEach((value, index1) => {
                    str += `<div style="display: flex; align-items: center; margin-bottom: 3px;">
          <label class="radio-inline">
            <input type="radio" name="${'chooseTerm'+ index}" value="${value.id}">${value.chooseTerm}
          </label>
        </div>`})
                let index1 = 0
                questionList.forEach((value, index2) => {
                    value.option.forEach(value1 => {
                        if (item.id === value1.linkQuestionId){
                            index1 = index2
                        }
                    })
                })
                console.log(index1)
                $(`#question${index1}`).append(`
        <div class="question" id="linkQuestion${index1}" data-type="1" data-problemIndex="${index}">
      <div class="top">
        <span class="question-title" id="questionTitle">${index +1}.${item.name}多选题</span>
        <span class="must-answer" id="mustAnswer">${item.isMust==='true'?'必答题':'非必答题'}</span>
      </div>
      <div class="bottom">
        ${str}
      </div>
        </div>
  `)
            } else {
                console.log('jjj')
                removeQuestionByLink(radioName)
            }
        }
    })
}

const getQuestionList = ()=>{
  let params = {
    id: localStorage.getItem("questionnaireId")
  }
  $.ajax({
    url: API_BASE_URL + '/questionnaire/questions',
    type: 'POST',
    data: JSON.stringify(params),
    dataType: 'json',
    contentType: 'application/json',
    success(res) {
      questionList = res.data
      questionList.map((item, index) => {
        switch (item.type){
            case '1':
              singleQuestions.push({questionId: item.id, name: 'chooseTerm'+index})
              let str=''
              item.option.forEach((value, index1) => {
                  if (value.linkQuestionId === null){
                      str += `<div style="display: flex; align-items: center; margin-bottom: 3px;">
          <label class="radio-inline">
            <input type="radio" data-link-question-id="'null'" name="${'chooseTerm'+index}" value="${value.id}">${value.chooseTerm}
          </label>
        </div>`
                  } else {
                      str += `<div style="display: flex; align-items: center; margin-bottom: 3px;">
          <label class="radio-inline">
            <input type="radio" data-link-question-id="${value.linkQuestionId}"  name="${'chooseTerm'+index}" value="${value.id}">${value.chooseTerm}
          </label>
          </div>`
                  }
                })
            $('#problem').append(`
    <div class="question" id="question${index}" data-type="1" data-problemIndex="${index}">
      <div class="top">
        <span class="question-title" id="questionTitle">${index +1}.${item.name},单选题</span>
        <span class="must-answer" id="mustAnswer">${item.isMust==='true'?'必答题':'非必答题'}</span>
      </div>
      <div class="bottom" id="optionBox">
      ${str}
      </div>
    </div>`)
                break
          case '2':
              multiQuestions.push({questionId: item.id, name: 'chooseTerm'+index})
              let str1=''
              item.option.forEach((value, index1) => {
                  str1 += `<div style="display: flex; align-items: center; margin-bottom: 3px;">
          <label class="radio-inline">
            <input type="checkbox" name="${'chooseTerm'+index}" class="link-option"  value="${value.id}">${value.chooseTerm}
          </label>
        </div>`})
              $('#problem').append(`
    <div class="question" id="question${index}" data-type="1" data-problemIndex="${index}">
      <div class="top">
        <span class="question-title" id="questionTitle">${index +1}.${item.name}多选题</span>
        <span class="must-answer" id="mustAnswer">${item.isMust==='true'?'必答题':'非必答题'}</span>
      </div>
      <div class="bottom">
        ${str1}
      </div>
    </div>
  `)
                break
        }
      })
        $('input[type="radio"][data-link-question-id]').change( function(e) {
            // e.stopPropagation()
            let linkQuestionId = $(this).data("link-question-id");
            let radioName = $(this)[0].getAttribute("name")
            console.log(radioName)
            console.log(linkQuestionId)
            if ($(this).is(":checked")) {

                // 选中了具有linkQuestionId的radio
                getQuestionByLink(linkQuestionId, radioName)
                console.log(`选中了具有linkQuestionId ${linkQuestionId} 的radio`);
            }
        });

    }
  })
}
const handleChange = () =>{
    let checked = $('input[type="radio"][data-link-question-id]:not(:checked)').val()
    console.log(checked)
}
const handleSubmit = ()=>{
    singleQuestions.forEach((value, index) => {
        let selectedValue = $(`input[name= ${value.name}]:checked`).val();
        console.log(selectedValue);
        answer.push({questionId: value.questionId, optionId: selectedValue})
    })
    multiQuestions.forEach(value => {
        $(`input[name=${value.name}]:checkbox:checked`).each(function(){
            answer.push({questionId: value.questionId, optionId: $(this).val()})
        })
    })

    let params = {
        roleId ,
        questionnaireId: localStorage.getItem("questionnaireId"),
        answer: answer
    }
    $.ajax({
        url: API_BASE_URL + '/answer/submit',
        type: 'POST',
        data: JSON.stringify(params),
        dataType: 'json',
        contentType: 'application/json',
        success(res) {
            alert('提交成功')
            location.href = '/pages/questionnaire/index.html'
        }
    })

}
let roleId
onload = () => {
    roleId = $util.getItem('userInfo').id
    getQuestionList()

}

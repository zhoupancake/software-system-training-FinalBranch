let answer = []
let singleQuestions = []
let multiQuestions = []
let seeDetail = {}
onload = ()=>{
    let isPreview = $util.getPageParam("isPreview")
    console.log(isPreview)
    if (isPreview !== '1'){
        seeDetail = $util.getPageParam("answerInfo")
        $('.answer-role').text(`答卷人：${seeDetail.username}`)
        getQuestionList(seeDetail.questionnaireId, isPreview)
    } else {
        console.log('hhh')
        let id = localStorage.getItem("questionnaireId")
        getQuestionList(id, isPreview)
    }

}
const handleRadioChange = ()=>{

}
const getButtonAnswer = async (id)=>{
    let params = {
        id: id
    }
    let res = await fetch('/answer/review', {method: 'POST', body: JSON.stringify(params),
        headers: {"Content-Type": "application/json"}}).then(e =>{
        return e.json()
    })
    if (res.code === '666'){
        return res.data
    }
}
const getQuestionList = async (id, isPreview)=>{
    let answerData = []
    if (isPreview !== '1'){
        answerData = await getButtonAnswer(seeDetail.id)
    }
  let params = {
    id: id
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
          answer.push({questionId: item.id, answer: '1'})
        switch (item.type){
            case '1':
              singleQuestions.push({questionId: item.id, name: 'chooseTerm'+index})
              let str=''
                let found = {}
                if (isPreview !== '1'){
                    found = answerData.find(value1 => {
                        return item.id === value1.questionId
                    })
                }
              item.option.forEach((value, index1) => {
                  if (isPreview !== '1'){
                      if (found.optionId === value.id){
                          str += `<div style="display: flex; align-items: center; margin-bottom: 3px;">
            <label class="radio-inline">
            <input type="radio" name="${'chooseTerm'+index}" value="${index1}" checked>${value.chooseTerm}
            </label>`
                      } else {
                          str += `<div style="display: flex; align-items: center; margin-bottom: 3px;">
          <label class="radio-inline">
            <input type="radio" name="${'chooseTerm'+index}" value="${index1}">${value.chooseTerm}
          </label>`
                      }
                  } else {
                      str += `<div style="display: flex; align-items: center; margin-bottom: 3px;">
          <label class="radio-inline">
            <input type="radio" name="${'chooseTerm'+index}" value="${index1}">${value.chooseTerm}
          </label>
        </div>`
                  }
                })
            $('#problem').append(`
    <div class="question" id="question1" data-type="1" data-problemIndex="1">
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
              singleQuestions.push({questionId: item.id, name: 'chooseTerm'+index})
              let str1=''
              item.option.forEach((value, index1) => {
                  if (isPreview !== '1'){
                      let find =false
                      answerData.forEach(value1 => {
                          if (value1.questionId === item.id && value1.optionId === value.id){
                              str1 += `<div style="display: flex; align-items: center; margin-bottom: 3px;">
          <label class="radio-inline">
            <input type="checkbox" name="${'chooseTerm'+index}" onchange="" checked value="${index1}">${value.chooseTerm}
          </label>
        </div>`
                              find = true
                              return
                          }
                          })
                      if (!find){
                          str1 += `<div style="display: flex; align-items: center; margin-bottom: 3px;">
          <label class="radio-inline">
            <input type="checkbox" name="${'chooseTerm'+index}" onchange="" value="${index1}">${value.chooseTerm}
          </label>
        </div>`
                      }

                  } else {
                      str1 += `<div style="display: flex; align-items: center; margin-bottom: 3px;">
          <label class="radio-inline">
            <input type="checkbox" name="${'chooseTerm'+index}" onchange="" value="${index1}">${value.chooseTerm}
          </label>
        </div>`
                  }
                 })
              $('#problem').append(`
    <div class="question" id="question1" data-type="1" data-problemIndex="1">
      <div class="top">
        <span class="question-title" id="questionTitle">${index +1}.${item.name},多选题</span>
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
    }
  })
}
const handleBack = ()=>{
    history.back()

}


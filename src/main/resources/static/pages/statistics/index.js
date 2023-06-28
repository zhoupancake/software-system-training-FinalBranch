new Vue({
    el: '#app',
    data: function (){
        return{
            questionnaireName: localStorage.getItem("questionnaireName"),
            questionData: [],
            dialogVisible: false,
            questionStat: [],
            sameQuestionStat: {}
        }
    },
    async beforeMount(){
        await this.getQuestionList()
        await this.initStat()
        await this.initSameQuestion()
        await this.getRelateStatistic(0)
    },
    methods: {
        async getQuestionList(){
            let params = {
                questionnaireId: localStorage.getItem("questionnaireId")
            }
            let res = await fetch('/question/all', {method: 'POST', body: JSON.stringify(params),
                headers: {"Content-Type": "application/json"}}).then(e =>{
                return e.json()
            })
            this.questionData = res.data
            this.questionData.map(value => {
                Vue.set(value, "activeName", 'table')
                Vue.set(value, "isTable", true)
            })
        },
        async getQuestionCount(id){
            let params = {
                id: id
            }
            let res = await fetch('/question/statistic', {method: 'POST', body: JSON.stringify(params),
                headers: {"Content-Type": "application/json"}}).then(e =>{
                return e.json()
            })
            let data = res.data.option
            let optionStat =  data.map((value, index) => {
                return {name: value.chooseTerm, value: value.personCount}
            })
            return optionStat
        },
        async getSameQuestions(question){
            let params = {
                questionnaireId: localStorage.getItem("questionnaireId"),
                name: question.name
            }
            let res = await fetch('/questionnaire/same', {method: 'POST', body: JSON.stringify(params),
                headers: {"Content-Type": "application/json"}}).then(e =>{
                return e.json()
            })
            return res.data
        },
        async initSameQuestion(){
             this.questionData.forEach(async value =>{
                 let res = await this.getSameQuestions(value)
                 Vue.set(value, "sameQuestions", res)
             })
        },
        async clickSameQuestion(index){
            await this.getRelateStatistic(index)
            this.dialogVisible = true
        },
        async getRelateStatistic(index){
            console.log(this.questionData[index])
            let params = {
                id: this.questionData[index].id,
                name: this.questionData[index].name
            }
            let res = await fetch('/questionnaire/relate', {method: 'POST', body: JSON.stringify(params),
                headers: {"Content-Type": "application/json"}}).then(e =>{
                return e.json()
            })
            this.sameQuestionStat = res.data
            Vue.set(this.sameQuestionStat, "activeName", 'table')
            Vue.set(this.sameQuestionStat, "isTable", true)
        },
        handleDialogRadioClick(val){
            console.log(val)
            switch (val){
                case 'table':
                    this.sameQuestionStat.isTable = true
                    break
                case 'pie':
                    this.sameQuestionStat.isTable = false
                    this.$nextTick(()=> {
                        let chartContainer = document.getElementById(`question-dialog`);
                        let chartInstance = echarts.getInstanceByDom(chartContainer);
                        if (chartInstance) {
                            chartInstance.dispose()
                        }
                        chartContainer.style.width = '100%'
                        chartContainer.style.height = '250px'
                        // 初始化 ECharts 实例
                        let chart = echarts.init(chartContainer);
                        let options = {
                            title: {
                                text: this.sameQuestionStat.name,
                                left: 'center'
                            },
                            tooltip: {
                                trigger: 'item'
                            },
                            legend: {
                                top: '10%',
                                left: 'center'
                            },
                            series: [{
                                type: 'pie',
                                radius: '50%',
                                name: '选项',
                                data: this.sameQuestionStat.option.map(value =>{
                                    return {name: value.chooseTerm, value: value.personCount}
                                })
                            }]
                        };
                        chart.setOption(options)
                    })
                    break
                case 'ring':
                    this.sameQuestionStat.isTable = false
                    this.$nextTick(()=> {
                        let chartContainer = document.getElementById(`question-dialog`);
                        let chartInstance = echarts.getInstanceByDom(chartContainer);
                        if (chartInstance) {
                            chartInstance.dispose()
                        }
                        chartContainer.style.width = '100%'
                        chartContainer.style.height = '250px'
                        // 初始化 ECharts 实例
                        let chart = echarts.init(chartContainer);
                        let options = {
                            title: {
                                text: this.sameQuestionStat.name,
                                left: 'center'
                            },
                            legend: {
                                top: '10%',
                                left: 'center'
                            },
                            series: [{
                                type: 'pie',
                                name: '选项',
                                radius: ['40%', '70%'],
                                avoidLabelOverlap: false,
                                itemStyle: {
                                    borderRadius: 10,
                                    borderColor: '#fff',
                                    borderWidth: 2
                                },
                                label: {
                                    show: false,
                                    position: 'center'
                                },
                                emphasis: {
                                    label: {
                                        show: true,
                                        fontSize: 40,
                                        fontWeight: 'bold'
                                    }
                                },
                                labelLine: {
                                    show: false
                                },
                                data: this.sameQuestionStat.option.map(value =>{
                                    return {name: value.chooseTerm, value: value.personCount}
                                })
                            }]
                        };
                        chart.setOption(options)
                    })
                    break
                case 'bar':
                    this.sameQuestionStat.isTable = false
                    this.$nextTick(()=>{
                        let chartContainer = document.getElementById(`question-dialog`);
                        let chartInstance = echarts.getInstanceByDom(chartContainer);
                        if (chartInstance){
                            chartInstance.dispose()
                        }

                        chartContainer.style.width = '100%'
                        chartContainer.style.height = '250px'
                        // 初始化 ECharts 实例
                        let chart = echarts.init(chartContainer);
                        let xData = []
                        let valueData = []
                        this.sameQuestionStat.option.forEach(value => {
                            xData.push(value.chooseTerm)
                            valueData.push(value.personCount)
                        })
                        let options = {
                            title: {
                                text: this.sameQuestionStat.name,
                                left: 'center'
                            },
                            xAxis: {
                                type: 'category',
                                data: xData
                            },
                            yAxis: {
                                type: 'value'
                            },
                            tooltip: {
                                trigger: 'axis',
                                formatter: '{b}<br/>{c}人',
                                axisPointer: {
                                    type: 'shadow'
                                }
                            },
                            series: [{
                                type: 'bar',
                                showBackground: true,
                                backgroundStyle: {
                                    color: 'rgba(180, 180, 180, 0.2)'
                                },
                                data: valueData
                            }]
                        };
                        chart.setOption(options)
                    })
                    break
                case 'strip':
                    this.sameQuestionStat.isTable = false
                    this.$nextTick(()=>{
                        let chartContainer = document.getElementById(`question-dialog`);
                        let chartInstance = echarts.getInstanceByDom(chartContainer);
                        if (chartInstance){
                            chartInstance.dispose()
                        }

                        chartContainer.style.width = '100%'
                        chartContainer.style.height = '250px'
                        // 初始化 ECharts 实例
                        let chart = echarts.init(chartContainer);
                        let xData = []
                        let valueData = []
                        this.sameQuestionStat.option.forEach(value => {
                            xData.push(value.chooseTerm)
                            valueData.push(value.personCount)
                        })
                        let options = {
                            title: {
                                text: this.sameQuestionStat.name,
                                left: 'center'
                            },
                            yAxis: {
                                type: 'category',
                                data: xData
                            },
                            xAxis: {
                                type: 'value'
                            },
                            tooltip: {
                                trigger: 'axis',
                                formatter: '{b}<br/>{c}人',
                                axisPointer: {
                                    type: 'shadow'
                                }
                            },
                            series: [{
                                type: 'bar',
                                showBackground: true,
                                backgroundStyle: {
                                    color: 'rgba(180, 180, 180, 0.2)'
                                },
                                data: valueData
                            }]
                        };
                        chart.setOption(options)
                    })
                    break
                case 'broke':
                    this.sameQuestionStat.isTable = false
                    this.$nextTick(()=>{
                        let chartContainer = document.getElementById(`question-dialog`);
                        let chartInstance = echarts.getInstanceByDom(chartContainer);
                        if (chartInstance){
                            chartInstance.dispose()
                        }

                        chartContainer.style.width = '100%'
                        chartContainer.style.height = '250px'
                        // 初始化 ECharts 实例
                        let chart = echarts.init(chartContainer);
                        let xData = []
                        let valueData = []
                        this.sameQuestionStat.option.forEach(value => {
                            xData.push(value.chooseTerm)
                            valueData.push(value.personCount)
                        })
                        let options = {
                            title: {
                                text: this.sameQuestionStat.name,
                                left: 'center'
                            },
                            xAxis: {
                                type: 'category',
                                data: xData
                            },
                            yAxis: {
                                type: 'value'
                            },
                            tooltip: {
                                trigger: 'axis',
                                formatter: '{b}<br/>{c}人',
                                axisPointer: {
                                    type: 'shadow'
                                }
                            },
                            series: [{
                                type: 'line',
                                data: valueData
                            }]
                        };
                        chart.setOption(options)
                    })
                    break
                default:
                    break
            }
        },
        handleButtonClick(index, val){
            if (val === 'table'){
                this.questionData[index].isTable = true

            } else if (val === 'pie'){
                this.questionData[index].isTable = false
                this.$nextTick(()=>{
                    let chartContainer = document.getElementById(`question-box${index}`);
                    console.log(chartContainer)
                    let chartInstance = echarts.getInstanceByDom(chartContainer);
                    if (chartInstance){
                        chartInstance.dispose()
                    }
                    chartContainer.style.width = '100%'
                    chartContainer.style.height = '250px'
                    // 初始化 ECharts 实例
                    let chart = echarts.init(chartContainer);
                    let options = {
                        title: {
                            text: `第${index+1}题：`+ this.questionData[index].name,
                            left: 'center'
                        },
                        tooltip: {
                            trigger: 'item'
                        },
                        legend: {
                            top: '10%',
                            left: 'center'
                        },
                        series: [{
                            type: 'pie',
                            radius: '50%',
                            name: '选项',
                            data: this.questionStat[index].stat
                        }]
                    };
                    chart.setOption(options)
                })

            } else if (val === 'ring'){
                this.questionData[index].isTable = false
                this.$nextTick(()=>{
                    let chartContainer = document.getElementById(`question-box${index}`);
                    let chartInstance = echarts.getInstanceByDom(chartContainer);
                    if (chartInstance){
                        chartInstance.dispose()
                    }
                    chartContainer.style.width = '100%'
                    chartContainer.style.height = '250px'
                    // 初始化 ECharts 实例
                    let chart = echarts.init(chartContainer);
                    console.log(this.questionStat)
                    let options = {
                        title: {
                            text: `第${index+1}题：`+ this.questionData[index].name,
                            left: 'center'
                        },
                        legend: {
                            top: '10%',
                            left: 'center'
                        },
                        series: [{
                            type: 'pie',
                            name: '选项',
                            radius: ['40%', '70%'],
                            avoidLabelOverlap: false,
                            itemStyle: {
                                borderRadius: 10,
                                borderColor: '#fff',
                                borderWidth: 2
                            },
                            label: {
                                show: false,
                                position: 'center'
                            },
                            emphasis: {
                                label: {
                                    show: true,
                                    fontSize: 40,
                                    fontWeight: 'bold'
                                }
                            },
                            labelLine: {
                                show: false
                            },
                            data: this.questionStat[index].stat
                        }]
                    };
                    chart.setOption(options)
                })
            } else if (val === 'bar'){
                this.questionData[index].isTable = false
                this.$nextTick(()=>{
                    let chartContainer = document.getElementById(`question-box${index}`);
                    let chartInstance = echarts.getInstanceByDom(chartContainer);
                    if (chartInstance){
                        chartInstance.dispose()
                    }

                    chartContainer.style.width = '100%'
                    chartContainer.style.height = '250px'
                    // 初始化 ECharts 实例
                    let chart = echarts.init(chartContainer);
                    let xData = []
                    let valueData = []
                    this.questionStat[index].stat.forEach(value => {
                        xData.push(value.name)
                        valueData.push(value.value)
                    })
                    let options = {
                        title: {
                            text: `第${index+1}题：`+ this.questionData[index].name,
                            left: 'center'
                        },
                        xAxis: {
                            type: 'category',
                            data: xData
                        },
                        yAxis: {
                            type: 'value'
                        },
                        tooltip: {
                            trigger: 'axis',
                            formatter: '{b}<br/>{c}人',
                            axisPointer: {
                                type: 'shadow'
                            }
                        },
                        series: [{
                            type: 'bar',
                            showBackground: true,
                            backgroundStyle: {
                                color: 'rgba(180, 180, 180, 0.2)'
                            },
                            data: valueData
                        }]
                    };
                    chart.setOption(options)
                })
            } else if (val === 'strip'){
                this.questionData[index].isTable = false
                this.$nextTick(()=>{
                    let chartContainer = document.getElementById(`question-box${index}`);
                    let chartInstance = echarts.getInstanceByDom(chartContainer);
                    if (chartInstance){
                        chartInstance.dispose()
                    }

                    chartContainer.style.width = '100%'
                    chartContainer.style.height = '250px'
                    // 初始化 ECharts 实例
                    let chart = echarts.init(chartContainer);
                    let xData = []
                    let valueData = []
                    this.questionStat[index].stat.forEach(value => {
                        xData.push(value.name)
                        valueData.push(value.value)
                    })
                    let options = {
                        title: {
                            text: `第${index+1}题：`+ this.questionData[index].name,
                            left: 'center'
                        },
                        yAxis: {
                            type: 'category',
                            data: xData
                        },
                        xAxis: {
                            type: 'value'
                        },
                        tooltip: {
                            trigger: 'axis',
                            formatter: '{b}<br/>{c}人',
                            axisPointer: {
                                type: 'shadow'
                            }
                        },
                        series: [{
                            type: 'bar',
                            showBackground: true,
                            backgroundStyle: {
                                color: 'rgba(180, 180, 180, 0.2)'
                            },
                            data: valueData
                        }]
                    };
                    chart.setOption(options)
                })
            } else {
                this.questionData[index].isTable = false
                this.$nextTick(()=>{
                    let chartContainer = document.getElementById(`question-box${index}`);
                    let chartInstance = echarts.getInstanceByDom(chartContainer);
                    if (chartInstance){
                        chartInstance.dispose()
                    }

                    chartContainer.style.width = '100%'
                    chartContainer.style.height = '250px'
                    // 初始化 ECharts 实例
                    let chart = echarts.init(chartContainer);
                    let xData = []
                    let valueData = []
                    this.questionStat[index].stat.forEach(value => {
                        xData.push(value.name)
                        valueData.push(value.value)
                    })
                    let options = {
                        title: {
                            text: `第${index+1}题：`+ this.questionData[index].name,
                            left: 'center'
                        },
                        xAxis: {
                            type: 'category',
                            data: xData
                        },
                        yAxis: {
                            type: 'value'
                        },
                        tooltip: {
                            trigger: 'axis',
                            formatter: '{b}<br/>{c}人',
                            axisPointer: {
                                type: 'shadow'
                            }
                        },
                        series: [{
                            type: 'line',
                            data: valueData
                        }]
                    };
                    chart.setOption(options)
                })
            }
        },
        async initStat(){
            for (let value of this.questionData) {
                let stat = await this.getQuestionCount(value.id)
                this.questionStat.push({id: value.id, stat})
            }
            let swap
            this.questionData.forEach((value, index) =>{
                this.questionStat.forEach((value1, index1) => {
                    if (value.id === value1.id){
                        swap = this.questionStat[index1]
                        this.questionStat[index1] = value1
                        this.questionStat[index] = swap
                    }
                })
            })
        },

        handleClickBack(){
            history.back()
        }
    }
})
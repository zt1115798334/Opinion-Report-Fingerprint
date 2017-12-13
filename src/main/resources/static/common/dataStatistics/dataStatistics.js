$(function () {

    searchDisplayMenuFun("#li_005", "#lli_011");

    showMenuTitle("数据统计");

    /**
     * 获取数据
     */
    searchDataFun();

});

/**
 * 获取数据
 */
function searchDataFun() {
    var url = "/dataStatistics/searchData";
    var params = {};
    execAjax(url, params, callback);

    function callback(result) {

        //近一周舆情上报数
        var myChart1 = echarts.init(document.getElementById('echart-ds1'));

        // 图表屏幕自适应
        setTimeout(function () {
            window.onresize = function () {
                myChart1.resize();
                /*myChart2.resize();
                myChart3.resize();
                myChart4.resize();
                myChart5.resize();
                myChart6.resize();
                myChart7.resize();*/
            }
        }, 200);


        /**
         * 舆情上报分析 -- 折线图信息
         */
        dataAnalysisChartFun(myChart1);

        /**
         * 舆情上报分析 -- 比例信息
         */
        dataAnalysisProportionFun();

        /**
         * 舆情上报分析 -- 表信息
         */
        dataAnalysisTableFun();

        /**
         * 本周上报舆情等级分布
         */
        dataLevelDistributionFun(myChart1);

        /**
         * 本周上报舆情来源分布
         */
        dataSourceDistributionFun(myChart1);

        /**
         * 本周上报舆情等级来源  -- 表
         */
        dataLevelSourceTableFun();

        /**
         * 本周上报舆情影响力分析 -- 图
         */
        dataEffectDistributionFun(myChart1);

        /**
         * 本周上报舆情影响力分析 -- 表
         */
        dataEffectTableFun();
    }
}

/**
 * 舆情上报分析 -- 折线图信息
 */
function dataAnalysisChartFun(myChart1) {
    var url = "/dataStatistics/dataAnalysisChart";
    var params = {};
    execAjax(url, params, callback);

    function callback(result) {
        if (result.success) {
            var data = result.data;

            var date = data.date;
            var value = data.value;

            var option1 = {
                color: ["#03b2fc"],
                title: {
                    text: '近一周舆情上报数',
                    textStyle: {
                        fontSize: '14px'
                    }
                },
                tooltip: {
                    trigger: 'axis'
                },
                grid: {
                    left: '3%',
                    right: '0px',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: date,
                    axisLine: {
                        show: true,
                        lineStyle: {
                            color: '#8e99a8',
                            width: 1,
                            type: 'solid'
                        }
                    }
                },
                yAxis: [{
                    type: 'value',
                    axisTick: {
                        show: false
                    },
                    axisLine: {
                        show: true,
                        lineStyle: {
                            color: '#8e99a8',
                            width: 1,
                            type: 'solid'
                        }
                    },
                    axisLabel: {
                        margin: 10,
                    },

                    splitLine: {
                        show: false,
                        lineStyle: {
                            color: '#57617B'
                        }
                    },
                    splitArea: {
                        show: false
                    },
                }],
                series: [{
                    name: '舆情上报数',
                    type: 'line',
                    smooth: true,
                    areaStyle: {
                        normal: {
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                offset: 0,
                                color: 'rgba(3, 178, 252, 0.7)'
                            }, {
                                offset: 1,
                                color: 'rgba(3, 178, 252, 0)'
                            }], false),
                            shadowColor: 'rgba(0, 0, 0, 0.1)',
                            shadowBlur: 10
                        }
                    },
                    data: value
                }]
            };
            myChart1.setOption(option1);
            setTimeout(function () {
                var dataAnalysisChartBase64 = myChart1.getDataURL('png');
                $("#dataAnalysisChartBase64").val(dataAnalysisChartBase64);
            }, 5000);

        }
    }
}

/**
 * 舆情上报分析 -- 比例信息
 */
function dataAnalysisProportionFun() {
    var url = "/dataStatistics/dataAnalysisProportion";
    var params = {};
    execAjax(url, params, callback);

    function callback(result) {
        if (result.success) {

            var upImgUrl = "/assets/images/top_03.png";
            var downImgUrl = "/assets/images/bottom_03.png";

            var data = result.data;

            var allInfo = data.allInfo;
            var adoptInfo = data.adoptInfo;

            var _allInfo = $(".allInfo");
            _allInfo.html(allInfo.weekCount);
            if (allInfo.type == "down") {
                _allInfo.next().find("img").attr("src", downImgUrl);
            }
            if (allInfo.type == "up") {
                _allInfo.next().find("img").attr("src", upImgUrl);
            }
            _allInfo.next().find("span").html(allInfo.num + "% <span class=\"colorglay\">同比上周</span>");

            var _adoptInfo = $(".adoptInfo");
            _adoptInfo.html(adoptInfo.weekCount);
            if (adoptInfo.type == "down") {
                _adoptInfo.next().find("img").attr("src", downImgUrl);
            }
            if (adoptInfo.type == "up") {
                _adoptInfo.next().find("img").attr("src", upImgUrl);
            }
            _adoptInfo.next().find("span").html(adoptInfo.num + "% <span class=\"colorglay\">同比上周</span>");

        }
    }
}

/**
 * 舆情上报分析 -- 表信息
 */
function dataAnalysisTableFun() {
    var url = "/dataStatistics/dataAnalysisTable";
    var params = {};
    execAjax(url, params, callback);

    function callback(result) {
        if (result.success) {
            var data = result.data;
            var _table = $("table.dataAnalysisTable");


            var date = data.date;
            var dateHtml = '<th>日期</th>';
            $.each(date, function (index, value) {
                dateHtml += '<th>' + value + '</th>';
            });
            _table.find(".date").html(dateHtml);
            var reportCount = data.reportCount;
            var reportCountHtml = '<td>舆情上报数</td>';
            $.each(reportCount, function (index, value) {
                // reportCountHtml += '<td><a target="_blank" href="">' + value + '</a></td>';
                reportCountHtml += '<td>' + value + '</td>';
            });
            _table.find(".report").html(reportCountHtml);
            var adoptCount = data.adoptCount;
            var adoptCountHtml = '<td>舆情采纳数</td>';
            $.each(adoptCount, function (index, value) {
                // adoptCountHtml += '<td><a target="_blank" href="">' + value + '</a></td>';
                adoptCountHtml += '<td>' + value + '</td>';
            });
            _table.find(".adopt").html(adoptCountHtml);

        }
    }
}

/**
 * 本周上报舆情等级分布
 */
function dataLevelDistributionFun(myChart1) {
    var url = "/dataStatistics/dataLevelDistribution";
    var params = {};
    execAjax(url, params, callback);

    function callback(result) {
        if (result.success) {
            var data = result.data;

            var info = data.info;
            var name = data.name;
        }
    }
}

/**
 * 本周上报舆情来源分布
 */
function dataSourceDistributionFun(myChart1) {
    var url = "/dataStatistics/dataSourceDistribution";
    var params = {};
    execAjax(url, params, callback);

    function callback(result) {
        if (result.success) {
            var data = result.data;

            var info = data.info;
            var name = data.name;
        }
    }
}

/**
 * 本周上报舆情等级来源  -- 表
 */
function dataLevelSourceTableFun() {
    var url = "/dataStatistics/dataLevelSourceTable";
    var params = {};
    execAjax(url, params, callback);

    function callback(result) {
        if (result.success) {
            var data = result.data;

            var date = data.date;
            var dateHtml = '<th>日期</th>';
            $.each(date, function (index, value) {
                dateHtml += '<th>' + value + '</th>';
            });

            var redReportLevelCount = data.redReportLevelCount;
            var redReportLevelCountHtml = '<th>红色等级数</th>';
            $.each(redReportLevelCount, function (index, value) {
                redReportLevelCountHtml += '<th>' + value + '</th>';
            });
            var orangeReportLevelCount = data.orangeReportLevelCount;
            var orangeReportLevelCountHtml = '<th>橙色等级数</th>';
            $.each(orangeReportLevelCount, function (index, value) {
                orangeReportLevelCountHtml += '<th>' + value + '</th>';
            });
            var orangeReportLevelCount = data.orangeReportLevelCount;
            var orangeReportLevelCountHtml = '<th>黄色等级数</th>';
            $.each(orangeReportLevelCount, function (index, value) {
                orangeReportLevelCountHtml += '<th>' + value + '</th>';
            });

            var networkSourceTypeCount = data.networkSourceTypeCount;
            var networkSourceTypeCountHtml = '<th>网络</th>';
            $.each(networkSourceTypeCount, function (index, value) {
                networkSourceTypeCountHtml += '<th>' + value + '</th>';
            });
            var mediaSourceTypeCount = data.mediaSourceTypeCount;
            var mediaSourceTypeCountHtml = '<th>媒体</th>';
            $.each(mediaSourceTypeCount, function (index, value) {
                mediaSourceTypeCountHtml += '<th>' + value + '</th>';
            });
            var sceneSourceTypeCount = data.sceneSourceTypeCount;
            var sceneSourceTypeCountHtml = '<th>现场</th>';
            $.each(sceneSourceTypeCount, function (index, value) {
                sceneSourceTypeCountHtml += '<th>' + value + '</th>';
            });
            var otherSourceTypeCount = data.otherSourceTypeCount;
            var otherSourceTypeCountHtml = '<th>其他</th>';
            $.each(otherSourceTypeCount, function (index, value) {
                otherSourceTypeCountHtml += '<th>' + value + '</th>';
            });

        }
    }
}

/**
 * 本周上报舆情影响力分析 -- 图
 */
function dataEffectDistributionFun(myChart1) {
    var url = "/dataStatistics/dataEffectDistribution";
    var params = {};
    execAjax(url, params, callback);

    function callback(result) {
        if (result.success) {
            var data = result.data;

            var date = data.date;

            var clickCount = data.clickCount;
            var commentCount = data.commentCount;
            var estimateCount = data.estimateCount;

        }
    }
}

/**
 * 本周上报舆情影响力分析 -- 表
 */
function dataEffectTableFun() {
    var url = "/dataStatistics/dataEffectTable";
    var params = {};
    execAjax(url, params, callback);

    function callback(result) {
        if (result.success) {
            var data = result.data;

            var date = data.date;
            var dateHtml = '<th>日期</th>';
            $.each(date, function (index, value) {
                dateHtml += '<th>' + value + '</th>';
            });

            var clickCount = data.clickCount;
            var clickCountHtml = '<th>点击数</th>';
            $.each(clickCount, function (index, value) {
                clickCountHtml += '<th>' + value + '</th>';
            });
            var commentCount = data.commentCount;
            var commentCountHtml = '<th>评论数</th>';
            $.each(commentCount, function (index, value) {
                commentCountHtml += '<th>' + value + '</th>';
            });
            var estimateCount = data.estimateCount;
            var estimateCountHtml = '<th>预估值</th>';
            $.each(estimateCount, function (index, value) {
                estimateCountHtml += '<th>' + value + '</th>';
            });
        }
    }
}

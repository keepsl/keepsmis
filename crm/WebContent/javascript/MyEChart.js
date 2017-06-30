function MyEChart(obj){
	if(!obj){
		return false;
	}
	if(!obj.url){
		return false;
	}
	this.echartId = obj.echartId?obj.echartId:'echartId';
	this.title = obj.title?obj.title:'';
	this.titletext = obj.titletext?obj.titletext:'';
	this.titlesubtext = obj.titlesubtext?obj.titlesubtext:'';
	this.labname = obj.labname?obj.labname:'';
	this.url = obj.url;
	MyEChart.prototype.loadMyEChart = function(){
    	// 基于准备好的dom，初始化echarts实例
	    var myChart = echarts.init(document.getElementById(this.echartId));

	    // 指定图表的配置项和数据
	    var option = {
	    	color: ['#3398DB'],
	        title: {
	        	text: this.titletext,
	            subtext: this.titlesubtext,
	            left: 'center',
	            top:10
	        },
	        tooltip: {trigger: 'axis'},
	        legend: {
	        },
	        xAxis: {
	        	type : 'category',
	            data : [],
	            axisTick: {
	                alignWithLabel: true
	            }
	        },
	        yAxis: {},
	        series: [{
	            type: 'bar',
	            data: []
	        }]
	    };
	    myChart.setOption(option);
	    $.getJSON(this.url,function(json){
    		if(json&&json.code==1){
    		    myChart.setOption({
                    xAxis: {
                        data: json.recored.categories
                    },  
                    series: [{
        	            name:this.labname,
        	            barWidth: '40%',
                        data: json.recored.data
                    }]  
                });
    		}else{
    			layer.closeAll();
    			Log.e(json.msg);
    		}
    	});
	};
}
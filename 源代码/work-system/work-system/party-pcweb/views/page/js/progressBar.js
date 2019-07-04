// JavaScript Document

$(function(){
	//a 底色，b 加载色 , w 展示宽度，h 展示高度
	var a="#2AA15D";
	var b="#dfdfdf";
	var w="200px";
	var h="15px";
	var div=$(".div");//进度条要插入的地方
	var barb=function(){
		div.each(function(){
			var width=$(this).attr('w');
			var barbox='<dl class="barbox"><dd class="barline"><div w="'+width+'" class="charts" style="width:0px"><d></d></div></dd></dl>';
			$(this).append(barbox);
		})
	}
	
	var amimeat=function(){
		$(".charts").each(function(i,item){
			var wi=parseInt($(this).attr("w"));
			$(item).animate({width: wi+"%"},1000,function(){//一天内走完
				$(this).children('d').html(wi+"%");
			});
		});
	}
	var barbCss=function(a,b){
		$(".barbox").css({
			"height":h,
			"line-height":h,
			"text-align":"center",
			"color":"#fff",
		})
		$(".barbox>dd").css({
			"float":"left"
		})	
		$(".barline").css({
			"width":w,
			"background":b,
			"height":h,
			"overflow":"hidden",
			"display":"inline",
			"position":"relative",
			"border-radius":"8px",
		})
		$(".barline>d").css({
			"position":"absolute",
			"top":"0px",
		})
		$(".charts").css({
			"background":a,
			"height":h,
			"width":"0px",
			"overflow":"hidden",
			"border-radius":"8px"
		})
	}
	barb();
	amimeat();
	barbCss(a,b);
})


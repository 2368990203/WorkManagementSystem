 
var Vote={};
Vote.ListShow=(function(){
 var longWidth;
 var percentArr=[];
 var shortWidth=[];
 var spanArr=[];
  
 /*初始化*/
 function init(o){
  voteId=o.id;
  longWidth=o.width;
  percentArr=o.percent;
  shortWidth=calWidth();
  spanArr=findSpans();
 }
 /*根据百分比计每个算span的实际宽度*/
 function calWidth(){
  var arr=[];
  for(var i=0;i<percentArr.length;i++){
   var tempLength=percentArr[i]*longWidth;
   arr.push(tempLength);
  }
  return arr;
 }
 /*将全部span存为一个数组*/
 function findSpans(){
  var litems=$("#"+voteId).find(".litem");
  var arr=[]
  for(var i=0;i<litems.length;i++){
   arr.push(litems[i].children[0]);
  }
  return arr;
 }
 /*每个span元素设置宽度*/
 function setWidth(){
  for(i=0;i<percentArr.length;i++){
   $(spanArr[i]).animate({width:shortWidth[i]+"px"},'slow');
   $(spanArr[i]).css({'background-color':"#28acbb"}); 
  }
   
 }
 return {init:init,set:setWidth};
})();
 
/*调用*/
Vote.ListShow.init(
{
 id:'appVoteBox',
 width:200-2 ,
 percent:[0.02,0.61,0.36,0.13,0.3],
});
Vote.ListShow.set();
 
/**
 *  대시보드 ajax로 데이터 가져오고, 차트 그리기
 */

// ajax 통신 -> json 데이터 불러오기
function loadData(bcSeq){
	var result;
    $.ajax({
        type:"post",
        url:"/Dashboard/" + bcSeq,
        async:false,
        dataType:"json",
        success: function(data){
			result = data;
        },
        error: function(){
			console.log("실패");
        }
    });
	return result;
}

// 4. 실적률 데이터 차트 옵션값 리턴
function performanceOptions(value){
  var options = {
    //데이터 : 장바구니 담긴 수 대비 구매량
    series: [value],
    chart: {
	  height: 200,
	  type: 'radialBar',
	  offsetY: 5
	  },
	plotOptions: {
	  radialBar: {
	    startAngle: -135,
	    endAngle: 135,
	    dataLabels: {
		  name: { 
		    fontSize: '16px',
		    color: undefined,
		    offsetY: 70
		    },
          value: {
			offsetY: -6,
			fontSize: '22px',
			color: undefined,
			formatter: function (val) {
			  return val + "%";
			  }
            }
          }
        }
      },
      fill: {
		colors: ['#46AEFC']
		},
      stroke: {
		dashArray: 3
		},
      labels: ['Percent'],
    };
    
    return options;
} 

// 5. 고객 이벤트 맵 
function appendingTrTag(list, timeData, tagId, tableId){
	list.forEach((cur) => {
		var speech_time = cur.speech_time;
		
		var updown = timeData[speech_time + 3].viewer - timeData[speech_time].viewer;
		if(speech_time < 10){
			var speech_time = "0" + speech_time;
		}
        $(tagId).append(
    	  '<tr class="btn-reveal-trigger">' + 
          '<td class="py-2 align-middle">00:' + speech_time +'</td>' +
          '<td class="py-2 align-middle">' + cur.speech_keyword + '</td>' +
          '<td class="py-2 align-middle">' + cur.keyword_category + '</td>' +
          '<td class="py-2 align-middle">' + cur.reaction_count.salesCnt + '건</td>' +
          '<td class="py-2 align-middle">' + cur.reaction_count.basketCnt + '건</td>' +
          '<td class="py-2 align-middle">' + cur.reaction_count.lookingCnt + '건</td>' +
          '<td class="py-2 align-middle">' + updown + '명</td>' +
          '<td class="py-2 align-middle">' + cur.reaction_count.commentCnt + '건</td>' +
          '<td class="py-2 align-middle">' + cur.reaction_count.wishlistCnt + '건</td></tr>'
         );
         
         $(tableId).append(
    	  '<tr>' + 
          '<td>00:' + speech_time +'</td>' +
          '<td>' + cur.speech_keyword + '</td>' +
          '<td>' + cur.keyword_category + '</td>' +
          '<td>' + cur.reaction_count.salesCnt + '</td>' +
          '<td>' + cur.reaction_count.basketCnt + '</td>' +
          '<td>' + cur.reaction_count.lookingCnt + '</td>' +
          '<td>' + updown + '</td>' +
          '<td>' + cur.reaction_count.commentCnt + '</td>' +
          '<td>' + cur.reaction_count.wishlistCnt + '</td></tr>'
         );
      });
}

// 6. 1 ~ 60분까지 차트 그릴 옵션 만들기 -> 차트 옵션 반환
function viewerReactions1to60(map, startTime, endTime){
	var len = startTime - endTime + 1;
	const xticks = Array.from({length:len}, (v,i)=>i+1).toString();
	
	var sales = [];
	var baskets = [];
	var lookings = [];
	var viewers = [];
	var comments = [];
	var wishlists = [];
	let excelData = [];
	
	for(var i = startTime; i <= endTime; i++){
		sales.push(map[String(i)].sale);
		baskets.push(map[String(i)].basket);
		lookings.push(map[String(i)].looking);
		comments.push(map[String(i)].comment);
		wishlists.push(map[String(i)].wishlist);
		
		// 시청자 수 -> 시청자 증감으로 변경, 다른 값들과 너무 범위가 크게 차이나서 차트가 보기 안좋음
		if(i == 1){
			viewers.push(0);
		} else{
			var diff = map[String(i)].viewer - map[String(i-1)].viewer 
			viewers.push(diff);
		}
	}
	// 1번 엑셀 파일에 추출할 테이블태그 만들 (display: none)
	let tableTag1 = '<tr><td>시간</td><td>판매</td><td>장바구니</td><td>댓글</td><td>상품 조회</td><td>찜</td></tr>';
	for(var i = startTime; i < endTime; i++){
		var idx = String(i)
		var curTag = '<tr><td>' + idx + '</td><td>' + sales[idx] + '</td><td>' + baskets[idx] +
		'</td><td>' + comments[i] + '</td><td>' + lookings[i] + '</td><td>' + wishlists[idx] + '</td></tr>';
		tableTag1 += curTag;
	}
	
	
	// 시간대별 차트 (판매수, 장바구니수, 조회수, 댓글수, 찜수)
	var area_options = {
        //데이터 : 1~60분 동안의 판매수, 장바구니수, 조회수, 댓글수, 찜수
        series: [{
          name: '상품 조회',
          data: lookings
        }, {
          name: '댓글',
          data: comments
        }],
        chart: {
          height: 300,
          type: 'area'
        },
        dataLabels: {
          enabled: false
        },
        stroke: {
          curve: 'smooth'
        },
        xaxis: {
          //데이터 : 1~60분 시간
          categories: xticks
        }, theme: {
          palette: 'palette1'
        }
      };
      
      // 시청자수 따로
      var bar_data = [];
      
      for(var i = 0; i < endTime; i++){
		  if(viewers[i] < 0){
   		    bar_data.push({x: (i + 1), y:[0, 0, viewers[i], viewers[i]]});
		  } else{
			  bar_data.push({x: (i + 1), y:[viewers[i], viewers[i], 0, 0]});
		  }
	  }
      var bar_options = {
        series: [{
          //데이터 : 1분단위 시청자 증감 수
          data: bar_data,
        }],
        chart: {
          type: 'candlestick',
          height: 350
        },
        xaxis: {
          type: 'category',
          categories: xticks
        },
        yaxis: {
          tooltip: {
            enabled: false
          }
        },
        plotOptions: {
          candlestick: {
            colors: {
        upward: '#DF7D46',
        downward: '#3C90EB'
            }
          }
        }
      };
      
      // 판매수, 장바구니 수, 찜수 -> line chart로
      var line_options = {
                series: [
                    {
                        // 데이터 : 장바구니수, 판매량 
                        name: "장바구니",
                        data: baskets
                    },
                    {
                        name: "판매",
                        data: sales
                    }
                ],
                chart: {

                    height: 300,
                    type: 'line',
                    dropShadow: {
                        enabled: true,
                        color: '#000',
                        top: 18,
                        left: 7,
                        blur: 10,
                        opacity: 0.2
                    },
                    toolbar: {
                        show: true
                    }
                },
                colors: ['#00E396', '#008FFB'],
                dataLabels: {
                    enabled: false,
                },
                stroke: {
                    curve: 'smooth'
                },
                grid: {
                    borderColor: '#e7e7e7',
                    row: {
                        colors: ['#f3f3f3'], // takes an array which will be repeated on columns
                        opacity: 0.5
                    },
                },
                markers: {
                    size: 1
                },
                xaxis: {
                    //데이터 : 1부터 60분까지
                    categories: Array.from({length:60}, (v,i)=>(i+1))
                },
                yaxis: {
                    //데이터 : 최대값 나온걸로 맞추면 될듯!
                    min: 0,
                    max: Math.max(...baskets.concat(sales)) + 10
                }
            };
      let result = [area_options, bar_options, line_options];
      
    return [result, tableTag1];
}
    
    // 9. 남여 비율 도넛차트
function genderDonutChart(male, female) {
    var options = {
		series: [male, female],
		labels: ['남자', '여자'],
        chart: {
            type: 'donut',
        },
        dataLabels: {
            enabled: true,
            formatter: function (val){return Math.round(val) + "%"}
        },
        responsive: [{
            breakpoint: 480,
            options: {
                chart: {
                    width: 200
                },
                legend: {
                    position: 'bottom'
                }
            }
        }]
	};
	
	return [options, male, female];
}
	
// 10. 카테고리 별 판매 기여도 계산
function getInvolvement(data){
	let design = 0;
	let inprice = 0;
	let safety = 0;
	let functional = 0;
	
	data.forEach((cur) => {
		var cate = cur.keyword_category
		if(cate == '디자인(감성)'){
			design += cur.reaction_count.salesCnt;
		} else if(cate == '가격(가성비)'){
			inprice += cur.reaction_count.salesCnt;
		} else if(cate == '안전성(위험요소)'){
			safety += cur.reaction_count.salesCnt;
		} else if(cate == '기능성(소재)'){
			functional += cur.reaction_count.salesCnt;
		}
	});
	
	let total = design + inprice + safety + functional
	let design_rate = Math.round(design / total * 10000) / 100;
	let inprice_rate = Math.round(inprice / total * 10000) / 100;
	let safety_rate = Math.round(safety / total * 10000) / 100;
	let functional_rate = Math.round(functional / total * 10000) / 100;
	
  	var options = {
		series: [design_rate, inprice_rate, safety_rate, functional_rate],
		labels: ['디자인', '만족도', '안전성', '기능성'],
        chart: {
            type: 'donut',
        },
        dataLabels: {
            enabled: true,
            formatter: function (val){return (Math.round(val * 100) / 100) + "%"}
        },
        responsive: [{
            breakpoint: 480,
            options: {
                chart: {
                    width: 300,
                },
                legend: {
                    position: 'bottom'
                }
            }
        }]
	};
  
  
  return [options, total, [design_rate, inprice_rate, safety_rate, functional_rate]];    
} 

// 카테고리별 별점 태그 style- width로 차트 그리기
function starChartTag(category, value) {
	var hTag = '<h4 class="small font-weight=bold">' + category
		+ '<span class="float-right">' + value + '%</span></h4>';
	
	var divTag1 = '<div class="progress mb-4"><div id="'	// + id값
	var divTag2 = '" class="progress-bar ';				// color class값
	var divTag3 =  '" role="progressbar" style="width: ' + value + '%" aria-valuenow="20"'
	+ ' aria-valuemin="0" aria-valuemax="100"></div></div>';
	
	var tagId = '';
	
	
	if(category == '디자인'){
		tagId = 'design'; 
		var tagClass = 'bg-danger';
	} else if(category == '만족도'){
		tagId = 'satisfaction';
		var tagClass = 'bg-warning'
	} else if(category == '안정성') {
		tagId = 'safety';
		var tagClass = 'bg-info';
	} else if(category == '소재') {
		tagId = 'functional'
		var tagClass = '';
	}
	
	var divTag = divTag1 + tagId + divTag2 + tagClass + divTag3;
	
	return hTag + divTag;
}


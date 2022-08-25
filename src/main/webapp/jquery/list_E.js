    
    const appRoot = "http://localhost:8080/organizationsystem/api/department/";
    const appRoot2 = "http://localhost:8080/organizationsystem/api/employee/";
    var prevPage;
    
    
    //직원 목록 가져오기 - 함수 employList(nowPage);
    const employList = function(nowPage, prevPageParam){
        $.ajax({
            url: appRoot2 + "page/" + nowPage + '?' + Math.random(), 
            cache : false,
            success: function(list){
                $("#employAll *").remove();
                for(let i = 0; i < list.length; i++){
                    var employObject = $(`
                            <tr>
                              <td>${list[i].dname }</td>
                              <td>
                                <a href="info_E.html?index=${list[i].id}"> ${list[i].name} </a>
                              </td>
                              <td>${list[i].position }</td>
                              <td>${list[i].hiredDate }</td>
                              <td>${list[i].birth }</td>
                            </tr>
                      `);
                    $("#employAll").append(employObject);
                    console.log("ajax 실행");
                }
                    window.sessionStorage.setItem("page", nowPage);
                console.log("nowPage: "+ nowPage);
            }
        });

        //페이지버튼 눌림 표시
        if(prevPageParam != null){
            console.log("first: "+ prevPageParam);
            var prevId = prevPageParam/10 + 1;
            $('.clickedBt').attr('class', 'pbt'); //이전 클릭표시 해제
        }
        
        var thisId = nowPage/10 + 1;
        $('#'+thisId).attr('class', 'pbt clickedBt'); //현재 클릭표시 추가
        
        var prevPage = nowPage;
        console.log("second: "+ prevPage);
        return prevPage;
    }
    
    prevPage = employList(0, null);
    
    // !!!!!!!!!!!!!!!!  ------------  뒤로가기 하면 새로고침 하고 데이터 리로드하기 (목록, 버튼)---------!!!
    $(window).bind("pageshow", function(event) {
        if (event.originalEvent.persisted) {
             //$("#employAll").load(window.location.href + "#employAll");
             location.reload(); // 에휴 새로고침이나 하자...
            //console.log("뒤로왔습니다");
            
            //$.ajax({
              //  url: appRoot2 + "getback",
              //  type:'GET',
              //  async:false,
              //  crossDomain: true,
              //  success: function(response){
                //  console.log("성공"+response);
              //  },
              //  error: function(error,textStatus){
              //    console.log("실패");
             //     console.log(error);
            //        console.log(textStatus);
            //    }
            //});
            
            //var historyPage = sessionStorage.getItem("page");
            //console.log("historyPage:"+ historyPage);
            //console.log("prevPage:"+ prevPage);
            //prevPage = employList(historyPage, null);
            
            //var historyGroup = sessionStorage.getItem("btn");
            //console.log("historyGroup:"+ historyGroup);
            //setButtons(historyGroup);
        }
    });
    
 // !!!!!!!!!!!!!!!!  ------------  뒤로가기 하면 새로고침 하고 데이터 리로드하기 (목록, 버튼)---------!!!
    
      //직원 수 가져오기
      const getEmployNum = function(){
          var employNumParam;
          $.ajax({
              url: appRoot2 + "num",
              async: false,
              success: function(data){
                  employNumParam = data;
              }
          });
          return employNumParam;
      }
          var employNum = getEmployNum();
          
          // 필요한 페이지 수 pageNum
          const getPageNum = function(employNumParam){
              var pageNumParam;
              if(employNumParam % 10 == 0){
                  pageNumParam = employNumParam/10;
              }else{
                  pageNumParam = parseInt(employNumParam/10) + 1;
              }
              return pageNumParam;
          }
          var pageNum = getPageNum(employNum);
          
          
          // 필요한 페이지그룹 수 groupNum
          const getGroupNum = function(pageNumParam){
              var groupNumParam;
              if(pageNumParam % 5 == 0){
                  groupNumParam = pageNumParam/5;
              }else{
                  groupNumParam = parseInt(pageNumParam/5) + 1;
              }
              return groupNumParam;
          }
          var groupNum = getGroupNum(pageNum);

          var nowGroup = 1;
          //버튼 5개씩만 가져오기 - 함수 setButtons(group)
          const setButtons = function(group){
              $("#pages *").remove();
              if(group != 1){
                  $("#pages").append('<button id="prev"><</button>');
              }
              for(let i = group*5 -4; i <= group*5; i++){
                  if(i > pageNum || i < 1) break;
                  $("#pages").append('<button class="pbt" id="' +i+ '">'+i+'</button>');
              }
              if(group != groupNum){
                  $("#pages").append('<button id="next">></button>');
              }
              sessionStorage.setItem("btn", group);
          }
          
          setButtons(nowGroup);
          
          // > 버튼 누르면 다음 페이지그룹 가져오기
          $(document).on('click','#next', function(){
              nowGroup++;
              setButtons(nowGroup);
              var loadNext = (nowGroup*5-5)*10;
              prevPage = employList(loadNext, prevPage);
          });
          
          // < 버튼 누르면 이전 페이지그룹 가져오기
          $(document).on('click','#prev', function(){
              nowGroup--;
              setButtons(nowGroup);
              var loadPrev = (nowGroup*5-1)*10;
              prevPage = employList(loadPrev, prevPage);
          });
        

        // 버튼 클릭하면 해당 페이지 직원 가져오기
        $(document).on('click','.pbt', function(){
              var j = (parseInt($(this).attr('id')) -1) * 10;
              console.log(j);
              //직원 목록 가져오기
              prevPage = employList(j, prevPage);
          });
      
      //부서목록 가져오기
      const getDepart = function(){
          $.ajax({
              url: appRoot,
              success: function(list){
                  for(let i = 0; i < list.length; i++){
                      var opt = $("<option value='" + list[i].id + "'>"+ list[i].name +"</option>");
                      $("#departId").append(opt);  
                  }
              }
          });
      }
      
      getDepart();
      
      //직원 등록하기
      
      $("#submit").click(function(){
          var departId = $("#departId").val();
          if(departId == 'null'){
              departId = 1;
          }else{
              departId = parseInt(departId);
          }
          
          
            var nametext = $("#name").val();
            if(nametext.replace(/\s|　/gi, "").length == 0){
                alert('내용을 입력해주세요.');
            }else{
                var data = JSON.stringify({
                    name: $("#name").val(),
                    position: $("#position").val(),
                    hiredDate: $("#hiredDate").val(),
                    birth: $("#birth").val(),
                    depart_id: departId
                });
                
                $.ajax({
                    url: appRoot2,
                    type: "POST",
                    data: data,
                    contentType: "application/json",
                    dataType: "json",
                    success: function(){
                        alert('직원이 등록되었습니다.');
                        $(".inputs").val('');
                        $("#select-default").prop('selected', true);
                        //직원목록 재로드 - 마지막 페이지로
                        
                        employNum = getEmployNum();
                        pageNum = getPageNum(employNum);
                        groupNum = getGroupNum(pageNum);
                        nowGroup = groupNum;
                        setButtons(groupNum);
                        prevPage = employList((pageNum-1)*10, prevPage);
                    },
                    error: function(jqXHR, textStatus, errorThrown){
                        alert(jqXHR.responseText);
                        //alert('등록을 실패했습니다.');
                    }
                });
            }
      });            
            //$("#cancel").click(function(){
               //$(".inputs").val('');
            //});


      
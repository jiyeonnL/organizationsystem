    const appRoot = "http://localhost:8080/organizationsystem/api/department/";
    const appRoot2 = "http://localhost:8080/organizationsystem/api/employee/";
    
    //index 가져오는 함수
    function get_query() {
    var url = document.location.href;
    var qs = url.substring(url.indexOf('?') + 1).split('&');
    for (var i = 0, result = {}; i < qs.length; i++) {
        qs[i] = qs[i].split('=');
        result[qs[i][0]] = decodeURIComponent(qs[i][1]);
    }
    return result;
    }
    
    
    var num = get_query()['index'];
    //console.log(num);
    
    //$("#newOne").hide();
    
     
    
    //부서 가져오기
    const departInfo = function(){
        $.ajax({
            url: appRoot + num,
            success: function(data){
                $("#departOne *").remove();
                    var departObject = $(`                             
                                  <td><input type="text" id="name" class="inputs" value="${data.name }" readonly></td>     
                                  <td><input type="text" id="head" class="inputs" value="${data.head }" readonly></td>
                                  <td><input type="text" id="location" class="inputs" value="${data.location }" readonly></td>
                                  <td><input type="text" id="contact" class="inputs" value="${data.contact }" readonly></td>
                                  <td>
                                     <button class="bt bt-normal" id="modify">수정</button>
                                     <button class="bt" id="remove">삭제</button>
                                  </td> 
                            `);      
                    $("#departOne").append(departObject);  
                    
                    $("#staticdId").attr('value', data.name);
                    
                    //부서 삭제
                    $("#remove").click(function(){
                        if(num == 1){
                            alert('이 부서는 삭제할 수 없습니다.');
                        }else{
                            $.ajax({
                                url: appRoot + num,
                                type: "DELETE",
                                success: function(){
                                    alert('부서가 삭제되었습니다.');
                                    window.location.href = "http://localhost:8080/organizationsystem/";
                                },
                                error: function(jqXHR, textStatus, errorThrown){
                                    alert(jqXHR.responseText);
                                    //alert('삭제를 실패했습니다.');
                                }
                            });
                        }
                    });
                    
                    //부서 수정
                    onoff = false;
                    $("#modify").click(function(){
                        if(onoff){
                            departInfo();
                        }else{
                            onoff = true;
                            $(".inputs").attr("readonly", false);
                            $("#modify").text('취소');
                            $("#modify").attr('id', 'cancel');
                            $("#remove").remove();
                            $("#cancel").after('&nbsp<button class="bt bt-normal" id="modifySubmit">저장</button>');
                            
                            $("#modifySubmit").click(function(){
                                var nametext = $("#name").val();
                                if(nametext.replace(/\s|　/gi, "").length == 0){
                                    alert('내용을 입력해주세요.');
                                }else{
                                    var data = JSON.stringify({
                                        name: $("#name").val(),
                                        head: $("#head").val(),
                                        location: $("#location").val(),
                                        contact: $("#contact").val()
                                    });
                                    
                                    $.ajax({
                                        url: appRoot + num,
                                        type: "PUT",
                                        data: data,
                                        contentType: "application/json",
                                        dataType: "json",
                                        success: function(){
                                            alert('부서가 수정되었습니다.');
                                            $(".inputs").val('');
                                            departInfo();
                                        },
                                        
                                        
                                        error: function(jqXHR, textStatus, errorThrown){
                                            //alert(jqXHR.status); //400
                                            //alert(jqXHR.statusText); //Bad Request 
                                            alert(jqXHR.responseText); //"부서수정실패"
                                            //alert(jqXHR.readyState); //4
                                            //alert('수정을 실패했습니다.');
                                        }
                                        
                                        //complete: function(data, status){
                                        //    alert('부서가 수정되었습니다.');
                                        //    $(".inputs").val('');
                                        //    departInfo();
                                        //} //수정 에러 뜨는 문제!
                                    });
                                }
                            });
                        }
                    });
            }
        });
    }
    departInfo();
    
    //직원조회
    const employList = function(){
        $.ajax({
            url: appRoot2 + "depart/" + num,
            success: function(list){
                $("#employThis *").remove();
                
                if(list.length == 0){
                    var no = $('<tr><td colspan="4">이 부서에 등록된 직원이 아직 없습니다.</td></tr>');
                    $("#employThis").append(no);
                }else{
                    for(let i = 0; i < list.length; i++){
                         var employObject = $(`
                                 <tr>
                             
                                 <td>
                                   <a href="info_E.html?index=${list[i].id}">${list[i].name }</a>
                                 </td>
                                           
                                 <td>${list[i].position }</td>
                                           
                                 <td>${list[i].hiredDate }</td>
                                           
                                 <td>${list[i].birth }</td>
                                 
                                 </tr>
                           `);      
                         $("#employThis").append(employObject); 
                }
            }
        }
    });
    }
    
    employList();
    
    
    //이 부서에 직원등록
    
    $("#submit").click(function(){
            var nametext = $("#nameE").val();
            if(nametext.replace(/\s|　/gi, "").length == 0){
                alert('내용을 입력해주세요.');
            }else{
                
                var departId = num;
                var data = JSON.stringify({
                    name: $("#nameE").val(),
                    position: $("#position").val(),
                    hiredDate: $.trim($("#hiredDate").val()),
                    birth: $.trim($("#birth").val()),
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
                        $(".inputsE").val('');
                        employList();
                    },
                    error: function(){
                        alert('등록을 실패했습니다.');
                    }
                });
            }
      }); 
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
    
    //직원 가져오기
    const employInfo = function(){
        $.ajax({
            url: appRoot2 + num,
            success: function(data){
                $("#employOne *").remove();
                    var employObject = $(`    
                                  <td><select disabled id="departId"> </select></td>
                                  <td><input type="text" id="name" class="inputs" value="${data.name}" readonly></td>     
                                  <td><input type="text" id="position" class="inputs" value="${data.position}" readonly></td>
                                  <td><input type="text" id="hiredDate" class="inputs" value="${data.hiredDate}" readonly></td>
                                  <td><input type="text" id="birth" class="inputs" value="${data.birth}" readonly></td>
                                  <td>
                                     <button class="bt bt-normal" id="modify">수정</button>
                                     <button class="bt" id="remove">삭제</button>
                                  </td> 
                            `);      
                    
                  //부서목록 가져오기
                    const getDepart = function(){
                        $.ajax({
                            url: appRoot,
                            success: function(list){
                                for(let i = 0; i < list.length; i++){
                                    if(list[i].id == data.depart_id){
                                        var opt = $("<option selected value='" + list[i].id + "'>"+ list[i].name +"</option>");
                                    }else{
                                        var opt = $("<option value='" + list[i].id + "'>"+ list[i].name +"</option>");
                                    }
                                    $("#departId").append(opt);  
                                }
                            }
                        });
                    }
                    
                    getDepart();
                    
                    
                    $("#employOne").append(employObject);  
                    
                    //직원 삭제
                    $("#remove").click(function(){
                        $.ajax({
                            url: appRoot2 + num,
                            type: "DELETE",
                            success: function(){
                                alert('직원이 삭제되었습니다.');
                                window.location.href = "http://localhost:8080/organizationsystem/list_E.html";
                            },
                            error: function(){
                                alert('삭제를 실패했습니다.');
                            }
                        });
                    });
                    
                    //직원 수정
                    onoff = false;
                    $("#modify").click(function(){
                        
                        if(onoff){
                            employInfo();
                        }else{
                            onoff = true;
                            $(".inputs").attr("readonly", false);
                            $("#departId").attr("disabled", false);
                            $("#modify").text('취소');
                            $("#modify").attr('id', 'cancel');
                            $("#remove").remove();
                            $("#cancel").after('&nbsp<button class="bt bt-normal" id="modifySubmit">저장</button>');
                            
                            //수정칸에 공백 생기는 문제 해결
                            
                            $("#modifySubmit").click(function(){
                                var nametext = $("#name").val();
                                if(nametext.replace(/\s|　/gi, "").length == 0){
                                    alert('내용을 입력해주세요.');
                                }else{
                                    
                                    var departId = parseInt($("#departId").val());
                                    
                                    var data = JSON.stringify({
                                        name: $("#name").val(),
                                        position: $("#position").val(),
                                        hiredDate: $.trim($("#hiredDate").val()),
                                        birth: $.trim($("#birth").val()),
                                        depart_id: departId
                                    });
                                    
                                    $.ajax({
                                        url: appRoot2 + num,
                                        type: "PUT",
                                        data: data,
                                        contentType: "application/json",
                                        dataType: "json",
                                        success: function(){
                                            alert('직원이 수정되었습니다.');
                                            $(".inputs").val('');
                                            employInfo();
                                        },
                                        error: function(jqXHR, textStatus, errorThrown){ 
                                            alert(jqXHR.responseText);
                                        }
                                    });
                                }
                            });
                        }     
                    });
            }
        });
    }
    employInfo();
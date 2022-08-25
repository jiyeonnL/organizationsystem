
 const appRoot = "http://localhost:8080/organizationsystem/api/department";
  
    //부서 목록 가져오기
    
    const departList = function(){
        $.ajax({
            url: appRoot,
            success: function(list){
                $("#departAll *").remove();
                for(let i = 0; i < list.length; i++){
                    var departObject = $(`
                                <tr>
                                  <td>
                                    <a href="info.html?index=${list[i].id}"> ${list[i].name} </a>
                                  </td>
                                            
                                  <td>${list[i].head }</td>
                                            
                                  <td>${list[i].location }</td>
                                            
                                  <td>${list[i].contact }</td>
                                </tr>
                                
                            `);      
                    $("#departAll").append(departObject);   
                }
            }
        });
    }
    
    departList();

    //부서 등록하기  
  
    $(document).ready(function(){
        $("#submit").click(function(){
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
                    url: appRoot,
                    type: "POST",
                    data: data,
                    contentType: "application/json",
                    dataType: "json",
                    success: function(){
                        alert('부서가 등록되었습니다.');
                        $(".inputs").val('');
                        departList();
                    },
                    error: function(jqXHR, textStatus, errorThrown){
                        //alert(jqXHR.status); //400
                        //alert(jqXHR.statusText); //Bad Request 
                        alert(jqXHR.responseText); //"부서등록실패"
                        //alert(jqXHR.readyState); //4
                        //alert('등록을 실패했습니다.');
                    }
                });
            }
        });
        
        //$("#cancel").click(function(){
            //$(".inputs").val('');
        //});
    });

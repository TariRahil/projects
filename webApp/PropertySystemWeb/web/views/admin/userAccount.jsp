<%--
  Created by IntelliJ IDEA.
  User: tarrah
  Date: 05/25/2019
  Time: 11:33 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>Title1522251</title>
    <%--<script src="views/shared/jquery-3.4.1.min.js" type="text/javascript"></script>--%>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="/resources/demos/style.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script type="text/javascript">

        $(document).ready(function(){
            $("#btnSave").attr("disabled", "disabled");
            $('.form-field').on('input', checkAndEnableButton);
            function checkAndEnableButton() {
                $("#btnSave").prop('disabled', true);
                var breakFlag = false;
                var enteredVal = '';
                $('.form-field').each(function() {
                   enteredVal = $(this).val();
                   if (enteredVal == ''  || enteredVal=="-1"  )
                       breakFlag = true;
               });

               if (!breakFlag) {
                   $("#btnSave").prop('disabled', false);
               }
           }
            $("#txtUserName").blur(function () {
               var userName=$("#txtUserName").val();
               if(userName!=""){
               $.ajax({
                  // async:false,
                   type: "POST",
                   url: "UserAccountServlet",
                   // dataType: "json",
                   data: ({
                       action:"checkDuplicateUserName",
                       userName:userName
                         }),
                   success: function (result) {
                       if(result.indexOf("non-duplicate")>-1  )
                       {
                           $("#lblMsgUserName").text("");


                       }
                       else  {
                           $("#lblMsgUserName").text("نام کاربری تکراری است!");
                           $("#txtUserName").val("");

                       }

               }
               });}
               else {
                   $("#lblMsgUserName").text("");

               }
           });

           $("#btnSave").click(function () {
               save();
               clearForm();
              });
           function save() {
                var firstName=$("#txtFirstName").val();
                var lastName=$("#txtLastName").val();
                var userName=$("#txtUserName").val();
                var password=$("#txtPassword").val();
                var managerId=$("#hdnManagerId").val();
                var unitId=$("#cboUnitId").val();
                var active=$("#chkActive").val();

                $.ajax({

                    type: "POST",
                    url: "UserAccountServlet",
                    data: ({
                        action:"save",
                        firstName:firstName,
                        lastName:lastName,
                        userName:userName,
                        password:password,
                        managerId:managerId,
                        unitId:unitId,
                        active:active
                    }),
                    success: function (result) {
                        alert(result);

                    }
                });
                  }
           function clearForm() {
                $("#txtFirstName").val("");
                $("#txtLastName").val("");
                $("#txtUserName").val("");
                $("#txtPassword").val("");
                $("#txtManagerId").val("");
                $("#cboUnitId").val("-1");
                $("#chkActive").prop("checked","checked");
                $("#hdnManagerId").val("");


            }

           $( "#txtManagerId" ).autocomplete({

               source: function (request, response) {
                   var tag=$("#txtManagerId").val();
                   $.ajax({
                       type: "POST",
                       url: "UserAccountServlet",
                       data:({action:"managerAutoComplete",word:tag}),
                       success: function (data) {
                          var transformed = $.map(data, function (el) {
                               return {
                                   label: el.firstName+" "+ el.lastName,
                                   id: el.userId
                               };
                           });
                           response(transformed);

                       },
                       error: function () {
                           // response([]);
                       }
                   });
               },
               select: function (event, ui) {
                   $("#hdnManagerId").val(ui.item.id);

               },
               change: function (event, ui) {
                   if (!ui.item) {
                       $(this).val("");
                       $("#hdnManagerId").val("");
                       $("#btnSave").prop('disabled', true);
                       //$('#empty-message').show();
                   // } else {
                   //     $('#empty-message').hide();
                   }
               }


           });
           $("#btnCancel").click(function () {
               clearForm();

           });



        });

    </script>
</head>
<body>
<%@include file="../shared/navbar.jsp"%>


<form>
    <div style="direction: rtl ; padding-right: 25%">
        <table>
            <tr>
                <th>
                    <%--<%=messages.getString("first_name")%>--%>نام:
                </th>
                <td>
                    <input type="text"  class="form-field" name="txtFirstName" id="txtFirstName"/>
                </td>
            </tr>
            <tr>
                <th>
                    <%--<%=messages.getString("last_name")%>--%>نام خانوادگی:
                </th>
                <td>
                    <input type="text" class="form-field" name="txtLastName" id="txtLastName"/>
                </td>
            </tr>
            <tr>
                <th>
                    نام کاربری:
                </th>
                <td>
                    <input type="text" class="form-field" name="txtUserName" id="txtUserName"/>
                    <label id="lblMsgUserName"  style="color: red;font-size: small"></label>

                </td>
            </tr>
            <tr>
                <th>
                    کلمه عبور:
                </th>
                <td>
                    <input type="password" class="form-field" name="txtPassword" id="txtPassword"/>
                </td>
            </tr>
            <tr>
                <th>
                    مدیر سازمانی:
                </th>
                <td>
                    <div class="ui-widget">

                         <input id="txtManagerId" name="txtManagerId" class="form-field">
                         <input type="hidden" id="hdnManagerId" name="hdnManagerId"></div>

                </td>

                </td>
            </tr>
            <tr>
                <th>
                    اداره کل:
                </th>
                <td>
                    <select name="cboUnitId" class="form-field" id="cboUnitId">
                        <option value="-1" selected>انتخاب کنید</option>
                        <option value="3">IS</option>
                        <option value="4">DC</option>
                        <option value="5">DP</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th>
                    وضعیت:
                </th>
                <td>
                    <input type="checkbox" checked="checked" name="chkActive" id="chkActive"> فعال </input>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="button" value="ذخیره" name="btnSave" id="btnSave" />
                    <input type="submit" value="انصراف" name="btnCancel" id="btnCancel" />
                </td>

            </tr>
        </table>
    </div>
</form>
<%@include file="../shared/footer.jsp"%>

</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>


<html class="w3-right-align">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            /*fillTable();*/
            GetResource_paging(1);
            function GetResource_paging(pageNumber) {
                var index = 10;
                $("#resourceTable > tbody tr:not(:first)").remove();
                try {
                    $.ajax({
                        type : "POST",
                        url : "ResourceServlet1",
                        //dataType: "json",
                        data : {
                            index : index,
                            pageNumber : pageNumber,
                            action : "fillResourceTable",
                        },
                        success : function (result){
                            if (result!=null){
/*
                                var resources = $.parseJSON(result);
*/
                               // var resource = $.parseJSON(result);
                               // var resources = $.parseJSON(resource.Resource);
                                fillTable(result,index,pageNumber);
                            }
                        },
                    });
                }catch (ex) {
                    $.log(">> Test>>: " + ex.message);
                }
            }

            function fillTable(Resources,index,pageNumber){

                $("#resourceTable").hide();
                var rowCount = 1;
                if (pageNumber >1){
                    rowCount = ((pageNumber-1)*index)+1;
                }
                var Pages = 0;
                var count = 0;
                $.map(Resources, function (el){

                    Pages = el.pageCount;
                    count = el.count;
                    var active="غیرفعال";
                    if (el.active=="true"){
                        active="فعال";
                    }
                    var row = "<tr id=\"" + rowCount + "\">";
                    row += "<td>" + rowCount + "</td>"
                        + "<td>" + el.name + "</td>"
                        + "<td>" + el.owner + "</td>"
                        + "<td>" + active + "</td>"
                        + "<td> </td>"
                        + "</tr>";

                    $(row).appendTo("#resourceTable");
                    $("#resourceTable tr#" + count).show("fast").slideDown("slow");
                    rowCount++;
                });
            /*    $.each(Resources , function () {
                    alert("salam"+this.pageCount);
                    Pages = this.pageCount;
                    count = this.count;
                    var row = "<tr id=\"" + rowCount + "\">";
                    row += "<td>" + rowCount + "</td>"
                        + "<td>" + this.resourceName + "</td>"
                        + "<td>" + this.resourceOwner + "</td>"
                        + "<td>" + this.resourceStatus + "</td>"
                        + "</tr>";

                    $(row).appendTo("#resourceTable");
                    $("#resourceTable tr#" + count).show("fast").slideDown("slow");
                    rowCount++;
                })*/

                if (rowCount >= 1) {
                    $("#resourceTable").show();

                } else {
                    alert("منبعی یافت نشد.");
                }

                $(".paginate").remove();
                var pagination = '';

                if (Pages != 0) {
                    pagination += '<div class="paginate"  >';
                    pagination += ' <input type="button" value="<<"  class="paginate_click_first" id="1-page" /> ';
                    if (Pages == 1) {
                        pagination += '<span id="lblcurrentPage"> صفحه :' + pageNumber + '</span></br>'
                    } else if (Pages > 1) {
                        pagination += '<input type="button" id="li_next" value="بعدی"></input>';
                        pagination += '<span id="lblcurrentPage"> صفحه :' + pageNumber + '</span>'
                        pagination += '<input type="button" id="li_last" value="قبلی"></input>';
                        pagination += ' <input type="button" value=">>" class="paginate_click_end" id="' + Pages + '-page" /> </br>';
                        pagination += '</div>';
                    }

                    $("#divCount").remove();
                    pagination += '<div id="divCount"><span id="lblcount">تعداد کل رکوردها:' + count +  "</span> </br> </div>";
                    $("#lblnorecord").remove();


                } else {
                    $("#lblnorecord").remove();
                    pagination = "<span id='lblnorecord'>رکوردی وجود ندارد.</span>";
                    $("#divCount").remove();

                }

                $(pagination).appendTo("#divPaging");
                $("#hdnCurrentPage").val(pageNumber);
            }
            $(document).on("click", ".paginate input", function (e) {
                var clicked_id = $(this).attr("id").split("-");
                var page_num = parseInt(clicked_id[0]);

                $('.paginate_click').removeClass('active');

                GetResource_paging(page_num);

            });
            $(document).on("click", "#li_next", function (e) {

                var end = $(".paginate_click_end").attr("id").split("-");
                var end_num = parseInt(end[0]);
                var current = parseInt($("#hdnCurrentPage").val()) + 1;
                if (current <= end_num) {

                    $("#hdnCurrentPage").val(current);
                    GetResource_paging(current);
                }

            });
            $(document).on("click", "#li_last", function (e) {

                var first = $(".paginate_click_first").attr("id").split("-");
                var first_num = parseInt(first[0]);
                var current = parseInt($("#hdnCurrentPage").val()) - 1;
                if (current >= first_num) {
                    $("#hdnCurrentPage").val(current);
                    GetResource_paging(current);
                }

            });


           /* $("#btnPrevious").click(function () {
                var currentP = $("#hdnCurrentPage").val();
                if (currentP!=1) {
                    currentP = parseInt(currentP) - 1;
                    $("#hdnCurrentPage").val(currentP);
                }
            });

            $("#btnNext").click(function () {
                var currentP = $("#hdnCurrentPage").val();
                currentP = parseInt(currentP)+1;
                if (currentP <= $("#hdnPageCount").val()) {
                    $("#hdnCurrentPage").val(currentP);
                }
            });*/


            $("#btnSearch").click(function () {
                $("#hdnButtonAction").val("search");
            });

            $("#btnAdd").click(function () {
                var resourceId           = $("#hdnEditById").val();
                var resourceName         = $("#txtResourceName").val();
                var resourceOwner        = $("#txtResourceOwner").val();
                var resourceCurrentPlace = $("#txtResourceCurrentPlace").val();
                var floor                = $("#selectFloor").val();
                var unitId               = $("#selectUnitId").val();
                var status               = $("#selectStatus").val();

                $.ajax({

                    type: "POST",
                    url: "ResourceServlet1",
                    data: ({
                        action: "addResource",
                        resourceId: resourceId,
                        resourceName: resourceName,
                        resourceOwner: resourceOwner,
                        resourceCurrentPlace: resourceCurrentPlace,
                        floor: floor,
                        unitId: unitId,
                        status: status
                    }),
                    success: function (result) {

                        $("#msg").append(result);
                        // alert(result);

                    }
                });
            });



            $("#btnEdit").click(function () {
              //
                var resourceId           = $("#hdnEditById").val();
                var resourceName         = $("#txtResourceName").val();
                var resourceOwner        = $("#txtResourceOwner").val();
                var resourceCurrentPlace = $("#txtResourceCurrentPlace").val();
                var floor                = $("#selectFloor").val();
                var unitId               = $("#selectUnitId").val();
                var status               = $("#selectStatus").val();

                $.ajax({

                    type: "POST",
                    url: "ResourceServlet1",
                    data: ({
                        action: "editResource",
                        resourceId: resourceId,
                        resourceName: resourceName,
                        resourceOwner: resourceOwner,
                        resourceCurrentPlace: resourceCurrentPlace,
                        floor: floor,
                        unitId: unitId,
                        status: status
                    }),
                    success: function (result) {

                        $("#msg").append(result);
                       // alert(result);

                    }
                });
            });





        });
    </script>

</head>
<body>

<%@include file="/views/shared/navbar.jsp"%>

<div class="w3-direction w3-center w3-content w3-light-grey w3-round-xxlarge w3-width-50">

    <div class="w3-round-large w3-width-75  w3-center w3-content">

        <div id = "msg"></div>
<%--ADD AND EDIT--%>
        <div class="w3-blue-grey w3-padding w3-round-large w3-center w3-content w3-margin-bottom">
            <input type="text" name="txtResourceName" id="txtResourceName" placeholder="<%=messages.getString("resourceName")%>" class="w3-round-large">
            <input type="text" name="txtResourceOwner" id="txtResourceOwner" placeholder="<%=messages.getString("resourceOwner")%>" class="w3-round-large">
            <input type="text" name="txtResourceCurrentPlace" id="txtResourceCurrentPlace" placeholder="<%=messages.getString("resourceCurrentPlace")%>" class="w3-round-large">
            <select class="w3-round-large" id="selectFloor">
                <option name="floor"  value="Title"><%=messages.getString("selectFloor")%></option>
                <option name="floor"  value="1">1</option>
                <option name="floor"  value="2">2</option>
                <option name="floor"  value="3">3</option>
                <option name="floor"  value="4">4</option>
                <option name="floor"  value="5">5</option>
                <option name="floor"  value="6">6</option>
                <option name="floor"  value="7">7</option>
                <option name="floor"  value="8">8</option>
            </select>
            <select class="w3-round-large" id="selectUnitId">
                <option name="resourceOwner"  value="Title"><%=messages.getString("selectUnitId")%></option>
                <option name="resourceOwner"  value="2"><%=messages.getString("is")%></option>
                <option name="resourceOwner"  value="1"><%=messages.getString("dc")%></option>
                <option name="resourceOwner"  value="0"><%=messages.getString("dp")%></option>
            </select>
            <select class="w3-round-large" id="selectStatus">
                <option name="status"  value="-1"><%=messages.getString("selectStatus")%></option>
                <option name="status"  value="1"><%=messages.getString("active")%></option>
                <option name="status"  value="0"><%=messages.getString("deActive")%></option>
            </select>
        </BR>
            <input type="button" name="btnAdd" id="btnAdd" value="<%=messages.getString("add")%>" class="w3-btn w3-round-large w3-blue w3-margin">
            <input type="button" name="btnEdit" id="btnEdit" value="<%=messages.getString("edit")%>" class="w3-btn w3-round-large w3-blue w3-margin">
        </div>

        <div>
        </div>

    </div>

<%--SEARCH BOX--%>
        <div class="w3-blue-grey w3-padding w3-round-large w3-width-75 w3-center w3-content w3-margin-bottom">
            <input type="text" name = "resourceName" placeholder="<%=messages.getString("resourceName")%>" class="w3-round-large">
            <input type="text" name="resourceOwner" placeholder="<%=messages.getString("resourceOwner")%>" class="w3-round-large">
        </BR>
            <input type="checkbox" value="active" name="active"><%=messages.getString("active")%>
            <input type="checkbox" value="deActive" name="deActive"><%=messages.getString("deActive")%>
        <BR/>
            <input type="button" name="btnSearch" id="btnSearch" value="<%=messages.getString("search")%>" class="w3-btn w3-round-large w3-blue w3-margin">
        </div>

<%--RESOURCE LIST TABLE--%>
<%--
        <input type="text" name="hdnCurrentPage" value="${currentPageValue}" id="hdnCurrentPage">
--%>
        <input type="text" name="hdnPageCount" value="${pageCountValue}"  id="hdnPageCount">
        <input type="text" name="hdnButtonAction" id="hdnButtonAction">
        <input type="text" name="hdnEditById" id="hdnEditById" value="0">

        <div  class="w3-round-xxlarge w3-width-75 w3-center w3-content">

            <div id="table" class="w3-blue w3-padding w3-round-large">

                <table id="resourceTable" class="w3-table">
                    <tr class=" w3-blue-gray w3-opacity">
                        <th>
                            <%=messages.getString("row")%>
                        </th>
                        <th>
                            <%=messages.getString("resourceName")%>
                        </th>
                        <th>
                            <%=messages.getString("resourceOwner")%>
                        </th>
                        <th>
                            <%=messages.getString("resourceStatus")%>
                        </th>
                        <th>

                        </th>
                    </tr>

                </table>
                <div id="divPaging" align="center">
                   <input type="hidden" id = "hdnCurrentPage">
                </div>

                <%--<td><input type="button" name="btnPrevious" value="<%=messages.getString("previous")%>" id="btnPrevious" class="w3-btn w3-round-large w3-blue w3-margin"/></td>
                <td><input type="button" name="btnNext" value="<%=messages.getString("next")%>" id="btnNext" class="w3-btn w3-round-large w3-blue w3-margin"/></td>--%>
            </div>

        </div>

</div>

<%@include file="/views/shared/footer.jsp"%>

</body>
</html>
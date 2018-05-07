<%--
  Created by IntelliJ IDEA.
  User: lvjiawei
  Date: 2018/3/17
  Time: 上午10:29
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="global.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>Add Book</title>
</head>
<body>
<style>
    #wrapper {
        background-color: #5D4B33;
        margin-right:70%;
    }
    @media ( min-width :768px) {
        .sidebar {
            z-index: 1;
            position: absolute;
            width:100%;
            margin-top: 51px;
            background-color: #5D4B33;
        }
    }
    #bookinfo
    {
        margin-top: 51px;
        margin-left:20%;
        margin-right:20%;
    }
    .form-control-noNewline {
        width:140px;
        display:inline;
    }
    .form-horizontal .form-group-auto {
        margin-right: 0px;
        margin-left: 0px;
    }

</style>

<!-- header -->
<s:action name="header" executeResult="true" namespace="/"/>

<div id=bookinfo>
    <form id="form" action="<%=path%>/adminAction/uploadBook" method=post enctype="multipart/form-data" role="form" class="form-horizontal" accept-charset="UTF-8">
        <h3 style="text-align: center;">发布图书</h3>
        <div class="form-group form-group-auto">
            <label>书名</label><font color="#FF0000">*</font><input id="bookName" name="bookProfile.bookName" type="text" class="form-control">
        </div>
        <div class="form-group form-group-auto">
            <label>作者</label><font color="#FF0000">*</font><input id="author" name="bookProfile.author" type="text" class="form-control">
        </div>
        <div class="form-group form-group-auto">
            <label>ISBN</label><font color="#FF0000">*</font><input id="isbn" name="bookProfile.isbn" type="text" class="form-control">
        </div>
        <div class="form-group form-group-auto">
            <label>出版社</label><font color="#FF0000">*</font><input id="press" name=bookProfile.press type="text" class="form-control">
        </div>
        <div class="form-group form-group-auto">
            <label>类别</label><font color="#FF0000">*</font>&nbsp;
            <select id="cate" name="bookProfile.category1" class="form-control form-control-noNewline">
                <s:iterator value="#category1List">
                    <option value="<s:property value="category1Name"/>"><s:property value="category1Name"/></option>
                </s:iterator>
            </select>&nbsp;
            <select id="category" name="bookProfile.category2" class="form-control form-control-noNewline">
                <s:iterator value="#category1List" begin="0" end="0">
                    <s:iterator value="category2List">
                        <option value="<s:property value="category2Name"/>"><s:property value="category2Name"/></option>
                    </s:iterator>
                </s:iterator>

            </select>

        </div>
        <div class="form-group form-group-auto">
            <label>库存</label><font color="#FF0000">*</font>&nbsp;
            <input name="bookProfile.amount" type="number" step="1" min="0" class="form-control form-control-noNewline">&nbsp;&nbsp;&nbsp;
        </div>
        <div class="form-group form-group-auto">
            <label>价格</label><font color="#FF0000">*</font>&nbsp;
            <input type="number" step="1" min="0" name="bookProfile.price" class="form-control form-control-noNewline">
        </div>
        <div class="form-group form-group-auto">
            <label>简介</label><font color="#FF0000">*</font><textarea id="intro" name="bookProfile.intro" class="form-control" rows="3"></textarea>
        </div>
        <div class="form-group form-group-auto">
            <label>图书封面</label><font color="#FF0000">*</font><input name="bookProfile.coverPicture" type="file" accept="image">
        </div>
        <div class="form-group form-group-auto">
            <label>其他图片（一）</label><font color="#FF0000">*</font><input name="bookProfile.otherPicture"  type="file" accept="image" class="file">
        </div>
        <div class="form-group form-group-auto">
            <label>其他图片（二）</label><font color="#FF0000">*</font><input name="bookProfile.otherPicture"  type="file" accept=image class="file">
        </div>

        <div class="form-group form-group-auto">
            <label id=warning></label>
        </div>
        <div class="clearfix"> </div>
        <div id=confirm class="register-but">
            <input type="button" id=commit value="确认添加">
        </div>
        <div class="clearfix"> </div>
    </form>

</div>

<script src="<%=path%>/js/fileinput.js"></script>
<script src="<%=path%>/js/fileinput.min.js"></script>
<script src="<%=path%>/js/zh.min.js"></script>
<script>
    $("#cate").change(function(){
        $("#category").empty();
        switch ($("#cate").val())
        {
            <s:iterator value="#category1List">
            case '<s:property value="category1Name"/>':
            <s:iterator value="category2List">
                $("#category").append($("<option>").val("<s:property value='category2Name'/>").text("<s:property value='category2Name'/>"));
            </s:iterator>
                break;
            </s:iterator>
        }
    });

    $("input[name='bookProfile.coverPicture']").fileinput({
        showUpload : false,
        allowedFileExtensions: ['jpg','jpeg','png','gif','bmp'],
        browseLabel : "浏览",
        language : 'zh'
    });
    $("input[name='bookProfile.otherPicture']").fileinput({
        showUpload : false,
        allowedFileExtensions: ['jpg','jpeg','png','gif','bmp'],
        browseLabel : "浏览",
        language : 'zh'
    });
    $("#commit").click(function(){
        var obj=$('#bookinfo').find('#warning');
        var type="^[0-9]*[1-9][0-9]*$";
        var r=new RegExp(type);
        if($("input[name='bookProfile.coverPicture']").val()=="")
        {obj.html("图书封面必须上传");return;}
        if($("input[name='bookProfile.otherPicture']").val()=="")
        {obj.html("必须上传");return;}
        if($("input[name='bookProfile.bookName']").val()=="")
        {obj.html("图书名称必须填写");return;}
        if($("input[name='bookProfile.author']").val()=="")
        {obj.html("图书作者必须填写");return;}
        if($("input[name='bookProfile.isbn']").val()=="")
        {obj.html("图书ISBN必须填写");return;}
        if($("input[name='bookProfile.press']").val()=="")
        {obj.html("图书出版社必须填写");return;}
        if($("input[name='bookProfile.amount']").val()=='')
        {obj.html("请输入库存");return;}
        if($("input[name='bookProfile.price']").val()=="")
        {obj.html("请输入价格");return;}
        if($("#introduction").val()=='')
        {obj.html("请输入简介");return;}
        obj.html("提交中...");
        $("#form").submit();
    });
</script>
<jsp:include page="footer.jsp"/>

</body>
</html>

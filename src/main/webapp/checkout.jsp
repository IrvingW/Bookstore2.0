<%--
  Created by IntelliJ IDEA.
  User: lvjiawei
  Date: 2018/3/17
  Time: 下午8:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="global.jsp"%>
<html>
<head>
    <title>Checkout</title>
</head>
<body>
<script src="<%=path%>/js/cryptojs/components/core.js"></script>
<script src="<%=path%>/js/cryptojs/rollups/aes.js"></script>
<script src="<%=path%>/js/cryptojs/components/enc-utf16.js"></script>
<script src="<%=path%>/js/cryptojs/rollups/md5.js"></script>
<script src="<%=path%>/js/cryptojs/components/mode-ecb.js"></script>
<script src="<%=path%>/js/cryptojs/components/pad-zeropadding.js"></script>
<script>
    function Encrypt(data){
        var key = CryptoJS.enc.Utf8.parse("h12ekx0qwdj9cup6");
        var src = CryptoJS.enc.Utf8.parse(data);
        var encrypted = CryptoJS.AES.encrypt(src, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
        return encrypted.toString();
    }

    function Decrypt(data){
        var key = CryptoJS.enc.Utf8.parse("h12ekx0qwdj9cup6");
        var decrypt = CryptoJS.AES.decrypt(data, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
        return CryptoJS.enc.Utf8.stringify(decrypt).toString();
    }

    function test() {
        alert(Encrypt("iloveu"));
    }

    function test2(){
        alert(Decrypt(Encrypt("iloveu")));
    }
</script>
<!-- header -->
<s:action name="header" executeResult="true" namespace="/"/>
<div class="cart-items">
    <div class="container">
<div id="tip"></div>
<h3>确认订单信息</h3>

    <div id="checkout" class="cart-header">
        <div class="cart-sec simpleCart_shelfItem">
            <div class="cart-item-info">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>书名</th>
                        <th>数量</th>
                        <th>价格</th>
                    </tr>
                    </thead>
                    <tbody>
                    <s:iterator value="#cartList" var="st">
                    <tr>
                        <td><a href="<%=path%>/bookAction/showBookProfile?bookID=<s:property value="#st.bookID"/>"><s:property value="#st.bookName"/></a></td>
                        <td><s:property value="#st.amount"/></td>
                        <td><s:property value="#st.price"/></td>
                    </tr>
                    </s:iterator>
                    </tbody>
                </table>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>


        <span>总金额：<s:property value="#totalPrice"/></span>

        <form class="form-group-auto" id="addrForm" action="<%=path%>/orderAction/createOrder">
            <label>请填写收货地址</label>
            <input type="text" id="address" name="address" placeholder="请输入收货地址">
            <label>请输入支付密码</label>
            <input type="password"id="password" name="password">
            <a href="#" class="add-cart item_add" onclick="gotoPay()">提交</a>
        </form>
    </div>
</div>
<script>
    function gotoPay(){
        if($("#address").val() == "" || $("#password").val() == ""){
            showTip("请填写地址或密码！",'danger');
        }
        else{
            var encryptAddr = Encrypt($("#address").val());
            var encryptPwd = Encrypt($("#password").val());
            var params = $("#addrForm").serialize();
            //$("#address").val(encryptAddr);
            //$("#password").val(encryptPwd);
            $.ajax({
               //url:'<%=path%>/orderAction/createOrder',
                url:'<%=path%>/testKafka/send',
               type:'POST',
               data:{
                   'address':encryptAddr,
                   'password':encryptPwd
               },
                success:function(msg){
                   if(msg.success){
                       showTip('提交订单成功！','success');
                       window.setTimeout("window.location='<%=path%>/index.jsp'",1500);
                   }
                   else{
                       showTip('提交订单错误！','danger');
                   }
                },
                error:function(xhr,status,error){
                    alert('status='+status+',error='+error);
                }
            });

            //$("#addrForm").submit();
        }
    }
</script>


</body>
</html>

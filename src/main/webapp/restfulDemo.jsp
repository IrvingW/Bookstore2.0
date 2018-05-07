<%--
  Created by IntelliJ IDEA.
  User: lvjiawei
  Date: 2018/5/3
  Time: 下午6:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="global.jsp"%>
<html>
<head>
    <title>Restful</title>
</head>
<body>

<div id="demo">
    <form :model="userID">
        <input v-model="userID.id" placeholder="输入用户id">
        <button @click="getOrders">GET</button>
    </form>
    <table :data="orders" style="width: 100%;">
        <tbody>
        <tr>
            <th v-for='item in columnList'>{{item}}</th>
        </tr>
        <tr v-for='item in orders'>
            <td v-for='it in columnList'>{{item[it]}}</td>
        </tr>
        </tbody>
    </table>
</div>

<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script>
    var app = new Vue({
        el: '#demo',
        data: {
            userID: { id:'' },
            columnList: ['orderID', 'buyerID', 'address', 'orderDate', 'totalPrice', 'status'],
            orders: [{'orderID': 0, 'buyerID': 0, 'address':'0', 'orderDate':0, 'totalPrice':0, 'status':'0'}]
        },
        methods: {
            getOrders: function() {
                var _self = this;
                var userID = this.userID.id;
                console.log(userID);
                axios.get('localhost:8080/yab/restful/showMyOrders/'+userID).then(function (res) {
                    console.log(res);
                    _self.orders = res;
                }).catch(function (error) {
                    console.log(error);
                });
                /*
                $.ajax({
                    url: '/restful/showMyOrders/'+userID,
                    type: 'GET',

                    success:function(data) {
                        _self.orders = data;
                    },
                    error:function(xhr,status,error){
                        //alert('status='+status+',error='+error);
                        console.log(error);
                    }
                });
                */
            }
        }
    });
</script>

</body>
</html>

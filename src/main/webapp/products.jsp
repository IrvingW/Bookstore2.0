<%--
  Created by IntelliJ IDEA.
  User: lvjiawei
  Date: 2018/3/17
  Time: 上午11:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="global.jsp"%>
<html>
<head>
    <title>Products</title>
</head>
<body>
<s:action name="header" executeResult="true" namespace="/"/>

<script>
    $(document).ready(function(){
        $(".tab1 .single-bottom").hide();
    });

    $(document).ready(function(){
        $('#select-status :checkbox[type="checkbox"]').each(function(){
            $(this).click(function(){
                if($(this).attr('checked')){
                    $(':checkbox[type="checkbox"]').removeAttr('checked');
                    $(this).attr('checked','checked');
                }
            });
        });

    });
</script>

<div class="products">
    <div class="container">
        <h2>图书浏览</h2>
        <h3 align="center" id="selete-info">
            <s:if test="#searchName!=''">
                关键字：<s:property value="#searchName"/>
            </s:if>
            <s:if test="#category1Name!=''">
                分类：<s:property value="#category1Name"/>
            </s:if>
            <s:if test="#category2Name!=''">
                标签：<s:property value="#category2Name"/>
            </s:if>
        </h3>

        <div class="col-md-9 product-model-sec">
            <div class="clearfix"> </div>

            <!--具体图书信息div，使用struts迭代器-->
            <s:iterator value="#allBooks"  status="st">
                <div id="product<s:property value="#st.index"/>" class="product-grid">
                    <a href="<%=path%>/bookAction/showBookProfile?bookID=<s:property value="bookID"/>">
                        <div class="more-product"><span> </span></div>
                        <div class="product-img b-link-stripe b-animate-go  thickbox">
                            <!--图书封面图片 ，需要imageID-->
                            <img src="<%=path%>/imageAction/showImage?imageID=<s:property value="imageID"/>" class="img-thumbnail" alt="">
                            <div class="b-wrapper">
                                <h4 class="b-animate b-from-left  b-delay03">
                                    <button>浏览</button>
                                </h4>
                            </div>
                        </div>
                    </a>
                    <div class="product-info simpleCart_shelfItem">
                        <div class="product-info-cust prt_name">
                            <h4><s:property value="bookName"/></h4>
                            <span class="book-isbn">ISBN:<s:property value="isbn"/></span>
                            <div class="ofr">
                                <p class="pric1">作者：<s:property value="author"/></p><br>
                                <p class="pric1">分类：<s:property value="category1"/>&nbsp;标签：<s:property value="category2"/></p><br>
                                <p class="disc">库存：<s:property value="amount"/> </p><br>
                            </div>
                            <div class="clearfix"> </div>
                        </div>
                    </div>
                </div>
            </s:iterator>
            <div class="clearfix"> </div>
            <ul id="pagination-digg" style="float: right;">
            </ul>
        </div>
        <div class="col-md-3 rsidebar span_1_of_left">
            <section  class="sky-form">
                <div class="product_right">
                    <h4 class="m_2"><span class="glyphicon glyphicon-minus" aria-hidden="true"></span>图书类型</h4>
                    <div id="cate-default" class="tab1">
                        <ul class="place">
                            <li onclick="selectCategory1('');selectCategory2('')" class="sort">全部</li>
                        </ul>
                        <div class="clearfix"> </div>
                    </div>
                    <s:iterator value="#category1List"  status="st1">
                        <div  id="cate-<s:property value="#st1.index"/>" class="tab1">
                            <script>
                                $(document).ready(function () {
                                    $("#cate-<s:property value='#st1.index'/> ul").click(function(){
                                        $("#cate-<s:property value='#st1.index'/> .single-bottom").slideToggle(300);

                                    });
                                });

                            </script>
                            <ul class="place">
                                <li class="sort"><s:property value="category1Name"/></li>
                                <li class="by"><span class="glyphicon glyphicon-triangle-bottom" aria-hidden="true"></span></li>
                            </ul>
                            <div class="clearfix"> </div>
                            <div class="single-bottom">
                                <a href="#" onclick="selectCategory1('<s:property value="category1Name"/>')"><p><s:property value="category1Name"/></p></a>
                                <s:iterator value="category2List" status="st2">
                                    <a href="#" onclick="selectCategory2('<s:property value="category2Name"/>')"><p><s:property value="category2Name"/></p></a>
                                </s:iterator>
                            </div>

                        </div>
                    </s:iterator>
                </div>
            </section>
        </div>
        <div class="clearfix"> </div>



        <style type="text/css" media="screen">
            #pagination-digg li {
                border:0; margin:0; padding:0; font-size:11px;
                list-style:none; /* savers */ float:left;
            }
            #pagination-digg a {
                border:solid 1px #9aafe5; margin-right:2px;
            }
            #pagination-digg .previous-off,#pagination-digg .next-off  {
                border:solid 1px #DEDEDE; color:#888888; display:block; float:left;
                font-weight:bold; margin-right:2px; padding:3px 4px;
            }
            #pagination-digg .next a,#pagination-digg .previous a {
                font-weight:bold;
            }
            #pagination-digg .active {
                background:#2e6ab1; color:#FFFFFF; font-weight:bold; display:block;
                float:left; padding:4px 6px; /* savers */ margin-right:2px;
            }
            #pagination-digg a:link,#pagination-digg a:visited { color:#0e509e; display:block;
                float:left; padding:3px 6px; text-decoration:none;
            }
            #pagination-digg a:hover {
                border:solid 1px #0e509e;
            }
        </style>

    </div>
</div>
<!--//products-->
<jsp:include page="footer.jsp"/>

</body>
</html>

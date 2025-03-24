<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <style>
        .content {
            background-color:rgb(247, 245, 245);
            width:80%;
            margin:auto;
        }
        .innerOuter {
            border:1px solid lightgray;
            width:80%;
            margin:auto;
            padding:5% 10%;
            background-color:white;
        }

        #boardList {text-align:center;}
        #boardList>tbody>tr:hover {cursor:pointer;}

        #pagingArea {width:fit-content; margin:auto;}
        
        #searchForm {
            width:80%;
            margin:auto;
        }
        #searchForm>* {
            float:left;
            margin:5px;
        }
        .select {width:20%;}
        .text {width:53%;}
        .searchBtn {width:20%;}
    </style>
</head>
<body>
    
    <jsp:include page="../include/header.jsp" />

    <div class="content">
        <br><br>
        <div class="innerOuter" style="padding:5% 10%;">
            <h2>게시판</h2>
            <br>
            
            <c:if test="${ !empty sessionScope.loginMember }">
	            <!-- 로그인 후 상태일 경우만 보여지는 글쓰기 버튼 -->
	            <a class="btn btn-secondary" style="float:right;" href="form.bo">글쓰기</a>
	        </c:if>
	        
            <br>
            <br>
            <table id="boardList" class="table table-hover" align="center">
                <thead>
                    <tr>
                        <th>글번호</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>조회수</th>
                        <th>작성일</th>
                        <th>첨부파일</th>
                    </tr>
                </thead>
                <tbody>
                	
                	<script>
                		function goBoard(num){
                			location.href = `boards/\${num}`;
                		}
                	</script>
                
                	<c:choose>
                		<c:when test="${not empty map.boards}">
	                		<c:forEach var="board" items="${ map.boards }">
			                    <tr onclick="goBoard(${ board.boardNo });">
			                        <td>${ board.boardNo }</td>
			                        <td>${ board.boardTitle  }</td>
			                        <td>${ board.boardWriter }</td>
			                        <td>${ board.count }</td>
			                        <td>${ board.createDate }</td>
			                        
			                        <c:if test="${ not empty board.changeName }">
			                        	<td>💌</td>
			                        </c:if>
			                    </tr>
		                    </c:forEach>
	                    </c:when>
	                    <c:otherwise>
		                    <tr>
		                    	<th colspan="6">게시글이 존재하지 않습니다.</th>
		                    </tr>
	                    </c:otherwise>
                    </c:choose>
                    
                </tbody>
            </table>
            <br>

            <div id="pagingArea">
                <ul class="pagination">
                
                    <li class="page-item disabled"><a class="page-link" href="#">이전</a></li>
                    
                    <c:forEach begin="${ map.pageInfo.startPage }" 
                    		   end="${ map.pageInfo.endPage }"
                    		   var="num">

                    	<li class="page-item">
                    		<c:choose>
                    			<c:when test="${ empty map.condition }">
                    				<!-- 일반 게시글 목록 조회 요청 -->
	                    			<a class="page-link" href="boards?page=${ num }">${ num }</a>
	                    		</c:when>
	                    		<c:otherwise>
		                    		<!-- 검색 게시글 목록 조회 요청 -->
		                    		<a class="page-link" href="search?page=${ num }&condition=${map.condition}&keyword=${map.keyword}">${ num }</a>
	                    		</c:otherwise>
                    		</c:choose>
                    	</li>
                    	
                    </c:forEach>
                    
                    <li class="page-item"><a class="page-link" href="#">다음</a></li>
                    
                </ul>
            </div>

            <br clear="both"><br>

            <form id="searchForm" action="search" method="get" align="center">
                <div class="select">
                    <select class="custom-select" name="condition">
                        <option value="writer">작성자</option>
                        <option value="title">제목</option>
                        <option value="content">내용</option>
                    </select>
                </div>
                <div class="text">
                    <input type="text" class="form-control" name="keyword" value="${ map.keyword }">
                </div>
                <button type="submit" class="searchBtn btn btn-secondary">검색</button>
            </form>
            <br><br>
        </div>
        <br><br>

    </div>
    
    <!-- search에서 내가 선택해서 검색한 select의 option에 selected 속성 주기 -->
    <script>
    	// 현재 자바스크립트 사용목적 == HTML요소 조작
    	// const selected = document.querySelector();
    	// 자바스크립트에서 URL의 파라미터값 뽑기
    	window.onload = function(){
    		// 자바스크립트 방식
    		const currentUrl = window.location.href;
    		//console.log(currentUrl);
    		const obj = new URL(currentUrl);
    		//console.obj;
    		const condition = obj.searchParams.get('condition');
    		//console.log(`condition : \${condition}`);
    		const selected = document.querySelector(`option[value='\${condition}']`);
    		selected.selected = true;
    		// console.log(selected);
    		
    		// EL구문 (서버단)
    		//console.log('${map.condition}');
    	}
    </script>

    <jsp:include page="../include/footer.jsp" />

</body>
</html>
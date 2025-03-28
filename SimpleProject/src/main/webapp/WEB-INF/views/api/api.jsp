<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>병원 데이터 출력해보기</title>
</head>
<body>
	
	<jsp:include page="../include/header.jsp"/>
	
	<button class="btn btn-sm btn-info" onclick="callHospital();">병원 정보 보기 </button>
	
	<div style="width:1000px; height:600px; margin:auto;">
		<table class="table">
			<thead>
				<tr>
					<th width="200">병원명</th>
					<th width="300">주소지</th>
					<th width="300">오시는길</th>
					<th width="150">전화번호</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
	
	</div>
	
	<script>
		function callHospital() {
			
			$.ajax({
				url: 'hospitals',
				type : 'get',
				success : result => {
					// console.log(result);
					const hospitals = result.body;
					
					const resultEl = hospitals.map(e => 
														`
															<tr>
																<td>\${e.INST_NM}</td>
																<td>\${e.ADDR}</td>
																<td>\${e.ESNS_RGHMP}</td>
																<td>\${e.RPRS_TLHN_1}</td>
															</tr>
														`).join('');
					document.querySelector('tbody').innerHTML = resultEl;
				}		
						
			})
		}	
	
	</script>
	
	<jsp:include page="../include/footer.jsp" />
</body>
</html>
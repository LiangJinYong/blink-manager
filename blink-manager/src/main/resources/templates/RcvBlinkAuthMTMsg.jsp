<%--
  - 작성자: Lucas
  - 일자: 2019.12.20
  - 설명: MT 발송 요청 수신 Page
  --%>

<%@ page language="java" contentType="application/json; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="java.nio.charset.Charset"%>
<%@ page import="java.io.BufferedReader"%>
<%@ page import="java.io.FileReader"%>
<%@ page import="java.io.IOException"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.Calendar"%>

<%@ page import="org.json.simple.*"%>
<%@ page import="org.json.JSONObject"%>
<%@ page import="org.json.JSONArray"%>
<%@page import="org.apache.log4j.*"%>

<%@ page import="java.sql.*"%>
<%@ page import="javax.sql.*"%>

<%
	request.setCharacterEncoding("utf-8");
	response.setContentType("text/html; charset=utf-8");

	StringBuffer jsonBuffer = new StringBuffer();
	JSONObject jsonObject = null;
	String json = null;
	String line = null;
	
	String query = "";
	String authCode = "";
	String phoneNumber = "";
	String mtMsg = "";
	
	// DB 정보 - 상용 
  	// String sBroadcastDB = "jdbc:mysql://211.233.76.193/itvAlarm?characterEncoding=euckr";
	// String callback = "#36811010";	
	
	// DB 정보 - 상용 241 
  	// String sBroadcastDB = "jdbc:mysql://211.233.76.193/itvMnStudio?characterEncoding=euckr";
 	// String callback = "#36811010";
 
	// DB 정보 - 테스트
	// String sBroadcastDB = "jdbc:mysql://172.16.0.134/itvMnStudioTemplate?characterEncoding=euckr";
	// String callback = "#36811010";

	// DB 정보 - AWS 상용 
  	String sBroadcastDB = "jdbc:mysql://blink-product-database.cluster-ro-c5xlbu29akak.ap-northeast-2.rds.amazonaws.com:3306/blink_mt";
 	String callback = "03180395171";

			
	authCode = request.getParameter("authCode")==null?"":request.getParameter("authCode");
	phoneNumber = request.getParameter("phoneNumber")==null?"":request.getParameter("phoneNumber");
	
	// MSG 셋팅
	mtMsg = "[Blink] 핸드폰 인증번호 [" + authCode + "]를 입력해 주세요.";
			
	System.out.println("\n*************************************");
	System.out.println("* MT 요청 들어온 메시지");
	System.out.println("* Auth Code : " + authCode);
	System.out.println("* 발송 MSG : " + mtMsg);
	System.out.println("* 전화번호 : " + phoneNumber);
	Class.forName("com.mysql.jdbc.Driver");

	java.sql.Connection conn = null;

	StringBuffer sbSQL = null;
	String sSQL = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	int resultValue = 0;
	String rtnStr = "";
	
	try{
		// conn = DriverManager.getConnection(sBroadcastDB, "itv", "itvpw");	
		conn = DriverManager.getConnection(sBroadcastDB, "blinkdb", "blinkdbpw");	

		sbSQL = new StringBuffer();
		sbSQL.append(" INSERT INTO em_smt_tran (mt_refkey, date_client_req, content, callback, service_type, broadcast_yn, msg_status, recipient_num) ");
		sbSQL.append(" VALUES ");
		sbSQL.append(" ('ARS', NOW(), ?, ?, '0', 'N', '1', ?)");
		sSQL = sbSQL.toString();
		
		ps = conn.prepareStatement(sSQL);

		int idx = 1;
		
		ps.setString(idx++, mtMsg);
		ps.setString(idx++, callback);
		ps.setString(idx++, phoneNumber);
		
		resultValue = ps.executeUpdate();
	}catch (Exception e) {
		e.printStackTrace();
		resultValue = 0;
	}finally{
		if(resultValue == 1){
			rtnStr = "SUCCESS";	
		}else{
			rtnStr = "FAIL";	
		}
		System.out.println("* 발송 결과 : " + rtnStr);
		out.clear();
		out.println(rtnStr);
		conn.close();
	}
%>

<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/jsp/Base.jsp"%>
<%@ page import="myOwnLibrary.util.Util"%>
<%@ page import="myOwnLibrary.util.DateUtil"%>
<%@ page import="tallyBook.action.MoneyDetailsReport"%>
<%
			String minTime = Util.changeToNotNull(request
			.getParameter("minTime"));
	String maxTime = Util.changeToNotNull(request
			.getParameter("maxTime"));
	String year = Util.changeToNotNull(request.getParameter("year"));
	String month = Util.changeToNotNull(request.getParameter("month"));
	//����ǰ��յ���ѡ��ʱ�����ݽ���ѡ��ʱ�䣬�ͼ����µ���ʼʱ��ͽ���ʱ��.
	if (!Util.isEmpty(year) && !"-1".equals(year)) {
		//ֻ��ѡ������ݲŽ��п�ʼʱ��ͽ���ʱ���ת��.
		String[] ans = MoneyDetailsReport.getStartAndEndTime(year,
		month);
		minTime = ans[0];
		maxTime = ans[1];
		//����������ڽ���ȡǰ���һ��
		maxTime = DateUtil.toString(DateUtil.afterAnyDay(DateUtil
		.getDate(maxTime, "yyyy-MM-dd"), -1));
	}
	String money = Util.changeToNotNull(request.getParameter("money"));
	String maxmoney = Util.changeToNotNull(request
			.getParameter("maxmoney"));
	String shopCard = Util.changeToNotNull(request
			.getParameter("shopCard"));
	String inOrOutTypeValue = Util.changeToNotNull(request
			.getParameter("inOrOutTypeValue"));
	String moneySortValue = Util.changeToNotNull(request
			.getParameter("moneySort"));
	String moneyDesc = Util.changeToNotNull(request
			.getParameter("moneyDesc"));
%>
<%@ page import="tallyBook.dao.TallyTypeDao"%>
<%@ page import="tallyBook.pojo.TallyType"%>
<html>
	<head> 
		<title>��֧��Ϣ��ѯ�б�</title>
		<script src="<%=appPath%>/jsp/extexample/sample.js"
			type="text/javascript" language="javascript"></script>
		<script src="<%=appPath%>/jsp/extexample/sample-grid.js"
			type="text/javascript" language="javascript"></script>
		<script src="<%=appPath%>/jsp/extexample/extgrid.js"
			type="text/javascript" language="javascript"></script>
	</head>
	<body style="overflow:auto;">
		<div id="grid"></div> 
	</body>
</html>

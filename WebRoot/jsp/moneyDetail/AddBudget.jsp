<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ include file="/jsp/Base.jsp"%>
<%@ page import="myOwnLibrary.util.Util"%>
<%@ page import="common.base.SpringContextUtil"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="myOwnLibrary.util.GroovyUtil2"%>
<%@ page import="tallyBook.dao.*"%>
<%@ page import="java.util.*"%>
<%@ page import="tallyBook.dao.CommonSelDao"%>
<%
	String title = "添加记录";
	boolean addNew = true;
	CommonSelDao dao = new CommonSelDao();
	List years = dao.getAllYears();
	List months = dao.getAllMonths();
	GroovyUtil2 dynamicGroovy = new GroovyUtil2(); 
	Map budgetDetail = (Map) dynamicGroovy
			.invokeScriptMethod(request.getRealPath("/")+
			"groovy\\BudgetGroovy.groovy",
			"queryBudgetDetail", null);
	Map budgetMoneyDetail = (Map) dynamicGroovy
			.invokeScriptMethod(request.getRealPath("/")+
			"groovy\\BudgetGroovy.groovy",
			"queryMoneyByBudget", null);
	List budgetDetailTime = (List) dynamicGroovy
			.invokeScriptMethod(request.getRealPath("/")+
			"groovy\\BudgetGroovy.groovy",
			"queryBudgetDetailTime", null);
	DaoUtil util = (DaoUtil)SpringContextUtil.getBean("daoUtil"); 
	List list = util
			.doGetSqlQueryList("select id,name,get_moneytypeBybudget(t.id) mname from budgettype t ");
	List budgettypes = util
			.doGetSqlQueryList("select id,name from budgettype t ");
%>
<html>
	<head>
		<title><%=title%></title>
		<script type='text/javascript' src='<%=appPath%>/js/common/date.js'></script>
		<link rel="stylesheet" type="text/css"
			href="<%=appPath%>/js/flexigrid_my/css/flexigrid_blue.css">
		<script type="text/javascript"
			src="<%=appPath%>/js/flexigrid_my/jquery-1.3.2.min.js"></script>
		<script type="text/javascript"
			src="<%=appPath%>/js/flexigrid_my/flexigrid.js"></script>
		<LINK rel=stylesheet type=text/css
			href="<%=appPath%>/js/common/red.css">
		<script type='text/javascript'
			src='<%=appPath%>/dwr/interface/dwrBo.js'></script>
		<script language="javascript" type="text/javascript"
			src="<%=appPath%>/js/calendar/WdatePicker.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=appPath%>/jsp/moneyDetail/AddBudget.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=appPath%>/jsp/moneyDetail/mySimpliTable.css">
	</head>
	<body>
		<input type='hidden' id='status' value="<%=addNew%>">
		<div class="content"
			style="display: inline; top: 0px; left: 0px; width: 100%; height: 100%;">
			<table>
				<tr>
					<td>
						预算类别:
					</td>
					<td>
						<input id="budgetName" type="text" value="${budgetName}">
					</td>
					<td>
						收支类别:
					</td>
					<td>
						<input type='hidden' id='moneyType' value='${moneyType}' />
						<input type='text' id='moneyTypeDesc' value='${'
							moneyTypeDesc'}'
							readOnly='true' />
						<button id='tallyTypeImg'
							onclick="test('moneyType','moneyTypeDesc')"></button>
						<span></span>
					</td>
				</tr>
				<tr>
					<td colspan="4" align="left">
						<%
						if ("添加记录".equals(title)) {
						%>
						<input type='hidden' id='addType' />
						<button onclick="add();">
							添加预算类别
						</button>
						<%
						} else {
						%>
						<button onclick="update();">
							修改预算类别
						</button>
						<%
						}
						%>
						<button onclick="del()">
							删除
						</button>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<span style="color: red" id="mes"></span>
					</td>
				</tr>
			</table>
			<table width="60%" class="mytable">
				<tr>
					<th>
						选择
					</th>
					<th>
						预算类别
					</th>
					<th>
						金额类别
					</th>
				</tr>
				<%
						for (int i = 0, j = list.size(); i < j; i++) {

						Object[] objs = (Object[]) list.get(i);
				%>
				<tr>
					<td>
						<input type="checkbox" name='ck' value="<%=objs[0]%>"></input>
					</td>
					<td><%=objs[1]%></td>
					<td><%=objs[2]%></td>
				</tr>
				<%
				}
				%>
			</table>
			<hr> 
				<table width="800px" style="align: top">
					<tr>
						<td>
							年份:
						</td>
						<td>
							<select id="year" name="year" style="width: 150px"
								onchange="resetChooseReport();">
								<%
										if (years != null) {
										for (int i = 0; i < years.size(); i++) {
											Object[] objs = (Object[]) years.get(i);
								%>
								<option value="<%=objs[0].toString()%>"><%=objs[1].toString()%></option>
								<%
									}
									}
								%>
							</select>
						</td> 
						<td>
							月份:
						</td>
						<td>
							<select id="month" name="month" style="width: 150px"
								onchange="resetChooseReport();">
								<option value="-1">
									请选择
									</optiozn>
									<%
											if (months != null) {
											for (int i = 0; i < months.size(); i++) {
												Object[] objs = (Object[]) months.get(i);
									%>
								
								<option value="<%=objs[0].toString()%>"><%=objs[1].toString()%></option>
								<%
									}
									}
								%>
							</select>
						</td>
						<td  align="left">
							<%
							if ("添加记录".equals(title)) {
							%>
							<input type='hidden' id='addType' />
							<button onclick="addDetail();" width='100'>
								 添加预算1
							</button> 
							<%
							} else {
							%>
							<button onclick="updateDetail();" width='100'>
								 修改预算
							</button> 
							<%
							}
							%>
							<button onclick="weishenme()" width='100'>
								删除
							</button>
						</td>
					</tr>
					</table><table id='budgettypes'>
					<tr>
					<%
							if (list != null) { 
							for (int i = 0; i < list.size(); i++) { 
								Object[] objs = (Object[]) list.get(i); 
					%> 
						<td width="100px"><%=objs[1].toString()%>:
						</td>
						<td>
							<input name="budgettype<%=objs[0].toString()%>">
						</td> 
					<%
						}
						}
					%></tr> 
				</table> 
			<hr>
			<div style="height: 500px; over-flow: scroll;">
				<table class="mytable" width="800px" height="500px">
					<TR>
						<th>
							选择
						</th>
						<th>
							年份
						</th>
						<th>
							月份
						</th>
						<%
								for (int i = 0, j = list.size(); i < j; i++) {
								Object[] objs = (Object[]) list.get(i);
						%>
						<th><%=objs[1]%></th>
						<%
						}
						%>
					</TR>
					<%
					
							for (int i = 0, j = budgetDetailTime.size(); i < j; i++) {
							String time = (String) budgetDetailTime.get(i);
							 
							String year = time.substring(0, 4);
							String month = time.substring(5, 7);
					%>
					<TR>
						<td>
							<input type='radio' name='chk' year="<%=year%>" month="<%=month%>">
						</td>
						<td><%=year%></td>
						<td><%=month%></td>
						<%
									for (int ii = 0, jj = list.size(); ii < jj; ii++) {
									Object[] objs = (Object[]) list.get(ii);
									String key = year + ", " + month + "," + objs[0];
									//实际金额
									BigDecimal d = (BigDecimal) budgetMoneyDetail.get(key); 
									double dd = 0.0;
									if (d != null)
										dd = d.doubleValue();
									//预算金额 
									BigDecimal b = (BigDecimal) budgetDetail.get(key);
									double bd = 0.0;
									if (d != null)
										bd = d.doubleValue(); 
									if (dd > bd) {
						%>
						<td style="color: red"><%=bd + "/" + dd%></td>
						<%
						} else {
						%>
						<td><%=bd + "/" + dd%></td>
						<%
								}
								}
						%>
					</tr>
					<%
					}
					%>
				</table>
			</div>
		</div>
	</body>
</html>

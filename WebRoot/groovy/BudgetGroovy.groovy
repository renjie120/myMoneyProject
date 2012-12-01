package tallyBook.action; 
import groovy.sql.*
import java.sql.* 
import java.util.*
  
class BudgetType  { 
    def oradb,oraclesql
    public BudgetType(){
        oradb = [url:"jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS = (PROTOCOL = TCP)(HOST = 127.0.0.1)(PORT = 1521))(CONNECT_DATA =(SERVER = DEDICATED)(SERVICE_NAME = lsq)))"
                     ,user:"lsqtest",
                     password:"lsqtest",driver:"oracle.jdbc.driver.OracleDriver"] 
        oraclesql  = Sql.newInstance(oradb.url,oradb.user,oradb.password,oradb.driver) 
    }
   
    def saveBudgetType(String name,String moneyType){
        def bid = -1
        oraclesql.eachRow("select id from budgettype where name = ?",[name]){
            bid = it.id 
        }
        if(bid==-1){
            oraclesql.execute("insert into budgettype(name) values (?)",[name])
            oraclesql.eachRow("select id from budgettype where name = ?",[name]){
                bid = it.id 
            }
            def mtypes = moneyType.split(",")
            mtypes.each{
                oraclesql.execute("insert into budgetandmoneytype values(?,?)",[bid,it])
            } 
            return "生成成功，新的id是："+bid
        }
        else{
        	return   "已经存在!"
        } 
    }
    
    def updateBudgetType(String id,String name,String moneyType){ 
          oraclesql.execute("update budgettype set name = ? where id = ?",[name,id]) 
          oraclesql.execute("delete from budgetandmoneytype where budgettypeid = ?",[id]);
          def mtypes = moneyType.split(",")
          mtypes.each{
                oraclesql.execute("insert into budgetandmoneytype values(?,?)",[bid,it])
          }  
          return   "更新完毕!"
    }
    
    def deleteBudgetType(id ){ 
    	def strs = id.split(',')
    	strs.each{
    		oraclesql.execute("delete from  budgettype where id = ?",[it]) 
    		oraclesql.execute("delete from budgetandmoneytype where budgettypeid = ?",[it]);
    	} 
    	return   "删除完毕!"
  }
    
    def queryBudgetDetail(){ 
    	def m = [:]
    	oraclesql.eachRow("select year,to_char(month,'00') month,budgettype,money from budgetdetail "){
    		def time =  it.year+','+it.month+','+it.budgettype
    		m[time] = it.money
    	}
    	return m
    }
    
    def queryMoneyByBudget(){ 
    	def m = [:]
    	oraclesql.eachRow('''
    			 select dt.year, dt.month, bt.budgettype, sum(dt.money) money
    			    from budgetdetail bt, budgetandmoneytype btr, money_detail_report_view dt
    			   where dt.year = bt.year
    			     and ' '||dt.month = to_char(bt.month,'09')
    			     and dt.money_type = btr.moneyttype
    			     and btr.budgettypeid = bt.budgettype
    			   group by dt.year, dt.month, bt.budgettype
    			  '''){ 
    		def time =  it.year+', '+it.month+','+it.budgettype
    		m[time] = it.money
    	}
    	return m
    }
    
   

    def queryBudgetDetailTime(){ 
    	def m = []
    	oraclesql.eachRow("select distinct year,to_char(month,'00') month from budgetdetail order by year ,month"){
    		m<<it.year+it.month
    	}
    	return m
    }
    
    def deleteBudgetDetail(y,m){    
    	oraclesql.execute("delete from  budgetdetail where year = ? and month = ?",[y,m])  
    	return   "删除完毕!"
    }
    
    def addBudgetDetail(m){   
    	def year  =m.get("year")
    	def month  =m.get("month")   
    	 if(!"-1".equals(month)){ 
    		 m.each{k,v-> 
	    		if(k.indexOf('budgettype')==0){
	    			k = k.replace('budgettype','') 
	    			oraclesql.execute("insert into budgetdetail(year,month,budgettype,money) values(?,?,?,?)",[year,month,k,v])
	    		}
	    	} 
    	}else{ 
    		(1..12).each{item->
    		    m.each{k,v-> 
		    		if(k.indexOf('budgettype')==0){
		    			k = k.replace('budgettype','')
		    			oraclesql.execute("insert into budgetdetail(year,month,budgettype,money) values(?,?,?,?)",[year,item,k,v])
		    		}
		    	} 
    		}
    	} 
    	return "保存预算数据成功";
   }
}

def addBudget(name,moneyType) {
	BudgetType tp = new BudgetType(); 
	tp.saveBudgetType(name,moneyType);  
}

def deleteBudget(id) {
	BudgetType tp = new BudgetType(); 
	tp.deleteBudgetType(id);  
}

def queryBudgetDetail() {
	BudgetType tp = new BudgetType(); 
	tp.queryBudgetDetail();  
}

def queryMoneyByBudget() {
	BudgetType tp = new BudgetType(); 
	tp.queryMoneyByBudget();  
}

def queryBudgetDetailTime() {
	BudgetType tp = new BudgetType(); 
	tp.queryBudgetDetailTime();  
}


def updateBudget(id,name,moneyType) {
	BudgetType tp = new BudgetType(); 
	tp.updateBudgetType(id,name,moneyType);  
}

def deleteBudgetDetail(year,month) {
	BudgetType tp = new BudgetType(); 
	println 12345
	tp.deleteBudgetDetail(year,month);  
}


def addBudgetDetail(m) {
	BudgetType tp = new BudgetType(); 
	tp.addBudgetDetail(m);  
}
 
//runtest name,moneyType 

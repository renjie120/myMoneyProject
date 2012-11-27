package tallyBook.action;

public class StockTreeNode {
	// id
	private String id;
	// 上级id
	private String pId;
	// 股票名称
	private String stockName;
	// 股票号
	private String stockNo;
	// 股票价格
	private String stockPrice;
	// 收益率
	private String profit;
	//是否父亲节点.
	private String isLeaf;
	// 股数
	private String num;
	// 消耗总金额
	private String cost;
	// 涨停价格
	private String price110;
	// 跌停价格
	private String price90;
	// 收益金额
	private String profitSum;
	// 交易费用
	private String fee;
	// 缴纳的税费
	private String tax;
	// 交易费用
	private String type;

	public StockTreeNode() {
		pId = "";
		id = "";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPId() {
		return pId;
	}

	public void setPId(String id) {
		pId = id;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getStockNo() {
		return stockNo;
	}

	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}

	public String getStockPrice() {
		return stockPrice;
	}

	public void setStockPrice(String stockPrice) {
		this.stockPrice = stockPrice;
	}

	public String getProfit() {
		return profit;
	}

	public void setProfit(String profit) {
		this.profit = profit;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getPrice110() {
		return price110;
	}

	public void setPrice110(String price110) {
		this.price110 = price110;
	}

	public String getPrice90() {
		return price90;
	}

	public void setPrice90(String price90) {
		this.price90 = price90;
	}

	public String getProfitSum() {
		return profitSum;
	}

	public void setProfitSum(String profitSum) {
		this.profitSum = profitSum;
	}

	public String getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}
}

package stock.pojo;

/**
 * 交易配置实体类
 * @author renjie120 419723443@qq.com
 *
 */
public class StockConfigVO {
	public StockConfigVO(){}
	public StockConfigVO(int configSno,double stockFee,double taxFee,double minFee){
		this.configSno = configSno;
		this.stockFee = stockFee;
		this.taxFee = taxFee;
		this.minFee = minFee;		
	}
	/**
	 * 配置流水号
	 */
	private int configSno;
	/**
	 * 手续费率
	 */
	private double stockFee;
	/**
	 * 印花税费率
	 */
	private double taxFee;
	/**
	 * 最低交易费率
	 */
	private double minFee;
	/**
	 * 配置名称
	 */
	private String configName;
	public int getConfigSno() {
		return configSno;
	}
	public void setConfigSno(int configSno) {
		this.configSno = configSno;
	}
	public double getStockFee() {
		return stockFee;
	}
	public void setStockFee(double stockFee) {
		this.stockFee = stockFee;
	}
	public double getTaxFee() {
		return taxFee;
	}
	public void setTaxFee(double taxFee) {
		this.taxFee = taxFee;
	}
	public String getConfigName() {
		return configName;
	}
	public void setConfigName(String configName) {
		this.configName = configName;
	}
	public double getMinFee() {
		return minFee;
	}
	public void setMinFee(double minFee) {
		this.minFee = minFee;
	}
}

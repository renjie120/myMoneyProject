package stock.pojo;

/**
 * ��������ʵ����
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
	 * ������ˮ��
	 */
	private int configSno;
	/**
	 * ��������
	 */
	private double stockFee;
	/**
	 * ӡ��˰����
	 */
	private double taxFee;
	/**
	 * ��ͽ��׷���
	 */
	private double minFee;
	/**
	 * ��������
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

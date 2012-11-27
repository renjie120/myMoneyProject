package stock.bo;

import java.util.Date;

import myOwnLibrary.util.Util;
import stock.pojo.StockConfigVO;
import stock.pojo.StockHolderOnVO;
import stock.pojo.StockVO;
import stock.dao.StockDao;
/**
 * ���ڹ�Ʊ�ĳ�����Ϣ�������ࡣ
 * ���������֣�������Ʊ������֣�������Ʊ�����Ӳ֣����ֵ�
 * @author renjie120 419723443@qq.com
 *
 */
public class StockUtil { 
	public static void main(String[] a){
		StockHolderOnVO stockHolderOnVO = new StockHolderOnVO();
		StockConfigVO buyConfig = new StockConfigVO(1,0.001,0,5);
		StockConfigVO sellConfig = new StockConfigVO(2,0.001,0.001,5);
		 
		StockVO stockVO = new StockVO("test","test",13.18,1200,new Date());
		StockUtil.bindStockConfig(stockVO, buyConfig);
		stockHolderOnVO = StockUtil.addStock(stockHolderOnVO, stockVO); 
		 
		StockVO stockVO2 = new StockVO("test","test",13.97,400,new Date());
		StockUtil.bindStockConfig(stockVO2, sellConfig);
		stockHolderOnVO = StockUtil.reduceStock(stockHolderOnVO, stockVO2); 
		
		StockVO stockVO3 = new StockVO("test","test",14.15,400,new Date());
		StockUtil.bindStockConfig(stockVO3, sellConfig);
		stockHolderOnVO = StockUtil.reduceStock(stockHolderOnVO, stockVO3); 
		
		StockVO stockVO4 = new StockVO("test","test",14.20,400,new Date());
		StockUtil.bindStockConfig(stockVO4, sellConfig);
		System.out.println(StockUtil.reduceStock(stockHolderOnVO, stockVO4));
	}
	
	
	/**
	 * ���÷���
	 * @param stockVO
	 * @param config
	 */
	public static void bindStockConfig(StockVO stockVO,StockConfigVO config){
		double buyPrice = stockVO.getDealPrice();
		int buyNum = stockVO.getDealStockNum(); 
		double marketMoney =  Util.multiply(buyPrice, buyNum); 
		double stockFee= Util.multiply(marketMoney, config.getStockFee());
		stockFee = stockFee>config.getMinFee()?stockFee:config.getMinFee();
		double taxFee = Util.multiply(marketMoney, config.getTaxFee());
		//���ý���������
		stockVO.setDealFee(stockFee);
		//����ӡ��˰
		stockVO.setDealTax(taxFee);
	}
	
	/**
	 * �����Ʊ
	 * @param stockHolderOnVO
	 * @param stockVO
	 * @return
	 */
	public static StockHolderOnVO addStock(StockHolderOnVO stockHolderOnVO,StockVO stockVO){  
		double buyPrice = stockVO.getDealPrice();
		int buyNum = stockVO.getDealStockNum();
		//������
		double profitMoney;
		//������
		double profit;
		//���봿��ֵ
		double marketMoney =  Util.multiply(buyPrice, buyNum); 
		//ͳ��������
		stockHolderOnVO.setDealFee(getDealFee(stockHolderOnVO,stockVO));
		//ͳ��ӡ��˰
		stockHolderOnVO.setDealTax(getDealTax(stockHolderOnVO,stockVO)); 
		//������й���
		stockHolderOnVO.setStockNum((int)Util.add(stockHolderOnVO.getStockNum(), stockVO.getDealStockNum()));
		//��������Ĺ�Ʊȫ���ɱ�
		stockHolderOnVO.setCostMoney(Util.add(Util.add(Util.add(stockHolderOnVO.getCostMoney(), marketMoney),stockVO.getDealFee()),stockVO.getDealTax()));
		//���������ƽ���۸�
		stockHolderOnVO.setCostPrice(Util.divide(stockHolderOnVO.getCostMoney(),stockHolderOnVO.getStockNum() ,4));
		//�����г��۸�
		stockHolderOnVO.setRealPrice(stockVO.getDealPrice());
		//�����г���ֵ
		stockHolderOnVO.setMarketMoney(Util.multiply(stockVO.getDealPrice(), stockHolderOnVO.getStockNum()));
		//������й�Ʊ��Ϣ�е�����ʱ��Ϊ��,˵�����½��ֵĹ�Ʊ!Ҫ���õ�һ������ʱ��!
		if(stockHolderOnVO.getFitstBuyTime()==null){
			//���ý���ʱ��,��Ʊ����,��Ʊ����
			stockHolderOnVO.setFitstBuyTime(stockVO.getDealTime());
			stockHolderOnVO.setDealType(StockDao.BUY);
			stockHolderOnVO.setStockCode(stockVO.getDealStockCode());
			stockHolderOnVO.setStockName(stockVO.getDealStockName());
			//���ֵ������������ʧ�������Ѻ�ӡ��˰!!
			profitMoney = 0.0-stockHolderOnVO.getDealFee()-stockHolderOnVO.getDealTax(); 
		}
		else{
			profitMoney = Util.subtract(stockHolderOnVO.getMarketMoney(),stockHolderOnVO.getCostMoney()); 
		}
		profit = Util.divide(profitMoney, stockHolderOnVO.getCostMoney(),4);		
		stockHolderOnVO.setProfit(profit); 
		stockHolderOnVO.setProfitMoney(profitMoney);
		return stockHolderOnVO;
	}
	 
	/**
	 * ������Ʊ
	 * @param stockHolderOnVO
	 * @param stockVO
	 * @return
	 */
	public static StockHolderOnVO reduceStock(StockHolderOnVO stockHolderOnVO,StockVO stockVO){  
		double sellPrice = stockVO.getDealPrice();
		int sellNum = stockVO.getDealStockNum();
		//������ֵ
		double marketMoney =  Util.multiply(sellNum, sellPrice); 
		//ͳ��������		
		stockHolderOnVO.setDealFee(getDealFee(stockHolderOnVO,stockVO));
		//ͳ��ӡ��˰
		stockHolderOnVO.setDealTax(getDealTax(stockHolderOnVO,stockVO)); 
		//�������
		stockHolderOnVO.setStockNum((int)Util.subtract(stockHolderOnVO.getStockNum(), stockVO.getDealStockNum()));
		//������: �ֲ��г���ֵ-�ֲֵĹ�Ʊ�ɱ�
		double profitMoney;
		//������
		double profit;
		//���ʣ���Ʊ��ĿΪ0,˵���Ѿ�ȫ������ȥ��.��������ʱ��, 
		if(stockHolderOnVO.getStockNum()==0){
			stockHolderOnVO.setSellTime(stockVO.getDealTime()); 
			stockHolderOnVO.setDealType(StockDao.SELL);
			//��������,��Ʊ�ĳɱ�:   �Ѿ����еĳɱ�+������+ӡ��˰
			stockHolderOnVO.setCostMoney(Util.add(Util.add(stockHolderOnVO.getCostMoney(), stockVO.getDealFee()),stockVO.getDealTax()));
			//������й�Ʊ��ƽ���۸�: �ֲֹ�Ʊ�ĳɱ�/�����Ĺ���
			stockHolderOnVO.setCostPrice(Util.divide(stockHolderOnVO.getCostMoney(),stockVO.getDealStockNum() ,4));
			//����ֲֹ�Ʊ���г���ֵ: �г��۸�*�����Ĺ���
			stockHolderOnVO.setMarketMoney(Util.multiply(stockVO.getDealPrice(), stockVO.getDealStockNum()));
		}
		else{
			//����ֲֵĹ�Ʊ�ĳɱ�:   �Ѿ����еĳɱ�-�������г���ֵ+������+ӡ��˰
			stockHolderOnVO.setCostMoney(Util.add(Util.add(Util.subtract(stockHolderOnVO.getCostMoney(), marketMoney),stockVO.getDealFee()),stockVO.getDealTax()));
			//������й�Ʊ��ƽ���۸�: �ֲֹ�Ʊ�ĳɱ�/���й���
			stockHolderOnVO.setCostPrice(Util.divide(stockHolderOnVO.getCostMoney(),stockHolderOnVO.getStockNum() ,4));
			//����ֲֹ�Ʊ���г���ֵ: �г��۸�*���й���
			stockHolderOnVO.setMarketMoney(Util.multiply(stockVO.getDealPrice(), stockHolderOnVO.getStockNum()));
		}
		profitMoney =Util.subtract(stockHolderOnVO.getMarketMoney(),stockHolderOnVO.getCostMoney());
		profit = Util.divide(profitMoney, stockHolderOnVO.getCostMoney(),4);
		stockHolderOnVO.setSellTime(stockVO.getDealTime()); 
		stockHolderOnVO.setProfit(profit);  
		stockHolderOnVO.setProfitMoney(profitMoney);
		stockHolderOnVO.setRealPrice(stockVO.getDealPrice()); 
		return stockHolderOnVO;
	}  
	
	/**
	 * ���㽻�׵�������
	 * @param stockHolderOnVO
	 * @param stockVO
	 * @return
	 */
	private static double getDealFee(StockHolderOnVO stockHolderOnVO,StockVO stockVO){
		return Util.add(stockHolderOnVO.getDealFee(), stockVO.getDealFee());
	}
	
	/**
	 * ���㽻�׵�ӡ��˰
	 * @param stockHolderOnVO
	 * @param stockVO
	 * @return
	 */
	private static double getDealTax(StockHolderOnVO stockHolderOnVO,StockVO stockVO){
		return Util.add(stockHolderOnVO.getDealTax(), stockVO.getDealTax());
	}  
}

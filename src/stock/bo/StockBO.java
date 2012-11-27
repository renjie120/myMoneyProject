package stock.bo;

import java.util.List;

import stock.dao.StockDao;
import stock.pojo.StockConfigVO;
import stock.pojo.StockHolderOnVO;
import stock.pojo.StockVO;

/**
 * ��һ��dwr��bo�࣡
 * @author renjie120 419723443@qq.com
 *
 */
public class StockBO { 

	StockDao stockDao = new StockDao(); 
	/**
	 * �����Ʊ��Ϣ.
	 * @param mDetail
	 * @return
	 */
	public String saveStock(StockVO stockVO) {
		stockDao.doCreate(stockVO);
		//��ѯ�Ƿ��Ѿ�����ĳ��Ʊ��Ϣ
		List stockHoldOnList = stockDao.doQueryExistedHoldon(stockVO.getDealStockCode());
		StockHolderOnVO stockHolderOnVO = null;
		//����õ���Ʊ�Ľ��������ѵ���ϢVO,�����а󶨵���Ʊʵ��VO��
		StockConfigVO config = stockDao.getStockConfigDetail(stockVO.getConfigType());
		StockUtil.bindStockConfig(stockVO, config);
		//������б��浽��Ʊ������Ϣ�Ĳ���
		int holdOnId;
		if(stockHoldOnList.size()>0){
			holdOnId = Integer.parseInt(stockHoldOnList.get(0).toString());
			stockHolderOnVO = stockDao.getStockHoldOnDetail(holdOnId);
			//����������Ʊ�ͼ�����ӹ�Ʊ������Ϣ.
			if(StockDao.BUY.equals(stockVO.getDealType())){
				stockHolderOnVO = StockUtil.addStock(stockHolderOnVO, stockVO);  
			}
			//�����������Ʊ��Ϣ
			else{
				stockHolderOnVO = StockUtil.reduceStock(stockHolderOnVO, stockVO); 
			}
			stockDao.doUpdateStockHoldOn(holdOnId, stockHolderOnVO);
		}
		//û���ҵ���˵���ǽ��֡�
		else{
			stockHolderOnVO = new StockHolderOnVO(); 
			stockHolderOnVO = StockUtil.addStock(stockHolderOnVO, stockVO);  
			stockDao.doCreate(stockHolderOnVO);
		}
		return "����ɹ�!";
	} 
	
	/**
	 * ������֧��Ϣ
	 * @param mDetail
	 * @return
	 */
	public String updateStock(StockVO stockVO) {
		stockDao.doUpdate(stockVO.getDealSno(),stockVO);
		return "�޸ĳɹ�!";
	}	
}

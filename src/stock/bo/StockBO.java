package stock.bo;

import java.util.List;

import stock.dao.StockDao;
import stock.pojo.StockConfigVO;
import stock.pojo.StockHolderOnVO;
import stock.pojo.StockVO;

/**
 * 是一个dwr的bo类！
 * @author renjie120 419723443@qq.com
 *
 */
public class StockBO { 

	StockDao stockDao = new StockDao(); 
	/**
	 * 保存股票信息.
	 * @param mDetail
	 * @return
	 */
	public String saveStock(StockVO stockVO) {
		stockDao.doCreate(stockVO);
		//查询是否已经持有某股票信息
		List stockHoldOnList = stockDao.doQueryExistedHoldon(stockVO.getDealStockCode());
		StockHolderOnVO stockHolderOnVO = null;
		//下面得到股票的交易手续费等信息VO,并进行绑定到股票实体VO中
		StockConfigVO config = stockDao.getStockConfigDetail(stockVO.getConfigType());
		StockUtil.bindStockConfig(stockVO, config);
		//下面进行保存到股票持有信息的操作
		int holdOnId;
		if(stockHoldOnList.size()>0){
			holdOnId = Integer.parseInt(stockHoldOnList.get(0).toString());
			stockHolderOnVO = stockDao.getStockHoldOnDetail(holdOnId);
			//如果是买入股票就继续添加股票买卖信息.
			if(StockDao.BUY.equals(stockVO.getDealType())){
				stockHolderOnVO = StockUtil.addStock(stockHolderOnVO, stockVO);  
			}
			//否则就卖出股票信息
			else{
				stockHolderOnVO = StockUtil.reduceStock(stockHolderOnVO, stockVO); 
			}
			stockDao.doUpdateStockHoldOn(holdOnId, stockHolderOnVO);
		}
		//没有找到，说明是建仓。
		else{
			stockHolderOnVO = new StockHolderOnVO(); 
			stockHolderOnVO = StockUtil.addStock(stockHolderOnVO, stockVO);  
			stockDao.doCreate(stockHolderOnVO);
		}
		return "保存成功!";
	} 
	
	/**
	 * 更新收支信息
	 * @param mDetail
	 * @return
	 */
	public String updateStock(StockVO stockVO) {
		stockDao.doUpdate(stockVO.getDealSno(),stockVO);
		return "修改成功!";
	}	
}

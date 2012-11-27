package stock.dao;

import java.util.List;

import stock.pojo.StockVO;

public interface IStockDao {
	public void doCreate(StockVO stock);
	public StockVO doGet(int stockSno);
	public StockVO doUpdate(int stockSno,StockVO stockVO);
	public void doDeleteStock(int stockSno);
	public List doGetAllStocks(String queryStr);
}

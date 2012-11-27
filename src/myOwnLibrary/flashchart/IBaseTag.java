package myOwnLibrary.flashchart;

/** 
* @ClassName: IBaseTag 
* @Description: 定义了必须要在flash便签中实现的方法
* @author 419723443@qq.com
* @date Jan 20, 2010 9:06:14 PM 
*  
*/
public interface IBaseTag {
	/**
	 * 开始的组串
	 * @return
	 */
	public FlashChartBase start();
	/**
	 * 结束的组串
	 * @return
	 */
	public FlashChartBase end();
}

package tallyBook.action;

import tallyBook.dao.CardDao;
import tallyBook.pojo.ShopCard;

public class CardAction {
	CardDao cardDao = new CardDao();
	private String shopCard;
	private String cardDesc;
	private Integer cardDeadLine;
	private String cardNo;
	private String cardUrl;
	private String message;
	private String cardSno;
	public String getCardSno() {
		return cardSno;
	}
	public void setCardSno(String cardSno) {
		this.cardSno = cardSno;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCardDesc() {
		return cardDesc;
	}
	public void setCardDesc(String cardDesc) {
		this.cardDesc = cardDesc;
	}
	public Integer getCardDeadLine() {
		return cardDeadLine;
	}
	public void setCardDeadLine(Integer cardDeadLine) {
		this.cardDeadLine = cardDeadLine;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardUrl() {
		return cardUrl;
	}
	public void setCardUrl(String cardUrl) {
		this.cardUrl = cardUrl;
	}
	/**
	 * 更新卡片信息.
	 * @return
	 */
	public String beforeUpdateCard(){
		shopCard = getShopCard();
		ShopCard shopCardVo = cardDao.doGetCardInfo(Integer.parseInt(shopCard));
		setCardDesc(shopCardVo.getCardDesc());
		setCardSno(shopCard);
		setCardDeadLine(shopCardVo.getCardDeadLine());
		setCardNo(shopCardVo.getCardNo());
		setCardUrl(shopCardVo.getCardUrl());		
		setMessage("修改卡片信息");
		return "info";
	}
	public String getShopCard() {
		return shopCard;
	}
	public void setShopCard(String shopCard) {
		this.shopCard = shopCard;
	}
	
}

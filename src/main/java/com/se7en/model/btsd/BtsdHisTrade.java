package com.se7en.model.btsd;

import com.se7en.model.btsd.BtsdCoin;

/**
 * BtsdHisTrade entity
 */
public class BtsdHisTrade implements java.io.Serializable {

public final static BtsdHisTrade EMPTY_BTSDHISTRADE = new BtsdHisTrade();

	/** default constructor */
	public BtsdHisTrade() {
	}

	// Fields

	private Integer id;

	private BtsdCoin coin;

	private String date;

	private Double price;

	private Double volume;

	private Integer type;

	// Property accessors
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BtsdCoin getCoin() {
		return coin;
	}

	public void setCoin(BtsdCoin coin) {
		this.coin = coin;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "BtsdHisTrade{" +
				"id=" + id +
				", coin=" + coin +
				", date='" + date + '\'' +
				", price=" + price +
				", volume=" + volume +
				", type=" + type +
				'}';
	}
}
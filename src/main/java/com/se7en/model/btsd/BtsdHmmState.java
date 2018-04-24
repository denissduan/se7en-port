package com.se7en.model.btsd;


/**
 * BtsdHmmState entity
 */
public class BtsdHmmState implements java.io.Serializable {

public final static BtsdHmmState EMPTY_BTSDHMMSTATE = new BtsdHmmState();

	/** default constructor */
	public BtsdHmmState() {
	}

	// Fields

	private Integer id;

	private Integer type;

	private BtsdCoin coin;

	private String name;

	private Integer no;

	private Double minBound;

	private Double maxBound;

	// Property accessors
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public BtsdCoin getCoin() {
		return coin;
	}

	public void setCoin(BtsdCoin coin) {
		this.coin = coin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public Double getMinBound() {
		return minBound;
	}

	public void setMinBound(Double minBound) {
		this.minBound = minBound;
	}

	public Double getMaxBound() {
		return maxBound;
	}

	public void setMaxBound(Double maxBound) {
		this.maxBound = maxBound;
	}

}
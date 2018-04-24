package com.se7en.model.btsd;
/**
 * BtsdCoin entity
 */
public class BtsdCoin implements java.io.Serializable {
	public final static BtsdCoin EMPTY_BTSDCOIN = new BtsdCoin();
	// Fields
	private String id;			//虚拟币代码
	private String name;			//虚拟币名称
	private String mk;			//市场
	// Property accessors

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMk() {
		return mk;
	}
	public void setMk(String mk) {
		this.mk = mk;
	}
	
	/** default constructor */
	public BtsdCoin() {
	}
}
package com.se7en.model.btsd;

import com.se7en.model.btsd.BtsdCoin;

/**
 * BtsdHourLine entity
 */
public class BtsdHourLine implements java.io.Serializable {

public final static BtsdHourLine EMPTY_BTSDHOURLINE = new BtsdHourLine();

	/** default constructor */
	public BtsdHourLine() {
	}

	// Fields

	private Integer id;

	private BtsdCoin coin;

	private Long date;

	private String datefmt;

	private Double cjl;

	private Double kp;

	private Double zg;

	private Double zd;

	private Double sp;

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

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public String getDatefmt() {
		return datefmt;
	}

	public void setDatefmt(String datefmt) {
		this.datefmt = datefmt;
	}

	public Double getCjl() {
		return cjl;
	}

	public void setCjl(Double cjl) {
		this.cjl = cjl;
	}

	public Double getKp() {
		return kp;
	}

	public void setKp(Double kp) {
		this.kp = kp;
	}

	public Double getZg() {
		return zg;
	}

	public void setZg(Double zg) {
		this.zg = zg;
	}

	public Double getZd() {
		return zd;
	}

	public void setZd(Double zd) {
		this.zd = zd;
	}

	public Double getSp() {
		return sp;
	}

	public void setSp(Double sp) {
		this.sp = sp;
	}

	@Override
	public String toString() {
		return "BtsdHourLine{" +
				"id=" + id +
				", coin=" + coin +
				", date=" + date +
				", datefmt='" + datefmt + '\'' +
				", cjl=" + cjl +
				", kp=" + kp +
				", zg=" + zg +
				", zd=" + zd +
				", sp=" + sp +
				'}';
	}
}
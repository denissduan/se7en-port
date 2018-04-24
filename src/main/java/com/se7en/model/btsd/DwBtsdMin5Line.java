package com.se7en.model.btsd;

import com.se7en.model.btsd.BtsdCoin;

/**
 * DwBtsdMin5Line entity
 */
public class DwBtsdMin5Line implements java.io.Serializable {

public final static DwBtsdMin5Line EMPTY_DWBTSDMIN5LINE = new DwBtsdMin5Line();

	/** default constructor */
	public DwBtsdMin5Line() {
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

	private Double zgscale;

	private Double zdscale;

	private Double spscale;

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

	public Double getZgscale() {
		return zgscale;
	}

	public void setZgscale(Double zgscale) {
		this.zgscale = zgscale;
	}

	public Double getZdscale() {
		return zdscale;
	}

	public void setZdscale(Double zdscale) {
		this.zdscale = zdscale;
	}

	public Double getSpscale() {
		return spscale;
	}

	public void setSpscale(Double spscale) {
		this.spscale = spscale;
	}

}
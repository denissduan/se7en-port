package com.se7en.model.btsd;

import java.util.Set;
import java.util.LinkedHashSet;

/**
 * HmmBtsd entity
 */
public class HmmBtsd implements java.io.Serializable {

public final static HmmBtsd EMPTY_HMMBTSD = new HmmBtsd();

	/** default constructor */
	public HmmBtsd() {
	}

	// Fields

	private Integer id;

	private Integer type;

	private BtsdCoin coin;

	private Long startDate;

	private Long endDate;

	private String startDatefmt;

	private String endDatefmt;

	private Integer stateSize;

	private String transProbability;

	private Set<HmmBtsdDetail> details = new LinkedHashSet<HmmBtsdDetail>();

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

	public Long getStartDate() {
		return startDate;
	}

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

	public Long getEndDate() {
		return endDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}

	public String getStartDatefmt() {
		return startDatefmt;
	}

	public void setStartDatefmt(String startDatefmt) {
		this.startDatefmt = startDatefmt;
	}

	public String getEndDatefmt() {
		return endDatefmt;
	}

	public void setEndDatefmt(String endDatefmt) {
		this.endDatefmt = endDatefmt;
	}

	public Integer getStateSize() {
		return stateSize;
	}

	public void setStateSize(Integer stateSize) {
		this.stateSize = stateSize;
	}

	public String getTransProbability() {
		return transProbability;
	}

	public void setTransProbability(String transProbability) {
		this.transProbability = transProbability;
	}

	public Set<HmmBtsdDetail> getDetails() {
		return details;
	}

	public void setDetails(Set<HmmBtsdDetail> details) {
		this.details = details;
	}
}
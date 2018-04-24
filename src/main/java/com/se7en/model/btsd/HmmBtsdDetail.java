package com.se7en.model.btsd;

import com.se7en.model.btsd.HmmBtsd;

/**
 * HmmBtsdDetail entity
 */
public class HmmBtsdDetail implements java.io.Serializable {

public final static HmmBtsdDetail EMPTY_HMMBTSDDETAIL = new HmmBtsdDetail();

	/** default constructor */
	public HmmBtsdDetail() {
	}

	// Fields

	private Integer id;

	private HmmBtsd hmm;

	private String name;

	private Integer stateIndex;

	private Double minBound;

	private Double maxBound;

	private String pi;

	private String mean;

	private String covariance;

	// Property accessors
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public HmmBtsd getHmm() {
		return hmm;
	}

	public void setHmm(HmmBtsd hmm) {
		this.hmm = hmm;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStateIndex() {
		return stateIndex;
	}

	public void setStateIndex(Integer stateIndex) {
		this.stateIndex = stateIndex;
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

	public String getPi() {
		return pi;
	}

	public void setPi(String pi) {
		this.pi = pi;
	}

	public String getMean() {
		return mean;
	}

	public void setMean(String mean) {
		this.mean = mean;
	}

	public String getCovariance() {
		return covariance;
	}

	public void setCovariance(String covariance) {
		this.covariance = covariance;
	}

}
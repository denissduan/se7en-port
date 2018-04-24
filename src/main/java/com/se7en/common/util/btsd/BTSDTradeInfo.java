package com.se7en.common.util.btsd;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/9/20.
 */
public class BTSDTradeInfo {

    private int id;

    private String code;

    private String name;

    private BigDecimal price;

    private BigDecimal seilPrice;

    private BigDecimal amount;

    private String time;

    private int state;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSeilPrice() {
        return seilPrice;
    }

    public void setSeilPrice(BigDecimal seilPrice) {
        this.seilPrice = seilPrice;
    }

    @Override
    public String toString() {
        return "BTSDTradeInfo{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", seilPrice=" + seilPrice +
                ", amount=" + amount +
                ", time='" + time + '\'' +
                ", state=" + state +
                '}';
    }
}

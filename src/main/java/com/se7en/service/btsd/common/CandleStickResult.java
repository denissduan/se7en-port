package com.se7en.service.btsd.common;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by admin on 2017/7/13.
 */
public class CandleStickResult {

    public final static CandleStickResult EMPTY_ENTITY = new CandleStickResult();

    private List<AxisData> xAxis = new LinkedList<>();

    private List<CandleStickData> data = new LinkedList<>();

    public class AxisData{

        private String label;

        private String x;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }
    }

    public class CandleStickData{

        private String open;

        private String high;

        private String low;

        private String close;

        private String x;

        private String volume;

        private String date;

        private String color;

        private String tooltext;

        private String point;

        public String getOpen() {
            return open;
        }

        public void setOpen(String open) {
            this.open = open;
        }

        public String getHigh() {
            return high;
        }

        public void setHigh(String high) {
            this.high = high;
        }

        public String getLow() {
            return low;
        }

        public void setLow(String low) {
            this.low = low;
        }

        public String getClose() {
            return close;
        }

        public void setClose(String close) {
            this.close = close;
        }

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getTooltext() {
            return tooltext;
        }

        public void setTooltext(String tooltext) {
            this.tooltext = tooltext;
        }

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }
    }

    public List<AxisData> getxAxis() {
        return xAxis;
    }

    public void setxAxis(List<AxisData> xAxis) {
        this.xAxis = xAxis;
    }

    public List<CandleStickData> getData() {
        return data;
    }

    public void setData(List<CandleStickData> data) {
        this.data = data;
    }
}

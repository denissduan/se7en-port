package com.se7en.service.btsd.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/6/30.
 */
public class HmmDimension {

    private static final List<DimensionEntry> entrys = new ArrayList<>();

    static {
        DimensionEntry zg = new DimensionEntry("zg", 0);
        DimensionEntry zd = new DimensionEntry("zd", 1);
        DimensionEntry sp = new DimensionEntry("sp", 2);

        entrys.add(zg);
        entrys.add(zd);
        entrys.add(sp);
    }

    public static List<DimensionEntry> getDimensions(){
        return entrys;
    }

    public static class DimensionEntry{

        private String name;

        private int index;

        public DimensionEntry(String name,int index){
            this.name = name;
            this.index = index;

        }

        public int getIndex() {
            return index;
        }

    }

}

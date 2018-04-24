package com.se7en.service.btsd.common;

import com.se7en.common.util.ContextUtil;
import com.se7en.model.btsd.BtsdHmmState;
import com.se7en.service.btsd.BtsdHmmStateService;
import java.util.List;

/**
 * Created by admin on 2017/7/11.
 */
public class HmmStateWrapper {

    private List<BtsdHmmState> states;

    public static HmmStateWrapper wrap(int type,String code){
        BtsdHmmStateService btsdHmmStateService = ContextUtil.getBean(BtsdHmmStateService.class);
        List<BtsdHmmState> list = btsdHmmStateService.queryByTypeAndCode(type, code);
        if(list.size() == 0){           //如果配置为空查询标配
            list = btsdHmmStateService.queryByTypeAndCode(type, "");
        }
        HmmStateWrapper hmmStateWrapper = new HmmStateWrapper();
        hmmStateWrapper.states = list;

        return hmmStateWrapper;
    }

    public List<BtsdHmmState> getStates() {
        return states;
    }

    /**
     * 根据比例判断
     * @param scale
     * @return
     */
    public BtsdHmmState valueOf(double scale){
        for(BtsdHmmState state : states){
            if(scale >= state.getMinBound() && scale <= state.getMaxBound()){
                return state;
            }
        }
        return null;
    }

    /**
     * 根据状态判断
     * @param index
     * @return
     */
    public BtsdHmmState valueOf(int index){
        for(BtsdHmmState state : states){
            if(state.getNo() == index){
                return state;
            }
        }
        return null;
    }

}

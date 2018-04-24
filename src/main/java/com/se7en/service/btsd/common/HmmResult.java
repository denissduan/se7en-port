package com.se7en.service.btsd.common;

import org.encog.ml.hmm.HiddenMarkovModel;

import java.util.HashMap;

/**
 * Created by admin on 2017/7/5.
 */
public class HmmResult {

    public HmmResult(){
        this.success = false;
    }

    private boolean success;

    private HiddenMarkovModel hmm;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public HiddenMarkovModel getHmm() {
        return hmm;
    }

    public void setHmm(HiddenMarkovModel hmm) {
        this.hmm = hmm;
    }
}

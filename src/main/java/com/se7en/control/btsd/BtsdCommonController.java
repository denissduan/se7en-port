package com.se7en.control.btsd;

import com.se7en.control.AbstractControl;
import com.se7en.service.btsd.common.BtsdCommonService;
import com.se7en.service.btsd.common.CandleStickResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by admin on 2017/7/13.
 */
@Controller
@RequestMapping("/btsd")
public class BtsdCommonController extends AbstractControl {

    @Resource
    private BtsdCommonService btsdCommonService;

    @RequestMapping("/candleStick.do")
    public ModelAndView chart(HttpServletRequest req, HttpServletResponse res) throws Exception {
        return new ModelAndView("btsd/common/candleStick");
    }

    @RequestMapping("/getCandleStickData.do")
    public @ResponseBody CandleStickResult getCandleStickData(int type,String coin,String startDate,String endDate) {
        CandleStickResult result = btsdCommonService.getCandleStickData(type, coin, startDate, endDate);

        return result;
    }

}

package com.common.tips;

/**
 * 返回成功提示
 */
public class SuccessTip extends Tip{

    public  SuccessTip(){
        super.code = 200;
        super.message = "操作成功";
    }
}

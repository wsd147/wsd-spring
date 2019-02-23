package com.common.tips;

/**
 * 返回失败提示
 */
public class ErrorTip extends Tip{

    public ErrorTip(Integer code,String message){
        super.code = code;
        super.message = message;
    }

}

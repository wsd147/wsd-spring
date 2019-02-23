package com.common.tips;

import lombok.Getter;
import lombok.Setter;

/**
 * 返回前台提示
 */
@Getter
@Setter
public abstract class Tip {

    protected   Integer code;

    protected   String message;
}

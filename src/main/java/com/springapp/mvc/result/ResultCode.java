package com.springapp.mvc.result;

/**
 * Created by pcdalao on 2017/3/20.
 */
public enum  ResultCode {
    // Use standard HTTP status code
    OK("200", "OK"),
    BAD_REQUEST("400", "Bad Request"),
    UNAUTHORIZED("401", "Unauthorized"),
    FORBIDDEN("403", "Forbidden"),
    NOT_FOUND("404", "Not Found"),
    CONFLICT("409", "Conflict"),
    NULL_PARAMETER("408", "null parameter"),
    INTERNAL_SERVER_ERROR("500", " Internal Server Error"),

    //------------------business code
    //user
    USER_NOT_FOUND("1101", "对应账户不存在"),
    USER_REG_CAPTCHA_ERROR("1102", "图片验证码错误"),
    USER_CELLPHONE_DUPLICATED("1103", "手机号重复"),
    USER_CELLPHONE_BINDED("1104", "该手机号已绑定其它账号"),
    USER_PASSWORD_ERROR("1105", "密码错误"),
    AUTH_FAILED("1106", "登陆授权失败，手机号或密码错误。"),
    SESSION_IS_NULL("1107", "当前用户未登录。"),
    NOT_RELATED_TO_EMPLOYEES("1108","当前登录用户未关联到员工"),
    USER_TYPE_MISMATCH("1109","用户类型不匹配"),

    PHONE_CODE_ERROR("1201", "手机验证码错误"),
    PHONE_SMS_REJECTED("1202", "发送手机短信失败"),

    ADMIN_USER_CELLPHONE_ERROR_PLATFORM_SOURCE("1301", "错误的平台来源"),
    ADMIN_USER_CELLPHONE_ERROR_OLDCELLPHONE("1302", "旧手机号非用户对应手机号"),

    /****************基础数据加载*****************/
    //分站
    NOT_EXIST_COUNTRY("5001","国家不存在"),
    NOT_EXIST_AREA("5002","无关联的业务大区"),
    //国家
    UNIVERSAL_CODE_IS_NULL("5021","通用语言编码为空"),
    NOT_ONLY_BY_UNIVERSALCODE("5022","通过通用语言编码查询结果不唯一"),
    NATIVE_CODE_IS_NULL("5023","本地语言编码为空"),
    NOT_ONLY_BY_NATIVECODE("5024","通过本地语言编码查询结果不唯一"),
    //basic-language
    LANG_CODE_IS_NULL("5041","语言编码为空"),
    NOT_FOUND_BY_LANG_CODE("5042","未查询到记录"),
    INVALID_LANG_CODE("5043", "无效的语言编码"),
    LANG_ID_IS_NULL("5044","语言ID为空"),
    //任务
    TASK_DOES_NOT_EXIST("6001","任务不存在"),
    TASK_CAN_NOT_SIGN_ON("6002","任务不可签到"),
    TASK_CAN_NOT_SIGN_OFF("6003","任务不可签退"),
    TASK_ASSIGNMENT_CAN_NOT_UPDATE("6004","任务已分配，不可修改"),

    //商品
    INVALID_SUBSTATION_ID("7003", "商品绑定了无效的分站id"),
    INVALID_CATEGORY_ID("7004","无商品分类信息"),
    INVALID_GOOD_LANG_INFO("7005","无商品多语言信息"),
    GOODS_ID_IS_NULL("7014", "商品id为空"),
    //商品多语言
    INVALID_LANG_ID("7006", "无效的语言id"),
    //商品sku
    INVALID_SKUPRICE("7007", "商品sku无价格异常"),
    INVALID_GOODSSKU_PRICE("7008", "根据商品skuId查询出的价格记录为空，或者大于一条"),
    INVALID_GOODSID("7009", "无效的商品id"),
    INVALID_GOODS_SKU_ID("7013", "无效的商品SKUID"),
    GOODSSKUID_IS_NULL("7012", "商品SKUid为空"),
    //商品价格
    INVALID_GOODSSKUID("7010", "无效的商品skuId"),
    INVALID_KEY("7011", "无效的主键"),

    //产品
    INVALID_FIGURE_ID("8001","无效的轮播图id"),
    INVALID_SORT_STATUS("8002","无效的up/down操作"),
    INVALID_BRAND_OR_CATEGORY_OR_PRODUCTID("8003", "无效的品牌或者分类或者产品id"),

    //实体操作
    //采购
    ITEM_ID_IS_NULL("9010","元素 ID 为空！"),
    ITEM_NOT_FOUND("9020","未找到操作元素！"),
    ITEM_STATUS_ERROR("9030","未找到对应状态！"),

    //广告
    ITEM_AD_CODE_IS_NULL("1010","广告组 code 不能为空！"),
    ITEM_AD_CODE_IS_EXIST("1020","广告组 code 已存在！"),
    ITEM_AD_IS_NOT_FOUND("1030","没有找到指定广告组信息！"),

    /***************client*****************/
    //收货地址
    SET_ADDRESS_EMOJI_ERROR("10004", "收货地址信息不支持emoji表情"),

    //优惠券
    NOT_FOUND_COUPON("11001","没找到该优惠券"),
    GRANT_TYPE_ERROR("11002","发放类型错误"),
    COUPON_CODE_ERROR("11004","优惠券代码错误"),
    CHANGE_COUPON_CODE_LIMIT("11005","兑换优惠券,当天错误次数达到上限，请24小时后在操作"),

    //销售订单
    SELL_ORDER_STATUS_UPDATE_ERROR("12001", "订单状态已改变。建议重新加载后重试。"),
    APLIT_QUANTITY_CANNOT_BE_GREATER("12002", "拆分数量不合规！"),

    //楼层
    ITEM_FL_IS_NOT_FOUND("1210","没有找到指定楼层信息！"),
    ITEM_RG_IS_NOT_FOUND("1220","没有找到指定推荐的商品信息！"),

    ;


    private final String code;
    private final String message;

    private ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static ResultCode codeOf(String code){
        for(ResultCode value : values()){
            if(value.code == code)
                return value;
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}

package com.system.technologyinformation.common.net;

public interface Constant {
    String COOKIE = "cookie";

    int pushMessageDbVersion = 2;
    String pushMessageDbName = "pushMessage_db";
    String pushMessageDbTable = "pushMessage_table";
    String pushMessageDbTableColumn1 = "id";
    String pushMessageDbTableColumn2 = "pmcontent";
    String pushMessageDbTableColumn3 = "pmident";
    String pushMessageDbTableColumn4 = "pmcreatetime";
    /**
     * 登录
     */
    String USER_LOGIN= "/v2/user/login";

    /**
     * 推送token保存
     */
    String USER_ADD_PUSH_TOKEN= "/v2/user/savePushToken";

    /**
     * 注册
     */
    String USER_REGISTER= "/user/register";


    /**
     * 短信
     */
    String USER_SMS_SEND= "/sms/send";

    /**
     * 验证手机号
     */
    String USER_REGISTER_CHECK_PHONE= "/user/checkPhone";

    /**
     * 修改密码
     */
    String USER_FORGOT_PASSWORD= "/user/reset/password";

    /**
     * 更新用户信息
     */
    String UPDATE_USER_INFO = "/user/get";

    /**
     * 用户过期刷新
     */
    String REFRESH_USER = "/user/refresh";

    /**
     * 退出登录
     */
    String USER_LOGOUT = "/user/logout";

    /**
     * 首页服务功能
     */
    String HOME_FUNCTION_DIVISION= "/v2/home/getService";

    /**
     * 首页头部配置
     */
    String HOME_FUNCTION_TOP= "/v2/backgroud/get";

    /**
     * 闪屏信息
     */
    String FICKERSCREENINFO= "/v2/splash/get";

    /**
     * 首页消息功能
     */
    String HOME_FUNCTION_MESSAGE= "/v2/home/getMsg";

    /**
     * 首页Banner
     */
    String HOME_BANNER= "/banner/search";

    /**
     * 资讯列表
     */
    String HOME_DATA= "/consultation/search";

    /**
     * 首页列表详情
     */
    String HOME_DATA_DETAILED= "/consultation/get";


    /**
     * 首页商品列表
     */
    String HOME_DATA_SHOPPING= "/v2/market/getHomeProduct";

    /**
     * 商品列表
     */
    String DATA_SHOPPING= "/v2/product/mall/search";

    /**
     * 兑换商品列表
     */
    String CREDITS_EXCHANGE_DATA_SHOPPING= "/v2/product/exchange/search";

    /**
     * 商品XQ
     */
    String CREDITS_EXCHANGE_DATA_SHOPPING_DETAILED= "/v2/product/get";

    /**
     * 消费列表
     */
    String USER_CONSUMEINFO_LIST= "/v2/user/myPayOrder";

    /**
     * 积分列表
     */
    String USER_POINTSINFO_LIST= "/pointsInfo/search";

    /**
     * 更新用户信息
     */
    String USER_UPDATE_INFO= "/v2/user/modify";

    /**
     * 抢购兑换下单
     */
    String USER_MARKET_CREATEORDER= "/v2/market/createOrder";

    /**
     * 地址信息添加
     */
    String USER_UPDATE_ADDRESS_OR_SALARY_ADD= "/v2/user/modifyCP";

    /**
     * 职业类型
     */
    String USER_PROFESSIONAL_TYPE= "/careerKind/search";


    /**
     * 职业认证列表
     */
    String USER_PROFESSIONAL_LIST= "/v2/userCareer/getAllCareer";

    /**
     * 工种add
     */
    String USER_PROFESSIONAL_ADD= "/v2/userCareer/save";

    /**
     * 实名add
     */
    String USER_NAME_ADD= "/identificationInfo/add";

    /**
     * 实名信息
     */
    String USER_NAME_INFO= "/identificationInfo/get";

    /**
     * 职业修改
     */
    String USER_PROFESSIONAL_UPDATE= "/identificationInfo/modify";

    /**
     * 文件上传
     */
    String FILE_UPLOAD= "/upload/resource";

    /**
     * 签到
     */
    String USER_SIGN_ADD= "/v2/dailyTask/checkIn";

    /**
     * 补签
     */
    String USER_SIGN_ADD_REPAIR= "/v2/dailyTask/supplement";

    /**
     * 任务列表
     */
    String DAILYMISSIONLIST= "/v2/task/getList";

    /**
     * 签到信息每日
     */
    String USER_SIGN_DAY_INFO= "/dailyTask/search";

    /**
     * 签到信息每月
     */
    String USER_SIGN_MONTH_INFO= "/v2/dailyTask/checkInSituation";

    /**
     * 投票
     */
    String CONSULTATION_ADD_VOTE= "/vote/choice";

    /**
     * 反馈
     */
    String CONSULTATION_ADD_FEEDBACK= "/consultationFeedback/add";

    /**
     * 城市信息
     */
    String PROVINCEORCITY= "/province/search";

    /**
     * 分享
     */
    String CONSULTATION_ADD_FORWARD= "/consultation/forward";

    /**
     * 更新反馈
     */
    String CONSULTATION_UPDATE_FEEDBACK= "/consultationFeedback/search";

    /**
     * 支付商铺信息
     */
    String STORE_INFO= "/shop/get";

    /**
     * 获取预定单
     */
    String STORE_ORDER= "/order/add";

    /**
     * 消息列表
     */
    String USER_MESSAGE_LIST= "/message/search";

    /**
     * 版本更新
     */
    String FINDVERSION="/version/check";

    /**
     * 微信支付UserRechargeWx
     */
    String USERRECHARGEWX="/v2/shopOrder/wxpay/create";

    /**
     * 支付宝支付
     */
    String USERRECHARGEZFB="/v2/shopOrder/alipay/create";

    /**
     *免单
     */
    String USERFREEOFCHARGERESULT="/v2/shopOrder/toAccount";

    /**
     * 现金支付
     */
    String USERRECHARGEXJ="/order/pay/inCash";

    /**
     * 取消订单扫码支付
     */
    String USERORDERCANCEL="/v2/shopOrder/cancel";

    /**
     * 打开红包
     */
    String USER_OPEN_REWAR="/reward/unwarp";

    /**
     * 工地列表
     */
    String CONSTRUCTIONPLACE="/construction/place/search";



    /**
     * 订餐列表
     */
    String FOODSEARCH="/v2/product/food/search";

    /**
     * 线上超市商品
     */
    String ONLINESUPERMARKETGOODS="/product/search";

    /**
     * 线上超市商品分类
     */
    String ONLINESUPERMARKETGOODSTYPE="/category/search";

    /**
     * 线上超市商品分类
     */
    String ONLINESUPERMARKETGOODSANDTYPE="/product/category/products";

    /**
     * 订单生成
     */
    String ONLINESUPERMARKETGOODSORDER="/productOrder/addOrder";

    /**
     * 订单生成
     */
    String USERADDDELIVERY="/delivery/modify";

    /**
     * 订单WX支付
     */
    String USERCONFIRMANORDERPAYWX="order/wxpay/create";

    /**
     * 订单支付宝支付
     */
    String USERCONFIRMANORDERPAYZFB="order/alipay/create";

    /**
     * 订单
     */
    String USERORDER="/v2/market/getOrderSearch";

    /**
     * 活动信息
     */
    String ENROLLMENTINFO="/race/getRace";

    /**
     * 是否报过名
     */
    String WHETHERTHEREGISTRATION="/contestants/auth";

    /**
     * 报名
     */
    String WHETHERTHEREGISTRATIONISSUCCESSFUL="/contestants/add";

    /**
     * 获取直播信息
     */
    String ALIYUNPLAYERSKININFO="/live/getPull";

    /**
     * 点播列表
     */
    String ONDEMANDLISTINFO="/live/getVodPage";

    /**
     * 点播列表XQ
     */
    String ONDEMANDLISTDETAILESINFO="/live/getPlayUrl";

    /**
     * 查询工单
     */
    String ADDQUESTION="/feedback/submit";

    /**
     * Group user 信息
     */
    String GROUPUSERINFO="/chatGroup/getGroupUsers";

    /**
     * Group列表
     */
    String GROUPINFO="/chatGroup/getUserAllChatGroups";

    /**
     * 分享内容
     */
    String SHARE_CONTENT="/v2/task/getShare";

    /**
     * 分享反馈
     */
    String SHARE_ADD="/v2/task/completeShare";

    /**
     * 充值价格表
     */
    String PHONE_BILL_LIST="/v2/phoneBill/getPrice";

    /**
     * 充值价格表
     */
    String PHONE_BILL_ADD="/v2/phoneBill/onlineOrder";

    /**
     * 充值WX
     */
    String PHONE_BILL_WX="/v2/phoneBill/wxpay/create";

    /**
     * 充值价ZFB
     */
    String PHONE_BILL_ZFB="/v2/phoneBill/alipay/create";

    /**
     * 添加配送地址
     */
    String DELIVERY_ADDRESS_ADD="/v2/receivingAddress/add";

    /**
     * 获取配送地址
     */
    String DELIVERY_ADDRESS_LIST="/v2/receivingAddress/getByUser";

    /**
     * 修改配送地址
     */
    String DELIVERY_ADDRESS_UPDATE="/v2/receivingAddress/modify";

    /**
     * 修改配送地址
     */
    String USER_ORDER_REFUNDS="/v2/market/returnGoods";

    /**
     * //扫码获取商户信息
     */
    String SHOP_INFO="/shop/get2";

    /**
     * 获取支付方式
     */
    String PAYMETHOD="/v2/home/getPayWay";

    /**
     * 取消订单商城
     */
    String USERMARKETORDERCANCEL="/v2/market/cancelOrder";

    /**
     * 获取广告信息
     */
    String ADVERTISEINFO = "/v2/popupShow/get";

    /**
     *
     */
    String RECHARGESHISTORY = "/v2/phoneBill/getOrderSearch";
}

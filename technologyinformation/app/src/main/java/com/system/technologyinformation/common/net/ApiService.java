package com.system.technologyinformation.common.net;

import com.system.technologyinformation.model.AdvertiseInfoBean;
import com.system.technologyinformation.model.AliyunPlayerSkinBean;
import com.system.technologyinformation.model.ConfirmOrderBean;
import com.system.technologyinformation.model.ConstructionPlaceBean;
import com.system.technologyinformation.model.ConvenientFunctionsBean;
import com.system.technologyinformation.model.CreditsExchangePayBean;
import com.system.technologyinformation.model.DailyMissionBean;
import com.system.technologyinformation.model.DeliveryAddressBean;
import com.system.technologyinformation.model.DeliveryBean;
import com.system.technologyinformation.model.EnrollmentBean;
import com.system.technologyinformation.model.FeedbacksBean;
import com.system.technologyinformation.model.FlickerScreenBean;
import com.system.technologyinformation.model.GroupInfoBean;
import com.system.technologyinformation.model.GroupUserInfoBean;
import com.system.technologyinformation.model.HomeBannerBean;
import com.system.technologyinformation.model.HomeConfigurationInformationBean;
import com.system.technologyinformation.model.HomeDataBean;
import com.system.technologyinformation.model.HomeDetailedBean;
import com.system.technologyinformation.model.HomeShoppingSpreeBean;
import com.system.technologyinformation.model.IdentificationInfosBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.OnDemandBean;
import com.system.technologyinformation.model.OnlineSupermarketBean;
import com.system.technologyinformation.model.OnlineSupermarketTypeBean;
import com.system.technologyinformation.model.PagingInformationBean;
import com.system.technologyinformation.model.PayMethodBean;
import com.system.technologyinformation.model.ProductItemInfo;
import com.system.technologyinformation.model.ProfessionalTypeBean;
import com.system.technologyinformation.model.ProvinceBean;
import com.system.technologyinformation.model.QuestionBean;
import com.system.technologyinformation.model.RechargersTelephoneChargesBean;
import com.system.technologyinformation.model.RechargesHistoryBean;
import com.system.technologyinformation.model.RewardBean;
import com.system.technologyinformation.model.RtcOrderBean;
import com.system.technologyinformation.model.SignBean;
import com.system.technologyinformation.model.StoreInfoBean;
import com.system.technologyinformation.model.StoreOrderBean;
import com.system.technologyinformation.model.UserConsumptionBean;
import com.system.technologyinformation.model.UserIntegralBean;
import com.system.technologyinformation.model.UserMessage;
import com.system.technologyinformation.model.UserNameInfoBean;
import com.system.technologyinformation.model.UserOrderBean;
import com.system.technologyinformation.model.UserRechargeAliBean;
import com.system.technologyinformation.model.UserRechargeWxBean;
import com.system.technologyinformation.model.UserWxPayBean;
import com.system.technologyinformation.model.VersionShowBean;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by wujiajun on 17/4/5.
 */

public interface ApiService {

    //http://api8081.ximuok.com/  http://10.10.10.202:8000/  http://gyj-api.idougong.com/  String HOST = "http://api8081.ximuok.com/";
    @GET("api/index/goods.json")
    Flowable<BaseResponseInfo<List<ProductItemInfo>>> getProductList(@Query("page") int page,
                                                                     @Query("rows") int rows,
                                                                     @Query("type") String type);

    //用户登录
    @POST(Constant.USER_LOGIN)
    @FormUrlEncoded
    Flowable<BaseResponseInfo<LoginBean>> getUserLoginResult(@FieldMap Map<String, String> fieldsMaps);

    //推送token
    @POST(Constant.USER_ADD_PUSH_TOKEN)
    @FormUrlEncoded
    Flowable<BaseResponseInfo<String>> addPushMessageToken(@FieldMap Map<String, String> fieldsMaps);

    //用户注册
    @POST(Constant.USER_REGISTER)
    @FormUrlEncoded
    Flowable<BaseResponseInfo> registerUserInfo(@FieldMap Map<String, String> fieldsMaps);

    //用户注册
    @POST(Constant.USER_FORGOT_PASSWORD)
    @FormUrlEncoded
    Flowable<BaseResponseInfo> forgotPasswordInfo(@FieldMap Map<String, String> fieldsMaps);

    //验证码
    @POST(Constant.USER_SMS_SEND)
    @FormUrlEncoded
    Flowable<BaseResponseInfo> registerUserInfoSms(@FieldMap Map<String, String> fieldsMaps);


    //验证手机号
    @POST(Constant.USER_REGISTER_CHECK_PHONE)
    @FormUrlEncoded
    Flowable<BaseResponseInfo> registerCheckPhone(@FieldMap Map<String, String> fieldsMaps);


    //更新用户信息
    @GET(Constant.UPDATE_USER_INFO)
    Flowable<BaseResponseInfo<LoginBean>> getUpdateUserInfoResult();

    //更新用户信息
    @GET(Constant.UPDATE_USER_INFO)
    Flowable<BaseResponseInfo> getUpdateUserInfoResult1();

    //用户过期刷新  f
    @POST(Constant.REFRESH_USER)
    Flowable<BaseResponseInfo> refreshUserTime();

    //退出登录
    @POST(Constant.USER_LOGOUT)
    Flowable<BaseResponseInfo<String>> getUserLogoutResult();

    //首页头部配置
    @GET(Constant.HOME_FUNCTION_TOP)
    Flowable<BaseResponseInfo<List<HomeConfigurationInformationBean>>> getHomeConfigurationInformation();

    //闪屏信息
    @GET(Constant.FICKERSCREENINFO)
    Flowable<BaseResponseInfo<List<FlickerScreenBean>>> getFlickerScreenInfo();

    //首页服务功能
    @GET(Constant.HOME_FUNCTION_DIVISION)
    Flowable<BaseResponseInfo<List<ConvenientFunctionsBean>>> getFunctionDivisionOne();

    //首页消息功能
    @GET(Constant.HOME_FUNCTION_MESSAGE)
    Flowable<BaseResponseInfo> getFunctionDivisionTwo();

    //首页Banner
    @GET(Constant.HOME_BANNER)
    Flowable<BaseResponseInfo<List<HomeBannerBean>>> getHomeBannerResult(@Query("type") int type);

    //资讯列表
    @GET(Constant.HOME_DATA)
    Flowable<BaseResponseInfo<PagingInformationBean<List<HomeDataBean>>>> getHomeDataResult(@Query("pageNum") int page, @Query("pageSize") int pageSize);

    //抢购商品列表
    @GET(Constant.DATA_SHOPPING)
    Flowable<BaseResponseInfo<PagingInformationBean<List<HomeShoppingSpreeBean>>>> getHomeShoppingDataResult(@Query("pageNum") int page, @Query("pageSize") int pageSize);

    //首页抢购商品列表
    @GET(Constant.HOME_DATA_SHOPPING)
    Flowable<BaseResponseInfo<List<HomeShoppingSpreeBean>>> getHomeShoppingDataResult1();

    //兑换商品
    @GET(Constant.CREDITS_EXCHANGE_DATA_SHOPPING)
    Flowable<BaseResponseInfo<PagingInformationBean<List<HomeShoppingSpreeBean>>>> getShoppingDataResult(@Query("pageNum") int page, @Query("pageSize") int pageSize);


    //商品xq
    @GET(Constant.CREDITS_EXCHANGE_DATA_SHOPPING_DETAILED)
    Flowable<BaseResponseInfo<HomeShoppingSpreeBean>> getShoppingDetailedResult(@Query("id") int id);


    //抢购兑换下单
    @POST(Constant.USER_MARKET_CREATEORDER)
    @FormUrlEncoded
    Flowable<BaseResponseInfo<CreditsExchangePayBean>> addUserMarketCreateOrderResult(@FieldMap Map<String, String> fieldsMaps);

    //首页列表详情
    @GET(Constant.HOME_DATA_DETAILED)
    Flowable<BaseResponseInfo<HomeDetailedBean>> getHomeDetailedResult(@Query("id") int id, @Query("countReadNum") boolean countReadNum);

    //消费列表
    @GET(Constant.USER_CONSUMEINFO_LIST)
    Flowable<BaseResponseInfo<PagingInformationBean<List<UserConsumptionBean>>>> getUserConsumptionListResult(@Query("pageNum") int page, @Query("pageSize") int pageSize);

    //积分列表
    @GET(Constant.USER_POINTSINFO_LIST)
    Flowable<BaseResponseInfo<PagingInformationBean<List<UserIntegralBean>>>> getUserIntegralListResult(@Query("pageNum") int page, @Query("pageSize") int pageSize);

    //完善用户信息
    @POST(Constant.USER_UPDATE_INFO)
    @FormUrlEncoded
    Flowable<BaseResponseInfo<String>> getUpdateUserInfoResult(@FieldMap Map<String, Object> fieldsMaps);

    //地址信息，薪资添加
    @POST(Constant.USER_UPDATE_ADDRESS_OR_SALARY_ADD)
    @FormUrlEncoded
    Flowable<BaseResponseInfo<String>> getUserAddressResult(@FieldMap Map<String, String> fieldsMaps);

    //职业类型
    @GET(Constant.USER_PROFESSIONAL_TYPE)
    Flowable<BaseResponseInfo<PagingInformationBean<List<ProfessionalTypeBean>>>> getProfessionalTypeListResult();

    //工种认证
    @POST(Constant.USER_PROFESSIONAL_ADD)
    @FormUrlEncoded
    Flowable<BaseResponseInfo<String>> getProfessionalAddResult(@FieldMap Map<String, String> fieldsMaps);


    //实名认证
    @POST(Constant.USER_NAME_ADD)
    @FormUrlEncoded
    Flowable<BaseResponseInfo<String>> getNameCertificationAddResult(@FieldMap Map<String, String> fieldsMaps);


    //实名认证 信息
    @GET(Constant.USER_NAME_INFO)
    Flowable<BaseResponseInfo<UserNameInfoBean>> getNameCertificationInfoResult();

    //职业认证列表
    @GET(Constant.USER_PROFESSIONAL_LIST)
    Flowable<BaseResponseInfo<List<IdentificationInfosBean>>> getProfessionalListResult();

    //文件上传
    @Multipart
    @POST(Constant.FILE_UPLOAD)
    Flowable<BaseResponseInfo<String>> getFileUploadResult(@Part("file") RequestBody description,
                                                           @Part MultipartBody.Part file, @PartMap Map<String, RequestBody> files);

    //职业/姓名 认证修改
    @POST(Constant.USER_PROFESSIONAL_UPDATE)
    @FormUrlEncoded
    Flowable<BaseResponseInfo<String>> getNameCertificationUpdateResult(@FieldMap Map<String, String> fieldsMaps);


    //签到
    @POST(Constant.USER_SIGN_ADD)
    Flowable<BaseResponseInfo<RewardBean>> getUserSignAddResult();

    //补签
    @POST(Constant.USER_SIGN_ADD_REPAIR)
    @FormUrlEncoded
    Flowable<BaseResponseInfo<RewardBean>> getUserSignRepairAddResult(@FieldMap Map<String, String> fieldsMaps);

    //任务列表 DailyMission
    @GET(Constant.DAILYMISSIONLIST)
    Flowable<BaseResponseInfo<List<DailyMissionBean>>> getDailyMissionListResult();

    //当天签到信息
    @GET(Constant.USER_SIGN_DAY_INFO)
    Flowable<BaseResponseInfo<PagingInformationBean<List<SignBean>>>> getUserSignDayInfoResult(@Query("date") String date);

    //当月签到信息
    @GET(Constant.USER_SIGN_MONTH_INFO)
    Flowable<BaseResponseInfo<List<String>>> getUserSignMonthInfoResult(@Query("month") String month);

    //投票Consultation
    @POST(Constant.CONSULTATION_ADD_VOTE)
    @FormUrlEncoded
    Flowable<BaseResponseInfo<String>> getHomeDetailedAddVoteResult(@FieldMap Map<String, String> fieldsMaps);

    //投票Consultation
    @POST(Constant.CONSULTATION_ADD_FEEDBACK)
    @FormUrlEncoded
    Flowable<BaseResponseInfo<String>> getHomeDetailedAddFeedbackResult(@FieldMap Map<String, String> fieldsMaps);

    //消息列表
    @GET(Constant.USER_MESSAGE_LIST)
    Flowable<BaseResponseInfo<PagingInformationBean<List<UserMessage>>>> getUserMessageListResult(@Query("pageNum") int page, @Query("pageSize") int pageSize);

    //职业类型
    @GET(Constant.PROVINCEORCITY)
    Flowable<BaseResponseInfo<PagingInformationBean<List<ProvinceBean>>>> getProvinceOrCityInfoResult();


    //分享Consultation
    @POST(Constant.CONSULTATION_ADD_FORWARD)
    @FormUrlEncoded
    Flowable<BaseResponseInfo<String>> getHomeDetailedAddForwardResult(@FieldMap Map<String, String> fieldsMaps);

    //投票Consultation
    @POST(Constant.CONSULTATION_UPDATE_FEEDBACK)
    @FormUrlEncoded
    Flowable<BaseResponseInfo<PagingInformationBean<List<FeedbacksBean>>>> getHomeDetailedUpdateFeedbackResult(@FieldMap Map<String, String> fieldsMaps);

    //商店信息
    @GET(Constant.STORE_INFO)
    Flowable<BaseResponseInfo<StoreInfoBean>> getStoreInfoResult(@Query("id") int id);

    //预订单生成
    @POST(Constant.STORE_ORDER)
    @FormUrlEncoded
    Flowable<BaseResponseInfo<StoreOrderBean>> getStoreOrderResult(@FieldMap Map<String, String> fieldsMaps);

    //版本更新
    @POST(Constant.FINDVERSION)
    @FormUrlEncoded
    Flowable<BaseResponseInfo<VersionShowBean>> getVersionResult(@FieldMap Map<String, String> fieldsMaps);

    //wx支付
    @POST(Constant.USERRECHARGEWX)
    @FormUrlEncoded
    Flowable<BaseResponseInfo<UserWxPayBean>> getUserRechargeWx(@FieldMap Map<String, Object> fieldsMaps);

    //zfb支付
    @POST(Constant.USERRECHARGEZFB)
    @FormUrlEncoded
    Flowable<BaseResponseInfo<UserRechargeAliBean>> getUserRechargeZfb(@FieldMap Map<String, Object> fieldsMaps);

    //免单
    @POST(Constant.USERFREEOFCHARGERESULT)
    @FormUrlEncoded
    Flowable<BaseResponseInfo<String>> getUserFreeOfChargeResult(@FieldMap Map<String, Object> fieldsMaps);

    //xj支付
    @POST(Constant.USERRECHARGEXJ)
    @FormUrlEncoded
    Flowable<BaseResponseInfo<String>> getUserRechargeXj(@FieldMap Map<String, String> fieldsMaps);

    //取消订单
    @POST(Constant.USERORDERCANCEL)
    @FormUrlEncoded
    Flowable<BaseResponseInfo<String>> getUserOrderCancelResult(@FieldMap Map<String, String> fieldsMaps);

    //打开红包
    @GET(Constant.USER_OPEN_REWAR)
    Flowable<BaseResponseInfo<RewardBean>> getUserOpenRewardInfoResult(@Query("type") int type);

    //工地列表
    @GET(Constant.CONSTRUCTIONPLACE)
    Flowable<BaseResponseInfo<List<ConstructionPlaceBean>>> getConstructionPlaceInfoResult();


    @GET(Constant.FOODSEARCH)
    Flowable<BaseResponseInfo<PagingInformationBean<List<OnlineSupermarketBean>>>> getTableReservationList(@Query("name") String name,
                                                                                                           @Query("pageSize") int pageSize,
                                                                                                           @Query("pageNum") int pageNum);


    @GET(Constant.ONLINESUPERMARKETGOODS)
    Flowable<BaseResponseInfo<PagingInformationBean<List<OnlineSupermarketBean>>>> getOnlineSupermarketGoodsList(@Query("name") String name,
                                                                                                                 @Query("pageSize") int pageSize,
                                                                                                                 @Query("pageNum") int pageNum);

    @GET(Constant.ONLINESUPERMARKETGOODSTYPE)
    Flowable<BaseResponseInfo<PagingInformationBean<List<OnlineSupermarketTypeBean>>>> getOnlineSupermarketGoodsTypeList(@Query("name") String name,
                                                                                                                         @Query("categoryId") Integer parentId);

    @GET(Constant.ONLINESUPERMARKETGOODSANDTYPE)
    Flowable<BaseResponseInfo<List<OnlineSupermarketTypeBean>>> getOnlineSupermarketGoodsAndTypeList(@Query("food") Boolean food);

    //订单生成
    @POST(Constant.ONLINESUPERMARKETGOODSORDER)
    @FormUrlEncoded
    Flowable<BaseResponseInfo<ConfirmOrderBean>> setOnlineSupermarketGoodsOreder(@FieldMap Map<String, String> fieldsMaps);

    //添加配送
    @POST(Constant.USERADDDELIVERY)
    @FormUrlEncoded
    Flowable<BaseResponseInfo> addDeliveryResult(@FieldMap Map<String, String> fieldsMaps);


    //订单WX支付
    @POST(Constant.USERCONFIRMANORDERPAYWX)
    @FormUrlEncoded
    Flowable<BaseResponseInfo<UserRechargeWxBean>> getUserConfirmAnOrderPayWx(@FieldMap Map<String, String> fieldsMaps);

    //订单ZFB支付
    @POST(Constant.USERCONFIRMANORDERPAYZFB)
    @FormUrlEncoded
    Flowable<BaseResponseInfo<String>> getUserConfirmAnOrderPayZfb(@FieldMap Map<String, String> fieldsMaps);

    //订单
    @GET(Constant.USERORDER)
    Flowable<BaseResponseInfo<PagingInformationBean<List<UserOrderBean>>>> getUserOrderListResult(@Query("pageNum") int page, @Query("pageSize") int pageSize, @Query("status") Integer status);

    //活动信息
    @GET(Constant.ENROLLMENTINFO)
    Flowable<BaseResponseInfo<EnrollmentBean>> getEnrollmentInfoResult();

    //是否报过名
    @GET(Constant.WHETHERTHEREGISTRATION)
    Flowable<BaseResponseInfo<String>> getWhetherTheRegistration(@Query("raceId") int raceId);

    //报名
    @GET(Constant.WHETHERTHEREGISTRATIONISSUCCESSFUL)
    Flowable<BaseResponseInfo<String>> getWhetherTheRegistrationIsSuccessful(@Query("raceId") int raceId, @Query("project") String project);

    //直播信息
    @GET(Constant.ALIYUNPLAYERSKININFO)
    Flowable<BaseResponseInfo<AliyunPlayerSkinBean>> getAliyunPlayerSkinInfoResult();

    //点播列表
    @GET(Constant.ONDEMANDLISTINFO)
    Flowable<BaseResponseInfo<OnDemandBean>> getOnDemandListInfoResult(@Query("vodPageNum") int page, @Query("vodPageSize") int pageSize);

    //是否报过名
    @GET(Constant.ONDEMANDLISTDETAILESINFO)
    Flowable<BaseResponseInfo> getOnDemandDetailesInfoResult(@Query("vodId") String vodId);


    //提交工单
    @POST(Constant.ADDQUESTION)
    @FormUrlEncoded
    Flowable<BaseResponseInfo<QuestionBean>> addquestionMessage(@FieldMap Map<String, String> fieldsMaps);

    //Group user 信息
    @GET(Constant.GROUPUSERINFO)
    Flowable<BaseResponseInfo<List<GroupUserInfoBean>>> loadGroupUserInfo(@Query("groupId") String groupId);

    //Group列表
    @GET(Constant.GROUPINFO)
    Flowable<BaseResponseInfo<List<GroupInfoBean>>> loadGroupInfo();

    //分享内容
    @GET(Constant.SHARE_CONTENT)
    Flowable<BaseResponseInfo<String>> getUserShareContentResult(@Query("type") Integer type);

    //分享反馈
    @GET(Constant.SHARE_ADD)
    Flowable<BaseResponseInfo<String>> addUserShareResult();

    // 充值价格表
    @GET(Constant.PHONE_BILL_LIST)
    Flowable<BaseResponseInfo<List<RechargersTelephoneChargesBean>>> getRechargersTelephoneChargesListResult();

    // 充值Add
    @POST(Constant.PHONE_BILL_ADD)
    @FormUrlEncoded
    Flowable<BaseResponseInfo<RtcOrderBean>> addRtcOrderResult(@FieldMap Map<String, Object> fieldsMaps);


    //wx支付
    @POST(Constant.PHONE_BILL_WX)
    @FormUrlEncoded
    Flowable<BaseResponseInfo<UserRechargeWxBean>> getRtcRechargeWx(@FieldMap Map<String, Object> fieldsMaps);

    //zfb支付
    @POST(Constant.PHONE_BILL_ZFB)
    @FormUrlEncoded
    Flowable<BaseResponseInfo<String>> getRtcrRechargeZfb(@FieldMap Map<String, Object> fieldsMaps);

    //配送地址添加
    @POST(Constant.DELIVERY_ADDRESS_ADD)
    @FormUrlEncoded
    Flowable<BaseResponseInfo<String>> addUserDeliveryAddress(@FieldMap Map<String, Object> fieldsMaps);

    //配送地址修改
    @POST(Constant.DELIVERY_ADDRESS_UPDATE)
    @FormUrlEncoded
    Flowable<BaseResponseInfo<String>> updateUserDeliveryAddress(@FieldMap Map<String, Object> fieldsMaps);

    //获取配送地址
    @GET(Constant.DELIVERY_ADDRESS_LIST)
    Flowable<BaseResponseInfo<List<DeliveryAddressBean>>> getUserDeliveryAddressResult();

    //退款发起
    @GET(Constant.USER_ORDER_REFUNDS)
    Flowable<BaseResponseInfo<String>> addUserOrderRefunds(@Query("orderId") long orderId, @Query("reasons") String reasons);


    //扫码获取商户信息
    @POST(Constant.SHOP_INFO)
    @FormUrlEncoded
    Flowable<BaseResponseInfo<StoreInfoBean>> getShopInfo(@FieldMap Map<String, Object> fieldsMaps);

    //获取支付方式
    @GET(Constant.PAYMETHOD)
    Flowable<BaseResponseInfo<PayMethodBean>> getMethodOfPayment();

    //取消订单
    @POST(Constant.USERMARKETORDERCANCEL)
    @FormUrlEncoded
    Flowable<BaseResponseInfo<String>> getUserMarketOrderCancelResult(@FieldMap Map<String, Object> fieldsMaps);

    @GET(Constant.ADVERTISEINFO)
    Flowable<BaseResponseInfo<List<AdvertiseInfoBean>>> getAdvertiseInfo();

    @GET(Constant.RECHARGESHISTORY)
    Flowable<BaseResponseInfo<PagingInformationBean<List<RechargesHistoryBean>>>> getRechargesHistory(@Query("pageNum") int pageNum,@Query("pageSize") int pageSize);
}

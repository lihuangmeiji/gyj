package com.system.technologyinformation.common.di;

import com.system.technologyinformation.module.ui.MainActivity;
import com.system.technologyinformation.module.ui.account.ForgotPasswordActivity;
import com.system.technologyinformation.module.ui.account.LoginActivity;
import com.system.technologyinformation.module.ui.account.RegisterActivity;
import com.system.technologyinformation.module.ui.chat.QuestionDetailesActivity;
import com.system.technologyinformation.module.ui.chat.QuestionMessageActivity;
import com.system.technologyinformation.module.ui.entertainment.OnlineSupermarketActivity;
import com.system.technologyinformation.module.ui.entertainment.TableReservationActivity;
import com.system.technologyinformation.module.ui.entertainment.UserConfirmAnOrderActivity;
import com.system.technologyinformation.module.ui.home.CreditsExchangeActivity;
import com.system.technologyinformation.module.ui.home.CreditsExchangeConfirmActivity;
import com.system.technologyinformation.module.ui.home.CreditsExchangeDetailedActivity;
import com.system.technologyinformation.module.ui.home.EnrollmentActivity;
import com.system.technologyinformation.module.ui.home.HomeDetailedActivity;
import com.system.technologyinformation.module.ui.home.HomeVideoDetailedActivityctivity;
import com.system.technologyinformation.module.ui.home.InformationConsultingActivity;
import com.system.technologyinformation.module.ui.home.PanicBuyingActivity;
import com.system.technologyinformation.module.ui.home.RechargersTelephoneChargesActivity;
import com.system.technologyinformation.module.ui.home.RechargesHistoryActivity;
import com.system.technologyinformation.module.ui.users.DeliveryAddressActivity;
import com.system.technologyinformation.module.ui.users.ShopUserActivity;
import com.system.technologyinformation.module.ui.users.UserAddressActivity;
import com.system.technologyinformation.module.ui.users.UserConsumptionActivity;
import com.system.technologyinformation.module.ui.users.UserIntegralActivity;
import com.system.technologyinformation.module.ui.users.UserInviteCodeActivity;
import com.system.technologyinformation.module.ui.users.UserMessageActivity;
import com.system.technologyinformation.module.ui.users.UserNameCertificationActivity;
import com.system.technologyinformation.module.ui.users.UserNameCertificationAddActivity;
import com.system.technologyinformation.module.ui.users.UserNameCertificationSuccessActivity;
import com.system.technologyinformation.module.ui.users.UserOrderActivity;
import com.system.technologyinformation.module.ui.users.UserPayActivity;
import com.system.technologyinformation.module.ui.users.UserPerfectInformationActivity;
import com.system.technologyinformation.module.ui.users.UserProfessionalActivity;
import com.system.technologyinformation.module.ui.users.UserProfessionalAddActivity;
import com.system.technologyinformation.module.ui.users.UserAboutActivity;
import com.system.technologyinformation.wxapi.WXPayEntryActivity;
import com.wjj.easy.easyandroid.mvp.di.components.ActivityComponent;
import com.wjj.easy.easyandroid.mvp.di.modules.ActivityModule;
import com.wjj.easy.easyandroid.mvp.di.scopes.ActivityScope;
import com.system.technologyinformation.module.demo1.Demo1Activity;
import com.system.technologyinformation.module.demo5.Demo5Activity;

import dagger.Component;

/**
 * Activity注入器
 *
 * @author wujiajun
 */
@ActivityScope
@Component(dependencies = AppCommonComponent.class, modules = {ActivityModule.class})
public interface ActivityCommonComponent extends ActivityComponent {
    void inject(Demo1Activity activity);

    void inject(Demo5Activity activity);

    void inject(MainActivity activity);

    void inject(LoginActivity activity);

    void inject(UserIntegralActivity activity);

    void inject(RegisterActivity activity);

    void inject(ForgotPasswordActivity activity);

    void inject(UserConsumptionActivity activity);

    void inject(UserProfessionalActivity activity);

    void inject(UserAddressActivity activity);

    void inject(HomeDetailedActivity activity);

    void inject(UserProfessionalAddActivity activity);

    void inject(UserNameCertificationActivity activity);

    void inject(UserNameCertificationSuccessActivity activity);

    void inject(UserNameCertificationAddActivity activity);

    void inject(UserPerfectInformationActivity activity);

    void inject(HomeVideoDetailedActivityctivity activity);

    void inject(UserMessageActivity activity);

    void inject(UserPayActivity activity);

    void inject(WXPayEntryActivity activity);

    void inject(OnlineSupermarketActivity activity);

    void inject(TableReservationActivity activity);

    void inject(UserConfirmAnOrderActivity activity);

    void inject(UserOrderActivity activity);

    void inject(EnrollmentActivity activity);

    void inject(QuestionMessageActivity activity);

    void inject(QuestionDetailesActivity activity);

    void inject(UserAboutActivity activity);

    void inject(InformationConsultingActivity activity);

    void inject(PanicBuyingActivity activity);

    void inject(CreditsExchangeActivity activity);

    void inject(CreditsExchangeDetailedActivity activity);

    void inject(CreditsExchangeConfirmActivity activity);

    void inject(UserInviteCodeActivity activity);

    void inject(RechargersTelephoneChargesActivity activity);

    void inject(DeliveryAddressActivity activity);

    void inject(ShopUserActivity activity);

    void inject(RechargesHistoryActivity activity);
}

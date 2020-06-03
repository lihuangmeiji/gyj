package com.system.technologyinformation.common.di;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.system.technologyinformation.common.net.Constant;
import com.system.technologyinformation.common.net.EnvConstant;
import com.system.technologyinformation.utils.ConstUtils;
import com.system.technologyinformation.utils.MySharedPreferences;
import com.system.technologyinformation.utils.SharedPreferencesHelper;
import com.system.technologyinformation.widget.SystemUtil;
import com.wjj.easy.easyandroid.http.Http;
import com.wjj.easy.easyandroid.mvp.di.modules.AppModule;
import com.system.technologyinformation.common.net.ApiService;
import com.system.technologyinformation.common.net.CookieInterceptor;

import java.io.IOException;
import java.net.URLEncoder;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Application Module
 *
 * @author wujiajun
 */
@Module
public class AppCommonModule extends AppModule {

    public static String API_BASE_URL = EnvConstant.API_DEVENV_URL;

    static {
        API_BASE_URL = EnvConstant.isDevEnv ? EnvConstant.API_DEVENV_URL : EnvConstant.API_RELEASE_URL;
    }

    private Http mHttp;
    private int myScreenWidth;
    private int myScreenHeight;
    private String latitude;
    private String longitude;
    private String city;

    public AppCommonModule(Context context) {
        super(context);
        initScreenWidth(context);
        if (!EmptyUtils.isEmpty(MySharedPreferences.getName(context))) {
            API_BASE_URL = MySharedPreferences.getName(context);
            //spUtils.remove(ConstUtils.CONTEXTSWITCHINGKEYVALUE);
        }
        initHttp(context);
        initUtils();
        initRealm(context);
    }

    /**
     * 获取屏幕的参数，宽度和高度
     */
    private void initScreenWidth(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        myScreenHeight = metrics.heightPixels;
        myScreenWidth = metrics.widthPixels;
    }

    /**
     * Http初始化
     */
    private void initHttp(final Context context) {
        //cookie cache & persistor  .setCookieJar(cookieJar) .addInterceptor(new CookieInterceptor())
       /* ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(provideContext()));*/
        mHttp = new Http.HttpBuilder()
                .setBaseUrl(API_BASE_URL)
                .setTimeout(30)
                .setCookieJar(null)
                .addInterceptor(new CookieInterceptor())
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        //这里获取请求返回的cookie
                        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                            final StringBuffer cookieBuffer = new StringBuffer();
                            for (int i = 0; i < originalResponse.headers("Set-Cookie").size(); i++) {
                                cookieBuffer.append(originalResponse.headers("Set-Cookie").get(i)).append(";");
                            }
                            //保存cookie数据
                            SPUtils spUtils = new SPUtils(Constant.COOKIE);
                            spUtils.put(Constant.COOKIE, cookieBuffer.toString());
                            String cookie = spUtils.getString(Constant.COOKIE);
                            //Log.i("cookie add", "intercept: "+cookie+"url:"+chain.request().url());
                        }
                        return originalResponse;
                    }
                }).addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder builder = chain.request().newBuilder();
                        SPUtils spUtils = new SPUtils(Constant.COOKIE);
                        String cookie = spUtils.getString(Constant.COOKIE);
                        Log.i("cookie", "intercept: "+cookie+"url:"+chain.request().url());
                        if (!EmptyUtils.isEmpty(cookie)) {
                            builder.addHeader("Cookie", cookie);
                        }

                        builder.addHeader("apptype", "android");
                        builder.addHeader("appversion", ConstUtils.getVersioncode(context) + "");
                        builder.addHeader("timestamp", System.currentTimeMillis() + "");
                        latitude = SharedPreferencesHelper.getStringSF(context, "lat");
                        longitude = SharedPreferencesHelper.getStringSF(context, "lng");
                        city = SharedPreferencesHelper.getStringSF(context, "locality");
                        String maptype = "BD-09";
                        if (latitude == null||longitude == null) {
                            maptype = "";
                            latitude="";
                            longitude="";
                        }
                        if (city != null) {
                            city = URLEncoder.encode(city, "utf-8");
                        }else{
                            city="";
                        }
                        //Log.i("url", "intercept: "+chain.request().url()+", city="+city);
                        //+"|"+longitude+","+latitude+","+maptype+"|"+city
                        String ua = SystemUtil.getSystemVersion() + "|okhttp 3.3.6|android," + SystemUtil.getSystemVersion() + "|" + SystemUtil.getDeviceBrand() + "," + SystemUtil.getSystemModel() + "|" + myScreenWidth + "," + myScreenHeight + "|" + SystemUtil.getAppMetaData(context, "gyj_channel")+"|"+longitude+","+latitude+","+maptype+"|"+city;
                        builder.addHeader("user-agent", ua);
                        return chain.proceed(builder.build());
                    }
                })
                .build();
    }

    /**
     * Utils库初始化
     */
    private void initUtils() {
        Utils.init(provideContext());
    }

    /**
     * init Realm
     *
     * @param context
     */
    private void initRealm(Context context) {
        Realm.init(context);
    }

    @Provides
    ApiService provideApiService() {
        return mHttp.getRetrofit().create(ApiService.class);
    }

    @Provides
    Realm provideRealm() {
        return Realm.getDefaultInstance();
    }
}

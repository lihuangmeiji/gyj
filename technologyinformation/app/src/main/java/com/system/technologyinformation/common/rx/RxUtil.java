package com.system.technologyinformation.common.rx;

import com.blankj.utilcode.util.EmptyUtils;
import com.system.technologyinformation.model.http.base.BaseResponseInfo;
import com.system.technologyinformation.model.http.base.BaseStatus;
import com.system.technologyinformation.model.http.exception.ApiException;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Rx工具类
 */
public class RxUtil {

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new FlowableTransformer<T, T>() {
            @Override
            public Flowable<T> apply(Flowable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<BaseResponseInfo<T>, T> handleResult() {   //compose判断结果
        return new FlowableTransformer<BaseResponseInfo<T>, T>() {
            @Override
            public Flowable<T> apply(Flowable<BaseResponseInfo<T>> httpResponseFlowable) {
                return httpResponseFlowable.flatMap(new Function<BaseResponseInfo<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(BaseResponseInfo<T> tMyHttpResponse) {
                        if (tMyHttpResponse.getCode() == BaseStatus.RESPONSE_CODE_SUCCESS) {
                            if (tMyHttpResponse.getData() == null) {
                                return Flowable.error(new ApiException("", -10));  //data空处理
                            }
                            return createData(tMyHttpResponse.getData());
                        } else if (tMyHttpResponse.getCode() == BaseStatus.RESPONSE_CODE_LOGIN_OVERDUE) {
                            return Flowable.error(new ApiException("重置本地信息", -1));
                        } else {
                            return Flowable.error(new ApiException(tMyHttpResponse.getMsg(), tMyHttpResponse.getCode()));
                        }
                    }
                });
            }
        };
    }

    /**
     * 生成Flowable
     *
     * @param <T>
     * @return
     */
    public static <T> Flowable<T> createData(final T t) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }
}

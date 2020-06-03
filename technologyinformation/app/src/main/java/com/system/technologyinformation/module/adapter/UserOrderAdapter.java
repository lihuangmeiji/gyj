package com.system.technologyinformation.module.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.constant.TimeConstants;
import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.system.technologyinformation.R;
import com.system.technologyinformation.model.DeliveryInfoBean;
import com.system.technologyinformation.model.LoginBean;
import com.system.technologyinformation.model.ProductItemInfo;
import com.system.technologyinformation.model.UserOrderBean;
import com.system.technologyinformation.utils.TextUtil;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.List;

/**
 * Created by wujiajun on 2017/10/18.
 */

public class UserOrderAdapter extends BaseQuickAdapter<UserOrderBean, BaseViewHolder> {
    private UserOrderInterface userOrderInterface;

    public UserOrderInterface getUserOrderInterface() {
        return userOrderInterface;
    }

    public void setUserOrderInterface(UserOrderInterface userOrderInterface) {
        this.userOrderInterface = userOrderInterface;
    }

    public UserOrderAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final UserOrderBean item) {

        TextView tv_order_id = helper.getView(R.id.tv_order_id);
        tv_order_id.setText("订单编号:" + item.getOrderNo());
        ImageView iv_order_id = helper.getView(R.id.iv_order_id);
        iv_order_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userOrderInterface.UserOrderNoCopy(item.getOrderNo());
            }
        });
        TextView tv_uo_status = helper.getView(R.id.tv_uo_status);
        RecyclerView recycler_view = helper.getView(R.id.recycler_view);
        UserOrderVoListAdapter userOrderVoListAdapter = new UserOrderVoListAdapter(R.layout.item_confirm_order);
        userOrderVoListAdapter.addData(item.getProducts());
        recycler_view.setLayoutManager(new LinearLayoutManager(recycler_view.getContext()));
        recycler_view.setAdapter(userOrderVoListAdapter);
        TextView tv_uo_operation1 = helper.getView(R.id.tv_uo_operation1);
        TextView tv_uo_operation2 = helper.getView(R.id.tv_uo_operation2);
        TextView tv_order_c_time = helper.getView(R.id.tv_order_c_time);
        tv_order_c_time.setText("下单时间:" + item.getCreateAt());
        TextView tv_order_l_time = helper.getView(R.id.tv_order_l_time);
        tv_order_l_time.setVisibility(View.GONE);
        //qg 1 2dh 3dc   model==2 type 1st 2xn 3q
        if (item.getModel() == 1) {
            int num = 0;
            if (item.getProducts() != null && item.getProducts().size() > 0) {
                for (int i = 0; i < item.getProducts().size(); i++) {
                    num = num + item.getProducts().get(i).getNum();
                }
            }
            helper.setText(R.id.tv_uo_info, "共" + num + "件商品 合计 ¥" + item.getFinalPrice());
            tv_uo_status.setVisibility(View.VISIBLE);
            if (item.getStatus() == 1) {
                tv_uo_status.setText("未支付");
                tv_uo_operation1.setVisibility(View.GONE);
                tv_uo_operation2.setVisibility(View.VISIBLE);
                tv_uo_operation2.setText("去支付");
                tv_uo_operation2.setBackground(tv_uo_operation2.getContext().getResources().getDrawable(R.drawable.bg_shape_order));
                tv_uo_operation2.setTextColor(tv_uo_operation2.getContext().getResources().getColor(R.color.color37));
                tv_uo_operation2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userOrderInterface.payOrder(item);
                    }
                });
            } else {
                tv_uo_status.setVisibility(View.VISIBLE);
                if (item.getStatus() == 2) {
                    tv_uo_status.setText("支付成功");
                } else if (item.getStatus() == 3) {
                    tv_uo_status.setText("取消订单");
                } else if (item.getStatus() == 4) {
                    tv_uo_status.setText("已发货");
                } else if (item.getStatus() == 5) {
                    tv_uo_status.setText("交易成功");
                } else if (item.getStatus() == 6) {
                    tv_uo_status.setText("退货/退款中");
                } else if (item.getStatus() == 7) {
                    tv_uo_status.setText("订单完成");
                }
                if (item.isSalesReturn()) {
                    tv_uo_operation2.setText("退货退款");
                    tv_uo_operation2.setBackground(tv_uo_operation2.getContext().getResources().getDrawable(R.drawable.bg_shape_order1));
                    tv_uo_operation2.setTextColor(tv_uo_operation2.getContext().getResources().getColor(R.color.black));
                    tv_uo_operation2.setVisibility(View.VISIBLE);
                    tv_uo_operation2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            userOrderInterface.refundsOrder(item.getId());
                        }
                    });
                    helper.getView(R.id.v_live).setVisibility(View.VISIBLE);
                } else {
                    tv_uo_operation2.setVisibility(View.GONE);
                    helper.getView(R.id.v_live).setVisibility(View.GONE);
                }
                if (item.getDelivery()!=null&&item.getDelivery().getDeliveryInfo() != null) {
                    tv_uo_operation1.setVisibility(View.VISIBLE);
                    tv_uo_operation1.setText("查看物流");
                    tv_uo_operation1.setBackground(tv_uo_operation2.getContext().getResources().getDrawable(R.drawable.bg_shape_order1));
                    tv_uo_operation1.setTextColor(tv_uo_operation2.getContext().getResources().getColor(R.color.black));
                    tv_uo_operation1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Type type = new TypeToken<List<DeliveryInfoBean>>() {
                            }.getType();
                            List<DeliveryInfoBean> deliveryInfoBeanList = new Gson().fromJson(item.getDelivery().getDeliveryInfo(), type);
                            userOrderInterface.ShowLogistics(deliveryInfoBeanList, 1);
                        }
                    });
                    helper.getView(R.id.v_live).setVisibility(View.VISIBLE);
                } else {
                    tv_uo_operation1.setVisibility(View.GONE);
                    helper.getView(R.id.v_live).setVisibility(View.GONE);
                }
            }
        } else if (item.getModel() == 2) {
            TextView tv_uo_info = helper.getView(R.id.tv_uo_info);
            if (item.getProducts() != null && item.getProducts().size() > 0) {
                if (!EmptyUtils.isEmpty(item.getFinalPrice()) && item.getFinalPrice() > 0) {
                    if (item.getPoint() > 0) {
                        tv_uo_info.setText(TextUtil.FontHighlighting(tv_uo_info.getContext(), "共" + item.getProducts().size() + "件商品 合计 ¥", "" + item.getFinalPrice(), "+" + item.getPoint() + "积分", R.style.tv_ce_point1, R.style.tv_ce_point2, R.style.tv_ce_point1));
                    } else {
                        tv_uo_info.setText(TextUtil.FontHighlighting(tv_uo_info.getContext(), "共" + item.getProducts().size() + "件商品 合计 ¥", "" + item.getFinalPrice(), R.style.tv_ce_point1, R.style.tv_ce_point2));
                    }
                } else {
                    if (item.getPoint() > 0) {
                        tv_uo_info.setText(TextUtil.FontHighlighting(tv_uo_info.getContext(), "共" + item.getProducts().size() + "件商品 合计 ", item.getPoint() + "积分", R.style.tv_ce_point1, R.style.tv_ce_point1));
                    }
                }
            }
            tv_uo_status.setVisibility(View.GONE);
            tv_uo_operation1.setVisibility(View.GONE);
            tv_uo_operation2.setVisibility(View.VISIBLE);
            if (item.getType() == 1) {
                tv_uo_status.setVisibility(View.VISIBLE);
                if (item.getStatus() == 2) {
                    tv_uo_status.setText("支付成功");
                } else if (item.getStatus() == 3) {
                    tv_uo_status.setText("取消订单");
                } else if (item.getStatus() == 4) {
                    tv_uo_status.setText("已发货");
                } else if (item.getStatus() == 5) {
                    tv_uo_status.setText("交易成功");
                } else if (item.getStatus() == 6) {
                    tv_uo_status.setText("退货/退款中");
                } else if (item.getStatus() == 7) {
                    tv_uo_status.setText("订单完成");
                }
                if (item.getDelivery()!=null&&item.getDelivery().getDeliveryInfo() != null) {
                    tv_uo_operation2.setVisibility(View.VISIBLE);
                    tv_uo_operation2.setText("查看物流");
                    tv_uo_operation2.setBackground(tv_uo_operation2.getContext().getResources().getDrawable(R.drawable.bg_shape_order1));
                    tv_uo_operation2.setTextColor(tv_uo_operation2.getContext().getResources().getColor(R.color.black));
                    tv_uo_operation2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Type type = new TypeToken<List<DeliveryInfoBean>>() {
                            }.getType();
                            List<DeliveryInfoBean> deliveryInfoBeanList = new Gson().fromJson(item.getDelivery().getDeliveryInfo(), type);
                            userOrderInterface.ShowLogistics(deliveryInfoBeanList, 1);
                        }
                    });
                    helper.getView(R.id.v_live).setVisibility(View.VISIBLE);
                } else {
                    helper.getView(R.id.v_live).setVisibility(View.GONE);
                    tv_uo_operation2.setVisibility(View.GONE);
                }
            } else if (item.getType() == 2) {
                tv_uo_operation2.setText("查看状态");
                tv_uo_operation2.setBackground(tv_uo_operation2.getContext().getResources().getDrawable(R.drawable.bg_shape_order1));
                tv_uo_operation2.setTextColor(tv_uo_operation2.getContext().getResources().getColor(R.color.black));
                if (!EmptyUtils.isEmpty(item.getExchangeOrder())&&!EmptyUtils.isEmpty(item.getExchangeOrder().getUseInfo())) {
                    helper.getView(R.id.v_live).setVisibility(View.VISIBLE);
                    tv_uo_operation2.setVisibility(View.VISIBLE);
                    tv_uo_operation2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Type type = new TypeToken<List<DeliveryInfoBean>>() {
                            }.getType();
                            List<DeliveryInfoBean> deliveryInfoBeanList = new Gson().fromJson(item.getExchangeOrder().getUseInfo(), type);
                            userOrderInterface.ShowLogistics(deliveryInfoBeanList, 2);
                        }
                    });
                } else {
                    helper.getView(R.id.v_live).setVisibility(View.GONE);
                    tv_uo_operation2.setVisibility(View.GONE);
                }
            } else if (item.getType() == 3) {
                tv_uo_operation2.setBackground(tv_uo_operation2.getContext().getResources().getDrawable(R.drawable.bg_shape_order));
                tv_uo_operation2.setTextColor(tv_uo_operation2.getContext().getResources().getColor(R.color.color37));
                tv_uo_operation2.setText("兑换码");
                if (item.getExchangeOrder() != null && item.getExchangeOrder().getStatus() == 1) {
                    tv_uo_operation2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            userOrderInterface.ShowQrCodeImg(item.getModel(), item.getExchangeOrder().getTarget());
                        }
                    });
                } else {
                    helper.getView(R.id.v_live).setVisibility(View.GONE);
                    tv_uo_operation2.setVisibility(View.GONE);
                }
            }
        } else if (item.getModel() == 3) {
            int num = 0;
            if (item.getProducts() != null && item.getProducts().size() > 0) {
                for (int i = 0; i < item.getProducts().size(); i++) {
                    num = num + item.getProducts().get(i).getNum();
                }
            }
            helper.setText(R.id.tv_uo_info, "共" + num + "件商品 合计 ¥" + item.getFinalPrice());
            tv_uo_status.setVisibility(View.GONE);
            tv_uo_operation1.setVisibility(View.GONE);
            tv_uo_operation2.setVisibility(View.VISIBLE);
            tv_uo_operation2.setBackground(tv_uo_operation2.getContext().getResources().getDrawable(R.drawable.bg_shape_order));
            tv_uo_operation2.setTextColor(tv_uo_operation2.getContext().getResources().getColor(R.color.color37));
            if (item.getStatus() == 1) {
                tv_uo_operation2.setText("去支付");
                tv_uo_operation2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userOrderInterface.payOrder(item);
                    }
                });
            } else {
                if (item.getDelivery() != null && item.getDelivery().getStatus() == 2) {
                    tv_order_l_time.setVisibility(View.VISIBLE);
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.setTime(TimeUtils.string2Date(item.getDelivery().getTime()));
                    int myear = calendar1.get(Calendar.YEAR);
                    int mMonth = calendar1.get(Calendar.MONTH) + 1;// 获取当前月份
                    int day = calendar1.get(Calendar.DAY_OF_MONTH);// 获取当前天数
                    int hour = calendar1.get(Calendar.HOUR_OF_DAY);// 获取当前天数
                    if (hour == 12) {
                        tv_order_l_time.setText("领取时间:" + myear + "-" + mMonth + "-" + day + "  午餐");
                    } else if (hour == 18) {
                        tv_order_l_time.setText("领取时间:" + myear + "-" + mMonth + "-" + day + "  晚餐");
                    } else {
                        tv_order_l_time.setText("领取时间:" + myear + "-" + mMonth + "-" + day + "  " + hour + ":00");
                    }
                    //tv_order_l_time.setText("领取时间:" +item.getDelivery().getTime());
                    tv_uo_operation2.setText("取货码");
                    tv_uo_operation2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            userOrderInterface.ShowQrCodeImg(item.getModel(), item.getDelivery().getQrCodeImg());
                        }
                    });
                } else {
                    helper.getView(R.id.v_live).setVisibility(View.GONE);
                    tv_uo_operation2.setVisibility(View.GONE);
                }
            }
        }

    }

    /**
     * 改变数量的接口
     */
    public interface UserOrderInterface {
        void BuyAgain(UserOrderBean item);

        void ShowQrCodeImg(int model, String qrCode);

        void ShowLogistics(List<DeliveryInfoBean> deliveryInfoBeanList, int type);

        void payOrder(UserOrderBean userOrderBean);

        void refundsOrder(int orderId);

        void UserOrderNoCopy(String orderNo);
    }
}

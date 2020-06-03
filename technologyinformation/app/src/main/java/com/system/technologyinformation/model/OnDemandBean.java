package com.system.technologyinformation.model;

import java.util.List;

public class OnDemandBean {

    /**
     * liveStatus : false
     * liveName : 赛况直播
     * liveIcon : http://gongyoujia-image.oss-cn-hangzhou.aliyuncs.com/24734d57-c43e-470d-b6eb-fbb22b32f144.jpg
     * description : 工友家赛况直播
     * obj : [{"vodId":"864144475c4745c9a0fbd3e9c719275d","coverUrl":"http://outin-2e78673886a111e9899d00163e1a625e.oss-cn-shanghai.aliyuncs.com/864144475c4745c9a0fbd3e9c719275d/snapshots/9eed29fc754b49ea82ed4a6da19ca707-00002.jpg?Expires=1560767581&OSSAccessKeyId=LTAIrkwb21KyGjJl&Signature=daFyqd%2FEQL3JFhIm3aXGiTQpEBo%3D","title":"gyjStream"},{"vodId":"07506fb556804560a825d31acfcb398f","coverUrl":"http://outin-2e78673886a111e9899d00163e1a625e.oss-cn-shanghai.aliyuncs.com/07506fb556804560a825d31acfcb398f/snapshots/d763d46d26684c6faddb2c3d254613e6-00005.jpg?Expires=1560767581&OSSAccessKeyId=LTAIrkwb21KyGjJl&Signature=XZ75vh4PUw%2BNP9C%2BIOlqqI5A6XE%3D","title":"gyjStream"},{"vodId":"63923bb2d82b4a6f80a1f55a7bdd0fc2","coverUrl":"http://outin-2e78673886a111e9899d00163e1a625e.oss-cn-shanghai.aliyuncs.com/63923bb2d82b4a6f80a1f55a7bdd0fc2/snapshots/0e839b650f644c49bfea0020b30d4599-00005.jpg?Expires=1560767581&OSSAccessKeyId=LTAIrkwb21KyGjJl&Signature=hvc8CFw%2B8%2BTn0owk3nExbX4qMwE%3D","title":"gyjStream"},{"vodId":"f207184fef1844179b04303539ce8d0f","coverUrl":"http://outin-2e78673886a111e9899d00163e1a625e.oss-cn-shanghai.aliyuncs.com/f207184fef1844179b04303539ce8d0f/snapshots/5f8661cd78fc4456a465c066d52c2534-00005.jpg?Expires=1560767581&OSSAccessKeyId=LTAIrkwb21KyGjJl&Signature=XHTyFTe3Nm0DaDEdWhgt%2FvQSzXc%3D","title":"gyjStream"},{"vodId":"e1ce86ef3adf4b3eb7e2989f3d10da23","coverUrl":"http://outin-2e78673886a111e9899d00163e1a625e.oss-cn-shanghai.aliyuncs.com/e1ce86ef3adf4b3eb7e2989f3d10da23/snapshots/c9c4fcbe41c04a2584e18f73ed01c43f-00005.jpg?Expires=1560767581&OSSAccessKeyId=LTAIrkwb21KyGjJl&Signature=KhvA%2FA2OSaxiMc0JtBSgVZiIFos%3D","title":"gyjStream"},{"vodId":"6810073d04fa4833a3faa0a453dae94d","coverUrl":"http://outin-2e78673886a111e9899d00163e1a625e.oss-cn-shanghai.aliyuncs.com/6810073d04fa4833a3faa0a453dae94d/snapshots/973b73e7be07431b90f1dbe18be9229a-00005.jpg?Expires=1560767581&OSSAccessKeyId=LTAIrkwb21KyGjJl&Signature=lKfatpv944StaYuOdiz2tLx2ghU%3D","title":"gyjStream"},{"vodId":"bad0d67a3caa4844bd7621365e012dcd","coverUrl":"http://outin-2e78673886a111e9899d00163e1a625e.oss-cn-shanghai.aliyuncs.com/bad0d67a3caa4844bd7621365e012dcd/snapshots/be6c1e5661ec4e17970ec5f4dc623a73-00005.jpg?Expires=1560767581&OSSAccessKeyId=LTAIrkwb21KyGjJl&Signature=oa7ji42AR%2BLspE12Z97g%2F%2FP0t6I%3D","title":"gyjStream"},{"vodId":"7d8727a6ced54f079cfa4b6ccd047dd1","coverUrl":"http://outin-2e78673886a111e9899d00163e1a625e.oss-cn-shanghai.aliyuncs.com/7d8727a6ced54f079cfa4b6ccd047dd1/snapshots/137a724d52ed4349b42780540899afbc-00005.jpg?Expires=1560767581&OSSAccessKeyId=LTAIrkwb21KyGjJl&Signature=3BTIEXFRX6WC6A6sibAuZZ3j2XE%3D","title":"gyjStream"},{"vodId":"2fe4aaf9fdc74f0aa06751e25f7f5d39","coverUrl":"http://outin-2e78673886a111e9899d00163e1a625e.oss-cn-shanghai.aliyuncs.com/2fe4aaf9fdc74f0aa06751e25f7f5d39/snapshots/011bf0e90dd14dbb878c5ac8335b2c14-00005.jpg?Expires=1560767581&OSSAccessKeyId=LTAIrkwb21KyGjJl&Signature=3XDhjFYMfcghHBejG7K5XI9gCKs%3D","title":"gyjStream"},{"vodId":"172cd598acc6466cbb3e2692ea3a0fb4","coverUrl":"http://outin-2e78673886a111e9899d00163e1a625e.oss-cn-shanghai.aliyuncs.com/172cd598acc6466cbb3e2692ea3a0fb4/snapshots/3415f392d7be4002aff01f1b0ec90ebd-00005.jpg?Expires=1560767581&OSSAccessKeyId=LTAIrkwb21KyGjJl&Signature=6TFxd5f4Q%2BGf%2BmK2oJNs2CATl0k%3D","title":"gyjStream"},{"vodId":"2df1dd09798e4be188a6c8296031ad30","coverUrl":"http://outin-2e78673886a111e9899d00163e1a625e.oss-cn-shanghai.aliyuncs.com/2df1dd09798e4be188a6c8296031ad30/snapshots/b8aeefd2d66843eb91712e52a4bcf50e-00005.jpg?Expires=1560767581&OSSAccessKeyId=LTAIrkwb21KyGjJl&Signature=yZvNJjhV%2FxqLww44VPZYNZAB1yU%3D","title":"gyjStream"},{"vodId":"7d8d507e2882415992849d103845ab9a","coverUrl":"http://outin-2e78673886a111e9899d00163e1a625e.oss-cn-shanghai.aliyuncs.com/7d8d507e2882415992849d103845ab9a/snapshots/be0f9687976341f0a6f157d0e67df2a2-00003.jpg?Expires=1560767581&OSSAccessKeyId=LTAIrkwb21KyGjJl&Signature=iqvmrraCp%2FnQqiclEAr%2FZPbDOGM%3D","title":"gyjStream"},{"vodId":"3b441ab08dc646da87bebd01a2f610ca","coverUrl":"http://outin-2e78673886a111e9899d00163e1a625e.oss-cn-shanghai.aliyuncs.com/3b441ab08dc646da87bebd01a2f610ca/snapshots/64964fa4aae146e09cd7c229c624e570-00005.jpg?Expires=1560767581&OSSAccessKeyId=LTAIrkwb21KyGjJl&Signature=ZDQdJyBhMmChfrxG8XcxhiyaJJE%3D","title":"gyjStream"},{"vodId":"8822966a7a8441028900cef6ac8244bb","coverUrl":"http://outin-2e78673886a111e9899d00163e1a625e.oss-cn-shanghai.aliyuncs.com/8822966a7a8441028900cef6ac8244bb/snapshots/867ce6af4a794a11b9f126430e24ec09-00005.jpg?Expires=1560767581&OSSAccessKeyId=LTAIrkwb21KyGjJl&Signature=mJi3hjQ6oC5NjvgxoS286yWK5t0%3D","title":"gyjStream"},{"vodId":"bbd8f3293a754e70b6faf378027f3389","coverUrl":"http://outin-2e78673886a111e9899d00163e1a625e.oss-cn-shanghai.aliyuncs.com/bbd8f3293a754e70b6faf378027f3389/snapshots/a78b79b929cb4b299ff2287122ae00be-00004.jpg?Expires=1560767581&OSSAccessKeyId=LTAIrkwb21KyGjJl&Signature=khsnQqLiA9%2FMI%2BTD6MPSIt0iIiE%3D","title":"11111"},{"vodId":"c8c4119468ec4aaf8d013c3ecb283eee","coverUrl":"http://outin-2e78673886a111e9899d00163e1a625e.oss-cn-shanghai.aliyuncs.com/c8c4119468ec4aaf8d013c3ecb283eee/snapshots/244300879c3d4ed987e786f152ae0ff6-00005.jpg?Expires=1560767581&OSSAccessKeyId=LTAIrkwb21KyGjJl&Signature=qFpI9SFbNCogjSa9Th8wSgirPNE%3D","title":"gyjStream"},{"vodId":"2ef443ad396b43e4bc2120460d37421f","coverUrl":"http://outin-2e78673886a111e9899d00163e1a625e.oss-cn-shanghai.aliyuncs.com/2ef443ad396b43e4bc2120460d37421f/snapshots/98534a1604cd45c287f6643327e13e64-00002.jpg?Expires=1560767581&OSSAccessKeyId=LTAIrkwb21KyGjJl&Signature=u1XHes2BF037ifLlyKDzKzsyok0%3D","title":"gyjStream"},{"vodId":"a31a1dc4fce74d3fb4e2203ae30813ca","coverUrl":"http://outin-2e78673886a111e9899d00163e1a625e.oss-cn-shanghai.aliyuncs.com/a31a1dc4fce74d3fb4e2203ae30813ca/snapshots/76853f5b753a4876ba43a3bf9a861798-00003.jpg?Expires=1560767581&OSSAccessKeyId=LTAIrkwb21KyGjJl&Signature=6X%2BD1NKvS%2FfKpj6lgfQdoIdxyn4%3D","title":"gyjStream"}]
     * total : 18
     */

    private boolean liveStatus;
    private String liveName;
    private String liveIcon;
    private String description;
    private int total;
    private List<ObjBean> obj;

    public boolean isLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(boolean liveStatus) {
        this.liveStatus = liveStatus;
    }

    public String getLiveName() {
        return liveName;
    }

    public void setLiveName(String liveName) {
        this.liveName = liveName;
    }

    public String getLiveIcon() {
        return liveIcon;
    }

    public void setLiveIcon(String liveIcon) {
        this.liveIcon = liveIcon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ObjBean> getObj() {
        return obj;
    }

    public void setObj(List<ObjBean> obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        /**
         * vodId : 864144475c4745c9a0fbd3e9c719275d
         * coverUrl : http://outin-2e78673886a111e9899d00163e1a625e.oss-cn-shanghai.aliyuncs.com/864144475c4745c9a0fbd3e9c719275d/snapshots/9eed29fc754b49ea82ed4a6da19ca707-00002.jpg?Expires=1560767581&OSSAccessKeyId=LTAIrkwb21KyGjJl&Signature=daFyqd%2FEQL3JFhIm3aXGiTQpEBo%3D
         * title : gyjStream
         */

        private String vodId;
        private String coverUrl;
        private String title;

        public String getVodId() {
            return vodId;
        }

        public void setVodId(String vodId) {
            this.vodId = vodId;
        }

        public String getCoverUrl() {
            return coverUrl;
        }

        public void setCoverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}

package com.system.technologyinformation.model;

public class AliyunPlayerSkinBean {

    /**
     * liveStatus : true
     * liveName : 赛况直播
     * liveIcon : http://gongyoujia-image.oss-cn-hangzhou.aliyuncs.com/24734d57-c43e-470d-b6eb-fbb22b32f144.jpg
     * description : 工友家赛况直播
     * obj : {"rtmpUrl":"rtmp://play.idougong.com/gyjApp/gyjStream?auth_key=1560341642-0-0-80f24991c6ace3fcc2f892c4336b5845","flvUrl":"http://play.idougong.com/gyjApp/gyjStream.flv?auth_key=1560341642-0-0-5d9cc58bf31902cdccebc54cb0e6b83e","m3u8Url":"http://play.idougong.com/gyjApp/gyjStream.m3u8?auth_key=1560341642-0-0-dd812ff6261c3d30428a1b6c9811d8d4"}
     */

    private boolean liveStatus;
    private String liveName;
    private String liveIcon;
    private String description;
    private ObjBean obj;

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

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        /**
         * rtmpUrl : rtmp://play.idougong.com/gyjApp/gyjStream?auth_key=1560341642-0-0-80f24991c6ace3fcc2f892c4336b5845
         * flvUrl : http://play.idougong.com/gyjApp/gyjStream.flv?auth_key=1560341642-0-0-5d9cc58bf31902cdccebc54cb0e6b83e
         * m3u8Url : http://play.idougong.com/gyjApp/gyjStream.m3u8?auth_key=1560341642-0-0-dd812ff6261c3d30428a1b6c9811d8d4
         */

        private String rtmpUrl;
        private String flvUrl;
        private String m3u8Url;

        public String getRtmpUrl() {
            return rtmpUrl;
        }

        public void setRtmpUrl(String rtmpUrl) {
            this.rtmpUrl = rtmpUrl;
        }

        public String getFlvUrl() {
            return flvUrl;
        }

        public void setFlvUrl(String flvUrl) {
            this.flvUrl = flvUrl;
        }

        public String getM3u8Url() {
            return m3u8Url;
        }

        public void setM3u8Url(String m3u8Url) {
            this.m3u8Url = m3u8Url;
        }
    }
}

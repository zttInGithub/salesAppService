package com.hrtp.MQService.common;

/**
 * PushCliParam
 * description 推送参数
 * create class by lxj 2019/1/11
 **/
public enum PushCliParam {
    AGENT_ID_MD("10000", "秒到", "f12daad9215dc64474086c9c", "3e43c0cc64ca4998285fe6c2"),
    AGENT_ID_SYT("10005", "会员宝收银台", "1dd65f73bd4ba6a485ab9998", "2ae1293e1dea22b7607afc2d"),
    AGENT_ID_ZYB("10006", "展业宝", "e571120949f26fe7a22c336f", "239f47b8abd2ad3afad7ea2d");
    private final String code;
    private final String msg;
    private final String appkey;
    private final String mastersecret;

    /** 私有构造
     * @param code 产品编号
     * @param msg  产品描述
     * @param appkey 产品appkey
     * @param mastersecret 产品masterkey
     */
    private PushCliParam(String code, String msg, String appkey, String mastersecret) {
        this.code = code;
        this.msg = msg;
        this.appkey = appkey;
        this.mastersecret = mastersecret;
    }

    /**根据产品编号获取app信息
     * @param agentId
     * @return
     */
    public static PushCliParam getByAgentId(String agentId) {
        switch (agentId) {
            case "10000":
                return PushCliParam.AGENT_ID_MD;
            case "10005":
                return PushCliParam.AGENT_ID_SYT;
            case "10006":
                return PushCliParam.AGENT_ID_ZYB;
        }
        return null;
    }

    public String code() {
        return code;
    }

    public String msg() {
        return msg;
    }

    public String appkey() {
        return appkey;
    }

    public String mastersecret() {
        return mastersecret;
    }
}
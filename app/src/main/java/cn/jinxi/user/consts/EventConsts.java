package cn.jinxi.user.consts;

import cn.jinxi.user.manager.vo.InfoDelivery;
import cn.jinxi.utils.DataUtils;

/**
 * Created by jiewang on 2015/10/19.
 */
public class EventConsts {


    public static class InfoDeliveryEvent extends BaseEvent<InfoDelivery> {
    }

    public static class BaseEvent<T> {
        public T data;

        public BaseEvent() {

        }

        public BaseEvent(T data) {
            this.data = data;
        }

        public static BaseEvent CreateFromJsonString(String jsonString, Class<? extends BaseEvent> jsonClass) {
            if (jsonString == null) return null;
            try {
                BaseEvent event = (BaseEvent) DataUtils.JsonStr2Obj(jsonString, jsonClass);
                return event;
            } catch (Exception e) {
                return null;
            }
        }

        public T getData() {
            return data;
        }

        public BaseEvent setData(T data) {
            this.data = data;
            return this;
        }

        public String toJsonString() {
            try {
                return DataUtils.Obj2JsonStr(this);
            } catch (Exception e) {
                return null;
            }
        }
    }

}

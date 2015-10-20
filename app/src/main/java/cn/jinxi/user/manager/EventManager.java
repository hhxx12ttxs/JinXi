package cn.jinxi.user.manager;

import cn.jinxi.user.consts.EventConsts;
import de.greenrobot.event.EventBus;

/**
 * Created by jiewang on 2015/10/19.
 */
public class EventManager {
    private static EventManager instance;
    private EventBus eventBus;

    private EventManager() {
        eventBus = EventBus.getDefault();
    }

    public static EventManager GetInstance() {
        if (instance == null) {
            synchronized (EventManager.class) {
                if (instance == null) {
                    instance = new EventManager();
                }
            }
        }
        return instance;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public void post(EventConsts.BaseEvent event) {
        eventBus.post(event);
    }

}

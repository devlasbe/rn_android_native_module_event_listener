package com.android_native_module_event_listener;

import android.os.Handler;
import android.os.Looper;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class EventModule extends ReactContextBaseJavaModule {
  ReactApplicationContext reactContext;
  private final Handler handler = new Handler(Looper.getMainLooper());  
  private Runnable runnable;
  private boolean isSending = false;

  EventModule(ReactApplicationContext context) {
    super(context);
    reactContext = context;
  }

  @Override
  public String getName() {
    return "EventModule";
  }

  private void sendEvent(String eventName, String eventData) {
    if (reactContext.hasActiveCatalystInstance()) {
        reactContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit(eventName, eventData);
    }
  }

  public void startSendingEvents() {
    if (isSending) return; // 이미 실행 중이면 중복 방지
    isSending = true;
    runnable = new Runnable() {
        @Override
        public void run() {
            sendEvent("MyCustomEvent", "Hello from Native!");
            handler.postDelayed(this, 1000); // 1초마다 실행
        }
    };
    handler.post(runnable);
  }

  public void stopSendingEvents() {
    if (runnable != null) {
        handler.removeCallbacks(runnable);
        runnable = null;
        isSending = false;
    }
  }

  @ReactMethod
  public void start() {
    startSendingEvents();
  }

  @ReactMethod
  public void stop() {
    stopSendingEvents();
  }
}

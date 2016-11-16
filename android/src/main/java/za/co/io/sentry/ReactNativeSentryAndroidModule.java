
package za.co.io.sentry;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import com.getsentry.raven.Raven;
import com.getsentry.raven.RavenFactory;
import com.getsentry.raven.event.Event.Level;
import com.getsentry.raven.event.EventBuilder;
import com.getsentry.raven.event.interfaces.ExceptionInterface;
import com.getsentry.raven.event.interfaces.MessageInterface;

import java.util.HashMap;
import java.util.Map;

public final class ReactNativeSentryAndroidModule extends ReactContextBaseJavaModule {

  public ReactNativeSentryAndroidModule(ReactApplicationContext rctx) {
    super(rctx);
    // Raven.init(this.getCurrentActivity().getApplication());
    Raven.init(rctx.getApplicationContext());
  }

  @Override
  public String getName() {
    return "ReactNativeSentryAndroid";
  }

  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    constants.put("SENTRY_EVENT_LEVEL_FATAL", Level.FATAL);
    constants.put("SENTRY_EVENT_LEVEL_ERROR", Level.ERROR);
    constants.put("SENTRY_EVENT_LEVEL_WARNING", Level.WARNING);
    constants.put("SENTRY_EVENT_LEVEL_INFO", Level.INFO);
    constants.put("SENTRY_EVENT_LEVEL_DEBUG", Level.DEBUG);
    return constants;
  }

  private static final Level getLevel(final String level) {
    if (level == null || level.isEmpty()) return Level.DEBUG;
    level = level.toLowerCase();
    if (level == "fatal") return Level.FATAL;
    else if (level == "error") return Level.ERROR;
    else if (level == "warning") return Level.WARNING;
    else if (level == "info") return Level.INFO;
    else if (level == "debug") return Level.DEBUG;
    else return Level.FATAL;
  }

  @ReactMethod
  public final void capture(final ReadableMap event, final Promise promise) {
    final EventBuilder eb = new EventBuilder();
    eb.withMessage(event.getString("message"));
    eb.withLevel(getLevel(event.getString("level")));
    eb.withLogger(event.getString("class"));
    eb.withSentryInterface(new ExceptionInterface(new Exception(event.getString("stack"))));
    Raven.capture(eb.build());
    promise.resolve(null);
  }
}

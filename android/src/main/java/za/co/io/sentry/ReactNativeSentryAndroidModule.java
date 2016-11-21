package za.co.io.sentry;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.getsentry.raven.android.Raven;
import com.getsentry.raven.event.Event.Level;
import com.getsentry.raven.event.EventBuilder;
import com.getsentry.raven.event.interfaces.ExceptionInterface;

public final class ReactNativeSentryAndroidModule extends ReactContextBaseJavaModule {

  public ReactNativeSentryAndroidModule(final ReactApplicationContext rctx) {
    super(rctx);
    Raven.init(rctx);
  }

  @Override
  public String getName() {
    return "ReactNativeSentryAndroid";
  }

  private static final Level getLevel(final String level) {
    if (level == null || level.isEmpty()) return Level.DEBUG;
    final String levelLowerCase = level.toLowerCase();
    if (levelLowerCase == "fatal") return Level.FATAL;
    else if (levelLowerCase == "error") return Level.ERROR;
    else if (levelLowerCase == "warning") return Level.WARNING;
    else if (levelLowerCase == "info") return Level.INFO;
    else if (levelLowerCase == "debug") return Level.DEBUG;
    else return Level.FATAL;
  }

  @ReactMethod
  @SuppressWarnings("unused")
  public final void capture(final ReadableMap event, final Promise promise) {
    final EventBuilder eb = new EventBuilder();
    eb.withMessage(event.getString("message"));
    eb.withLevel(getLevel(event.getString("level")));
    eb.withLogger(event.getString("class"));
    eb.withSentryInterface(new ExceptionInterface(new Exception(event.getString("stack"))));
    Raven.capture(eb.build());
    promise.resolve(null);
  }

  @ReactMethod
  @SuppressWarnings("unused")
  public final void forceCrash(final Promise promise) throws Error {
    throw new Error("just because");
  }
}

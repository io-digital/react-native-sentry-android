
# react-native-sentry-android

## Installation

Download and link with: `npm install --save react-native-sentry-android && rnpm link`
Add your Sentry DSN to your `AndroidManifest.xml` with: 
```xml
<meta-data android:name="com.getsentry.raven.android.DSN"
           android:value="https://publicKey:secretKey@host:port/1?options" />
```
## Usage

```javascript
import sentry from 'react-native-sentry-android';

// ...

try {
  somethingThatMightThrow()
} catch (e) {
  sentry.capture({
    message: e.toString(),
    level: 'fatal',
    stack: e.stack,
    class: 'MyComponent',
  }).then(function() {
    // apologise to the user for the failure
  })
}
```
# RemoteConfigExercise
A/B Testing using Firebase Remote Config &amp; Firebase Analytics - Exercise

This exercise will attempt to conduct A/B test on an "Add To Cart" UI component in either one of the following forms:
1. Button
2. FloatingActionButton

using Firebase Remote Config and Firebase Analytics.

## Steps
1. Download the source code from this repo.

2. Install [Firebase SDK](https://firebase.google.com/docs/android/setup).

3. Add [Firebase Remote Config](https://firebase.google.com/docs/remote-config/use-config-android) and [Firebase Analytics](https://firebase.google.com/docs/analytics/android/start/) to the app.

```groovy
compile 'com.google.firebase:firebase-core:11.2.2'
compile 'com.google.firebase:firebase-config:11.2.2'
```

4. Add
```xml
<?xml version="1.0" encoding="utf-8"?>
<defaultsMap>
    <entry>
        <key>useFAB</key>
        <value>true</value>
    </entry>
</defaultsMap>
```
into ```res/xml/remote_config_defaults.xml``` to create default value for this configuration.

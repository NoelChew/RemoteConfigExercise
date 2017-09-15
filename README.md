# RemoteConfigExercise
A/B Testing using Firebase Remote Config &amp; Firebase Analytics - Exercise

This exercise will attempt to conduct A/B test on an "Add To Cart" UI component in either one of the following forms:
1. Button
2. FloatingActionButton

using Firebase Remote Config and Firebase Analytics.

## Part 1: Implementing Firebase Remote Config
1. Download the source code from this repo.

2. Install [Firebase SDK](https://firebase.google.com/docs/android/setup).

3. Add [Firebase Remote Config](https://firebase.google.com/docs/remote-config/use-config-android) dependency into the app.

```groovy
compile 'com.google.firebase:firebase-core:11.2.2'
compile 'com.google.firebase:firebase-config:11.2.2'
```

4. Create default values for Remote Config locally.

Add
```xml
<?xml version="1.0" encoding="utf-8"?>
<defaultsMap>
    <entry>
        <key>useFAB</key>
        <value>true</value>
    </entry>
</defaultsMap>
```
into ```res/xml/remote_config_defaults.xml```.

5. Initialise Remote Config. Please refer to [guide](https://firebase.google.com/docs/remote-config/android).
- create Remote Config Singleton object
- set Remote Config settings
- set default values

6. Create parameter in [Firebase Console](https://console.firebase.google.com/u/0/).

Set the parameter key as "useFAB" with default value of true.

Then, click on "Add value for condition" and key in these conditions:

- "Use FAB for Add To Cart", with condition: User with random percentile <= 50%
- "Use Button for Add To Cart", with condition: User with random percentile > 50% AND User with random percentile <= 100%

Publish changes.

7. Get parameter values from the Remote Config object. 
Refer [here](https://firebase.google.com/docs/reference/android/com/google/firebase/remoteconfig/FirebaseRemoteConfig#getBoolean(java.lang.String)).

8. Update UI after fetching value from Remote Config.

For more guidance, please refer to this [quick start](https://firebase.google.com/docs/remote-config/android).


## Part 2: Tracking User Behaviour via Firebase Analytics

1. Add [Firebase Analytics](https://firebase.google.com/docs/analytics/android/start/) dependency to the app.

2. Enable debug mode for Firebase Analytics by sending this command to device
```
adb shell setprop debug.firebase.analytics.app com.noelchew.remoteconfigsample
```

Without this, events logged will be batched together over the period of approximately one hour and uploaded together.

For more information (and how to disable this), please refer to this [link](https://firebase.google.com/docs/analytics/debugview).

3. Send "clicked_add_to_cart" event with a custom boolean parameter called "usingFAB" to Firebase Analytics.

Refer to this [guide](https://firebase.google.com/docs/analytics/android/events).

4. Create user property called "usingFAB" in Firebase Analytics.

5. Uncomment setting of user property inside displayAddToCartUI() function.

Refer to this [guide]() to learn more about setting user property.

6. Run the app and check the events in DebugView (toggle StreamView if you can't find it).

7. To check the user behaviour for different UI, check the Events tab and add filter to show "usingFAB" User Property.

However, data will only be available after 24 hours.


## Done!
That's it! You have successfully implemented Firebase Remote Config and Firebase Analytics for a simple A/B testing. Note that in this exercise, there's not much sample data for you to tinker with in the Firebase Analytics console. You probably only have 1 sample size to play with. You can install the app into more devices if you wish to see more data (24 hours later).


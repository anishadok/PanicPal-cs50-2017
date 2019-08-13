Our final project is an Android application called PanicPal. Designed to be a safety companion app, PanicPal’s SMS-sending functionality is activated when the user shakes his or her Android device, and the change in acceleration exceeds a certain threshold.

To use the app, the user would have to download Android Studio because Panic Pal is not yet on the app store. Ensure that your phone’s developer options are enabled. After importing the app’s files into Android Studio, connect your Android device to your computer using a USB cord and run the project. The downloaded app should appear on your phone shortly. Make sure to enable location services on your phone when prompted.

The app is constantly keeping track of the user's motion and location using the phone's built-in GPS and accelerometer. When the user's acceleration suddenly changes (if,
for example, the user has been ambushed by an attacker and is displaying struggle through jerky movements), the phone will send an SMS text message of the URL containing the user's GPS coordinates to the specified emergency contact number. To use our application, therefore, the user would simply need to shake their Android device.

The user will be able to utilize the app as long as the app is running in the background. This provides the user with the convenience of shaking and activating the app when their device is on sleep mode, as well as when awake.

When navigating through our files in Android Studio, you can find our code in the following folders:
Go to “apps” -> “java” -> “com.example.michaelhuang.panicpalreal” -> “MainActivity”
          “apps” -> “java” -> “com.example.michaelhuang.panicpalreal” -> “ShakeEventListener”

Go to “manifests” -> “AndroidManifest.xml”
Go to “res” -> “layout” -> “activity_main.xml”

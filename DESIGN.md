Panic Pal, underneath the hood, has three main components that work together: Accelerometer detection, GPS location, and SMS Sending.
The accelerometer and the GPS run in the background whenever the app is running. Once the accelerometer reads a certain threshold (we calculate
the accelerometer reading by summing the squares of the accelerations in the x, y, and z direction), we craft a new SMS and send it off to the
designated number.

Originally, our team wanted to create an app that would send a text message to a designated person after the user presses and holds a button, but
we realized that that was not novel enough. There is an app on the market, called SafeTrek, which implements these exact features.
Thus, we decided to incorporate the use of the accelerometer in order to automatically attempt to detect danger.

We decided to use the device's built-in SMS function instead of outsourcing it to a third-party hosting app because the host we wanted to use, Twilio,
required the emergency contact to also be registered onto the website, which would have been inconvenient for the user. Another idea we were experimenting
with was having the app send a GET or POST request to a flask web server that we have running. The web server could then process the results further in some
code we write in the CS50 IDE, but we decided against it because it would be dependent on if we have our server up and also it would require that we send our
users' data over the internet, which could be insecure.

Our UI was designed to be as simple as possible, because most of the work happens in the background. The user does not need to stare at the app to use
it, because that could be harmful to the user's security if the user was walking through a dangerous area. Thus we use a toast notification to alert the user
when a text message has been sent, and Panic Pal only has one layout indicating that the app is active.

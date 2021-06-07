# Humber Capstone Project #


## About the project ##


For our final project, my group and I decided to make a Covid Security System that would help businesses reopen safely. Our main goal was to alert the owner of a business when an employee’s temperature was too high. With Covid-19, a fever is one of the first signs that you have contracted the virus, by taking the employees temperature when they upon arrival the owner can deny them access which could stop the spread within the business. 


## How it was created ##

There are two major parts to this project, the android application, and the hardware/sensors. The android application was created in the Android Studio IDE, it was coded in Java, and it uses firebase as the database for login information and temperature data. For the hardware and sensors, we used a raspberry pi 3 B+ as the powerhouse for the whole project and each sensor was coded in python. 


## How it works: Hardware ##

First, the motion sensor detects if someone is near the device. If someone is near it, there will be a signal sent to the ultrasonic sensor. Second, the employee scans their personal QR code that can be found on the app. Third, the ultrasonic sensor checks whether the person is within an acceptable range to get an accurate temperature reading. If the person is in the accepted range, there will be a green LED that illuminates, and a signal will be sent to the temperature sensor. If the person is not in the accepted range, there will be a red LED that illuminates, and no signal will be sent. Next, the temperature sensor sends the reading to the database, regardless of what it is. If the temperature reading is less than 38°C, a signal will be sent to the motor driver and the door will be unlocked, if the reading is greater than 38°C, the door will stay locked, and an alert will be sent to the admin account on the app. 


## How it works: Software ##

On the app there is a login and signup page where users can gain access to all the features of the app. Once logged in, there are multiple pages for the user to check out. These pages include, a QR code, a graph and list view of the users’ recent temperatures, as well as a personal log with times and other information. If the user has an admin account, there are a couple other pages they have access to. For instance, there is an overview of all their employees’ temperatures and an override button for the locking mechanism that either doesn't let anyone in or opens the lock in case of emergency.

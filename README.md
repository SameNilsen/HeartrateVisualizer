# HeartrateVisualizer
https://www.heartratevisualizer.com/
\
\
This project was my first encounter with web development. It is a dynamic website that uses springboot, Apache Tomcat, Apache Maven, java and standard html javascript css jquery etc. The app in itself is a tool to help visualize which intensity zones you have been in 
based on a collection of workouts extracted from Strava. To get these activities i used the strava api and specifically javastravav3api (https://github.com/danshannon/javastravav3api). Btw the code is in the master branch.
\
PS!!!!: The strava api is stupid and rude so it allows only a small number of requests per 15 minutes. So if someone spams the application or just more than a handful uses it at the same time it will crash :( In the future i might optimize the calls to the api
so that it uses as few unecessary calls as possible, but its still pretty useless and annoying.

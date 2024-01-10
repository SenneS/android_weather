# Android Weather Application
This is an Android Weather Application, it makes use of jetpack compose and libraries such as hilt and retrofit2. I tried out several patterns but that made the code a bit messy, if I had a lot more time I would probably rewrite it and use what I learned along the way to improve it.


## Main Features
- Weather for a given location
- Weather Locations can be saved to the home menu
- Units of Measurement can be changed in settings
- Weather Data is cached in a Room Database and used directly instead of pulling from the remote api
- Partially Adaptive Layout

## API
The application makes use of these APIs, they both have a daily limit of 10000 API calls.  
### Weather Forecast API
API which provides weather data for a provided location.  
- endpoint: https://api.open-meteo.com/v1/forecast
### Geocoding API
API which allows for locations to be searched on a name basis.  
- endpoint: https://geocoding-api.open-meteo.com/v1/search

## Sources
https://developer.android.com/develop/ui/views/launch/splash-screen
https://developer.android.com/develop/ui/views/launch/splash-screen/migrate
https://developer.android.com/jetpack/compose/navigation
https://developer.android.com/jetpack/compose/layouts/pager
https://uxwing.com/category/weather/
https://developer.android.com/jetpack/compose/components/app-bars
https://primasky.medium.com/android-jetpack-compose-clean-arhitecture-2022-8ea280c91fd5
https://medium.com/@ramg7/android-user-preferences-simplified-preferences-datastore-with-hilt-c08da9691667
https://www.youtube.com/watch?v=4gUeyNkGE3g
https://developer.android.com/codelabs/basic-android-kotlin-training-repository-pattern#0
https://itnext.io/android-data-repository-a-simple-pattern-that-we-often-misunderstand-6a6fb13b5a81
https://alexzh.com/jetpack-compose-pull-to-refresh/
https://stackoverflow.com/questions/74905271/how-to-iterate-over-an-element-node-list-on-android-jetpack-compose-ui-testss
https://proandroiddev.com/cleaner-way-to-interact-between-composable-and-viewmodel-in-jetpack-compose-14c8b3a74bbe
https://medium.com/androiddevelopers/datastore-and-dependency-injection-ea32b95704e3
Phind pair programming
https://codelabs.developers.google.com/jetpack-compose-adaptability#0
https://stackoverflow.com/questions/67023923/materialbuttontogglegroup-in-jetpack-compose
https://stackoverflow.com/questions/74606072/jetpack-compose-partial-or-open-side-border
https://medium.com/@stephenja/timestamps-with-android-room-f3fd57b48250
https://developer.android.com/codelabs/basic-android-kotlin-compose-viewmodel-and-state#0
https://www.youtube.com/watch?v=4D79It7Jnzg
https://www.c-sharpcorner.com/article/material-3-bottom-navigation-bar-in-jetpack-compose/
https://developersancho.medium.com/36ca40c6196b
https://blog.kotlin-academy.com/ui-testing-in-jetpack-compose-dbafee8953ab
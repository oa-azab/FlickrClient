Flickr Client
=============

Simple flickr client that fetches most interesting photos for the most recent day store them locally and display them to user.

Application consist of two screens

 - MainScreen
 - DetailScreen

## MainScreen
This screen is responsible for show the images in scrolling list and it consist of
 - Activity -> observes ui data from the ViewModel and update UI
   automatically
 - ViewModel -> holds a liveData of UI data (Images, refreshState)

I am using Room for local data storage and Retrofit to fetch data from network
	considering the localDatabase is my single source of truth and whenever all data consumed new data fetched from network and inserted into database.
	This is implement by retrieving LiveData<PagedList<Image>> from Room and attach a boundary callback to it so can request more pages when all data is consumed.

## DetailScreen
This is simple activity showing single image in fullscreen mode

## Libraries

 - Picasso -> for image fetching and caching
 - Android architecture components -> Room, Paging and LiveData
 - Retrofit + Gson -> networking
 - PhotoView -> pinch zooming for images

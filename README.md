# PopularMovies
Popular Movies App for Udacity Nanodegree

This branch reflects some changes I made to match the suggestions/recommendations from the Udacity reviewer.

These included:
Convert Data Model MovieInfo to implement Parcelable. Instead of referring to the static ArrayList in MainActivity, this now parcels the MovieOnfo Object in the intent for the DetailActivity/Fragment to use when populating the Detail View.
Correct use of UriBuilder for the API calls. Removed String concatenation (+) and used appendPath() instead.
Replaced the SettingsActivity in favour of a PreferenceFragment. Since Android 3.0+ a PreferenceFragment for displaying preferences is preferred over a PreferenceActivity. Had to add compile 'com.android.support:preference-v7:24.2.0' in the build.gradle dependencies.

# PopularMovies
Popular Movies App for Udacity Nanodegree

This branch reflects some changes I made to match the suggestions/recommendations from the Udacity reviewer.

These included:
<dl><dt>Convert Data Model MovieInfo to implement Parcelable.</dt><dd>Instead of referring to the static ArrayList in MainActivity, this now parcels the MovieOnfo Object in the intent for the DetailActivity/Fragment to use when populating the Detail View.</dd>
<dt>Correct use of UriBuilder for the API calls.</dt><dd>Removed String concatenation (+) and used appendPath() instead.</dd>
<dt>Replaced the SettingsActivity in favour of a PreferenceFragment.</dt><dd>Since Android 3.0+ a PreferenceFragment for displaying preferences is preferred over a PreferenceActivity. Had to add compile 'com.android.support:preference-v7:24.2.0' in the build.gradle dependencies.</dd>
<dt>Added Retrofit for the Calls to the MovieDb APIs.</dt><dd>Created Service and Interface, had to add a Model for the full response from MovieDb. Removed AsyncTask Class.</dd>
</dl>
Thanks to https://futurestud.io/blog/retrofit-getting-started-and-android-client for there help and examples to get Retrofit up and running.

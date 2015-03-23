List of features in development for the next release, and a list of features included in previous releases.
# Slotted 0.4 (testing/documenting) #

  * Code Splitting - Provide simple code splitting on Activities or Groups of Activities

  * SlottedDialog - Provides the ability to show Activities inside a Dialog with navigation.  The dialog is independent of the main window, but can share the same Activities.

# Slotted 0.3 (Released Jan 21, 2015) #

  * Activity Caching - The ability to cache child activities as long a the parent activity is around.  For example, if you have an EmailActivity, EmailListActivity, and EmailMessageActivity, you could tell EmailActivity that it should cache EmailListActivity, then if you navigate to EmailMessageActivity, the EmailListActivity will still be alive and accessible through getCurrentActivity().  Calling History.back() or goTo(EmailListPlace), will just display original EmailListActivity, which has all the visual state like scroll positions.

  * Multi Parent - The ability to have a Place to have many different parents.  This solves the problem where a a group of Places/Activities need to be shown in two different locations with lots of navigation between child Places.  Before you could create another set of Places that use the same Activity, but the Activity would have to have conditional goTo() calls.  Now there is only on set of Places, so there is no need to have conditional goTo().

  * AutoTokenizer support for null Strings vs empty Strings.

  * AutoTokenizer support for Java Primitive Wrapper objects.

  * Ability to get the Place's URL prefix

  * ContainerPlace which is parent Place with no visual effects.

  * Fixed when mayStop() threw an exception hanging Slotted.

  * Fixed Cmd/Ctrl open new window to only when mouse is clicked

  * Fixed goTo(parent) will display default places always to provide reproducible navigations.

  * Improved Exception recover while navigating.


# Slotted 0.2 (Released Jan 3, 2014) #

  * Javadoc Improvements

  * Register API change - This changes the registerPlace() from taking a SlottedPlace instance to taking a SlottedPlace class.  This also fixes a bug where mayStop() canceling a navigation cause the existing Place parameters to change.

  * ReloadAll Places / onRefresh()  - Release 0.1 was coded to refresh all Activities on every goTo(), which called onStop() and start() for Places that didn't change.  0.2 switches that functionality to only calling onStop() on Activities where the old Place not equal new Place.   If they are equal, onRefresh() will be called notifying the Activity that a goTo() was performed and may have changed global parameters.

  * Delay View Loading - This feature allows you to keep the existing view visible while the data for the next requested Place is loading.   The best example of this is Gmail.  If you are showing your "inbox" and click to see "sent" email list, Gmail keeps the "inbox" display while showing a loading... popup.  Once the date has been retrieved, the "sent" view is then shown.  This is handled by setLoadingStarted() and setLoadingCompleted(), and only changes the view once all Activities are done loading.

  * AutoHistoryMapper - This automatically registers all the SlottedPlaces found in the module.  This removes a lot of boilerplate code for registering places.

  * Tokenized Parameters - This allows you to use A&P PlaceTokenizer to add parameters to the URL like this "#Place1/Place2:placetoken/Place3?global=param".  It is possible to mix both tokenized parameter values and global values.  TokenizerUtil was also added to help tokenizing multiple parameters.

  * AutoPlaceTokenizer - This would automatically generate a tokenizer for place variable marked with @TokenParameter, or automatically add variables marked with @GlobalParameter to the PlaceParameters.


# Slotted 0.1 #

This was our initial release that provided basic nesting functionality for Activities and Places.
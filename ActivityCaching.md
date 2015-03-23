# Activity Cache EXPERIMENTAL (v0.3) #

Activity Cache allows for Slots to cache the DOM content of an Activity.  The Activity DOM elements don't detach from the page, but are kept with a CSS (`display: none`).   The Activity is moved into a backgrounded state, and when the Place is navigated to again, the CSS (`display: none`) is removed and the view is shown with visual state intact.

## Why EXPERIMENTAL? ##

This feature was created without a specific problem to solve.  It has been used in a theoretical conditions, but hasn't been fully tested in real world situations.   We highly encourage people to use this in real world situations with the knowledge that some features might be lacking.  We will quickly provide patches for unforeseen situations.

## Setup ##

Add the `@ActivityCache` annotation to any existing Place.
```
@CacheActivities({MyChildPlace.class})
public class MyParentPlace extends SlottedPlace {
...
}
```

The `@ActivityCache` takes a list of Place classes that need to be located in the hierarchy below the annotated Place.

## Lifecycle ##

To describe the lifecycle of backgrounded Places/Activities, only the Places will be referenced.  The actual lifecycle method will be called on the Activity associated with the Place.

Two new life cycle methods have been added: `mayBackground()` and `onBackground()`.  In the above example, when `MyChildPlace` navigated to, the normal lifecycle `start()` is called, and the `MyParentPlace` is also created.  If we navigate to a new Place that isn't a child of `MyParentPlace`, then the normal lifecycle `mayStop()` and `onStop()` are called.

But If instead we navigate to `AnotherChildPlace` which is a child of `MyParentPlace`, then `MyChildPlace` lifecycle will change to `mayBackground()`, `onBackground()` and `onRefresh()` when restored.   As long as `MyParentPlace` isn't stopped, `MyChildPlace` will remain backgrounded.  When `MyParentPlace` is stopped then `onStop` will also be called on backgrounded Places.
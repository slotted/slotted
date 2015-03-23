# Delayed Load #

## What should be displayed while my data is loading? ##

In the normal Slotted and GWT Activities & Places flow, when a navigation via URL or `goTo()` call, the `start()` is passed an AcceptsOneWidget container widget which gets filled with the new UI for the Activity.  Often the new UI isn't ready to be displayed until data arrives from the server.

It is possible to create a UI that has a bunch of widgets that provide a nice loading experience, or you can delay the displaying of the new UI until the all the data has been loaded.  Delayed Load was designed to help with the second option of delaying the UI until data arrives.

Gmail is a good example of delaying the new UI until data is loaded.  If you are showing your "inbox" and click to see "sent" email list, Gmail keeps the "inbox" display while showing a loading... popup.  Once the data arrives, the "sent" messages view is shown.

## Existing Solution ##

It is possible to get this effect by managing when you `add/set` the new UI's widget in the AcceptsOneWidget container widget.  A couple of class level variables can be used to hold the AcceptsOneWidget and the new UI's base widget.  When the data arrives, the UI's base widget is added to the AcceptsOneWidget.

This works for simple Activities with a little extra boiler plate code, but is starts getting complicated when you have nested Activities.  How do you manage when to `add/set` all the base widgets into the different AcceptsOneWidgets?  Delayed Load was created to manage that complexity.

## Delayed Load Solution ##

Slotted provides two methods in SlottedActivity to simplify the process: `setLoadingStarted()` and `setLoadingComplete()`.  When `setLoadingStarted()` is called, Slotted knows not to display new UI until `setLoadingComplete()` is called.  Slotted does this for nested Activities also, which means Slotted won't display any Activity until all Activities have completed loading.

It is possible to mix and match loading Activities.  If an Activity never a calls `setLoadingStarted()`, then it is considered complete once the `start()` completes.  Activities that don't call `setLoadingStarted()` still get their new UI's delayed until the Activities that did call `setLoadingStarted()` have called `setLoadingComplete()`.

### Multiple Async Data Calls ###

Slotted also provides the ability for handle multiple data calls.  When calling `setLoadingStarted()`, pass a series of labels for each data element that you want to wait for.  Then for each data arrival, called the `setLoadingComplete()` with the label.

```
setLoadingStarted(FolderData.class, "messages");
```

This call tells Slotted to delay the new UI until both these methods are called.

```
setLoadingComplete(FolderData.class);
//and
setLoadingComplete("messages");
```

## Life Cycle ##

Delayed Load is focused around `start()`.  For an Activity to be considered loading, the `setLoadingStarted()` must be called during the processing of `start()`.   When `setLoadingStarted()` is called, a `LoadingEvent` is fired, even if the `setLoadingStarted()` is called outside of the `start()`.   `LoadingEvent` can be fired multiple times during a single navigation.

Once all the `setLoadingComplete()` calls are done, a `LoadingEvent` is fired once the Activities are displayed.  If another call is made to `setLoadingComplete()`, after all the Activities are shown, another `LoadingEvent` will be fired.  The extra `LoadingEvent`s are fired to allow `setLoadingStarted()` and `setLoadingComplete()` to handle loading messages outside of `start()` processing.

The `onLoadComplete()` of a SlottedActivity will be called once all the Activities' UIs are displayed.  This method is good for doing any code that requires widgets to be attached to the DOM to work correctly.

If a navigation happens while a Activity is loading, the `onCancel()` will be called just as if the UI was never `add/set` in the AcceptsOneWidget.  Remember that SlottedActivity's `onCancel()` default implementation calls `onStop()`, so most cases implementing `onStop()` should be sufficient.
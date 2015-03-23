

# Slotted #

The Slotted framework is an extension of GWT Activities and Places (A&P), which adds the ability to nest Activities inside other Activities. Slotted has retained much of the A&P API, and has only changed where needed to support nesting.  This allows for easy migration of existing A&P projects.

Just like Activities and Places, Slotted is a history management framework that allows you to create bookmarkable URLs within your application, thus allowing the browser's back button and bookmarks to work as users expect.  It maybe used in conjunction with MVP development, but is not strictly speaking an MVP framework.

This documentation assumes that you completely understand GWT Activities and Places.  If you don't, please read GWT's documentation before continuing: https://developers.google.com/web-toolkit/doc/latest/DevGuideMvpActivitiesAndPlaces

## Why Nesting? ##

GWT's Activities are great, and provide a great flow for page initialization and page navigation.  Now imagine a page with a TabPanel.  Wouldn't it be great if each Tab was setup like an Activity?  Wouldn't it be great to have delayed initialization and navigation checks?

This is why Slotted was created.  Slotted allows you to organize your entire site into hierarchy of Activities.  You no longer need to manage complex Views/Activities yourself.  You can divide these complex pages into many Activities, and allow Slotted to manage the nesting structure and provide nice bookmarkable URLs for these complex views.

## What's Different ##

To provide the ability of nesting, Slotted needed to add the concept of a `Slot`.  A `Slot` is a Java object that represents a location where content can be displayed.  It isn't a GWT widget, but more akin to a Place, and combined with `SlottedPlaces` defines the nesting hierarchy and URL history token.

It also created `SlottedPlace` and `SlottedActivity`, which add the understanding of Slots to Places and Activities.  Throughout this document, SlottedPlace/Place and SlottedActivity/Activity are used interchangeably with the understanding that if Slot information is needed, SlottedPlace and SlottedActivity must be used.  If Slots are not being used, the standard Place and Activity can be used.

Slotted also added generators to help reduce some of the boiler plate code associated with Places and HistoryMappers.  See [AutoTokenizer/AutoHistoryMapper](AutoTokenizerAutoHistoryMapper.md) for more details.

## Migrate Activities and Places to Slotted ##

Please see our migration wiki [MigrateGWTActivitiesPlaces](MigrateGWTActivitiesPlaces.md) for a full description of how to migrate an existing A&P project to Slotted.

Be reassured that you only need to change a few lines of code to get an A&P project running under the Slotted framework.  You can then start adding new `SlottedActivities` without effecting existing code, or you can migrate a couple of Activities to Slots, while leaving the rest alone.

## Simple Example ##

We are going to build a simple example to demonstrate how Slots work in the Slotted framework.

### HomePlace.java ###
```
public class HomePlace extends SlottedPlace {
    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }

    @Override
    public SlottedActivity getActivity() {
        return new HomeActivity();
    }
}
```

`SlottedPlace` requires you to implement three methods: `getParentSlot()`, `getChildSlots()`, and `getActivity()`.

`getParentSlot()` returns the Slot in which this place will be displayed.  For this example, the `HomePlace` will be displayed in the `RootSlot`, which is the base for all hierarchies in the framework.

`getChildSlots()` returns all the Slots that will be under this place.  In this example, we don't have any child Slots, so a null is returned.  It is possible to return an empty array also.

`getActivity()` returns the Activitiy object that will be used for this place.  Slotted diverged from the A&P `ActivityMapper`, because we felt that there should be one place to find the information describing Place and its role in the display hierarchy.  It also removes the large switch statement, which we feel is bad practice.  If you prefer the old `ActivityMapper` model, you can still use it, by extending `MSlottedPlace`, which stands for Mapped SlottedPlace, and passing the `ActivityMapper` to the `SlottedController`.

In this example, there really is no Slot information, so it would be completely fine to use `Place` instead of `SlottedPlace`, except by using `Place`, you must use the ActivityMapper model.

### ParentPlace ###

We will start showing a simple hierarchy, starting with the `ParentPlace`.

```
public class ParentPlace extends SlottedPlace {
    public static final Slot SLOT = new Slot(new ParentPlace(), new NestedPlace());

    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {SLOT};
    }

    @Override
    public SlottedActivity getActivity() {
        return new ParentActivity();
    }
}
```

To define the hierarchy, we need to introduce a new object: `Slot`.  `Slot` is a light weight object that defines two attributes.  The first is the Parent Place this slot is displayed inside, and second is the default Place that should be displayed if none is provided.  We return the `Slot` in the `getChildSlots`.

For example, if you tell `SlottedController` to `goTo()` the `ParentPlace`, it will display the `ParentActivity` in the `RootSlot`, but it will also display the `NestedPlace` inside the `ParentActivity`.  You could also call `goTo()`, and pass the `ParentSlot` along with different Place that should be displayed instead of `NestedPlace`

### ParentActivity ###

Now lets look at the differences between `Activity` and `SlottedActivity`.

```
public class ParentActivity extends SlottedActivity {
    private SimplePanel slotPNL;

    @Override public void start(AcceptsOneWidget panel) {
        VerticalPanel mainPNL = new VerticalPanel();
        panel.setWidget(mainPNL);

        mainPNL.add(new HTML("Parent View"));

        slotPNL = new SimplePanel();
        mainPNL.add(slotPNL);
    }

    @Override public AcceptsOneWidget getChildSlotDisplay(Slot slot) {
        if (ParentPlace.SLOT == slot) {
            return slotPNL;
        }
        return null;
    }
```

To start with, the `start()` method only has one parameter.  This was done to remove the dependency to the legacy `EventBus`.  `EventBus` can now be accessed via the `getEventBus()` method which uses the web bindery version.

The second difference is the `getChildSlotDisplay()`, which is called by the `SlottedController` to get the `AcceptsOneWidget` that will be used to display the child slot's content.  The `AcceptsOneWidget` that is returned will be passed to the child `Activity.start()`.  In this example, there is only one `Slot` defined in the `ParentPlace`, but it is still good practice check the slot with an if statement .  If there were 2 or more slots, just add another if statement for each slot.

In this example we aren't using an MVP model for code brevity, but we could easily add a View to the `ParentActivity` to get a true MVP.  Please take a look at the [MigrateGWTActivitiesPlaces](MigrateGWTActivitiesPlaces.md) where we port GWT's MVP example to Slotted.

### NestedPlace and  NestedActivity ###

`NestedPlace` is the middle Place in the hierarchy, and defines the `NestedLevelTwo` as its default child place.

```
public class NestedPlace extends SlottedPlace {
    public static final Slot SLOT = new Slot(new NestedPlace(), new NestedLevelTwoPlace());

    @Override public Slot getParentSlot() {
        return ParentPlace.SLOT;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {SLOT};
    }

    @Override
    public SlottedActivity getActivity() {
        return new NestedActivity();
    }
}
```

`NestedActivity` is just like the `ParentActivity`, and again for code brevity we skip the View, but that can easily be added.

```
public class NestedActivity extends SlottedActivity {
    private SimplePanel slotPNL;

    @Override public void start(AcceptsOneWidget panel) {
        VerticalPanel mainPNL = new VerticalPanel();
        panel.setWidget(mainPNL);

        mainPNL.add(new HTML("Nested View"));

        slotPNL = new SimplePanel();
        mainPNL.add(slotPNL);
    }

    @Override public AcceptsOneWidget getChildSlotDisplay(Slot slot) {
        if (NestedPlace.SLOT == slot) {
            return slotPNL;
        }
        return null;
    }
}
```

### NestedLevelTwoPlace and NestedLevelTwoActivity ###

For the final level in the hierarchy, we show how you can mix a `SlottedPlace` with a standard `Activity`.

```
public class NestedLevelTwoPlace extends SlottedPlace {
    @Override public Slot getParentSlot() {
        return NestedPlace.SLOT;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }

    @Overridepublic Activity getActivity() {
        return new NestedLevelTwoActivity();
    }
}
```

`NestedLevelTwoActivity` extends the standard `AbstactActivity` that is used in A&P.  You can also see that you must implement the start method with two parameters using the legacy `EventBus`.

```
public class NestedLevelTwoActivity extends AbstractActivity {
    @Override public void start(AcceptsOneWidget panel, com.google.gwt.event.shared.EventBus eventBus) {
        panel.setWidget(new HTML("Nested Level Two View"));
    }
}
```


### Putting It All Together ###

Here's how all the pieces come together in `onModuleLoad()`:

```
public class Simple implements EntryPoint {
    public static SlottedController slottedController;

    public void onModuleLoad() {
        HistoryMapper historyMapper = GWT.create(AutoHistoryMapper.class);
        slottedController = new SlottedController(historyMapper, new SlottedEventBus());
        slottedController.setDefaultPlace(new HomePlace());

        SimplePanel rootDisplay = new SimplePanel();
        RootPanel.get().add(rootDisplay);
        slottedController.setDisplay(rootDisplay);
    }
}
```

### How It Works ###

Slotted manages Activities and any nested child Activities with the context of one container widget.  Slotted manages all Place changes by first notifying all active Activities that a new Place has been requested.  If all the Activities allow the Place change (Activity.onMayStop() returns null) or the user allows it (by clicking OK in the confirmation dialog), then Slotted discards the current hierarchy of Activities and creates a new hierarchy based on the requested Place or Places.

Slotted creates all the required Activities by taking the new requested Place, and creating the entire hierarchy as defined by the Places and Slots.  It will create all the parent Activities and fill all the Slots with their default Activities.  If the default Activities are not wanted, it is possible to pass of list of Activities that should be used instead.

Sometimes a navigation request will only change a child Place/Activity, but the parent will remain the same.  In this case, Slotted will not destroy the parent Activity and recreate it, but instead will call the `onRefresh()` method of the parent SlottedActivity.  This informs the Activity a navigation happened, but that it was not changed.  Most of the time this is not needed, but there might be a case where the parent wants to change its display based on what child Place is being shown.

### How to Navigate ###

To navigate to a new Place, it is as easy calling `SlottedController.goTo()` and pass newly constructed Place or a list of Places if you don't want to show the default Places in the rest of the hierarchy.

Most places require parameters to determine how to display the page with the correct data.   To add a parameter to a Place just add a class property and annotate it with either @TokenizerParameter like this:
```
public class ParentPlace extends SlottedPlace {
    public static final Slot SLOT = new Slot(new ParentPlace(), new NestedPlace());

    @TokenizerParameter
    public String myParameter; 

    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {SLOT};
    }

    @Override
    public SlottedActivity getActivity() {
        return new ParentActivity();
    }
}
```
Slotted will automatically register the Place for navigation, and creates a tokenizer to add the String value of `myParameter` to the History token.  For more information on this see [AutoTokenizer/AutoHistoryMapper](AutoTokenizerAutoHistoryMapper.md).

Sometimes a parameter is needed for more then one Place.  You could use a global parameter, but a better way is to pick a Place that is the owner of that parameter.  Any Activity can access that parameter by calling `getCurrentPlace(Class)` to get the Place that owns the data.  An Activity can get a Place any where in the hierarchy, and is not limited the Place that it is associated with.  Using this method prevents conflicts of global parameter being set by two different Places with different values, but you can easily do global parameters with the @GlobalParameter annotation.
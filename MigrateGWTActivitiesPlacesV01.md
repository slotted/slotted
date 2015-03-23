# Migrate GWT Activities and Places #

This document is broken up into 2 parts.  Part 1 shows the minimum changes needed to make an existing site run in Slotted.  Part 2 shows how to extend that site by adding a new hierarchy, and migrating an existing Activity and Place into the hierarchy.

To illustrate how to migrate GWT Activities and Places (A&P) to Slotted we will use the HelloMVP example found in GWT's documentation: https://developers.google.com/web-toolkit/doc/latest/DevGuideMvpActivitiesAndPlaces

## Part 1 ##

### Create AppHistoryMapper ###

The first step is creating the `AppHistoryMapper`, which extends the Slotted `HistoryMapper`.

```
public class AppHistoryMapper extends HistoryMapper {
    @Override protected void init() {
    }
}
```

For the initial migration, no Places or SlottedPlaces are registered, because the `AppPlaceHistoryMapper` will be used to process the history token.  The XXX  section shows how to migrate existing Places to `AppHistoryMapper`, but for now we will leave it blank.

### Update ClientFactory and ClientFactoryImpl ###

In ClientFactory.java change this line:
```
PlaceController getPlaceController();
```
to:
```
SlottedController getPlaceController();
```

And in ClientFactoryImpl.java change this line:
```
private final PlaceController placeController = new PlaceController(eventBus);
```
to:
```
    private final AppHistoryMapper appHistoryMapper = new AppHistoryMapper();
    private final SlottedController placeController = new SlottedController(appHistoryMapper, eventBus);
```

The accessor method `getPlaceController()` will also need to be changed to return `SlottedController`.

### Update HelloMVP ###
In the HelloMVP.java, change the `onModuleLoad()` from:
```
    public void onModuleLoad() {
        ClientFactory clientFactory = GWT.create(ClientFactory.class);
        EventBus eventBus = clientFactory.getEventBus();
        PlaceController placeController = clientFactory.getPlaceController();

        // Start ActivityManager for the main widget with our ActivityMapper
        ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
        ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
        activityManager.setDisplay(appWidget);

        // Start PlaceHistoryHandler with our PlaceHistoryMapper
        AppPlaceHistoryMapper historyMapper= GWT.create(AppPlaceHistoryMapper.class);
        PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
        historyHandler.register(placeController, eventBus, defaultPlace);

        RootPanel.get().add(appWidget);
        // Goes to the place represented on URL else default place
        historyHandler.handleCurrentHistory();
    }
```

to:
```
    public void onModuleLoad() {
        ClientFactory clientFactory = GWT.create(ClientFactory.class);
        EventBus eventBus = clientFactory.getEventBus();
        SlottedController slottedController = clientFactory.getPlaceController();

        // Start ActivityManager for the main widget with our ActivityMapper
        ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
        AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
        slottedController.setLegacyMappers(activityMapper, historyMapper, defaultPlace);

        RootPanel.get().add(appWidget);
        // Goes to the place represented on URL else default place
        slottedController.setDisplay(appWidget);
    }
```

When migrating to Slotted, the `ActivityManager` and `PlaceHistoryHandler` are no longer used.  The functionality of these two classes is handled by the `SlottedController` and the `HistoryMapper`.  The `ActivityMapper`, `AppPlaceHistoryMapper`, and the default Place are passed to `slottedController.setLegacyMappers()`, which allows `SlottedController` to handling the existing site without any changes.  Finally, the `slottedController.setDisplay()` automatically processes the current history token to fill the display container widget.

## Part 2 ##
The changes described above in Part 1 should be simple enough to change for any existing A&P site.  There is no need to make any changes to existing Acivities or Places, because Slotted provides the same funtionality as the A&P framework.

We now migrate the existing places to `SlottedPlace`, and then create a new hierarchy with those Places.

### Migrate HelloPlace and GoodbyePlace ###
In order to migrate a Place to Slotted, it needs to extend `SlottedPlace` and implement a couple new methods defining how the place fits in the hierarchy.  For this example, we extend `MappedSlottedPlace`, so that Slotted knows to still
use the `AppActivityMapper` to create the Activity.

```
public class HelloPlace extends MappedSlottedPlace {
    public HelloPlace(String token) {
        setParameter("helloName", token);
    }

    public String getHelloName() {
        return getParameter("helloName");
    }

    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }
}
```

The name paramter has also been moved to a Slotted parameter which will appear at the end of the URL as a parameter list: "?helloName=World!".  The same thing needs to be done for `GoodbyePlace`.

### Add Places to AppHistoryMapper ###
Now that `HelloPlace` and `GoodbyePlace` are `SlottedPlaces`, we need to add them to `AppHistoryMapper`.

```
public class AppHistoryMapper extends HistoryMapper {
    @Override protected void init() {
        registerDefaultPlace(new HelloPlace("World!"));
        registerPlace(new GoodbyePlace(""), "gb");
    }
}
```

The call to `registerDefaultPlace()` overrided the default Place that was passed to `setLegacyMappers()`.  The `HelloPlace` URL token will appear as "Hello", because Slotted uses the simple class name with "Place" stripped off.  If "Place" is not at the end of the class name, then the name is just used.  If you want to use a different URL token, then you can just pass the a different URL token like we did with `GoodbyePlace`.

### Cleaning Up ###

Since we migrated all Places to Slotted, we can clean up the HelloMVP of some unused code.

```
public class HelloMVP implements EntryPoint {
    private SimplePanel appWidget = new SimplePanel();

    public void onModuleLoad() {
        ClientFactory clientFactory = GWT.create(ClientFactory.class);
        SlottedController slottedController = clientFactory.getPlaceController();

        // Start ActivityManager for the main widget with our ActivityMapper
        ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
        slottedController.setActivityMapper(activityMapper);

        RootPanel.get().add(appWidget);
        // Goes to the place represented on URL else default place
        slottedController.setDisplay(appWidget);
    }
}
```

We removed the creation of the `defaultPlace` and `AppPlaceHistoryMapper`.  `AppPlaceHistoryMapper` needs to be deleted, because it no longer compiles, because the `Tokenizer`s were removed.  We also switched the call to `setLegacyMappers()` to `setActivityMapper()`.

### Create a Base for the Hierarchy ###

Below is an implementation of the Base location with a Slot implemented as an MVP.  For more information on how Slot hierarchies are created, please refer to the Overview wiki.

```
public class BasePlace extends MappedSlottedPlace {
    public static final Slot SLOT = new Slot(new BasePlace(), new HelloPlace("Base!"));

    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {SLOT};
    }
}
```

```
public class BaseActivity extends SlottedActivity {
    // Used to obtain views
    private ClientFactory clientFactory;
    private BaseView baseView;

    public BaseActivity(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    /**
     * Invoked by the SlottedController to start a new Activity
     */
    @Override public void start(AcceptsOneWidget containerWidget) {
        baseView = clientFactory.getBaseView();
        containerWidget.setWidget(baseView.asWidget());
    }

    @Override public void setChildSlotDisplay(Slot slot) {
        slot.setDisplay(baseView.getSlotDisplay());
    }
}
```

```
public interface BaseView extends IsWidget {
    AcceptsOneWidget getSlotDisplay();
}
```

```
public class BaseViewImpl extends Composite implements BaseView {
    interface TheUiBinder extends UiBinder<Widget, BaseViewImpl> {}
    private static TheUiBinder uiBinder = GWT.create(TheUiBinder.class);
    interface HelloViewImplUiBinder extends UiBinder {}

    @UiField SimplePanel slotPanel;

    public BaseViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override public AcceptsOneWidget getSlotDisplay() {
        return slotPanel;
    }
}
```

```
<!DOCTYPE ui:UiBinder SYSTEM "http://dl.google.com/gwt/DTD/xhtml.ent">
<ui:UiBinder xmlns:ui="urn:ui:com.google.gwt.uibinder"
                         xmlns:g="urn:import:com.google.gwt.user.client.ui">
        <g:HTMLPanel>
            Here is the slot:
            <g:SimplePanel ui:field="slotPanel"/>
        </g:HTMLPanel>
</ui:UiBinder>
```

### Add BaseActivity to AppActivityMapper ###

Add creation of the `BaseActivity` to legacy `AppActivityMapper`:
```
    @Override
    public Activity getActivity(Place place) {
        if (place instanceof HelloPlace)
            return new HelloActivity((HelloPlace) place, clientFactory);
        else if (place instanceof GoodbyePlace)
            return new GoodbyeActivity((GoodbyePlace) place, clientFactory);
        else if (place instanceof BasePlace)
            return new BaseActivity(clientFactory);
        return null;
    }
```

The `ClientFactory` design works best in the `ActivityMapper` construct, which is why we are continuing to use the `ActivityMapper` to create the `BaseActivity`.  To see alternative methods see the [Overview](Overview.md) and [GinIntegration](GinIntegration.md) wikis.

### Update HelloPlace and GoodbyePlace ###

To make `HelloPlace` display in the `BasePlace`'s Slot, change `getParentSlot()` in `HelloPlace.java` to return `BasePlace.SLOT`:
```
    @Override public Slot getParentSlot() {
        return BasePlace.SLOT;
    }
```

Do the same for `GoodbyePlace` to have it display in the `BasePlace` `Slot` or leave it alone still display it in the Root Slot.

### Register BasePlace ###

Register the `BasePlace` in the `AppHistoryMapper`:
```
public class AppHistoryMapper extends HistoryMapper {
    @Override protected void init() {
        registerDefaultPlace(new HelloPlace("World!"));
        registerPlace(new GoodbyePlace(""), "gb");
        registerPlace(new BasePlace());
    }
}
```

NOTE: If you noticed, `BasePlace` defines the default Place for it's Slot with the name "Base!" instead of the "World!" defined when registering the `HelloPlace`.  If `BasePlace` is registered as the Default Place, you will see that the name will display "Base!" instead of "World!".
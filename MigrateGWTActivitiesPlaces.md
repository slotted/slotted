

# Migrate GWT Activities and Places #

This document is broken up into 2 parts.  Part 1 shows the minimum changes needed to make an existing site run in Slotted.  Part 2 shows how to extend that site by adding a new hierarchy, and migrating an existing Activity and Place into the hierarchy.

To illustrate how to migrate GWT Activities and Places (A&P) to Slotted we will use the HelloMVP example found in GWT's documentation: https://developers.google.com/web-toolkit/doc/latest/DevGuideMvpActivitiesAndPlaces

## Part 1 ##

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
    private final AutoHistoryMapper autoHistoryMapper = GWT.create(AutoHistoryMapper.class);
    private final SlottedController placeController = new SlottedController(autoHistoryMapper, eventBus);
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

### Port Complete ###
That is all that needs to be done to migrate a GWT Activities and Places to the Slotted framework.  Part 2 will describe how to change the existing GWT Activities and Places to take advantage of some of Slotted's features.

## Part 2 ##
We are going to add a BasePlace and BaseActivity, and then change HelloPlace and GoodbyePlace to be nested under BasePlace.  We will also update HelloPlace to use the AutoHistoryMapper and AutoTokenizer to remove some of the boiler plate code.

### Create a Base for the Hierarchy ###

Below is an implementation of the Base location with a Slot implemented as an MVP.  For more information on how Slot hierarchies are created, please refer to the [Overview](Overview.md) wiki.

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

    @Override public AcceptsOneWidget getChildSlotDisplay(Slot slot) {
        if (BasePlace.SLOT == slot) {
            return baseView.getSlotDisplay();
        }
        return null;
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

### Migrate HelloPlace and GoodbyePlace ###
Before HelloPlace and GoodbyePlace can be used in a hierarchy, they need to be migrated to a SlottedPlace, which has new methods defining how the Place fits in the hierarchy.  For this example, we extend `MappedSlottedPlace`, so that Slotted knows to still use the `AppActivityMapper` to create the Activity.

```
@Prefix("hp")
public class HelloPlace extends MappedSlottedPlace {
    @TokenizerParameter
    private String helloName;

    private HelloPlace() {
    }

    public HelloPlace(String token) {
        this.helloName = token;
    }

    public String getHelloName() {
        return helloName;
    }

    @Override public Slot getParentSlot() {
        return BasePlace.SLOT;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }
}
```

`getParentSlot()` method tells Slotted that the HelloPlace will new be displayed in the `BasePlace.SLOT`.  `getChildSlots()` returns a null telling Slotted that HelloPlace has no nested Places.  We also added a private default constructor which is needed for AutoHistoryMapper.   We also added the @TokenizerParameter which allows us to remove the Tokenizer and use an AutoTokenizer.  For more information, read the [AutoTokenizerAutoHistoryMapper](AutoTokenizerAutoHistoryMapper.md) wiki.

Also notice that the @Prefix was added to the class to change the name that is used in the History token.  If the Tokenizer class wasn't removed, @Prefix could be placed on the Tokenizer.  It is perfectly fine to leave the existing Tokenizer, and might be easier when porting a lot of Places.

GoodbyePlace also needs to be migrated for the next step to work properly.   In the example ([here](https://code.google.com/p/slotted/source/browse/examples/GwtActivitiesPlaces2/src/com/googlecode/slotted/gap2_example/client/GoodbyePlace.java)), the tokenizer is kept and the @Prefix is attached to the Tokenizer.

### Cleaning Up ###

Since we migrated all Places to Slotted, we can clean up the HelloMVP of some unused code.

```
public class HelloMVP implements EntryPoint {
    private Place defaultPlace = new HelloPlace("World!");
    private SimplePanel appWidget = new SimplePanel();

    public void onModuleLoad() {
        ClientFactory clientFactory = GWT.create(ClientFactory.class);
        SlottedController slottedController = clientFactory.getPlaceController();

        // Start ActivityManager for the main widget with our ActivityMapper
        ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
        slottedController.setActivityMapper(activityMapper);

        slottedController.setDefaultPlace(defaultPlace);

        RootPanel.get().add(appWidget);
        // Goes to the place represented on URL else default place
        slottedController.setDisplay(appWidget);
    }
}
```

We removed the creation of the `AppPlaceHistoryMapper`.  `AppPlaceHistoryMapper` needs to be deleted, because it no longer compiles, because the `Tokenizer`s were removed.  We also switched the call to `setLegacyMappers()` to `setActivityMapper()`, and added the call to `setDefaultPlace()`, because `setLegacyMappers()` was setting the default Place.

### Done ###
That is all that needs to be done to add a simple hierarchy to an existing GWT Activities and Places.  Remember that Slotted was designed to allow existing sites to migrate in parts, or just leave the existing code and GWT A&P, and then add new functionality as Slotted Places and Activities to take advantage of all the Slotted features.  See [Overview](Overview.md) for a description of those features.
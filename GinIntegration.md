

# Gin Integration #

This wiki describes one way to integrate with GIN (GWT INjection - http://code.google.com/p/google-gin/).  There maybe better ways to integrate, so please share your ideas.

## Design Factors ##

Places were intended to be lightweight  objects that were constructed with "`new`" keyword.  We were hoping that the GIN implementation would allow us to use the `injectMembers()` method, but GIN doesn't allow that to be generic, which means that you have to create an `injectMembers()` for each class Place.

Instead, we decide add a method for each Activity to the `Ginjector`.  It ended up being the same lines of code as the `injectMembers()`, but it removed GIN dependencies from Slotted.  This integration point also allows SlottedPlace to be a complete definition by having the Slot definitions, Parent/Child Places, and Activity association is one place.

## Example ##

We will again use the GWT Activites and Places example to demonstrate.  We will be extend the [MigrateGWTActivitiesPlaces](MigrateGWTActivitiesPlaces.md) to show how to move that to a GIN version.  You can find the complete source code [here](http://code.google.com/p/slotted/source/browse/#git%2Fexamples%2FGin%2Fsrc%2Fcom%2Fgooglecode%2Fslotted%2Fgin_example%2Fclient).

### GinModule and Ginjector ###

These are pretty straight forward `GinModule` and `Ginjector`.

```
public class AppGinModule extends AbstractGinModule {
    @Override
    protected void configure() {
        bind(EventBus.class).to(SlottedEventBus.class).in(Singleton.class);

        bind(BaseView.class).to(BaseViewImpl.class).in(Singleton.class);
        bind(GoodbyeView.class).to(GoodbyeViewImpl.class).in(Singleton.class);
        bind(HelloView.class).to(HelloViewImpl.class).in(Singleton.class);
    }

    @Provides @Singleton
    public SlottedController getSlottedController(AutoHistoryMapper mapper, EventBus eventBus) {
        return new SlottedController(mapper, eventBus);
    }
}
```

The binding of `EventBus` to `SlottedEventBus` is not required.  You can bind to the `SimpleEventBus`, but `SlottedEventBus` was created to fix a bug where new `EventHandler`s might not get Events.  See the `SlottedEventBus` javadoc for more details.

```
@GinModules({AppGinModule.class})
public interface AppGinjector extends Ginjector {
    public static final AppGinjector instance = GWT.create(AppGinjector.class);

    SlottedController getSlottedController();
    EventBus getEventBus();
    BaseActivity getBaseActivity();
    GoodbyeActivity getGoodbyeActivity();
    HelloActivity getHelloActivity();
}
```

### Update BasePlace ###

We change `BasePlace` to extend `SlottedPlace` instead of `MappedSlottedPlace`.  This requires us to add the `getActivity()` method, which will be called instead of the `ActivityMapper`.

```
public class BasePlace extends SlottedPlace {
    public static final Slot SLOT = new Slot(new BasePlace(), new HelloPlace("Base!"));

    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {SLOT};
    }

    @Override public Activity getActivity() {
        return AppGinjector.instance.getBaseActivity();
    }
}
```

This should be done for `HelloPlace` and `GoodbyePlace`.  This also means the `AppActivityMapper` is nolonger needed and can be removed.

### Update BaseActivity ###

Migrate `BaseActivity` to use `@Inject` to get dependencies.

```
public class BaseActivity extends SlottedActivity {
    @Inject private BaseView baseView;

    public BaseActivity() {
    }

    /**
     * Invoked by the SlottedController to start a new Activity
     */
    @Override public void start(AcceptsOneWidget containerWidget) {
        containerWidget.setWidget(baseView.asWidget());
    }

    @Override public void setChildSlotDisplay(Slot slot) {
        slot.setDisplay(baseView.getSlotDisplay());
    }
}
```

This should be done for `HelloActivity` and `GoodbyeActivity`.  Please see the source [here](http://code.google.com/p/slotted/source/browse/examples/Gin/src/com/googlecode/slotted/gin_example/client/HelloActivity.java)  and [here](http://code.google.com/p/slotted/source/browse/examples/Gin/src/com/googlecode/slotted/gin_example/client/GoodbyeActivity.java) for complete changes.

### Update HelloMVP ###

Now, update `HelloMVP` to use GIN to construct Slotted.

```
public class HelloMVP implements EntryPoint {
    private SimplePanel appWidget = new SimplePanel();

    public void onModuleLoad() {
        SlottedController slottedController = AppGinjector.instance.getSlottedController();
        slottedController.setDefaultPlace(new HelloPlace("World!"));
        RootPanel.get().add(appWidget);
        // Goes to the place represented on URL else default place
        slottedController.setDisplay(appWidget);
    }
}
```
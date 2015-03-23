# WORK IN PROGRESS (v0.4) #


# Code Splitting in Slotted #

Slotted 0.4 was refactored to make code splitting easy.  Slotted uses the Async Callback pattern to create all Activities, which allows the Activity construction to happen inside a `GWT.runAsync()` call needed for code splitting.  The code splitting can happen at the Activity level, or multiple Activities can be grouped into a CodeSplitMapper to provide a large grouping.  Slotted also provides the ability to code split around Ginjectors.

## Single Activity ##

Here is an example of how to code split a single Activity using SlottedPlace:
```
public class HomePlace extends SlottedPlace {
    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return null;
    }

    @Override protected void getActivity(final Callback<? super Activity, ? super Throwable> callback) {
        GWT.runAsync(new RunAsyncCallback() {
            @Override public void onFailure(Throwable reason) {
                callback.onFailure(reason);
            }

            @Override public void onSuccess() {
                callback.onSuccess(new !HomeActivity());
            }
        });
    }
}
```

All that is needed is to override the `getActivity(Callback)` method and create the HomeActivity inside a `GWT.runAsync()`.

## Multiple Activities ##

Often times you have an area of the site that should be grouped into one code split block.  CodeSplitMapper allows multiple Activites to be created inside the same `GWT.runAsync()` call.  It works similar to the A&P ActivityMapper, but with an asynchronous `getActivity()` call.

The CodeSplitMapper can be written by manually, but Slotted provides a CodeSplitMapper generator which creates all the boilerplate code for constructing the Activity using the default constructor.  The documentation will describe the generator version of the CodeSplitMapper, but you can find a manual example here.

First, a new mapper interface should be created like this:
```
public interface AdminMapper extends CodeSplitMapper {
}
```

Second, the AdminMapper should be created via `GWT.create()` and registered with the SlottedController:
```
        slottedController.registerCodeSplitMapper(AdminMapper.class, GWT.<CodeSplitMapper>create(AdminMapper.class));
        
        //Needs to be after all register calls
        slottedController.setDisplay(appWidget);
```

Third, all places that will create Activities in this block need to get `@CodeSplit` and `@PlaceActivty` annotations:
```
@CodeSplit(AdminMapper.class)
@PlaceActivity(EditAccountActivity.class)
public class EditAccountPlace extends SlottedPlace {
    @Override public Slot getParentSlot() {
        return SlottedController.RootSlot;
    }

    @Override public Slot[] getChildSlots() {
        return new Slot[] {};
    }
}
```

AutoHistoryMapper is required to get the `@CodeSplit` annotation to work correctly.  If AutoHistoryMapper is not being used, the CodeSplitMapper needs to be passed into the `registerPlace()`. If a manual CodeSplitMapper is used, `@CodeSplit` can still be used with the AutoHistoryMapper, but the `@PlaceActivity` isn't needed.

## Gin ##

If Gin is being used to create the Activities, the Ginjector needs to be split into a Ginjector for each code split block.  Then a CodeSplitGinMapper needs to be created:
```
public interface GapMapper extends CodeSplitGinMapper<AdminGinjector> {
}
```
Notice that the CodeSplitGinMapper has a generic of the Ginjector that is being mapped, which is required.

The CodeSplitGinMapper needs to be registered with the SlottedController just like the CodeSplitMapper, and Places are annotated the same way as with the CodeSplitMapper.

The Ginjecter does need to provide a method for each Activit in this format:
```
	SomeActivity getSomeActivity();
```


### Singletons ###

If you are familar with Gin, you will realize that multiple Ginjectors cause problems with Singletons.  Each Ginjector will have its own list of Singletons.  This might not be a problem, and might actually help code splitting.  For example, if HomeGinjector has HomeView as a Singleton, but HomeView is only ever used by HomeActivity. Then we want HomeView to only exist in that HomeGinjector, so that HomeView is code split correctly.  The problem arises if multiple Ginjectors need to have the same Singleton object, a Global Singleton.

Slotted has created a solution for this with the use of an Annotation preprocessor.  The preprocessor will create a SingletonGinjector and a SingletonModule.  The SingletonGinjector creates all the global singletons, and the SingletonModule has a bunch of `@Provides` methods that get the Singleton objects from the SingletonGinjector.

#### How it Works ####

The preprossor is registered as a service in the Slotted jar, which means that it will automatically run when a javac is done (IDEs need to be configured).  The preprossor doesn't create any files unless `@GenerateGinSingletons` annotation added to a class.  We recommed adding it to the GWT Entry point class.  Here is an example:
```
@GenerateGinSingletons(
        fullPackage = "com.googlecode.slotted.gin_!CodeSplitting.client",
        scanPackages = {"com.googlecode.slotted.gin_!CodeSplitting.client"},
        modules = {"com.gaggle.gwt.client.gin.GlobalGinModule"})
public class HelloMVP implements EntryPoint {
...
```

The `fullPackage` parameter gives the package where the generated files should be put.  The `scanPackages` parameter is a list of packages that contain the Singletons you want to be global.  The preprocessor automatically uses searches child packages, and wild cards aren't supported.  The `modules` parameter specifies GinModules should be added to the SingletonGinjector and that should be searched for `@GlobalSingletons`

The preprocessor then searches the package for any class or interface that has the javax.inject.Singleton or com.google.inject.Singleton annotation. It is possible to exclude Singletons in the scanPackages by adding the `@GlobalSingleton(false)` annotation to the class.  It is also possible to mark a GinModule provide methods with `@GlobalSingleton` and it will also be added to SingletonGinjector.

To get a Ginjector to use the Global Singletons, just include the SingletonModule in the list of module for the Ginjector.
```
@GinModules({AdminModule.class, SingletonModule.class})
public interface GapGinjector extends Ginjector {
	EditAccountActivity getEditAccountActivity();
}
```
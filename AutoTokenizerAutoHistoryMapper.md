
# AutoTokenizer and AutoHistoryMapper #

AutoTokenizer and AutoHistoryMapper were created to remove a lot of boiler plate code that is needed to wire up each Place with its History token.  GWT provides a great mechanism for removing boiler plate code: Generators.  Generators search through the code and write java classes that handle all the boiler plate code, and it does it during compile time, which means there is no performance hit during runtime.

## Boiler Plate Code ##

Let's look at the !HelloMVP example found in GWT's [documentation](https://developers.google.com/web-toolkit/doc/latest/DevGuideMvpActivitiesAndPlaces) to see what boiler plate we are talking about.

Let's look at the HelloPlace:
```
public class HelloPlace extends Place {
    private String helloName;

    public HelloPlace(String token) {
        this.helloName = token;
    }

    public String getHelloName() {
        return helloName;
    }

    public static class Tokenizer implements PlaceTokenizer<HelloPlace> {
        @Override
        public String getToken(HelloPlace place) {
            return place.getHelloName();
        }

        @Override
        public HelloPlace getPlace(String token) {
            return new HelloPlace(token);
        }
    }
}
```

HelloPlace looks like a standard Java Bean until we get to the Tokenizer class.  The Tokenizer is the boiler plate code we are talking about.  Wouldn't it be great if that could be taken care of automatically?  That is what the AutoTokenizer will do.

Next we look at the AppPlaceHistoryMapper:
```
@WithTokenizers({HelloPlace.Tokenizer.class, GoodbyePlace.Tokenizer.class})
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper
{
}
```

This looks like a small chunk of code, but for every Place we need to add `NewPlace.Tokenizer.class` to the @WithTokenizers list.  If you forget to added it, your code will fail to navigate to that Place.  Wouldn't it be great if all the Places in your code were automatically registered?  That is what AutoHistoryMapper does.

## How AutoTokenizer Works ##

To use the AutoTokenizer we just replace:
```
public static class Tokenizer implements PlaceTokenizer<HelloPlace> {
        @Override
        public String getToken(HelloPlace place) {
            return place.getHelloName();
        }

        @Override
        public HelloPlace getPlace(String token) {
            return new HelloPlace(token);
        }
    }
```
with:
```
public static interface Tokenizer implements AutoTokenizer<HelloPlace> {}
```

We then need to add an Annotation (@TokenizerParameter or @GlobalParameter) to each property that we want included in the History token.  Here is the new HelloPlace with a couple extra parameters:

```
public class HelloPlace extends MappedSlottedPlace {
    @TokenizerParameter
    private String helloName;
    @TokenizerParameter
    private int someId;
    @GlobalParameter
    private String global;

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

    public static interface Tokenizer implements AutoTokenizer<HelloPlace>{}
}
```

Notice the we can also have properties that are of type `int`, which automatically get converted to and from Strings in the History token.

AutoTokenizer interfaces still need to be added to the @WithTokenizers list unless you use AutoHistoryMapper also.

### History Token Format ###
Here is a what the History token would look like for HelloPlace above:
```
#Hello:Jeff&1234?global=someString
```

Slotted uses the simple class name of the Place, and removes "Place" if the class name ends with "Place".  It is possible to use the @Prefix("name") to redefine the name.  If there are @TokenizerParameters the appends a ":" followed by the value of the first @TokenizerParameter in the Place.  It then appends an "&" and the next value.  All the values are added in the order they appear in the Place class.  At the very end of all the Places, a "?" will be added and all the @GlobalParameters will be added as "key=value" pairs.

Here is another example with nesting:
```
#Base:abc/Sub1/Sub2:123&45?id=12345&sort=columnA
```

## How AutoHistoryMapper Works ##
Instead of creating the AppPlaceHistoryMapper class, just construct a AutoHistoryMapper class like this:
```
HistoryMapper mapper = GWT.create(AutoHistoryMapper.class);
```

This will automatically search through all of your code for any none abstract class that is assignable to Place.class.  If the Place has a Tokenizer defined, it will be used, otherwise AutoHistoryMapper will create an AutoTokenizer.

For example if we wanted to create a new simple Place, it could look like this:
```
public class SimplePlace {}
```
AutoHistoryMapper will find SimplePlace and register it, allowing SimplePlace to be used in goTo() calls.

If we wanted to add a parameter it would look like this:
```
public class SimplePlace {
    @TokenizerParameter
    public String myParameter;
}
```
No need to even define the AutoTokenizer interface.  AutoHistoryMapper does that for you.

See [Overview](Overview.md) or [Migrating GWT Activites and Places](MigrateGWTActivitiesPlaces.md) for complete examples on how AutoHistoryMapper is wired with the SlottedController
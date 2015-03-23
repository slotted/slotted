# Slotted #

The Slotted framework is an extension of GWT Activities and Places (A&P), which adds the ability to nest Activities inside other Activities. Slotted has retained much of the A&P API, and has only changed where needed to support nesting.  This allows for easy migration of existing A&P projects.

Just like Activities and Places, Slotted is a history management framework that allows you to create bookmarkable URLs within your application, thus allowing the browser's back button and bookmarks to work as users expect.  It maybe used in conjunction with MVP development, but is not strictly speaking an MVP framework.

If you haven't seen GWT's Activities and Places, you can read GWT's documentation here: https://developers.google.com/web-toolkit/doc/latest/DevGuideMvpActivitiesAndPlaces

## Why Nesting? ##

GWT's Activities are great, and provide a great flow for page initialization and page navigation.  Now imagine a page with a TabPanel.  Wouldn't it be great if each Tab was setup like an Activity?  Wouldn't it be great to have delayed initialization and navigation checks?

This is why Slotted was created.  Slotted allows you to organize your entire site into hierarchy of Activities.  You no longer need to manage complex Views/Activities yourself.  You can divide these complex pages into many Activities, and allow Slotted to manage the nesting structure and provide nice bookmarkable URLs for these complex views.

## Supporters ##
| ![https://gaggle.net/wp-content/themes/gaggle-net/img/logo@2x.png](https://gaggle.net/wp-content/themes/gaggle-net/img/logo@2x.png) |  We want to send out great thanks to Gaggle for using <br> Slotted on their Next UI.  They have worked closely with <br>Slotted to make improvements and support the development <br>of many features. </tbody></table>

<h2>Status</h2>

Slotted has been released to Maven Central Repository, but should still be considered BETA.  We have been using the code in Production environments on a couple of projects, so we feel that the code is solid.  Please let us know if you are using it and how we can make Slotted better for your needs.<br>
<br>
Version 0.2 has been released, and there is active work on Version 0.3.  To see what is in version 0.2 and what is coming version 0.3, check out our <a href='RoadMap.md'>RoadMap</a> wiki.<br>
<br>
<h2>Getting Started</h2>

Want to get started using Slotted? Take a look at the <a href='Overview.md'>Overview</a> and <a href='MigrateGWTActivitiesPlaces.md'>MigrateGWTActivitiesPlaces</a>.  You can also find code samples <a href='http://code.google.com/p/slotted/source/browse/#git%2Fexamples'>here</a>.
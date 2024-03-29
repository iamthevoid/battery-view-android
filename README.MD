
[<img src="media/lib.svg">](https://search.maven.org/search?q=g:io.github.iamthevoid.batteryview)  

# battery-view-android

Simple View to show battery level. View scales perfectly inside bounds whatever they are, so it looks always beautiful. View can be customized. Except bounds and color can be changed: charging status - is charging or not, border thickness, spacing between border and battery-level rect and sure battery level percent. If need something else you can just leave feature request. Screenshots how it looks like:

Charged             |  Charging          |  Discharged a bit       |  Low with colored border
:-------------------------:|:-------------------------:|:-------------------------:|:-------------------------:
![charged](media/charged.png "Charged")   |  ![charging](media/charging.png "Charging") |  ![discharged](media/discharged.png "Discharged a bit") | ![borderColor](media/borderColor.png "Low with colored border")
##

Also you can set attributes in xml:
attribute             |  meaning
:-------------------------:|:-------------------------:
```app:bv_borderColor``` | Has 'color' type, and sets the color of the border of the battery. Also can be set in code with view `borderColor` property.
```app:bv_infillColor``` | Has 'color' type, and sets the color of the infill of the battery. Also can be set in code with view `infillColor` property.
```app:bv_percent``` | Has 'integer' type, sets the percent of battery. Also can be set in code with view `batteryLevel` property.
```app:bv_charging``` | Has 'boolean' type, sets the charging state. If battery is 'charging' level rect is not shown. Also can be set in code with view `isCharging` property.
```app:bv_internalSpacing``` | Has 'float' type, sets spacing between border and level drawable. Value is relative to border width and actually this is percent of border width. Also can be set in code with view `internalSpacing` property.
##
You can add this lib in your project as simple as add this line to your build.gradle file

```
   implementation "io.github.iamthevoid.batteryview:batteryview:0.6.1"
```

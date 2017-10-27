# battery-view-android

Simple View to show battery level as

![red](https://github.com/iamthevoid/battery-view-android/blob/master/red_empty.png)

or as

![green](https://github.com/iamthevoid/battery-view-android/blob/master/green_63_percent.png)

or charging

![charging](https://github.com/iamthevoid/battery-view-android/blob/master/charging_white.png)

You can set any size as you want, aspect ratio of view will be the same anyway;

Also you can set percet in code:

``` java
    BatteryView bv = (BatteryView) findViewById(R.id.battery);
    bv.setPercent(32);
```

or

``` java
    BatteryView bv = (BatteryView) findViewById(R.id.battery);
    bv.setCharging(true);
```

You can set attributes in xml:


```app:bv_color``` has 'color' type sets the color of view:

app:bv_color="#000"

or

app:bv_color="@color/red"

```app:bv_percent``` has 'integer' type sets the percent of battery:

app:bv_percent="34"

```app:bv_charging``` has 'boolean' type sets the charging state. If battery is 'charging'
percents are not shown

app:bv_charging="true"



   You can add this lib in your project as simple as add this line to your build.gradle file
```
   implementation 'iam.thevoid.batteryview:batteryview:0.1'
```

[Library at Bintray](https://bintray.com/iamthevoid/maven/BatteryView)



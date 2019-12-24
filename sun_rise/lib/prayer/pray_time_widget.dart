import 'package:flare_flutter/flare_actor.dart';
import 'package:flare_flutter/flare_cache_builder.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:sun_rise/prayer/sun_arch_painter.dart';

import 'PrayerTime.dart';
import 'pray_time.dart';
class WidgetPrayerTime extends StatefulWidget {
  static final String routeName = "/prayer" ;
  WidgetPrayerTime({Key key}) : super(key: key);
  @override
  _WidgetPrayerTimeState createState() => _WidgetPrayerTimeState();
}

class _WidgetPrayerTimeState extends State<WidgetPrayerTime> with TickerProviderStateMixin {
  List<Salah> salatTimes;
  List<double> percents;
  double sunLocation;

  Animation<double> fastAnimation;
  AnimationController fastController;

  Animation<double> slowAnimation;
  AnimationController slowController;

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    // Test Prayer times here
    salatTimes = getSalahTimes();
    percents = calculateSunPlaces();
    initAnimationController();
  }

  List<double> calculateSunPlaces() {
    //calculate total minutes between sunrise and sunset
    Salah fagr = salatTimes[0];
    Salah sunRise = salatTimes[1];
    Salah dohr = salatTimes[2];
    Salah asr = salatTimes[3];
    Salah sunset = salatTimes[4];
    Salah maghrib = salatTimes[5];
    Salah isha = salatTimes[6];

    int minutesSunriseToSunset =
        sunset.duration.inMinutes - sunRise.duration.inMinutes;

    int minutesToDohr = dohr.duration.inMinutes - sunRise.duration.inMinutes;
    int minutesToAsr = asr.duration.inMinutes - sunRise.duration.inMinutes;

    int minutesToCurrentTime =
        Duration(hours: DateTime.now().hour, minutes: DateTime.now().minute)
                .inMinutes -
            sunRise.duration.inMinutes;

    double dohrPercent = (minutesToDohr / minutesSunriseToSunset) * 100;
    double asrPercent = (minutesToAsr / minutesSunriseToSunset) * 100;
    double currentPercent =
        (minutesToCurrentTime / minutesSunriseToSunset) * 100;

    List<double> percents = [0, dohrPercent, asrPercent, 100, currentPercent];
    return percents;
  }

  initAnimationController() {
    fastController =
        AnimationController(duration: const Duration(seconds: 2), vsync: this);
    fastAnimation = Tween<double>(begin: 0.5, end: 1).animate(fastController)
      ..addListener(() {
        setState(() {
          // The state that has changed here is the animation object’s value.
        });
      })
      ..addStatusListener((status) {
        if (status == AnimationStatus.completed) {
          slowController.forward();
        }
      });
    fastController.forward();

    slowController =
        AnimationController(duration: const Duration(seconds: 4), vsync: this);
    slowAnimation = Tween<double>(begin: 0.0, end: 1).animate(slowController)
      ..addListener(() {
        setState(() {
          // The state that has changed here is the animation object’s value.
        });
      });
  }

  List<Salah> getSalahTimes() {
    PrayTime prayers = new PrayTime();

    double latitude = 32.887211;
    double longitude = 13.191338;
    double timezone = 1;

    prayers.setTimeFormat(prayers.Time24);
    prayers.setCalcMethod(prayers.Egypt);
    prayers.setAsrJuristic(prayers.Shafii);
    prayers.setAdjustHighLats(prayers.AngleBased);
//    List<int> offsets = [0, 0, 0, 0, 0, 0, 0]; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
//    prayers.tune(offsets);

    DateTime now = new DateTime.now();

    List<String> prayerTimes =
        prayers.getPrayerTimes(now, latitude, longitude, timezone);
    List<String> prayerNames = prayers.getTimeNames();
    List<Salah> times = List();
    for (int i = 0; i < prayerTimes.length; i++) {
      times.add(Salah(prayerNames[i], prayerTimes[i]));
    }
    return times;
  }


  @override
  Widget build(BuildContext context) {
    return  Container(
      color: Colors.transparent,
      padding: EdgeInsets.all(10),
      child: new CustomPaint(
          painter: new SunArchPainter(
              lineColor: Colors.amber,
              pointsColor: Colors.blueAccent,
              pointsPercent: percents,
              fastAnimationValue: fastAnimation.value,
              slowAnimationValue: slowAnimation.value,
              lineWidth: 4.0)),
    );
  }

  @override
  void dispose() {
    // TODO: implement dispose
    super.dispose();
    fastController.dispose();
    slowController.dispose();
  }
}
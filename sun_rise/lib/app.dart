import 'package:flame/flame.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:sun_rise/prayer/pray_time_widget.dart';
import 'package:sun_rise/trx_game/trx_game_widget.dart';

class MApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      routes: appRoute,
      initialRoute: '/',
    );
  }
}
Map<String, WidgetBuilder> appRoute = {
  '/': (context) => WidgetTRexGame(),

  WidgetPrayerTime.routeName: (context) => WidgetPrayerTime(),

  WidgetTRexGame.routeName: (context) => WidgetTRexGame()

};

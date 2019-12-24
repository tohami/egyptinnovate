import 'package:flame/flame.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:sun_rise/app.dart';

void main() async {
  Flame.audio.disableLog();

  runApp(MApp());

  SystemChrome.setEnabledSystemUIOverlays([]);
}
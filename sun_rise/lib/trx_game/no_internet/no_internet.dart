import 'dart:ui' as ui;
import 'package:flame/components/component.dart';
import 'package:flame/components/mixins/resizable.dart';
import 'package:flutter/material.dart';

class NoInternetText extends PositionComponent with Resizable {
  Size screenSize;

  NoInternetText() ;

  @override
  void resize(Size size) {
    this.screenSize = size;
  }

  @override
  void render(Canvas c) {
    Rect bgRect = Rect.fromLTWH(screenSize.width/2 - 300 , screenSize.height - 100 , 600, 100);
    Paint bgPaint = Paint();
    bgPaint.color = Color(0xff000000);

    final textStyle = TextStyle(
      color: Colors.black,
      fontSize: 40,
    );
    final textSpan = TextSpan(
      text: 'No internet connection',
      style: textStyle,
    );
    final textPainter = TextPainter(
        text: textSpan,
        textDirection: TextDirection.ltr,
        textAlign: TextAlign.center
    );
    textPainter.layout(
      minWidth: 500,
      maxWidth: 500,
    );
    final offset = Offset(screenSize.width/2 - 250 , screenSize.height - 100);
    textPainter.paint(c, offset);
  }

  @override
  void update(double t) {

  }



}

import 'package:flame/flame.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:sun_rise/trx_game/game.dart';
import 'dart:ui' as ui;

class WidgetTRexGame extends StatefulWidget {
  static final String routeName = "/game" ;

  @override
  _WidgetTRexGameState createState() => _WidgetTRexGameState();
}

class _WidgetTRexGameState extends State<WidgetTRexGame> {

  List<ui.Image> image;
  TRexGame game;

  @override
  void initState() {
    super.initState();
    startGame();
  }

  void startGame() async {
    image = await Flame.images.loadAll(["sprite.png"]);
    game = TRexGame(spriteImage: image[0]);
    setState(() {});
    Flame.util.addGestureRecognizer(TapGestureRecognizer()
      ..onTapDown = (TapDownDetails evt) => game.onTap());
  }

  @override
  Widget build(BuildContext context) {
    if(image == null || game == null) {
      return Container();
    }
    return Container(
      decoration: BoxDecoration(
        color: Colors.red
      ),
      child: game.widget,
    );
  }
}
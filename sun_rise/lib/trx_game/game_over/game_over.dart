import 'dart:ui' as ui;
import 'package:flame/components/component.dart';
import 'package:flame/components/composed_component.dart';
import 'package:flame/components/mixins/has_game_ref.dart';
import 'package:flame/components/mixins/resizable.dart';
import 'package:flame/components/mixins/tapable.dart';
import 'package:flame/sprite.dart';
import 'package:flutter/material.dart';
import 'package:sun_rise/trx_game/game_over/config.dart';

class GameOverPanel extends PositionComponent
    with Resizable, HasGameRef, Tapable, ComposedComponent {

  GameOverPanel(ui.Image spriteImage) : super() {
    gameOverText = GameOverText(spriteImage);

    components..add(gameOverText);
  }

  bool visible = false;

  GameOverText gameOverText;

  @override
  void render(Canvas canvas) {
    if (visible) {
      super.render(canvas);
    }
  }
}

class GameOverText extends SpriteComponent with Resizable {
  GameOverText(ui.Image spriteImage)
      : super.fromSprite(
          GameOverConfig.textWidth,
          GameOverConfig.textHeight,
          Sprite.fromImage(
            spriteImage,
            x: 955.0,
            y: 26.0,
            width: GameOverConfig.textWidth,
            height: GameOverConfig.textHeight,
          ),
        );

  @override
  void resize(Size size) {
    if (width > size.width * 0.8) {
      width = size.width * 0.8;
    }
    y = size.height * .25;
    x = (size.width / 2) - width / 2;
  }
}

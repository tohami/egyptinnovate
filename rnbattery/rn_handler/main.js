import React from 'react';
import {AppRegistry, StyleSheet, Text, View} from 'react-native';
import BatteryStatus from 'react-native-battery';

class HelloWorld extends React.Component {

  render() {
    return (
      <BatteryStatus/>
    );
  }
}
var styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
  },
  hello: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
});

AppRegistry.registerComponent('MyReactNativeApp', () => HelloWorld);
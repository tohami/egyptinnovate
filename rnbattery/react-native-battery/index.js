import React, { Component } from 'react';
import { NativeModules, Button } from 'react-native';
import { Platform, StyleSheet, Text, View } from 'react-native';
const { Battery } = NativeModules;

export default class BatteryStatus extends Component<{}> {
    state = {
        message: '--'
    };
    componentDidMount() {
        this.updateBatterLevel()
    }
    render() {
        return (
            <View style={styles.container}>
                <Text style={styles.instructions}>[{this.state.message}]</Text>
                
                <Button style={styles.instructions} onPress={() => {
                    this.setState({
                        message: "refreshing"
                    })
                    this.updateBatterLevel()
                }} title="Refresh"></Button>
                <View style = {{height:8}}/>
                <Button style={styles.instructions} onPress={() => {
                    this.showToast("your battery status is " + this.state.message)
                }} title="show in toast"></Button>

            </View>
        );
    }

    updateBatterLevel() {
        Battery.batteryLevel((message) => {
            this.setState({
                message
            });
        });
    }

    showToast(message){
        Battery.showToast(message , (msg) =>{})
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#F5FCFF',
    },
    welcome: {
        fontSize: 20,
        textAlign: 'center',
        margin: 10,
    },
    instructions: {
        textAlign: 'center',
        color: '#333333',
        marginBottom: 5,
    },
});
import React from 'react';
import {NativeEventEmitter, NativeModules, View} from 'react-native';

const {EventModule} = NativeModules;
const eventEmitter = new NativeEventEmitter(EventModule);
EventModule?.start();
eventEmitter.addListener('MyCustomEvent', data => console.log(data));

function App(): React.JSX.Element {
  return <View></View>;
}

export default App;

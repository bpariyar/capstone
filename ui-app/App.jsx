import React, { useState, useEffect } from 'react';
import { View, Text, StyleSheet } from 'react-native';

const App = () => {
  const [title, setTitle] = useState('');
  useEffect(() => {
    fetch('http://192.168.0.110:8080/flashcard/set/all')
      .then(response => response.json())
      .then(setTitle)
      .catch(error => console.error(error));
  }, []);

  return (
    <View style={styles.container}>
    <div>
          <Text>{title.title}</Text>

    </div>
    </View>
  );

}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  quoteText: {
    fontSize: 24,
    fontStyle: 'italic',
    textAlign: 'center',
    marginHorizontal: 20,
  },
});

export default App;
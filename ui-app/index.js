import { View, Text, StyleSheet } from 'react-native';

async function fetchData() {
  try {
    const response = await fetch('https://my-api.com/data');
    const data = await response.json();
    console.log(data);
  } catch (error) {
    console.error(error);
  }
}
fetchData();

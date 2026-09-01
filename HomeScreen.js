import React, { useState, useEffect } from 'react';
import { View, Alert } from 'react-native';
import MapView from 'react-native-maps';
import * as Location from 'expo-location';
 import HomeStyles from './style/HomeStyles';

export default function Home() {
    const latDelta = 0.008;

    const [region, setRegion] = useState({
        latitude: 55.861159,
        longitude: -4.243424,
        latitudeDelta: latDelta,
        longitudeDelta: latDelta,
    });
    useEffect(() => {
        let subscription;

        (async () => {
            const { status } = await Location.requestForegroundPermissionsAsync();
            if (status !== 'granted') {
                Alert.alert('Permission denied', 'Location access is needed.');
                return;
            }
            const initial = await Location.getCurrentPositionAsync({
                accuracy: Location.Accuracy.High,
            });
            const { latitude, longitude } = initial.coords;
            setRegion(prev => ({ ...prev, latitude, longitude }));
            subscription = await Location.watchPositionAsync(
                { accuracy: Location.Accuracy.High, timeInterval: 3000, distanceInterval: 5 },
                (loc) => setRegion(prev => ({
                    ...prev,
                    latitude: loc.coords.latitude,
                    longitude: loc.coords.longitude,
                }))
            );
        })();

        return () => subscription?.remove();
    }, []);

    return (
        <View style={HomeStyles.container}>
            <MapView
                style={HomeStyles.map}
                region={region}
                onRegionChangeComplete={(r) => setRegion(r)}
                showsUserLocation={true}     
                showsMyLocationButton={true}   
            />
        </View>
    );
}
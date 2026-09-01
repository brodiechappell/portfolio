import * as Location from 'expo-location';
import { useEffect, useRef, useState } from 'react';
import { Alert, Text, TouchableOpacity, View } from 'react-native';
import MapView, { Callout, Marker } from 'react-native-maps';
import HomeStyles from '../theme/HomeStyles';

export default function createWMap({navigation}) {
    const latDelta = 0.008;

    const [region, setRegion] = useState({
        latitude: 55.861159,
        longitude: -4.243424,
        latitudeDelta: latDelta,
        longitudeDelta: latDelta,
    });
    const [marker, setMarker] = useState(null);
    const savedLat = useRef(null);
    const savedLng = useRef(null);
    const [userLocation, setUserLocation] = useState(null);
    const mapRef = useRef(null);
    
    const confirmLocation = () => {
    if (!marker) {
        Alert.alert("No location", "Long press to drop a pin first.");
    return;
    }
    navigation.navigate('CreateWorkshop', {
        latitude: marker.latitude,
        longitude: marker.longitude,
    });
    };
    useEffect(() => {
        let subscription;

        (async () => {
            const { status } = await Location.requestForegroundPermissionsAsync();
            if (status !== 'granted') {
                Alert.alert('Permission denied', 'Location access is needed for live tracking.');
                return;
            }

            const initial = await Location.getCurrentPositionAsync({
                accuracy: Location.Accuracy.High,
            });
            const { latitude, longitude } = initial.coords;
            setUserLocation({ latitude, longitude });
            setRegion(prev => ({ ...prev, latitude, longitude }));

            subscription = await Location.watchPositionAsync(
                {
                    accuracy: Location.Accuracy.High,
                    timeInterval: 3000,
                    distanceInterval: 5,
                },
                (loc) => {
                    setUserLocation({
                        latitude: loc.coords.latitude,
                        longitude: loc.coords.longitude,
                    });
                }
            );
        })();

        return () => subscription?.remove();
    }, []);
    const handleLongPress = (evt) => {
        const { latitude, longitude } = evt?.nativeEvent?.coordinate ?? {};
        if (!latitude || !longitude) return;

        savedLat.current = latitude;
        savedLng.current = longitude;

        setMarker({ latitude, longitude });

        console.log('Maker saved at lat:', savedLat.current, 'lng:', savedLng.current);
    };
    const handleMarkerButtonPress = () => {
        Alert.alert(
            'Marker Details',
            `Lat: ${savedLat.current?.toFixed(6)}\nLng: ${savedLng.current?.toFixed(6)}`
        );
    };
    const clearMarker = () => {
        setMarker(null);
        savedLat.current = null;
        savedLng.current = null;
    };

    return (
        <View style={HomeStyles.container}>
            <MapView
                ref={mapRef}
                style={HomeStyles.map}
                region={region}
                onRegionChangeComplete={(r) => setRegion(r)}
                onLongPress={handleLongPress}
                showsUserLocation={true}
                showsMyLocationButton={true}
            >
                {marker && (
                    <Marker
                        coordinate={{ latitude: marker.latitude, longitude: marker.longitude }}
                        title="Workshop"
                        description="Long press to move"
                    >
                        <Callout tooltip={false}>
                            <View style={HomeStyles.calloutContainer}>
                                <Text style={HomeStyles.calloutTitle}>Workshop</Text>
                                <Text style={HomeStyles.calloutDescription}>
                                    {marker.latitude.toFixed(6)}, {marker.longitude.toFixed(6)}
                                </Text>
                                <TouchableOpacity
                                    style={HomeStyles.calloutButton}
                                    onPress={handleMarkerButtonPress}
                                >
                                    <Text style={HomeStyles.calloutButtonText}>See details</Text>
                                </TouchableOpacity>
                            </View>
                        </Callout>
                    </Marker>
                )}
            </MapView>

            {marker && (
                <View>
                <TouchableOpacity style={HomeStyles.clearButton} onPress={clearMarker}>
                    <Text style={HomeStyles.clearButtonText}>✕ Clear marker</Text>
                </TouchableOpacity>
                <TouchableOpacity style={HomeStyles.clearButton} onPress={confirmLocation}>
                    <Text style={HomeStyles.clearButtonText}>✓ Confirm location</Text>
                </TouchableOpacity>
                </View>
)}
        </View>
    );
}
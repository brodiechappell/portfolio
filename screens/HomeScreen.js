import AsyncStorage from '@react-native-async-storage/async-storage';
import * as Location from 'expo-location';
import { useEffect, useState } from 'react';
import { Alert, StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import MapView, { Callout, Marker } from 'react-native-maps';
import HomeStyles from '../theme/HomeStyles';

const STORAGE_KEY = "wellbeing_workshop_list";

export default function Home({ navigation }) {
    const latDelta = 0.008;

    const [region, setRegion] = useState({
        latitude: 55.861159,
        longitude: -4.243424,
        latitudeDelta: latDelta,
        longitudeDelta: latDelta,
    });
    const [workshops, setWorkshops] = useState([]);

    useEffect(() => {
        const loadWorkshops = async () => {
            try {
                const saved = await AsyncStorage.getItem(STORAGE_KEY);
                if (!saved) {
                    setWorkshops([]);
                    return;
                }

                const parsed = JSON.parse(saved);

                if (Array.isArray(parsed)) {
                    setWorkshops(parsed);
                } else {
                    setWorkshops([]);
                }
            } catch {
                setWorkshops([]);
            }
        };

        loadWorkshops();

        const unsubscribe = navigation.addListener('focus', loadWorkshops);
        return unsubscribe;
    }, [navigation]);

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
            >
                {workshops.map((workshop, index) => (
                    workshop.latitude != null && workshop.longitude != null && (
                        <Marker
                            key={workshop.id || index}
                            coordinate={{
                                latitude: Number(workshop.latitude),
                                longitude: Number(workshop.longitude),
                            }}
                        >
                            <Callout
                                tooltip={false}
                                onPress={() => navigation.navigate('WorkshopDetails', {
                                    workshop: workshop,
                                })}
                            >
                                <View style={styles.callout}>
                                    <Text style={styles.calloutTitle}>
                                        {workshop.title || "Untitled Workshop"}
                                    </Text>
                                    <Text style={styles.calloutDescription}>
                                        {workshop.description || ""}
                                    </Text>
                                    <TouchableOpacity style={styles.calloutButton}>
                                        <Text style={styles.calloutButtonText}>See details →</Text>
                                    </TouchableOpacity>
                                </View>
                            </Callout>
                        </Marker>
                    )
                ))}
            </MapView>
            <TouchableOpacity
               onPress={() => navigation.navigate('CreateWorkshop')}
               style={{
               position: 'absolute',
               bottom: 20,
               right: 20,
               backgroundColor: 'black',
               padding: 12,
        }   }
>
    <Text style={{ color: 'white' }}>+ Create</Text>
</TouchableOpacity>
        </View>
    );
}

const styles = StyleSheet.create({
    callout: { width: 200, padding: 8 },
    calloutTitle: { fontWeight: "bold", fontSize: 14, marginBottom: 4 },
    calloutDescription: { fontSize: 12, color: "#555", marginBottom: 8 },
    calloutButton: { borderWidth: 1, borderColor: "#000", padding: 6, alignItems: "center" },
    calloutButtonText: { fontWeight: "bold", fontSize: 12 },
});
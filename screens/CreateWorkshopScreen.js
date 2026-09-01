import AsyncStorage from "@react-native-async-storage/async-storage";
import * as Location from 'expo-location';
import { useEffect, useState } from "react"; //importing all apis and using async storage
import {
  Alert,
  SafeAreaView,
  ScrollView,
  StyleSheet,
  Switch,
  Text,
  TextInput,
  TouchableOpacity,
  View,
} from "react-native";


const DRAFT_STORAGE_KEY = "wellbeing_workshop_draft";
const WORKSHOPS_STORAGE_KEY = "wellbeing_workshop_list";

export function getDefaultForm() {
  return {
    title: "",
    description: "",
    date: "",
    time: "",
    maxAttendees: "",
    locationName: "",
    materials: "",
    hostQA: true,
    groupChat: true,
    accessibility: "",
    longitude: null,
    latitude: null,

  };
}

export function safeParseWorkshopDraft(value) {
  if (!value) return null;

  try {
    const parsed = JSON.parse(value);
    if (!parsed || typeof parsed !== "object" || Array.isArray(parsed)) {
      return null;
    }

    return {
      ...getDefaultForm(),
      ...parsed,
    };
  } catch {
    return null;
  }
}
export function safeParseWorkshopList(value) {
  if (!value) return [];

  try {
    const parsed = JSON.parse(value);
    if (!Array.isArray(parsed)) {
      return [];
    }

    return parsed
      .map((item) => ({ ...getDefaultForm(), ...item }))
      .filter((item) => item && typeof item === "object" && !Array.isArray(item));
  } catch {
    return [];
  }
}

export default function CreateWorkshopScreen({navigation, route}) {
  const [formData, setFormData] = useState(getDefaultForm());
  const [saveMessage, setSaveMessage] = useState("Loading saved draft...");
  const [hasLoaded, setHasLoaded] = useState(false);
  const [savedWorkshops, setSavedWorkshops] = useState([]);

  useEffect(() => {
  loadDraftAndWorkshops();
  }, []);

  useEffect(() => {
    if (!hasLoaded) return;

    const autoSave = async () => {
      try {
        await AsyncStorage.setItem(DRAFT_STORAGE_KEY, JSON.stringify(formData));
        setSaveMessage("Draft auto-saved on this device");
      } catch {
        setSaveMessage("Could not save draft on this device");
      }
    };

    autoSave();
  }, [formData, hasLoaded]);

  const loadDraftAndWorkshops = async () => {
  try {
    const savedDraft = await AsyncStorage.getItem(DRAFT_STORAGE_KEY);
    const parsedDraft = safeParseWorkshopDraft(savedDraft);

    if (parsedDraft) {
      setFormData(parsedDraft);
      setSaveMessage("Saved draft loaded from this device");
    } else {
      setSaveMessage("No saved draft yet");
    }

    const savedList = await AsyncStorage.getItem(WORKSHOPS_STORAGE_KEY);
    setSavedWorkshops(safeParseWorkshopList(savedList));
  } catch {
    setSaveMessage("Could not load saved draft");
  } finally {
    setHasLoaded(true);
  }
};

  const updateField = (field, value) => {
    setFormData((prev) => ({ ...prev, [field]: value }));
  };

  const saveDraftNow = async () => {
  try {
    // Convert location name → coordinates
    let coords = null;

    if (formData.locationName) {
      const results = await Location.geocodeAsync(formData.locationName);

      if (results.length > 0) {
        coords = results[0];
      } else {
        Alert.alert("Location not found", "Could not find that place.");
        return;
      }
    }

    const savedList = await AsyncStorage.getItem(WORKSHOPS_STORAGE_KEY);
    const parsedList = safeParseWorkshopList(savedList);

    const workshopToSave = {
      ...formData,
      id: Date.now().toString(),
      latitude: coords?.latitude ?? null,
      longitude: coords?.longitude ?? null,
    };

    const updatedList = [...parsedList, workshopToSave];

    await AsyncStorage.setItem(WORKSHOPS_STORAGE_KEY, JSON.stringify(updatedList));

    setSavedWorkshops(updatedList);
    Alert.alert("Saved", "Workshop saved successfully");
  } catch {
    Alert.alert("Error", "Could not save workshop");
  }
  };

  const publishWorkshop = () => {
  
  };

  
  
  return (
    <SafeAreaView style={styles.screen}>
      <ScrollView contentContainerStyle={styles.container}>
        <Text style={styles.title}>Create Workshop</Text>
        <Text style={styles.status}>{saveMessage}</Text>

        <Text style={styles.label}>Workshop title</Text>
        <TextInput
          style={styles.input}
          value={formData.title}
          onChangeText={(text) => updateField("title", text)}
          placeholder="Workshop title"
        />

        <Text style={styles.label}>Description</Text>
        <TextInput
          style={[styles.input, styles.textArea]}
          value={formData.description}
          onChangeText={(text) => updateField("description", text)}
          placeholder="Describe the workshop"
          multiline
        />

        <Text style={styles.label}>Date</Text>
        <TextInput
          style={styles.input}
          value={formData.date}
          onChangeText={(text) => updateField("date", text)}
          placeholder="YYYY-MM-DD"
        />

        <Text style={styles.label}>Time</Text>
        <TextInput
          style={styles.input}
          value={formData.time}
          onChangeText={(text) => updateField("time", text)}
          placeholder="HH:MM"
        />

        <Text style={styles.label}>Max attendees</Text>
        <TextInput
          style={styles.input}
          value={formData.maxAttendees}
          onChangeText={(text) => updateField("maxAttendees", text)}
          placeholder="20"
          keyboardType="numeric"
        />

        <Text style={styles.label}>Location</Text>
        <TextInput
          style={styles.input}
          value={formData.locationName}
          onChangeText={(text) => updateField("locationName", text)}
          placeholder="Location"
        />
        
        <Text style={styles.label}>Materials</Text>
        <TextInput
          style={[styles.input, styles.textArea]}
          value={formData.materials}
          onChangeText={(text) => updateField("materials", text)}
          placeholder="Materials or notes"
          multiline
        />


        <View style={styles.switchRow}>
            <Text style={{ color: "#1F2A44" }}>Group chat</Text>
            <Switch
                  value={formData.groupChat}
                  onValueChange={(value) => updateField("groupChat", value)}
                  trackColor={{ false: "#CFE5FF", true: "#9B8CFF" }}
                  thumbColor={formData.groupChat ? "#4DA3FF" : "#FFFFFF"}
                />
         </View>

        <Text style={styles.label}>Accessibility notes</Text>
        <TextInput
          style={[styles.input, styles.textArea]}
          value={formData.accessibility}
          onChangeText={(text) => updateField("accessibility", text)}
          placeholder="Accessibility notes"
          multiline
        />
        <TouchableOpacity style={styles.button} onPress={saveDraftNow}>
          <Text style={styles.buttonText}>Save Draft</Text>
        </TouchableOpacity>

        <TouchableOpacity style={styles.button} onPress={publishWorkshop}>
          <Text style={styles.buttonText}>Publish Workshop</Text>
        </TouchableOpacity>      
      </ScrollView>
    </SafeAreaView>
  );
}
const styles = StyleSheet.create({
  screen: {
    flex: 1,
    backgroundColor: "#EAF6FF",
  },
  container: {
    padding: 16,
  },
  title: {
    fontSize: 24,
    fontWeight: "bold",
    marginBottom: 8,
    color: "#1F2A44",
  },
  status: {
    fontSize: 14,
    marginBottom: 12,
    color: "#4DA3FF",
  },
  label: {
    marginTop: 12,
    marginBottom: 4,
    fontSize: 14,
    fontWeight: "bold",
    color: "#1F2A44",
  },
  input: {
    borderWidth: 1,
    borderColor: "#9B8CFF",
    backgroundColor: "#FFFFFF",
    padding: 10,
    marginBottom: 8,
    borderRadius: 8,
    color: "#1F2A44",
  },
  textArea: {
    minHeight: 80,
    textAlignVertical: "top",
  },
  switchRow: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    marginTop: 12,
    marginBottom: 4,
  },
  button: {
    backgroundColor: "#4DA3FF",
    padding: 12,
    marginTop: 12,
    alignItems: "center",
    borderRadius: 8,
  },
  buttonText: {
    fontWeight: "bold",
    color: "#FFFFFF",
  },
  clearButton: {
    backgroundColor: "#FF6B6B",
    padding: 12,
    marginTop: 12,
    alignItems: "center",
    marginBottom: 24,
    borderRadius: 8,
  },
});

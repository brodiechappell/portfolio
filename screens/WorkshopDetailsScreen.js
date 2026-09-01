import { Pressable, SafeAreaView, ScrollView, StyleSheet, Text, View } from "react-native";


export default function WorkshopDetailsScreen({ navigation, route }) {
    const workshop = route?.params?.workshop;

    const HandleChatButton = (chatTarget) => {
        navigation.navigate('Chat', { chatID: chatTarget, })
    };

    const GcButton = () => {
        return (
            <Pressable style={styles.button} onPress={() => HandleChatButton(workshop.title)}>
                <Text style={styles.buttonText}>Group Chat</Text>
            </Pressable>
        );
    };

    if (!workshop) {
        return (
            <View>
                <Text>No workshop details found.</Text>
            </View>
        );
    }

    return (
        <SafeAreaView style={styles.screen}>
            <ScrollView contentContainerStyle={styles.container}>
                <Text style={styles.title}>{workshop.title || "N/A"}</Text>
                <Text style={styles.subtitle}>Description: {workshop.description || "N/A"}</Text>
                <View style={styles.timing}>
                  <Text style={styles.regText}>{workshop.date || "N/A"}</Text>
                  <Text style={styles.regText}>{workshop.time || "N/A"}</Text>
                </View>
                <Text style={styles.regText}>{workshop.locationName || "N/A"}</Text>
                <Text style={styles.regText}>{workshop.maxAttendees || "N/A"} maximum attendees allowed.</Text>
                <Text style={styles.label}>Materials:</Text>
                <Text style={styles.regText}>{workshop.materials || "N/A"}</Text>
                <Text style={styles.label}>Accessibility:</Text>
                <Text style={styles.regText}>{workshop.accessibility || "N/A"}</Text>
                {workshop.groupChat === true && <GcButton />}
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
    alignItems: "center",
  },
  title: {
    fontSize: 24,
    fontWeight: "bold",
    marginBottom: 8,
    color: "#1F2A44",
  },
  subtitle: {
    fontSize: 20,
  },
  label: {
    fontSize: 18,
    fontWeight: "bold",
  },
  status: {
    fontSize: 14,
    marginBottom: 12,
    color: "#4DA3FF",
  },
  regText: {
    marginTop: 12,
    marginBottom: 5,
    fontSize: 18,
    color: "#1F2A44",
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
  timing: {
    flexDirection: "row",
    columnGap: 10,
  }
});
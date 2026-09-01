import { createNativeStackNavigator } from "@react-navigation/native-stack";

import CreateWorkshopScreen from "./screens/CreateWorkshopScreen";
import HomeScreen from "./screens/HomeScreen";
import LoginScreen from "./screens/LoginScreen";
import MessagingScreen from "./screens/MessagingScreen";
import WorkshopDetailsScreen from "./screens/WorkshopDetailsScreen";

const Stack = createNativeStackNavigator();

export default function AppNavigator() {
  return (
    <Stack.Navigator initialRouteName="Login">
      <Stack.Screen
        name="Login"
        component={LoginScreen}
        options={{ title: "Welcome" }}
      />
      <Stack.Screen
        name="Home"
        component={HomeScreen}
        options={{ title: "Welcome Page" }}
      />
      <Stack.Screen
        name="CreateWorkshop"
        component={CreateWorkshopScreen}
        options={{ title: "Create Workshop" }}
      />
      <Stack.Screen
        name="WorkshopDetails"
        component={WorkshopDetailsScreen}
        options={{ title: "Workshop Details" }}
      />
      <Stack.Screen
        name="Chat"
        component={MessagingScreen}
        options={{ title: "Chat"}}
      />
    </Stack.Navigator>
  );
}
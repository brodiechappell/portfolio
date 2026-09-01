import AsyncStorage from "@react-native-async-storage/async-storage";
import { useEffect, useState } from 'react';
import { Pressable, SafeAreaView, ScrollView, StyleSheet, Text, TextInput, View } from 'react-native';

export default function MessagingScreen({route}) {
    const [chats, setChats] = useState([])
    const [messages, setMessages] = useState([])
    const [message, setMessage] = useState('')
    const chatID = route?.params?.chatID; 

    const update = async () => {
        try{
            const tmpMsgs = [...messages, message];
            setMessages(tmpMsgs)
            const tmpCh = chats.map(chat => {
              if (chat[0] === chatID) {
                return [chatID, tmpMsgs];
              }
              return chat;
            });
            if (!chats.some(chat => chat[0] === chatID)) {
              tmpCh.push([chatID, tmpMsgs]);
              //console.log("found chat to save :D")
            }
            setChats(tmpCh);
            await AsyncStorage.setItem('chats', JSON.stringify(tmpCh));
            setMessage('');
        } catch(error) {
            //console.log(error)
        }
    };

    const load = async () => {
        const stored = await AsyncStorage.getItem('chats')
        const storedMessages = getChat(JSON.parse(stored))
        setChats(JSON.parse(stored) ?? [])
        setMessages(storedMessages ?? [])
    };

    useEffect(() => { load() }, []);

    const getChat = (array) => {
        for (let i in array) {
            if (array[i][0] === chatID) {
                return array[i][1]
            }
        }
        return []
    }

    const MessageDisplay = (props) => {
        const messageArray = []
        for (let i=0; i<(props.chat).length; i++) {
            messageArray.push(<Text key={i} style={styles.message}>{props.chat[i]}</Text>);
        }

        return (
            <View>
                {messageArray}
            </View>
        );
    };

    return (
      <SafeAreaView style={ styles.screen }>
        <View style={styles.container}>
          <ScrollView style={{flex:1}}>
              <MessageDisplay chat={messages}/>
          </ScrollView>
          <TextInput style={[styles.input, styles.textArea]} onChangeText={setMessage}/>
          {message && 
              <Pressable onPress={update} style={styles.button}>
                  <Text style={styles.buttonText}>Send</Text>
              </Pressable>}
        </View>
      </SafeAreaView>
    );
}

const styles = StyleSheet.create({
  message: {
    backgroundColor: "#325afc",
    color: "#FFFFFF",
    padding: 12,
    marginLeft: "40%",
    alignItems: "flex-end",
    borderRadius: 8,
    marginBottom: 8,
  },
  screen: {
    flex: 1,
    backgroundColor: "#EAF6FF",
  },
  container: {
    flex: 1,
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
    minHeight: 40,
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
import React, { useState } from 'react';
import { Text, View, Button, ScrollView, StyleSheet, Dimensions, TouchableOpacity } from 'react-native';
import { getAString, encode, decode, getVowelMapArray } from './caeser';

const SCREEN_WIDTH = Dimensions.get('window').width; // calculate tile size
const TILE_SIZE = Math.floor((SCREEN_WIDTH - 40) / 12);

export default function GameComponent() {

  const [secretPhrase, setSecretPhrase] = useState(getAString());
  const [key, setKey] = useState(getRandomKey()); 
  const [guesses, setGuesses] = useState([]); 
  const [encoded, setEncoded] = useState(encode(secretPhrase, key));
  const [message, setMessage] = useState("");
  const [score, setScore] = useState(0);
  const [showHint, setShowHint] = useState(false);


  const scoreTable = [16,8,4,2,1];//scoring system

  function getRandomKey() {
    const k = Math.floor(Math.random() * 13) + 1;
    return Math.random() < 0.5 ? -k : k;
  }

  const handleGuess = (guessKey) => {
  if (guesses.length >= 5) return; 

  const decodedText = decode(encoded, guessKey);
  const correct = guessKey === key;
  const newGuesses = [...guesses, { guess: guessKey, result: decodedText, correct }];
  setGuesses(newGuesses);


  if (correct) {
    const points = scoreTable[newGuesses.length - 1] || 0;
    setScore(score + points);
    setMessage(`correct key was ${key}`);
    } else {
      if (newGuesses.length >= 5) {
        setMessage(` out of guesses the key was ${key} and the secret phrase was ${secretPhrase}`);
        } else {
        setMessage(`wrong guess`);
      }
    }
  };

  const newGame = () => {
    const newPhrase = getAString();
    const newKey = getRandomKey();
    setSecretPhrase(newPhrase);
    setKey(newKey);
    setGuesses([]);
    setMessage("");
    setEncoded(encode(newPhrase, newKey));
  };

  function GuessRow({ text, isBlank, vowelMap, showHint, isCorrectRow }) {
    const MAX_LEN = 12;
    const letters = text.padEnd(MAX_LEN).split('');

    return (
      <View style={styles.row}>
        {letters.map((char, idx) => (
          <View
            key={idx}
            style={[
              styles.tile,
              isBlank && styles.blankTile,
              isCorrectRow && styles.correctTile,
              showHint && vowelMap[idx] && styles.vowelTile
            ]}
          >
            <Text style={styles.tileText}>
              {char !== ' ' ? char : ''}
            </Text>
          </View>
        ))}
      </View>
    );
  }


  const gridRows = [];

  for (let i = 0; i < 6; i++) {
    let rowText = "";
    let isBlank = true;
    let vowelMap = [];
    let isCorrectRow = false;

    if (i === 0) {
      rowText = encoded;
      isBlank = false;
      vowelMap = getVowelMapArray(secretPhrase);
    } 
    else if (guesses[i - 1]) {
      rowText = guesses[i - 1].result;
      isBlank = false;
      isCorrectRow = guesses[i - 1].correct === true;
    }

    gridRows.push(
      <GuessRow
        key={i}
        text={rowText}
        isBlank={isBlank}
        vowelMap={vowelMap}
        showHint={showHint}
        isCorrectRow={isCorrectRow}
      />
    );
  }
  const gameWon = guesses.some(g => g.correct === true);
  const guessButtons = [];

  for (let i = -13; i <= 13; i++) {
    if (i === 0) continue;
    const disabled = guesses.some(g => g.guess === i) || guesses.length >= 5 || gameWon;
    guessButtons.push(
      <View key={i} style={{ margin: 3 }}>
        <TouchableOpacity
          onPress={() => handleGuess(i)}
          disabled={disabled}
          style={[
            styles.keyButton,
            disabled && styles.keyButtonDisabled
          ]}
        >
        <Text style={styles.keyButtonText}>
          {`${i > 0 ? "+" : ""}${i}`}
        </Text>
        </TouchableOpacity>
      </View>
    );
  }

  return (
    <ScrollView contentContainerStyle={styles.container}>
      <Text style={styles.title}>Caesar Guesser</Text>

      <View style={styles.grid}>
        {gridRows}
      </View>

      <Text style={styles.message}>{message}</Text>

      <View style={styles.buttonsContainer}>
        {guessButtons}
      </View>
      
      <View style={{ marginTop: 10 }}>
        <TouchableOpacity style={styles.actionButton} onPress={newGame}>
          <Text style={styles.actionButtonText}>New Game</Text>
        </TouchableOpacity>
      </View>
      
      <TouchableOpacity 
        style={styles.actionButton} 
        onPress={() => setShowHint(!showHint)}
      >
        <Text style={styles.actionButtonText}>
          {showHint ? "Hide Hint" : "Show Hint"}
        </Text>
      </TouchableOpacity>


      <Text style={styles.score}>Score: {score}</Text>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    alignItems: 'center',
    padding: 20,
  },

  title: {
    fontSize: 24,
    marginBottom: 20,
  },

  encoded: {
    fontSize: 20,
    marginBottom: 10,
    fontWeight: 'bold',
  },

  guessRow: {
    fontSize: 18,
    marginVertical: 3,
  },

  message: {
    fontSize: 18,
    marginVertical: 10,
    fontWeight: 'bold',
  },

  buttonsContainer: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    justifyContent: 'center',
  },

  actionButton: {
    marginTop: 10,
    paddingVertical: 10,
    paddingHorizontal: 20,
    borderRadius: 8,
    backgroundColor: 'blue',
  },

  actionButtonText: {
    color: 'white',
    fontSize: 16,
    fontWeight: 'bold',
  },

  score: {
    fontSize: 20,
    marginTop: 15,
    fontWeight: 'bold',
  },

  row: {
    flexDirection: 'row',    
    justifyContent: 'center',
    marginVertical: 6,
  },

  tile: {
    width: TILE_SIZE,
    height: TILE_SIZE + 8,
    borderWidth: 1,
    marginHorizontal: 1,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'black',
  },

  tileText: {
    fontSize: 18,
    fontWeight: 'bold',
    color: 'white',
  },

  correctTile: {
    backgroundColor: 'green',
  },

  grid: {
    width: '100%',
    marginVertical: 10,
  },

  blankTile: {
    backgroundColor: 'black',
  },

  vowelTile: {
    backgroundColor: 'orange',
  },
  keyButton: {
    width: 48,
    height: 36,
    margin: 4,
    borderRadius: 6,
    backgroundColor: 'blue',
    alignItems: 'center',
    justifyContent: 'center',
  },

  keyButtonDisabled: {
    backgroundColor: 'grey',
  },

  keyButtonText: {
    color: 'white',
    fontWeight: 'bold',
  },

});

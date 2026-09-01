"use strict";

/** Encodes p using key k
 *  The string should be alphabetic+space only
 *  The key should be between -13 and +13, if ommitted a random key is generated
 *  Returns a string, vowels 2 element array
 *  The string will be all uppercase with no spaces
 *  The vowels array will have a true element where the original string has a vowel (a hint)
 *  Throws an Error if conditions are not met.
 */
export function encode(p, k = NaN){
    if ((typeof p !== "string") || !(/^[A-Za-z]+$/.test(p))){
        throw new Error("Function encode not passed a valid plaintext string");
    }
     if ((typeof k !== "number") || (k<-13) || (k>13)){
        throw new Error("Function encode passed an invalid key (must be a number) if provided");
    }
    if (Number.isNaN(k)){// second parameter not supplied as it's default NaN
        k = (Math.floor(Math.random() * 13) + 1) * (Math.random() < 0.5 ? -1 : 1);
        // k was not a number so generate a random key between -13 and 13 but excluding zero
    }

    const encoded = p.toUpperCase().split('').map(char => {
        const code = char.charCodeAt(0);
        if (code >= 65 && code <= 90) {// Uppercase letters
            return String.fromCharCode(((code - 65 + 26 + k) % 26) + 65);
        }
        else {
            throw new Error("Function encode not passed a valid plaintext string");
        }
    }).join('');

    return encoded;
}



/** Given a plain string p, getVowelMapArray returns an array such that
 *  element i is true iff there is a vowel at that position in string p.
 *  The array is the same length as the string. 
 *  Throws an Error if conditions are not met.
 */
export function getVowelMapArray(p){
    if (typeof p !== "string"){
        throw new Error("Function getVowelMapArray not passed a valid string");
    }
    const vowels = 'AEIOU';
    const vowelArray = Array.from(p.toUpperCase(), char => vowels.includes(char));
    return vowelArray;
}



/** Decodes string e using key k
 *  The string should be alphabetic only
 *  The key should be between -13 and +13
 *  Returns a string of the decode
 *  Throws an Error if conditions are not met.
 */
export function decode(e, k){
     if ((typeof e !== "string") || !(/^[A-Za-z]+$/.test(e))){
        throw new Error("Function decode not passed a valid string");
    }
     if ((typeof k !== "number") || (k<-13) || (k>13)){
        throw new Error("Function decode not passed a valid decode key");
    }
   return encode(e,-k);
}



/** get a random 12 char uppercase phrase with no spaces as a string
 * 
 *  if optional index is given then that string is returned 
 *  there are 81 phrases so 0<=index<82 is perfect 
 *  but it mods index so no worries if out of range so long as numeric
 *  Throws an Error if conditions are not met.
 */

export function getAString(index = NaN){
    if (typeof index !== "number"){
        throw new Error("Function getAString not passed a valid index");
    }

    const phrases = [
        "A QUIET MOMENT",
        "AT THE BUSSTOP",
        "AT THE HARBOUR",
        "AT THE SEASIDE",
        "AT THE STADIUM",
        "AFTERNOON TEA",
        "AUTUMN LEAVES",
        "BUS STOP CHATS",
        "CALM IN ORKNEY",
        "CANTEEN TABLE",
        "COFFEE BREAKS",
        "COUNTRY RIDES",
        "COUNTRY ROADS",
        "COZY BLANKETS",
        "DAILY COMMUTE",
        "DAILY ROUTINE",
        "DOORSTEP CHAT",
        "DRIVE TO ELGIN",
        "EARLY SUNRISE",
        "EVENING MEALS",
        "EVENING RAINS",
        "EWAN MCKAY NOW",
        "FAMILY DINNER",
        "FAMILY SUPPER",
        "FISHING BOATS",
        "FOND MEMORIES",
        "FRESH MORNING",
        "FRESHLY BAKED",
        "FRIENDLY FACE",
        "FRONT GARDENS",
        "GENTLE BREEZE",
        "GO UP BEN NEVIS",
        "GREAT COMPANY",
        "HARBOUR LIGHT",
        "HELPFUL HANDS",
        "HILLTOP VIEWS",
        "IN THE HALLWAY",
        "IN THE KITCHEN",
        "IN THE VILLAGE",
        "ISLA IS AT HOME",
        "LATE BEDTIMES",
        "LATE TRAIN AYR",
        "LIBRARY BOOKS",
        "LOCAL COUNCIL",
        "LOCAL MARKETS",
        "LOCAL STORIES",
        "MARKET SQUARE",
        "MORNING LIGHT",
        "OCEAN SUNRISE",
        "OFFICE COFFEE",
        "PAPER LETTERS",
        "PROPER SUPPER",
        "PUBLIC NOTICE",
        "QUIET LIBRARY",
        "QUIET STREETS",
        "RAIL PLATFORM",
        "REST BY THE TAY",
        "SHORELINE WAY",
        "SPRING FLOWER",
        "STAFF MEETING",
        "STONE BRIDGES",
        "STONE COTTAGE",
        "STOP IN FORFAR",
        "TALK WITH IAIN",
        "TEACHER NOTES",
        "TICKET OFFICE",
        "TO THE CARPARK",
        "TO THE LIBRARY",
        "TO THE MAILBOX",
        "TO THE POSTBOX",
        "TO THE STATION",
        "TRAIN STATION",
        "VILLAGE GREEN",
        "VISIT TO CLYDE",
        "WAITING ROOMS",
        "WALK IN HAWICK",
        "WALKING HOMES",
        "WALKING IN AYR",
        "WALKING PATHS",
        "WARM WELCOMES",
        "WINTER NIGHTS",
        "MOBILEAPPDEV"
   ];

    const i = Number.isNaN(index) ? Math.floor(Math.random() * phrases.length) : index%phrases.length;
    const rawPhrase = phrases[i];
    const noSpaces = rawPhrase.replace(/\s+/g, '').toUpperCase();
    return noSpaces;
}
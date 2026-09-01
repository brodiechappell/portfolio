import { StyleSheet } from 'react-native';

const HomeStyles = StyleSheet.create({
    container: {
        flex: 1,
    },
    map: {
        flex: 1,
    },
    calloutContainer: {
        width: 180,
        padding: 8,
    },
    calloutTitle: {
        fontWeight: '700',
        fontSize: 14,
        marginBottom: 4,
    },
    calloutDescription: {
        fontSize: 12,
        color: 'grey',
        marginBottom: 8,
    },
    calloutButton: {
        backgroundColor: 'dark blue',
        borderRadius: 6,
        paddingVertical: 6,
        alignItems: 'center',
    },
    calloutButtonText: {
        color: 'white',
        fontSize: 13,
        fontWeight: '600',
    },
    clearButton: {
        position: 'absolute',
        bottom: 32,
        alignSelf: 'center',
        backgroundColor: 'red',
        paddingHorizontal: 20,
        paddingVertical: 10,
        borderRadius: 20,
        shadowColor: 'black',
        shadowOpacity: 0.2,
        shadowRadius: 4,
        elevation: 4,
    },
    clearButtonText: {
        color: 'white',
        fontWeight: '700',
        fontSize: 14,
    },
});

export default HomeStyles;
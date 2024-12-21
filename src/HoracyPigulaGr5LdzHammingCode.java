//autor i opis
/*
Autor: Horacy Piguła
Oddział: SAN Łódź
Grupa: 5
Temat: Implementacja Kodu Hamminga w Języku JAVA

Opis: Jest to algorytm używany do korekcji błedów, które wystąpiły
w trakcie przesyłu danych cyfrowych np. przez sieć czy na nośnikach typu dyski twarde.
Może być użyty również do naprawy błędów na częściowo uszkodzonych płytach DVD itp.
Również jest on powszechnie stosowany w pamięci operacyjnej RAM  z obsługą ECC
(Error Correction Code => Kod Korekcji Błędów) używanej głównie w komputerach serwerowych
*/
//Opis programu i jego funkcji
/*
Wyjaśnienie kodu:
1.Imports: Importujemy klasę Scanner, aby umożliwić użytkownikowi wprowadzanie danych.
2.calculateParityBits: Metoda oblicza, ile bitów parzystości jest potrzebnych.
3.generateHammingCode: Ta metoda generuje kod Hamminga, wpisując dane i obliczając bity parzystości.
4.isPowerOfTwo: Metoda sprawdza, czy dana pozycja jest potęgą dwóch, co jest istotne dla pozycji bitów parzystości.
5.main: W tej metodzie program pyta użytkownika o dane binarne, które następnie są walidowane i przetwarzane w celu wygenerowania kodu Hamminga.

Używanie programu:
    1.Uruchom program.
    2.Wprowadź dane binarne składające się z 0 i 1.
    3.Otrzymasz zakodowany ciąg Hamminga jako wynik.

*/
//Opis Algorytmu i działania kodu algorytmu Hamminga
/*
 Zasada Działania Kodu Hamminga

Kod Hamminga to technika wykrywania i korekcji błędów, która umożliwia poprawne odczytywanie danych, nawet jeśli część z nich ulegnie uszkodzeniu podczas transmisji. Poniżej przedstawiam główne zasady działania tego kodu:

 1. **Bit parzystości**
- Kod Hamminga dodaje bity parzystości do oryginalnego ciągu danych.
- Bity parzystości są umieszczane w pozycjach, które są potęgami dwóch (tj. 1, 2, 4, 8, ...).
- Każdy bit parzystości kontroluje określone bity danych, a jego wartość jest ustalana tak, aby suma kontrolowanych bitów była parzysta.

 2. **Dodawanie bitów parzystości**
- Dla ciągu danych o długości `m`, liczba bitów parzystości `r` jest obliczana tak, aby spełniać warunek:
    \[
    2^r \geq m + r + 1
    \]
- Przykład: Jeśli mamy 4 bity danych, potrzebujemy 3 bity parzystości, ponieważ:
    - \(2^3 = 8\) (czyli 3 bity parzystości zapewniają kontrolę dla 4 bitów danych + 3 bity parzystości + 1).

 3. **Obliczanie bitów parzystości**
- Każdy bit parzystości oblicza się na podstawie bitów danych, które kontroluje.
- Na przykład, bit parzystości w pozycji 1 kontroluje bity w pozycjach 1, 3, 5, 7, itd.
- Wartość bitu parzystości jest ustawiana w taki sposób, aby zapewnić parzystość (suma kontrolowanych bitów modulo 2 = 0).

 4. **Odczyt kodu**
- Po przesłaniu zakodowanego ciągu, odbiorca sprawdza bity parzystości.
- Jeśli wszystkie bity parzystości są zgodne, dane są poprawne.
- Jeśli którykolwiek bit parzystości nie zgadza się, można określić, które bity są błędne.

 5. **Korekcja błędów**
- W przypadku wykrycia błędów, można znaleźć pozycję błędnego bitu.
- Pozycja błędnego bitu jest ustalana na podstawie wyników bitów parzystości. Suma pozycji, które wykazały błąd, wskazuje na konkretny bit do zmiany.
- Na przykład, jeśli bity parzystości w pozycjach 1 i 2 są błędne, to błąd znajduje się w pozycji 3 (1 + 2 = 3).

 Przykład
Załóżmy, że mamy dane binarne `1011`:
1. Obliczamy potrzebne bity parzystości (w tym przypadku 3).
2. Umieszczamy bity parzystości w odpowiednich pozycjach:
   - Ostateczny kod to `P1 P2 1 P4 0 1 1`, gdzie `P1`, `P2` i `P4` to bity parzystości.
3. Obliczamy wartości `P1`, `P2` i `P4` na podstawie bitów danych.
4. Ostateczny wynik może wyglądać tak: `1011010` (gdzie `1011` to dane, a `010` to bity parzystości).

### Podsumowanie
Kod Hamminga jest skuteczną metodą wykrywania i korekcji błędów w danych. Jego zasada działania opiera się na dodawaniu bitów parzystości oraz sprawdzaniu ich wartości w celu lokalizacji i korekcji błędów.
 */


import java.util.Scanner; // Importowanie klasy Scanner do odczytu danych wejściowych od użytkownika
//import java.io.*;


public class HoracyPigulaGr5LdzHammingCode {



    // Metoda do obliczania wysokości bitów parzystości
    public static int calculateParityBits(int m) {
        int r = 0; // Inicjalizacja liczby bitów parzystości
        while (Math.pow(2, r) < (m + r + 1)) { // Warunek do wyznaczenia liczby bitów parzystości
            r++; // Zwiększanie liczby bitów parzystości
        }
        return r; // Zwracanie liczby bitów parzystości
    }

    // Metoda do generowania kodu Hamminga
    public static String generateHammingCode(String data) {
        int m = data.length(); // Długoacść danych wejściowych
        int r = calculateParityBits(m); // Obliczanie liczby bitów parzystości
        int totalLength = m + r; // Całkowita długość zakodowanych danych
        char[] hammingCode = new char[totalLength]; // Tablica do przechowywania kodu Hamminga

        // Wstawianie danych do odpowiednich pozycji
        int j = 0;
        for (int i = 0; i < totalLength; i++) {
            if (isPowerOfTwo(i + 1)) { // Sprawdzanie, czy pozycja jest potęgą dwóch
                hammingCode[i] = '0'; // Wstawianie bitu parzystości
            } else {
                hammingCode[i] = data.charAt(j++); // Wstawianie bitu danych
            }
        }

        // Obliczanie bitów parzystości
        for (int i = 0; i < r; i++) {
            int parityPosition = (int) Math.pow(2, i) - 1; // Obliczanie pozycji bitu parzystości
            int parityValue = 0; // Inicjalizacja wartości bitu parzystości

            // Obliczanie wartości bitu parzystości
            for (int k = parityPosition; k < totalLength; k++) {
                if (((k + 1) & (parityPosition + 1)) != 0) { // Sprawdzanie, które bity są objęte parzystością
                    parityValue ^= (hammingCode[k] - '0'); // Obliczanie XOR
                }
            }

            hammingCode[parityPosition] = (char) (parityValue + '0'); // Ustawianie bitu parzystości
        }

        return new String(hammingCode); // Zwracanie zakodowanego ciągu
    }

    // Metoda do sprawdzania, czy liczba jest potęgą dwóch
    public static boolean isPowerOfTwo(int x) {
        return (x & (x - 1)) == 0; // Warunek sprawdzający potęgę dwóch
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Inicjalizacja skanera do odczytu danych od użytkownika
        System.out.print("Wprowadź dane binarne[czyli tylko kombinacja liczb 0 i 1]: "); // Prośba o wprowadzenie danych
        String inputData = scanner.nextLine(); // Odczytanie danych od użytkownika

        // Sprawdzanie, czy dane są binarne
        if (!inputData.matches("[01]+")) {
            System.out.println("Wprowadź poprawne dane binarne (0 lub 1)"); // Komunikat błędu
            return; // Zakończenie programu
        }

        // Generowanie kodu Hamminga
        String hammingCode = generateHammingCode(inputData);
        System.out.println("Zakodowany ciąg Hamminga: " + hammingCode); // Wyświetlenie zakodowanego ciągu
        scanner.close(); // Zamknięcie skanera
        
        System.out.println("Horacy Piguła, Indeks 122696, Grupa 5, SAN Łódź, Polska, kod Hamminga");
    }
}

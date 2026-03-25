# Metrika, pregled i statička analiza

## LOC metrike

Ukupan broj linija koda (LOC): 154

LOC je izračunat korišćenjem PowerShella, analizom svih .java fajlova u projektu komandom: 
(Get-ChildItem -Recurse -Include *.java | Get-Content | Measure-Object -Line).Lines

---

## Statička analiza i Code Review

Za neformalan pregled koda i njegovu statičku analizu (bez pokretanja koda) koristio sam IntelliJ IDEA Community Edition.

Rezultat pregleda koda je sledeći:
Java: 4 upozorenja i 3 slaba upozorenja

Code Maturity: 1 slabo upozorenje
Metoda se može izvući inspekcijom
Predlaže izdvajanje fragmenata koda u posebnu metodu kako bi kod bio jasniji. Ova inspekcija ima niz heuristika za odabir dobrih kandidata za izdvajanje, uključujući sledeće.
Izdvojeni fragment nema nelokalni tok upravljanja
Izdvojeni fragment ima tačno jednu izlaznu promenljivu
Ne postoje slične upotrebe izlazne promenljive unutar izdvojenog fragmenta i van njega
Izdvojeni fragment ima samo nekoliko ulaznih parametara (ne više od tri podrazumevano; konfigurisano sa opcijom inspekcije)
Izdvojeni fragment nije manji od konfigurisane dužine (500 znakova podrazumevano), ali nije veći od 60% tela metode koja ga sadrži
	Calculator - 1 slabo upozorenje:
	- Moguće je izdvojiti metodu koja vraća 'operationList' iz dugačke okolne metode

Control flow issues: 2 slaba upozorenja
  	'if' naredba sa identičnim granama ili zajedničkim delovima.
	Calculator - 2 slaba upozorenja
	- Zajednički deo se može izdvojiti iz 'if'.
	- Zajednički deo se može izdvojiti iz 'if'.

Data flow: 1 upozorenje
Redundantna lokalna promenljiva:
Prijavljuje nepotrebne lokalne promenljive koje ne doprinose razumljivosti metode, uključujući:
Lokalne promenljive koje se odmah vraćaju.
Lokalne promenljive koje se odmah dodeljuju drugoj promenljivoj, a zatim se ne koriste.
Lokalne promenljive koje uvek imaju istu vrednost kao druga lokalna promenljiva ili parametar.
	Calculator - 1 upozorenje
	- Lokalna promenljiva 'textResult' je suvišna.

Java language level migration aids: 2 upozorenja
Java 21: 1 upozorenje
Može se koristiti metod SequencedCollection.
Pozivi API metoda za kolekciju izveštaja koji se mogu pojednostaviti korišćenjem SequencedCollection metoda.
Podržane su sledeće konverzije:
list.add(0, element) → list.addFirst(element);
list.get(0) → list.getFirst();
list.get(list.size() - 1) → list.getLast();
list.remove(0) → list.removeFirst();
list.remove(list.size() - 1) → list.removeLast();
collection.iterator().next() → collection.getFirst(); 
	Calculator: 1 upozorenje
	- Može se zameniti pozivom funkcije 'getFirst()'

Java 5: 1 upozorenje
Petlja 'for' može se zameniti poboljšanom petljom: 1 upozorenje
Izveštaji o petljama 'for' koje iteriraju kroz kolekcije ili nizove i mogu se automatski zameniti poboljšanom petljom 'for' (sintaksa iteracije foreach).
	Calculator: 1 upozorenje
	- Petlja 'for' može se zameniti poboljšanom petljom 'for'.

Verbose or redundant code constructs: 1 upozorenje
Unnecessary 'return' statement: 1 upozorenje
Nepotrebna naredba „return“
Prijavljuje naredbe return na kraju konstruktora i metoda koje vraćaju void. Ove naredbe su suvišne i mogu se bezbedno ukloniti.
Ova inspekcija ne prikazuje JSP datoteke.
	Calculator: 1 upozorenje

# TEST RESULTS

## Opis testiranja

Izvršeno je sistemsko testiranje (black-box testing) kalkulator aplikacije.  
Testiranje je obavljeno unosom različitih aritmetičkih izraza i analizom dobijenih rezultata.

Cilj testiranja bio je da se proveri:
- tačnost računanja
- poštovanje prioriteta operacija
- ponašanje pri neispravnim unosima
- identifikacija potencijalnih grešaka i ograničenja sistema

---

## Pozitivni testovi (ispravni unosi)

| Ulaz     | Očekivani rezultat | Dobijeni rezultat | Status |
|----------|--------------------|-------------------|--------|
| 4+5      |  9                 | 9.0               | ✅     |
| 10+5*4+3 | 33                 | 33.0              | ✅     |
| 10*2+5   | 25                 | 25.0              | ✅     |
| 20/5+2   | 6                  | 6.0               | ✅     |
| 2+3*4    | 14                 | 14.0              | ✅     |
| 2.5+3.5  | 6.0                | 6.0               | ✅     |
| -5+3     | -2                 | -2.0              | ✅     |
| +5       |  5                 | 5.0               | ✅     |
| 5+       |  5                 | 5.0               | ✅     | 
| 5 + 3    | 8                  |8.0                | ✅     |

### Zaključak:
- Kalkulator ispravno izvršava osnovne aritmetičke operacije
- Poštuje prioritet operacija (* i / pre + i -)
- Podržava decimalne brojeve
- Podržava razmake u izrazu

---

## Negativni testovi (neispravni unosi)

| Ulaz | Očekivano | Dobijeno | Status |
|------|----------|----------|--------|
| 4++5 | ERROR    | ERROR    | ✅     |
| abc  | ERROR    | ERROR    | ✅     |
| ""   | ERROR    | ERROR    | ✅     |


### Zaključak:
- Aplikacija pravilno detektuje nevalidne izraze u većini slučajeva

---

## Edge case testovi (kritični scenariji)

### 1. Deljenje sa nulom
 
| Ulaz | Očekivano | Dobijeno | Status |
|------|-----------|----------|--------|
| 5/0  | ERROR     | Infinity | ❌     |

Problem:  
Umesto greške, aplikacija vraća `Infinity`.

---

### 2. Kombinacija operatora

| Ulaz | Očekivano | Dobijeno | Status |
|------|-----------|----------|--------|
| 5+-3 | 2         | ERROR    | ❌     |

Problem:  
Aplikacija ne podržava kombinacije operatora.

---

## Jedinični test (Unit test)

Primer unit testa za proveru metode `Calculate`:

```java
public class CalculatorTest {

    public static void main(String[] args) {

        testCalculateExpression();

    }

    static void testCalculateExpression() {
        String expected = "14.0";
        String actual = Calculator.Run("2+3*4");

        if (expected.equals(actual)) {
            System.out.println("TEST PASSED ");
        } else {
            System.out.println("TEST FAILED ");
            System.out.println("Expected: " + expected);
            System.out.println("Actual: " + actual);
        }
    }
}
	

---



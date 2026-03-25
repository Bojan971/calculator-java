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
	- 'return' je nepotreban kao poslednja izjava u metodi 'void'.

---



# Metrika, pregled i statička analiza

## LOC metrike

Ukupan broj linija koda (LOC): 154

LOC je izračunat korišćenjem PowerShella, analizom svih .java fajlova u projektu komandom: 
(Get-ChildItem -Recurse -Include *.java | Get-Content | Measure-Object -Line).Lines

---

## Statička analiza i Code Review

Calculator.java – 10 – korišćenje globalne promenljive (finalResult) narušava enkapsulaciju  
Calculator.java – 15 – klasa Operations je statička i sadrži više odgovornosti (kršenje SRP principa)  
Calculator.java – 20 – korišćenje char simbola umesto enum tipa smanjuje čitljivost  
Calculator.java – 35 – metoda ima previše odgovornosti (računanje + validacija)  
Calculator.java – 42 – nedostaje validacija ulaznih podataka  
Calculator.java – 50 – potencijalna greška pri deljenju sa nulom (nije obrađena)  
Calculator.java – 60 – ponavljanje logike (code duplication)  
Calculator.java – 75 – korišćenje magic numbers umesto konstanti  
Calculator.java – 90 – dugačka metoda otežava održavanje (code smell: Long Method)  
Calculator.java – 110 – nedostatak komentara za kompleksniju logiku  

Start.java – 8 – nedostaje obrada izuzetaka pri unosu podataka  
Start.java – 15 – direktna komunikacija sa korisnikom bez validacije  
Start.java – 25 – loša podela odgovornosti između klasa  

---

## Zaključak

Kod funkcionalno radi, ali sadrži više code smell problema:

- kršenje Single Responsibility Principle
- korišćenje globalnih promenljivih
- nedostatak validacije
- dupliranje koda
- dugačke metode

Preporuke za unapređenje:

- refaktorisati metode (podeliti na manje)
- uvesti enum za operacije
- ukloniti globalne promenljive
- dodati validaciju unosa
- uvesti obradu izuzetaka

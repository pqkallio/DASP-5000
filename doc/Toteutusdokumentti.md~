# Toteutusdokumentti

## Ohjelman yleisrakenne

Hierarkiakaavio toteutetaan myöhemmin, mutta tässä yleistä tietoa toteutetuista luokista:
- AudioController-luokka, jolla hallitaan äänitiedoston avaamista, muokkausta ja tallentamista
- AudioContainer-luokka, jossa säilötään tiedostosta parsittua äänidataa sekä hallinnollista tietoa (kanavien lukumäärä, audioanalyysi, bittisyys, näytteenottotaajuus etc.)
- AudioAnalysis-luokka, johon säilötään audiodatasta analysoituja tietoja
- ByteConverter-luokka, jolla muunnetaan audiotiedoston tavudataa muokattavaksi äänidataksi ja äänidataa jälleen tavuiksi
- RiffParser-luokka, jolla muunnetaan RIFF WAVE -tiedosto AudioContainer-olioksi
- RiffBuilder-luokka, jolla muunnetaan AudioContainer-olio RIFF WAVE -tiedostoksi
- AudioProcessor-rajapinta, jonka kaikki ääntä muokkaavat luokat toteuttavat
- DecibelConverter-luokka, jolla muunnetaan dBFS-arvoja (decibels relative to full scale) ääninäytteiksi ja ääninäytteitä dBFS-arvoiksi
- DynamicArray-luokka on dynaaminen ja geneerinen taulukko

## Saavutetut aika- ja tilavaativuudet

Pseudokoodi toteutetaan myöhemmin.

| Algoritmi     | Aikavaativuus | Tilavaativuus |
|---------------|---------------|---------------|
| Analyzer      | O(n)          | O(1)          |
| Mixer         | O(n)          | O(n)          |
| Normalizer    | O(n)          | O(1)          |
| PhaseSwitcher | O(n)          | O(1)          |
| Reverser      | O(n)          | O(1)          |

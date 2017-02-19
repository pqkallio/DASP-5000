# Viikkoraportti 5

Käytetty noin 25 tuntia.

Suurimmat edistysaskeleet tällä viikolla:
- RiffBuilder luokalla saa nyt luotua muokattavasta audiodatasta wav-tiedoston
- RiffParser toimii wav-tiedostojen avaamisessa muokattavaksi äänidataksi
- Gate-algoritmi kirjoitettu.
- testausprosenttia nostettu
- AudioProcessor-luokka on nyt abstrakti luokka eikä rajapinta ja kaikki prosessointiluokat perivät sen, jotka on myös siis refaktoroitu
- alkeellinen graafinen käyttöliittymä tehty, joten käyttäjä voi nyt avata audiotiedostoja, muokata niitä ja tallentaa tiedostoja
- kaikki algoritmit toimivat nyt sekä mono- että stereoaudion kanssa

Seuraavaksi jatkan algoritmien toteuttamista, otan mukaan ainakin kompressorin. Lisäksi tutustun Fourier-analyysin toteuttamiseen sekä audion reaaliaikaiseen muokkaukseen ja koitan saada näitä toteutettua vielä projektin puitteissa.

Cobertura- ja Pitest-raportit sekä JavaDoc löytyvät jälleen doc-kansiosta. Käyttöliittymään liittyviä luokkia ja metodeja ei ole dokumentoitu eikä testattu, ainoastaan ohjelmalogiikka.

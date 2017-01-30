# Viikkoraportti 2

Käytetty noin 15 tuntia.

Toisella viikolla aloitin toteutusta sekä testien ja JavaDocin tuottamista. JavaDoc on tehty kaikille julkisille metodeille ja testien kattavuus on noin 60 prosenttia. Testien tekemisessä haasteita aiheutti vielä tiedostoja ja AudioInputStreameja käsittelevät metodit. Näiden testien toteuttamisessa saatan kaivata hieman apua, saapunen siis torstaina pajaan.

Suurimmat edistysaskeleet tällä viikolla:
- tiedoston avaaminen
- tiedoston tavuvirran tallentaminen sanoiksi (ottaa huomioon myös onko kyseessä big- vai little-endian sekä näytteen bittisyyden)
- audio datan analysointi (testattu että saadaan samanlaisia dBFS-arvoja audiotiedostoista kuin valideilla DAWeilla)
- dynaaminen taulukko sanojen tallentamista varten

Seuraavaksi toteutan ohjelmaani äänitiedoston tallentamisen ja aloitan signaalinmuokkausalgoritmien toteuttamisen. Testien ja dokumentoinnin toteuttaminen jäi kuitenkin hieman viime tinkaan joten koitanpa jälleen tällä viikolla toteuttaa JavaDocit heti metodeja kirjoittaessa.

Cobertura- ja Pitest-raportit sekä JavaDoc löytyvät doc-kansiosta.

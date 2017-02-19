# DASP-5000

Nyt yksinkertaisella graafisella käyttöliittymällä varustettu.

Aja ohjelma ja tämän jälkeen voit avata WAV-tiedostoja muokattavaksi. Avaaminen ja tiedoston tallentaminen tapahtuu File-valikon kautta ja tiedostojen muokkaaminen Edit-valikon kautta.

Avattuasi halutun tiedoston tai tiedostot, voit valita checkboxilla tiedostot, joita haluat muokata ja tallentaa.

Äänenmuokkausalgoritmit toimivat seuraavasti

1. Gate
  * suodattaa kaikki määritettyä äänenvoimakkuutta vaimeammat äänet pois
  * threshold: tämä annetaan dBFS (decibels relative to full scale) muodossa double-arvona, eli 0 dBFS tarkoittaa maksimiäänenvoimakkuutta, -6 dBFS puolet maksimista, -12 dBFS neljäsosa maksimista jne., logaritmisesti
  * attack on millisekunneissa annettava arvo, jonka aikana äänenvoimakkuuden ylittäessä kynnysarvon palataan ääniraidan alkuperäiseen äänenvoimakkuuteen
  * sustain on millisekunneissa annettava arvo, jonka ajan ei välitetä äänenvoimakkuuden painumisesta kynnysarvon alapuolelle (tämä johtuu siitä, että koska ääni on jatkuvaa vaihtelua plus- ja miinus-arvojen välillä, mahtuu huippujen väleihin paljon alueita, joissa alitetaan kynnyarvo sen takia, että ollaan menossa kohti seuraavaa huippua, eli tällöin näitä vaihteluja ei suodateta pois)
  * release on millisekunneissa annettava arvo, jonka aikana äänenvoimakkuuden alittaessa kynnysarvon vaimennetaan raita kokonaan
  
2. Mix
  * Miksaa valitut signaalit yhdeksi mono- tai stereoraidaksi ja avaa uuden ääniraidan muokattavaksi ja tallennettavaksi

3. Normalize
  * Normalisoi ääniraidan äänenvoimakkuuden niin, että ääniraidan uusiksi maksimitaso on haluttu dBFS-arvo

4. Phase switch
  * vaihtaa kaikkien näytteiden vaiheen, eli tekee miinusmerkkisistä näytteistä plusmerkkisiä ja päin vastoin
5. Reverse
  * kääntää äänitiedoston ympäri, joten se soi tämän jälkeen lopusta alkuun eikä alusta loppuun
  
_Huom!_ Tällä hetkellä ohjelma avaa ainoastaan pakkaamattomia PCM WAV -tiedostoja, joten floating point precision wav -tiedostojen avaaminen ei onnistu, eikä myöskään pakattujen äänitiedostojen tai aiff-tiedostojen avaaminen.

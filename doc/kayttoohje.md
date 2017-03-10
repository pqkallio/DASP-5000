# Käyttöohje

## Tiedostojen avaaminen

Tiedostojen avaaminen tapahtuu valitsemalla _File_-valikosta _Open_. Tässä vaiheessa ohjelma tukee vain Microsoft Riff WAV-tiedostoja, joissa ei ole muita otsakekenttiä kuin _format_ ja _data_. Jos tiedosto ei aukea, avaa se ensin esim. Audacityssä ja tallenna se uudestaan wav-tiedostoksi.

## Tiedostojen muokkaaminen

Avattu tiedosto avautuu käyttöliittymään. Valitse avattu tiedosto ja valitse tämän jälkeen _Edit_-valikosta käytettävä algoritmi.

### Delay

Delay-efekti toistaa (kaiuttaa) äänisignaalia halutulla tavalla.
Repeat: toistojen määrä
Delay: aika signaalin ja toiston välillä millisekunteissa
Decay: toiston vaimeneminen

### Gate

Gate-efekti poistaa kaikki haluttua tasoa vaimeammat alueet äänisignaalista.
Threshold: kynnysarvo, jota vaimeampi signaali poistetaan
Attack: kun signaali nousee kynnysarvon yläpuolelle, kuinka nopeasti vaimentaminen lopetetaan
Sustain: jos signaali laskee kynnysarvon alapuolelle, kuinka kauan odotetaan, ennen kuin vaimentaminen aloitetaan
Release: kun sustain-arvo on ylitetty, kuinka nopeasti signaali vaimennetaan

### Mix

Miksaa valitut äänisignaalit uudeksi äänisignaaliksi ja avaa uuden miksatun signaalin käyttöliittymään.

### Normalize

Etsii signaalin voimakkaimman kohdan ja muuttaa kaikki äänisignaalin näytteitä niin, että voimakkaimman kohdan äänenvoimakkuus on parametrina annettu arvo.

### Phase switch

Muuttaa kaikkien valitun äänisignaalin näytteiden arvot niiden vasta-arvoiksi (esim. -32 -> 32 ja 45 -> -45).

### Reverse

Kääntää äänisignaalin ympäri, niin että se soi tämän jälkeen lopusta alkuun.

### Loudness Analysis

Suorittaa äänisignaalille Fast Fourier Transform -algoritmin ja tuottaa algoritmin tuottamasta datasta kuvan, jossa värit vaihtelevat hiljaisimmasta signaalista voimakkaimpaan välillä musta-sininen-violetti-punainen-oranssi-keltainen. Analyysi ei valitettavasti tässä vaiheessa tulosta hertsejä kuvaajaan, vaikkakin algoritmi tuottaa tämän tiedon. Piirretty kuvaaja sisältää lisäksi vain vasemman kanavan tiedon, vaikka itse algoritmi tuottaa analyysin kaikille kanaville.

## Tiedostojen tallentaminen

Tiedostojen tallentaminen tapahtuu valitsemalla halutut avatut äänisignaalit ja valitsemalla _File_-valikosta _Save_.

## Tunnettuja puutteita

Tiedoston avaaminen on toteutettu avaamalla jokainen ääninäyte dynaamiseen taulukkoon, jotta myöhemmin näytteiden lisääminen olisi yksinkertaista. Tästä johtuen kuitenkin tiedoston avaanminen on hitaampaa ja suurikokoisten tiedostojen (esim. 10 minuuttia stereoääntä) avaaminen ei onnistu. Tämä olisi ollut parempi toteuttaa esim. rengaslistana tai tavallisena taulukkona.

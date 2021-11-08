package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KassapaateTest {
    Kassapaate kassapaate;
    Maksukortti kortti;

    @Before
    public void setUp() {
        kassapaate = new Kassapaate();
        kortti = new Maksukortti(10000);
    }

    @Test
    public void maukkaanOstaminenOnnistuu() {
        kassapaate.syoMaukkaasti(kortti);
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void edullisenOstaminenKateisellaOnnistuu() {
        kassapaate.syoEdullisesti(300);
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(100240, kassapaate.kassassaRahaa());
    }

    @Test
    public void maukkaanOstaminenKateisellaOnnistuu() {
        kassapaate.syoMaukkaasti(500);
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
        assertEquals(100400, kassapaate.kassassaRahaa());
    }

    @Test
    public void myytyjenMaukkaidenMaaraEiKasvaJosMaksuEiOleriittava() {
        kassapaate.syoMaukkaasti(100);
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
        assertEquals(100000, kassapaate.kassassaRahaa());
    }

    @Test
    public void myytyjenEdullistenMaaraEiKasvaJosMaksuEiOleriittava() {
        kassapaate.syoEdullisesti(100);
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(100000, kassapaate.kassassaRahaa());
    }

    @Test
    public void maukkaanOstaminenKortillaOnnistuu() {
        boolean vastaus = kassapaate.syoMaukkaasti(kortti);
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
        assertEquals(100000, kassapaate.kassassaRahaa());
        assertEquals(true, vastaus);
    }

    @Test
    public void edullisenOstaminenKortillaOnnistuu() {
        boolean vastaus = kassapaate.syoEdullisesti(kortti);
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(100000, kassapaate.kassassaRahaa());
        assertEquals(true, vastaus);
    }

    @Test
    public void myytyjenEdullistenMaaraEiKasvaJosKortillaEiSaldoa() {
        kortti.otaRahaa(9900);
        boolean vastaus = kassapaate.syoEdullisesti(kortti);
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(100000, kassapaate.kassassaRahaa());
        assertEquals(false, vastaus);
    }

    @Test
    public void myytyjenMaukkaidenMaaraEiKasvaJosKortillaEiSaldoa() {
        kortti.otaRahaa(9900);
        boolean vastaus = kassapaate.syoMaukkaasti(kortti);
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
        assertEquals(100000, kassapaate.kassassaRahaa());
        assertEquals(false, vastaus);
    }

    @Test
    public void kortinSaldonLataaminenToimiiOikein() {
        kassapaate.lataaRahaaKortille(kortti, 20000);
        assertEquals("saldo: 300.0", kortti.toString());
        assertEquals(120000, kassapaate.kassassaRahaa());
    }

    @Test
    public void negatiivisenSummanLataaminenEiOleMahdollista() {
        kassapaate.lataaRahaaKortille(kortti, -20000);
        assertEquals("saldo: 100.0", kortti.toString());
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
}

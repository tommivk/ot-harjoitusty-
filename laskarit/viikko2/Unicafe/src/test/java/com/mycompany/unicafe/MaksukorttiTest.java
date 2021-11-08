package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti != null);
    }

    @Test
    public void kortinAlkuSaldoOnoikea() {
        assertEquals("saldo: 0.10", kortti.toString());
    }

    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(100);
        assertEquals("saldo: 1.10", kortti.toString());
    }

    @Test
    public void rahanOttaminenToimiiOikeinKunSaldoaOnTarpeeksi() {
        kortti.otaRahaa(5);
        assertEquals("saldo: 0.5", kortti.toString());
    }

    @Test
    public void saldoEiMuutuKunOtetaanLiikaaRahaa() {
        kortti.otaRahaa(50);
        assertEquals("saldo: 0.10", kortti.toString());
    }

    @Test
    public void otaRahaaPalauttaaTrueKunSaldoaOnTarpeeksi() {
        assertEquals(true, kortti.otaRahaa(10));
    }

    @Test
    public void otaRahaaPalauttaaFalseKunSaldoaEiOleRiittävästi() {
        assertEquals(false, kortti.otaRahaa(20));
    }
}

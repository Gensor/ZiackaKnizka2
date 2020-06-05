package com.example.ziackaknizka2;

final class MyContract {

    static class Osoba{
        static final String TABLE_NAME = "osoby";
        static final String COL_MENO = "meno";
        static final String COL_PRIEZVISKO = "priezvisko";
    }
    static class Ucitel{
        static final String TABLE_NAME = "ucitelia";
        static final String COL_OSOBA = "osoba";
    }
    static class Ziak{
        static final String TABLE_NAME = "ziaci";
        static final String COL_OSOBA = "osoba";
        static final String COL_HODNOTENIE = "hodnotenie";
        static final String COL_HODNOTENIE_NAZOV = "nazov_hodnotenia";
    }
    static class Predmet{
        static final String TABLE_NAME = "predmety";
        static final String COL_NAZOV = "nazov";
        static final String COL_POPIS = "popis";
        static final String COL_OSNOVA = "osnova";
    }
    static class UcitelovPredmet{
        static final String TABLE_NAME = "ucitelove_predmety";
        static final String COL_UCITEL = "ucitel";
        static final String COL_PREDMET = "predmet";
        static final String COL_TRIEDA = "trieda";
        static final String COL_ZIAK = "ziak";
    }
}

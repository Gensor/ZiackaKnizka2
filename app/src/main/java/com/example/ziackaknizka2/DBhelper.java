package com.example.ziackaknizka2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class DBhelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 14;

    private static final String DATABASE_NAME = "ziackaKnizka.db";
    private static final int POCET_GENEROVANYCH_LUDI = 150;
    private static final int POCET_GENEROVANYCH_UCITELOV = 15;
    private static final int POCET_GENEROVANYCH_TRIED = 7;
    private static final int POCET_PRIRADENYCH_PREDMETOV = 4;
    private SQLiteDatabase db = getWritableDatabase();




    DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println(" - ON-CREATE - ");
        this.db=db;
    //na id pouzivam ROWID
        String sqlCreate_osoba = "CREATE TABLE " +
                MyContract.Osoba.TABLE_NAME + " ( " +
                MyContract.Osoba.COL_MENO + " text not null, " +
                MyContract.Osoba.COL_PRIEZVISKO + " text not null ) ";
        db.execSQL(sqlCreate_osoba);
        String sqlCreate_trieda = "CREATE TABLE " +
                MyContract.Trieda.TABLE_NAME + " ( " +
                MyContract.Trieda.COL_NAZOV + " text not null ) ";
        db.execSQL(sqlCreate_trieda);
        String sqlCreate_ucitelia = "CREATE TABLE " +
                MyContract.Ucitel.TABLE_NAME + " ( " +
                MyContract.Ucitel.COL_OSOBA + " text not null ) ";
        db.execSQL(sqlCreate_ucitelia);
        String sqlCreate_ziaci = "CREATE TABLE " +
                MyContract.Ziak.TABLE_NAME + " ( " +
                MyContract.Ziak.COL_OSOBA + " int not null, " +
                MyContract.Ziak.COL_TRIEDA + " int not null) ";
        db.execSQL(sqlCreate_ziaci);
        String sqlCreate_predmety = "CREATE TABLE " +
                MyContract.Predmet.TABLE_NAME + " ( " +
                MyContract.Predmet.COL_NAZOV + " text not null, " +
                MyContract.Predmet.COL_POPIS + " text, " +
                MyContract.Predmet.COL_OSNOVA + " text )";
        db.execSQL(sqlCreate_predmety);
        String sqlCreate_ucitelovePredmety = "CREATE TABLE " +
                MyContract.UcitelovPredmet.TABLE_NAME + " ( " +
                MyContract.UcitelovPredmet.COL_UCITEL + " int not null, " +
                MyContract.UcitelovPredmet.COL_PREDMET + " int not null, " +
                MyContract.UcitelovPredmet.COL_TRIEDA + " text not null) ";
        db.execSQL(sqlCreate_ucitelovePredmety);
        String sqlCreate_hodnotenie = "CREATE TABLE " +
                MyContract.Hodnotenie.TABLE_NAME + " ( " +
                MyContract.Hodnotenie.COL_NAZOV + " text not null, " +
                MyContract.Hodnotenie.COL_BODY + " int not null, " +
                MyContract.Hodnotenie.COL_UCITELOVPREDMET + " int not null, " +
                MyContract.Hodnotenie.COL_ZIAK +" int not null) ";
        db.execSQL(sqlCreate_hodnotenie);

        generujDatabazu();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println(" - ON-UPDATE - ");
        db.execSQL("DROP TABLE IF EXISTS "+ MyContract.UcitelovPredmet.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ MyContract.Ucitel.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ MyContract.Predmet.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ MyContract.Ziak.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ MyContract.Osoba.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ MyContract.Trieda.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ MyContract.Hodnotenie.TABLE_NAME);
        onCreate(db);
    }




   private ArrayList<Ucitel> getVsetkychUcitelov(){

        ArrayList<Ucitel> celaZborovna = new ArrayList<>();

        for (int id_ucitela = 1; id_ucitela <= getVelkostTabulky(MyContract.Ucitel.TABLE_NAME); id_ucitela++){
            Ucitel ucitel = getUcitel(id_ucitela);
            celaZborovna.add(ucitel);
        }
        return celaZborovna;
    }

     ArrayList<Trieda> getVsetkyTriedy() {

        ArrayList<Trieda> triedy = new ArrayList<>();
        String[] stlpce = {"rowid", MyContract.Trieda.COL_NAZOV};

        Cursor c = db.query(MyContract.Trieda.TABLE_NAME,stlpce,null,null,null,null,null);
        if(c.moveToFirst()){
            do{
                Trieda trieda = new Trieda(
                        c.getInt(0),
                        c.getString( c.getColumnIndex(MyContract.Trieda.COL_NAZOV)));
                triedy.add(trieda);
            }while(c.moveToNext());
        }
        c.close();
        return triedy;

    }

    ArrayList<Predmet> getVsetkyPredmety(){

        ArrayList<Predmet> predmety = new ArrayList<>();

        String[] stlpce = {"rowid",MyContract.Predmet.COL_NAZOV,MyContract.Predmet.COL_POPIS, MyContract.Predmet.COL_OSNOVA};

        Cursor c = db.query(MyContract.Predmet.TABLE_NAME,stlpce,null,null,null,null,null);
        if(c.moveToFirst()){
            do{
                Predmet predmet = new Predmet(
                    c.getInt(0),
                    c.getString( c.getColumnIndex(MyContract.Predmet.COL_NAZOV)),
                    c.getString( c.getColumnIndex(MyContract.Predmet.COL_POPIS)),
                    c.getString( c.getColumnIndex(MyContract.Predmet.COL_OSNOVA)));
                predmety.add(predmet);

            }while(c.moveToNext());
        }
        c.close();
        return predmety;

    }

    public void addUcitelovyPredmet(Ucitel ucitel , Predmet predmet, Trieda trieda){
        if(ucitel==null||predmet==null||trieda==null){
            System.out.println("\n v adducitelovpredmet mi prisiel null\n");
            return;
        }else {
            UcitelovPredmet ucitelovPredmet = new UcitelovPredmet(654654,ucitel, predmet, trieda);
            if(isUcitelovPredmet(ucitelovPredmet)){
                System.out.println("taky predmet ucitel ma s touto triedou");
                return;
            }
        }

        int id_ucitel = ucitel.getId();
        int id_predmet = predmet.getId();
        int id_trieda = trieda.getID();

        String sql = "INSERT INTO " +
                MyContract.UcitelovPredmet.TABLE_NAME +
                " (" + MyContract.UcitelovPredmet.COL_UCITEL + ", " +
                MyContract.UcitelovPredmet.COL_PREDMET + ", " +
                MyContract.UcitelovPredmet.COL_TRIEDA +")" +
                " VALUES (?, ?, ?)";
        SQLiteStatement stm = db.compileStatement(sql);

        stm.bindLong(1,id_ucitel);
        stm.bindLong(2,id_predmet);
        stm.bindLong(3,id_trieda);
        long rowid = stm.executeInsert();

    }

    boolean isUcitelovPredmet(UcitelovPredmet ucitelovPredmet){

        String sqlQuery = "SELECT * FROM " + MyContract.UcitelovPredmet.TABLE_NAME +
                " WHERE "+ MyContract.UcitelovPredmet.COL_UCITEL +" = ? AND " +
                MyContract.UcitelovPredmet.COL_PREDMET + " = ? AND " +
                MyContract.UcitelovPredmet.COL_TRIEDA + " = ?";
        String[] selectionArgs = {""+ucitelovPredmet.getUcitel().getId(),""+ucitelovPredmet.getPredmet().getId(),""+ucitelovPredmet.getTrieda().getID()};
        Cursor c = db.rawQuery(sqlQuery, selectionArgs);
        int count = c.getCount();
        c.close();

        if(count>1) System.out.println("CHYBA : mas v db duplicitu");
        return count>=1;

    }
    public void deleteUcitelovPredmet(UcitelovPredmet predmet) {
        db.delete(MyContract.UcitelovPredmet.TABLE_NAME,"rowid = "+predmet.getId(),null);
    }

    public void deleteHodnotenie(Hodnotenie hodnotenie) {
        db.delete(MyContract.Hodnotenie.TABLE_NAME,"rowid = "+hodnotenie.getId(),null);
    }

    public void addOsoba(Osoba osoba) {
        if(osoba==null)return;

        String sql = "INSERT INTO osoby (meno, priezvisko) VALUES (?, ?)";
        SQLiteStatement stm = db.compileStatement(sql);

        stm.clearBindings();
        stm.bindString(1,osoba.getMeno());
        stm.bindString(2,osoba.getPriezvisko());
        long rowid = stm.executeInsert();
    }

    public void addUcitel(Osoba osoba) {
        if(osoba==null)return;


        String sql = "INSERT INTO " +
                MyContract.Ucitel.TABLE_NAME +
                " (" + MyContract.Ucitel.COL_OSOBA +  ") " +
                "VALUES (?)";
        SQLiteStatement stm = db.compileStatement(sql);

        stm.clearBindings();
        stm.bindLong(1,osoba.getId());
        long rowid = stm.executeInsert();

    }

    public void addTrieda(Trieda trieda){
        if(trieda==null)return;

        String sql = "INSERT INTO "+ MyContract.Trieda.TABLE_NAME +" (" +
                MyContract.Trieda.COL_NAZOV+ ")" +
                " VALUES (?)";
        SQLiteStatement stm = db.compileStatement(sql);

        stm.clearBindings();
        stm.bindString(1,trieda.getNazov());
        long rowid = stm.executeInsert();
    }

    public void addHodnotenie(Hodnotenie hodnotenie){
        if(hodnotenie==null){
            System.out.println("chyba pri vkladani hodnotenia do DB");
            return;
        }

        String sql = "INSERT INTO "+ MyContract.Hodnotenie.TABLE_NAME +" (" +
                MyContract.Hodnotenie.COL_NAZOV+ ", " +
                MyContract.Hodnotenie.COL_BODY +", " +
                MyContract.Hodnotenie.COL_UCITELOVPREDMET +", " +
                MyContract.Hodnotenie.COL_ZIAK +")" +
                " VALUES (?,?,?,?)";
        SQLiteStatement stm = db.compileStatement(sql);

        stm.clearBindings();
        stm.bindString(1,hodnotenie.getNazov());
        stm.bindLong(2,hodnotenie.getBody());
        stm.bindLong(3,hodnotenie.getPredmet().getId());
        stm.bindLong(4,hodnotenie.getZiak().getId());
        long rowid = stm.executeInsert();


    }

    public Hodnotenie getHodnotenie(int id){

        String[] stlpce = { "rowid",MyContract.Hodnotenie.COL_NAZOV, MyContract.Hodnotenie.COL_BODY, MyContract.Hodnotenie.COL_UCITELOVPREDMET, MyContract.Hodnotenie.COL_ZIAK};
        String selection = "rowid=?";
        String[] selectionArgs = {""+id};

        Cursor c = db.query(MyContract.Hodnotenie.TABLE_NAME,stlpce,selection,selectionArgs,null,null,null);
        c.moveToFirst();

        Hodnotenie hodnotenie = new Hodnotenie(id,
                c.getString(c.getColumnIndex(MyContract.Hodnotenie.COL_NAZOV)),
                c.getInt(c.getColumnIndex(MyContract.Hodnotenie.COL_BODY)),
                getUcitelovPredmet(c.getInt(c.getColumnIndex(MyContract.Hodnotenie.COL_UCITELOVPREDMET))),
                getZiak(c.getInt(c.getColumnIndex(MyContract.Hodnotenie.COL_ZIAK)))
                );
        c.close();

        return hodnotenie;

    }

    public ArrayList<Hodnotenie> getVsetkyHodnotenia(UcitelovPredmet predmet, Ziak ziak){
        ArrayList<Hodnotenie> vsetkyHodnotenia = new ArrayList<>();

        String[] stlpce = {"rowid", MyContract.Hodnotenie.COL_UCITELOVPREDMET, MyContract.Hodnotenie.COL_ZIAK};
        String selection = MyContract.Hodnotenie.COL_UCITELOVPREDMET +"=? AND "+ MyContract.Hodnotenie.COL_ZIAK+"=?";
        String[] selectionArgs = {""+predmet.getId(),""+ziak.getId()};

        Cursor c = db.query(MyContract.Hodnotenie.TABLE_NAME,stlpce,selection,selectionArgs,null,null,null);
        if(c.moveToFirst()){
            do{
                Hodnotenie hodnotenie = getHodnotenie(c.getInt(0));

                if(hodnotenie!=null) {
                    vsetkyHodnotenia.add(hodnotenie);
                }
            }while (c.moveToNext());
        }

        c.close();

        return vsetkyHodnotenia;

    }

     void addZiak(Osoba osoba, Trieda trieda){
        if(osoba==null){
            System.out.println("chyba pri vkladani ziaka");
            return;
        }


        String sql = "INSERT INTO " +
                MyContract.Ziak.TABLE_NAME +
                " (" + MyContract.Ziak.COL_OSOBA +  ", " +
                MyContract.Ziak.COL_TRIEDA +") " +
                "VALUES (?, ?)";
        SQLiteStatement stm = db.compileStatement(sql);

        stm.clearBindings();
        stm.bindLong(1,osoba.getId());
        stm.bindLong(2,trieda.getID());
        long rowid = stm.executeInsert();
    }

    void addPredmet(Predmet predmet) {
        if(predmet==null){
            System.out.println("chyba pri vkladani predmetu");
            return;
        }

        String sql = "INSERT INTO "+ MyContract.Predmet.TABLE_NAME +" " +
                "(" +MyContract.Predmet.COL_NAZOV + ", " +
                MyContract.Predmet.COL_POPIS + ", " +
                MyContract.Predmet.COL_OSNOVA + ") " +
                "VALUES (?, ?, ?)";
        SQLiteStatement stm = db.compileStatement(sql);

        stm.bindString(1,predmet.getNazov());
        stm.bindString(2,predmet.getPopis());
        stm.bindString(3,predmet.getOsnova());
        long rowid = stm.executeInsert();
    }

    Osoba getOsoba(int id){

        String[] stlpce = {MyContract.Osoba.COL_MENO,MyContract.Osoba.COL_PRIEZVISKO};
        String selection = "rowid=?";
        String[] selectionArgs = {""+id};

        Cursor c = db.query(MyContract.Osoba.TABLE_NAME,stlpce,selection,selectionArgs,null,null,null);
        c.moveToFirst();
        Osoba osoba = new Osoba(id,
                c.getString( c.getColumnIndex(MyContract.Osoba.COL_MENO)),
                c.getString( c.getColumnIndex(MyContract.Osoba.COL_PRIEZVISKO)));
        c.close();
        return osoba;
    }

    public Predmet getPredmet(int id){
        String[] stlpce = {MyContract.Predmet.COL_NAZOV,MyContract.Predmet.COL_POPIS, MyContract.Predmet.COL_OSNOVA};
        String selection = "rowid=?";
        String[] selectionArgs = {""+id};

        Cursor c = db.query(MyContract.Predmet.TABLE_NAME,stlpce,selection,selectionArgs,null,null,null);
        c.moveToFirst();
        Predmet predmet = new Predmet(id,
                c.getString( c.getColumnIndex(MyContract.Predmet.COL_NAZOV)),
                c.getString( c.getColumnIndex(MyContract.Predmet.COL_POPIS)),
                c.getString( c.getColumnIndex(MyContract.Predmet.COL_OSNOVA)));
        c.close();
        return predmet;
    }

    Ucitel getUcitel(int id){
        String[] stlpce = {MyContract.Ucitel.COL_OSOBA};
        String selection = "rowid=?";
        String[] selectionArgs = {""+id};

        Cursor c = db.query(MyContract.Ucitel.TABLE_NAME,stlpce,selection,selectionArgs,null,null,null);
        c.moveToFirst();
        Osoba osoba = getOsoba(c.getInt(c.getColumnIndex(MyContract.Ucitel.COL_OSOBA)));

        Ucitel ucitel = new Ucitel(id,
                osoba.getMeno(),
                osoba.getPriezvisko()
                );
        c.close();

        return ucitel;
    }
    boolean isUcitel(Osoba osoba){

        String sqlQuery = "SELECT * FROM " + MyContract.Ucitel.TABLE_NAME +
                " WHERE "+ MyContract.Ucitel.COL_OSOBA +" = ? ";
        String[] selectionArgs = {""+osoba.getId()};
        Cursor c = db.rawQuery(sqlQuery, selectionArgs);
        int count = c.getCount();
        c.close();

        if(count>1) System.out.println("mas duplicitu v uciteloch");
        return count==1;

    }

    UcitelovPredmet getUcitelovPredmet(int id){
        String[] stlpce = {MyContract.UcitelovPredmet.COL_UCITEL, MyContract.UcitelovPredmet.COL_PREDMET, MyContract.UcitelovPredmet.COL_TRIEDA};
        String selection = "rowid=?";
        String[] selectionArgs = {""+id};

        Cursor c = db.query(MyContract.UcitelovPredmet.TABLE_NAME,stlpce,selection,selectionArgs,null,null,null);
        c.moveToFirst();
        UcitelovPredmet ucitelovPredmet = new UcitelovPredmet(id,
                (   getUcitel( c.getInt( c.getColumnIndex(MyContract.UcitelovPredmet.COL_UCITEL)))),
                    getPredmet(c.getInt( c.getColumnIndex(MyContract.UcitelovPredmet.COL_PREDMET))),
                    getTrieda( c.getInt( c.getColumnIndex(MyContract.UcitelovPredmet.COL_TRIEDA))));
        c.close();

        return ucitelovPredmet;
    }

    public ArrayList<UcitelovPredmet> getVsetkyUcitelovePredmety(Ucitel ucitel){
        ArrayList<UcitelovPredmet> predmety = new ArrayList<>();

        String[] stlpce = {"rowid",MyContract.UcitelovPredmet.COL_UCITEL, MyContract.UcitelovPredmet.COL_PREDMET, MyContract.UcitelovPredmet.COL_TRIEDA};
        String selection = MyContract.UcitelovPredmet.COL_UCITEL+"=?";
        String[] selectionArgs = {""+ucitel.getId()};

        Cursor c = db.query(MyContract.UcitelovPredmet.TABLE_NAME,stlpce,selection,selectionArgs,null,null,null);
        if(c.moveToFirst()){
            do{
                UcitelovPredmet ucitelovPredmet = new UcitelovPredmet(
                        c.getInt(0),
                        getUcitel( c.getInt( c.getColumnIndex(MyContract.UcitelovPredmet.COL_UCITEL))),
                        getPredmet(c.getInt( c.getColumnIndex(MyContract.UcitelovPredmet.COL_PREDMET))),
                        getTrieda( c.getInt( c.getColumnIndex(MyContract.UcitelovPredmet.COL_TRIEDA))));

                if(ucitelovPredmet!=null) {
                    predmety.add(ucitelovPredmet);
                }
            }while (c.moveToNext());
        }

        c.close();

        return predmety;

    }

    Trieda getTrieda(int id){

        String[] stlpce = {MyContract.Trieda.COL_NAZOV};
        String selection = "rowid=?";
        String[] selectionArgs = {""+id};

        Cursor c = db.query(MyContract.Trieda.TABLE_NAME,stlpce,selection,selectionArgs,null,null,null);
        c.moveToFirst();
        Trieda trieda = new Trieda(id,
                c.getString( c.getColumnIndex(MyContract.Trieda.COL_NAZOV)));
        c.close();

        return trieda;
    }
    public ArrayList<Ziak> getZiakov(Trieda trieda){

        ArrayList<Ziak> ziaci = new ArrayList<>();
        String[] stlpce = {"rowid", MyContract.Ziak.COL_OSOBA};
        String selection = MyContract.Ziak.COL_TRIEDA+"=?";
        String[] args = {""+trieda.getID()};

        Cursor c = db.query(MyContract.Ziak.TABLE_NAME,stlpce,selection,args,null,null,null);
        if(c.moveToFirst()){
            do{
               int id = c.getInt(0);
               Osoba osoba = getOsoba(c.getInt(c.getColumnIndex(MyContract.Ziak.COL_OSOBA)));
                Ziak ziak= new Ziak(id,osoba.getMeno(),osoba.getPriezvisko(),trieda);
                ziaci.add(ziak);
            }while(c.moveToNext());
        }
        c.close();
        return ziaci;

    }

    public Ziak getZiak(int id){

        String[] stlpce = {MyContract.Ziak.COL_OSOBA, MyContract.Ziak.COL_TRIEDA};
        String selection = "rowid=?";
        String[] selectionArgs = {""+id};

        Cursor c = db.query(MyContract.Ziak.TABLE_NAME,stlpce,selection,selectionArgs,null,null,null);
        c.moveToFirst();
        Osoba osoba = getOsoba(c.getInt(c.getColumnIndex(MyContract.Ziak.COL_OSOBA)));

        Ziak ziak = new Ziak(id,
                osoba.getMeno(),
                osoba.getPriezvisko(),
                getTrieda(c.getInt( c.getColumnIndex(MyContract.Ziak.COL_TRIEDA))));
        c.close();

        return ziak;

    }


    public int getVelkostTabulky(String nazovTabulky){
        int count = 0;
        String sqlQuery = "SELECT * FROM " + nazovTabulky ;

        Cursor c = db.rawQuery(sqlQuery, null);

        count = c.getCount();
        c.close();

        return count;
    }


















    private void generujDatabazu(){
        generujLudi(POCET_GENEROVANYCH_LUDI);
        generujUcitelov(POCET_GENEROVANYCH_UCITELOV);
        generujTriedy(POCET_GENEROVANYCH_TRIED);
        generujZiakov();
        generujPredmety();
        generujUcitelomPredmety();
    }
    private void generujUcitelov(int pocet ) {
        if(pocet<1)return;

        try {
            db.beginTransaction();

            for (int i = 1; i < pocet; i++) {
                addUcitel(getOsoba(i));
            }

            db.setTransactionSuccessful();

        }catch(Exception e) {
            Log.w("exception",e);
        }finally {
            db.endTransaction();
        }
    }

    private Osoba[] generatorLudi(int pocet){
        // zenske mena nebudu (skoda casu :D)
        String[] mena = {"Bohuslav","Boleslav","Bonifác","Boris","Bořek","Bořivoj","Bronislav","Bruno","Břetislav","Ctibor","Ctirad","Cyril","Čeněk","Čestmír","Dalibor","Dalimil","Daniel","David","Dobroslav","Dominik","Drahoslav","Dušan","Eduard","Emanuel","Emil","Erik","Evžen","Felix","Ferdinand","Filip","František","Gabriel","Gustav","Hanuš","Havel","Herbert","Heřman","Horymír","Hubert","Hugo","Hynek","Ignác","Igor","Ingrid","Ivan","Ivo","Jáchym","Jakub","Jan","Jarmil","Jaromír","Jaroslav","Jeroným","Jindřich","Jiří","Jonáš","Josef","Julius","Kamil","Karel","Kazimír",
                "Rudolf","Řehoř","Samuel","Servác","Silvestr","Slavomír","Soběslav","Stanislav","Svatopluk","Svatoslav","Šimon","Štefan","Štěpán","Tadeáš","Teodor","Tibor","Tomáš","Václav","Valdemar","Valentýn","Vavřinec","Věnceslav","Vendelín","Věroslav","Viktor","Vilém","Vincenc","Vít","Vítězslav","Vladan","Vladimír","Vladislav","Vlastimil","Vlastislav","Vojtěch","Vratislav","Zbyněk","Zbyšek","Zdeněk","Zikmund",
                "Klement","Kristián","Kryštof","Květoslav","Kvido","Ladislav","Leopold","Leoš","Libor","Lubomír","Lubor","Luboš","Luděk","Ludvík","Lukáš","Lumír","Marcel","Marek","Marián","Martin","Matěj","Matouš","Maxim","Medard","Metoděj","Michal","Mikuláš","Milan","Miloslav","Miloš","Miroslav","Mojmír","Norbert","Oldřich","Oleg","Oliver","Ondřej","Oskar","Otakar","Otmar","Oto","Pankrác","Patrik","Pavel","Petr","Pravoslav","Prokop","Přemysl","Radan","Radek","Radim","Radomír","Radoslav","Radovan","René","Richard","Robert","Robin","Roland","Roman","Rostislav"};
        String[] priezviska = {"Abraham","Absolon","Adam","Adamčík","Adamec","Adámek","Adamík","Albert","Albrecht","Alexa","Altman","Ambrož","Anděl","Anderle","Andrle","Andrlík","Andrš","Andrýsek","Antl","Antoš","Aubrecht","Augusta","Augustin","Babák","Babický","Babka","Bača","Báča","Bačík","Baďura","Baier","Bajer","Bajgar","Bakeš","Balák","Baláš","Baláž","Balcar","Balcárek","Balek","Bálek","Balík","Balog","Balogh","Baloun","Balvín","Bambas","Bandy","Barák","Baran","Baránek","Bareš","Baroš","Bárta","Barták","Bartek","Bártek","Bartík","Bartl","Bártl","Bartoň","Bartoněk","Bartoníček","Bartoš","Bartošek","Bartošík","Bartůněk","Bartušek","Bařina","Bastl","Bašta","Batěk","Bauer","Baxa","Bayer","Bažant","Beck","Bečka","Bečvář","Bednář","Bednařík","Běhal","Běhounek","Bechyně","Bejček","Bek","Bělík","Bělka","Bělohlávek","Bém","Benák","Benda","Bendík","Bendl","Benedikt","Beneš","Beníšek","Benko","Beňo","Beran","Beránek","Berger","Berka","Berky","Bernard","Bernášek","Bernat","Bezděk","Bezdíček","Bican","Bílek","Bilík","Bilý","Bílý","Bína","Binar","Binder","Biskup","Bittner","Blaha","Bláha","Blahut","Blahuta","Blažej","Blažek","Blecha","Bobek","Bocek","Boček","Bodlák","Boháč","Boháček","Bohatý","Böhm","Bőhm","Bohuslav","Bojko","Bolek","Borek","Borkovec","Borovec","Borovička","Borovský","Borůvka","Bořil","Bosák","Bošek","Botek","Bouček","Bouda","Bouchal","Bouška","Bouzek","Božek","Brabec","Brabenec","Brada","Bradáč","Branda","Brandejs","Braun","Bravenec","Brázda","Brázdil","Brejcha","Brhel","Brodský","Brom","Brouček","Brož","Brožek","Brožík","Brůha","Brůna","Brunclík","Brůžek","Brych","Brychta","Brynda","Brzák","Brzobohatý","Břečka","Březina","Břicháček","Bříza","Buben","Bubeníček","Bubeník","Bublík","Buček","Bučko","Budík","Budil","Budín","Buchar","Buchta","Bui","Bukáček","Bukovský","Bulíček","Bulín","Bulíř","Burda","Bureš","Burger","Burget","Burian","Burián","Buriánek","Bursa","Bursík","Buršík","Buřič","Bušek","Buzek","Bůžek","Bydžovský","Byrtus","Bystroň","Bystřický","Caha","Cahová","Cach","Cajthaml","Cao","Carda","Cejnar","Cejpek","Cerman","Cibulka","Cienciala","Cieslar","Cigánek","Cihlář","Cina","Cink","Císař","Coufal","Crha","Culek","Cvrček","Čada","Čadek","Čajka","Čáp","Čapek","Čapka","Čáslavský","Čech","Čechura","Čejka","Čepelák","Čermák","Černík","Černohorský","Černohous","Černoch","Černý","Červeňák","Červenka","Červený","Červinka","Čeřovský","Češka","Čevela","Čihák","Číhal","Čipera","Číž","Čížek","Čonka","Čurda","Čureja","Danda","Daněk","Dang","Daňhel","Daníček","Daniel","Danihel","Daniš","Danko","Daňo","Dao","David","Davídek","Dedek","Dědek","Dědič","Dejmek","Demel","Demeter","Denk","Diblík","Dimitrov","Dinh","Dítě","Dittrich","Diviš","Dlask","Dlouhý","Dluhoš","Do","Doan","Dobeš","Dobiáš","Dobrovolný","Dobrý","Dočekal","Dočkal","Dohnal","Dohnálek","Dokoupil","Dokulil","Dolák","Dolanský","Doleček","Dolejš","Dolejší","Doležal","Doležel","Donát","Dopita","Doseděl","Doskočil","Dosoudil","Dostál","Dostálek","Došek","Doubek","Doubrava","Douda","Douša","Dráb","Drábek","Dragoun","Drahokoupil","Drahoňovský","Drahoš","Drápal","Drápela","Drbal","Drbohlav","Drda","Drexler","Drlík","Drobná","Drobný","Drozd","Drtina","Dub","Duba","Dubec","Duben","Dubský","Duda","Dudek","Dufek","Ducháček","Duchek","Duchoň","Duna","Dunka","Duong","Ďuriš","Dušek","Dvorský","Dvořáček","Dvořák","Dytrych","Dzurko","Eder","Ehrenberger","Eichler","Eliáš","Erben","Ernest","Exner","Fabian","Fabián","Fait","Fajkus","Fajt","Falta","Faltus","Faltýnek","Fanta","Farkaš","Farský","Fedák","Fejfar","Fencl","Ferenc","Ferko","Fiala","Fialka","Ficek","Fidler","Fiedler","Fikar","Fila","Filip","Filípek","Filipi","Filo","Fink","Fischer","Fišar","Fišer","Fišera","Flachs","Flek","Flídr","Florian","Florián","Fojt","Fojtík","Fojtů","Foldyna","Folprecht","Folta","Foltýn","Forejt","Forman","Formánek","Forst","Forstová","Fořt","Foukal","Fous","Fousek","Franc","Franek","Franěk","Frank","Franta","Franz","Fric","Frič","Fridrich","Friedl","Friedrich","Fröhlich","Frolík","Froněk","Frühauf","Fryč","Frýdl","Frydrych","Fučík","Fuchs","Fuka","Fuksa","Fulín","Fürst","Fusek","Gabčo","Gábor","Gabriel","Gajda","Gajdoš","Gajdošík","Gál","Gašpar","Gavenda","Gazda","Gazdík","Gaži","Gebauer","Giňa","Glaser","Glos","Gondek","Gorol","Gottvald","Gottwald","Gregor","Grepl","Grim","Gross","Grossmann","Gruber","Grulich","Grund","Grundza","Grunt","Gryc","Grygar","Ha","Haas","Hadač","Hadrava","Hahn","Hajda","Hájek","Hajný","Hák","Haken","Hakl","Hála","Halama","Halfar","Halík","Halíř","Halouzka","Haluška","Haluza","Hamáček","Haman","Hamerník","Hamouz","Hampl","Hána","Hanáček","Hanák","Handl","Hanke","Hanko","Hanousek","Hanus","Hanuš","Hanzal","Hanzelka","Hanzl","Hanzlíček","Hanzlík","Harant","Hartl","Hartman","Hartmann","Hašek","Hauser","Havel","Havelka","Havlena","Havlíček","Havlík","Havlín","Havran","Havránek","Havrda","Heczko","Heger","Hegr","Heinrich","Heinz","Hejda","Hejduk","Hejl","Hejna","Hejný","Hejtmánek","Hejzlar","Helešic","Heller","Hendrych","Henzl","Herák","Herink","Herman","Hermann","Herold","Herzán","Heřman","Heřmánek","Hes","Hess","Hladík","Hladký","Hlava","Hlaváč","Hlaváček","Hlavatý","Hlavička","Hlavinka","Hlávka","Hlavnička","Hlavsa","Hlinka","Hlobil","Hloušek","Hložek","Hlubuček","Hnát","Hnátek","Hnilica","Hnilička","Hnízdil","Hoang","Hobza","Hodan","Hodek","Hoffman","Hoffmann","Hofman","Hofmann","Hoch","Hochman","Holan","Holas","Holásek","Holba","Holčák","Holec","Holeček","Holek","Holík","Holiš","Holman","Holomek","Holoubek","Holub","Holubec","Holuša","Holý","Homola","Homolka","Hon","Hons","Honzík","Hora","Horáček","Horák","Horálek","Horčička","Horký","Horn","Horna","Horňák","Horníček","Horník","Horský","Hort","Horvát","Horváth","Horyna","Hořák","Hořejší","Hoření","Hořínek","Hošek","Hotový","Houdek","Houser","Houska","Houška","Hovorka","Hoza","Hozák","Hrabák","Hrabal","Hrabánek","Hrabě","Hrabec","Hrabovský","Hradecký","Hrádek","Hradil","Hradský","Hrach","Hrachovec","Hrbáč","Hrbáček","Hrbek","Hrdina","Hrdlička","Hrdý","Hrnčíř","Hroch","Hromada","Hromádka","Hromádko","Hromek","Hron","Hronek","Hrouda","Hrstka","Hrubeš","Hrubý","Hruška","Hrůza","Hřebíček","Hříbal","Hubáček","Hubálek","Hubený","Hubka","Hübner","Hudák","Hudec","Hudeček","Hůla","Hůlka","Huml","Hůrka","Hurt","Hurta","Hurych","Husák","Husár","Hušek","Hvězda","Hýbl","Hynek","Hýsek","Chadim","Chadima","Chaloupka","Chalupa","Charvát","Chen","Chlad","Chládek","Chloupek","Chlubna","Chlumský","Chlup","Chmel","Chmela","Chmelař","Chmelík","Chochola","Chovanec","Chramosta","Chromý","Chroust","Chudoba","Chudý","Chvátal","Chvojka","Chyba","Chytil","Chytka","Indra","Indrák","Ištok","Ivan","Ivanov","Jabůrek","Jäger","Jahn","Jahoda","Jakeš","Jakl","Jakoubek","Jakubec","Jakubík","Jalůvka","Jambor","Janáč","Janáček","Janák","Janata","Janča","Jančík","Janda","Jandera","Jandík","Jandl","Janeček","Janečka","Janek","Janíček","Janík","Janiš","Janko","Jankovič","Jankovský","Jano","Janoš","Janošek","Janota","Janouch","Janouš","Janoušek","Janovský","Jansa","Janský","Jánský","Januš","Jareš","Jarkovský","Jarolím","Jarolímek","Jaroš","Jarý","Jašek","Javorský","Javůrek","Jebavý","Jedlička","Jehlička","Jech","Jelen","Jelínek","Jemelka","Jeníček","Jeník","Jeřábek","Jež","Ježek","Jícha","Jílek","Jindra","Jindřich","Jíra","Jiráček","Jirák","Jiránek","Jirásek","Jirka","Jirkovský","Jirouš","Jiroušek","Jirout","Jirsa","Jiříček","Jiřička","Jiřík","Jiskra","Jíša","John","Jonák","Jonáš","Jordán","Joska","Juchelka","Jukl","Junek","Jung","Jungmann","Juračka","Jurák","Juráň","Juránek","Jurásek","Jurášek","Jurča","Jurčík","Jurečka","Jurek","Jurka","Juřena","Juřica","Juříček","Juřík","Just","Jůza","Kabát","Kabelka","Kábrt","Kačer","Kačírek","Kadeřábek","Kadlčík","Kadlec","Kadleček","Kafka","Kahánek","Kahoun","Kaiser","Kala","Kaláb","Kalaš","Kaleja","Kalenda","Kaleta","Kalina","Kališ","Kalivoda","Kalous","Kalousek","Kalus","Kaluža","Kalvoda","Kamenický","Kameník","Kamenský","Kaňa","Káňa","Kaňák","Kania","Kaňka","Kaňok","Kantor","Kaplan","Kapoun","Kapusta","Kára","Karafiát","Karas","Karásek","Karban","Karel","Kareš","Karlík","Kárník","Karpíšek","Kasal","Kasík","Kasl","Kaslová","Kastner","Kaše","Kašík","Kašpar","Kašpárek","Kaucký","Kavan","Kavka","Kazda","Keller","Kellner","Keprt","Kettner","Kilián","Kim","Kincl","Kindl","Kiss","Klaban","Klapka","Klas","Klásek","Klaus","Klečka","Klein","Klement","Klíč","Klička","Klika","Klíma","Klimek","Klímek","Kliment","Klimeš","Klos","Klouček","Klouda","Klus","Klusáček","Klusák","Kment","Kmoch","Kmoníček","Knap","Knápek","Kníže","Knížek","Knobloch","Knop","Knopp","Knotek","Kobliha","Koblížek","Kobr","Kobylka","Kobza","Kocáb","Kocián","Kocman","Kocourek","Kocur","Kočí","Kočka","Kofroň","Kohl","Köhler","Kohout","Kohoutek","Kohut","Koch","Kokeš","Kokoška","Koky","Koláček","Kolaja","Kolář","Kolařík","Kolda","Kolek","Kolínek","Kolínský","Kollár","Koller","Kolman","Kolomazník","Kolouch","Komárek","Komenda","Komínek","Koňařík","Konečný","Koníček","König","Konopásek","Konrád","Konvalina","Konvalinka","Konvička","Kopáček","Kopal","Kopecký","Kopeček","Kopečný","Kopp","Kopřiva","Koranda","Korbel","Korčák","Korda","Korec","Korecký","Kořán","Kořenek","Kořínek","Kos","Kosař","Kosek","Kosík","Kosina","Kostelník","Kostka","Košař","Košek",
                "Košťál","Kotala","Kotas","Kotásek","Kotek","Kotík","Kotlár","Kotrba","Kotrč","Kotrla","Kott","Kotyza","Kouba","Koubek","Koucký","Koudela","Koudelka","Koukal","Kouřil","Kousal","Kout","Koutník","Koutný","Koutský","Kovács","Kovač","Kováč","Kováčik","Kovach","Koval","Kovalčík","Kovanda","Kovář","Kovařík","Kovářík","Koza","Kozák","Kozel","Kozlík","Kozubík","Koželuh","Kožený","Kožíšek","Kožušník","Kracík","Kraft","Krajča","Krajčovič","Krajíček","Krákora","Král","Králíček","Králík","Kramář","Krása","Krásný","Kratina","Krátký","Kratochvil","Kratochvíl","Kraus","Krbec","Krč","Krčál","Krček","Krčil","Krčma","Krčmář","Krejcar","Krejča","Krejčí","Krejčík","Krejčíř","Krejčiřík","Krejsa","Krejza","Kresta","Krist","Kristek","Krištof","Krkoška","Krob","Krobot","Kročil","Kroka","Kropáč","Kropáček","Kroščen","Kroupa","Kroutil","Krpec","Krsek","Krška","Kruliš","Kruml","Krupa","Krupička","Krupka","Kružík","Kryl","Kryštof","Křeček","Křemen","Křen","Křenek","Křepelka","Křikava","Křístek","Křivan","Křivánek","Křivka","Kříž","Křížek","Kuba","Kubala","Kubálek","Kubáň","Kubánek","Kubát","Kubec","Kubeček","Kuběna","Kubeš","Kubica","Kubíček","Kubík","Kubín","Kubina","Kubiš","Kubišta","Kuča","Kučera","Kučírek","Kudela","Kudělka","Kudláček","Kudlička","Kudrna","Kufa","Kugler","Kuhn","Kuchař","Kuchařík","Kuchta","Kukačka","Kukla","Kula","Kulhánek","Kulhavý","Kulich","Kulík","Kulíšek","Kuna","Kunc","Kundrát","Kunert","Kuneš","Kunst","Kunz","Kupčík","Kupec","Kupka","Kurečka","Kurka","Kůrka","Kuruc","Kůs","Kusák","Kusý","Kuthan","Kutil","Kutílek","Kužel","Kužela","Kvapil","Kvasnica","Kvasnička","Květoň","Kyncl","Kysela","Kyselý","Kysilka","Lacina","Lacko","Lachman","Lakatoš","Lakomý","Lamač","Landa","Lang","Langer","Langr","Láník","Lánský","Lapčík","Lasák","Láska","Laštůvka","Látal","Lavička","Lazar","Le","Lebeda","Ledvina","Ledvinka","Lee","Lehký","Lejsek","Lekeš","Lelek","Lenc","Lerch","Lesák","Lev","Levý","Lexa","Lhota","Lhoták","Lhotský","Li","Líbal","Linhart","Linka","Lipka","Lipovský","Lipták","Lisý","Liška","Lněnička","Lochman","Lokaj","Lorenc","Lorenz","Loskot","Loučka","Louda","Loukota","Ludvík","Lukáč","Lukáš","Lukášek","Lukeš","Luňáček","Luňák","Lupač","Lysák","Máca","Macák","Maceček","Macek","Macík","Macko","Macoun","Macura","Maděra","Mádle","Mádr","Mahdal","Mach","Mácha","Machač","Macháč","Macháček","Machala","Machálek","Macháň","Machek","Macho","Machovec","Mai","Maier","Maixner","Majer","Makovec","Makula","Malát","Malec","Maleček","Málek","Malík","Malina","Mališ","Malý","Man","Maňák","Maňas","Maňásek","Mandík","Mann","Mára","Mareček","Marek","Mareš","Marko","Markovič","Markvart","Maroušek","Maršálek","Maršík","Martin","Martinák","Martinec","Martinek","Martínek","Martinka","Martinovský","Martynek","Marvan","Maryška","Mařík","Masař","Masařík","Máslo","Masopust","Mastný","Máša","Mašek","Mašín","Maška","Maštalíř","Matas","Matěj","Matějček","Matějíček","Matějka","Matějovský","Materna","Mátl","Matocha","Matouš","Matoušek","Matula","Matura","Matušek","Matuška","Matyáš","Matys","Matýsek","Maxa","Mayer","Mazáč","Mazal","Mazanec","Mazánek","Mazur","Med","Medek","Meduna","Měchura","Mejstřík","Mejzlík","Melich","Melichar","Melka","Melnyk","Meloun","Mencl","Menšík","Měrka","Merta","Mertl","Metelka","Mezera","Míča","Míček","Mička","Michal","Míchal","Michalčík","Michalec","Michálek","Michalík","Michl","Michna","Mika","Míka","Mikel","Mikeska","Mikeš","Miklík","Miko","Mikoláš","Mikolášek","Mikšík","Mikšovský","Mikula","Mikuláš","Mikulášek","Mikulec","Mikulecký","Mikulenka","Mikulík","Mikulka","Mikuš","Miler","Miller","Milota","Minarčík","Minář","Minařík","Mirga","Míšek","Miškovský","Mitáš","Mizera","Mládek","Mlčák","Mlčoch","Mlejnek","Mlynář","Mlýnek","Moc","Mocek","Mohyla","Mojžíš","Mokrý","Molnár","Moravčík","Moravec","Morávek","Motl","Mottl","Motyčka","Motyka","Moučka","Moudrý","Moucha","Moulis","Mráček","Mráz","Mrázek","Mrkva","Mrkvička","Mrva","Mudra","Mucha","Müller","Műller","Munzar","Musil","Musílek","Mužík","Mynář","Myslivec","Myšák","Myška","Nádvorník","Nagy","Nahodil","Najman","Nakládal","Nápravník","Návrat","Navrátil","Nečas","Nedbal","Nedoma","Nedvěd","Nechvátal","Nejedlý","Nejezchleb","Nekola","Němec","Němeček","Németh","Nepraš","Nerad","Nesvadba","Nešpor","Netík","Netolický","Netušil","Neubauer","Neugebauer","Neuman","Neumann","Neuwirth","Neužil","Nevrlý","Nezbeda","Nezval","Ngo","Nguyen","Nguyen Thi","Nguyen Van","Nikl","Noga","Nohejl","Nohel","Nosek","Nováček","Novák","Novosad","Novosád","Novotný","Nový","Nowak","Nožička","Nykl","Nytrová","Nývlt","Obdržálek","Odehnal","Odvárka","Olah","Oláh","Olejník","Oliva","Olšák","Onderka","Ondra","Ondráček","Ondrák","Ondrášek","Ondroušek","Ondruch","Ondruš","Ondřej","Opatrný","Opletal","Oplt","Opluštil","Opravil","Oračko","Oravec","Orel","Orság","Osička","Osvald","Ošťádal","Otáhal","Otruba","Ott","Otta","Oulehla","Pacák","Pačes","Pagáč","Pajer","Pala","Palán","Palát","Paleček","Páleník","Palička","Pálka","Panáček","Pánek","Papež","Papoušek","Páral","Parma","Pařík","Pařil","Pařízek","Paseka","Pastorek","Pastrňák","Pastyřík","Pašek","Paták","Pátek","Patera","Patka","Patočka","Paul","Paulík","Paulus","Paur","Páv","Pávek","Pavel","Pavelec","Pavelek","Pavelka","Pavlas","Pavlásek","Pavlát","Pavlica","Pavlíček","Pavlík","Pavlis","Pavliš","Pavlovič","Pavlovský","Pazdera","Pazderka","Pazourek","Pažout","Pecina","Pecka","Pečenka","Pech","Pecha","Pecháček","Pechar","Pejša","Pek","Pekárek","Pekař","Pěkný","Pelán","Pelant","Pelc","Pelikán","Pelíšek","Peňáz","Pěnička","Pěnkava","Pergl","Pernica","Peroutka","Peřina","Peřinová","Pešák","Pešek","Peška","Pešl","Pešta","Peter","Petera","Peterek","Peterka","Petr","Petráček","Petrák","Petráň","Petrásek","Petráš","Petrášek","Petrlík","Petrov","Petrus","Petržela","Petrželka","Petřek","Petříček","Petřík","Pfeifer","Pham","Phan","Picek","Picka","Pícha","Pilař","Pilát","Pilný","Pinc","Pinkas","Pinkava","Piňos","Pipek","Pírek","Pirkl","Píša","Pištěk","Pitra","Pivoňka","Plaček","Plachý","Plášek","Plašil","Plášil","Plecitý","Plecháč","Plechatý","Pleskot","Pleva","Plch","Plíhal","Pliska","Plíšek","Plíva","Plocek","Plšek","Pluhař","Plzák","Podaný","Podešva","Podhorský","Podroužek","Podzimek","Pohanka","Pohl","Pokorný","Poláček","Polach","Polách","Polák","Polanský","Polášek","Polcar","Poledník","Polívka","Pollák","Pompa","Pop","Popek","Popelka","Popovič","Popovych","Poslední","Poslušný","Pospěch","Pospíchal","Pospíšil","Potůček","Poul","Pour","Pouzar","Povolný","Prachař","Prášek","Prášil","PRAVDA","Pražák","Pražan","Preisler","Prchal","Prchlík","Princ","Procházka","Prokeš","Prokop","Prokopec","Prokš","Prokůpek","Prošek","Prouza","Provazník","Průcha","Průša","Přecechtěl","Přenosil","Přibyl","Přidal","Příhoda","Přichystal","Přikryl","Psota","Pšenička","Ptáček","Ptáčník","Pták","Pudil","Půlpán","Punčochář","Pustějovský","Pýcha","Pyszko","Pytlík","Rác","Racek","Ráček","Rada","Rafaj","Rais","Rak","Rambousek","Randa","Raszka","Raška","Rataj","Rathouský","Reich","Reichl","Rejman","Rejzek","Rek","Remeš","Rendl","Rezek","Riedl","Rieger","Richter","Richtr","Rishko","Roček","Rod","Roháč","Roháček","Rokos","Roman","Rosa","Roth","Rott","Rotter","Roubal","Roubíček","Roučka","Rous","Rousek","Rozehnal","Rozsíval","Rozsypal","Rubáš","Rubeš","Ručka","Rudolf","Ruml","Rus","Rusek","Rusňák","Růžek","Růžička","Ryba","Rybák","Rybár","Rybář","Rybka","Rybníček","Rýdl","Rychlík","Rychlý","Rychtařík","Rys","Ryšánek","Ryšavý","Ryška","Rytíř","Řeháček","Řehák","Řehoř","Řepa","Řepka","Řeřicha","Řezáč","Řezníček","Řezník","Řežábek","Říha","Řihák","Řípa","Sadílek","Salaba","Salač","Salajka","Salák","Samec","Samek","Samko","Sedláček","Sedlák","Sedlář","Sehnal","Seidl","Seidler","Seifert","Sejkora","Sekanina","Sekera","Sekyra","Semerád","Semerák","Semrád","Severa","Schejbal","Schiller","Schindler","Schmid","Schmidt","Schmied","Schneider","Scholz","Schreiber","Schubert","Schulz","Schuster","Schwarz","Sigmund","Sikora","Simandl","Simon","Sirotek","Sivák","Sixta","Skácel","Skala","Skála","Skalický","Skalka","Skalník","Skalský","Sklenář","Skokan","Skopal","Skořepa","Skotnica","Skoumal","Skoupý","Skřivan","Skřivánek","Skýpala","Slabý","Sládeček","Sládek","Sladký","Sláma","Slanina","Slánský","Slaný","Slavíček","Slavík","Slepička","Slezák","Slíva","Slivka","Slouka","Sloup","Slováček","Slovák","Sluka","Smejkal","Smékal","Smetana","Smíšek","Smola","Smolík","Smolka","Smrček","Smrčka","Smrž","Smutný","Snášel","Snopek","Sobek","Sobota","Sobotka","Sodomka","Socha","Sochor","Sojka","Sokol","Solař","Soldán","Sommer","Sosna","Souček","Soukal","Soukup","Soušek","Sova","Spáčil","Spěvák","Spilka","Spousta","Spurný","Srb","Srba","Srnec","Srp","Stach","Staněk","Staník","Stanislav","Stárek","Starý","Stašek","Stavinoha","Stehlík","Stehno","Steiner","Stejskal","Steklý","Stibor","Stibůrek","Stibůrková","Stloukal","Stodola","Stodůlka","Stojka","Stoklasa","Stoklásek","Strachota","Straka","Strakoš","Stránský","Stratil","Strejc","Strejček","Strmiska","Strnad","Strnadel","Strouhal","Strýček","Středa","Střelec","Stříbrný","Stříž","Studený","Studnička","Stuchlík","Stuchlý","Stupka","Stýblo","Suda","Suchan","Suchánek","Sucharda","Suchomel","Suchý","Suk","Surý","Sůva","Svačina","Svárovský","Svatek",
                "Svatoň","Svatoš","Světlík","Sviták","Svoboda","Svozil","Sychra","Sýkora","Synek","Syrovátka","Sysel","Szabo","Szabó","Szkandera","Szotkowski","Šabata","Šafář","Šafařík","Šafránek","Šach","Šácha","Šálek","Šámal","Šanda","Šandera","Šandor","Šašek","Šauer","Ščuka","Šebek","Šebela","Šebesta","Šeda","Šedivý","Šedý","Šefčík","Šefl","Šembera","Šenk","Šenkeřík","Šenkýř","Šerá","Šerý","Šesták","Ševčík","Šídlo","Šigut","Šik","Šikula","Šilar","Šilhan","Šilhán","Šilhánek","Šíma","Šimáček","Šimák","Šiman","Šimánek","Šimčík","Šimeček","Šimek","Šimíček","Šimon","Šimoník","Šimůnek","Šindelář","Šindelka","Šindler","Šíp","Šípek","Šír","Široký","Šiška","Škarda","Škoda","Škop","Škopek","Škorpík","Škrabal","Škuta","Škvor","Šlajs","Šlapák","Šlehofer","Šlechta","Šlesinger","Šmarda","Šmehlík","Šmejkal","Šmerda","Šmíd","Šmída","Šmídek","Šmídl","Šmolík","Šnajdr","Šnobl","Šolc","Šoltys","Šorm","Šotola","Šourek","Špaček","Špalek","Šperl","Špičák","Špička","Špinar","Špinka","Šplíchal","Šrámek","Šrom","Šrubař","Štancl","Šťástka","Šťastný","Štefan","Štefek","Štefka","Štefl","Štěch","Štencl","Štěpán","Štěpánek","Štěpnička","Štěrba","Štětina","Štětka","Štika","Štípek","Štochl","Štolba","Štorek","Šťovíček","Štrobl","Štrunc","Šturma","Šuba","Šubert","Šubrt","Šula","Šulák","Šulc","Šusta","Šustek","Šustr","Šváb","Švadlenka","Švancara","Švanda","Švarc","Švec","Švéda","Švehla","Švejda","Švestka","Švorc","Švrček","Táborský","Takáč","Tancoš","Tauchman","Tauš","Teplý","Tesárek","Tesař","Tesařík","Ticháček","Tichý","Tkáč","Tkadlec","Tlustý","Tobiáš","Tobola","Tokár","Tolar","Toman","Tomanec","Tománek","Tomáš","Tomášek","Tomeček","Tomek","Tomeš","Tomíček","Topič","Topinka","Toth","Tóth","Toufar","Touš","Toušek","Tran","Tran Thi","Trávníček","Trčka","Trefil","Trejbal","Trinh","Trnka","Troják","Trojan","Truhlář","Truong","Třešňák","Tříska","Tuček","Tůma","Tupý","Tureček","Turek","Turoň","Tvarůžek","Tvrdík","Tvrdoň","Tvrdý","Tvrzník","Tyl","Uher","Uherek","Uhlík","Uhlíř","Uchytil","Úlehla","Ullmann","Ulman","Ulrich","Ulrych","Unger","Ungr","Urban","Urbanec","Urbánek","Urbášek","Vacek","Vacík","Václavek","Václavík","Vacula","Vaculík","Vágner","Vach","Vácha","Váchal","Vajda","Vala","Vála","Valach","Valášek","Valčík","Válek","Valenta","Valeš","Valíček","Valík","Vališ","Válka","Valouch","Valta","Valter","Valtr","Váňa","Vančura","Vaněček","Vaněk","Vaníček","Vaník","Vaniš","Vaňous","Vápeník","Varga","Varmuža","Vařeka","Váša","Vašák","Vašek","Vašíček","Vašina","Vaško","Vašků","Vašut","Vaverka","Vávra","Vavrečka","Vavruška","Vavřička","Vavřík","Vavřín","Vavřina","Večerka","Večeřa","Vedral","Vejvoda","Veleba","Velecký","Velek","Velička","Velíšek","Vencl","Vepřek","Verner","Veselka","Veselský","Veselý","Větrovec","Větrovský","Veverka","Vích","Vícha","Vik","Viktora","Viktorin","Vild","Vilím","Vilímek","Vinklárek","Vinkler","Vinš","Vintr","Víšek","Vít","Vitásek","Vítek","Vítovec","Vladyka","Vlach","Vlasák","Vlášek","Vlček","Vlk","Vo","Voborník","Vobořil","Vocásek","Voda","Vodák","Vodička","Vodrážka","Vogel","Vogl","Vojáček","Vojkůvka","Vojta","Vojtěch","Vojtek","Vojtíšek","Vokáč","Vokál","Vokoun","Vokurka","Volák","Voldřich","Volejník","Volek","Volf","Volný","Vomáčka","Vondra","Vondráček","Vondrák","Vondrášek","Vondruška","Vopat","Voráč","Voráček","Vorel","Vorlíček","Voříšek","Vosáhlo","Vosátka","Vostrý","Votava","Votoček","Votruba","Votýpka","Voves","Voženílek","Vrabec","Vrábel","Vráblík","Vrána","Vránek","Vraný","Vrátný","Vrba","Vrbka","Vršecký","Vrzal","Všetečka","Vu","Vybíral","Výborný","Vydra","Vyhlídal","Vyhnálek","Vyhnánek","Vychodil","Vykoukal","Vykydal","Vymazal","Vymětal","Vyoral","Vyroubal","Vyskočil","Vysloužil","Vystrčil","Vytlačil","Wagner","Wágner","Walter","Wang","Wasserbauer","Weber","Weiss","Werner","Wiesner","Wimmer","Winkler","Winter","Wojnar","Wolf","Zabloudil","Zábojník","Zábranský","Zadina","Zadražil","Zahálka","Záhora","Zahrádka","Zahradníček","Zahradník","Zach","Zachoval","Zajac","Zajíc","Zajíček","Záleský","Zálešák","Zamazal","Zámečník","Zaoral","Zapletal","Zápotocký","Záruba","Zástěra","Zatloukal","Zavadil","Záveský","Zavřel","Zbořil","Zbranek","Zdeněk","Zdráhal","Zdražil","Zedníček","Zedník","Zejda","Zelenka","Zelený","Zelinka","Zeman","Zemánek","Zemek","Zezula","Zezulka","Zhang","Ziegler","Zich","Zicha","Zika","Zíka","Zikmund","Zima","Zíma","Zimmermann","Zítek","Zítka","Zlámal","Zmeškal","Znamenáček","Zoubek","Zouhar","Zvěřina","Zvolánek","Zvoníček","Zýka","Žabka","Žáček","Žák","Žaloudek","Žalud","Žampach","Žďárský","Železný","Ženíšek","Židek","Žídek","Žiga","Žilka","Živný","Žižka","Žůrek"};

        Osoba[] ludia = new Osoba[pocet];
        for(int i = 0;i<pocet;i++){
            Osoba osoba = new Osoba(i,vyberNahodnyPrvok(mena),vyberNahodnyPrvok(priezviska));
            ludia[i]=osoba;
        }

        return ludia;
    }

    private String vyberNahodnyPrvok(String [] pole){
        Random rand= new Random();
        return pole[rand.nextInt(pole.length)];
    }

    private void generujLudi(int pocet) {
        if(pocet<1)return;

        Osoba []ludia = generatorLudi(pocet);
        try {

            db.beginTransaction();

            for (Osoba osoba : ludia) {
                addOsoba(osoba);
            }

            db.setTransactionSuccessful();

        }catch(Exception e) {
            Log.w("exception",e);
        }finally {
            db.endTransaction();
        }
    }

    private void generujZiakov(){

        for(int i = 1; i<=POCET_GENEROVANYCH_LUDI; i++){
            Osoba osoba = getOsoba(i);
            if(!isUcitel(osoba)){

                Trieda trieda = getTrieda((i%POCET_GENEROVANYCH_TRIED)+1);
                addZiak(osoba, trieda);

            }
        }
    }

    private void generujTriedy(int pocet) {

        for(int i = 1; i<=pocet; i++){
            String nazov = "TR_"+((i%pocet)+1);
            Trieda trieda = new Trieda(i,nazov);
            addTrieda(trieda);
        }

    }

    private void generujUcitelomPredmety() {
        ArrayList<Ucitel> celaZborovna = getVsetkychUcitelov();
        ArrayList<Predmet> vsetkyPredmety = getVsetkyPredmety();
        ArrayList<Trieda> vsetkyTriedy = getVsetkyTriedy();
        Random rand = new Random();

        for (Ucitel ucitel : celaZborovna) {
            if (ucitel == null) return;
            for (int i = 0; i < POCET_PRIRADENYCH_PREDMETOV; i++) {
                int randomPredmetID = rand.nextInt(vsetkyPredmety.size());
                Predmet predmet = vsetkyPredmety.get(randomPredmetID);
                Trieda trieda = vsetkyTriedy.get(rand.nextInt(vsetkyTriedy.size()));
                addUcitelovyPredmet(ucitel, predmet, trieda);
            }
        }
    }

    private void generujPredmety() {

        addPredmet(new Predmet(1,"Vývoj aplikácií pre iOS" , "Študent efektívne využíva prostredie pre vývoj mobilných aplikácií pre iOS. Študent pozná situácie a techniky typické pre tvorbu mobilných aplikácií pre iOS. Študent dokáže využívaťGUI komponenty prostredia a jazyk Swift pri tvorbe vlastných aplikácií. Študent dokáže využívať grafiku, animáciu a spracovanie dotykov pri grafických aplikáciách. Študent dokáže vytvoriť komplexnú mobilnú aplikáciu využívajúcu všetky potrebné komunikačné rozhrania v operačnom systéme iOS. Študent je schopný samostatne vytvoriť komplexnú aplikáciu pre zariadenia s iOS.","1. Swift. Základné riadiace a údajové štruktúry. 2. Úvod do iOS 3. MVC architektúra 4. Pokročilé prvky jazyka Swift 5. Grafika 6. Spracovanie dotykov a multitouch 7. Časovač a animácia 8. Multithreading a autolayout 9. Drag&drop 10. Vybrané komponenty prostredia" ));
        addPredmet(new Predmet(2,"Úvod do modelovania a simulácie","VV1: Študent získava vedomosti o základných typoch modelov v oblasti prírodných vied. VV2: Študent porozumie základným vlastnostiam uvedených modelov. VV3: Študent analyzuje získané vedomosti o jednotlivých modeloch. VV4: Študent aplikuje naučené vedomosti do oblasti modelovania a simulácie.","1. Základné pojmy 2. Diskrétne systémy: Markovove náhodne procesy a ich vlastnosti Systémy hromadnej obsluhy (SHO) a ich klasifikácia Kolmogorovove diferenciálne rovnice na analytické riešenie SHO Popis a riešenie rôznych typov systémov hromadnej obsluhy Siete systémov hromadnej obsluhy a ich analytické riešenie Metódy generovania náhodných čísel Metóda Monte Carlo a jej aplikácie Jazyky na simuláciu diskrétnych systémov Počítačová simulácia diskrétnych systémov. 3. Spojité systémy: Popis spojitých systémov, matematické modely spojitých systémov a ich tvorba Jazyky na simuláciu spojitých systémov (Simulink) Počítačová simulácia spojitých systémov."));
        addPredmet(new Predmet(3,"Umelá inteligencia","Cieľom predmetu je vysvetliť pojmy, postupy a zameranie výskumu umelej inteligencie tak, aby poslucháči získali orientáciu v tejto modernej a prudko sa rozvíjajúcej oblasti, nadobudli predstavu o jej teoretických základoch i o aplikačnom aspekte a získali základnú zručnosť pri riešení typických príkladov z danej oblasti.","1. Umelá inteligencia ako vedný odbor, základné smery výskumu, poznatky ako predmet výskumu. 2. Riešenie problémov, informatívne a neinformatívne hľadanie, heuristika 3. Herné problémy, riešenie hier o dvoch hráčov, MinMax a jeho modifikácie. 4. Reprezentácia problémov stavovým priestorom, metódy prehľadávania, pojem heuristiky. Informované a neinformované metódy prehľadávania, typické problémy z tejto oblasti. 5. Pravidlové systémy, ich architektúra, inferenčný mechanizmus, stratégie inferencie, aplikácia pravidlových systémov. 6. Aplikačné výstupy umelej inteligencie, možné smery jej ďalšieho rozvoja. 7. Strojové učenie, bifľovanie, induktívne učenie sa, Naivný Bayesovský klasifikátor. 8. Strojové učenie, regresia, metóda najmenších štvorcov, problém preučenia sa. 9. Vnímanie, rozpoznávanie vzorov, rozpoznávanie textu, extrakcia príznakov. 10. Plánovanie, hľadanie riešenia vs. plánovanie, strips, reprezentácia akcií. 11. Úvod do neurónových sietí, perceptrón. 12. Programovanie v jazyku Prolog."));
        addPredmet(new Predmet(4,"Počítačové siete ","",""));
        addPredmet(new Predmet(5,"Počítačová grafika (KI/PG) ","",""));
        addPredmet(new Predmet(6,"Programovanie 1 ","",""));
        addPredmet(new Predmet(7,"Výpočtová zložitosť algoritmov","",""));
        addPredmet(new Predmet(8,"Operačné systémy (KI/OS/15)","",""));
        addPredmet(new Predmet(9,"Kryptografia (KI/KRY) 2019","",""));

    }


    public void upravHodnotenie(Hodnotenie hodnotenie) {

        ContentValues values = new ContentValues();
            values.put(MyContract.Hodnotenie.COL_NAZOV,hodnotenie.getNazov());
            values.put(MyContract.Hodnotenie.COL_BODY,hodnotenie.getBody());

        db.update(
                MyContract.Hodnotenie.TABLE_NAME,
                values,
                "rowid = ?",
                new String[]{""+hodnotenie.getId()}
        );

    }
}

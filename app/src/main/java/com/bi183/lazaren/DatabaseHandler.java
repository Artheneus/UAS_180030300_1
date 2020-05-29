package com.bi183.lazaren;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.security.keystore.StrongBoxUnavailableException;

import androidx.lifecycle.ViewModelProvider;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "db_myFilm";
    private final static String TABLE_FILM = "t_film";
    private final static String KEY_ID_FILM = "ID_Film";
    private final static String KEY_JUDUL = "Judul";
    private final static String KEY_GAMBAR = "Gambar";
    private final static String KEY_TANGGAL = "Tanggal";
    private final static String KEY_SINOPSIS = "Sinopsis";
    private final static String KEY_LINK_TRAILER = "Link";
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private Context context;

    public DatabaseHandler (Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_FILM = "CREATE TABLE " + TABLE_FILM
                + "(" + KEY_ID_FILM + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_JUDUL + " TEXT, " + KEY_GAMBAR + " TEXT, "
                + KEY_TANGGAL + " DATE, " + KEY_SINOPSIS + " TEXT, "
                + KEY_LINK_TRAILER + " TEXT);";

        db.execSQL(CREATE_TABLE_FILM);
        insialisasiFilmAwal(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_FILM;
            db.execSQL(DROP_TABLE);
            onCreate(db);
    }

    public void tambahFilm(Film dataFilm){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_TANGGAL, sdFormat.format(dataFilm.getTanggal()));
        cv.put(KEY_SINOPSIS, dataFilm.getSinopsis());
        cv.put(KEY_LINK_TRAILER, dataFilm.getLinkTrailer());

        db.insert(TABLE_FILM, null, cv);
        db.close();
    }

    public void tambahFilm(Film dataFilm, SQLiteDatabase db){
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_TANGGAL, sdFormat.format(dataFilm.getTanggal()));
        cv.put(KEY_SINOPSIS, dataFilm.getSinopsis());
        cv.put(KEY_LINK_TRAILER, dataFilm.getLinkTrailer());

        db.insert(TABLE_FILM, null, cv);
    }

    public void editFilm(Film dataFilm){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_TANGGAL, sdFormat.format(dataFilm.getTanggal()));
        cv.put(KEY_SINOPSIS, dataFilm.getSinopsis());
        cv.put(KEY_LINK_TRAILER, dataFilm.getLinkTrailer());

        db.update(TABLE_FILM, cv, KEY_ID_FILM + "=?", new String[]{String.valueOf(dataFilm.getIdFilm())});
        db.close();
    }

    public void hapusFilm(int idFilm){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_FILM, KEY_ID_FILM + "=?", new String[]{String.valueOf(idFilm)});
    }

    public ArrayList<Film> getAllFilm(){
        ArrayList<Film> dataFilm = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_FILM;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);

        if(csr.moveToFirst()){
            do {
                Date tempDate = new Date();
                try{
                    tempDate = sdFormat.parse(csr.getString(3));
                }catch (ParseException er){
                    er.printStackTrace();
                }

                Film tempFilm = new Film(
                        csr.getInt(0),
                        csr.getString(1),
                        csr.getString(2),
                        tempDate,
                        csr.getString(4),
                        csr.getString(5)
                );

                dataFilm.add(tempFilm);
            } while (csr.moveToNext());
        }

        return dataFilm;
    }

    private String storeImageFile(int id){
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    private void insialisasiFilmAwal(SQLiteDatabase db){
        int idFilm = 0;
        Date tempDate = new Date();

        //Adding Data
        try{
            tempDate = sdFormat.parse("25/04/2018");
        }catch (ParseException er){
            er.printStackTrace();
        }



        Film film1 = new Film(
                idFilm,
                "AVENGER : INFINITY WARS",
                storeImageFile(R.drawable.film1),
                tempDate,
                "Thanos berambisi untuk mengumpulkan enam batu abadi yang tersebar di berbagai tempat. Setiap batu memiliki kekuatan tersendiri sehingga tidak sembarang orang bisa mendapatkannya. \n" +
                        "\n" +
                        "Jika sudah memiliki keenam batu abadi, Thanos bisa menjadi penguasa semesta. Thanos pun berniat menghilangkan separuh populasi semesta karena dipercaya bisa membuat kehidupan lebih sejahtera.\n" +
                        "\n" +
                        "Thanos bersama anak buahnya menebar teror sambil mencari batu-batu tersebut. Kekejaman Thanos disaksikan sendiri oleh Bruce Banner/Hulk dan Thor. Mereka berniat menemukan superhero lain agar bisa mencegah ambisi Thanos.\n" +
                        "\n" +
                        "Hulk kembali ke bumi dan memberi tahu aksi Thanos kepada Tony Stark/Iron Man, Doctor Strange  serta Wong. Sialnya, Thanos sudah muncul sebelum mereka bertindak lebih jauh. \n" +
                        "\n" +
                        "Alhasil Iron Man dan Doctor Strange masuk kapal luar angkasa Thanos. Kekacauan bertambah ketika Peter Parker/Spider-Man ikut terbawa mereka.\n" +
                        "\n" +
                        "Sementara Thor diselamatkan anggota Guardians of the Galaxy. Thor percaya hanya bisa menyelamatkan Thanos dengan bantuan palu sakti miliknya. Bersama Rocket dan Groot, Thor pergi ke planet lain untuk mendapatkan palu sakti baru.\n" +
                        "\n" +
                        "Saat Thor dan Hulk menyusun rencana, anak buah Thanos mengincar Vision. Salah satu batu abadi berada dalam tubuh Vision. Sadar nyawa pacarnya terancam, Wanda Maximoff/Scarlet Witch meminta bantuan Steve Rogers/Captain America, Natasha Romanoff/Black Widow, dan Sam Wilson/Falcon.\n" +
                        "\n" +
                        "Captain America kemudian membawa mereka ke Wakanda dan bertemu T'Challa/Black Panther  serta Bucky Barnes/Winter Soldier. Captain America merasa Wakanda merupakan tempat yang aman. Wakanda juga memiliki pasukan memadai sehingga mampu menghadapi perang.\n" +
                        "\n" +
                        "Lantas dimana Thanos? Sang penjahat tengah memaksa putri tirinya, Gamora untuk menunjukan letak salah satu batu abadi. Ini membuat Peter Quill/Star-Lord, Drax, dan Mantis  bertekad menyelamatkannya.\n",
                "https://www.youtube.com/watch?v=6ZfuNTqbHE8"
        );

        tambahFilm(film1, db);
        idFilm++;

        //Data 2
        try{
            tempDate = sdFormat.parse("24/04/2019");
        }catch (ParseException er){
            er.printStackTrace();
        }

        Film film2 = new Film(
                idFilm,
                "AVENGER : END GAME",
                storeImageFile(R.drawable.film2),
                tempDate,
                "23 hari telah berlalu semenjak thanos memusnahkan setengah dari peradaban semesta dan menghilang menggunakan space stone, akhirnya avenger menemukan keberadaan thanos.\n" +
                        "\n" +
                        "Thanos berada di tempat yang dikataknnya Garden, bersama Captain Marvel avenger langsung menuju ketempat keberadaan thanos. Tapi sangat disayangkan mereka mendapatkan bahwa thanos telah menghancurkan infinity stone dan kini sedang mencari cara bagaimana untuk mengembalikan alam semesta kembali seperti semula.",
                "https://www.youtube.com/watch?v=TcMBFSGVi1c&t=13s"
        );

        tambahFilm(film2, db);
        idFilm++;

        //Data3
        try{
            tempDate = sdFormat.parse("1/05/2015");
        }catch (ParseException er){
            er.printStackTrace();
        }
        Film film3 = new Film(
                idFilm,
                "AVENGER : AGE OF ULTRON",
                storeImageFile(R.drawable.film3),
                tempDate,
                "Dalam film ini diceritakan, setelah pasukan S.H.I.E.L.D runtuh, Tony Stark berusaha untuk mempertahankan keamanan Bumi dan manusia. Salah satu caranya dengan menciptakan tentara bantuan yang akan membantunya menjaga perdamaian dunia. \n" +
                        "\n" +
                        "Dengan perjalanannya, Tony berhasil menciptakan Ultron atau robot tentara. Ultron yang merupakan bagian dari Iron Legion, pasukan tentara penjaga perdamaian bentukan Tony, memiliki tujuan awal membantu Avengers menumpas para penjahat. \n" +
                        "\n" +
                        "Sayangnya, bukannya membantu membasmi kejahatan, Ultron malah menjadi sumber petaka bagi bumi dan manusia. Ultron yang semakin berkembang menganggap bahwa justru manusia lah yang menjadi ancaman bagi kedamaian Bumi. \n" +
                        "\n" +
                        "Dengan anggapan tersebut, Ultron berupaya membasmi manusia sebanyak mungkin. Sekarang merupakan tugas Tony dan timnya, Captain America, Hulk, Black Widows, Thor serta Hawkeye untuk menghentikan Ultron.\n",
                "https://www.youtube.com/watch?v=tmeOjFno6Do"
        );

        tambahFilm(film3, db);
        idFilm++;

        //Data4
        try{
            tempDate = sdFormat.parse("14/12/2018");
        }catch (ParseException er){
            er.printStackTrace();
        }
        Film film4 = new Film(
                idFilm,
                "SPIDER-MAN : INTO THE SPIDER VERSE",
                storeImageFile(R.drawable.film4n),
                tempDate,
                "Miles Morales adalah seorang remaja yang mengagumi Spider-Man, dan berjuang memenuhi ekspektasi orangtuanya. Ayahnya, petugas kepolisian, Jefferson Davis, melihat Spider-Man sebagai sebuah ancaman. Sepulang sekolah Miles diam-diam mengunjungi pamannya, Aaron Davis, yang membawa Miles ke stasiun kereta bawah tanah yang tidak digunakan tempat ia bisa melukis grafiti. Sementara di sana, Miles digigit laba-laba radioaktif dan mengembangkan kemampuan seperti laba-laba.\n" +
                        "\n" +
                        "Mencari laba-laba yang menggigitnya, Miles kemudian kembali ke stasiun dan tanpa sengaja menemukan akselerator partikel yang dibangun oleh Wilson Fisk, yang ingin mengakses alam semesta paralel dan menemukan versi alternatif dari istri dan putranya yang meninggal dalam kecelakaan mobil. Spider-Man tiba untuk menonaktifkan akselerator dan melawan mahluk super lain yang berada di pihak Fisk, Green Goblin dan Prowler. Spider-Man menemukan bahwa Miles memiliki kemampuan yang serupa, tetapi terluka parah oleh ledakan selama pertempuran yang membunuh Green Goblin. Spider-Man memberi Miles USB untuk menonaktifkan akselerator dan memperingatkan bahwa mesin itu dapat menghancurkan kota jika dihidupkan lagi. Miles kemudian menyaksikan Fisk membunuh Spider-Man dengan ngeri sebelum melarikan diri.\n" +
                        "\n" +
                        "Saat mencoba menguasai kemampuan barunya, Miles secara tidak sengaja merusak USB tersebut. Di makam Spider-Man, Miles bertemu Peter B. Parker, versi Spider-Man yang lebih tua dan letih dari dimensi lain. Peter telah dibawa ke dimensi Miles oleh akselerator dan perlu kembali pulang, jadi dia dengan enggan setuju untuk melatih Miles dengan imbalan bantuan mencuri data untuk membuat USB baru. Ketika membobol fasilitas penelitian Kingpin, mereka berhadapan dengan kepala ilmuwan Fisk, Olivia Octavius, yang mengungkapkan bahwa Peter pada akhirnya akan memburuk dan mati semakin lama ia tinggal di dimensi mereka. Miles dan Peter kemudian diselamatkan oleh Gwen Stacy, pahlawan wanita lain yang berasal dari dimensi lain. Gwen membawa Peter dan Miles ke May Parker, yang melindungi Spider-Man Noir, Spider-Ham, dan Peni Parker dan SP // dr, yang juga memburuk. Miles menawarkan untuk menonaktifkan akselerator untuk mengirim Spider-People lainnya pulang.\n" +
                        "\n" +
                        "The Spider-People mencoba untuk mengajarkan Miles bagaimana mengendalikan kekuatannya, tetapi Miles menjadi kewalahan dan mundur ke rumah Aaron, di mana ia menemukan bahwa Aaron adalah Prowler. Miles kembali ke rumah May, di mana Peni telah menyelesaikan USB untuk menghentikan akselerator. Namun, ia diikuti oleh Fisk, Prowler, Octavius, Scorpion dan Tombstone, yang mengarah ke perkelahian. Miles melarikan diri dari rumah May selama pertempuran tetapi ditangkap oleh Prowler, yang bersiap untuk membunuhnya. Miles membuka topengnya, menyebabkan Aaron menyadari bahwa ia telah memburu keponakannya sendiri. Aaron memutuskan untuk tidak membunuh Miles, jadi Fisk menembak dan membunuh Aaron.\n" +
                        "\n" +
                        "Ketika Spider-People bersiap menghadapi Fisk, Peter menahan Miles di asramanya dan meninggalkannya demi keselamatannya sendiri, memutuskan untuk mengorbankan dirinya dengan mengambil tempat Miles dalam menonaktifkan akselerator. Jefferson tiba di asrama Miles untuk memberitahunya tentang kematian Aaron dan, berasumsi bahwa Miles tidak ingin berbicara dengannya, meminta maaf atas kesalahannya. Akhirnya mengendalikan kekuatannya, Miles pergi ke Bibi May, di mana ia menyemprotkan cat setelan klasik Peter untuk dirinya sendiri, kemudian bergabung dengan Spider-People lainnya dan membantu mereka mengalahkan para orang-orang Fisk sebelum mengaktifkan USB dan mengirim semua Spider-People kembali pulang. Fisk dan Miles bertarung sepanjang akselerator, menarik perhatian Jefferson. Jefferson menyadari bahwa Spider-Man bukanlah musuh dan mendukungnya, memberi Miles motivasi untuk mengalahkan Fisk, menghancurkan akselerator. Pihak berwenang menangkap Fisk dan orang-orangnya, Jefferson mengakui Spider-Man sebagai pahlawan dan Miles memikul tanggung jawab dalam kehidupan barunya. Belakangan, Gwen menemukan cara untuk menghubungi Miles lintas dimensi.\n" +
                        "\n" +
                        "Di dimensi lain, asisten Miguel O'Hara, Lyra, memberi tahu dia tentang krisis dan memberinya teknologi berpindah dimensi. Dia memutuskan untuk \"kembali ke awal\", dan akhirnya berdebat dengan Spider-Man semesta itu.\n",
                "https://www.youtube.com/watch?v=g4Hbz2jLxvQ"
        );

        tambahFilm(film4, db);
        idFilm++;

        //Data5
        try{
            tempDate = sdFormat.parse("4/11/2016");
        }catch (ParseException er){
            er.printStackTrace();
        }
        Film film5 = new Film(
                idFilm,
                "DOCTOR STRANGE",
                storeImageFile(R.drawable.film5),
                tempDate,
                "Hidup Stephen Strange , seorang dokter bedah pintar yang sombong, mendadak berubah drastis. Sebuah kecelakaan membuat kemampuan tangannya menjadi sangat terbatas. Bertekad untuk menyembuhkan kondisinya, ia pun berpetualang mencari obat untuk memulihkan lengannya.\n" +
                        "\n" +
                        "Perjalanan tersebut mempertemukan sang doktor bedah dengan penyihir bernama The Ancient One , yang kemudian mengangkat Strange menjadi murid, dengan tujuan menjadikan ia sebagai pelindung alam manusia. Kali ini Strange harus mengesampingkan egonya, dan menggunakan segala kemampuannya untuk mnenjadi perantara antara dimensi manusia dan dimensi lain.\n",
                "https://www.youtube.com/watch?v=Lt-U_t2pUHI"
        );

        tambahFilm(film5, db);
    }
}

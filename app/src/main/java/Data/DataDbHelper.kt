package Data

import Models.MarcaTabaco
import Models.MezclaTabaco
import Models.SaboresTabaco
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.tabacosshishaspain.R

val DATABASE_NAME = "ShishasDB"
class DataDbHelper (context : Context) :SQLiteOpenHelper(context,DATABASE_NAME,null,2){


    private val values: ContentValues = ContentValues()
    private val valuesSabores: ContentValues = ContentValues()
    private val valuesMezclas: ContentValues = ContentValues()

    override fun onCreate(db: SQLiteDatabase?) {

    db?.execSQL("DROP TABLE IF EXISTS "+Tables.Tabacos.TABLE_NAME+";")
    db?.execSQL("DROP TABLE IF EXISTS "+Tables.Sabores.TABLE_NAME+";")
    db?.execSQL("DROP TABLE IF EXISTS "+Tables.Mezclas.TABLE_NAME+";")

    db?.execSQL("CREATE TABLE "+Tables.Tabacos.TABLE_NAME+" ("+Tables.Tabacos._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                Tables.Tabacos.COLUMN_NOMBRE+ " TEXT NOT NULL,"+
                Tables.Tabacos.COLUMN_IMAGEN+ " TEXT NOT NULL)");
    db?.execSQL("CREATE TABLE "+Tables.Mezclas.TABLE_NAME+" ("+Tables.Mezclas._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                Tables.Mezclas.COLUMN_MARCA1+ " TEXT,"+
                Tables.Mezclas.COLUMN_MARCA2+ " TEXT,"+
                Tables.Mezclas.COLUMN_SABOR1+ " TEXT,"+
                Tables.Mezclas.COLUMN_SABOR2+ " TEXT,"+
                Tables.Mezclas.COLUMN_PORCENTAJE1+ " INTEGER,"+
                Tables.Mezclas.COLUMN_PORCENTAJE2+ " INTEGER)");
    db?.execSQL("CREATE TABLE "+Tables.Sabores.TABLE_NAME+" ("+Tables.Sabores._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            Tables.Sabores.COLUMN_NOMBRE+ " TEXT NOT NULL,"+
            Tables.Sabores.COLUMN_DESCRIPCION+ " TEXT NOT NULL,"+
            Tables.Sabores.COLUMN_IDTABACO+ " INTEGER NOT NULL,"+
            " FOREIGN KEY (" +Tables.Sabores.COLUMN_IDTABACO+") REFERENCES "+Tables.Tabacos.TABLE_NAME+"( "+Tables.Tabacos._ID+"));")

        insertTabacos(db)
        insertSabores(db)
        insertMezclas(db)


    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun insertTabacos (db: SQLiteDatabase?){
        val tabacos: ArrayList<MarcaTabaco> = createMarcasTabaco()
        //val db = this.readableDatabase

        for (objTabaco in tabacos) {
            values.put(Tables.Tabacos._ID, objTabaco.id)
            values.put(Tables.Tabacos.COLUMN_NOMBRE, objTabaco.nombre)
            values.put(Tables.Tabacos.COLUMN_IMAGEN, objTabaco.imagen)
            db!!.insert(Tables.Tabacos.TABLE_NAME,null,values)
        }

    }

    fun insertSabores (db: SQLiteDatabase?){
        val sabores = crearSaboresTabaco()
        for (sabor in sabores) {
            valuesSabores.put(Tables.Sabores.COLUMN_IDTABACO, sabor.idTabaco)
            valuesSabores.put(Tables.Sabores.COLUMN_NOMBRE, sabor.nombre)
            valuesSabores.put(Tables.Sabores.COLUMN_DESCRIPCION, sabor.descripcion)
            db!!.insert(Tables.Sabores.TABLE_NAME,null,valuesSabores)
        }

    }
    fun insertMezclas (db: SQLiteDatabase?){

        val mezclas = añadirMezclas()
        for (objMezcla in mezclas) {
            valuesMezclas.put(Tables.Mezclas.COLUMN_MARCA1, objMezcla.tabaco1)
            valuesMezclas.put(Tables.Mezclas.COLUMN_MARCA2, objMezcla.tabaco2)
            valuesMezclas.put(Tables.Mezclas.COLUMN_SABOR1, objMezcla.sabor1)
            valuesMezclas.put(Tables.Mezclas.COLUMN_SABOR2, objMezcla.sabor2)
            valuesMezclas.put(Tables.Mezclas.COLUMN_PORCENTAJE1, objMezcla.porcentaje1)
            valuesMezclas.put(Tables.Mezclas.COLUMN_PORCENTAJE2, objMezcla.porcentaje2)
            db!!.insert(Tables.Mezclas.TABLE_NAME,null,valuesMezclas)

        }
    }

    fun getTabacos(): MutableList<MarcaTabaco>{

            val list: MutableList<MarcaTabaco> = ArrayList()
            val db = this.readableDatabase
            val query = "Select * from "+ Tables.Tabacos.TABLE_NAME
            val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val marcaTabaco = MarcaTabaco()
                marcaTabaco.id =
                    result.getInt(result.getColumnIndex(Tables.Tabacos._ID))
                marcaTabaco.nombre =
                    result.getString(result.getColumnIndex(Tables.Tabacos.COLUMN_NOMBRE))
                marcaTabaco.imagen =
                    result.getInt(result.getColumnIndex(Tables.Tabacos.COLUMN_IMAGEN))
                list.add(marcaTabaco)
            } while (result.moveToNext())
        }
            return list
        }

    fun getSabores(idTabaco:Int): MutableList<SaboresTabaco>{

        val list: MutableList<SaboresTabaco> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM "+ Tables.Sabores.TABLE_NAME +" WHERE "+Tables.Sabores.COLUMN_IDTABACO +" = "+idTabaco +";"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val saborTabaco = SaboresTabaco()
                saborTabaco.idTabaco =
                    result.getInt(result.getColumnIndex(Tables.Sabores.COLUMN_IDTABACO))
                saborTabaco.nombre =
                    result.getString(result.getColumnIndex(Tables.Sabores.COLUMN_NOMBRE))
                saborTabaco.descripcion =
                    result.getString(result.getColumnIndex(Tables.Sabores.COLUMN_DESCRIPCION))
                list.add(saborTabaco)
            } while (result.moveToNext())
        }
        return list
    }


    fun getMezclas(): MutableList<MezclaTabaco>{

        val list: MutableList<MezclaTabaco> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM "+ Tables.Mezclas.TABLE_NAME
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val saborTabaco = MezclaTabaco()
                saborTabaco.tabaco1 =
                    result.getString(result.getColumnIndex(Tables.Mezclas.COLUMN_MARCA1))
                saborTabaco.tabaco2 =
                    result.getString(result.getColumnIndex(Tables.Mezclas.COLUMN_MARCA2))
                saborTabaco.sabor1 =
                    result.getString(result.getColumnIndex(Tables.Mezclas.COLUMN_SABOR1))
                saborTabaco.sabor2 =
                    result.getString(result.getColumnIndex(Tables.Mezclas.COLUMN_SABOR2))
                saborTabaco.porcentaje1 =
                    result.getInt(result.getColumnIndex(Tables.Mezclas.COLUMN_PORCENTAJE1))
                saborTabaco.porcentaje2 =
                    result.getInt(result.getColumnIndex(Tables.Mezclas.COLUMN_PORCENTAJE2))
                list.add(saborTabaco)
            } while (result.moveToNext())
        }
        return list
    }

    fun createMarcasTabaco(): ArrayList<MarcaTabaco>{
        var tabacos:ArrayList<MarcaTabaco> = ArrayList()
        tabacos.add(
            MarcaTabaco(
                0,
                "Adalya",
                R.drawable.adalya
            )
        )
        tabacos.add(
            MarcaTabaco(
                1,
                "Overdozz",
                R.drawable.overdozz
            )
        )
        tabacos.add(
            MarcaTabaco(
                2,
                "Privilege",
                R.drawable.privilege
            )
        )
        tabacos.add(
            MarcaTabaco(
                3,
                "Blue Horse",
                R.drawable.bluehorse
            )
        )
        return tabacos
    }

    fun crearSaboresTabaco(): ArrayList<SaboresTabaco>{
        var tabacos = añadirSaboresAdalya()
        tabacos.addAll(añadirSaboresOverdozz())
        tabacos.addAll(añadirSaboresBh())
        tabacos.addAll(añadirSaboresPrivilege())
        return tabacos
    }

    fun añadirSaboresAdalya(): ArrayList<SaboresTabaco>{
        var tabacosAdalya:ArrayList<SaboresTabaco> = ArrayList()
        var tabaco1= SaboresTabaco(0,"BERLIN NIGHTS","Melocotón y menta ")
        tabacosAdalya.add(tabaco1)
        var tabaco2= SaboresTabaco(0,"BLUE DRAGAN ","Fruta Dragón, Arándano y Bombón Helado")
        tabacosAdalya.add(tabaco2)
        var tabaco3= SaboresTabaco(0,"BLUE I'SS","Arándano y hielo")
        tabacosAdalya.add(tabaco3)
        var tabaco4= SaboresTabaco(0,"BLUE YELLOW","Melón y arándanos")
        tabacosAdalya.add(tabaco4)
        var tabaco5= SaboresTabaco(0,"CHUNG WOOD KIZZ","Chicle, canela y menta")
        tabacosAdalya.add(tabaco5)
        var tabaco6= SaboresTabaco(0,"DRAGAN CODE","Cola y bebidas energéticas")
        tabacosAdalya.add(tabaco6)
        var tabaco7= SaboresTabaco(0,"ESKIMO LEON ","Kiwi, limón y hielo")
        tabacosAdalya.add(tabaco7)
        var tabaco8= SaboresTabaco(0,"GREEN LEON KIZZ","Lima y menta")
        tabacosAdalya.add(tabaco8)
        var tabaco9= SaboresTabaco(0,"HAWAII","Piña, mango y menta")
        tabacosAdalya.add(tabaco9)
        var tabaco10= SaboresTabaco(0,"I'SS","Hielo")
        tabacosAdalya.add(tabaco10)
        var tabaco11= SaboresTabaco(0,"I'SS BONI","Menta dulce y chicle")
        tabacosAdalya.add(tabaco11)
        var tabaco12= SaboresTabaco(0,"I'SS LIE ON THE ROCKS","Hielo, lima, arándano y chicle")
        tabacosAdalya.add(tabaco12)
        var tabaco13= SaboresTabaco(0,"KIZZ","Menta")
        tabacosAdalya.add(tabaco13)
        var tabaco14= SaboresTabaco(0,"LADY KILLER","Melocotón, mango, menta y hielo")
        tabacosAdalya.add(tabaco14)
        var tabaco15= SaboresTabaco(0,"LEON PI ","Tarta de limón")
        tabacosAdalya.add(tabaco15)
        var tabaco16= SaboresTabaco(0,"LOVE 66","La receta de oro de hookah de toda la vida. 6 sabores y Menta.")
        tabacosAdalya.add(tabaco16)
        var tabaco17= SaboresTabaco(0,"MANKO TANKO ","Mango y maracuyá ")
        tabacosAdalya.add(tabaco17)
        var tabaco18= SaboresTabaco(0,"MANKO TANKO I'SS","Mango, maracuyá y hielo")
        tabacosAdalya.add(tabaco18)
        var tabaco19= SaboresTabaco(0,"MOON DREAM","Vainilla, chicle, canela y menta")
        tabacosAdalya.add(tabaco19)
        var tabaco20= SaboresTabaco(0,"PAIR KIZZ","Pera y menta ")
        tabacosAdalya.add(tabaco20)
        var tabaco21= SaboresTabaco(0,"PARADISO I'SS","Pomelo y hielo")
        tabacosAdalya.add(tabaco21)
        var tabaco22= SaboresTabaco(0,"PASSION KIZZ","Maracuyá y menta")
        tabacosAdalya.add(tabaco22)
        var tabaco23= SaboresTabaco(0,"POWER","Bebida energética")
        tabacosAdalya.add(tabaco23)
        var tabaco24= SaboresTabaco(0,"PYCH BLUE KIZZ","Melocotón, arándanos y menta")
        tabacosAdalya.add(tabaco24)
        var tabaco25= SaboresTabaco(0,"RHAPSODY","Melocotón, piña, mora, menta y hielo")
        tabacosAdalya.add(tabaco25)
        var tabaco26= SaboresTabaco(0,"SKYFALL","Melocotón, sandía, melón y hielo")
        tabacosAdalya.add(tabaco26)
        var tabaco27= SaboresTabaco(0,"SWEES BOMB","Bombón suizo")
        tabacosAdalya.add(tabaco27)
        var tabaco28= SaboresTabaco(0,"THREE ANGELS","Pomelo, Maracuya y Hielo")
        tabacosAdalya.add(tabaco28)
        var tabaco29= SaboresTabaco(0,"TONY'S DESTINY","Melocotón, maracuyá y hielo")
        tabacosAdalya.add(tabaco29)
        var tabaco30= SaboresTabaco(0,"TWO APP","Dos manzanas. Verde y Roja")
        tabacosAdalya.add(tabaco30)
        var tabaco31= SaboresTabaco(0,"TYNKY WYNKY","Uva, limón y cardamomo")
        tabacosAdalya.add(tabaco31)
        var tabaco32= SaboresTabaco(0,"WATERMELON KIZZ","Sandía y menta ")
        tabacosAdalya.add(tabaco32)

        return tabacosAdalya
    }
    fun añadirSaboresOverdozz(): ArrayList<SaboresTabaco>{
        var tabacosOverdozz:ArrayList<SaboresTabaco> = ArrayList()
        var tabaco1= SaboresTabaco(1,"1st LOVE ","Delicioso y fresco zumo de chicle de sandía ")
        tabacosOverdozz.add(tabaco1)
        var tabaco2= SaboresTabaco(1,"24 KARATINE ","Dulce galleta con un toque inconfundible de plátano")
        tabacosOverdozz.add(tabaco2)
        var tabaco3= SaboresTabaco(1,"BAD HABIT","Auténtico sabor a uva")
        tabacosOverdozz.add(tabaco3)
        var tabaco4= SaboresTabaco(1,"CRAZY EX","Pan masala y mango")
        tabacosOverdozz.add(tabaco4)
        var tabaco5= SaboresTabaco(1,"DOPOMINE","Cítricos, uva y arándanos")
        tabacosOverdozz.add(tabaco5)
        var tabaco6= SaboresTabaco(1,"DOUBLE TROUBLE","Doble manzana original ")
        tabacosOverdozz.add(tabaco6)
        var tabaco7= SaboresTabaco(1,"FRESH GREENS","Poderosa mezcla mentolada ")
        tabacosOverdozz.add(tabaco7)
        var tabaco8= SaboresTabaco(1,"GO FOR BROKE","Mezcla de uva con menta")
        tabacosOverdozz.add(tabaco8)
        var tabaco9= SaboresTabaco(1,"HEAT WAVE","Sabor único a chicle")
        tabacosOverdozz.add(tabaco9)
        var tabaco10= SaboresTabaco(1,"JUDGEMENT DAY","Melocotón y coco")
        tabacosOverdozz.add(tabaco10)
        var tabaco11= SaboresTabaco(1,"KAFFEINE ADDIKT","Mezcla de café con cítricos y mentas")
        tabacosOverdozz.add(tabaco11)
        var tabaco12= SaboresTabaco(1,"LOVE BUG","Perfecta mezcla de frutas tropicales y mentas")
        tabacosOverdozz.add(tabaco12)
        var tabaco13= SaboresTabaco(1,"LUSIDREM","Deliciosa mezcla de sandía y menta")
        tabacosOverdozz.add(tabaco13)
        var tabaco14= SaboresTabaco(1,"ONE NIGHT STAND","Fruta de la pasión con toques cítricos")
        tabacosOverdozz.add(tabaco14)
        var tabaco15= SaboresTabaco(1,"PSYCH OUT","Mezcla de zumo de piña y menta")
        tabacosOverdozz.add(tabaco15)
        var tabaco16= SaboresTabaco(1,"SUMMER FLING","Auténtico arándano combinado con menta")
        tabacosOverdozz.add(tabaco16)
        var tabaco17= SaboresTabaco(1,"WILD NIGH OUT","Tarta de limón")
        tabacosOverdozz.add(tabaco17)
        var tabaco18= SaboresTabaco(1,"ZERO GRAVITY","Equilibrio perfecto entre limón y menta")
        tabacosOverdozz.add(tabaco18)

        return tabacosOverdozz
    }

    fun añadirSaboresBh(): ArrayList<SaboresTabaco>{
        var tabacosBh:ArrayList<SaboresTabaco> = ArrayList()
        var tabaco1= SaboresTabaco(3,"BAKU NIGHTS","Perfecta combinación entre chicle y menta. ")
        tabacosBh.add(tabaco1)
        var tabaco2= SaboresTabaco(3,"DISCOVERY","Disfruta de cítricos con un toque de chicle")
        tabacosBh.add(tabaco2)
        var tabaco3= SaboresTabaco(3,"LA BEIRUT","Gran mezcla de café con un toque de mentol.")
        tabacosBh.add(tabaco3)
        var tabaco4= SaboresTabaco(3,"LONDON FOG","Fantástica combinación de pomelo, rosa y menta dulce")
        tabacosBh.add(tabaco4)
        var tabaco5= SaboresTabaco(3,"MI AMOR","Pues eso, amor a primera vista. Piña, plátano y mentol")
        tabacosBh.add(tabaco5)
        var tabaco6= SaboresTabaco(3,"MOSKOW EVENINGS","Desde Rusia con amor. Gran mezcla de melocotón, sandía y menta ")
        tabacosBh.add(tabaco6)
        var tabaco7= SaboresTabaco(3,"THE COLDEST GREEN","Déjate seducir por manzana y lima en una mezlca maravillosa")
        tabacosBh.add(tabaco7)
        var tabaco8= SaboresTabaco(3,"THE PERFECT STORM","Mango, fruta de la pasión, mentol y carambola, una fruta estrella.")
        tabacosBh.add(tabaco8)
        var tabaco9= SaboresTabaco(3,"THOR'S FLASH","Un auténtico trueno. Menta y mentol. Helado")
        tabacosBh.add(tabaco9)
        var tabaco10= SaboresTabaco(3,"WIND OF AMAZON","Encontrarás un auténtico melocotón envuelto en frescura tropical ")
        tabacosBh.add(tabaco10)


        return tabacosBh
    }
    fun añadirSaboresPrivilege(): ArrayList<SaboresTabaco>{
        var tabacosPrivilege:ArrayList<SaboresTabaco> = ArrayList()
        var tabaco1= SaboresTabaco(2,"BREATHE ","Equilibrio perfecto entre menta y fresa")
        tabacosPrivilege.add(tabaco1)
        var tabaco2= SaboresTabaco(2,"BREAK","Tarta de arándanos")
        tabacosPrivilege.add(tabaco2)
        var tabaco3= SaboresTabaco(2,"DANCE","Jugoso melocotón con un toque de menta")
        tabacosPrivilege.add(tabaco3)
        var tabaco4= SaboresTabaco(2,"DISCOVER","Refrescante y puro sabor a limón")
        tabacosPrivilege.add(tabaco4)
        var tabaco5= SaboresTabaco(2,"DIVE","Melón mentolado")
        tabacosPrivilege.add(tabaco5)
        var tabaco6= SaboresTabaco(2,"DREAM","Frío polar. Pura hierbabuena, aromática e intensa")
        tabacosPrivilege.add(tabaco6)
        var tabaco7= SaboresTabaco(2,"ENJOY","Mix de piña, menta y caramelo")
        tabacosPrivilege.add(tabaco7)
        var tabaco8= SaboresTabaco(2,"FEEL","Pera y menta")
        tabacosPrivilege.add(tabaco8)
        var tabaco9= SaboresTabaco(2,"FIGHT","Abre tus pulmones a la intensidad de la menta silvestre")
        tabacosPrivilege.add(tabaco9)
        var tabaco10= SaboresTabaco(2,"JUMP","Tarta de limón")
        tabacosPrivilege.add(tabaco10)
        var tabaco11= SaboresTabaco(2,"LIKE","Chicle de canela")
        tabacosPrivilege.add(tabaco11)
        var tabaco12= SaboresTabaco(2,"LIVE","Piña Colada")
        tabacosPrivilege.add(tabaco12)
        var tabaco13= SaboresTabaco(2,"LOVE","La dulce potencia de los frutos rojos ")
        tabacosPrivilege.add(tabaco13)
        var tabaco14= SaboresTabaco(2,"PLAY","Sandía helada")
        tabacosPrivilege.add(tabaco14)
        var tabaco15= SaboresTabaco(2,"READ","El postre perfecto, galleta y naranja")
        tabacosPrivilege.add(tabaco15)
        var tabaco16= SaboresTabaco(2,"TRAVEL","Exótica mezcla de cereza y menta")
        tabacosPrivilege.add(tabaco16)


        return tabacosPrivilege
    }

    fun añadirMezclas(): ArrayList<MezclaTabaco>{

        var mezclasTabaco:ArrayList<MezclaTabaco> = ArrayList()
        var mezcla1= MezclaTabaco("Adalya","Adalya","Love 66","Hawaii",50,50)
        mezclasTabaco.add(mezcla1)
        var mezcla2= MezclaTabaco("Adalya","Adalya ","Love 66","Moon Dream",50,50)
        mezclasTabaco.add(mezcla2)
        var mezcla3= MezclaTabaco("Adalya","Adalya","Love 66","Iss boni",60,60)
        mezclasTabaco.add(mezcla3)
        var mezcla4= MezclaTabaco("Adalya","Adalya","Love 66","Berlin Nights",35,65)
        mezclasTabaco.add(mezcla4)
        var mezcla7= MezclaTabaco("Adalya","Adalya","Love 66","Blue Yellow",20,80)
        mezclasTabaco.add(mezcla7)
        var mezcla8= MezclaTabaco("Adalya","Overdozz","Love 66","Psych Out",50,50)
        mezclasTabaco.add(mezcla8)
        var mezcla9= MezclaTabaco("Adalya","Adalya","Leon Pi","Chwng wood kizz",50,50)
        mezclasTabaco.add(mezcla9)
        var mezcla11= MezclaTabaco("Adalya","Overdozz"," L Kill","Go for Broke",50,50)
        mezclasTabaco.add(mezcla11)
        var mezcla12= MezclaTabaco("Adalya","Adalya","Gipsy King","Watermelon Kizz",50,50)
        mezclasTabaco.add(mezcla12)
        var mezcla14= MezclaTabaco("Adalya","Adalya","Delhi tea","Blue Yellow",25,75)
        mezclasTabaco.add(mezcla14)
        var mezcla15= MezclaTabaco("Adalya","Adalya ","Chwng Wood Kizz","Eskimo leon",50,50)
        mezclasTabaco.add(mezcla15)
        var mezcla16= MezclaTabaco("Adalya","Adalya","Chwng Wood Kizz","Blue yellow",70,30)
        mezclasTabaco.add(mezcla16)
        var mezcla17= MezclaTabaco("Adalya","Overdozz ","Chwng Wood Kizz","24 Karatine",60,40)
        mezclasTabaco.add(mezcla17)
        var mezcla18= MezclaTabaco("Adalya","Adalya","Blue Dragan","Moon Dream",50,50)
        mezclasTabaco.add(mezcla18)
        var mezcla19= MezclaTabaco("Adalya","Overdozz","Manco Tanko","Psich Out",65,40)
        mezclasTabaco.add(mezcla19)
        var mezcla21= MezclaTabaco("Adalya","Adalya","Moon Dream","Chicle Canela",65,35)
        mezclasTabaco.add(mezcla21)
        var mezcla22= MezclaTabaco("Adalya","Overdozz","Moon Dream","24 karatine",40,60)
        mezclasTabaco.add(mezcla22)
        var mezcla23= MezclaTabaco("Adalya","Overdozz","Moon Dream","Wild Night Out",60,40)
        mezclasTabaco.add(mezcla23)
        var mezcla24= MezclaTabaco("Adalya","Overdozz","Moon Dream","Zero Gravity",60,40)
        mezclasTabaco.add(mezcla24)
        var mezcla25= MezclaTabaco("Adalya","Adalya","Paradise iss ","Leon pi ",60,40)
        mezclasTabaco.add(mezcla25)
        var mezcla26= MezclaTabaco("Adalya","Overdozz","Three angels","Psichout",40,60)
        mezclasTabaco.add(mezcla26)
        var mezcla28= MezclaTabaco("Overdozz","Adalya","Bad habit","Kizz",80,20)
        mezclasTabaco.add(mezcla28)
        var mezcla29= MezclaTabaco("Overdozz","Adalya","Psych Out","Lady Killer",50,50)
        mezclasTabaco.add(mezcla29)
        var mezcla30= MezclaTabaco("Overdozz","Overdozz","Psych Out","Go for Broke",70,30)
        mezclasTabaco.add(mezcla30)
        var mezcla31= MezclaTabaco("Overdozz","Overdozz","Psych Out","Zero gravity",60,40)
        mezclasTabaco.add(mezcla31)
        var mezcla32= MezclaTabaco("Overdozz","Overdozz","Psych Out","Juicy Dream",40,60)
        mezclasTabaco.add(mezcla32)
        var mezcla33= MezclaTabaco("Overdozz","Overdozz","Wild Night Out","24 Karatine",60,40)
        mezclasTabaco.add(mezcla33)
        var mezcla34= MezclaTabaco("Overdozz","Overdozz","24 karatine","Zero Gravity",30,70)
        mezclasTabaco.add(mezcla34)
        var mezcla36= MezclaTabaco("Overdozz","Overdozz","Zero Gravity","24 Karatine",50,50)
        mezclasTabaco.add(mezcla36)
        var mezcla35= MezclaTabaco("Overdozz","Overdozz","zumo de piña","uva menta ",50,50)
        mezclasTabaco.add(mezcla35)
        var mezcla37= MezclaTabaco("Privilege","Adalya","FEEL","Watermelon Kizz",40,60)
        mezclasTabaco.add(mezcla37)

        return mezclasTabaco
    }


}
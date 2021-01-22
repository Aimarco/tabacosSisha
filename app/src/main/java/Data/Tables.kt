package Data

import Models.MarcaTabaco
import Models.SaboresTabaco

class Tables {

    abstract class Tabacos{
        companion object {

            val _ID = "_id"
            val TABLE_NAME = "Tabacos"
            val COLUMN_NOMBRE = "nombre"
            val COLUMN_IMAGEN = "imagen"
            var tabacos : MutableList<MarcaTabaco> = ArrayList()
        }

    }
    abstract class Sabores{
        companion object {

            val _ID = "_id"
            val TABLE_NAME = "Sabores"
            val COLUMN_IDTABACO = "idTabaco"
            val COLUMN_NOMBRE = "nombre"
            val COLUMN_DESCRIPCION = "descripcion"
            var tabacos : MutableList<SaboresTabaco> = ArrayList()
        }

    }
    abstract class Mezclas{
        companion object {

            val _ID = "_id"
            val TABLE_NAME = "Mezclas"
            val COLUMN_MARCA1 = "marca1"
            val COLUMN_MARCA2 = "marca2"
            val COLUMN_SABOR1 = "sabor1"
            val COLUMN_SABOR2 = "sabor2"
            val COLUMN_PORCENTAJE1 = "porcentaje1"
            val COLUMN_PORCENTAJE2 = "porcentaje2"
            var tabacos : MutableList<MarcaTabaco> = ArrayList()
        }

    }
}
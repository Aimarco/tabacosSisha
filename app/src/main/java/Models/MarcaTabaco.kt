package Models

import java.io.Serializable


class MarcaTabaco (
    var id:Int,
    var nombre:String,
    var imagen:Int) : Serializable
{

    constructor():this(0,"",0)
}

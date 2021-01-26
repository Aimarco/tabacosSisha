package Utils

import com.example.tabacosshishaspain.R

object Utils {

    fun convertImage(nombre:String) : Int {
        var nombreReturn = 0
        when (nombre.toLowerCase().trim()) {
            "adalya" -> nombreReturn = R.drawable.adalya
            "blue horse" -> nombreReturn = R.drawable.bluehorse
            "overdozz" -> nombreReturn = R.drawable.overdozz
            "privilege" -> nombreReturn = R.drawable.privilege
        }
        return nombreReturn
    }
}
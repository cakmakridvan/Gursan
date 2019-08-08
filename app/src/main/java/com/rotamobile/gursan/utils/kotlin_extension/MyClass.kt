
package com.rotamobile.gursan.utils.kotlin_extension

class MyClass{

     fun faktoriyelBul(sayi:Int) : Int{

        var sonuc = 1
        for (i in 1..sayi)
            sonuc = sonuc * i
        return sonuc
    }
}


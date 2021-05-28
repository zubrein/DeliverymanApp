package xit.zubrein.gogocarrier.Utils

import xit.zubrein.gogocarrier.model.ModelArea

object DeliveryAreaUtils {

    fun addToLIst(list: List<ModelArea>,item:ModelArea): MutableList<String>{

        val newList : MutableList<String> = mutableListOf()
        for(items in list){
            newList.add(items.id.toString())
        }

        newList.add(item.id.toString())

        return newList

    }

    fun deleteFromLIst(list: List<ModelArea>,item:ModelArea): MutableList<String>{

        val newList : MutableList<String> = mutableListOf()
        for(items in list){
            newList.add(items.id.toString())
        }

        newList.remove(item.id.toString())

        return newList

    }


}
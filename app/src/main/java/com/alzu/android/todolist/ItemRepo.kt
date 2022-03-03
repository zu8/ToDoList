package com.alzu.android.todolist

object ItemRepo {
    fun getItemsToDo() = listOf(
        Item("thing #1","some description","22-10-2022",false),
        Item("thing #2","another description","16-08-2022",false),
        Item("thing #3","","08-04-2022",false),
        Item("thing #4","and so on...description","19-11-2022",false),
        Item("thing #5","","24-06-2022",false),
        Item("thing #6","and description,description","15-01-2022",false),
        Item("thing #7","and so on...description","25-03-2022",false),
        Item("thing #8","","10-10-2022",false),
        Item("thing #9 ghgh ghghg ghghgh sesef sefsef sefsefef sefsefsef","and description,description","15-01-2022",false)
    ).toMutableList()
    fun getItemsDone() = listOf(
        Item("thing #11","","02-10-2021",true),
        Item("thing #22","another description","14-08-2021",true),
        Item("thing #33","","08-04-2020",true),
        Item("thing #44","and so on...description","28-02-2021",true)
    ).toMutableList()

}
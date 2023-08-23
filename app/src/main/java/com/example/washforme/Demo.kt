package com.example.washforme


class Demo {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val a: ArrayList<Item> = arrayListOf(
                Item(1, "Shirt", 10, 0),
                Item(2, "Shirt1", 11, 0),
                Item(3, "Shirt2", 12, 0),
                Item(4, "Shirt3", 13, 0),
                Item(5, "Shirt4", 14, 0),
                Item(6, "Shirt5", 15, 0),
            )

            val b = a.clone() as ArrayList<Item>

            b.add(Item(7, "abc", 5, 0))
            println(a)
            println(b)
        }
    }
}

data class Item(
    val id: Int,
    val name: String,
    val amount: Int,
    val count: Int
)
package application.example.myturn

import java.sql.Time

data class Turn(
    var id: Int,
    var name: String,
    var time: String,
    var email: String,
    var description: String
){
    constructor() : this(
        0,
        "",
        "",
        "",
        ""
    )
}
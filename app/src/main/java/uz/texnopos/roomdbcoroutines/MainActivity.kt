package uz.texnopos.roomdbcoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import uz.texnopos.roomdbcoroutines.databinding.ActivityMainBinding
import uz.texnopos.roomdbcoroutines.db.User
import uz.texnopos.roomdbcoroutines.db.UserDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var db: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Room.databaseBuilder(applicationContext, UserDatabase::class.java, "database-name")
            .build()

        val user1 = User("James", "Bond", 35,true)
        val user2 = User("Alice", "Smith", 28,true)
        val user3 = User("Michael", "M.", 25,false)

        GlobalScope.launch {
            db.userDao().deleteAll()
            db.userDao().insert(user1, user2, user3)
            displayUsers()
        }
    }

    private suspend fun displayUsers(){
        val users = db.userDao().getAllUsers()
        var displayText = ""
        for(user in users){
            displayText += "${user.name} ${user.lastName} - ${user.age}\n"
        }
        binding.display.text = displayText
    }
}
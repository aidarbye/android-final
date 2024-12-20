import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidfinalxml.models.JokeModel
import com.example.androidfinalxml.network.ApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class JokeViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isFavoritesLoading = MutableLiveData<Boolean>()
    val isFavoritesLoading: LiveData<Boolean> get() = _isFavoritesLoading

    private val _jokes = MutableLiveData<List<JokeModel>>()
    val jokes: LiveData<List<JokeModel>> get() = _jokes

    private val _favoriteJokes = MutableLiveData<List<JokeModel>>()
    val favoriteJokes: LiveData<List<JokeModel>> get() = _favoriteJokes

    private val db = FirebaseFirestore.getInstance()

    fun getJokeList() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val jokes = ApiClient.getJokeList()
                _jokes.postValue(jokes)
            } catch (e: Exception) {
                _jokes.postValue(emptyList())
                Log.e("JokeViewModel", "Error fetching jokes: ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun saveFavoriteJoke(joke: JokeModel) {
        val updatedFavorites = _favoriteJokes.value?.toMutableList() ?: mutableListOf()
        updatedFavorites.add(joke)
        _favoriteJokes.postValue(updatedFavorites)

        viewModelScope.launch {
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            if (userId != null) {
                try {
                    val jokeMap = hashMapOf(
                        "type" to joke.type,
                        "setup" to joke.setup,
                        "punchline" to joke.punchline,
                        "id" to joke.id
                    )
                    db.collection("users")
                        .document(userId)
                        .collection("favorites")
                        .add(jokeMap)
                        .await()

                    Log.d("JokeViewModel", "Favorite joke added successfully.")
                } catch (e: Exception) {
                    Log.e("JokeViewModel", "Error adding favorite joke: ${e.message}")
                    updatedFavorites.remove(joke)
                    _favoriteJokes.postValue(updatedFavorites)
                }
            }
        }
    }

    fun removeFavoriteJoke(joke: JokeModel) {
        val updatedFavorites = _favoriteJokes.value?.toMutableList() ?: mutableListOf()
        updatedFavorites.remove(joke)
        _favoriteJokes.postValue(updatedFavorites)

        viewModelScope.launch {
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            if (userId != null) {
                try {
                    val querySnapshot = db.collection("users")
                        .document(userId)
                        .collection("favorites")
                        .whereEqualTo("id", joke.id)
                        .get()
                        .await()

                    if (querySnapshot.documents.isNotEmpty()) {
                        val documentToDelete = querySnapshot.documents.first()
                        documentToDelete.reference.delete().await()
                        Log.d("JokeViewModel", "Favorite joke removed successfully.")
                    } else {
                        Log.d("JokeViewModel", "No such joke found in favorites.")
                    }
                } catch (e: Exception) {
                    Log.e("JokeViewModel", "Error removing favorite joke: ${e.message}")
                    updatedFavorites.add(joke)
                    _favoriteJokes.postValue(updatedFavorites)
                }
            }
        }
    }


    fun getFavoriteJokes() {
        viewModelScope.launch {
            _isFavoritesLoading.postValue(true)
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            if (userId != null) {
                try {
                    val result = db.collection("users")
                        .document(userId)
                        .collection("favorites")
                        .get()
                        .await()

                    val jokes = result.documents.mapNotNull { document ->
                        val type = document.getString("type")
                        val setup = document.getString("setup")
                        val punchline = document.getString("punchline")
                        val id = document.getLong("id")?.toInt()

                        if (setup != null && punchline != null && id != null) {
                            JokeModel(type, setup, punchline, id)
                        } else {
                            null
                        }
                    }
                    _favoriteJokes.postValue(jokes)
                } catch (e: Exception) {
                    Log.e("JokeViewModel", "Error getting favorite jokes: ${e.message}")
                } finally {
                    _isFavoritesLoading.postValue(false)
                }
            } else {
                _isFavoritesLoading.postValue(false)
            }
        }
    }
}

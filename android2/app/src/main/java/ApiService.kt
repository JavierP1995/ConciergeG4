import model.Department
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("Departments/")
    fun getDepartments(): Call<List<Department>>

}
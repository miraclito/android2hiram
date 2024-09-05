package pe.edu.upeu.asistenciaupeunagi2.repository

import pe.edu.upeu.asistenciaupeunagi2.data.remote.RestUsuario
import pe.edu.upeu.asistenciaupeunagi2.modelo.UsuarioDto
import pe.edu.upeu.asistenciaupeunagi2.modelo.UsuarioResp
import retrofit2.Response
import javax.inject.Inject

interface UsuarioRepository {
    suspend fun loginUsuario(user:UsuarioDto):
            Response<UsuarioResp>
}
class UsuarioRepositoryImp @Inject constructor(private val restUsuario: RestUsuario):UsuarioRepository{
    override suspend fun loginUsuario(user:UsuarioDto):
            Response<UsuarioResp>{
        return restUsuario.login(user)
    }
}
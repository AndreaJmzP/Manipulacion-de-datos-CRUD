package edu.itvo.roompersistence

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RoomPersistenceApp : Application()
//elemento principal de nuestra aplicación, que sepa que se debe inicializar con Hilt busca todos los archivos cuales se van a inyectar
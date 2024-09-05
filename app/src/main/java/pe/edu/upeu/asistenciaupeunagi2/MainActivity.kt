package pe.edu.upeu.asistenciaupeunagi2

import android.graphics.PorterDuff
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import pe.edu.upeu.asistenciaupeujcn.ui.presentation.components.FabItem
import pe.edu.upeu.asistenciaupeunagi2.ui.presentation.components.AppDrawer
import pe.edu.upeu.asistenciaupeunagi2.ui.presentation.components.CustomTopAppBar
import pe.edu.upeu.asistenciaupeunagi2.ui.presentation.components.Dialog
import pe.edu.upeu.asistenciaupeunagi2.ui.navigation.Destinations
import pe.edu.upeu.asistenciaupeunagi2.ui.navigation.NavigationHost
import pe.edu.upeu.asistenciaupeunagi2.ui.theme.AsistenciaUpeUNagi2Theme
import pe.edu.upeu.asistenciaupeunagi2.ui.theme.DarkGreenColors
import pe.edu.upeu.asistenciaupeunagi2.ui.theme.DarkPurpleColors
import pe.edu.upeu.asistenciaupeunagi2.ui.theme.DarkRedColors
import pe.edu.upeu.asistenciaupeunagi2.ui.theme.LightGreenColors
import pe.edu.upeu.asistenciaupeunagi2.ui.theme.LightPurpleColors
import pe.edu.upeu.asistenciaupeunagi2.ui.theme.LightRedColors
import pe.edu.upeu.asistenciaupeunagi2.ui.theme.ThemeType
import pe.edu.upeu.asistenciaupeunagi2.utils.TokenUtils
import pe.edu.upeu.asistenciaupeunagi2.utils.isNight

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeType= remember{ mutableStateOf(ThemeType.RED) }
            val darkThemex= isNight()
            val darkTheme = remember { mutableStateOf(darkThemex) }
            val colorScheme=when(themeType.value){
                ThemeType.PURPLE->{if (darkTheme.value) DarkPurpleColors else LightPurpleColors}
                ThemeType.RED->{if (darkTheme.value) DarkRedColors else LightRedColors}
                ThemeType.GREEN->{if (darkTheme.value) DarkGreenColors else LightGreenColors}else->{LightRedColors}
            }
            TokenUtils.CONTEXTO_APPX=this@MainActivity
            AsistenciaUpeUNagi2Theme (colorScheme=colorScheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Greeting("Android")
                    val navController= rememberNavController()
                    MainScreen(navController, darkMode = darkTheme,
                        themeType=themeType)
                }
                /*Scaffold(modifier = Modifier.fillMaxSize()) {
               innerPadding ->
                Greeting(
                name = "Android",
                modifier = Modifier.padding(innerPadding)
                )
                }*/
            }
            /*AsistenciaUpeUNagi2Theme(colorScheme = DarkGreenColors) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }*/
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AsistenciaUpeUNagi2Theme(colorScheme = DarkGreenColors) {
        Greeting("Android")
    }
}
@Composable
fun MainScreen(
    navController: NavHostController,
    darkMode: MutableState<Boolean>,
    themeType: MutableState<ThemeType>
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val openDialog = remember { mutableStateOf(false) }
    val navigationItems = listOf(
        Destinations.Pantalla1,
        Destinations.Pantalla2,
        Destinations.Pantalla3,
        Destinations.Pantalla4,
        Destinations.Pantalla5,
        //Destinations.ActividadUI,
        //Destinations.MaterialesxUI,
        //Destinations.PantallaQR
    )
    val navigationItems2 = listOf(
        Destinations.Pantalla1,
        Destinations.Pantalla2,
        Destinations.Pantalla3,
    )
    val currentNavBackStackEntry by
    navController.currentBackStackEntryAsState()
    val currentRoute =
        currentNavBackStackEntry?.destination?.route ?:
        Destinations.Pantalla1.route
    val list = currentRoute.split("/", "?")
    ModalNavigationDrawer (drawerContent = {
            AppDrawer(route = list[0], scope = scope,
                scaffoldState =
                drawerState,
                navController = navController, items =
                navigationItems)
        },
        drawerState = drawerState) {
        val snackbarHostState = remember { SnackbarHostState() }
        val snackbarMessage = "Succeed!"
        val showSnackbar = remember { mutableStateOf(false) }
        val context = LocalContext.current
        val fabItems = listOf(
            FabItem(
                Icons.Filled.ShoppingCart,
                "Shopping Cart"
            ) {
                val toast = Toast.makeText(context, "Hola Mundo",
                        Toast.LENGTH_LONG) // in Activity
                toast.view!!.getBackground().setColorFilter(
                    android.graphics.Color.CYAN,
                    PorterDuff.Mode.SRC_IN)
                toast.show()
            },
            FabItem(
                Icons.Filled.Favorite,
                "Favorite"
            ) { /*TODO*/ }
        )
        Scaffold(
            topBar = { CustomTopAppBar (
                list[0],
                darkMode = darkMode,
                themeType = themeType,
                navController = navController,
                scope = scope,
                scaffoldState = drawerState,
                openDialog={openDialog.value=true}
            )
            }, modifier = Modifier,
            /*floatingActionButton = {
            MultiFloatingActionButton(
            navController=navController,
            fabIcon = Icons.Filled.Add,
            items = fabItems,
            showLabels = true
            )
            },
            floatingActionButtonPosition = FabPosition.End,
            bottomBar = { BottomAppBar {
            BottomNavigationBar(navigationItems2,
           navController =
            navController)
            }}*/
        ) {
            NavigationHost(navController, darkMode, modif= it
            )
        }
    }
    Dialog (showDialog = openDialog.value, dismissDialog = {
        openDialog.value = false })
}
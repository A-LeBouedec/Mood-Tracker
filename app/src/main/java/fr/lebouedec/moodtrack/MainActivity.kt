package fr.lebouedec.moodtrack

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import fr.lebouedec.moodtrack.ui.component.MainScreen
import fr.lebouedec.moodtrack.ui.theme.MoodTrackTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val postNotificationPermission =
                rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
            val scheduleExactAlarmPermission =
                rememberPermissionState(permission = Manifest.permission.SCHEDULE_EXACT_ALARM)

            LaunchedEffect(key1 = true) {
                if (!postNotificationPermission.status.isGranted) {
                    postNotificationPermission.launchPermissionRequest()
                }
                if (!scheduleExactAlarmPermission.status.isGranted) {
                    scheduleExactAlarmPermission.launchPermissionRequest()
                }
            }
            MoodTrackTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(innerPadding = innerPadding)
                }
            }
        }
    }
}

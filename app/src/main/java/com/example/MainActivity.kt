package com.example

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
  private var webView: WebView? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        Scaffold(
          modifier = Modifier
            .fillMaxSize()
            .testTag("sketch_brawler_screen")
        ) { innerPadding ->
          Box(
            modifier = Modifier
              .fillMaxSize()
              .padding(innerPadding)
              .background(Color(0xFF121118))
          ) {
            GameWebView(modifier = Modifier.fillMaxSize())
          }
        }
      }
    }
  }

  override fun onPause() {
    super.onPause()
    webView?.onPause()
  }

  override fun onResume() {
    super.onResume()
    webView?.onResume()
  }

  override fun onDestroy() {
    webView?.destroy()
    webView = null
    super.onDestroy()
  }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun GameWebView(modifier: Modifier = Modifier) {
  AndroidView(
    modifier = modifier.testTag("game_web_view"),
    factory = { context ->
      WebView(context).apply {
        layoutParams = ViewGroup.LayoutParams(
          ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.MATCH_PARENT
        )
        setLayerType(View.LAYER_TYPE_HARDWARE, null)
        setBackgroundColor(android.graphics.Color.parseColor("#121118"))

        settings.apply {
          javaScriptEnabled = true
          domStorageEnabled = true
          loadWithOverviewMode = true
          useWideViewPort = true
          builtInZoomControls = false
          displayZoomControls = false
          setSupportZoom(false)
          cacheMode = WebSettings.LOAD_NO_CACHE
          mediaPlaybackRequiresUserGesture = false
        }

        webViewClient = object : WebViewClient() {}
        webChromeClient = WebChromeClient()

        loadUrl("file:///android_asset/index.html")
      }
    },
    update = { wv ->
      // Keeps webview instance synced
    }
  )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(text = "Hello $name!", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  MyApplicationTheme { Greeting("Android") }
}

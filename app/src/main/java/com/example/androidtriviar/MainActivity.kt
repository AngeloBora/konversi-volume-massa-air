package com.example.androidtriviar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import com.example.androidtriviar.ui.theme.AndroidTriviarTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidTriviarTheme {
                KonversiAir()
            }
        }
    }
}

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun KonversiAir() {

    // Input volume
    var volume by remember {
        mutableStateOf("")
    }

    // Satuan yang dipilih
    var satuan by remember {
        mutableStateOf("Liter (L)")
    }

    // Menampilkan atau menyembunyikan dropdown
    var expanded by remember {
        mutableStateOf(false)
    }

    // Hasil konversi
    var hasil by remember {
        mutableStateOf("")
    }

    // Massa jenis air
    val massaJenisAir = 1000.0

    // Daftar pilihan satuan
    val daftarSatuan = listOf(
        "Liter (L)",
        "Mililiter (mL)",
        "Meter Kubik (m³)"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center
    ) {

        // =========================
        // JUDUL
        // =========================

        Text(
            text = "Konversi Volume Air",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Volume Air → Massa Air",
            fontSize = 16.sp
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        // =========================
        // CARD INPUT
        // =========================

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Masukkan Volume Air",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                // =========================
                // INPUT VOLUME
                // =========================

                OutlinedTextField(
                    value = volume,

                    onValueChange = {
                        volume = it
                    },

                    label = {
                        Text("Volume")
                    },

                    placeholder = {
                        Text("Contoh: 10")
                    },

                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),

                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                // =========================
                // DROPDOWN SATUAN
                // =========================

                ExposedDropdownMenuBox(
                    expanded = expanded,

                    onExpandedChange = {
                        expanded = !expanded
                    },

                    modifier = Modifier.fillMaxWidth()
                ) {

                    OutlinedTextField(
                        value = satuan,

                        onValueChange = {},

                        readOnly = true,

                        label = {
                            Text("Satuan Volume")
                        },

                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded
                            )
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,

                        onDismissRequest = {
                            expanded = false
                        }
                    ) {

                        daftarSatuan.forEach { pilihan ->

                            DropdownMenuItem(

                                text = {
                                    Text(pilihan)
                                },

                                onClick = {

                                    satuan = pilihan

                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                // =========================
                // TOMBOL KONVERSI
                // =========================

                Button(
                    onClick = {

                        val nilaiVolume =
                            volume
                                .replace(",", ".")
                                .toDoubleOrNull()

                        if (nilaiVolume != null && nilaiVolume >= 0) {

                            // Mengubah semua satuan menjadi m³
                            val volumeM3 = when (satuan) {

                                "Liter (L)" ->
                                    nilaiVolume / 1000.0

                                "Mililiter (mL)" ->
                                    nilaiVolume / 1_000_000.0

                                "Meter Kubik (m³)" ->
                                    nilaiVolume

                                else ->
                                    0.0
                            }

                            // Rumus:
                            // Massa = Volume × Massa Jenis
                            val massa =
                                volumeM3 * massaJenisAir

                            hasil = String.format(
                                "%.2f kg",
                                massa
                            )

                        } else {

                            hasil =
                                "Masukkan volume yang benar"
                        }
                    },

                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = "KONVERSI"
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        // =========================
        // HASIL
        // =========================

        Text(
            text = "Hasil Konversi",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text = if (hasil.isEmpty()) {
                "Belum ada hasil"
            } else {
                hasil
            },

            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,

            color = MaterialTheme.colorScheme.primary
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        // =========================
        // INFORMASI
        // =========================

        Text(
            text = "Massa jenis air = 1000 kg/m³",
            fontSize = 14.sp
        )
    }
}
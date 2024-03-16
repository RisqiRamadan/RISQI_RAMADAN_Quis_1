package com.example.risqiramadanquiss1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etNameUser, etKodeBarang, etJumlahBarang;
    private RadioButton rbGold, rbSilver, rbBiasa;
    private Button btnProces;
    private Map<String, Integer> hargaBarangMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNameUser = findViewById(R.id.etNameUser);
        etKodeBarang = findViewById(R.id.etKBarang);
        etJumlahBarang = findViewById(R.id.etJBarang);
        rbGold = findViewById(R.id.rbtnGold);
        rbSilver = findViewById(R.id.rbtnSilver);
        rbBiasa = findViewById(R.id.rbtnBiasa);
        btnProces = findViewById(R.id.btnProses);

        ImageView ivAsus = findViewById(R.id.ivbackasus);
        ImageView ivAcer = findViewById(R.id.ivbackacer);
        ImageView ivMacpro = findViewById(R.id.ivbackmacpro);
        Button btnAsus = findViewById(R.id.btnbackasus);
        Button btnAcer = findViewById(R.id.btnbackacer);
        Button btnMacpro = findViewById(R.id.btnbackmacpro);

        ivAsus.setOnClickListener(this);
        ivAcer.setOnClickListener(this);
        ivMacpro.setOnClickListener(this);
        btnAsus.setOnClickListener(this);
        btnAcer.setOnClickListener(this);
        btnMacpro.setOnClickListener(this);

        // Inisialisasi harga barang
        hargaBarangMap.put("AV4", 9150999);
        hargaBarangMap.put("AA5", 9999999);
        hargaBarangMap.put("MP3", 28999999);

        rbGold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbGold.isChecked()) {
                    rbSilver.setChecked(false);
                    rbBiasa.setChecked(false);
                }
            }
        });

        rbSilver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbSilver.isChecked()) {
                    rbGold.setChecked(false);
                    rbBiasa.setChecked(false);
                }
            }
        });
        rbBiasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbBiasa.isChecked()) {
                    rbGold.setChecked(false);
                    rbSilver.setChecked(false);
                }
            }
        });

        btnProces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prosesTransaksi();
            }
        });
    }

    private void prosesTransaksi() {
        String nama = etNameUser.getText().toString();
        int jumlahBarang;
        try {
            jumlahBarang = Integer.parseInt(etJumlahBarang.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Masukkan jumlah barang yang valid", Toast.LENGTH_SHORT).show();
            return;
        }

        String jenisMembership = "";
        if (rbGold.isChecked()) {
            jenisMembership = "Gold";
        } else if (rbSilver.isChecked()) {
            jenisMembership = "Silver";
        } else if (rbBiasa.isChecked()) {
            jenisMembership = "Biasa";
        }
        String kodeBarang = etKodeBarang.getText().toString().toUpperCase(); // Mengubah menjadi huruf kapital
        int hargaBarang = 0;

        // Validasi kode barang dan ambil harga sesuai kode barang
        if (hargaBarangMap.containsKey(kodeBarang)) {
            hargaBarang = hargaBarangMap.get(kodeBarang);
        } else {
            Toast.makeText(this, "Kode barang tidak valid", Toast.LENGTH_SHORT).show();
            return;
        }

        int totalHarga = jumlahBarang * hargaBarang;

        // Berikan diskon harga sebesar 100 ribu jika total harga melebihi 10 juta
        if (totalHarga > 10000000) {
            totalHarga -= 100000;
        } // Format harga ke format mata uang Rupiah
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        String hargaFormatted = formatRupiah.format(hargaBarang);

        Intent intent = new Intent(this, ProsesPembelian.class);
        intent.putExtra("nama", nama);
        intent.putExtra("jumlahBarang", jumlahBarang);
        intent.putExtra("jenisMembership", jenisMembership);
        intent.putExtra("kodeBarang", kodeBarang);
        intent.putExtra("hargaBarang", hargaFormatted);
        intent.putExtra("totalHarga", totalHarga);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnbackasus || v.getId() == R.id.ivbackasus) {
            Intent intentBaju = new Intent(this, AsusActivity.class);
            startActivity(intentBaju);
        }
        if (v.getId() == R.id.btnbackacer || v.getId() == R.id.ivbackacer) {
            Intent intentKacamata = new Intent(this, AcerActivity.class);
            startActivity(intentKacamata);
        }
        if (v.getId() == R.id.btnbackmacpro || v.getId() == R.id.ivbackmacpro) {
            Intent intentSepatu = new Intent(this, MacproActivity.class);
            startActivity(intentSepatu);
        }
    }
}







package com.example.galeriadavid;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView gato, elefante, leon, perro, opcional, elegida, agrandar;
    RadioButton camara, celular;
    Button accionar;
    public final static int cons=0;
    public Uri uri;
    Bitmap bitmap, bitmapimagen;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gato = (ImageView) findViewById(R.id.imageview1);
        elefante = (ImageView) findViewById(R.id.imageview2);
        leon = (ImageView) findViewById(R.id.imageview3);
        perro = (ImageView) findViewById(R.id.imageview4);
        opcional = (ImageView) findViewById(R.id.imageviewopcional);
        elegida = (ImageView) findViewById(R.id.elegida);
        agrandar = (ImageView) findViewById(R.id.agrandar);
        camara = (RadioButton) findViewById(R.id.camara);
        celular = (RadioButton) findViewById(R.id.celular);
        accionar = (Button) findViewById(R.id.accionar);

        //se programa un click local
        gato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agrandar.setScaleType(ImageView.ScaleType.CENTER_CROP);
                agrandar.setImageResource(R.drawable.gato);
            }
        });
        //se programa un click global
        elefante.setOnClickListener(this);
        leon.setOnClickListener(this);
        perro.setOnClickListener(this);
        opcional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agrandar.setScaleType(ImageView.ScaleType.CENTER_CROP);
                agrandar.setImageResource(R.drawable.lobo);
            }
        });
        agrandar.setOnClickListener(this);
        elegida.setOnClickListener(this);

        camara.setOnClickListener(this);
        celular.setOnClickListener(this);
        accionar.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //coidgo por si eligio la imagen de la galeria del telefono
        if(requestCode==cons){
            uri=data.getData();
            try {
                bitmap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                elegida.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //codigo para la camara del telefono
        if(resultCode==RESULT_OK && requestCode==REQUEST_IMAGE_CAPTURE){
            Bundle b=data.getExtras();
            bitmapimagen=(Bitmap) b.get("data");
            elegida.setImageBitmap(bitmapimagen);
        }
    }

    @Override
    public void onClick(View view) {



        switch (view.getId())
        {
            case  R.id.imageview2:
                agrandar.setScaleType(ImageView.ScaleType.CENTER_CROP);
                agrandar.setImageResource(R.drawable.elefante);
                break;

            case  R.id.imageview3:
                agrandar.setScaleType(ImageView.ScaleType.CENTER_CROP);
                agrandar.setImageResource(R.drawable.leon);
                break;

            case R.id.imageview4:
                agrandar.setScaleType(ImageView.ScaleType.CENTER_CROP);
                agrandar.setImageResource(R.drawable.perro);
                break;

            case R.id.elegida:
                agrandar.setScaleType(ImageView.ScaleType.CENTER_CROP);
                if (camara.isChecked()){
                agrandar.setImageBitmap(bitmapimagen);
            }
                if (celular.isChecked()){
                    agrandar.setImageBitmap(bitmap);
                }
                break;

            case R.id.agrandar:
                break;

            case R.id.camara:
                break;

            case R.id.celular:
                break;

            case R.id.accionar:
                if(camara.isChecked())
                {
                    Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (i.resolveActivity(getPackageManager()) !=null)
                    {
                        startActivityForResult(i, REQUEST_IMAGE_CAPTURE);

                    }

                }

                if(celular.isChecked()){
                    Intent i=new Intent (Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    i.setType("image/*");
                    startActivityForResult(i.createChooser(i,""),cons);
                }
                break;



        }

    }
}
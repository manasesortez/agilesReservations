package com.amtodev.hospitalReservations.admin.Specialty;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amtodev.hospitalReservations.Class.ConexionSQLite;
import com.amtodev.hospitalReservations.Class.Configurations;
import com.amtodev.hospitalReservations.R;
import com.amtodev.hospitalReservations.admin.Admin;
import com.amtodev.hospitalReservations.admin.Doctor.AdminDoctor;
import com.amtodev.hospitalReservations.admin.Hospital.UpdateHospital;
import com.amtodev.hospitalReservations.admin.Hospital.ViewHospital;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ViewSpecialty extends AppCompatActivity {

    ConexionSQLite objConexion;
    final String NOMBRE_BASE_DATOS = "agilesReservas";
    ListView lv_Scepcialty;
    EditText CriterioSpecialty;
    Button SearchSpecialty, ExportSpecialty;
    Configurations objConfiguracion;
    ArrayList<String> lista;
    ArrayAdapter adaptador;
    List<Integer> arregloID = new ArrayList<Integer>();

    //permisos
    private String DIRECTORIO_PDFS = Environment.getExternalStorageDirectory().getAbsolutePath()+"/MIAPPPDFS";
    private boolean tienePermisoAlmacenamiento = false;
    private static final int CODIGO_PERMISOS_ALMACENAMIENTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_specialty);
        //Verificar Permisos
        verificarYPermisosDeAlmacenamiento();
        //Conexion a SQLITE
        objConfiguracion = new Configurations();
        objConexion = new ConexionSQLite(ViewSpecialty.this, objConfiguracion.BD, null, 1);

        SearchSpecialty = (Button) findViewById(R.id.btnSearchSpecialty);
        ExportSpecialty = (Button) findViewById(R.id.btnExportSpecialty);
        CriterioSpecialty = (EditText) findViewById(R.id.txtCriterioSpecialty);
        lv_Scepcialty = (ListView) findViewById(R.id.lvSpecialty);


        ExportSpecialty.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("ObsoleteSdkInt")
            @Override
            public void onClick(View view) {
                try{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        exportarPDF();
                    }
                }catch(FileNotFoundException e){
                    e.printStackTrace();
                }
            }
        });

        lv_Scepcialty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idSeleccionado = arregloID.get(position);
                Intent ventanaModificar = new Intent(ViewSpecialty.this, UpdateSpeciality.class);
                ventanaModificar.putExtra("especialidad_id", idSeleccionado);
                startActivity(ventanaModificar);
            }
        });

        SearchSpecialty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar();
            }
        });

    }


    private void buscar() {
        lista = llenarLista();
        adaptador = new ArrayAdapter(ViewSpecialty.this, R.layout.list_black_text, lista);
        lv_Scepcialty.setAdapter(adaptador);
    }


    @Override
    protected void onResume() {
        super.onResume();
        buscar();
    }


    public void openAdminViewSpecialty(View view) {
        startActivity(new Intent(this, AdminSpecialty.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
        finish();
    }

    @SuppressLint("Recycle")
    public ArrayList llenarLista(){
        ArrayList<String> miLista = new ArrayList<String>();
        Cursor Select;
        try{
            SQLiteDatabase base = objConexion.getReadableDatabase();
            String consulta = "SELECT especialidad_id,especialidad_nombre, hospital_nombre FROM especialidades, hospitales WHERE especialidades.hospital_id = hospitales.hospital_id  ORDER BY especialidad_nombre ASC";
            @SuppressLint("Recycle") Cursor cadaRegistro = base.rawQuery(consulta, null);
            arregloID.clear();
            if(cadaRegistro.moveToFirst()){
                do{
                    miLista.add(cadaRegistro.getString(1).toString()+ "\n"+cadaRegistro.getString(2).toString());
                    arregloID.add(cadaRegistro.getInt(0));
                }while(cadaRegistro.moveToNext());
            }
            base.close();
        }catch(Exception error){
            Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
        }
        return miLista;
    }


    private void verificarYPermisosDeAlmacenamiento(){
        int estadoPermiso = ContextCompat.checkSelfPermission(ViewSpecialty.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(estadoPermiso == PackageManager.PERMISSION_GRANTED){
            permisosDeAlmacenamientoConcedido();
        }else{
            //si no, entonces pedimos permisos, ahora mira onRequestPermissionResult
            ActivityCompat.requestPermissions(ViewSpecialty.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODIGO_PERMISOS_ALMACENAMIENTO);
        }
    }

    private void permisosDeAlmacenamientoConcedido(){
        File carpeta = new File(DIRECTORIO_PDFS);
        if (!carpeta.exists()){
            carpeta.mkdir();
        }else{
            Log.i("Speacialty", "Filed Created Successfully");
        }
        tienePermisoAlmacenamiento = true;
    }

    //Solicitud de Permisos
    private void permisosDeAlmacenamientoDenegado(){
        Toast.makeText(ViewSpecialty.this, "Permission for storage is denied", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CODIGO_PERMISOS_ALMACENAMIENTO:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    permisosDeAlmacenamientoConcedido();
                }else{
                    permisosDeAlmacenamientoDenegado();
                }
                break;
        }
    }

    public void abrirPDF(File archivo, Context contexto){
        try {
            String s = String .valueOf(archivo);
            File arch = new File(s);
            if (arch.exists()){
                Uri uri = FileProvider.getUriForFile(contexto, contexto.getApplicationContext().getPackageName() + ".provider", arch);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                try{
                    startActivity(intent);
                }catch(ActivityNotFoundException e){
                    Toast.makeText(contexto, "Error: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }catch(Exception error){
            Toast.makeText(contexto, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void exportarPDF() throws FileNotFoundException {
        try {

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String nombreArchivo = "Specialty"+dtf.format(LocalDateTime.now())+".pdf";
            File archivo = new File(DIRECTORIO_PDFS, nombreArchivo);

            PdfWriter pdfEscrito = new PdfWriter(archivo);
            PdfDocument pdfDocumento = new PdfDocument(pdfEscrito);
            Document documento = new Document(pdfDocumento);
            pdfDocumento.setDefaultPageSize(PageSize.A4);

            //encabezado
            @SuppressLint("UseCompatLoadingForDrawables")
            Drawable logo = getDrawable(R.drawable.agile);
            Bitmap lienzo = ((BitmapDrawable)logo).getBitmap();
            ByteArrayOutputStream streamArchivo = new ByteArrayOutputStream();
            lienzo.compress(Bitmap.CompressFormat.PNG, 100, streamArchivo);
            byte[] datosLienzo = streamArchivo.toByteArray();

            ImageData imgDatos = ImageDataFactory.create(datosLienzo);
            Image elLogo = new Image(imgDatos);
            elLogo.setHeight(50);
            elLogo.setHorizontalAlignment(HorizontalAlignment.CENTER);
            documento.add(elLogo);
            documento.add(new Paragraph(""));

            float anchoColumna[] = {40f, 1750f, 1750f};
            Table table = new Table(anchoColumna);

            Cell encabezadoTabla1 = new Cell();
            encabezadoTabla1.setBackgroundColor(ColorConstants.BLACK);
            encabezadoTabla1.setFontColor(ColorConstants.WHITE);
            encabezadoTabla1.add(new Paragraph("#"));
            table.addCell(encabezadoTabla1);

            Cell encabezadoTabla2 = new Cell();
            encabezadoTabla2.setBackgroundColor(ColorConstants.BLACK);
            encabezadoTabla2.setFontColor(ColorConstants.WHITE);
            encabezadoTabla2.add(new Paragraph("ESPECIALIDAD"));
            table.addCell(encabezadoTabla2);

            Cell encabezadoTabla3 = new Cell();
            encabezadoTabla3.setBackgroundColor(ColorConstants.BLACK);
            encabezadoTabla3.setFontColor(ColorConstants.WHITE);
            encabezadoTabla3.add(new Paragraph("HOSPITAL"));
            table.addCell(encabezadoTabla3);


            //conectarse en modo lectura
            SQLiteDatabase miDB = objConexion.getReadableDatabase();
            String comando = "SELECT especialidad_id,especialidad_nombre, hospital_nombre FROM especialidades, hospitales WHERE especialidades.hospital_id = hospitales.hospital_id  ORDER BY especialidad_nombre ASC";
            @SuppressLint("Recycle") Cursor cadaFila = miDB.rawQuery(comando, null);

            if(cadaFila.moveToFirst()){
                do {
                    Cell celda1 = new Cell();
                    celda1.setBackgroundColor(ColorConstants.WHITE);
                    celda1.setFontColor(ColorConstants.BLACK);
                    celda1.add(new Paragraph(cadaFila.getString(0).toString()));
                    table.addCell(celda1);

                    Cell celda2 = new Cell();
                    celda2.setBackgroundColor(ColorConstants.WHITE);
                    celda2.setFontColor(ColorConstants.BLACK);
                    celda2.add(new Paragraph(cadaFila.getString(1).toString()));
                    table.addCell(celda2);

                    Cell celda3 = new Cell();
                    celda3.setBackgroundColor(ColorConstants.WHITE);
                    celda3.setFontColor(ColorConstants.BLACK);
                    celda3.add(new Paragraph(cadaFila.getString(2).toString()));
                    table.addCell(celda3);

                }while(cadaFila.moveToNext());
            }

            //cerrar la base de datos
            miDB.close();
            documento.add(table);
            documento.close();
            abrirPDF(archivo, ViewSpecialty.this);


        }catch(Exception e){
            Toast.makeText(ViewSpecialty.this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        ViewSpecialty.this.finish();
    }
}
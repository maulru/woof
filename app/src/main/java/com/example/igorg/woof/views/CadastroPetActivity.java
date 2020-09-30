package com.example.igorg.woof.views;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.masks.Mask;
import com.example.igorg.woof.modelo.Servidor;
import com.example.igorg.woof.modelo.Usuario;
import com.example.igorg.woof.voids.SharedPrefManager;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static com.example.igorg.woof.modelo.Servidor.usuario_rede;

public class CadastroPetActivity extends AppCompatActivity {

    private ImageView imageview;
    private String caminhoFoto,sexo;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALERIA = 1, CAMERA = 2;
    private Uri mImageUri;
    private EditText petNome,petNascimento,petObservacoes;
    private AlertDialog alerta;
    private ImageButton buttonCamera;
    private String server;
    private RadioGroup petSexo,petCastracao;
    private Spinner petTipo,petRaca;
    private ImageView petFoto;
    private Button Cadastrar;

    ProgressDialog progressDialog;

    Usuario usuario;

    public CadastroPetActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pet);

        //definindo ip do servidor
        server = (String) Servidor.getIp();

       // Toolbar toolbar_proxima = (Toolbar) findViewById(R.id.criarpet_toolbar);
      //  setSupportActionBar(toolbar_proxima);
       // getSupportActionBar().setDisplayShowTitleEnabled(false);

        usuario= SharedPrefManager.getInstance(this).getUser();

        //Máscara para formatar data
        final EditText campo_data_nascimento = (EditText) findViewById(R.id.editCadastroPetNascimento);
        campo_data_nascimento.addTextChangedListener(Mask.insert("##/##/####", campo_data_nascimento));

        petNome = (EditText) findViewById(R.id.editCadastroPetNome);
        petSexo = (RadioGroup) findViewById(R.id.radioGroupSexoPet);
        petNascimento = (EditText) findViewById(R.id.editCadastroPetNascimento);
        petObservacoes = (EditText) findViewById(R.id.editCadastroPetObservacoes);
        petTipo = (Spinner) findViewById(R.id.spinnerTipoAnimal);
        petRaca = (Spinner) findViewById(R.id.spinnerRaca);
     //   petFoto = (ImageView) findViewById(R.id.imageViewCadastroPetFoto);

        Cadastrar = (Button) findViewById(R.id.butt_cadastrar_pet);

        Spinner tipo = (Spinner) findViewById(R.id.spinnerTipoAnimal);
        final Spinner raca = (Spinner) findViewById(R.id.spinnerRaca);

        List<String> tipos = new ArrayList<>(Arrays.asList("Cachorro","Gato"));
        final List<String> cachorros = new ArrayList<>(Arrays.asList("Affenpinscher",
                "Afghan Hound",
                "Airedale Terrier",
                "Akita",
                "Akita Americano",
                "American Pit Bull Terrier",
                "American Staffordshire Terrier",
                "Australian Shepherd",
                "Basenji",
                "Basset Fulvo da Bretanha",
                "Basset Hound",
                "Beagle",
                "Beagle-Harrier",
                "Bearded Collie",
                "Bedlington Terrier",
                "Bernese Mountain Dog",
                "Bichon Bolonhês",
                "Bichon Frisé",
                "Bichon Havanês",
                "Boerboel",
                "Boiadeiro de Entlebuch",
                "Border Collie",
                "Border Terrier",
                "Borzoi",
                "Boston Terrier",
                "Bouvier de Flandres",
                "Boxer",
                "Braco Alemão Pelo Curto",
                "Braco Alemão Pelo Duro",
                "Braco Italiano",
                "Buldogue Americano",
                "Buldogue Francês",
                "Buldogue Inglês",
                "Bull Terrier",
                "Bullmastiff",
                "Cairn Terrier",
                "Cane Corso",
                "Cão de Crista Chinês",
                "Cão de Santo Humberto",
                "Cão D’água Espanhol",
                "Cão D’água Português",
                "Cão Lobo Checoslovaco",
                "Cão Pelado Mexicano",
                "Cão Pelado Peruano",
                "Cavalier King Charles Spaniel",
                "Cesky Terrier",
                "Chesapeake Bay Retriever",
                "Chihuahua",
                "Chin",
                "Chow-chow Pelo Curto",
                "Chow-chow Pelo Longo",
                "Cirneco do Etna",
                "Clumber Spaniel",
                "Cocker Spaniel Americano",
                "Cocker Spaniel Inglês",
                "Collie pelo longo",
                "Coton de Tulear",
                "Dachshund Teckel - Pelo Curto",
                "Dálmata",
                "Dandie Dinmont Terrier",
                "Dobermann",
                "Dogo Argentino",
                "Dogo Canário",
                "Dogue Alemão",
                "Dogue de Bordeaux",
                "Elkhound Norueguês Cinza",
                "Fila Brasileiro",
                "Flat-Coated Retriever",
                "Fox Terrier Pelo Duro ",
                "Fox Terrier Pelo Liso",
                "Foxhound Inglês",
                "Galgo Espanhol",
                "Golden Retriever",
                "Grande Münsterländer",
                "Greyhound",
                "Griffon Belga",
                "Griffon de Bruxelas",
                "Husky Siberiano",
                "Ibizan Hound",
                "Irish Soft Coated Wheaten Terrier",
                "Irish Wolfhound",
                "Jack Russell Terrier",
                "Kerry Blue Terrier",
                "Komondor",
                "Kuvasz",
                "Labrador Retriever",
                "Lagotto Romagnolo",
                "Lakeland Terrier",
                "Leonberger",
                "Lhasa Apso",
                "Malamute do Alasca",
                "Maltês",
                "Mastiff",
                "Mastim Espanhol",
                "Mastino Napoletano",
                "Mudi",
                "Nordic Spitz",
                "Norfolk Terrier",
                "Norwich Terrier",
                "Old English Sheepdog",
                "Papillon",
                "Parson Russell Terrier",
                "Pastor Alemão",
                "Pastor Beauceron",
                "Pastor Belga",
                "Pastor Bergamasco",
                "Pastor Branco Suíço",
                "Pastor Briard",
                "Pastor da Ásia Central",
                "Pastor de Shetland",
                "Pastor dos Pirineus",
                "Pastor Maremano Abruzês",
                "Pastor Polonês",
                "Pastor Polonês da Planície",
                "Pequeno Basset Griffon da Vendéia",
                "Pequeno Cão Leão",
                "Pequeno Lebrel Italiano",
                "Pequinês",
                "Perdigueiro Português",
                "Petit Brabançon",
                "Pharaoh Hound",
                "Pinscher Miniatura",
                "Podengo Canário",
                "Podengo Português",
                "Pointer Inglês",
                "Poodle Anão",
                "Poodle Médio",
                "Poodle Standard",
                "Poodle Toy",
                "Pug",
                "Puli",
                "Pumi",
                "Rhodesian Ridgeback",
                "Rottweiler",
                "Saluki",
                "Samoieda",
                "São Bernardo",
                "Schipperke",
                "Schnauzer",
                "Schnauzer Gigante",
                "Schnauzer Miniatura",
                "Scottish Terrier",
                "Sealyham Terrier",
                "Setter Gordon",
                "Setter Inglês",
                "Setter Irlandês Vermelho",
                "Setter Irlandês Vermelho e Branco",
                "Shar-pei",
                "Shiba",
                "Shih-Tzu",
                "Silky Terrier Australiano",
                "Skye Terrier",
                "Smoushond Holandês",
                "Spaniel Bretão",
                "Spinone Italiano",
                "Spitz Alemão Anão",
                "Spitz Finlandês",
                "Spitz Japonês Anão",
                "Springer Spaniel Inglês",
                "Stabyhoun",
                "Staffordshire Bull Terrier",
                "Terra Nova",
                "Terrier Alemão de caça Jagd",
                "Terrier Brasileiro",
                "Terrier Irlandês de Glen do Imaal",
                "Terrier Preto da Rússia",
                "Tibetan Terrier",
                "Tosa Inu",
                "Vira-Latas",
                "Vizsla",
                "Volpino Italiano",
                "Weimaraner",
                "Welsh Corgi Cardigan",
                "Welsh Corgi Pembroke",
                "Welsh Springer Spaniel",
                "Welsh Terrier",
                "West Highland White Terrier",
                "Whippet",
                "Yorkshire Terrier"
        ));
        final List<String> gatos = new ArrayList<>(Arrays.asList(
                "Abyssinian",
                "American Bobtail Longhair",
                "American Bobtail Shorthair",
                "American Shorthair",
                "American Wirehair",
                "Arabian Mau",
                "Asian Semi-long Hair",
                "Australian Mist",
                "Bengal",
                "Bobtail Japonês",
                "Bombaim",
                "Brazilian Shorthair",
                "British Longhair",
                "Burmês",
                "California Spangled Cat",
                "Chausie",
                "Cornish Rex",
                "Curl Americano Pelo Curto",
                "Curl Americano Pelo Longo",
                "Cymric",
                "Devon Rex",
                "Domestic Long-Haired",
                "Domestic Short-Haired",
                "Don Sphynx",
                "Egyptian Mau",
                "European",
                "Exotic Shorthair",
                "German Rex",
                "Havana",
                "Himalaio",
                "Khao Manee",
                "Korat",
                "Kurilian Bobtail Longhair",
                "Kurilian Bobtail Shorthair",
                "LaPerm Longhair",
                "LaPerm Shorthair",
                "Maine Coon",
                "Manx",
                "Mekong Bobtail",
                "Munchkin Longhair",
                "Munchkin Shorthair",
                "Nebelung",
                "Norwegian Forest Cat",
                "Ocicat",
                "Ojos Azules Shorthair",
                "Oriental Longhair",
                "Oriental Shorthair",
                "Outras",
                "Persa",
                "Peterbald",
                "Pixiebob Longhair",
                "Pixiebob Shorthair",
                "Ragamuffin",
                "Ragdoll",
                "Russo Azul",
                "Sagrado da Birmânia",
                "Savannah Cat",
                "Scottish Fold",
                "Selkirk Rex Longhair",
                "Selkirk Rex Shorthair",
                "Serengeti",
                "Siamês",
                "Siberian",
                "Singapura",
                "Snowshoe",
                "Sokoke",
                "Somali",
                "Sphynx",
                "Thai",
                "Tonkinese Shorthair",
                "Toyger",
                "Turkish Angorá",
                "Turkish Van",
                "York Chocolate"

        ));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, tipos );
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tipo.setAdapter(dataAdapter);


        //Raças de acordo com o tipo

        tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch(position){
                    case 0:
                        ArrayAdapter<String> cachorroAdapter = new ArrayAdapter<String>(CadastroPetActivity.this,
                                android.R.layout.simple_spinner_item, cachorros );
                        cachorroAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        raca.setAdapter(cachorroAdapter);
                        break;

                    case 1:
                        ArrayAdapter<String> gatoAdapter = new ArrayAdapter<String>(CadastroPetActivity.this,
                                android.R.layout.simple_spinner_item, gatos );
                        gatoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        raca.setAdapter(gatoAdapter);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        Cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View contextView = findViewById(R.id.butt_cadastrar_pet);

                if(petNome.getText().length() >=3 ){
                    if(petNascimento.getText().length() >=8 ){
                        if(petObservacoes.getText().length() >1 ){

                            criarPet();

                        }else{
                            Snackbar.make(contextView,"Você deve digitar uma observação!",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                            petObservacoes.requestFocus();

                        }
                    }else{
                        Snackbar.make(contextView,"Você deve digitar uma data válida!",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                        petNascimento.requestFocus();

                    }
                }else{
                    Snackbar.make(contextView,"Você deve digitar um nome válido para o seu pet!",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                    petNome.requestFocus();
                }
            }
        });


    } //Fim do método OnCreate da classe.



    //Início das funções para escolha, captura e upload de imagem

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Selecione uma foto");
        String[] pictureDialogItems = {
                "Escolher na galeria",
                "Usar a câmera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                escolhe();
                                break;
                            case 1:
                                tirafoto();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void escolhe() {
        Intent galeriaIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galeriaIntent, GALERIA);
    }

    private void tirafoto() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALERIA) {
            if (data != null) {
                mImageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageUri);
                    String path = saveImage(bitmap);
                    Toast.makeText(CadastroPetActivity.this, "Imagem salva!", Toast.LENGTH_SHORT).show();
                    imageview.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(CadastroPetActivity.this, "Erro!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageview.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(CadastroPetActivity.this, "Imagem salva!", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);


        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadPhoto(){

    }

    private void  requestMultiplePermissions(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "todas permissões aceitas!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                    }


                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Erro! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    //Fim das funções para escolha, captura e upload de imagem


    private void criarPet() {
        progressDialog = ProgressDialog.show(CadastroPetActivity.this, "Enviando dados", "Por favor aguarde", false, false);



        switch (petSexo.getCheckedRadioButtonId()){
            case R.id.radioPetMacho:
                sexo = "Macho";
                break;
            case R.id.radioPetFemea:
                sexo = "Fêmea";
                break;

        }


        //Início do código para inserir pet

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+server+"/woof/registrar_animal.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("Response_pet", response);

                progressDialog.dismiss();


                Toast.makeText(getApplicationContext(),"Pet cadastrado com sucesso!" +response, Toast.LENGTH_SHORT).show();

                finish();

            }

        }, new Response.ErrorListener() {
            @Override            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"erro: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })

        {
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("animal_nome",petNome.getText().toString());
                params.put("animal_tipo",petTipo.getSelectedItem().toString());
                params.put("animal_raca",petRaca.getSelectedItem().toString());
                params.put("animal_sexo",sexo);
                params.put("animal_nascimento", petNascimento.getText().toString());
                params.put("animal_observacoes", petObservacoes.getText().toString());
                params.put("animal_id_dono", usuario.getId());
                params.put("usuario_rede",String.valueOf(usuario_rede));

                return params;
            }
        };


        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

        //Fim do código para inserir pet





        //Inserir outro pet?

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Woof");
        //define a mensagem
        builder.setMessage("Você deseja adicionar outro pet?");
        //define um botão como positivo
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //  Toast.makeText(CadastroPet.this, "sim=" + arg1, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CadastroPetActivity.this, CadastroPetActivity.class);
                startActivity(intent);
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                /// Toast.makeText(CadastroPet.this, "nao=" + arg1, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CadastroPetActivity.this, HomeUserActivity.class);
                startActivity(intent);
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe



    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario2, menu);

        return super.onCreateOptionsMenu(menu);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_formulario_okk:
                View contextView = findViewById(R.id.menu_formulario_okk);


                    criarPet();



                break;
        }
        return super.onOptionsItemSelected(item);
    }

*/

}

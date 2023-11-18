package com.example.collagereminder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Random;

public class Estres extends AppCompatActivity {

    //Agregar todos los consejos deseados

    String[] motivacion = {
            "El único modo de hacer un gran trabajo es amar lo que haces.",

            "No importa lo lento que vayas, siempre y cuando no te detengas.",

            "El único límite para nuestros logros de mañana serán nuestras dudas de hoy.",

            "La vida es 10% lo que nos sucede y 90% cómo reaccionamos ante ello.",

            "No busques el éxito. Busca la realización personal. Haz lo que amas y cree en lo que haces. El éxito vendrá naturalmente."

    };

    String[] cargaAcademica = {
            "Cada asignatura difícil es una oportunidad para aprender y crecer intelectualmente.",

            "La presión académica es una oportunidad para demostrar tu resistencia y compromiso con tu educación.",

            "Afrontar la carga académica con valentía y resiliencia te prepara para los éxitos futuros.",


            "Cada día es una oportunidad nueva para aprender y mejorar en tus estudios.",

            "Confío en tu capacidad para superar los desafíos académicos con determinación y perseverancia."
    };

    String[] problemasPersonales = {
            "La paz interior comienza cuando dejas de culparte y empiezas a concentrarte en soluciones constructivas.",

            "Tus problemas personales no definen tu valía; estás en control de cómo eliges responder y superarlos.",

            "La empatía hacia ti mismo/a es esencial; mereces el mismo amor y comprensión que brindas a los demás.",

            "La autorreflexión y la paciencia son tus aliadas mientras navegas a través de los desafíos personales.",

            "Tu resiliencia es tu superpoder; puedes adaptarte y encontrar fuerza incluso en los momentos más difíciles."
    };

    String[] problemasFamiliares = {
            "Enfrentar problemas familiares es difícil, pero eres lo suficientemente fuerte para superar cualquier desafío que se presente.",

            "Cada familia enfrenta desafíos; lo importante es cómo eliges manejarlos. Confío en tu capacidad para abordar estos problemas con sabiduría.",

            "Aprender y crecer a través de los problemas familiares es un proceso. Permítete el tiempo y el espacio necesario para sanar.",

            "La importancia de tu familia trasciende cualquier obstáculo. Juntos, pueden superar cualquier problema que se interponga en su camino.",

            "Establecer límites saludables es una forma valiente de proteger tu bienestar emocional en medio de problemas familiares.",

            "A pesar de los problemas, su familia es un refugio seguro donde el amor siempre prevalece."
    };

    String[] relacionesInterpersonales = {
            "Aprende a soltar lo que no puedes controlar. No dejes que las interacciones negativas afecten tu paz interior.",

            "Elige rodearte de personas que te apoyen y te inspiren. Las relaciones que nutren tu vida son las que deben ocupar un lugar central.",

            "Recuerda que eres digno/a de amor y respeto. No dudes en alejarte de relaciones tóxicas y priorizar tu bienestar emocional.",

            "Tu felicidad no depende de la aprobación de los demás. Vive auténticamente y sigue siendo fiel a ti mismo/a.",

            "No te tomes las acciones de los demás de manera personal. Cada persona tiene su propio viaje y sus propias luchas.",

            "No subestimes el impacto positivo que puedes tener en las relaciones. Tu actitud y bondad pueden ser poderosas fuerzas para el cambio."
    };
    String[] estres = {
            "Prioriza el descanso y el sueño adecuado. Un buen descanso es esencial para mantener la concentración y la resistencia frente al estrés.",
            "Divide tareas grandes en tareas más pequeñas y abordables. Esto hace que las responsabilidades parezcan más manejables y reduce la sensación de abrumo.",
            "Celebra tus logros, incluso los pequeños. Reconocer tus éxitos puede tener un impacto positivo en tu motivación y autoestima.",
            "Establecer prioridades te ayuda a dirigir tu energía hacia lo más importante y a evitar la sensación de estar constantemente detrás de tus responsabilidades.",

            "Reconoce tus límites y aprende a decir no cuando sea necesario. No te sobrecargues con demasiadas responsabilidades."
    };

    String[] transtornoAlimenticio ={
            "El horario universitario puede ser agitado, pero es importante planificar comidas y snacks saludables. Intenta llevar contigo opciones nutritivas para evitar situaciones de hambre extrema que puedan desencadenar comportamientos alimenticios no saludables.",

            "Participa en actividades que te brinden alegría y reduzcan el estrés. La actividad física, el arte, la música o cualquier pasatiempo que disfrutes pueden ser parte de tu estrategia para mejorar la salud mental y emocional.",

            "Aprovecha los recursos de salud mental disponibles en tu campus universitario. Muchas universidades ofrecen servicios de asesoramiento y apoyo para estudiantes que enfrentan trastornos alimenticios. Infórmate sobre estos recursos y no dudes en buscar ayuda.",

            "Prioriza el tiempo para el cuidado personal y la relajación. La vida universitaria puede ser demandante, pero es fundamental reservar momentos para el autocuidado. Esto puede incluir actividades relajantes, como leer, meditar o dar un paseo.",

            "En un entorno universitario, es fácil caer en la trampa de compararse con los demás en términos de apariencia física, rendimiento académico o estilos de vida. Recuerda que cada persona tiene su propio camino y desafíos únicos. Concéntrate en tu propio bienestar y crecimiento."
    };

    String[] ansiedad = {
            "Programa momentos para desconectar de dispositivos digitales. Las redes sociales y constantes notificaciones pueden contribuir al estrés y la ansiedad. Establece períodos durante el día para desconectarte y enfocarte en actividades que te relajen.",

            "Incorpora técnicas de respiración profunda y relajación en tu rutina diaria. La respiración abdominal lenta puede calmar el sistema nervioso y reducir la ansiedad. Dedica unos minutos cada día a prácticas como la meditación o el yoga.",

            "Habla abiertamente con amigos, familiares o compañeros de clase acerca de tus preocupaciones. La comunicación puede aliviar la carga emocional y proporcionarte perspectivas valiosas. Sentirte respaldado/a puede ser reconfortante.",

            "Escribe sin juzgarte ni censurarte. Deja que tus pensamientos fluyan libremente, incluso si al principio son confusos o contradictorios. La meta es liberar lo que llevas dentro.",

            "No esperes a una ocasión especial para arreglarte bonito/a. Puedes hacerlo cualquier día. Darle importancia a tu apariencia incluso en situaciones cotidianas puede cambiar tu perspectiva y mejorar tu estado de ánimo."
    };

    String[] soledad = {
            "Aprovecha los 'eventos' 'y' actividades organizados por la universidad. Conferencias, talleres, actividades deportivas y sociales son excelentes lugares para conocer gente nueva. Participar en estos eventos te permite ampliar tu círculo social y sentirte más conectado/a con la comunidad universitaria.",

            "Utiliza las redes sociales y las plataformas en línea para establecer conexiones. Muchas universidades tienen grupos en línea, foros estudiantiles o incluso aplicaciones específicas para conectarse con otros estudiantes. Esto puede ser especialmente útil si te resulta difícil conocer gente en persona.",

            "Sé proactivo/a en la interacción con tus compañeros de clase. Puedes iniciar conversaciones antes o después de las clases, formar grupos de estudio o participar en discusiones en línea relacionadas con tus cursos. La familiaridad en el entorno académico puede allanar el camino para desarrollar amistades.",

            "Pasa tiempo en espacios comunes como bibliotecas, salas de estudio o cafeterías. Estos lugares suelen ser puntos de encuentro donde es más probable que encuentres a otros estudiantes. No dudes en entablar conversaciones informales en estos entornos.",

            "Aprende a depender de ti mismo/a y a disfrutar de tu propia compañía. Esto implica tomar decisiones independientes, resolver problemas por tu cuenta y desarrollar un sentido de autonomía. La capacidad de estar solo/a puede fortalecer tu resiliencia y confianza."
    };

    String[] amorPropio = {
            "Enfrenta los fracasos como oportunidades de aprendizaje en lugar de culparte a ti mismo/a. La universidad puede presentar desafíos académicos y personales, pero entender que los errores son parte del proceso de crecimiento te ayuda a mantener una perspectiva positiva.",

            "Acepta quién eres en este momento, con todas tus fortalezas y áreas de crecimiento. Practica la gratitud, centrándote en las cosas positivas en tu vida. La autoaceptación y la gratitud fomentan una actitud más positiva hacia ti mismo/a.",

            "Trátate a ti mismo/a con la misma amabilidad y compasión que ofrecerías a un amigo en momentos difíciles. La autocompasión implica reconocer tus luchas sin juzgarte y recordarte que eres humano/a.",

            "Fomenta una mentalidad positiva al enfocarte en tus fortalezas y posibilidades en lugar de en las limitaciones. Desafía los pensamientos negativos y practica cambiarlos por afirmaciones positivas sobre ti mismo/a.",

            "Evita compararte constantemente con tus compañeros. Cada persona tiene su propio camino y ritmo de desarrollo. Enfócate en tu crecimiento personal y en tus logros individuales, en lugar de medir tu valía en comparación con los demás."
    };
    String[] consejos = {
            //Agregar todos los consejos que quieran
            "Respira profundamente y relájate.",
            "Dedica tiempo para ti mismo todos los días.",
            "Encuentra una actividad que te guste y que te relaje.",
            "Mantén una actitud positiva.",
            "Evita el estrés innecesario."
    };
    //Agregar todas las fraseds deseadas
    String[] frasesInspiradoras = {
            "La vida es corta, sonríe a menudo.",
            "Cree en ti mismo y todo será posible.",
            "El éxito es la suma de pequeños esfuerzos repetidos día tras día.",
            "La paciencia es amarga, pero su fruto es dulce.",
            "El optimismo es la fe que conduce al logro. Nada se puede hacer sin esperanza y confianza.",

    };

    //Agregar todos los enlaces deseados
    //En el metodo de video se agregaran los titulos de videos
    String[] enlacesVideosMeditacion = {
            "https://youtu.be/7jamzK0C4Eg?si=Amug3ENZpgJg40Nr",
            "https://youtu.be/10siFf9RUH8?si=xbpGWS3fKQhv3JEV",
            "https://youtu.be/d5dilTOFfSI?si=eSdtcagiyDV8TkoW",
            "https://youtu.be/KEElZdEWz0M?si=Y_PgkiDVnnE2KpdN",

    };



    //Creamos los botones
    Button buttonConsejos, buttonFrases, buttonVideos;
    TextView btnEstres, btnTranstorno, btnAnsiedad, btnSoledad, btnAmorPropio, btnMotivacion, btnCargaAcademica, btnProblemasPersonales;
    TextView btnProblemasFamiliares, btnRelacionesIntra, btnVideos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estres);


        cargarelementos();

        btnEstres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customRandom(estres);
            }
        });
        btnTranstorno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customRandom(transtornoAlimenticio);
            }
        });
        btnAnsiedad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customRandom(ansiedad);
            }
        });
        btnSoledad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customRandom(soledad);
            }
        });
        btnAmorPropio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customRandom(amorPropio);
            }
        });
        btnCargaAcademica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customRandom(cargaAcademica);
            }
        });
        btnProblemasPersonales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customRandom(problemasPersonales);
            }
        });
        btnProblemasFamiliares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customRandom(problemasFamiliares);
            }
        });
        btnRelacionesIntra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customRandom(relacionesInterpersonales);
            }
        });
        btnMotivacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customRandom(motivacion);
            }
        });

        btnVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videos();
            }
        });

    }

    //Metodos par nuestros mensajes aleatorios

    private void cargarelementos(){
        btnVideos = findViewById(R.id.lblVideos);
        btnEstres = findViewById(R.id.lblEstres);
        btnTranstorno = findViewById(R.id.lblTranstorno);
        btnAnsiedad = findViewById(R.id.lblAnsiedad);
        btnSoledad = findViewById(R.id.lblSoledad);
        btnAmorPropio = findViewById(R.id.lblAmorPropio);
        btnCargaAcademica = findViewById(R.id.lblCargaAcademica);
        btnProblemasPersonales = findViewById(R.id.lblProblemasPersonales);
        btnProblemasFamiliares = findViewById(R.id.lblProblemasFamiliares);
        btnRelacionesIntra = findViewById(R.id.lblRelacionesIntra);
        btnMotivacion = findViewById(R.id.lblMotivacion);
    }

    private void customRandom(String[] area){
        Random random = new Random();
        int itemIndex = random.nextInt(area.length);
        String consejo = area[itemIndex];
        crearDialogo("Recuerda", consejo);
    }


    //Metodo para crear nuestro cuadro de dialgo
    private void crearDialogo(String title, String contenido){
        AlertDialog.Builder builder = new AlertDialog.Builder(Estres.this);
        builder.setTitle(title);
        builder.setMessage(contenido);
        builder.setPositiveButton("Aceptar",null);
        builder.show();

    }

    //Metodo para mostrar los videos
    private void videos(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Estres.this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialogo_videos, null);
        builder.setView(dialogView);
        ListView listViewVideos = dialogView.findViewById(R.id.listViewVideos);

        // Colocar los titulos de los videos aca
        String[] titulosVideosMeditacion = {
                "MENSAJE PARA REFLEXIONAR (Michael Ronda - ES POSIBLE)",
                "Tu peor enemigo... EL MIEDO! / Psicóloga Paola Durazo",
                "Unstoppable - Sia | Sub. español",
                "Miley Cyrus - The Climb | En Español",

        };

        // Crea un ArrayAdapter para mostrar los títulos en la lista
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Estres.this, android.R.layout.simple_list_item_1, titulosVideosMeditacion);
        listViewVideos.setAdapter(adapter);

        final AlertDialog dialog = builder.create();

        listViewVideos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Cuando se selecciona un título, puedes abrir el enlace correspondiente
                String enlaceSeleccionado = enlacesVideosMeditacion[position];
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(enlaceSeleccionado));
                startActivity(intent);
                dialog.dismiss(); // Cierra el cuadro de diálogo
            }
        });

        dialog.show();
    }
}
package pe.turismo_local.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "lugares.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_LUGARES = "lugares";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOMBRE = "nombre";
    private static final String COLUMN_DESCRIPCION = "descripcion";
    private static final String COLUMN_CUIDAD = "ciudad";
    private static final String COLUMN_LATITUD = "latitud";
    private static final String COLUMN_LONGITUD = "longitud";
    private static final String COLUMN_IMAGEN = "imagen";
    private static final String COLUMN_FAVORITO = "favorito";

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_LUGARES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOMBRE + " TEXT, " +
                COLUMN_DESCRIPCION + " TEXT, " +
                COLUMN_CUIDAD + " TEXT, " +
                COLUMN_LATITUD + " REAL, " +
                COLUMN_LONGITUD + " REAL, " +
                COLUMN_IMAGEN + " TEXT, " +
                COLUMN_FAVORITO + " INTEGER DEFAULT 0)";

        db.execSQL(CREATE_TABLE);

        insertDatosIniciales(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LUGARES);
        onCreate(db);
    }

    public void insertDatosIniciales(SQLiteDatabase db) {
        String INSERT_LUGAR = "INSERT INTO " + TABLE_LUGARES + " (" +
                COLUMN_NOMBRE + ", " +
                COLUMN_DESCRIPCION + ", " +
                COLUMN_CUIDAD + ", " +
                COLUMN_LATITUD + ", " +
                COLUMN_LONGITUD + ", " +
                COLUMN_IMAGEN + ", " +
                COLUMN_FAVORITO + ") VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Lima
        db.execSQL(INSERT_LUGAR, new String[]{
                "Plaza de Armas",
                "La Plaza de Armas de Lima, también conocida como Plaza Mayor, es el corazón del Centro Histórico de Lima y el lugar donde se fundó la ciudad en 1535 por Francisco Pizarro. Rodeada de edificios emblemáticos como el Palacio de Gobierno, la Catedral de Lima y el Palacio Municipal, conserva su arquitectura colonial y una fuente de bronce del siglo XVII en su centro. Declarada Patrimonio Cultural de la Humanidad por la UNESCO en 1991, es un punto clave para la historia, la cultura y el turismo en la capital peruana.",
                "Lima",
                "-12.0453",
                "-77.0311",
                "https://www.chullostravelperu.com/blog/wp-content/uploads/2023/05/Plaza-de-Armas-de-Lima.jpg",
                "0"
        });

        db.execSQL(INSERT_LUGAR, new String[]{
                "Catedral de Lima",
                "La Catedral de Lima, ubicada en la Plaza de Armas, es el principal templo católico de la ciudad y un símbolo de la arquitectura colonial. Su construcción comenzó en 1535 por orden de Francisco Pizarro y ha sido reconstruida varias veces debido a terremotos. Su fachada neoclásica y sus altas torres contrastan con su interior barroco, donde se encuentran valiosas obras de arte religioso y la tumba del propio Pizarro. Es un importante destino turístico y religioso, reflejando la historia y el legado cultural del Perú.",
                "Lima",
                "-12.0432",
                "-77.0282",
                "https://www.peru.travel/Contenido/General/Imagen/es/630/1.1/catedral-de-lima.jpg",
                "0"
        });

        db.execSQL(INSERT_LUGAR, new String[]{
                "Palacio de Gobierno",
                "El Palacio de Gobierno del Perú, también conocido como Casa de Pizarro, es la sede del Poder Ejecutivo y residencia oficial del Presidente de la República. Ubicado en la Plaza de Armas de Lima, fue construido en 1535 por orden de Francisco Pizarro en el mismo lugar donde se encontraba el palacio del gobernante inca Taulichusco. A lo largo de los siglos, ha sido remodelado varias veces, destacando su actual fachada de estilo neobarroco. Es famoso por el Cambio de Guardia, un atractivo turístico que se realiza diariamente en su patio principal.",
                "Lima",
                "-12.0464",
                "-77.0300",
                "https://media.tacdn.com/media/attractions-splice-spp-674x446/0b/2c/f0/90.jpg",
                "0"
        });

        db.execSQL(INSERT_LUGAR, new String[]{
                "Parque Kennedy",
                "El Parque Kennedy, ubicado en el distrito de Miraflores, es uno de los espacios públicos más conocidos y concurridos de Lima. Es famoso por su ambiente vibrante, rodeado de restaurantes, cafeterías y tiendas, además de ser un punto de encuentro para artistas callejeros y ferias de artesanía. Su característica más peculiar es la gran cantidad de gatos que habitan en el parque, convirtiéndolo en un lugar icónico para los amantes de los animales. Es un destino ideal para disfrutar de la cultura, la gastronomía y la vida nocturna de la ciudad.",
                "Lima",
                "-12.1210",
                "-77.0301",
                "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/0e/b6/2e/51/parque-kennedy.jpg?w=800&h=500&s=1",
                "0"
        });

        db.execSQL(INSERT_LUGAR, new String[]{
                "Museo Larco",
                "El Museo Larco, ubicado en el distrito de Pueblo Libre, es uno de los museos más importantes del Perú, conocido por su impresionante colección de arte precolombino. Fundado en 1926 por Rafael Larco Hoyle, alberga más de 45,000 piezas arqueológicas, incluyendo cerámicas, textiles y joyería de antiguas civilizaciones peruanas. Destaca por su famosa colección de cerámica erótica moche y sus exhibiciones accesibles al público en depósitos abiertos. Su sede es una casona virreinal del siglo XVIII, rodeada de hermosos jardines, lo que lo convierte en un destino imprescindible para quienes desean conocer la historia del Perú prehispánico.",
                "Lima",
                "-12.0855",
                "-77.0944",
                "https://majestictyt.com/wp-content/uploads/2020/11/Museo-Larco.jpg",
                "0"
        });

        // Arequipa
        db.execSQL(INSERT_LUGAR, new String[]{
                "Plaza de Armas de Arequipa",
                "La Plaza de Armas de Arequipa es el corazón histórico y cultural de la ciudad, rodeada de majestuosos edificios construidos con sillar, una piedra volcánica blanca característica de la región. Destacan la Catedral de Arequipa, los portales coloniales y la Iglesia de la Compañía, que reflejan la riqueza arquitectónica de la ciudad. Es un punto de encuentro para locales y turistas, ofreciendo una vista impresionante del volcán Misti. Su belleza y valor histórico la convierten en uno de los espacios más emblemáticos del sur del Perú.",
                "Arequipa",
                "-16.3989",
                "-71.5369",
                "https://andinoperu.b-cdn.net/wp-content/uploads/2024/03/city-tour-arequipa-peru-plaza-de-armas-arequipa-tours-3.webp",
                "0"
        });

        db.execSQL(INSERT_LUGAR, new String[]{
                "Monasterio de Santa Catalina",
                "El Monasterio de Santa Catalina, ubicado en el centro histórico de Arequipa, es un impresionante complejo religioso fundado en 1579. Construido con sillar, una piedra volcánica blanca y rosada, abarca más de 20,000 m², formando una pequeña ciudad con calles, plazas y claustros. Durante siglos fue un convento de clausura para monjas de élite, manteniéndose aislado del mundo exterior hasta 1970, cuando se abrió al público. Su arquitectura colonial, combinada con su historia y coloridos pasajes, lo convierte en uno de los atractivos turísticos más fascinantes del Perú.",
                "Arequipa",
                "-16.3956",
                "-71.5372",
                "https://santacatalina.org.pe/wp-content/uploads/2020/06/santa-catalina-home.jpg",
                "0"
        });

        db.execSQL(INSERT_LUGAR, new String[]{
                "Mirador de Yanahuara",
                "El Mirador de Yanahuara, ubicado en el tradicional distrito de Yanahuara en Arequipa, es un icónico punto panorámico que ofrece una vista espectacular de la ciudad y del imponente volcán Misti. Construido con sillar, cuenta con arcos en los que están grabadas frases de personajes ilustres. Además de su impresionante paisaje, el mirador está rodeado de calles empedradas y casonas coloniales, reflejando la riqueza histórica y cultural de la ciudad. Es un destino ideal para disfrutar de la arquitectura arequipeña y capturar impresionantes fotografías.",
                "Arequipa",
                "-16.3905",
                "-71.5454",
                "https://www.chullostravelperu.com/blog/wp-content/uploads/2023/02/MIRADOR-DE-YANAHUARA.jpg",
                "0"
        });

        db.execSQL(INSERT_LUGAR, new String[]{
                "Molino de Sabandía",
                "El Molino de Sabandía, construido en 1621, es una joya de la arquitectura colonial ubicada en el distrito de Sabandía, Arequipa. Este histórico molino de sillar aún conserva su estructura original y su mecanismo de molienda, utilizado antiguamente para procesar granos. Rodeado de bellos paisajes, con árboles, andenes y un río cercano, es un destino perfecto para disfrutar de la naturaleza y la historia. Además, el lugar cuenta con áreas verdes y animales típicos de la región, como alpacas y llamas, lo que lo convierte en un atractivo turístico ideal para toda la familia.",
                "Arequipa",
                "-16.4493",
                "-71.4973",
                "https://viasatelital.com/mapas/images/sabandia-arequipa.jpg",
                "0"
        });

        db.execSQL(INSERT_LUGAR, new String[]{
                "Reserva Nacional de Salinas y Aguada Blanca",
                "La Reserva Nacional de Salinas y Aguada Blanca, ubicada entre Arequipa y Moquegua, es un área protegida que abarca más de 366,000 hectáreas y resguarda una gran diversidad de fauna y paisajes espectaculares. Es hogar de especies emblemáticas como la vicuña, el zorro andino y los flamencos, además de contar con impresionantes volcanes como el Misti, Chachani y Pichu Pichu. Destaca la Laguna de Salinas, donde se pueden observar espejismos y flamencos andinos. Su combinación de paisajes altiplánicos, humedales y volcanes la convierte en un destino ideal para el ecoturismo y la fotografía.",
                "Arequipa",
                "-16.4162",
                "-71.1065",
                "https://www.chullostravelperu.com/blog/wp-content/uploads/2023/02/000278982W.jpg",
                "0"
        });

        // Tacna
        db.execSQL(INSERT_LUGAR, new String[]{
                "Catedral de Tacna",
                "La Catedral de Tacna, ubicada en el centro de la ciudad, es el principal templo católico de la región y un símbolo de su identidad. Su construcción comenzó en 1875 bajo el diseño de la empresa francesa Alejandro Gustavo Eiffel, pero fue interrumpida por la Guerra del Pacífico y finalizada recién en 1954. De estilo neorrenacentista, destaca por su imponente fachada de sillar, sus altas torres y su elegante interior con vitrales y esculturas religiosas. Es un ícono arquitectónico e histórico de Tacna, reflejando la resiliencia y el espíritu de la ciudad.",
                "Tacna",
                "-18.0066",
                "-70.2474",
                "https://i.ytimg.com/vi/mXyZ9sA4xEk/maxresdefault.jpg",
                "0"
        });

        db.execSQL(INSERT_LUGAR, new String[]{
                "Museo Ferroviario de Tacna",
                "El Museo Ferroviario de Tacna, ubicado en la antigua estación de trenes de la ciudad, es un importante espacio histórico que preserva el legado del ferrocarril Tacna-Arica, inaugurado en 1856 y considerado el primer ferrocarril del Perú. El museo exhibe locomotoras, vagones antiguos, herramientas ferroviarias y documentos históricos que narran la evolución del transporte en la región. Su arquitectura y su colección transportan a los visitantes a una época en la que el tren era el principal medio de conexión entre Perú y Chile, convirtiéndolo en un sitio de gran valor cultural y turístico.",
                "Tacna",
                "-18.0048",
                "-70.2462",
                "https://portal.andina.pe/EDPfotografia3/Thumbnail/2023/08/29/000989419W.jpg",
                "0"
        });

        db.execSQL(INSERT_LUGAR, new String[]{
                "Baños Termales de Calientes",
                "Los Baños Termales de Calientes, ubicados en el distrito de Pachía, a unos 22 km de la ciudad de Tacna, son un destino ideal para el descanso y el turismo de bienestar. Sus aguas, de origen volcánico, alcanzan temperaturas de hasta 50°C y son ricas en minerales con propiedades terapéuticas, beneficiosas para tratar dolencias musculares y articulares. El complejo cuenta con pozas y piscinas al aire libre, rodeadas de un hermoso paisaje andino. Es un lugar perfecto para relajarse y disfrutar de la naturaleza en un ambiente tranquilo y acogedor.",
                "Tacna",
                "-17.7768",
                "-70.1715",
                "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/11/c6/bb/d8/img-20180114-171122-largejpg.jpg?w=1200&h=-1&s=1",
                "0"
        });

        db.execSQL(INSERT_LUGAR, new String[]{
                "Petroglifos de Miculla",
                "Los Petroglifos de Miculla, ubicados en el distrito de Pachía, a unos 25 km de la ciudad de Tacna, son uno de los conjuntos de arte rupestre más importantes del Perú. Se estima que fueron grabados entre 500 y 1500 d.C. por antiguas culturas preincaicas, representando escenas de caza, figuras antropomorfas, zoomorfas y geométricas. Estas enigmáticas inscripciones, distribuidas en un área de aproximadamente 20 km², ofrecen un vistazo al pensamiento y la cosmovisión de los antiguos habitantes de la región. Además, el sitio cuenta con senderos turísticos y un puente colgante, convirtiéndolo en un destino fascinante para los amantes de la historia y la arqueología.",
                "Tacna",
                "-17.9373",
                "-70.1502",
                "https://www.somosperu.org.pe/wp-content/uploads/2018/09/PETROGLIFOS-DE-MICULLA.jpg",
                "0"
        });

        db.execSQL(INSERT_LUGAR, new String[]{
                "Monumento al Soldado Desconocido",
                "El Monumento al Soldado Desconocido, ubicado en el Cerro Intiorko de Tacna, es un homenaje a los peruanos que lucharon en la Guerra del Pacífico (1879-1883) y cuya identidad quedó en el anonimato. La escultura representa a un soldado con la mirada firme y su fusil en posición de descanso, simbolizando el sacrificio y la valentía de quienes defendieron la patria. Desde su ubicación privilegiada, se puede apreciar una vista panorámica de la ciudad. Es un sitio de gran significado histórico y patriótico, visitado especialmente durante las celebraciones de la Reincorporación de Tacna al Perú.",
                "Tacna",
                "-17.9994",
                "-70.8111",
                "https://100fotosdelviaje.com.ar/wp-content/uploads/2019/05/Foto-1454-A%C3%91O-2019-Per%C3%BA-Lima-Monumento-al-Soldado-Desconocido-Chorrillos-100fotosdelviaje.com_.ar_.jpg",
                "0"
        });
    }
}

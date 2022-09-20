package data;

import Estructuras_de_datos.*;

import java.io.*;

public class DataBase implements Serializable {

    //Direccion sebastian: C:\\Users\\Sebastian\\Documents\\NetBeansProjects\\Proyecto_Estructuras\\src\\
    //Dirección David: C:\\Users\\USUARIO\\Desktop\\Estructuras de Datos\\Proyecto\\src\\
    private static String localDatabase = "";

    public static MyArrayList<Production> myArrayListProduction = new MyArrayList<Production>(); // guarda la lista de producciones
    public static SinglyLinkedList<String> sLnameP = new SinglyLinkedList<String>();// carga los nombres de las producciones para luego buscarlas
     public static AvlNodeTree<Production> prodTree = new AvlNodeTree<>();
    
    private static SinglyLinkedList<User> singlyLinkedListUser;
    public static SinglyLinkedList<String> sLnameU = new SinglyLinkedList<String>();

    public static MyHashTable<User> hashTableUser=new  MyHashTable<User>();
    
    public static MyArrayList<RawMaterial> myArrayListMaterial;
    public static MyArrayList<Stage> myArrayListStage;

    //WRITE
    public static void WriteArchive() {
        try {
            if (sLnameU.getSize() == 0) {
                CreateArchive("Usuarios", true);
            }
            if (sLnameP.getSize() == 0) {
                CreateArchive("Informe", true);
            }
            
            singlyLinkedListUser = new SinglyLinkedList<User>();
            
                for (int i = 0; i < hashTableUser.getTheLists().getSize(); i++) {
                    SinglyLinkedList<User> user= hashTableUser.getTheLists().getItem(i);
                    for (int j = 0; j < user.getSize(); j++) {
                        singlyLinkedListUser.pushBack(user.getItem(j));
                        
                    }
                }
               
            for (int i = 0; i < singlyLinkedListUser.getSize(); i++) {
                Write("Usuarios", "Usuarios", i);
            }
            
            for (int k = 0; k < myArrayListProduction.getSize(); k++) {
                String nameA = myArrayListProduction.getItem(k).getId()+myArrayListProduction.getItem(k).getName();
                llenarArchive(nameA, k);
            }

        } catch (IOException e) {
            System.out.println("Error al guardar datos");
        }
    }

    public static void CreateArchive(String name, Boolean new1) throws IOException {//Crea un archivo de nombre name
        String nametxt = localDatabase;
        nametxt = nametxt + name;
        File file = new File(nametxt + ".txt");
        FileWriter flwriter = new FileWriter(file.getAbsoluteFile(), new1);
        flwriter.close();
            
    }

    public static void llenarArchive(String nameA, int i) throws IOException {//Llena los archivos de producciones
        CreateArchive(nameA, false);
        Write(nameA, "Producciones", i);
        Write(nameA, "Materiales", i);
        Write(nameA, "Stage", i);
        informeArchive(nameA, true);
    }

    public static void eliminar(String nameA, String tip) throws IOException {//Elimina un archivo
        try{
        String nametxt = localDatabase;
        nametxt = nametxt + nameA;
        File file = new File(nametxt + ".txt");
        
        if (file.delete()) {
            System.out.println("El archivo" + nameA + "ha sido borrado satisfactoriamente");
        } else {
          //  System.out.println("El archivo" + nameA + "no puedo ser borrado satisfactoriamente");
        }
        
        
        
        
        if(tip.equalsIgnoreCase("Usuario")){            
        }else{
            sLnameP.removeItem(nameA);
            informeArchive("Eliminar", false);
        }}catch (IOException e){
        }
            
    
    }

    public static void Write(String nameA, String name, int number) throws IOException {//Escribe el archivo dependiendo lo que vaya a escribir
        
        String nametxt = localDatabase;
        nametxt = nametxt + nameA;
        File file = new File(nametxt + ".txt");
        Boolean User0=true;
        if(name.equalsIgnoreCase("Usuarios") && number ==0){
            User0=false;
        }
        FileWriter flwriter = new FileWriter(file.getAbsoluteFile(), User0);
        BufferedWriter bfwriter = new BufferedWriter(flwriter);

        if (name.equalsIgnoreCase("Materiales")) {
            myArrayListMaterial = myArrayListProduction.getItem(number).getRawMaterials();
            bfwriter.write("3.Materiales;\n");
            int size = myArrayListMaterial.getSize();
            for (int index = 0; index < size; index++) {
                String write = myArrayListMaterial.getItem(index).toString();
                String size2 = String.valueOf(5 + myArrayListMaterial.getItem(index).getParametrosCalidad().getSize() * 4);
                bfwriter.write(size2 + "$" + write);
                for (int j = 0; j < myArrayListMaterial.getItem(index).getParametrosCalidad().getSize(); j++) {
                    write = myArrayListMaterial.getItem(index).getParametrosCalidad().getItem(j).toString();
                    bfwriter.write("*" + write);
                }

                bfwriter.write(";\n");
            }

        } else if (name.equalsIgnoreCase("Usuarios") && singlyLinkedListUser.getSize() != 0) {
            String write = "";        
            write = singlyLinkedListUser.getItem(number).toString();
            bfwriter.write("5" + "$" + write + ";\n");

        } else if (name.equalsIgnoreCase("Producciones") && myArrayListProduction.getSize() != 0) {
            String write = myArrayListProduction.getItem(number).getName();
            bfwriter.write("1.Nombre: " + write + ";\n");
            bfwriter.write(myArrayListProduction.getItem(number).getId()+ ";\n");
            Boolean a = myArrayListProduction.getItem(number).isIsActive();
            write = "0";
            if (a) {
                write = "1";
            }
            bfwriter.write(write + ";\n");

            Boolean b = myArrayListProduction.getItem(number).isIsFinished();
            write = "0";
            if (b) {
                write = "1";
            }
            bfwriter.write(write + ";\n");

            write = myArrayListProduction.getItem(number).getStartDate();
            bfwriter.write(write + ";\n");

            write = myArrayListProduction.getItem(number).getEndDate();
            bfwriter.write(write + ";\n");

            write = Integer.toString(myArrayListProduction.getItem(number).getCurrentStage());
            bfwriter.write(write + ";\n");

            write = myArrayListProduction.getItem(number).getDescription();
            bfwriter.write("2.Descripcion" + ";\n" + write + ";\n");


        } else if (name.equalsIgnoreCase("Stage")) {
            bfwriter.write("4.Stage;\n");

            myArrayListStage = myArrayListProduction.getItem(number).getStages();

            for (int index = 0; index < myArrayListStage.getSize(); index++) {

                String write = myArrayListStage.getItem(index).toString();

                String size2 = String.valueOf(8 + myArrayListStage.getItem(index).getParameterList().getSize() * 4);

                bfwriter.write(size2 + "$" + write);

                for (int j = 0; j < myArrayListStage.getItem(index).getParameterList().getSize(); j++) {
                    write = myArrayListStage.getItem(index).getParameterList().getItem(j).toString();
                    bfwriter.write("*" + write);
                }

                bfwriter.write(";\n");
            }

        }

        bfwriter.close();
         flwriter.close();
    }

    public static void informeArchive(String name, Boolean neww) throws IOException { //Actualiza la lista de procesos
        try{
        String nametxt = localDatabase;
        nametxt = nametxt + "Informe";
        File file = new File(nametxt + ".txt");
        FileWriter flwriter = new FileWriter(file.getAbsoluteFile(), neww);
        try (BufferedWriter bfwriter = new BufferedWriter(flwriter)) {
            if (name.equals("Eliminar")) {
                for (int i = 0; i < sLnameP.getSize(); i++) {
                    bfwriter.write(sLnameP.getItem(i) + ";" + "\n");
                }

            } else {
                if (sLnameP.getSize() != 0) {

                    if (sLnameP.getIndex(name) == -1) {
                        bfwriter.write(name + ";" + "\n");
                    }
                } else {
                    bfwriter.write(name + ";" + "\n");

                }
            }
            bfwriter.close();
        }
        flwriter.close();
        }catch(IOException e){}
    }

    //Load
    public static void loadArchive() {
        try {

            load("", "Informe");
            load("", "Usuarios");
            for (int i = 0; i < sLnameP.getSize(); i++) {
                DataBase.reach(sLnameP.getItem(i), "Informe");
                
            }
              for (int i = 0; i < sLnameU.getSize(); i++) {
                 DataBase.reach(String.valueOf(i), "Usuarios");
            }
           
       
        } catch (IOException e) {
            System.out.println("Error al cargar archivos");
        }
    }

    public static void load(String numbertxt, String name) throws IOException { //Enlista los procesos guardados
        String url = localDatabase;
        BufferedReader os2;
        try {
            FileReader fileStremx2 = new FileReader(url + name + numbertxt + ".txt");
            os2 = new BufferedReader(fileStremx2);

            if (name.equalsIgnoreCase("Informe")) {

                String loadData2 = os2.readLine();
                while (loadData2 != null) {
                    for (int i = 0; i < loadData2.length(); i++) {
                        if (loadData2.charAt(i) == ';') {
                            sLnameP.pushBack(loadData2.substring(0, i));
                            break;
                        }

                    }
                    loadData2 = os2.readLine();
                }
            } else if (name.equalsIgnoreCase("Usuarios")) {
                String loadData2 = os2.readLine();
                while (loadData2 != null) {
                    for (int i = 0; i < loadData2.length(); i++) {
                        if (loadData2.charAt(i) == ';') {
                            sLnameU.pushBack(loadData2.substring(2, i));
                            break;
                        }
                    }
                    loadData2 = os2.readLine();
                }
            }
        } catch (IOException e) {

        }
    }
//Busca el nombre proceso o el nombre de usuario y luego lo carga al sistema con todos sus datos

    public static Boolean reach(String buscarBD, String tip) throws IOException {
        int i = -1;

        if (tip.equals("Usuarios")) {
            // i = sLnameU.getIndex(buscarBD);
        } else if (tip.equals("Informe")) {
            //i = sLnameP.getIndex(buscarBD);
        }

        if (tip.equals("Informe")) {
            FileReader fileStremx = new FileReader(localDatabase + buscarBD + ".txt");

            BufferedReader os = new BufferedReader(fileStremx);
            String loadData = os.readLine();
            String name = loadData.substring(10, loadData.length() - 1);
            
            loadData = os.readLine();
            String ID = loadData.substring(0, loadData.length() - 1);
            
            Boolean start = true;
            loadData = os.readLine();
            if ((loadData).equals("0;")) {
                start = false;
            }
            loadData = os.readLine();
            Boolean end = true;
            if ((loadData).equals("0;")) {
                end = false;
            }
            loadData = os.readLine();
            String fecha1 = loadData.substring(0, loadData.length() - 1);

            loadData = os.readLine();
            String fecha2 = loadData.substring(0, loadData.length() - 1);

            loadData = os.readLine();
            String num = loadData.substring(0, loadData.length() - 1);

            os.readLine();
            loadData = os.readLine();
            String descripcion = loadData.substring(0, loadData.length() - 1);

            os.readLine();
            myArrayListMaterial = new MyArrayList<>();

            while (!(loadData = os.readLine()).equals("4.Stage;")) {
                int tam = 0;
                int count = 0;
                for (int o = 0; o < loadData.length(); o++) {
                    if (loadData.charAt(o) == '$') {
                        tam = Integer.parseInt(loadData.substring(0, o));
                        count++;
                        break;
                    }
                    count++;

                }
                String load[] = new String[tam];
                int j = 0;
                int k = count;
                int z = 0;
                for (int o = k; o < loadData.length(); o++) {
                    if (loadData.charAt(o) == ';') {
                        load[j] = loadData.substring(k, o);
                        k = o + 1;
                        j++;
                    } else if (loadData.charAt(o) == '*') {
                        load[j] = loadData.substring(k, o);
                        j++;
                        k = o + 1;
                        z++;
                    }
                }

                MyArrayList<Parameter> parametrosCalidad = new MyArrayList<>();
                Parameter parameter;
                RawMaterial rawMaterial;
                int a = 0;
                for (int o = 0; o < z; o++) {
                    parameter = new Parameter(load[5 + a], Double.parseDouble(load[6 + a]), Double.parseDouble(load[7 + a]), Double.parseDouble(load[8 + a]));
                    a = a + 4;
                    parametrosCalidad.pushBack(parameter);
                    
                }
                rawMaterial = new RawMaterial(load[0], load[1], load[2], load[3], load[4], parametrosCalidad);
                myArrayListMaterial.pushBack(rawMaterial);
            }
            // os.readLine();
            myArrayListStage = new MyArrayList<>();

            while ((loadData = os.readLine()) != null) {

                int tam = 0;
                int count = 0;
                for (int o = 0; o < loadData.length(); o++) {
                    if (loadData.charAt(o) == '$') {
                        tam = Integer.parseInt(loadData.substring(0, o));
                        break;
                    }
                    count++;
                }
                String load[] = new String[tam];
                int j = 0;
                int k = count + 1;
                int z = 0;
                for (int o = k; o < loadData.length(); o++) {
                    if (loadData.charAt(o) == ';') {
                        load[j] = loadData.substring(k, o);
                        k = o + 1;
                        j++;
                    } else if (loadData.charAt(o) == '*') {
                        load[j] = loadData.substring(k, o);
                        j++;
                        k = o + 1;
                        z++;
                    }
                }
                MyArrayList<Parameter> parametrosCalidad = new MyArrayList<>();
                Parameter parameter;
                Stage stage;
                int a = 0;
                for (int o = 0; o < z; o++) {
                    parameter = new Parameter(load[8 + a], Double.parseDouble(load[9 + a]), Double.parseDouble(load[10 + a]), Double.parseDouble(load[11 + a]));
                    a = a + 4;
                    parametrosCalidad.pushBack(parameter);

                }
                Boolean s = true;
                if (load[6].equals("0")) {
                    s = false;
                }
                Boolean s2 = true;
                if (load[7].equals("0")) {
                    s2 = false;
                }

                stage = new Stage(Integer.valueOf(load[5]), parametrosCalidad, load[0], load[2], load[3], load[1], load[4]);
                stage.setIsActive(s);
                stage.setIsFinished(s2);
                myArrayListStage.pushBack(stage);

            }

            Production production = new Production(name, descripcion, myArrayListMaterial, myArrayListStage);
            production.setIsActive(start);
            production.setIsFinished(end);
            production.setStartDate(fecha1);
            production.setEndDate(fecha2);
            production.setCurrentStage(Integer.valueOf(num));
            production.setId(ID);
            myArrayListProduction.pushBack(production);
            prodTree.insert(production);
            os.close();
            return true;
        } else if (tip.equals("Usuarios")) {
            i=Integer.valueOf(buscarBD);
           
            FileReader fileStremx = new FileReader(localDatabase + "Usuarios" + ".txt");
            BufferedReader os = new BufferedReader(fileStremx);
            for (int j = 0; j < i; j++) {
                os.readLine();
            }
            String loadData = os.readLine();
            int tam = 0;
            for (int o = 0; o < loadData.length(); o++) {
                if (loadData.charAt(o) == '$') {
                    tam = Integer.parseInt(loadData.substring(0, o));
                }
            }
            String load[] = new String[tam];
            int j = 0;
            int k = 2;
            int z = 0;
            for (int o = 0; o < loadData.length(); o++) {
                if (loadData.charAt(o) == ';') {
                    load[j] = loadData.substring(k, o);
                    k = o + 1;
                    j++;
                }
            }

            User user;
            Boolean ad = true;
            if (load[4].equals("0")) {
                ad = false;
            }
            user = new User(load[2], Integer.valueOf(load[1]), load[0], load[3], ad);
            hashTableUser.Add(user);
            return true;
        }
        return false;
    }

    public static Boolean printTXT(int index, String t) throws IOException {
        try {
            String nametxt2 = "Producción " + myArrayListProduction.getItem(index).getName();

            CreateArchive(nametxt2, true);
            String nametxt = localDatabase;
            nametxt = nametxt + nametxt2;
            System.out.println(t);
            File file = new File(nametxt + ".txt");
            FileWriter flwriter = new FileWriter(file.getAbsoluteFile(), true);
           
            BufferedWriter bfwriter = new BufferedWriter(flwriter);
            bfwriter.write(t);
            
            bfwriter.close();
            
            
            return true;
        } catch (IOException e) {
            return false;
           
        }

    }
}

package com.app.pagos.Service;

import com.app.pagos.Controller.PaymentController;
import com.app.pagos.Model.Consignment;
import com.app.pagos.Persistence.PaymentFile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class ConsignmentService {

    PaymentService service;

    private final static String FILEPATH = "src/main/java/com/app/pagos/Persistence/consignmentData.txt";

    private PaymentFile file = new PaymentFile();
    ArrayList<Consignment> list = new ArrayList<>();


    public ConsignmentService() {
        loadList();
        service = new PaymentService();
    }

    public void create(int id, int payments){
        int tPreview= (int) vTotal(id);
        int pPreview= payments;

        double cont=Math.random()*10+1;
        double aux=0;
        if (tPreview<pPreview){
            for (int i = 0; i<=(int)cont;i++){
                double n=Math.random()*100000+1;
                aux+=n;
                if (pPreview>(aux+tPreview)){
                    Consignment consignment = new Consignment(list.size()+1,(int) n,String.valueOf(LocalDate.now()),id);
                    list.add(consignment);
                }else {
                    aux-=n;
                    Consignment consignment = new Consignment(list.size()+1,(int)(pPreview-(aux+tPreview)),String.valueOf(LocalDate.now()),id);
                    list.add(consignment);
                    cont=i;
                }

            }
        }
/*
        int tPreview= (int) vTotal(id);
        int pPreview= (int) service.tValue(id);

        if (tPreview!=pPreview){
            addConsigments(tPreview,pPreview,id);
        }*/


        loadFile();
    }

    private void addConsigments(int tPreview, int pPreview, int id) {
        double percent = (Math.floor(Math.random()*10+1))/10;
        if (tPreview+(pPreview*percent)<=pPreview){
            Consignment consignment = new Consignment(list.size()+1,(int)(pPreview*percent),String.valueOf(LocalDate.now()),id);
            list.add(consignment);
            tPreview+=(pPreview*percent);
        }else{
            addConsigments(tPreview,pPreview,id);
        }
    }

    public void update(){

    }

    public ArrayList<Consignment> showAll(){
        return list;
    }

    public ArrayList<Consignment> showByAptm(int id){
        ArrayList<Consignment> auxList = new ArrayList<>();
        for (Consignment consignment :
                list) {
            if (consignment.getId_aptm() == id){
                auxList.add(consignment);
            }
        }
        return auxList;
    }

    public Consignment showOne(int id){
        for (Consignment auc :
                list) {
            if (auc.getId_consignment() == id){
                return auc;
            }
        }
        return null;
    }

    public double vTotal(int id_client){
        double sum=0;
        for (Consignment consignment :
                list) {
            if (consignment.getId_aptm() == id_client){
                sum+=consignment.getValue();
            }
        }
        return sum;
    }

    public void loadFile()  {
        String consignments = "";
        for (Consignment aux :
                list) {
            consignments += aux.toString()+"\n";
        }

        file.SobreescribirInformacion(FILEPATH, consignments);
    }

    public void loadList() {
        list.clear();
        for (int i = 0; i < file.ContenidoArchivo(FILEPATH).size(); i++) {

            String line="";
            line = file.ContenidoArchivo(FILEPATH).get(i);

            String [] split = line.split(",");
            Consignment consignment = new Consignment(Integer.parseInt(split[0]), Double.parseDouble(split[1]),split[2],Integer.parseInt(split[3]));
            list.add(consignment);
        }
    }
}

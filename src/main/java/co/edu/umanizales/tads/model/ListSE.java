package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.controller.dto.ReportKidsDTO;
import co.edu.umanizales.tads.exception.ListSEException;
import lombok.Data;

@Data
public class ListSE {
    private Node head;
    private int size;


    public void add(Kid kid) throws ListSEException {
        if(head != null){
            Node temp = head;
            while(temp.getNext() !=null)
            {
                if(temp.getData().getIdentification().equals(kid.getIdentification())){
                    throw new ListSEException("Ya existe un niño");
                }
                temp = temp.getNext();

            }
            if(temp.getData().getIdentification().equals(kid.getIdentification())){
                throw new ListSEException("Ya existe un niño");
            }
            /// Parado en el último
            Node newNode = new Node(kid);
            temp.setNext(newNode);
        }
        else {
            head = new Node(kid);
        }
        size ++;
    }

    public void invert() throws ListSEException{
        if (this.head != null) {
            ListSE listCP = new ListSE();
            Node temp = this.head;
            while (temp != null) {
                listCP.addToStart(temp.getData());
                temp = temp.getNext();
            }
            this.head = listCP.getHead();
        }
        else{
            throw new ListSEException("La lista está vacía");
        }
    }


    public void orderBoysToStart() throws ListSEException {
        if (this.head != null) {
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getGender() == 'M') {
                    listCp.addToStart(temp.getData());
                } else {
                    listCp.add(temp.getData());
                }

                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
        else{
            throw new ListSEException("La lista está vacía");
        }
    }

    /**
     - 3: Intercalar niño, niña, niño, niña *//

    public void intercalateBoysGirls() throws ListSEException {
        ListSE listBoy = new ListSE();
        ListSE listGirl = new ListSE();
        Node temp = this.head;

        if (temp == null) {
            throw new ListSEException("La lista está vacía");
        }

        while (temp != null){
            if(temp.getData().getGender()=='M'){
                listBoy.add(temp.getData());
            }
            if(temp.getData().getGender()=='F'){
                listGirl.add(temp.getData());
            }
            temp = temp.getNext();
        }
        ListSE newListBoysGirls = new ListSE();
        Node boyNode = listBoy.getHead();
        Node girlNode = listGirl.getHead();
        while (boyNode != null || girlNode != null){
            if (boyNode != null){
                newListBoysGirls.add(boyNode.getData());
                boyNode = boyNode.getNext();
            }
            if (girlNode != null){
                newListBoysGirls.add(girlNode.getData());
                girlNode = girlNode.getNext();
            }
        }
        this.head = newListBoysGirls.getHead();
    }

    /**
     -Ejercicio número 4: Dada una edad eliminar a los niños de la edad dada

     */
    public void deleteByAge(byte age) throws ListSEException {
        if(this.head!=null)
        {
            if(this.head.getData().getAge() == age) {
                this.head = this.head.getNext();
            }
            else {
                Node temp = this.head;
                while(temp!=null) {
                    if(temp.getNext() != null && temp.getNext().getData().getAge() == age) {
                        break;
                    }
                    temp= temp.getNext();
                }
                if(temp!= null) {
                    temp.setNext(temp.getNext().getNext());
                }
                else {
                    throw  new ListSEException("La edad "+ age + " no existe en la lista");
                }
            }
        }
        else {
            throw  new ListSEException("No hay datos en la lista");
        }
    }

    /**
     -Ejercicio 5: Obtener el promedio de edad de los niños de la lista
     */
    public float averageAge() throws ListSEException {
        if (head != null){
            Node temp = head;
            int count = 0;
            int ages = 0;
            while(temp.getNext() != null) {
                count++;
                ages = ages + temp.getData().getAge();
                temp = temp.getNext();
            }
            return (float) ages/count;
        }
        else{
            throw new ListSEException("La lista está vacía");
        }
    }

    /**
     Ejercicio 6: Generar un reporte que me diga cuantos niños hay de cada ciudad.
     */
    public int getCountKidsByLocationCode(String code) throws ListSEException {
        int count = 0;
        if (this.head != null) {
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getLocation().getCode().equals(code)) {
                    count++;
                }
                temp = temp.getNext();
            }
            return count;
        } else {
            throw new ListSEException("La lista está vacía");
        }
    }

    public int getCountKidsByDepartmentCode(String code) throws ListSEException{
        int count = 0;
        if (this.head != null) {
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getLocation().getCode().equals(code)) {
                    count++;
                }
                temp = temp.getNext();
            }
            return count;
        }
        else{
            throw new ListSEException("La lista está vacía");
        }
    }

    public void getReportKidsByLocationGendersByAge(byte age, ReportKidsDTO report){
        if(head != null){
            Node temp = this.head;
            while(temp!=null){
                if(temp.getData().getAge() > age){
                    report.updateQuantity(temp.getData().getLocation().getName(),
                            temp.getData().getGender());
                }
                temp = temp.getNext();
            }
        }
    }

    /**
     -Ejercicio 7: Método que me permita decirle a un niño determinado que adelante un número de posiciones dadas

     */
    public void passByPosition(String identification, int positions) throws ListSEException{
        if (head != null){
            if(positions<size){
                if(head.getData().getIdentification()==identification){

                }
                else{
                    int count = 1;
                    Node temp = head;
                    while(temp.getNext().getData().getIdentification()!=identification){
                        temp = temp.getNext();
                        count++;
                        if(temp.getNext()!=null){
                            return;
                        }
                    }
                    Node temp2 =new Node(temp.getNext().getData());
                    temp.setNext(temp.getNext().getNext());
                    if(positions >= count+1){
                        addToStart(temp2.getData());
                    }
                    else{
                        addByPosition((count+1) - positions, temp2.getData());
                    }
                }
            }
            else{
                throw new ListSEException("La posición ingresada es mayor o igual al tamaño de la lista.");
            }
        }
        else{
            throw new ListSEException("La lista se encuentra vacía.");
        }
    }

    /**
     -Ejercicio 8: Método que me permita decirle a un niño determinado que pierda un numero de posiciones dadas

     */
    public void afterwardsPositions(String identification, int positions) throws ListSEException {
        if (head!=null){
            if(positions<size){
                if(head.getData().getIdentification()==identification){
                    Node node = new Node(head.getNext().getData());
                    addByPosition(positions+1, node.getData());
                    head = head.getNext();
                }
                else{
                    int count = 1;
                    Node temp = head;
                    while(temp.getNext().getData().getIdentification()!=identification){
                        temp = temp.getNext();
                        count++;
                        if(temp.getNext()!=null){
                            return;
                        }
                    }
                    Node temp2=new Node(temp.getNext().getData());
                    temp.setNext(temp.getNext().getNext());
                    addByPosition(count+1+positions, temp2.getData());
                }
            }
            else{
                throw new ListSEException("La posición proporcionada excede el tamaño de la lista");
            }
        }
        else{
            throw new ListSEException("La lista se encuentra vacía.");
        }
    }

    /**
     -Ejercicio 9: Obtener un informe de niños por rango de edades

     */
    public int rangeByAge(int min, int max) throws ListSEException{
        if (head == null) {
            throw new ListSEException("La lista se encuentra vacía.");
        }
        Node temp = head;
        int count = 0;
        while(temp != null){
            if(temp.getData().getAge() >= min && temp.getData().getAge() <= max){
                count++;
            }
            temp = temp.getNext();
        }
        return count;
    }


    /*
    -Ejercicio 10: Implementar un método que me permita enviar al final de la lista a los niños que

     */
    public void kidsByLetter(char initial) throws ListSEException {

        if (this.head == null) {
            throw new ListSEException("La lista está vacía");
        }

        ListSE listCP = new ListSE();
        Node temp = this.head;

        while (temp != null){
            if (temp.getData().getName().charAt(0) != Character.toUpperCase(initial)){
                listCP.add(temp.getData());
            }
            temp = temp.getNext();
        }
        temp = this.head;
        while (temp != null){
            if (temp.getData().getName().charAt(0) == Character.toUpperCase(initial)){
                listCP.add(temp.getData());
            }
            temp = temp.getNext();
        }
        this.head = listCP.getHead();
    }

    public void addByPosition(int position, Kid kid) throws ListSEException {
        if (head!=null) {
            if (position == 1) {
                addToStart(kid);
            } else {
                Node temp = head;
                int cont =1;
                while (temp != null && cont<position-1)
                {
                    temp = temp.getNext();
                    cont++;
                }
                if (temp != null) {
                    Node newNode = new Node(kid);
                    newNode.setNext(temp.getNext());
                    temp.setNext(newNode);

                } else {
                    add(kid);
                }
            }
        }else{
            add(kid);
        }
    }

    /*
    si hay datos en la cabeza y si en el siguiente nodo también hay datos
    si
        llamo a un ayudante y le digo que se posicione en la cabeza
        que el ayudante recorra la lista hasta que llegue al último (mientras hayan datos)
            que el ayudante tome el siguiente nodo (o se pase al final)
            se guarda la cabeza en una variable copia de niños
            que se reemplace la cabeza con el objeto de datos del último nodo (el primero niño ahora es último)
            que se reemplace el último nodo (el último ahora es el primero)
     */
    public void changesExtremes() throws ListSEException{
        if (this.head != null && this.head.getNext() != null) {
            Node temp = this.head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }

            Kid copy = this.head.getData();
            this.head.setData(temp.getData());
            temp.setData(copy);
        }
        else
        {
            throw  new ListSEException("No es posible cambiar de extremos.");
        }
    }

    /* Adicionar al inicio
    si hay datos
    si
        meto al niño en un costal (nuevocostal)
        le digo a nuevo costal que tome con su brazo a la cabeza
        cabeza es igual a nuevo costal
    no
        meto el niño en un costal y lo asigno a la cabeza
     */
    public void addToStart(Kid kid) throws ListSEException {
        if (head != null) {
            Node newNode = new Node(kid);
            newNode.setNext(head);
            head = newNode;
        } else if(this.head == null) {
            head = new Node(kid);
        }
        else{
            throw new ListSEException("La lista está vacía");
        }
        size++;
    }

    /*
    Si la lista tiene aunque sea un elemento para agregar, se crea un nuevo nodo que contiene un niño
    si no contiene datos la cabeza, que se añada a un nuevo nodo
    llamo a un ayudante y le digo que se posicione en la cabeza
    mientras en el brazo exista algo que se pase
        metemos el niño en el costal y ese costal es la cabeza
     */
    public void addToFinal (Kid kid) {
        Node newNode = new Node(kid);
        if (head == null) {
            head = newNode;
            return;
        }
        Node temp = head;
        while (temp.getNext() != null) {
            temp = temp.getNext();
        }
        temp.setNext(newNode);
    }

}